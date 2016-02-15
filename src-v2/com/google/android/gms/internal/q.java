/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.o;
import java.io.IOException;

class q
implements o {
    private ko kk;
    private byte[] kl;
    private final int km;

    public q(int n2) {
        this.km = n2;
        this.reset();
    }

    @Override
    public void b(int n2, long l2) throws IOException {
        this.kk.b(n2, l2);
    }

    @Override
    public void b(int n2, String string2) throws IOException {
        this.kk.b(n2, string2);
    }

    @Override
    public void reset() {
        this.kl = new byte[this.km];
        this.kk = ko.o(this.kl);
    }

    @Override
    public byte[] z() throws IOException {
        int n2 = this.kk.mv();
        if (n2 < 0) {
            throw new IOException();
        }
        if (n2 == 0) {
            return this.kl;
        }
        byte[] arrby = new byte[this.kl.length - n2];
        System.arraycopy(this.kl, 0, arrby, 0, arrby.length);
        return arrby;
    }
}

