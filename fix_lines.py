with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "r") as f:
    t = f.read()
import re
t = re.sub(r'DAEMON OUTPUT:"\s*\+\s*"\n', r'DAEMON OUTPUT:" + "\\n', t)
t = re.sub(r'ERREUR:"\s*\+\s*"\n', r'ERREUR:" + "\\n', t)
with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "w") as f:
    f.write(t)
