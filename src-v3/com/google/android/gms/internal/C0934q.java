package com.google.android.gms.internal;

import java.io.IOException;

/* renamed from: com.google.android.gms.internal.q */
class C0934q implements C0413o {
    private ko kk;
    private byte[] kl;
    private final int km;

    public C0934q(int i) {
        this.km = i;
        reset();
    }

    public void m2326b(int i, long j) throws IOException {
        this.kk.m1151b(i, j);
    }

    public void m2327b(int i, String str) throws IOException {
        this.kk.m1152b(i, str);
    }

    public void reset() {
        this.kl = new byte[this.km];
        this.kk = ko.m1144o(this.kl);
    }

    public byte[] m2328z() throws IOException {
        int mv = this.kk.mv();
        if (mv < 0) {
            throw new IOException();
        } else if (mv == 0) {
            return this.kl;
        } else {
            Object obj = new byte[(this.kl.length - mv)];
            System.arraycopy(this.kl, 0, obj, 0, obj.length);
            return obj;
        }
    }
}
