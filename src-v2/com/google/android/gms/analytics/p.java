/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.analytics;

import android.os.Build;
import com.google.android.gms.analytics.aa;
import java.io.File;

class p {
    static boolean G(String object) {
        if (p.version() < 9) {
            return false;
        }
        object = new File((String)object);
        object.setReadable(false, false);
        object.setWritable(false, false);
        object.setReadable(true, true);
        object.setWritable(true, true);
        return true;
    }

    public static int version() {
        try {
            int n2 = Integer.parseInt(Build.VERSION.SDK);
            return n2;
        }
        catch (NumberFormatException var1_1) {
            aa.w("Invalid version number: " + Build.VERSION.SDK);
            return 0;
        }
    }
}

