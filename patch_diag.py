import re

path = "app/src/main/java/com/byd/myapp/DiagActivity.java"
with open(path, "r") as f:
    text = f.read()

# 1. headerCmd -> touch the lock file
text = text.replace('String headerCmd =\n            "logcat -c 2>/dev/null"',
'''String headerCmd =
            "logcat -c 2>/dev/null && touch /data/local/tmp/.sniffer_run"''')

# 2. snapshotCmd -> while [ -f ... ]
text = text.replace('"while true; do sleep 15;"', '"while [ -f /data/local/tmp/.sniffer_run ]; do sleep 15;"')

# 3. inputCmd -> while [ -f ... ]
text = text.replace('"while true; do sleep 5; dumpsys input 2>/dev/null', '"while [ -f /data/local/tmp/.sniffer_run ]; do sleep 5; dumpsys input 2>/dev/null')

with open(path, "w") as f:
    f.write(text)

print("DiagActivity lock file logic updated.")
