package com.google.android.gms.internal;

import android.util.Log;

public final class fj {
    private final String DH;

    public fj(String str) {
        this.DH = (String) fq.m985f(str);
    }

    public boolean m940P(int i) {
        return Log.isLoggable(this.DH, i);
    }

    public void m941a(String str, String str2, Throwable th) {
        if (m940P(6)) {
            Log.e(str, str2, th);
        }
    }

    public void m942f(String str, String str2) {
        if (m940P(2)) {
            Log.v(str, str2);
        }
    }

    public void m943g(String str, String str2) {
        if (m940P(5)) {
            Log.w(str, str2);
        }
    }

    public void m944h(String str, String str2) {
        if (m940P(6)) {
            Log.e(str, str2);
        }
    }
}
