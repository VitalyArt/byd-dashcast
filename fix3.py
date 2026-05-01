with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "r") as f:
    text = f.read()

text = text.replace('tvDaemonVdResult.setText("DAEMON OUTPUT:\n" + report)', 'tvDaemonVdResult.setText("DAEMON OUTPUT:\\n" + report)')
text = text.replace('tvDaemonVdResult.setText("ERREUR:\n" + error)', 'tvDaemonVdResult.setText("ERREUR:\\n" + error)')
with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "w") as f:
    f.write(text)
