with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "r") as f:
    c = f.read()
c = c.replace('+"\\nID: "', '+"\\\\nID: "')
c = c.replace('+"\\n" + se', '+"\\\\n" + se')
c = c.replace('+"\\n" + error', '+"\\\\n" + error')
with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "w") as f:
    f.write(c)
