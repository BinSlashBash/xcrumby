/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import com.google.android.gms.internal.kw;
import java.io.IOException;

public final class kn {
    private int adK;
    private int adL;
    private int adM;
    private int adN;
    private int adO;
    private int adP = Integer.MAX_VALUE;
    private int adQ;
    private int adR = 64;
    private int adS = 67108864;
    private final byte[] buffer;

    private kn(byte[] arrby, int n2, int n3) {
        this.buffer = arrby;
        this.adK = n2;
        this.adL = n2 + n3;
        this.adN = n2;
    }

    public static kn a(byte[] arrby, int n2, int n3) {
        return new kn(arrby, n2, n3);
    }

    private void mr() {
        this.adL += this.adM;
        int n2 = this.adL;
        if (n2 > this.adP) {
            this.adM = n2 - this.adP;
            this.adL -= this.adM;
            return;
        }
        this.adM = 0;
    }

    public static kn n(byte[] arrby) {
        return kn.a(arrby, 0, arrby.length);
    }

    public static long x(long l2) {
        return l2 >>> 1 ^ - (1 & l2);
    }

    public void a(kt kt2) throws IOException {
        int n2 = this.mn();
        if (this.adQ >= this.adR) {
            throw ks.mE();
        }
        n2 = this.cR(n2);
        ++this.adQ;
        kt2.b(this);
        this.cP(0);
        --this.adQ;
        this.cS(n2);
    }

    public void a(kt kt2, int n2) throws IOException {
        if (this.adQ >= this.adR) {
            throw ks.mE();
        }
        ++this.adQ;
        kt2.b(this);
        this.cP(kw.l(n2, 4));
        --this.adQ;
    }

    public void cP(int n2) throws ks {
        if (this.adO != n2) {
            throw ks.mC();
        }
    }

    public boolean cQ(int n2) throws IOException {
        switch (kw.de(n2)) {
            default: {
                throw ks.mD();
            }
            case 0: {
                this.mk();
                return true;
            }
            case 1: {
                this.mq();
                return true;
            }
            case 2: {
                this.cV(this.mn());
                return true;
            }
            case 3: {
                this.mi();
                this.cP(kw.l(kw.df(n2), 4));
                return true;
            }
            case 4: {
                return false;
            }
            case 5: 
        }
        this.mp();
        return true;
    }

    public int cR(int n2) throws ks {
        if (n2 < 0) {
            throw ks.mz();
        }
        int n3 = this.adP;
        if ((n2 = this.adN + n2) > n3) {
            throw ks.my();
        }
        this.adP = n2;
        this.mr();
        return n3;
    }

    public void cS(int n2) {
        this.adP = n2;
        this.mr();
    }

    public void cT(int n2) {
        if (n2 > this.adN - this.adK) {
            throw new IllegalArgumentException("Position " + n2 + " is beyond current " + (this.adN - this.adK));
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("Bad position " + n2);
        }
        this.adN = this.adK + n2;
    }

    public byte[] cU(int n2) throws IOException {
        if (n2 < 0) {
            throw ks.mz();
        }
        if (this.adN + n2 > this.adP) {
            this.cV(this.adP - this.adN);
            throw ks.my();
        }
        if (n2 <= this.adL - this.adN) {
            byte[] arrby = new byte[n2];
            System.arraycopy(this.buffer, this.adN, arrby, 0, n2);
            this.adN += n2;
            return arrby;
        }
        throw ks.my();
    }

    public void cV(int n2) throws IOException {
        if (n2 < 0) {
            throw ks.mz();
        }
        if (this.adN + n2 > this.adP) {
            this.cV(this.adP - this.adN);
            throw ks.my();
        }
        if (n2 <= this.adL - this.adN) {
            this.adN += n2;
            return;
        }
        throw ks.my();
    }

    public int getPosition() {
        return this.adN - this.adK;
    }

