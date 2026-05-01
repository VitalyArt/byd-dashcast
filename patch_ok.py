import os
import re

JAVA_FILE = "app/src/main/java/com/byd/myapp/DiagActivity.java"
XML_FILE = "app/src/main/res/layout/activity_diag.xml"

with open(JAVA_FILE, "r") as f:
    j = f.read()

# Add imports if missing
if "import android.hardware.display.VirtualDisplay;" not in j:
    imp = """import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.graphics.PixelFormat;
import android.content.Context;"""
    j = j.replace("import android.content.Intent;", imp)

# Add vars
vars_str = """
    private Button btnTest13;
    private TextView tvTest13Result;

    // TEST 14
    private Button btnVdTest;
    private TextView tvVdResult;

    // TEST 15
    private Button btnDumpsysWindows;
    private TextView tvDumpsysResult;
"""
j = j.replace("""
    private Button btnTest13;
    private TextView tvTest13Result;
""", vars_str)

# Find views
find_str = """
        // TEST 13
        btnTest13      = (Button)   findViewById(R.id.btn_test_13);
        tvTest13Result = (TextView) findViewById(R.id.tv_test_13_result);

        // TEST 14
        btnVdTest      = (Button)   findViewById(R.id.btn_vd_test);
        tvVdResult     = (TextView) findViewById(R.id.tv_vd_result);

        // TEST 15
        btnDumpsysWindows = (Button) findViewById(R.id.btn_dumpsys_windows);
        tvDumpsysResult   = (TextView) findViewById(R.id.tv_dumpsys_result);
"""
j = j.replace("""
        // TEST 13
        btnTest13      = (Button)   findViewById(R.id.btn_test_13);
        tvTest13Result = (TextView) findViewById(R.id.tv_test_13_result);
""", find_str)

# Add listeners
lis_str = """
        btnTest13.setOnClickListener(v -> runJniSurfaceProbe());
        btnVdTest.setOnClickListener(v -> testVirtualDisplayAPI());
        btnDumpsysWindows.setOnClickListener(v -> runDumpsysWindows());
"""
j = j.replace("        btnTest13.setOnClickListener(v -> runJniSurfaceProbe());", lis_str)

# Add methods at EOF right before last }
methods = """

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
                tvVdResult.setText("SUCCES MYSTERIEUX ! Un VirtualDisplay a pu etre cree par l'app !" + "\\nID: " + vd.getDisplay().getDisplayId() + " Name: " + vd.getDisplay().getName());
                vd.release();
            } else {
                tvVdResult.setText("Echec : createVirtualDisplay a renvoye null.");
            }
        } catch (SecurityException se) {
            tvVdResult.setText("EXCEPTION DE SECURITE (Attendu) :" + "\\n" + se.getMessage());
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
        AdbLocalClient.executeShellCmd(this, "dumpsys window displays", new AdbLocalClient.Callback() {
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
                    tvDumpsysResult.setText("ERREUR:" + "\\n" + error);
                    btnDumpsysWindows.setEnabled(true);
                });
            }
        });
    }
}
"""
j = re.sub(r'\}\s*$', methods, j)

with open(JAVA_FILE, "w") as f:
    f.write(j)

with open(XML_FILE, "r") as f:
    x = f.read()

xml_addition = """
            <Button
                android:id="@+id/btn_test_13"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Execute getQtProjectionDispInfo(0)"
                android:backgroundTint="#FF9800"/>

            <!-- TEST 14 -->
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="TEST 14 — API createVirtualDisplay"
                android:textStyle="bold"
                android:layout_marginTop="16dp"
                android:textColor="#333"/>
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Lancement direct dans l'app pour prouver qu'il faut un daemon Shell (exception de securite attendue)"
                android:textSize="12sp"
                android:layout_marginBottom="8dp"
                android:textColor="#666"/>
            <TextView
                android:id="@+id/tv_vd_result"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="--"
                android:textSize="12sp"
                android:fontFamily="monospace"
                android:background="#f5f5f5"
                android:padding="8dp"
                android:layout_marginBottom="8dp"/>
            <Button
                android:id="@+id/btn_vd_test"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Creer un VirtualDisplay (Java natif)"
                android:backgroundTint="#FF9800"/>

            <!-- TEST 15 -->
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="TEST 15 — Dumpsys window displays"
                android:textStyle="bold"
                android:layout_marginTop="16dp"
                android:textColor="#333"/>
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Capture via ADB de tout l'arbre d'affichage"
                android:textSize="12sp"
                android:layout_marginBottom="8dp"
                android:textColor="#666"/>
            <TextView
                android:id="@+id/tv_dumpsys_result"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="--"
                android:textSize="10sp"
                android:maxLines="40"
                android:scrollbars="vertical"
                android:fontFamily="monospace"
                android:background="#eef"
                android:padding="8dp"
                android:layout_marginBottom="8dp"/>
            <Button
                android:id="@+id/btn_dumpsys_windows"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Lancer dumpsys window displays"
                android:backgroundTint="#03A9F4"/>
"""

x = x.replace("""
            <Button
                android:id="@+id/btn_test_13"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Execute getQtProjectionDispInfo(0)"
                android:backgroundTint="#FF9800"/>
""", xml_addition)

with open(XML_FILE, "w") as f:
    f.write(x)

print("Patching done!")
