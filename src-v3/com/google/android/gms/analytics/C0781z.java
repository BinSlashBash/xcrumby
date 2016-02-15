package com.google.android.gms.analytics;

/* renamed from: com.google.android.gms.analytics.z */
class C0781z implements ad {
    private final long vm;
    private final int vn;
    private double vo;
    private long vp;
    private final Object vq;
    private final String vr;

    public C0781z(int i, long j, String str) {
        this.vq = new Object();
        this.vn = i;
        this.vo = (double) this.vn;
        this.vm = j;
        this.vr = str;
    }

    public C0781z(String str) {
        this(60, 2000, str);
    }

    public boolean cS() {
        boolean z;
        synchronized (this.vq) {
            long currentTimeMillis = System.currentTimeMillis();
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
                aa.m35z("Excessive " + this.vr + " detected; call ignored.");
                z = false;
            }
        }
        return z;
    }
}
