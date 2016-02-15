/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.os.Build;

public final class gr {
    private static boolean ab(int n2) {
        if (Build.VERSION.SDK_INT >= n2) {
            return true;
        }
        return false;
    }

    public static boolean fu() {
        return gr.ab(11);
    }

    public static boolean fv() {
        return gr.ab(12);
    }

    public static boolean fw() {
        return gr.ab(13);
    }

    public static boolean fx() {
        return gr.ab(14);
    }

    public static boolean fy() {
        return gr.ab(16);
    }

    public static boolean fz() {
        return gr.ab(17);
    }
}

