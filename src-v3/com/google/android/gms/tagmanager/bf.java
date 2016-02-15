package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gl;

class bf implements cf {
    private final gl Wv;
    private final long Yx;
    private final long vm;
    private final int vn;
    private double vo;
    private long vp;
    private final Object vq;
    private final String vr;

    public bf(int i, long j, long j2, String str, gl glVar) {
        this.vq = new Object();
        this.vn = i;
        this.vo = (double) this.vn;
        this.vm = j;
        this.Yx = j2;
        this.vr = str;
        this.Wv = glVar;
    }

    public boolean cS() {
        boolean z = false;
        synchronized (this.vq) {
            long currentTimeMillis = this.Wv.currentTimeMillis();
            if (currentTimeMillis - this.vp < this.Yx) {
                bh.m1387z("Excessive " + this.vr + " detected; call ignored.");
            } else {
                if (this.vo < ((double) this.vn)) {
                    double d = ((double) (currentTimeMillis - this.vp)) / ((double) this.vm);
                    if (d > 0.0d) {
                        this.vo = Math.min((double) this.vn, d + this.vo);
                    }
                }
                this.vp = currentTimeMillis;
                if (this.vo >= 1.0d) {
                    this.vo -= 1.0d;
                    z = true;
                } else {
                    bh.m1387z("Excessive " + this.vr + " detected; call ignored.");
                }
            }
        }
        return z;
    }
}
