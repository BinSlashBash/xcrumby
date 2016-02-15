/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cf;

class cv
implements cf {
    private long aab;
    private final long vm;
    private final int vn;
    private double vo;
    private final Object vq = new Object();

    public cv() {
        this(60, 2000);
    }

    public cv(int n2, long l2) {
        this.vn = n2;
        this.vo = this.vn;
        this.vm = l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean cS() {
        Object object = this.vq;
        synchronized (object) {
            double d2;
            long l2 = System.currentTimeMillis();
            if (this.vo < (double)this.vn && (d2 = (double)(l2 - this.aab) / (double)this.vm) > 0.0) {
                this.vo = Math.min((double)this.vn, d2 + this.vo);
            }
            this.aab = l2;
            if (this.vo >= 1.0) {
                this.vo -= 1.0;
                return true;
            }
            bh.z("No more tokens available.");
            return false;
        }
    }
}

