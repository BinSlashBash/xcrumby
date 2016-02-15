package com.google.android.gms.internal;

import android.util.Log;
import com.google.ads.AdRequest;

public final class dw {
    public static void m815a(String str, Throwable th) {
        if (m818n(3)) {
            Log.d(AdRequest.LOGTAG, str, th);
        }
    }

    public static void m816b(String str, Throwable th) {
        if (m818n(6)) {
            Log.e(AdRequest.LOGTAG, str, th);
        }
    }

    public static void m817c(String str, Throwable th) {
        if (m818n(5)) {
            Log.w(AdRequest.LOGTAG, str, th);
        }
    }

    public static boolean m818n(int i) {
        return (i >= 5 || Log.isLoggable(AdRequest.LOGTAG, i)) && i != 2;
    }

    public static void m819v(String str) {
        if (m818n(3)) {
            Log.d(AdRequest.LOGTAG, str);
        }
    }

    public static void m820w(String str) {
        if (m818n(6)) {
            Log.e(AdRequest.LOGTAG, str);
        }
    }

    public static void m821x(String str) {
        if (m818n(4)) {
            Log.i(AdRequest.LOGTAG, str);
        }
    }

    public static void m822y(String str) {
        if (m818n(2)) {
            Log.v(AdRequest.LOGTAG, str);
        }
    }

    public static void m823z(String str) {
        if (m818n(5)) {
            Log.w(AdRequest.LOGTAG, str);
        }
    }
}
