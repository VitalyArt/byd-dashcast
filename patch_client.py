import re

path = "app/src/main/java/com/byd/myapp/AdbLocalClient.java"
with open(path, "r") as f:
    text = f.read()

# Replace SNIFFER_KILL_CMD
old_cmd = '''public static final String SNIFFER_KILL_CMD =
            "ps -A | grep '[l]ogcat -v threadtime' | awk '{print $2}' | xargs -r kill -9 2>/dev/null; "
            + "ps -A | grep '[s]leep 30' | awk '{print $2}' | xargs -r kill -9 2>/dev/null; "
            + "echo killed";'''
new_cmd = '''public static final String SNIFFER_KILL_CMD =
            "rm -f /data/local/tmp/.sniffer_run; "
            + "ps -A | awk '/[s]leep 15/ {print $2}; /[s]leep 5/ {print $2}; /[l]ogcat -v threadtime/ {print $2}; /[l]ogcat -b events/ {print $2}' | xargs -n1 kill -9 2>/dev/null; "
            + "echo killed";'''
text = text.replace(old_cmd, new_cmd)

# Replace scanSniffer
old_scan = '''String logcatPs = safeOut(dadb.shell(
                        "ps -A | grep '[l]ogcat -v threadtime' 2>&1").getAllOutput()).trim();
                String sleepPs = safeOut(dadb.shell(
                        "ps -A | grep '[s]leep 30' 2>&1").getAllOutput()).trim();'''
new_scan = '''String logcatPs = safeOut(dadb.shell(
                        "ps -A | grep -E '[l]ogcat -v threadtime|[l]ogcat -b events' 2>&1").getAllOutput()).trim();
                String sleepPs = safeOut(dadb.shell(
                        "ps -A | grep -E '[s]leep 15|[s]leep 5' 2>&1").getAllOutput()).trim();'''
text = text.replace(old_scan, new_scan)

# Replace killSniffer verification
old_kill_check = '''String logcatAfter = safeOut(dadb.shell(
                        "ps -A | grep '[l]ogcat -v threadtime' 2>&1").getAllOutput()).trim();
                String sleepAfter = safeOut(dadb.shell(
                        "ps -A | grep '[s]leep 30' 2>&1").getAllOutput()).trim();'''
new_kill_check = '''String logcatAfter = safeOut(dadb.shell(
                        "ps -A | grep -E '[l]ogcat -v threadtime|[l]ogcat -b events' 2>&1").getAllOutput()).trim();
                String sleepAfter = safeOut(dadb.shell(
                        "ps -A | grep -E '[s]leep 15|[s]leep 5' 2>&1").getAllOutput()).trim();'''
text = text.replace(old_kill_check, new_kill_check)

with open(path, "w") as f:
    f.write(text)
print("Patched AdbLocalClient.java")
