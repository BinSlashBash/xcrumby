package com.google.android.gms.tagmanager;

import android.util.Log;

/* renamed from: com.google.android.gms.tagmanager.x */
class C1092x implements bi {
    private int sz;

    C1092x() {
        this.sz = 5;
    }

    public void m2561b(String str, Throwable th) {
        if (this.sz <= 6) {
            Log.e("GoogleTagManager", str, th);
        }
    }

    public void m2562c(String str, Throwable th) {
        if (this.sz <= 5) {
            Log.w("GoogleTagManager", str, th);
        }
    }

    public void setLogLevel(int logLevel) {
        this.sz = logLevel;
    }

    public void m2563v(String str) {
        if (this.sz <= 3) {
            Log.d("GoogleTagManager", str);
        }
    }

    public void m2564w(String str) {
        if (this.sz <= 6) {
            Log.e("GoogleTagManager", str);
        }
    }

    public void m2565x(String str) {
        if (this.sz <= 4) {
            Log.i("GoogleTagManager", str);
        }
    }

    public void m2566y(String str) {
        if (this.sz <= 2) {
            Log.v("GoogleTagManager", str);
        }
    }

    public void m2567z(String str) {
        if (this.sz <= 5) {
            Log.w("GoogleTagManager", str);
        }
    }
}