    public byte[] h(int n2, int n3) {
        if (n3 == 0) {
            return kw.aeh;
        }
        byte[] arrby = new byte[n3];
        int n4 = this.adK;
        System.arraycopy(this.buffer, n4 + n2, arrby, 0, n3);
        return arrby;
    }

    public int mh() throws IOException {
        if (this.mt()) {
            this.adO = 0;
            return 0;
        }
        this.adO = this.mn();
        if (this.adO == 0) {
            throw ks.mB();
        }
        return this.adO;
    }

    public void mi() throws IOException {
        int n2;
        while ((n2 = this.mh()) != 0 && this.cQ(n2)) {
        }
    }

    public long mj() throws IOException {
        return this.mo();
    }

    public int mk() throws IOException {
        return this.mn();
    }

    public boolean ml() throws IOException {
        if (this.mn() != 0) {
            return true;
        }
        return false;
    }

    public long mm() throws IOException {
        return kn.x(this.mo());
    }

    /*
     * Enabled aggressive block sorting
     */
    public int mn() throws IOException {
        int n2 = this.mu();
        if (n2 < 0) {
            n2 &= 127;
            int n3 = this.mu();
            if (n3 >= 0) {
                return n2 | n3 << 7;
            }
            n2 |= (n3 & 127) << 7;
            n3 = this.mu();
            if (n3 >= 0) {
                return n2 | n3 << 14;
            }
            n2 |= (n3 & 127) << 14;
            int n4 = this.mu();
            if (n4 >= 0) {
                return n2 | n4 << 21;
            }
            n3 = this.mu();
            n2 = n4 = n2 | (n4 & 127) << 21 | n3 << 28;
            if (n3 < 0) {
                n3 = 0;
                do {
                    if (n3 >= 5) {
                        throw ks.mA();
                    }
                    n2 = n4;
                    if (this.mu() >= 0) break;
                    ++n3;
                } while (true);
            }
        }
        return n2;
    }

    public long mo() throws IOException {
        long l2 = 0;
        for (int i2 = 0; i2 < 64; i2 += 7) {
            byte by2 = this.mu();
            l2 |= (long)(by2 & 127) << i2;
            if ((by2 & 128) != 0) continue;
            return l2;
        }
        throw ks.mA();
    }

    public int mp() throws IOException {
        return this.mu() & 255 | (this.mu() & 255) << 8 | (this.mu() & 255) << 16 | (this.mu() & 255) << 24;
    }

    public long mq() throws IOException {
        byte by2 = this.mu();
        byte by3 = this.mu();
        byte by4 = this.mu();
        byte by5 = this.mu();
        byte by6 = this.mu();
        byte by7 = this.mu();
        byte by8 = this.mu();
        byte by9 = this.mu();
        long l2 = by2;
        return ((long)by3 & 255) << 8 | l2 & 255 | ((long)by4 & 255) << 16 | ((long)by5 & 255) << 24 | ((long)by6 & 255) << 32 | ((long)by7 & 255) << 40 | ((long)by8 & 255) << 48 | ((long)by9 & 255) << 56;
    }

    public int ms() {
        if (this.adP == Integer.MAX_VALUE) {
            return -1;
        }
        int n2 = this.adN;
        return this.adP - n2;
    }

    public boolean mt() {
        if (this.adN == this.adL) {
            return true;
        }
        return false;
    }

    public byte mu() throws IOException {
        if (this.adN == this.adL) {
            throw ks.my();
        }
        byte[] arrby = this.buffer;
        int n2 = this.adN;
        this.adN = n2 + 1;
        return arrby[n2];
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.mp());
    }

    public String readString() throws IOException {
        int n2 = this.mn();
        if (n2 <= this.adL - this.adN && n2 > 0) {
            String string2 = new String(this.buffer, this.adN, n2, "UTF-8");
            this.adN = n2 + this.adN;
            return string2;
        }
        return new String(this.cU(n2), "UTF-8");
    }
}

