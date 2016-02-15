package com.crumby;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class CrDb {
    private static final String DEBUG_NAME = "crumby";
    private static Map<String, Long> timeKeeper;

    public static void m0d(String className, String event) {
        Log.d("crumby " + className, event);
    }

    static {
        timeKeeper = new HashMap();
    }

    public static void logTime(String classCaller, String event, boolean start) {
        long lastStartTime = 0;
        try {
            lastStartTime = ((Long) timeKeeper.get(event)).longValue();
        } catch (NullPointerException e) {
        }
        if (start) {
            timeKeeper.put(event, Long.valueOf(System.nanoTime()));
            return;
        }
        m0d(classCaller, event + " time event:" + ((System.nanoTime() - lastStartTime) / 1000000) + "ms");
        timeKeeper.put(event, Long.valueOf(0));
    }

    public static void newActivity() {
    }
}
