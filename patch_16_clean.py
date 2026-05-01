import re
JAVA_FILE = "app/src/main/java/com/byd/myapp/DiagActivity.java"
XML_FILE = "app/src/main/res/layout/activity_diag.xml"

with open(JAVA_FILE, "r") as f:
    j = f.read()

j = j.replace("""    // TEST 13
    private Button btnTest13;
    private TextView tvTest13Result;""", """    // TEST 13
    private Button btnTest13;
    private TextView tvTest13Result;
    
    // TEST 16
    private Button btnDaemonVdTest;
    private TextView tvDaemonVdResult;""")

j = j.replace("""        // TEST 13
        btnTest13      = (Button)   findViewById(R.id.btn_test_13);
        tvTest13Result = (TextView) findViewById(R.id.tv_test_13_result);""", """        // TEST 13
        btnTest13      = (Button)   findViewById(R.id.btn_test_13);
        tvTest13Result = (TextView) findViewById(R.id.tv_test_13_result);

        // TEST 16
        btnDaemonVdTest = (Button) findViewById(R.id.btn_daemon_vd_test);
        tvDaemonVdResult = (TextView) findViewById(R.id.tv_daemon_vd_result);""")

j = j.replace("""        btnTest13.setOnClickListener(v -> runJniSurfaceProbe());""", """        btnTest13.setOnClickListener(v -> runJniSurfaceProbe());
        btnDaemonVdTest.setOnClickListener(v -> runDaemonVdTest());""")

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
                        tvDaemonVdResult.setText("DAEMON OUTPUT:\\n" + report);
                        btnDaemonVdTest.setEnabled(true);
                    });
                }
                @Override
                public void onError(final String error) {
                    runOnUiThread(() -> {
                        tvDaemonVdResult.setText("ERREUR:\\n" + error);
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

with open(JAVA_FILE, "w") as f:
    f.write(j)

with open(XML_FILE, "r") as f:
    x = f.read()

xml_addition = """
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
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Execute DashCastDaemon.java dans app_process via ADB local pour tester la permission CAPTURE_VIDEO_OUTPUT"
                android:textSize="12sp"
                android:layout_marginBottom="8dp"
                android:textColor="#3b82f6"/>
            <TextView
                android:id="@+id/tv_daemon_vd_result"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="--"
                android:textSize="12sp"
                android:fontFamily="monospace"
                android:background="#eef"
                android:padding="8dp"
                android:layout_marginBottom="8dp"/>
            <Button
                android:id="@+id/btn_daemon_vd_test"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Creer VirtualDisplay (Via Daemon ADB)"
                android:backgroundTint="#4CAF50"/>
        </LinearLayout>

</LinearLayout>
"""
x = x.replace("</LinearLayout>\n</ScrollView>", xml_addition + "\n</ScrollView>")

with open(XML_FILE, "w") as f:
    f.write(x)
