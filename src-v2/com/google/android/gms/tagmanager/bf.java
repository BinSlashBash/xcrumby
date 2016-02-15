/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gl;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cf;

class bf
implements cf {
    private final gl Wv;
    private final long Yx;
    private final long vm;
    private final int vn;
    private double vo;
    private long vp;
    private final Object vq = new Object();
    private final String vr;

    public bf(int n2, long l2, long l3, String string2, gl gl2) {
        this.vn = n2;
        this.vo = this.vn;
        this.vm = l2;
        this.Yx = l3;
        this.vr = string2;
        this.Wv = gl2;
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
            long l2 = this.Wv.currentTimeMillis();
            if (l2 - this.vp < this.Yx) {
                bh.z("Excessive " + this.vr + " detected; call ignored.");
                return false;
            }
            if (this.vo < (double)this.vn && (d2 = (double)(l2 - this.vp) / (double)this.vm) > 0.0) {
                this.vo = Math.min((double)this.vn, d2 + this.vo);
            }
            this.vp = l2;
            if (this.vo >= 1.0) {
                this.vo -= 1.0;
                return true;
            }
            bh.z("Excessive " + this.vr + " detected; call ignored.");
            return false;
        }
    }
}

