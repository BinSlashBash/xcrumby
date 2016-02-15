/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.kt;
import com.google.android.gms.internal.kw;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public final class ko {
    private final int adT;
    private final byte[] buffer;
    private int position;

    private ko(byte[] arrby, int n2, int n3) {
        this.buffer = arrby;
        this.position = n2;
        this.adT = n2 + n3;
    }

    public static int A(long l2) {
        return ko.D(l2);
    }

    public static int B(long l2) {
        return ko.D(ko.E(l2));
    }

    public static int D(long l2) {
        if ((-128 & l2) == 0) {
            return 1;
        }
        if ((-16384 & l2) == 0) {
            return 2;
        }
        if ((-2097152 & l2) == 0) {
            return 3;
        }
        if ((-268435456 & l2) == 0) {
            return 4;
        }
        if ((-34359738368L & l2) == 0) {
            return 5;
        }
        if ((-4398046511104L & l2) == 0) {
            return 6;
        }
        if ((-562949953421312L & l2) == 0) {
            return 7;
        }
        if ((-72057594037927936L & l2) == 0) {
            return 8;
        }
        if ((Long.MIN_VALUE & l2) == 0) {
            return 9;
        }
        return 10;
    }

    public static int E(boolean bl2) {
        return 1;
    }

    public static long E(long l2) {
        return l2 << 1 ^ l2 >> 63;
    }

    public static int b(int n2, kt kt2) {
        return ko.cZ(n2) + ko.c(kt2);
    }

    public static int b(int n2, boolean bl2) {
        return ko.cZ(n2) + ko.E(bl2);
    }

    public static ko b(byte[] arrby, int n2, int n3) {
        return new ko(arrby, n2, n3);
    }

    public static int c(int n2, float f2) {
        return ko.cZ(n2) + ko.e(f2);
    }

    public static int c(kt kt2) {
        int n2 = kt2.c();
        return n2 + ko.db(n2);
    }

    public static int cX(int n2) {
        if (n2 >= 0) {
            return ko.db(n2);
        }
        return 10;
    }

    public static int cZ(int n2) {
        return ko.db(kw.l(n2, 0));
    }

    public static int cf(String string2) {
        try {
            string2 = (String)string2.getBytes("UTF-8");
            int n2 = ko.db(string2.length);
            int n3 = string2.length;
            return n3 + n2;
        }
        catch (UnsupportedEncodingException var0_1) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static int d(int n2, long l2) {
        return ko.cZ(n2) + ko.A(l2);
    }

    public static int db(int n2) {
        if ((n2 & -128) == 0) {
            return 1;
        }
        if ((n2 & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & n2) == 0) {
            return 3;
        }
        if ((-268435456 & n2) == 0) {
            return 4;
        }
        return 5;
    }

    public static int e(float f2) {
        return 4;
    }

    public static int e(int n2, long l2) {
        return ko.cZ(n2) + ko.B(l2);
    }

    public static int g(int n2, String string2) {
        return ko.cZ(n2) + ko.cf(string2);
    }

    public static int j(int n2, int n3) {
        return ko.cZ(n2) + ko.cX(n3);
    }

    public static ko o(byte[] arrby) {
        return ko.b(arrby, 0, arrby.length);
    }

    public void C(long l2) throws IOException {
        do {
            if ((-128 & l2) == 0) {
                this.cY((int)l2);
                return;
            }
            this.cY((int)l2 & 127 | 128);
            l2 >>>= 7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void D(boolean bl2) throws IOException {
        int n2 = bl2 ? 1 : 0;
        this.cY(n2);
    }

    public void a(int n2, kt kt2) throws IOException {
        this.k(n2, 2);
        this.b(kt2);
    }

    public void a(int n2, boolean bl2) throws IOException {
        this.k(n2, 0);
        this.D(bl2);
    }

    public void b(byte by2) throws IOException {
        if (this.position == this.adT) {
            throw new a(this.position, this.adT);
        }
        byte[] arrby = this.buffer;
        int n2 = this.position;
        this.position = n2 + 1;
        arrby[n2] = by2;
    }

    public void b(int n2, float f2) throws IOException {
        this.k(n2, 5);
        this.d(f2);
    }

    public void b(int n2, long l2) throws IOException {
        this.k(n2, 0);
        this.y(l2);
    }

    public void b(int n2, String string2) throws IOException {
        this.k(n2, 2);
        this.ce(string2);
    }

    public void b(kt kt2) throws IOException {
        this.da(kt2.mF());
        kt2.a(this);
    }

    public void c(int n2, long l2) throws IOException {
        this.k(n2, 0);
        this.z(l2);
    }

    public void c(byte[] arrby, int n2, int n3) throws IOException {
        if (this.adT - this.position >= n3) {
            System.arraycopy(arrby, n2, this.buffer, this.position, n3);
            this.position += n3;
            return;
        }
        throw new a(this.position, this.adT);
    }

    public void cW(int n2) throws IOException {
        if (n2 >= 0) {
            this.da(n2);
            return;
        }
        this.C(n2);
    }

    public void cY(int n2) throws IOException {
        this.b((byte)n2);
    }

    public void ce(String string2) throws IOException {
        string2 = (String)string2.getBytes("UTF-8");
        this.da(string2.length);
        this.p((byte[])string2);
    }

    public void d(float f2) throws IOException {
        this.dc(Float.floatToIntBits(f2));
    }

    public void da(int n2) throws IOException {
        do {
            if ((n2 & -128) == 0) {
                this.cY(n2);
                return;
            }
            this.cY(n2 & 127 | 128);
            n2 >>>= 7;
        } while (true);
    }

    public void dc(int n2) throws IOException {
        this.cY(n2 & 255);
        this.cY(n2 >> 8 & 255);
        this.cY(n2 >> 16 & 255);
        this.cY(n2 >> 24 & 255);
    }

    public void i(int n2, int n3) throws IOException {
        this.k(n2, 0);
        this.cW(n3);
    }

    public void k(int n2, int n3) throws IOException {
        this.da(kw.l(n2, n3));
    }

    public int mv() {
        return this.adT - this.position;
    }

    public void mw() {
        if (this.mv() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void p(byte[] arrby) throws IOException {
        this.c(arrby, 0, arrby.length);
    }

    public void y(long l2) throws IOException {
        this.C(l2);
    }

    public void z(long l2) throws IOException {
        this.C(ko.E(l2));
    }

    public static class a
    extends IOException {
        a(int n2, int n3) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + n2 + " limit " + n3 + ").");
        }
    }

}

