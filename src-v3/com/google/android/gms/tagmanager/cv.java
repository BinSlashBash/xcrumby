package com.google.android.gms.tagmanager;

class cv implements cf {
    private long aab;
    private final long vm;
    private final int vn;
    private double vo;
    private final Object vq;

    public cv() {
        this(60, 2000);
    }

    public cv(int i, long j) {
        this.vq = new Object();
        this.vn = i;
        this.vo = (double) this.vn;
        this.vm = j;
    }

    public boolean cS() {
        boolean z;
        synchronized (this.vq) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.vo < ((double) this.vn)) {
                double d = ((double) (currentTimeMillis - this.aab)) / ((double) this.vm);
                if (d > 0.0d) {
                    this.vo = Math.min((double) this.vn, d + this.vo);
                }
            }
            this.aab = currentTimeMillis;
            if (this.vo >= 1.0d) {
                this.vo -= 1.0d;
                z = true;
            } else {
                bh.m1387z("No more tokens available.");
                z = false;
            }
        }
        return z;
    }
}
