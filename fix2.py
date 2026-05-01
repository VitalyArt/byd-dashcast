with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "r") as f:
    text = f.read()

import re

# Fix literal newline after SUCCES
text = re.sub(r'l\'app !"\s*\+\s*"\n(.*?)', r'l\'app !" + "\\n\1', text)
text = re.sub(r'\(Attendu\) :"\s*\+\s*"\n(.*?)', r'(Attendu) :" + "\\n\1', text)
text = re.sub(r'ERREUR:"\s*\+\s*"\n(.*?)', r'ERREUR:" + "\\n\1', text)

with open("app/src/main/java/com/byd/myapp/DiagActivity.java", "w") as f:
    f.write(text)
