package com.byd.myapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.graphics.PixelFormat;

/**
 * DiagActivity — Diagnostic tools and configuration.
 *
 * TEST 1 — Local ADB connection (prerequisite, to run once)
 * TEST 2 — Cluster restore (sendInfo 30→16→18→0)
 * TEST 3 — Cluster display size (cmd 29 / 30 / 31)
 */
public class DiagActivity extends AppCompatActivity {

    // TEST 1 — Local ADB connection
    private TextView tvAdbLocalResult;
    private Button   btnAdbLocal;
    private Button   btnAdbShare;

    // TEST 2 — Cluster restore
    private TextView tvDisplay1Result;
    private Button   btnDisplay1;
    private Button   btnDisplay1Share;

    // TEST 3 — Cluster display size
    private TextView tvDisplaySizeResult;
    private Button   btnDisplaySize88;       // cmd 29 — 8.8"
    private Button   btnDisplaySize123;      // cmd 30 — 12.3"
    private Button   btnDisplaySize1025;     // cmd 31 — 10.25"
    private Button   btnDisplaySizeRestore;  // restore
    private Button   btnDisplaySizeFull;     // full diagnostic
    private Button   btnDisplaySizeShare;

    private Button   btnDumpSfMirror;
    private TextView tvSfDumpResult;

    // TEST 7 — Cluster orientation
    private Button   btnOrientFreezeLandscape;
    private Button   btnOrientFreezePortrait;
    private Button   btnOrientUnfreeze;
    private Button   btnOrientRead;
    private TextView tvOrientationResult;

    // TEST 13 — JNI Qt Surface
    private Button   btnTest13;
    private TextView tvTest13Result;

    // TEST 14
    private Button   btnVdTest;
    private TextView tvVdResult;

    // TEST 15
    private Button   btnDumpsysWindows;
    private TextView tvDumpsysResult;

    // TEST 16
    private Button btnDaemonVdTest;
    private TextView tvDaemonVdResult;

    // SNIFFER RE
    private Button   btnReSnifferStart;
    private Button   btnReSnifferStop;
    private Button   btnReSnifferSnapshot;
    private Button   btnReSnifferExport;
    private TextView tvReSnifferStatus;
    private java.io.File mReSnifferFile = null;

