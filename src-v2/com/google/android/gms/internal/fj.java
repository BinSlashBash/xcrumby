/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.internal.fq;

public final class fj {
    private final String DH;

    public fj(String string2) {
        this.DH = fq.f(string2);
    }

    public boolean P(int n2) {
        return Log.isLoggable((String)this.DH, (int)n2);
    }

    public void a(String string2, String string3, Throwable throwable) {
        if (this.P(6)) {
            Log.e((String)string2, (String)string3, (Throwable)throwable);
        }
    }

    public void f(String string2, String string3) {
        if (this.P(2)) {
            Log.v((String)string2, (String)string3);
        }
    }

    public void g(String string2, String string3) {
        if (this.P(5)) {
            Log.w((String)string2, (String)string3);
        }
    }

    public void h(String string2, String string3) {
        if (this.P(6)) {
            Log.e((String)string2, (String)string3);
        }
    }
}

