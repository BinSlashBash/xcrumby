/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.tagmanager;

import android.util.Log;
import com.google.android.gms.tagmanager.bi;

class x
implements bi {
    private int sz = 5;

    x() {
    }

    @Override
    public void b(String string2, Throwable throwable) {
        if (this.sz <= 6) {
            Log.e((String)"GoogleTagManager", (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public void c(String string2, Throwable throwable) {
        if (this.sz <= 5) {
            Log.w((String)"GoogleTagManager", (String)string2, (Throwable)throwable);
        }
    }

    @Override
    public void setLogLevel(int n2) {
        this.sz = n2;
    }

    @Override
    public void v(String string2) {
        if (this.sz <= 3) {
            Log.d((String)"GoogleTagManager", (String)string2);
        }
    }

    @Override
    public void w(String string2) {
        if (this.sz <= 6) {
            Log.e((String)"GoogleTagManager", (String)string2);
        }
    }

    @Override
    public void x(String string2) {
        if (this.sz <= 4) {
            Log.i((String)"GoogleTagManager", (String)string2);
        }
    }

    @Override
    public void y(String string2) {
        if (this.sz <= 2) {
            Log.v((String)"GoogleTagManager", (String)string2);
        }
    }

    @Override
    public void z(String string2) {
        if (this.sz <= 5) {
            Log.w((String)"GoogleTagManager", (String)string2);
        }
    }
}

