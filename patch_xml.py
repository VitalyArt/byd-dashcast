XML_FILE = "app/src/main/res/layout/activity_diag.xml"
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
                android:text="TEST 14 - API createVirtualDisplay"
                android:textStyle="bold"
                android:layout_marginTop="16dp"
                android:textColor="#333"/>
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Lancement direct dans l'app (Exception de securite attendue)"
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
                android:text="Creer un VirtualDisplay"
                android:backgroundTint="#FF9800"/>

            <!-- TEST 15 -->
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="TEST 15 - Dumpsys window displays"
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

print("Layer patched!")
