package com.byd.myapp.dashboard;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.graphics.PixelFormat;
import android.os.Looper;
import java.lang.reflect.Method;

/**
 * Daemon executé en ligne de commande via app_process (Shell)
 * Hérite de la permission CAPTURE_VIDEO_OUTPUT
 */
public class DashCastDaemon {

    public static void main(String[] args) {
        System.out.println("[DashCastDaemon] Daemon started with shell privileges!");
        
        try {
            Looper.prepareMainLooper();
            System.out.println("[DashCastDaemon] Main Looper prepared.");
            
            // Initialisation de l'environnement Android via Reflection
            Context systemContext = getSystemContext();
            System.out.println("[DashCastDaemon] Got SystemContext: " + systemContext);
            
            if (systemContext == null) {
                System.err.println("[DashCastDaemon] Failed to get SystemContext. Exiting.");
                return;
            }

            DisplayManager displayManager = (DisplayManager) systemContext.getSystemService(Context.DISPLAY_SERVICE);
            System.out.println("[DashCastDaemon] DisplayManager acquired: " + displayManager);

            // Simulation d'une Surface (Normalement fournie par l'IPC Binder depuis l'app)
            ImageReader reader = ImageReader.newInstance(1920, 720, PixelFormat.RGBA_8888, 2);
            System.out.println("[DashCastDaemon] Fake Surface created.");

            // Création du VirtualDisplay de la même manière que Dilink5
            // params: name, width, height, densityDpi, surface, flags
            VirtualDisplay createVirtualDisplay = displayManager.createVirtualDisplay(
                "remote_dashboard", 1920, 720, 320, reader.getSurface(), 320);

            if (createVirtualDisplay != null) {
                System.out.println("[DashCastDaemon] SUCCESS! VirtualDisplay instancié ! ID: " 
                    + createVirtualDisplay.getDisplay().getDisplayId());
                createVirtualDisplay.release();
            } else {
                System.err.println("[DashCastDaemon] WARNING: createVirtualDisplay returned null.");
            }
            
            System.out.println("[DashCastDaemon] Test completed safely. Exiting daemon.");
            // On ne fait pas de Looper.loop() pour l'instant car c'est un test "one-shot"
            System.exit(0);

        } catch (Exception e) {
            System.err.println("[DashCastDaemon] FATAL ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Context getSystemContext() throws Exception {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        
        // 1. Initialiser le thread principal système
        Method systemMainMethod = activityThreadClass.getDeclaredMethod("systemMain");
        systemMainMethod.setAccessible(true);
        Object activityThread = systemMainMethod.invoke(null);
        
        // 2. Récupérer le contexte système
        Method getSystemContextMethod = activityThreadClass.getDeclaredMethod("getSystemContext");
        getSystemContextMethod.setAccessible(true);
        return (Context) getSystemContextMethod.invoke(activityThread);
    }
}
