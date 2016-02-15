/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.kn;
import com.google.android.gms.internal.ko;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.ku;
import java.io.IOException;

public abstract class kt {
    protected volatile int adY = -1;

    public static final <T extends kt> T a(T t2, byte[] arrby) throws ks {
        return kt.b(t2, arrby, 0, arrby.length);
    }

    public static final void a(kt kt2, byte[] object, int n2, int n3) {
        try {
            object = ko.b((byte[])object, n2, n3);
            kt2.a((ko)object);
            object.mw();
            return;
        }
        catch (IOException var0_1) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", var0_1);
        }
    }

    public static final <T extends kt> T b(T t2, byte[] object, int n2, int n3) throws ks {
        try {
            object = kn.a((byte[])object, n2, n3);
            t2.b((kn)object);
            object.cP(0);
        }
        catch (ks var0_1) {
            throw var0_1;
        }
        catch (IOException var0_2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
        return t2;
    }

    public static final byte[] d(kt kt2) {
        byte[] arrby = new byte[kt2.c()];
        kt.a(kt2, arrby, 0, arrby.length);
        return arrby;
    }

    public void a(ko ko2) throws IOException {
    }

    public abstract kt b(kn var1) throws IOException;

    public int c() {
        int n2;
        this.adY = n2 = this.mx();
        return n2;
    }

    public int mF() {
        if (this.adY < 0) {
            this.c();
        }
        return this.adY;
    }

    protected int mx() {
        return 0;
    }

    public String toString() {
        return ku.e(this);
    }
}

