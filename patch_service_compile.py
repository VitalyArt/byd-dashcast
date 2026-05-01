import re

with open('app/src/main/java/com/byd/myapp/ClusterService.java', 'r') as f:
    c = f.read()

# remove mFreedomStatus definition
c = re.sub(r'private AdbLocalClient\.FreedomStatus mFreedomStatus = null;\n', '', c)

# remove replay block
c = re.sub(r'// Replay cached Freedom state if available \(check launched before bind\)\n\s*if \(mFreedomStatus != null && mListener != null\) \{\n\s*mListener\.onFreedomStatus\(mFreedomStatus\);\n\s*\}\n', '', c)

with open('app/src/main/java/com/byd/myapp/ClusterService.java', 'w') as f:
    f.write(c)
