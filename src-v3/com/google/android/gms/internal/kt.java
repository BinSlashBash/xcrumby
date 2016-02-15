package com.google.android.gms.internal;

import java.io.IOException;

public abstract class kt {
    protected volatile int adY;

    public kt() {
        this.adY = -1;
    }

    public static final <T extends kt> T m1166a(T t, byte[] bArr) throws ks {
        return m1168b(t, bArr, 0, bArr.length);
    }

    public static final void m1167a(kt ktVar, byte[] bArr, int i, int i2) {
        try {
            ko b = ko.m1136b(bArr, i, i2);
            ktVar.m1170a(b);
            b.mw();
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends kt> T m1168b(T t, byte[] bArr, int i, int i2) throws ks {
        try {
            kn a = kn.m1123a(bArr, i, i2);
            t.m1171b(a);
            a.cP(0);
            return t;
        } catch (ks e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final byte[] m1169d(kt ktVar) {
        byte[] bArr = new byte[ktVar.m1172c()];
        m1167a(ktVar, bArr, 0, bArr.length);
        return bArr;
    }

    public void m1170a(ko koVar) throws IOException {
    }

    public abstract kt m1171b(kn knVar) throws IOException;

    public int m1172c() {
        int mx = mx();
        this.adY = mx;
        return mx;
    }

    public int mF() {
        if (this.adY < 0) {
            m1172c();
        }
        return this.adY;
    }

    protected int mx() {
        return 0;
    }

    public String toString() {
        return ku.m1175e(this);
    }
}
