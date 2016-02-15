package com.google.android.gms.analytics;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

class aj implements C0211j {
    String wh;
    double wi;
    int wj;
    int wk;
    int wl;
    int wm;
    Map<String, String> wn;

    aj() {
        this.wi = -1.0d;
        this.wj = -1;
        this.wk = -1;
        this.wl = -1;
        this.wm = -1;
        this.wn = new HashMap();
    }

    public String m1576M(String str) {
        String str2 = (String) this.wn.get(str);
        return str2 != null ? str2 : str;
    }

    public boolean dj() {
        return this.wh != null;
    }

    public String dk() {
        return this.wh;
    }

    public boolean dl() {
        return this.wi >= 0.0d;
    }

    public double dm() {
        return this.wi;
    }

    public boolean dn() {
        return this.wj >= 0;
    }

    public boolean m1577do() {
        return this.wk != -1;
    }

    public boolean dp() {
        return this.wk == 1;
    }

    public boolean dq() {
        return this.wl != -1;
    }

    public boolean dr() {
        return this.wl == 1;
    }

    public boolean ds() {
        return this.wm == 1;
    }

    public int getSessionTimeout() {
        return this.wj;
    }

    public String m1578h(Activity activity) {
        return m1576M(activity.getClass().getCanonicalName());
    }
}
