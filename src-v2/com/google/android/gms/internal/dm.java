/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.internal.dj;

public class dm {
    private final Object li = new Object();
    private final String qA;
    private int qV;
    private int qW;
    private final dj qx;

    dm(dj dj2, String string2) {
        this.qx = dj2;
        this.qA = string2;
    }

    public dm(String string2) {
        this(dj.bq(), string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(int n2, int n3) {
        Object object = this.li;
        synchronized (object) {
            this.qV = n2;
            this.qW = n3;
            this.qx.a(this.qA, this);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bundle toBundle() {
        Object object = this.li;
        synchronized (object) {
            Bundle bundle = new Bundle();
            bundle.putInt("pmnli", this.qV);
            bundle.putInt("pmnll", this.qW);
            return bundle;
        }
    }
}

