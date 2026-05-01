import re
path = "app/src/main/java/com/byd/myapp/AdbLocalClient.java"
with open(path, "r") as f:
    text = f.read()

old_cmd = '''public static final String SNIFFER_KILL_CMD =
            "rm -f /data/local/tmp/.sniffer_run; "
            + "ps -A | awk '/[s]leep 15/ {print $2}; /[s]leep 5/ {print $2}; /[l]ogcat -v threadtime/ {print $2}; /[l]ogcat -b events/ {print $2}' | xargs -n1 kill -9 2>/dev/null; "
            + "echo killed";'''
new_cmd = '''public static final String SNIFFER_KILL_CMD =
            "rm -f /data/local/tmp/.sniffer_run; "
            + "for p in $(ps -A | awk '/[s]leep 15/ {print $2}; /[s]leep 5/ {print $2}; /[l]ogcat -v threadtime/ {print $2}; /[l]ogcat -b events/ {print $2}'); do kill -9 $p 2>/dev/null; done; "
            + "echo killed";'''
text = text.replace(old_cmd, new_cmd)
with open(path, "w") as f:
    f.write(text)
print("Patched AdbLocalClient.java (kill cmd)")
