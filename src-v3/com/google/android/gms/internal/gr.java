package com.google.android.gms.internal;

import android.os.Build.VERSION;

public final class gr {
    private static boolean ab(int i) {
        return VERSION.SDK_INT >= i;
    }

    public static boolean fu() {
        return ab(11);
    }

    public static boolean fv() {
        return ab(12);
    }

    public static boolean fw() {
        return ab(13);
    }

    public static boolean fx() {
        return ab(14);
    }

    public static boolean fy() {
        return ab(16);
    }

    public static boolean fz() {
        return ab(17);
    }
}
