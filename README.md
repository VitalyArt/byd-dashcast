# MyBYDApp — BYD Cluster Launcher & Mirror

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![API 29](https://img.shields.io/badge/API-29%20(Android%2010)-green.svg)](https://developer.android.com/about/versions/10)
[![Status: Alpha](https://img.shields.io/badge/Status-Alpha-red.svg)](CHANGELOG.md)

Android application for **BYD Seal EU** (DiLink 3.0 — Android 10) to push any installed app
onto the instrument cluster display, control it via a real-time touch mirror, and diagnose
BYD APIs.

> **Tested on**: BYD Seal EU 2024 — DiLink 3.0 (XDJA/Qualcomm 6125F) — Android 10 (API 29)

> [!WARNING]
> **Alpha software** — This project is in early alpha. Expect bugs, incomplete features,
> and breaking changes between releases. Use at your own risk.
> The authors are not responsible for any damage to your vehicle's infotainment system.

---

## Features

| # | Feature | Description |
|---|---|---|
| 1 | **App list** | All installed apps (sorted RecyclerView) |
| 2 | **→ Cluster** | Push an app to the cluster (ADB trampoline uid=2000 + display=1 FREEFORM) |
| 3 | **→ Main screen** | Move an app from the cluster back to display 0 |
| 4 | **Touch mirror** | Real-time TextureView of the cluster via `SurfaceControl.createDisplay()` + touch forwarding |
| 5 | **Split 50/50** | Two apps side by side on the cluster (force-stop + relaunch with `--bounds`) |
| 6 | **Remote control** | ←/⌂/↑/↓/Vol+/Vol− buttons via `InputManager.injectInputEvent()` |
| 7 | **Restore BYD** | `sendInfo(18+0)` → Qt regains control of the cluster |
| 8 | **Origin cluster** | `sendInfo(30+18+0)` → restores correct resolution + Qt |
| 9 | **⚙ Settings** | Cluster screen size: 8.8" / 12.3" (Seal EU default) / 10.25" |
| 10 | **🔧 Diagnostic** | 4 ADB tests (permissions, cluster restore, display size, Freedom BootReceiver) |
| 11 | **📋 System report** | Displays, permissions, build tags, APK signature |
| 12 | **Live log** | LogActivity — DEBUG/INFO/WARN/ERROR levels, filters,  export |
| 13 | **☁  Export** | Push to remote log analytics (HMAC-SHA256, table `BYDAppLog_CL`) |
| 14 | **Multilingual** | French / English, selected on first launch |

---


## 🔓 WindowManagement v1.2 — Reverse Engineering

The **anti-tamper obfuscation** of the `WindowManagement v1.2` third-party app has been
completely reverse-engineered.

* **Obfuscation scheme**: Unicode invisible characters are used as a substitution table
  (class `C0854a.f1372c`) mapping each character to a Base64 character, then Base64-decoded
  to the real UTF-8 string. An optional DES/ECB layer (key `"decrypt"`) is used for a few
  bootstrap strings.
* **Result**: All hidden Binder method names were extracted:
  `openTransaction`, `setDisplaySurface`, `setDisplayProjection`,
  `setDisplayLayerStack`, `closeTransaction`.
* **Our implementation** uses the same static `SurfaceControl` API, confirming compatibility
  with DiLink 3.0.

For full technical details, see `doc_api/DEOBFUSCATION_WindowManagement.md` in the root of this workspace (not included in the repo — proprietary reverse-engineering notes).

---

## Code structure

```
app/src/main/java/com/byd/myapp/
├── MainActivity.java           — Main 15.6" screen: app list, cluster mirror, split
├── WelcomeActivity.java        — Language selection (first launch)
├── DiagActivity.java           — Tests 1–4 (ADB, restore, display size, BootReceiver)
├── SysInfoActivity.java        — System report + share
├── ClusterService.java         — Foreground service: cluster projection independent of Activity lifecycle
├── AdbLocalClient.java         — All ADB logic (dadb, localhost:5555)
├── AppListAdapter.java         — RecyclerView (→ Cluster / ← Main / → Cluster / ✕)
├── AppLogger.java              — Singleton logger (levels, 3000 entries, saveToFile, share)
├── LogExporter.java       — HTTP Data Collector → remote log analytics
├── LogActivity.java            — Real-time log (filters, auto-scroll, )
├── FloatingLogButton.java      — Floating overlay (DEBUG builds only)
├── LocaleHelper.java           — Language persistence (SharedPreferences)
├── daemon/
│   └── MirrorDaemon.java        — Core proxy class mirroring cluster display
└── dashboard/
    ├── ClusterManager.java          — Cluster activation sequence (sendInfo 30+16, Freedom fallback)
    ├── DashboardDisplayHelper.java  — Cluster VirtualDisplay detection (DisplayManager + polling)
    ├── DashboardLauncher.java       — Launch app on main display (setLaunchDisplayId)
    ├── ClusterTrampolineActivity.java — Exported trampoline launched via ADB uid=2000 on display 1
    ├── ClusterMirrorManager.java    — SurfaceControl mirror (createDisplay + Transaction + touch)
    └── ClusterInputForwarder.java   — MotionEvent/KeyEvent injection to the cluster
```

---

## Core mechanism

### Cluster activation

```
sendInfo(1000, 30)   → switch cluster to Seal EU 12.3" mode (correct resolution, no ADAS stretching)
wait ~1 s
sendInfo(1000, 16)   → Qt standby (全屏投屏开启) — releases the surface for our app
wait ~2 s
am start --display 1 --windowingMode 5 ClusterTrampolineActivity --es target_package <pkg>
```

`sendInfo` is sent via **ADB relay** (uid=2000) because our app (uid=10xxx) is blocked
by `AutoContainerService.checkSendPermissionAndAllowType()`.

### Launching an app on the cluster

`ClusterTrampolineActivity` is **exported** in the Manifest. ADB shell (uid=2000) launches
it on `display=1` with `--windowingMode 5` (FREEFORM). Once on display 1, the trampoline
starts the target app via `startActivity()` without `setLaunchDisplayId` — the task
inherits the source display.

> **Why a trampoline?** Our APK is signed with the BYD SDK `platform.keystore`
> (CN=Android — AOSP testkey), not with the real BYD `auto_api` key (CN=auto_api, O=比亚迪).
> `INTERNAL_SYSTEM_WINDOW` is therefore not granted to our app (uid=10xxx). ADB shell
> (uid=2000) holds it on this ROM → it launches our exported trampoline.

### Real-time mirror

```java
// 1. Unlock @hide Android APIs (same mechanism as WindowManagement v1.2)
VMRuntime.setHiddenApiExemptions(["Landroid/", "Lcom/android/", "Ljava/lang/"]);

// 2. Create a virtual mirror display
IBinder token = SurfaceControl.createDisplay("byd_cluster_mirror", true);
// secure=true required on DiLink 3.0 (same as WindowManagement v1.2)

// 3. Project the cluster display onto the TextureView surface
SurfaceControl.openTransaction();
SurfaceControl.setDisplaySurface(token, new Surface(textureView.getSurfaceTexture()));
SurfaceControl.setDisplayLayerStack(token, clusterLayerStack);
SurfaceControl.setDisplayProjection(token, 0, srcRect, dstRect);
SurfaceControl.closeTransaction();
```

Touch: `MotionEvent.setDisplayId(clusterDisplayId)` + `InputManager.injectInputEvent()`.

### Restore

```
am force-stop <app>                  → releases the Qt surface
am force-stop com.xdja.clusterdemo   → stops Freedom (prevents it from reclaiming display 1)
sendInfo(1000, 18)                   → 投屏关闭 — close projection
sendInfo(1000, 0)                    → 主机恢复仪表视频流 — Qt resumes
```

---

## Prerequisites

### 1. ADB over network (TCP/IP)

The app communicates with the car via **ADB TCP/IP on port 5555** (localhost, tunneled from the infotainment unit itself). This requires ADB to be enabled on the DiLink system.

On BYD Seal EU (DiLink 3.0), ADB TCP is available at `localhost:5555` from within the infotainment Android environment — no USB cable needed at runtime. The app uses the [dadb](https://github.com/mobile-dev-inc/dadb) library to connect.

### 2. Platform keystore

The APK must be signed with `platform.keystore` (included in the BYD SDK v1.0.5) to obtain `signature`-level permissions (`INJECT_EVENTS`, `BYDAUTO_*`).

Place it at `app/keystore/platform.keystore` before building.

### 3. BYD SDK

See [Build requirements](#build-requirements) below.

---

## Installation

1. Build the APK (see [Build](#build))
2. Sideload onto the infotainment unit:
```bash
adb connect <car-ip>:5555
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
3. Launch the app and run **TEST 1** (Diagnostic) to grant `pm grant` permissions

> If you don't have the car's IP, the app can also be installed via USB when ADB USB debugging is enabled (developer options).

---

## Known issues (alpha)

- **Reliability**: The cluster activation sequence may fail on the first attempt — retry
- **Freedom dependency**: If Freedom (`com.xdja.clusterdemo`) is not installed, `display=1` is used as a hardcoded fallback, which may not always work
- **App persistence**: Apps launched on the cluster may return to the main display after a phone call or ADAS event (Qt reclaims the surface)
- **Split 50/50**: Experimental — may fail depending on target app window mode
- ** export**: Optional feature requiring a personal remote log analytics workspace (configure `local.properties`)
- **Language**: UI is bilingual (FR/EN) but some log messages are in French

---

## Build requirements

| Tool | Version |
|---|---|
| JDK | 11 (Temurin recommended) |
| Android SDK | API 29 compileSdk, **BYD SDK v1.0.5** as sdk.dir |
| AGP | 7.4.2 |
| Gradle wrapper | 7.6 |

### BYD SDK

This project requires BYD SDK v1.0.5 (modified `android.jar` with `android.hardware.bydauto.*`).

> The SDK is **not included** in this repository (proprietary).  
> Extract to: `../sdk/SDK_v1.0.5/byd-auto_sdk_windows/`  
> Configure `local.properties`:

```properties
sdk.dir=/path/to/sdk/SDK_v1.0.5/byd-auto_sdk_windows


```

### Signing

The APK must be signed with `platform.keystore` (BYD SDK) for `signature` permissions
(`INJECT_EVENTS`, `BYDAUTO_*_COMMON`).

```
app/keystore/platform.keystore
  alias: androiddebugkey | storepass/keypass: android
```

The `app/build.gradle` signing config applies this keystore for both debug and release.

---

## Build

```bash
cd MyBYDApp
./gradlew assembleDebug
# APK → app/build/outputs/apk/debug/app-debug.apk
```

---

## Permissions

| Permission | Type | Usage |
|---|---|---|
| `INJECT_EVENTS` | signature | Touch/key injection to the cluster |
| `SYSTEM_ALERT_WINDOW` | dangerous | Floating overlay (FloatingLogButton) |
| `FOREGROUND_SERVICE` | normal | ClusterService |
| `INTERNET` | normal | remote log analytics export |
| `BYDAUTO_*_COMMON` (×11) | dangerous | Vehicle data (speed, energy, gearbox…) |
| `BYDAUTO_*_GET` | signature | Extended read (not grantable without real BYD key) |

`dangerous` permissions are granted via `pm grant` on first launch (TEST 1 — Diagnostic).

---

## AutoContainer service (cluster)

- Binder: `ServiceManager.getService("AutoContainer")`
- Transaction `#2` = `sendInfo(int type, int infoInt, String infoStr)`
- ADB relay: `service call AutoContainer 2 i32 1000 i32 <cmd> s16 ""`

| cmd | Action | Confirmed |
|-----|--------|---------|
| 30 | Cluster 12.3" Seal EU | ✅ 16/04/2026 |
| 16 | Qt standby (enable projection) | ✅ 16/04/2026 |
| 18 | Close projection | ✅ 16/04/2026 |
| 0  | Refresh Qt video stream | ✅ |
| 1  | **⛔ DO NOT USE** — disconnects Qt entirely (destroys display 1) | — |

---

## Freedom (com.xdja.clusterdemo)

Freedom state is **checked at startup** before the cluster activation sequence.
`ClusterService.checkAndStartWithFreedom()` runs `AdbLocalClient.checkFreedomState()` and:

| State | Action |
|-------|--------|
| `ACTIVE` — VirtualDisplay `fission_*` present | Proceed directly to `sendInfo(30+16)` |
| `INACTIVE` — installed but VirtualDisplay absent | `startFreedom()` (force-stop + write `navigationType=1` + `am start`) → wait 2 s → activate |
| `NOT_INSTALLED` | Proceed anyway (fallback to display id=1 hardcoded) |

The current state is displayed in the main status bar (`tvDashboardStatus`).

`AutoDisplayService` (com.xdja.containerservice) creates the VirtualDisplay at boot:
```
createVirtualDisplay("fission_testVirtualSurface", 1920, 1080, 320, qtSurface, 11)
flags 11 = PUBLIC | PRESENTATION | OWN_CONTENT_ONLY
```

Freedom config file:
```
/sdcard/Android/data/com.xdja.clusterdemo/data/properties.xml
```
Java-serialized HashMap (ObjectOutputStream): `{"navigationType": Integer(1)}`  
→ `navigationType=1` = 全屏导航 (full-screen). Default (file absent) = 0 →
Freedom returns immediately without creating the VirtualDisplay.

---

## Field diagnostic

1. **TEST 1** → ADB connection + `pm grant` `_COMMON` permissions
2. **TEST 2** → cluster restore (sendInfo 30→16→18→0)
3. **TEST 3** → cluster display size change (cmd 29/30/31)
4. **TEST 4** → BOOT_COMPLETED broadcast to Freedom BootReceiver (headless)

### Retrieve logs without USB cable

```bash
adb pull /sdcard/Android/data/com.byd.myapp/files/
```

###  KQL queries

```kql
BYDAppLog_CL | order by TimeGenerated desc | take 200
BYDAppLog_CL | where Level_s in ("WARN","ERROR") | order by TimeGenerated desc
BYDAppLog_CL | where Tag_s in ("ClusterMirrorManager","AdbLocalClient","ClusterManager")
```

---

## Version history

| Version | Summary |
|---------|---------|
| **0.1.0-alpha** | First public release — cluster mirror working (image + touch) |

Full internal development history: [CHANGELOG.md](CHANGELOG.md)

---

## License

This project is licensed under the [MIT License](LICENSE).

> **Note on dependencies**: This project requires **BYD SDK v1.0.5** (proprietary) which
> is NOT included in this repository and is NOT covered by the MIT license.
> The BYD SDK contains a modified `android.jar` with `android.hardware.bydauto.*` APIs.
> You must obtain it separately.
>
> Freedom (`com.xdja.clusterdemo`) and WindowManagement are third-party applications
> (not BYD) whose behavior has been analyzed for interoperability purposes only.

