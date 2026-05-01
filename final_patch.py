import re

j_path = "app/src/main/java/com/byd/myapp/DiagActivity.java"
x_path = "app/src/main/res/layout/activity_diag.xml"

# JAVA
with open(j_path, "r") as f:
    j = f.read()

# Add to vars
j = j.replace("    private TextView tvDumpsysResult;", "    private TextView tvDumpsysResult;\n\n    // TEST 16\n    private Button btnDaemonVdTest;\n    private TextView tvDaemonVdResult;")

# Add to findViewById
j = j.replace("        tvDumpsysResult   = (TextView) findViewById(R.id.tv_dumpsys_result);", "        tvDumpsysResult   = (TextView) findViewById(R.id.tv_dumpsys_result);\n\n        // TEST 16\n        btnDaemonVdTest = (Button) findViewById(R.id.btn_daemon_vd_test);\n        tvDaemonVdResult = (TextView) findViewById(R.id.tv_daemon_vd_result);")

# Add to listeners
j = j.replace("        btnDumpsysWindows.setOnClickListener(v -> runDumpsysWindows());", "        btnDumpsysWindows.setOnClickListener(v -> runDumpsysWindows());\n        btnDaemonVdTest.setOnClickListener(v -> runDaemonVdTest());")

# Add method
method = """
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
}
"""
j = re.sub(r'\}\s*$', method, j)
with open(j_path, "w") as f:
    f.write(j)

# XML
with open(x_path, "r") as f:
    x = f.read()

xml_part = """
        <!-- TEST 16 -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp"
            android:background="#f5fdf5"
            android:layout_marginBottom="16dp"
            android:elevation="2dp">
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="TEST 16 - Shell Daemon VirtualDisplay"
                android:textStyle="bold"
                android:textColor="#333"/>
            <TextView
                android:id="@+id/tv_daemon_vd_result"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="--"
                android:textSize="10sp"
                android:fontFamily="monospace"
                android:background="#eef"
                android:padding="8dp"
                android:layout_marginBottom="8dp"/>
            <Button
                android:id="@+id/btn_daemon_vd_test"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Creer VirtualDisplay via Daemon ADB"
                android:backgroundTint="#4CAF50"/>
        </LinearLayout>

</LinearLayout>
"""
x = x.replace("</LinearLayout>\n</ScrollView>", xml_part + "\n</ScrollView>")
with open(x_path, "w") as f:
    f.write(x)

