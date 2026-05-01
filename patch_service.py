import re

with open('app/src/main/java/com/byd/myapp/ClusterService.java', 'r') as f:
    c = f.read()

# Remove onFreedomStatus from interface
c = re.sub(r'\s*/\*\* Freedom state checked at service startup \(called on the main thread\)\. \*/\s*void onFreedomStatus\(AdbLocalClient\.FreedomStatus status\);', '', c)

# Rename checkAndStartWithFreedom calling inside onCreate
c = c.replace('checkAndStartWithFreedom();', 'startNativeProjection();')
c = c.replace('ClusterService created — checking Freedom state', 'ClusterService created — starting native projection')

# Replace the whole checkAndStartWithFreedom method
method_pattern = r'private void checkAndStartWithFreedom\(\)\s*\{[\s\S]*?\}\s*\}\s*\);\s*\}'
c = re.sub(method_pattern, '''private void startNativeProjection() {
        AppLogger.i(TAG, "Starting cluster projection (native)...");
        mDisplayHelper.start(false);
    }''', c)

with open('app/src/main/java/com/byd/myapp/ClusterService.java', 'w') as f:
    f.write(c)
