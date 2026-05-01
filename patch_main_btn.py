import re

with open('app/src/main/java/com/byd/myapp/MainActivity.java', 'r') as f:
    c = f.read()

# Add btnActivateCluster.setEnabled(true) to onClusterDisplayConnected
pattern1 = r'updateDashboardStatus\(null\);'
repl1 = r'updateDashboardStatus(null);\n                btnActivateCluster.setEnabled(true);'
c = re.sub(pattern1, repl1, c)

# Add btnActivateCluster.setEnabled(true) to onClusterDisplayDisconnected
pattern2 = r'mCurrentDashboardApp = null;\n\s*mCurrentDashboardPkg = null;'
repl2 = r'mCurrentDashboardApp = null;\n                mCurrentDashboardPkg = null;\n                btnActivateCluster.setEnabled(true);'
c = re.sub(pattern2, repl2, c)

with open('app/src/main/java/com/byd/myapp/MainActivity.java', 'w') as f:
    f.write(c)

