/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;

class x {
    private String vh;
    private final long vi;
    private final long vj;
    private String vk = "https:";

    x(String string2, long l2, long l3) {
        this.vh = string2;
        this.vi = l2;
        this.vj = l3;
    }

    void J(String string2) {
        this.vh = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void K(String string2) {
        if (string2 == null || TextUtils.isEmpty((CharSequence)string2.trim()) || !string2.toLowerCase().startsWith("http:")) {
            return;
        }
        this.vk = "http:";
    }

    String cO() {
        return this.vh;
    }

    long cP() {
        return this.vi;
    }

    long cQ() {
        return this.vj;
    }

    String cR() {
        return this.vk;
    }
}

