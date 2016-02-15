/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;

public final class dw {
    public static void a(String string2, Throwable throwable) {
        if (dw.n(3)) {
            Log.d((String)"Ads", (String)string2, (Throwable)throwable);
        }
    }

    public static void b(String string2, Throwable throwable) {
        if (dw.n(6)) {
            Log.e((String)"Ads", (String)string2, (Throwable)throwable);
        }
    }

    public static void c(String string2, Throwable throwable) {
        if (dw.n(5)) {
            Log.w((String)"Ads", (String)string2, (Throwable)throwable);
        }
    }

    public static boolean n(int n2) {
        if (n2 < 5 && !Log.isLoggable((String)"Ads", (int)n2) || n2 == 2) {
            return false;
        }
        return true;
    }

    public static void v(String string2) {
        if (dw.n(3)) {
            Log.d((String)"Ads", (String)string2);
        }
    }

    public static void w(String string2) {
        if (dw.n(6)) {
            Log.e((String)"Ads", (String)string2);
        }
    }

    public static void x(String string2) {
        if (dw.n(4)) {
            Log.i((String)"Ads", (String)string2);
        }
    }

    public static void y(String string2) {
        if (dw.n(2)) {
            Log.v((String)"Ads", (String)string2);
        }
    }

    public static void z(String string2) {
        if (dw.n(5)) {
            Log.w((String)"Ads", (String)string2);
        }
    }
}

