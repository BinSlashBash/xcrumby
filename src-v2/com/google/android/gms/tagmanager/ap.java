/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.tagmanager;

import android.text.TextUtils;

class ap {
    private final long XX;
    private String XY;
    private final long vi;
    private final long vj;

    ap(long l2, long l3, long l4) {
        this.vi = l2;
        this.vj = l3;
        this.XX = l4;
    }

    void K(String string2) {
        if (string2 == null || TextUtils.isEmpty((CharSequence)string2.trim())) {
            return;
        }
        this.XY = string2;
    }

    long cP() {
        return this.vi;
    }

    long kD() {
        return this.XX;
    }

    String kE() {
        return this.XY;
    }
}

