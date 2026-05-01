import re

with open('app/src/main/java/com/byd/myapp/MainActivity.java', 'r') as f:
    c = f.read()

# remove onFreedomStatus method
method_pattern = r'@Override\s*public void onFreedomStatus\(final AdbLocalClient\.FreedomStatus status\)\s*\{[\s\S]*?\}\s*\}\s*\);\s*\}'
c = re.sub(method_pattern, '', c)

with open('app/src/main/java/com/byd/myapp/MainActivity.java', 'w') as f:
    f.write(c)
