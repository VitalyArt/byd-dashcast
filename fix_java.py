JAVA_FILE = "app/src/main/java/com/byd/myapp/DiagActivity.java"
with open(JAVA_FILE, "r") as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    if 'tvVdResult.setText("SUCCES MYSTERIEUX' in line:
        line = line.replace('\n', '') + '\\n"\n'
    elif 'ID: " +' in line:
        line = '                + "ID: " + vd.getDisplay().getDisplayId() + " Name: " + vd.getDisplay().getName());\n'
    elif 'tvVdResult.setText("EXCEPTION DE SECURITE' in line:
        line = line.replace('\n', '') + '\\n"\n'
    elif '" + se.getMessage());' in line:
        line = '                + se.getMessage());\n'
    elif 'tvDumpsysResult.setText("ERREUR:' in line:
        line = line.replace('\n', '') + '\\n"\n'
    elif '" + error);' in line:
        line = '                    + error);\n'
    new_lines.append(line)

with open(JAVA_FILE, "w") as f:
    f.writelines(new_lines)
