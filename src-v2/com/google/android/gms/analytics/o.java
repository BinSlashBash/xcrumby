/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.aa;

public final class o {
    private static String b(String string2, int n2) {
        if (n2 < 1) {
            aa.w("index out of range for " + string2 + " (" + n2 + ")");
            return "";
        }
        return string2 + n2;
    }

    static String q(int n2) {
        return o.b("&cd", n2);
    }

    static String r(int n2) {
        return o.b("&cm", n2);
    }
}

