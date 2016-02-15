/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.analytics;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

class l
implements Logger {
    private int sz = 1;

    l() {
    }

    private String E(String string2) {
        return Thread.currentThread().toString() + ": " + string2;
    }

    @Override
    public void error(Exception exception) {
        if (this.sz <= 3) {
            Log.e((String)"GAV4", (String)null, (Throwable)exception);
        }
    }

    @Override
    public void error(String string2) {
        if (this.sz <= 3) {
            Log.e((String)"GAV4", (String)this.E(string2));
        }
    }

    @Override
    public int getLogLevel() {
        return this.sz;
    }

    @Override
    public void info(String string2) {
        if (this.sz <= 1) {
            Log.i((String)"GAV4", (String)this.E(string2));
        }
    }

    @Override
    public void setLogLevel(int n2) {
        this.sz = n2;
    }

    @Override
    public void verbose(String string2) {
        if (this.sz <= 0) {
            Log.v((String)"GAV4", (String)this.E(string2));
        }
    }

    @Override
    public void warn(String string2) {
        if (this.sz <= 2) {
            Log.w((String)"GAV4", (String)this.E(string2));
        }
    }
}