    @Override
    protected void attachBaseContext(android.content.Context base) {
        super.attachBaseContext(LocaleHelper.applyLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diag);
        AppLogger.lifecycle(getClass().getSimpleName(), "onCreate");

        tvAdbLocalResult      = (TextView) findViewById(R.id.tv_adb_local_result);
        btnAdbLocal           = (Button)   findViewById(R.id.btn_adb_local);
        btnAdbShare           = (Button)   findViewById(R.id.btn_adb_share);

        tvDisplay1Result      = (TextView) findViewById(R.id.tv_display1_result);
        btnDisplay1           = (Button)   findViewById(R.id.btn_display1);
        btnDisplay1Share      = (Button)   findViewById(R.id.btn_display1_share);

        tvDisplaySizeResult    = (TextView) findViewById(R.id.tv_display_size_result);
        btnDisplaySize88       = (Button)   findViewById(R.id.btn_display_size_88);
        btnDisplaySize123      = (Button)   findViewById(R.id.btn_display_size_123);
        btnDisplaySize1025     = (Button)   findViewById(R.id.btn_display_size_1025);
        btnDisplaySizeRestore  = (Button)   findViewById(R.id.btn_display_size_restore);
        btnDisplaySizeFull     = (Button)   findViewById(R.id.btn_display_size_full);
        btnDisplaySizeShare    = (Button)   findViewById(R.id.btn_display_size_share);

        btnDumpSfMirror = (Button) findViewById(R.id.btn_dump_sf_mirror);
        tvSfDumpResult = (TextView) findViewById(R.id.tv_sf_dump_result);

        // TEST 7 — Cluster orientation
        btnOrientFreezeLandscape = (Button)   findViewById(R.id.btn_orient_freeze_landscape);
        btnOrientFreezePortrait  = (Button)   findViewById(R.id.btn_orient_freeze_portrait);
        btnOrientUnfreeze        = (Button)   findViewById(R.id.btn_orient_unfreeze);
        btnOrientRead            = (Button)   findViewById(R.id.btn_orient_read);
        tvOrientationResult      = (TextView) findViewById(R.id.tv_orientation_result);

        // TEST 13
        btnTest13      = (Button)   findViewById(R.id.btn_test_13);
        tvTest13Result = (TextView) findViewById(R.id.tv_test_13_result);

        // TEST 14
        btnVdTest      = (Button)   findViewById(R.id.btn_vd_test);
        tvVdResult     = (TextView) findViewById(R.id.tv_vd_result);

        // TEST 15
        btnDumpsysWindows = (Button) findViewById(R.id.btn_dumpsys_windows);
        tvDumpsysResult   = (TextView) findViewById(R.id.tv_dumpsys_result);

        // TEST 16
        btnDaemonVdTest = (Button) findViewById(R.id.btn_daemon_vd_test);
        tvDaemonVdResult = (TextView) findViewById(R.id.tv_daemon_vd_result);

        // SNIFFER RE
        btnReSnifferStart    = (Button)   findViewById(R.id.btn_re_sniffer_start);
        btnReSnifferStop     = (Button)   findViewById(R.id.btn_re_sniffer_stop);
        btnReSnifferSnapshot = (Button)   findViewById(R.id.btn_re_sniffer_snapshot);
        btnReSnifferExport   = (Button)   findViewById(R.id.btn_re_sniffer_export);
        tvReSnifferStatus    = (TextView) findViewById(R.id.tv_re_sniffer_status);

        btnReSnifferStart   .setOnClickListener(v -> startReSniffer());
        btnReSnifferStop    .setOnClickListener(v -> stopReSniffer());
        btnReSnifferSnapshot.setOnClickListener(v -> snapshotReSniffer());
        btnReSnifferExport  .setOnClickListener(v -> exportReSniffer());

        btnDumpSfMirror.setOnClickListener(v -> dumpSurfaceFlinger());
        btnOrientFreezeLandscape.setOnClickListener(v -> orientFreezeDisplay(0));
        btnOrientFreezePortrait .setOnClickListener(v -> orientFreezeDisplay(1));
        btnOrientUnfreeze       .setOnClickListener(v -> orientUnfreezeDisplay());
        btnOrientRead           .setOnClickListener(v -> orientReadDisplay());

        btnTest13.setOnClickListener(v -> runJniSurfaceProbe());
        btnVdTest.setOnClickListener(v -> testVirtualDisplayAPI());
        btnDumpsysWindows.setOnClickListener(v -> runDumpsysWindows());
        btnDaemonVdTest.setOnClickListener(v -> runDaemonVdTest());

        // TEST 1 — Local ADB connection
        btnAdbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, tvAdbLocalResult.getText().toString());
                startActivity(Intent.createChooser(intent, getString(R.string.diag_share_result1_btn)));
            }
        });
        btnAdbLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdbLocal.setEnabled(false);
                tvAdbLocalResult.setText(getString(R.string.diag_adb_connecting));
                AppLogger.log("DiagADB", "Starting local ADB connection");
                AdbLocalClient.connectAndGrant(DiagActivity.this,
                        new AdbLocalClient.Callback() {
                    @Override
                    public void onSuccess(final String report) {
                        runOnUiThread(new Runnable() {
                            @Override public void run() {
                                tvAdbLocalResult.setText(getString(R.string.diag_adb_connected) + report);
                                btnAdbLocal.setEnabled(true);
                            }
                        });
                    }
                    @Override
                    public void onError(final String error) {
                        runOnUiThread(new Runnable() {
                            @Override public void run() {
                                tvAdbLocalResult.setText(
                                        getString(R.string.diag_adb_failed, error));
                                btnAdbLocal.setEnabled(true);
                            }
                        });
                    }
                });
            }
        });

        // TEST 2 — Restauration cluster
        btnDisplay1Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, tvDisplay1Result.getText().toString());
                startActivity(Intent.createChooser(intent, getString(R.string.diag_share_result2_btn)));
            }
        });
        btnDisplay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runDisplayOneLaunch();
            }
        });

        // TEST 3 — Taille display cluster
        btnDisplaySizeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, tvDisplaySizeResult.getText().toString());
                startActivity(Intent.createChooser(intent, getString(R.string.diag_share_result3_btn)));
            }
        });
        btnDisplaySize88.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendScreenSize(29); }
        });
        btnDisplaySize123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendScreenSize(30); }
        });
        btnDisplaySize1025.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendScreenSize(31); }
        });
        btnDisplaySizeRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { restoreDisplaySize(); }
        });
        btnDisplaySizeFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { runClusterDisplaySizeTest(); }
        });

    }


    // -------------------------------------------------------------------------
    // TEST 3 : Taille display cluster — helpers
    // -------------------------------------------------------------------------

    private void setDisplaySizeBtnsEnabled(boolean enabled) {
        btnDisplaySize88.setEnabled(enabled);
        btnDisplaySize123.setEnabled(enabled);
        btnDisplaySize1025.setEnabled(enabled);
        btnDisplaySizeRestore.setEnabled(enabled);
        btnDisplaySizeFull.setEnabled(enabled);
    }

    private void sendScreenSize(final int sizeCmd) {
        setDisplaySizeBtnsEnabled(false);
        String label = sizeCmd == 29 ? "8.8\"" : sizeCmd == 30 ? "12.3\"" : "10.25\"";
        tvDisplaySizeResult.setText(getString(R.string.diag_size_sending, sizeCmd, label));
        tvDisplaySizeResult.setBackgroundColor(0xFF111A1A);
        AppLogger.log("DiagDisplaySize", "sendClusterScreenSize(" + sizeCmd + ")");

        AdbLocalClient.sendClusterScreenSize(DiagActivity.this, sizeCmd,
                new AdbLocalClient.Callback() {
            @Override public void onSuccess(final String report) {
                runOnUiThread(new Runnable() { @Override public void run() {
                    tvDisplaySizeResult.setBackgroundColor(0xFF1A2A1A);
                    tvDisplaySizeResult.setText(report);
                    setDisplaySizeBtnsEnabled(true);
                    AppLogger.log("DiagDisplaySize", report);
                }});
            }
            @Override public void onError(final String error) {
                runOnUiThread(new Runnable() { @Override public void run() {
                    tvDisplaySizeResult.setBackgroundColor(0xFF2A1A1A);
                    tvDisplaySizeResult.setText("❌ " + error
                            + "\n\n" + getString(R.string.diag_adb_test1_hint));
                    setDisplaySizeBtnsEnabled(true);
                    AppLogger.log("DiagDisplaySize", "ERREUR: " + error);
                }});
            }
        });
    }

    private void restoreDisplaySize() {
        setDisplaySizeBtnsEnabled(false);
        tvDisplaySizeResult.setText(getString(R.string.diag_size_restoring));
        tvDisplaySizeResult.setBackgroundColor(0xFF111A1A);
        AppLogger.log("DiagDisplaySize", "resetClusterDisplaySize");

        AdbLocalClient.resetClusterDisplaySize(DiagActivity.this,
                new AdbLocalClient.Callback() {
            @Override public void onSuccess(final String report) {
                runOnUiThread(new Runnable() { @Override public void run() {
                    tvDisplaySizeResult.setBackgroundColor(0xFF1A1A2A);
                    tvDisplaySizeResult.setText(report);
                    setDisplaySizeBtnsEnabled(true);
                    AppLogger.log("DiagDisplaySize", report);
                }});
            }
            @Override public void onError(final String error) {
                runOnUiThread(new Runnable() { @Override public void run() {
                    tvDisplaySizeResult.setBackgroundColor(0xFF2A1A1A);
                    tvDisplaySizeResult.setText("❌ " + error);
                    setDisplaySizeBtnsEnabled(true);
                }});
            }
        });
    }

    private void runClusterDisplaySizeTest() {
        setDisplaySizeBtnsEnabled(false);
        tvDisplaySizeResult.setText(getString(R.string.diag_size_full_running));
        tvDisplaySizeResult.setBackgroundColor(0xFF111A1A);
        AppLogger.log("DiagDisplaySize", "Lancement TEST 3 complet");

        AdbLocalClient.runClusterDisplaySizeTest(DiagActivity.this, new AdbLocalClient.Callback() {
            @Override
            public void onSuccess(final String report) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        tvDisplaySizeResult.setBackgroundColor(0xFF1A2A1A);
                        tvDisplaySizeResult.setText(report);
                        setDisplaySizeBtnsEnabled(true);
                        AppLogger.log("DiagDisplaySize", report);
                    }
                });
            }
            @Override
            public void onError(final String error) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        tvDisplaySizeResult.setBackgroundColor(0xFF2A1A1A);
                        tvDisplaySizeResult.setText("❌ " + error
                                + "\n\n" + getString(R.string.diag_adb_test1_hint));
                        setDisplaySizeBtnsEnabled(true);
                        AppLogger.log("DiagDisplaySize", "ERREUR: " + error);
                    }
                });
            }
        });
    }

    // -------------------------------------------------------------------------
    // TEST 2 : Lancement sur display 1 (cluster) — restauration cluster
    // -------------------------------------------------------------------------

    private void runDisplayOneLaunch() {
        btnDisplay1.setEnabled(false);
        tvDisplay1Result.setText(getString(R.string.diag_launching_display1));
        AppLogger.log("DiagDisplay1", "display 1 launch started");

        AdbLocalClient.runDisplayOneLaunch(DiagActivity.this, new AdbLocalClient.Callback() {
            @Override
            public void onSuccess(final String report) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        // TEST 2 no longer starts am start — check AutoContainer parcel responses.
                        // A valid parcel response contains "00000000" (empty parcel = success).
                        // If there is no explicit error in the report → success.
                        boolean ok = !report.contains("Exception")
                                && !report.contains("Error:")
                                && !report.contains("FAILED");
                        tvDisplay1Result.setBackgroundColor(ok ? 0xFF1A2A1A : 0xFF1A1A2A);
                        tvDisplay1Result.setText(report);
                        btnDisplay1.setEnabled(true);
                        AppLogger.log("DiagDisplay1", report);
                    }
                });
            }
            @Override
            public void onError(final String error) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        tvDisplay1Result.setBackgroundColor(0xFF2A1A1A);
                        tvDisplay1Result.setText("❌ " + error
                                + "\n\n" + getString(R.string.diag_adb_test1_hint));
                        btnDisplay1.setEnabled(true);
                        AppLogger.log("DiagDisplay1", "ERREUR: " + error);
                    }
                });
            }
        });
    }



    private void dumpSurfaceFlinger() {
        tvSfDumpResult.setText(getString(R.string.diag_sf_dumping));
        tvSfDumpResult.setTextColor(0xFFAAAAAA);
        // Filter on our display + layerStack=2 to check if SF knows about the mirror
        String cmd = "dumpsys SurfaceFlinger 2>/dev/null"
                + " | grep -iE 'byd_myapp_mirror|layerStack=2|fission_bg|virtual'";
        AdbLocalClient.executeShellWithResult(this, cmd, new AdbLocalClient.Callback() {
            @Override public void onSuccess(String report) {
                runOnUiThread(() -> {
                    String text = report.trim().isEmpty()
                            ? getString(R.string.diag_sf_no_result)
                            : report.trim();
                    tvSfDumpResult.setText(text);
                    boolean found = report.contains("byd_myapp_mirror");
                    tvSfDumpResult.setTextColor(found ? 0xFF69F0AE : 0xFFFF5252);
                    AppLogger.i("SFDump", "SF dump :\n" + text);
                });
            }
            @Override public void onError(String error) {
                runOnUiThread(() -> {
                    tvSfDumpResult.setText(getString(R.string.diag_sf_error, error));
                    tvSfDumpResult.setTextColor(0xFFFF5252);
                });
            }
        });
    }

    // -------------------------------------------------------------------------
    // TEST 7 — Cluster orientation (freezeDisplayRotation via IWindowManager)
    // NOTE: NO wm size call here — it would corrupt the main screen resolution
    //       on Android 10 (DiLink 3.0) because --display is silently ignored.
    // -------------------------------------------------------------------------

    /**
     * Returns the cluster display ID (first non-default display, fallback = 2).
     */
    private int getClusterDisplayId() {
        android.hardware.display.DisplayManager dm =
                (android.hardware.display.DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (dm != null) {
            for (android.view.Display d : dm.getDisplays()) {
                if (d.getDisplayId() != 0) return d.getDisplayId();
            }
        }
        return 2;
    }

    /**
     * Freezes the cluster display rotation via IWindowManager.freezeDisplayRotation().
     * rotation = 0 → ROTATION_0 (landscape), 1 → ROTATION_90 (portrait).
     * Does NOT call wm size — main screen resolution is never touched.
     */
    private void orientFreezeDisplay(int rotation) {
        final int displayId = getClusterDisplayId();
        tvOrientationResult.setText("⏳ freezeDisplayRotation(display=" + displayId
                + ", rotation=" + rotation + ")…");
        tvOrientationResult.setTextColor(0xFFFFAB40);
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            try {
                Class<?> smClass = Class.forName("android.os.ServiceManager");
                android.os.IBinder wmBinder = (android.os.IBinder)
                        smClass.getMethod("getService", String.class).invoke(null, "window");
                Class<?> iwmStub = Class.forName("android.view.IWindowManager$Stub");
                Object iwm = iwmStub.getMethod("asInterface", android.os.IBinder.class)
                        .invoke(null, wmBinder);
                java.lang.reflect.Method freeze = iwm.getClass()
                        .getMethod("freezeDisplayRotation", int.class, int.class);
                freeze.invoke(iwm, displayId, rotation);
                sb.append("✅ freezeDisplayRotation(").append(displayId).append(", ")
                  .append(rotation == 0 ? "LANDSCAPE" : "PORTRAIT").append(") OK\n");
            } catch (Exception e) {
                sb.append("❌ freezeDisplayRotation: ").append(e.getMessage()).append("\n");
            }
            final String result = sb.toString();
            runOnUiThread(() -> {
                tvOrientationResult.setText(result);
                tvOrientationResult.setTextColor(
                        result.contains("✅") ? 0xFF69F0AE : 0xFFFF5252);
            });
        }).start();
    }

    /**
     * Thaws the cluster display rotation via IWindowManager.thawDisplayRotation().
     */
    private void orientUnfreezeDisplay() {
        final int displayId = getClusterDisplayId();
        tvOrientationResult.setText("⏳ thawDisplayRotation(display=" + displayId + ")…");
        tvOrientationResult.setTextColor(0xFFFFAB40);
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            try {
                Class<?> smClass = Class.forName("android.os.ServiceManager");
                android.os.IBinder wmBinder = (android.os.IBinder)
                        smClass.getMethod("getService", String.class).invoke(null, "window");
                Class<?> iwmStub = Class.forName("android.view.IWindowManager$Stub");
                Object iwm = iwmStub.getMethod("asInterface", android.os.IBinder.class)
                        .invoke(null, wmBinder);
                // Try thawDisplayRotation(int displayId) first (API 30+),
                // fall back to thawRotation() (API 26-29).
                try {
                    java.lang.reflect.Method thaw = iwm.getClass()
                            .getMethod("thawDisplayRotation", int.class);
                    thaw.invoke(iwm, displayId);
                    sb.append("✅ thawDisplayRotation(").append(displayId).append(") OK\n");
                } catch (NoSuchMethodException e2) {
                    java.lang.reflect.Method thaw = iwm.getClass().getMethod("thawRotation");
                    thaw.invoke(iwm);
                    sb.append("✅ thawRotation() OK (fallback API 29)\n");
                }
            } catch (Exception e) {
                sb.append("❌ thawDisplayRotation: ").append(e.getMessage()).append("\n");
            }
            final String result = sb.toString();
            runOnUiThread(() -> {
                tvOrientationResult.setText(result);
                tvOrientationResult.setTextColor(
                        result.contains("✅") ? 0xFF69F0AE : 0xFFFF5252);
            });
        }).start();
    }

    /**
     * Reads current display rotation via ADB (wm rotation -d N or dumpsys display).
     */
    private void orientReadDisplay() {
        final int displayId = getClusterDisplayId();
        tvOrientationResult.setText("⏳ Reading display " + displayId + "…");
        tvOrientationResult.setTextColor(0xFFFFAB40);
        String cmd = "dumpsys display 2>/dev/null"
                + " | grep -E 'mDisplayId|mName|mCurrentOrientation|mRotation|PhysicalDisplayInfo' | head -20";
        AdbLocalClient.executeShellWithResult(this, cmd, new AdbLocalClient.Callback() {
            @Override public void onSuccess(String result) {
                runOnUiThread(() -> {
                    tvOrientationResult.setText(result.trim());
                    tvOrientationResult.setTextColor(0xFF69F0AE);
                });
            }
            @Override public void onError(String error) {
                runOnUiThread(() -> {
                    tvOrientationResult.setText("❌ " + error);
                    tvOrientationResult.setTextColor(0xFFFF5252);
                });
            }
        });
    }
    // TEST 13 — JNI Surface Probe
    private void runJniSurfaceProbe() {
        tvTest13Result.setText("Liberating Qt Display (sendInfo 16)...");
        btnTest13.setEnabled(false);
        AppLogger.log("DiagJNI", "Starting TEST 13 JNI Surface Probe");

        // 1. Release display
        AdbLocalClient.sendInfo(this, 1000, 16, "", new AdbLocalClient.Callback() {
            @Override
            public void onSuccess(String ignored) {
                runOnUiThread(() -> tvTest13Result.setText("Display released. Probing JNI..."));
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        com.xdja.containerservice.ContainerService.ensureLoaded();
                        com.xdja.containerservice.QtDisplayInfo[] arr = com.xdja.containerservice.ContainerService.getQtProjectionDispInfoArray();
                        com.xdja.containerservice.QtDisplayInfo info = com.xdja.containerservice.ContainerService.getQtProjectionDispInfo(0);

                        StringBuilder res = new StringBuilder();
                        res.append("JNI LOAD: ").append(com.xdja.containerservice.ContainerService.isLoaded).append("\n");
                        if (info != null) {
                            res.append("✅ SUCCESS Qt(0):\n").append(info.toString()).append("\n");
                        } else {
                            res.append("❌ FAIL Qt(0) returned null\n");
                        }
                        if (arr != null) {
                            res.append("✅ SUCCESS Array Size: ").append(arr.length).append("\n");
                            for (int i = 0; i < arr.length; i++) {
                                res.append(" - [").append(i).append("]: ").append(arr[i]).append("\n");
                            }
                        } else {
                            res.append("❌ FAIL Array returned null\n");
                        }

                        AppLogger.log("DiagJNI", res.toString());
                        runOnUiThread(() -> {
                            tvTest13Result.setText(res.toString());
                            btnTest13.setEnabled(true);
                        });
                    } catch (Exception e) {
                        AppLogger.e("DiagJNI", "Exception", e);
                        runOnUiThread(() -> {
                            tvTest13Result.setText("FATAL: " + e.getMessage());
                            btnTest13.setEnabled(true);
                        });
                    }
                }).start();
            }

            @Override
            public void onError(String e) {
                runOnUiThread(() -> {
                    tvTest13Result.setText("Failed to send 16: " + e);
                    btnTest13.setEnabled(true);
                });
            }
        });
    }

    // -------------------------------------------------------------------------
    // TEST 14 : VirtualDisplay API
    // -------------------------------------------------------------------------
    private void testVirtualDisplayAPI() {
        tvVdResult.setText("Appel en cours...");
        btnVdTest.setEnabled(false);
        try {
            DisplayManager dm = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
            ImageReader reader = ImageReader.newInstance(1920, 720, PixelFormat.RGBA_8888, 2);
            VirtualDisplay vd = dm.createVirtualDisplay("remote_dashboard", 1920, 720, 320, reader.getSurface(), 320);
            if (vd != null) {
                tvVdResult.setText("SUCCES MYSTERIEUX ! Un VirtualDisplay a pu etre cree par l'app !\nID: " 
                    + vd.getDisplay().getDisplayId() + " Name: " + vd.getDisplay().getName());
                vd.release();
            } else {
                tvVdResult.setText("Echec : createVirtualDisplay a renvoye null.");
            }
        } catch (SecurityException se) {
            tvVdResult.setText("EXCEPTION DE SECURITE (Attendu) :\n" + se.getMessage());
        } catch (Exception e) {
            tvVdResult.setText("ERREUR : " + e.getMessage());
        }
        btnVdTest.setEnabled(true);
    }

    // -------------------------------------------------------------------------
    // TEST 15 : Dumpsys window displays
    // -------------------------------------------------------------------------
    private void runDumpsysWindows() {
        tvDumpsysResult.setText("Execution via adb local...");
        btnDumpsysWindows.setEnabled(false);
        AdbLocalClient.executeShellWithResult(this, "dumpsys window displays", new AdbLocalClient.Callback() {
            @Override
            public void onSuccess(final String report) {
                runOnUiThread(() -> {
                    tvDumpsysResult.setText(report);
                    btnDumpsysWindows.setEnabled(true);
                });
            }

            @Override
            public void onError(final String error) {
                runOnUiThread(() -> {
                    tvDumpsysResult.setText("ERREUR:\n" + error);
                    btnDumpsysWindows.setEnabled(true);
                });
            }
        });
    }

    private void runDaemonVdTest() {
        tvDaemonVdResult.setText("Lancement du daemon via adb local...");
        btnDaemonVdTest.setEnabled(false);
        try {
            String apkPath = getPackageManager().getApplicationInfo(getPackageName(), 0).sourceDir;
            String cmd = "app_process -Djava.class.path=" + apkPath + " /system/bin com.byd.myapp.dashboard.DashCastDaemon";
            AdbLocalClient.executeShellWithResult(this, cmd, new AdbLocalClient.Callback() {
                @Override
                public void onSuccess(final String report) {
                    runOnUiThread(() -> {
                        tvDaemonVdResult.setText("DAEMON OUTPUT: " + report);
                        btnDaemonVdTest.setEnabled(true);
                    });
                }
                @Override
                public void onError(final String error) {
                    runOnUiThread(() -> {
                        tvDaemonVdResult.setText("ERREUR: " + error);
                        btnDaemonVdTest.setEnabled(true);
                    });
                }
            });
        } catch (Exception e) {
            tvDaemonVdResult.setText("Erreur APK path: " + e.getMessage());
            btnDaemonVdTest.setEnabled(true);
        }
    }

    // =========================================================================
    // SNIFFER SYSTÈME — Reverse Engineering
    // =========================================================================
    // Capture logcat + dumpsys périodiques dans un fichier exportable.
    // Conçu pour intercepter TOUT ce qui se passe sur le système BYD
    // sans dépendre de Freedom.
    // =========================================================================

    private static final String RE_SNIFFER_TAG    = ".re_sniffer_run";
    private static final String RE_SNIFFER_PIDS    = ".re_sniffer_pids";
    private static final String RE_SNIFFER_PREFIX  = "BYD_RE_Sniffer_";

    private java.io.File buildReSnifferFile() {
        String ts = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.US)
                .format(new java.util.Date());
        java.io.File dir = getExternalFilesDir(null);
        if (dir == null) dir = getFilesDir();
        return new java.io.File(dir, RE_SNIFFER_PREFIX + ts + ".txt");
    }

    private void startReSniffer() {
        // Toujours tuer les processus précédents avant de relancer
        killReSnifferProcesses();

        mReSnifferFile = buildReSnifferFile();
        final String p = mReSnifferFile.getAbsolutePath();
        AppLogger.i("RESniffer", "Starting RE Sniffer → " + p);

        // ── Header initial (synchrone) ─────────────────────────────────────────
        String headerCmd =
            "logcat -c 2>/dev/null"
            + " && touch /data/local/tmp/" + RE_SNIFFER_TAG
            + " && echo '============================================' > " + p
            + " && echo '=== BYD REVERSE ENGINEERING SNIFFER ===' >> " + p
            + " && echo '============================================' >> " + p
            + " && date >> " + p
            + " && echo '' >> " + p
            + " && echo '--- DEVICE ---' >> " + p
            + " && getprop ro.product.model >> " + p
            + " && getprop ro.build.fingerprint >> " + p
            + " && echo '' >> " + p
            + " && echo '--- PROCESSUS SYSTEME (snapshot initial) ---' >> " + p
            + " && ps -A >> " + p
            + " && echo '' >> " + p
            + " && echo '--- SERVICES (service list) ---' >> " + p
            + " && service list >> " + p
            + " && echo '' >> " + p
            + " && echo '--- DISPLAYS ---' >> " + p
            + " && dumpsys display 2>/dev/null >> " + p
            + " && echo '' >> " + p
            + " && echo '--- WINDOWS ---' >> " + p
            + " && dumpsys window 2>/dev/null >> " + p
            + " && echo '' >> " + p
            + " && echo '--- SURFACEFLINGER ---' >> " + p
            + " && dumpsys SurfaceFlinger 2>/dev/null >> " + p
            + " && echo '' >> " + p
            + " && echo '--- ACTIVITY MANAGER (broadcasts history) ---' >> " + p
            + " && dumpsys activity broadcasts history 2>/dev/null >> " + p
            + " && echo '' >> " + p
            + " && echo '--- SNIFFER TEMPS REEL DEMARRE ---' >> " + p;

        // ── Logcat temps réel — TOUT intercepter ──────────────────────────────
        // On capture tout (pas de filtre *:S) puis on filtre sur les tags utiles.
        // On garde aussi DEBUG (crash natif) et art (classloader).
        String logcatCmd =
            "logcat -v threadtime"
            + " ActivityManager:V WindowManager:V SurfaceFlinger:V"
            + " DisplayManagerService:V DisplayManagerGlobal:V"
            + " AutoContainer:V MirrorDaemon:V DashCastDaemon:V"
            + " byd:V xdja:V cluster:V dilink:V qt:V container:V"
            + " BYD_*:V Qt*:V DashCast*:V"
            + " Binder:V ServiceManager:V"
            + " am_*:V wm_*:V"
            + " DEBUG:E art:W dalvikvm:W"
            + " *:S"
            + " >> " + p + " 2>&1";

        // ── Snapshots complets toutes les 10s ─────────────────────────────────
        String snapshotCmd =
            "while [ -f /data/local/tmp/" + RE_SNIFFER_TAG + " ]; do sleep 10;"
            + " echo '' >> " + p + ";"
            + " echo '=== SNAPSHOT '$(date +%H:%M:%S)' ===' >> " + p + ";"
            + " dumpsys display 2>/dev/null | grep -E 'mDisplayId|mName|mState|fission|virtual|cluster|Qt|layerStack' >> " + p + ";"
            + " dumpsys SurfaceFlinger 2>/dev/null | grep -iE 'display|fission|layer|cluster|mirror|virtual|qt' | head -30 >> " + p + ";"
            + " dumpsys window 2>/dev/null | grep -iE 'mDisplayId|Window \\{|mSurface|fission|cluster|byd|qt|container' | head -50 >> " + p + ";"
            + " dumpsys activity broadcasts history 2>/dev/null | tail -30 >> " + p + ";"
            + " ps -A 2>/dev/null | grep -iE 'byd|xdja|daemon|mirror|dilink|qt|cluster|app_process' >> " + p + ";"
            + " done";

        // ── Broadcast events (buffer events logcat) ────────────────────────────
        String eventsCmd =
            "logcat -b events -v time >> " + p + " 2>/dev/null";

        // ── Lance les 3 processus et stocke leurs PIDs ────────────────────────
        // On wrappe chaque processus dans un sh qui écrit son PID dans le fichier pids
        // puis exécute la commande. De cette façon, stopReSniffer peut les kill proprement.
        String pidFile = "/data/local/tmp/" + RE_SNIFFER_PIDS;
        String fullCmd = headerCmd
                + " && echo '' > " + pidFile  // reset pids file
                + " && nohup sh -c 'echo $$ >> " + pidFile + "; " + logcatCmd + "' &"
                + " nohup sh -c 'echo $$ >> " + pidFile + "; " + snapshotCmd + "' &"
                + " nohup sh -c 'echo $$ >> " + pidFile + "; " + eventsCmd + "' &";

        AdbLocalClient.executeShell(this, fullCmd);

        runOnUiThread(() -> {
            tvReSnifferStatus.setText("ACTIF → " + mReSnifferFile.getName());
            tvReSnifferStatus.setTextColor(0xFF69F0AE);
            android.widget.Toast.makeText(this,
                    "Sniffer démarré : " + mReSnifferFile.getName(),
                    android.widget.Toast.LENGTH_LONG).show();
        });
    }

    /** Tue proprement tous les processus du sniffer (logcat + snapshot loop + events). */
    private void killReSnifferProcesses() {
        String pidFile = "/data/local/tmp/" + RE_SNIFFER_PIDS;
        // 1. Supprimer le flag → arrête le while loop des snapshots
        // 2. Lire le fichier PIDs et tuer chaque processus
        // 3. Tuer aussi par pattern en fallback (au cas où le PID file est corrompu)
        String killCmd =
            "rm -f /data/local/tmp/" + RE_SNIFFER_TAG
            + " ; if [ -f " + pidFile + " ]; then"
            + "   while read pid; do kill -9 \"$pid\" 2>/dev/null; done < " + pidFile + ";"
            + "   rm -f " + pidFile + ";"
            + " fi"
            // Fallback : kill les processus logcat qui écrivent dans nos fichiers RE Sniffer
            + " ; pkill -f '" + RE_SNIFFER_PREFIX + "' 2>/dev/null || true";
        AdbLocalClient.executeShell(this, killCmd);
    }

    private void stopReSniffer() {
        killReSnifferProcesses();
        final String fileName = mReSnifferFile != null ? mReSnifferFile.getName() : "aucun";
        if (mReSnifferFile != null) {
            AdbLocalClient.executeShell(this,
                    "echo '[RE Sniffer] Stopped.' >> " + mReSnifferFile.getAbsolutePath());
        }
        runOnUiThread(() -> {
            tvReSnifferStatus.setText("Arrêté — fichier : " + fileName);
            tvReSnifferStatus.setTextColor(0xFFFF5252);
            android.widget.Toast.makeText(this, "Sniffer arrêté.", android.widget.Toast.LENGTH_SHORT).show();
        });
    }

    private void snapshotReSniffer() {
        if (mReSnifferFile == null) {
            android.widget.Toast.makeText(this, "Démarrer le sniffer d'abord.", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
        final String p = mReSnifferFile.getAbsolutePath();
        String cmd =
            "echo '' >> " + p
            + " && echo '=== SNAPSHOT MANUEL '$(date +%H:%M:%S)' ===' >> " + p
            + " && echo '--- DISPLAYS ---' >> " + p
            + " && dumpsys display 2>/dev/null >> " + p
            + " && echo '--- WINDOWS ---' >> " + p
            + " && dumpsys window 2>/dev/null >> " + p
            + " && echo '--- SURFACEFLINGER ---' >> " + p
            + " && dumpsys SurfaceFlinger 2>/dev/null >> " + p
            + " && echo '--- PROCESSUS ---' >> " + p
            + " && ps -A >> " + p
            + " && echo '--- BROADCASTS ---' >> " + p
            + " && dumpsys activity broadcasts history 2>/dev/null >> " + p;
        AdbLocalClient.executeShell(this, cmd);
        android.widget.Toast.makeText(this, "Snapshot injecté dans le fichier.", android.widget.Toast.LENGTH_SHORT).show();
    }

    private void exportReSniffer() {
        java.io.File logFile = mReSnifferFile;
        if (logFile == null || !logFile.exists() || logFile.length() == 0) {
            android.widget.Toast.makeText(this, "Aucun fichier sniffer à exporter.", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            android.net.Uri uri = androidx.core.content.FileProvider.getUriForFile(
                    this, getPackageName() + ".fileprovider", logFile);
            android.content.Intent shareIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, logFile.getName());
            shareIntent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(android.content.Intent.createChooser(shareIntent, "Exporter Sniffer RE"));
        } catch (Exception e) {
            AppLogger.e("RESniffer", "Export erreur", e);
        }
    }
}
