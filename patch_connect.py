import re

with open("app/src/main/java/com/byd/myapp/AdbLocalClient.java", "r") as f:
    code = f.read()

old_connect = """    private static Dadb connect(Context context) throws Exception {
        File privateKey = new File(context.getFilesDir(), "adb.key");
        File publicKey  = new File(context.getFilesDir(), "adb.pub");
        AdbKeyPair keyPair;
        synchronized (sKeyLock) {
            if (!privateKey.exists() || !publicKey.exists()) {
                AdbKeyPair.generate(privateKey, publicKey);
            }
            keyPair = AdbKeyPair.read(privateKey, publicKey);
        }
        return Dadb.create("localhost", ADB_PORT, keyPair);
    }"""

new_connect = """    private static Dadb connect(Context context) throws Exception {
        File privateKey = new File(context.getFilesDir(), "adb.key");
        File publicKey  = new File(context.getFilesDir(), "adb.pub");
        AdbKeyPair keyPair;
        synchronized (sKeyLock) {
            if (!privateKey.exists() || !publicKey.exists()) {
                AdbKeyPair.generate(privateKey, publicKey);
            }
            keyPair = AdbKeyPair.read(privateKey, publicKey);
        }
        
        // Retry loop to give the user time to click 'Allow USB Debugging' if the popup appears
        int retries = 15;
        Exception lastE = null;
        while (retries > 0) {
            try {
                return Dadb.create("localhost", ADB_PORT, keyPair);
            } catch (Exception e) {
                lastE = e;
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
                AppLogger.log(TAG, "ADB connect exception (popup pending?), retrying in 2s... (" + retries + " left)");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
                retries--;
            }
        }
        throw lastE;
    }"""

if old_connect in code:
    code = code.replace(old_connect, new_connect)
    with open("app/src/main/java/com/byd/myapp/AdbLocalClient.java", "w") as f:
        f.write(code)
    print("Patch applied successfully.")
else:
    print("Could not find the block to replace.")
