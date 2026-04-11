package com.byd.myapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.util.Log;
import android.content.pm.ResolveInfo;

import com.byd.myapp.AppLogger;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.byd.myapp.dashboard.ClusterInputForwarder;
import com.byd.myapp.dashboard.DashboardLauncher;
import com.byd.myapp.model.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MainActivity — écran principal 15 pouces.
 *
 * Affiche la liste des apps installées. L'utilisateur choisit une app et
 * clique "→ Dashboard" pour l'envoyer sur le petit écran derrière le volant.
 * Le bouton "Restaurer BYD" ramène le widget vitesse/batterie/rapport.
 */
public class MainActivity extends AppCompatActivity
        implements ClusterService.Listener,
                   AppListAdapter.OnSendToDashboardListener {

    private static final String TAG = "BYDApp";

    // Service cluster
    private ClusterService          mClusterService;
    private boolean                 mServiceBound    = false;
    private boolean                 mBindRequested   = false; // vrai dès qu'un bindService est en cours
    private DashboardLauncher       mDashboardLauncher; // référence locale mise à jour après bind
    private ClusterInputForwarder   mClusterInputForwarder;

    // savedItem : package de la dernière app envoyée sur le cluster
    private static final String PREFS_NAME      = "byd_app_prefs";
    private static final String PREF_LAST_APP   = "last_app_package";
    // App en attente d'envoi pendant l'auto-activation du cluster
    private String mPendingLaunchPackage = null;

    private final ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mClusterService = ((ClusterService.LocalBinder) binder).getService();
            mServiceBound   = true;
            mDashboardLauncher = mClusterService.getLauncher();
            mClusterService.setListener(MainActivity.this);
            AppLogger.log(TAG, "Bind ClusterService OK — displayId=" + mClusterService.getDisplayId());
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound   = false;
            mBindRequested  = false; // autoriser un nouveau bindService si nécessaire
            mClusterService = null;
            AppLogger.log(TAG, "ClusterService déconnecté");
        }
    };
    private String mCurrentDashboardApp = null;

    // UI — barre statut
    private TextView tvDashboardStatus;
    private Button   btnRestoreByd;
    private Button   btnOverflow;
    private RecyclerView rvApps;
    private AppListAdapter mAdapter;

    // UI — panel contrôle cluster
    private LinearLayout panelClusterControl;
    private TextView     tvControlAppName;
    private FrameLayout  clusterTouchpad;

    private static final int REQ_COMMON_PERMS = 43;
    private static final String[] COMMON_PERMS = {
        "android.permission.BYDAUTO_SPEED_COMMON",
        "android.permission.BYDAUTO_ENERGY_COMMON",
        "android.permission.BYDAUTO_GEARBOX_COMMON",
        "android.permission.BYDAUTO_BODYWORK_COMMON"
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.applyLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Demander les permissions COMMON au démarrage (obligatoire sur ROM BYD)
        // afin qu'elles soient déjà accordées quand BYDDashboardActivity / BYDLiveActivity démarrent.
        boolean allGranted = true;
        for (String perm : COMMON_PERMS) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if (!allGranted) {
            ActivityCompat.requestPermissions(this, COMMON_PERMS, REQ_COMMON_PERMS);
        }

        tvDashboardStatus  = (TextView)    findViewById(R.id.tv_dashboard_status);
        btnRestoreByd      = (Button)      findViewById(R.id.btn_restore_byd);
        btnOverflow        = (Button)      findViewById(R.id.btn_overflow);
        rvApps             = (RecyclerView) findViewById(R.id.rv_apps);

        // Liste des apps
        mAdapter = new AppListAdapter(this);
        rvApps.setLayoutManager(new LinearLayoutManager(this));
        rvApps.setAdapter(mAdapter);

        // Bouton "Restaurer BYD"
        btnRestoreByd.setEnabled(false);
        btnRestoreByd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreBydDashboard();
            }
        });

        // Bouton ⋮ overflow — outils dev + activation manuelle
        btnOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOverflowMenu(v);
            }
        });

        // Démarrer le ClusterService maintenant (startForegroundService dans onStart)
        mDashboardLauncher     = new DashboardLauncher(this); // temporaire jusqu'au bind
        mClusterInputForwarder = new ClusterInputForwarder(this);

        // Panel contrôle cluster
        panelClusterControl = (LinearLayout) findViewById(R.id.panel_cluster_control);
        tvControlAppName    = (TextView)     findViewById(R.id.tv_control_app_name);
        clusterTouchpad     = (FrameLayout)  findViewById(R.id.cluster_touchpad);

        // Masquer le panel
        Button btnControlHide = (Button) findViewById(R.id.btn_control_hide);
        btnControlHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelClusterControl.setVisibility(View.GONE);
            }
        });

        // Touchpad : transférer tous les événements tactiles vers le cluster
        clusterTouchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Masquer le hint au premier contact
                TextView hint = (TextView) v.findViewById(R.id.tv_touchpad_hint);
                if (hint != null) hint.setVisibility(View.GONE);

                mClusterInputForwarder.forwardTouch(
                        event.getX(), event.getY(),
                        v.getWidth(), v.getHeight(),
                        event.getAction());
                return true;
            }
        });

        // Boutons de navigation
        ((Button) findViewById(R.id.btn_cluster_back)).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        mClusterInputForwarder.injectKey(KeyEvent.KEYCODE_BACK);
                    }
                });
        ((Button) findViewById(R.id.btn_cluster_home)).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        mClusterInputForwarder.injectKey(KeyEvent.KEYCODE_HOME);
                    }
                });
        ((Button) findViewById(R.id.btn_cluster_up)).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        mClusterInputForwarder.injectKey(KeyEvent.KEYCODE_DPAD_UP);
                    }
                });
        ((Button) findViewById(R.id.btn_cluster_down)).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        mClusterInputForwarder.injectKey(KeyEvent.KEYCODE_DPAD_DOWN);
                    }
                });
        ((Button) findViewById(R.id.btn_cluster_vol_up)).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        mClusterInputForwarder.injectKey(KeyEvent.KEYCODE_VOLUME_UP);
                    }
                });
        ((Button) findViewById(R.id.btn_cluster_vol_down)).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        mClusterInputForwarder.injectKey(KeyEvent.KEYCODE_VOLUME_DOWN);
                    }
                });

        // Charger la liste des apps (async pour ne pas bloquer l'UI)
        new LoadAppsTask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mServiceBound && mClusterService != null) {
            // Activity revenue au premier plan : ré-attacher le listener
            // (onStop l'avait mis à null pour éviter les leaks pendant le background)
            mClusterService.setListener(this);
        } else if (!mBindRequested) {
            // Premier démarrage ou après onDestroy : lancer + binder le service
            mBindRequested = true;
            Intent svcIntent = new Intent(this, ClusterService.class);
            startForegroundService(svcIntent);
            bindService(svcIntent, mServiceConn, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Retirer le listener mais garder le service actif : la projection continue
        if (mServiceBound && mClusterService != null) {
            mClusterService.setListener(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceBound) {
            unbindService(mServiceConn);
            mServiceBound  = false;
            mBindRequested = false;
        }
    }

    // ---- ClusterService.Listener ----

    @Override
    public void onClusterDisplayConnected(Display display, int displayId) {
        Log.i(TAG, "Dashboard display connecté : id=" + displayId);
        AppLogger.log(TAG, "Dashboard connecté — displayId=" + displayId
                + " nom=" + (display != null ? display.getName() : "IActivityManager/fallback"));
        if (mServiceBound && mClusterService != null) {
            mDashboardLauncher = mClusterService.getLauncher();
        }
        mClusterInputForwarder.setClusterDisplay(display);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateDashboardStatus(null);

                // Auto-envoi : app en attente (tap pendant l'activation) ou savedItem
                String toSend = mPendingLaunchPackage;
                if (toSend == null) {
                    toSend = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                            .getString(PREF_LAST_APP, null);
                }
                mPendingLaunchPackage = null;

                if (toSend != null) {
                    final String pkg = toSend;
                    AppLogger.log(TAG, "savedItem : relance auto → " + pkg);
                    // Chercher l'AppInfo correspondant dans l'adapter
                    AppInfo found = null;
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        // Pas d'accès direct à la liste — on lance via DashboardLauncher
                    }
                    boolean launched = mDashboardLauncher.launchOnDashboard(pkg);
                    if (launched) {
                        mCurrentDashboardApp = pkg;
                        mAdapter.setCurrentPackage(pkg);
                        updateDashboardStatus(pkg);
                        AppLogger.log(TAG, "savedItem relancé ✓ → " + pkg);
                    }
                }
            }
        });
    }

    @Override
    public void onClusterDisplayDisconnected() {
        AppLogger.log(TAG, "Dashboard déconnecté");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCurrentDashboardApp = null;
                mAdapter.setCurrentPackage(null);
                tvDashboardStatus.setText(getString(R.string.status_disconnected));
                btnRestoreByd.setEnabled(false);
                panelClusterControl.setVisibility(View.GONE);
            }
        });
    }

    // ---- AppListAdapter.OnSendToDashboardListener ----

    @Override
    public void onSendToDashboard(AppInfo app) {
        if (!mDashboardLauncher.isDashboardAvailable()) {
            // Cluster pas encore prêt : auto-activation + mise en attente de l'app
            AppLogger.log(TAG, "Cluster non prêt — auto-activation pour " + app.packageName);
            mPendingLaunchPackage = app.packageName;
            activateCluster();
            Toast.makeText(this, "Activation du cluster…", Toast.LENGTH_SHORT).show();
            return;
        }

        AppLogger.log(TAG, "Envoi cluster — " + app.packageName
                + " display=" + mDashboardLauncher.getDashboardDisplayId());
        boolean launched = mDashboardLauncher.launchOnDashboard(app.packageName);
        AppLogger.log(TAG, "launchOnDashboard " + app.packageName + " → " + (launched ? "OK" : "ÉCHEC"));
        if (launched) {
            mCurrentDashboardApp = app.appName;
            mAdapter.setCurrentPackage(app.packageName);
            // Persister pour restauration automatique au prochain reconnect
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                    .edit().putString(PREF_LAST_APP, app.packageName).apply();
            updateDashboardStatus(app.appName);
            tvControlAppName.setText(app.appName);
            View hint = clusterTouchpad.findViewById(R.id.tv_touchpad_hint);
            if (hint != null) hint.setVisibility(View.VISIBLE);
            panelClusterControl.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this,
                    getString(R.string.toast_app_incompatible, app.appName),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSendToMain(AppInfo app) {
        boolean launched = mDashboardLauncher.launchOnMainDisplay(app.packageName);
        AppLogger.log(TAG, "Envoi écran principal — " + app.packageName
                + " → " + (launched ? "OK" : "ÉCHEC"));
        if (launched) {
            // L'app quitte le cluster → restauration BYD déjà déclenchée dans launchOnMainDisplay
            mCurrentDashboardApp = null;
            updateDashboardStatus(null);
            panelClusterControl.setVisibility(View.GONE);
        } else {
            Toast.makeText(this,
                    getString(R.string.toast_main_error, app.appName),
                    Toast.LENGTH_LONG).show();
        }
    }

    // ---- Restaurer l'affichage BYD d'origine ----

    private void activateCluster() {
        if (!mServiceBound || mClusterService == null) {
            Toast.makeText(this, "Service non prêt — attendez quelques secondes", Toast.LENGTH_SHORT).show();
            return;
        }
        tvDashboardStatus.setText("Activation cluster…");
        AppLogger.log(TAG, "Activation cluster — ClusterService.restartProjection()");
        mClusterService.restartProjection();

        // Afficher un message d'échec si le display ne répond pas sous 9s
        tvDashboardStatus.postDelayed(new Runnable() {
            @Override public void run() {
                if (!mServiceBound || mClusterService == null
                        || mClusterService.getDisplayId() < 0) {
                    mPendingLaunchPackage = null; // annuler l'envoi en attente
                    tvDashboardStatus.setText(getString(R.string.status_disconnected));
                }
            }
        }, 9000);
    }

    /** Menu ⋮ — outils développeur accessibles sans encombrer la barre. */
    private void showOverflowMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add(0, 1, 0, "🖥 Activer cluster");
        popup.getMenu().add(0, 2, 0, "⚙ Diagnostic");
        popup.getMenu().add(0, 3, 0, "📋 Rapport système");
        popup.getMenu().add(0, 4, 0, "📊 Données live BYD");
        popup.getMenu().add(0, 5, 0, "🌐 Langue");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1: activateCluster(); return true;
                    case 2: startActivity(new Intent(MainActivity.this, DiagActivity.class)); return true;
                    case 3: startActivity(new Intent(MainActivity.this, SysInfoActivity.class)); return true;
                    case 4: startActivity(new Intent(MainActivity.this, BYDLiveActivity.class)); return true;
                    case 5:
                        android.content.SharedPreferences p = getSharedPreferences(
                                LocaleHelper.PREF_FILE, MODE_PRIVATE);
                        p.edit().remove(LocaleHelper.PREF_SETUP_DONE).apply();
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private void restoreBydDashboard() {
        final int displayId = mDashboardLauncher.getDashboardDisplayId();
        if (displayId < 0) {
            Toast.makeText(this, getString(R.string.toast_dashboard_unavailable), Toast.LENGTH_SHORT).show();
            return;
        }
        btnRestoreByd.setEnabled(false);
        AdbLocalClient.restoreBydOnCluster(this, displayId, new AdbLocalClient.Callback() {
            @Override
            public void onSuccess(String report) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        mCurrentDashboardApp = null;
                        updateDashboardStatus(null);
                        panelClusterControl.setVisibility(View.GONE);
                        AppLogger.log(TAG, "BYD restauré ✓");
                    }
                });
            }
            @Override
            public void onError(final String error) {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        btnRestoreByd.setEnabled(true);
                        Toast.makeText(MainActivity.this,
                                "Restauration échouée: " + error, Toast.LENGTH_LONG).show();
                        AppLogger.log(TAG, "Restauration ÉCHEC: " + error);
                    }
                });
            }
        });
    }

    private void updateDashboardStatus(String appName) {
        if (appName == null) {
            tvDashboardStatus.setText("Dashboard : affichage BYD");
            btnRestoreByd.setEnabled(false);
        } else {
            tvDashboardStatus.setText("Dashboard : " + appName);
            btnRestoreByd.setEnabled(true);
        }
    }

    // ---- Chargement async de la liste des apps ----

    private class LoadAppsTask extends AsyncTask<Void, Void, List<AppInfo>> {
        @Override
        protected List<AppInfo> doInBackground(Void... voids) {
            PackageManager pm = getPackageManager();
            Intent launcherIntent = new Intent(Intent.ACTION_MAIN, null);
            launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> resolveInfos = pm.queryIntentActivities(launcherIntent, 0);
            List<AppInfo> apps = new ArrayList<>();

            for (ResolveInfo ri : resolveInfos) {
                String pkg = ri.activityInfo.packageName;
                // Exclure notre propre app
                if (pkg.equals(getPackageName())) continue;

                String name = ri.loadLabel(pm).toString();
                apps.add(new AppInfo(pkg, name, ri.loadIcon(pm)));
            }

            // Trier par nom
            Collections.sort(apps, new Comparator<AppInfo>() {
                @Override
                public int compare(AppInfo a, AppInfo b) {
                    return a.appName.compareToIgnoreCase(b.appName);
                }
            });

            return apps;
        }

        @Override
        protected void onPostExecute(List<AppInfo> apps) {
            mAdapter.setApps(apps);
        }
    }

}

