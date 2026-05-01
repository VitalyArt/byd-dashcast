import re

with open('app/src/main/java/com/byd/myapp/ClusterService.java', 'r') as f:
    c = f.read()

# remove from "private void startNativeProjection() {" to the remaining junk up to setListener
start_idx = c.find('    private void startNativeProjection() {')
end_idx = c.find('    public void setListener(Listener listener) {')

c = c[:start_idx] + '''    private void startNativeProjection() {
        AppLogger.i(TAG, "Starting cluster projection (native)...");
        mDisplayHelper.start(false);
    }

''' + c[end_idx:]

with open('app/src/main/java/com/byd/myapp/ClusterService.java', 'w') as f:
    f.write(c)

