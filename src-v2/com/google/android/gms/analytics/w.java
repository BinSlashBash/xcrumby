/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.j;

class w
implements j {
    String so;
    String sp;
    String uV;
    int uW = -1;
    int uX = -1;

    w() {
    }

    public boolean cC() {
        if (this.so != null) {
            return true;
        }
        return false;
    }

    public String cD() {
        return this.so;
    }

    public boolean cE() {
        if (this.sp != null) {
            return true;
        }
        return false;
    }

    public String cF() {
        return this.sp;
    }

    public boolean cG() {
        if (this.uV != null) {
            return true;
        }
        return false;
    }

    public String cH() {
        return this.uV;
    }

    public boolean cI() {
        if (this.uW >= 0) {
            return true;
        }
        return false;
    }

    public int cJ() {
        return this.uW;
    }

    public boolean cK() {
        if (this.uX != -1) {
            return true;
        }
        return false;
    }

    public boolean cL() {
        if (this.uX == 1) {
            return true;
        }
        return false;
    }
}

