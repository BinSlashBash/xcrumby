/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;

public class aa {
    private static GoogleAnalytics vs;

    public static boolean cT() {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (aa.getLogger() != null) {
            bl3 = bl2;
            if (aa.getLogger().getLogLevel() == 0) {
                bl3 = true;
            }
        }
        return bl3;
    }

    private static Logger getLogger() {
        if (vs == null) {
            vs = GoogleAnalytics.cM();
        }
        if (vs != null) {
            return vs.getLogger();
        }
        return null;
    }

    public static void w(String string2) {
        Logger logger = aa.getLogger();
        if (logger != null) {
            logger.error(string2);
        }
    }

    public static void x(String string2) {
        Logger logger = aa.getLogger();
        if (logger != null) {
            logger.info(string2);
        }
    }

    public static void y(String string2) {
        Logger logger = aa.getLogger();
        if (logger != null) {
            logger.verbose(string2);
        }
    }

    public static void z(String string2) {
        Logger logger = aa.getLogger();
        if (logger != null) {
            logger.warn(string2);
        }
    }
}

