package com.google.android.gms.analytics;

import com.crumby.impl.crumby.UnsupportedUrlFragment;

/* renamed from: com.google.android.gms.analytics.o */
public final class C0216o {
    private static String m62b(String str, int i) {
        if (i >= 1) {
            return str + i;
        }
        aa.m32w("index out of range for " + str + " (" + i + ")");
        return UnsupportedUrlFragment.DISPLAY_NAME;
    }

    static String m63q(int i) {
        return C0216o.m62b("&cd", i);
    }

    static String m64r(int i) {
        return C0216o.m62b("&cm", i);
    }
}
