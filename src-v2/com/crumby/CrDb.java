/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.crumby;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class CrDb {
    private static final String DEBUG_NAME = "crumby";
    private static Map<String, Long> timeKeeper = new HashMap<String, Long>();

    public static void d(String string2, String string3) {
        Log.d((String)("crumby " + string2), (String)string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void logTime(String string2, String string3, boolean bl2) {
        long l2;
        l2 = 0;
        try {
            long l3;
            l2 = l3 = timeKeeper.get(string3).longValue();
        }
        catch (NullPointerException var7_5) {}
        if (!bl2) {
            CrDb.d(string2, string3 + " time event:" + (System.nanoTime() - l2) / 1000000 + "ms");
            timeKeeper.put(string3, 0);
            return;
        }
        timeKeeper.put(string3, System.nanoTime());
    }

    public static void newActivity() {
    }
}

