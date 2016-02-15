/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.analytics;

import android.app.Activity;
import com.google.android.gms.analytics.j;
import java.util.HashMap;
import java.util.Map;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
class aj
implements j {
    String wh;
    double wi = -1.0;
    int wj = -1;
    int wk = -1;
    int wl = -1;
    int wm = -1;
    Map<String, String> wn = new HashMap<String, String>();

    aj() {
    }

    public String M(String string2) {
        String string3 = this.wn.get(string2);
        if (string3 != null) {
            return string3;
        }
        return string2;
    }

    public boolean dj() {
        if (this.wh != null) {
            return true;
        }
        return false;
    }

    public String dk() {
        return this.wh;
    }

    public boolean dl() {
        if (this.wi >= 0.0) {
            return true;
        }
        return false;
    }

    public double dm() {
        return this.wi;
    }

    public boolean dn() {
        if (this.wj >= 0) {
            return true;
        }
        return false;
    }

    public boolean do() {
        if (this.wk != -1) {
            return true;
        }
        return false;
    }

    public boolean dp() {
        if (this.wk == 1) {
            return true;
        }
        return false;
    }

    public boolean dq() {
        if (this.wl != -1) {
            return true;
        }
        return false;
    }

    public boolean dr() {
        if (this.wl == 1) {
            return true;
        }
        return false;
    }

    public boolean ds() {
        if (this.wm == 1) {
            return true;
        }
        return false;
    }

    public int getSessionTimeout() {
        return this.wj;
    }

    public String h(Activity activity) {
        return this.M(activity.getClass().getCanonicalName());
    }
}

