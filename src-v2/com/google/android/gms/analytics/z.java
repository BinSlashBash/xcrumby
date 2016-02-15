/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.ad;

class z
implements ad {
    private final long vm;
    private final int vn;
    private double vo;
    private long vp;
    private final Object vq = new Object();
    private final String vr;

    public z(int n2, long l2, String string2) {
        this.vn = n2;
        this.vo = this.vn;
        this.vm = l2;
        this.vr = string2;
    }

    public z(String string2) {
        this(60, 2000, string2);
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
            if (this.vo < (double)this.vn && (d2 = (double)(l2 - this.vp) / (double)this.vm) > 0.0) {
                this.vo = Math.min((double)this.vn, d2 + this.vo);
            }
            this.vp = l2;
            if (this.vo >= 1.0) {
                this.vo -= 1.0;
                return true;
            }
            aa.z("Excessive " + this.vr + " detected; call ignored.");
            return false;
        }
    }
}

