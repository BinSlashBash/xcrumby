package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import java.io.IOException;
import org.apache.commons.codec.binary.Hex;
import org.json.zip.JSONzip;

public final class kn {
    private int adK;
    private int adL;
    private int adM;
    private int adN;
    private int adO;
    private int adP;
    private int adQ;
    private int adR;
    private int adS;
    private final byte[] buffer;

    private kn(byte[] bArr, int i, int i2) {
        this.adP = Integer.MAX_VALUE;
        this.adR = 64;
        this.adS = 67108864;
        this.buffer = bArr;
        this.adK = i;
        this.adL = i + i2;
        this.adN = i;
    }

    public static kn m1123a(byte[] bArr, int i, int i2) {
        return new kn(bArr, i, i2);
    }

    private void mr() {
        this.adL += this.adM;
        int i = this.adL;
        if (i > this.adP) {
            this.adM = i - this.adP;
            this.adL -= this.adM;
            return;
        }
        this.adM = 0;
    }

    public static kn m1124n(byte[] bArr) {
        return m1123a(bArr, 0, bArr.length);
    }

    public static long m1125x(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public void m1126a(kt ktVar) throws IOException {
        int mn = mn();
        if (this.adQ >= this.adR) {
            throw ks.mE();
        }
        mn = cR(mn);
        this.adQ++;
        ktVar.m1171b(this);
        cP(0);
        this.adQ--;
        cS(mn);
    }

    public void m1127a(kt ktVar, int i) throws IOException {
        if (this.adQ >= this.adR) {
            throw ks.mE();
        }
        this.adQ++;
        ktVar.m1171b(this);
        cP(kw.m1177l(i, 4));
        this.adQ--;
    }

    public void cP(int i) throws ks {
        if (this.adO != i) {
            throw ks.mC();
        }
    }

    public boolean cQ(int i) throws IOException {
        switch (kw.de(i)) {
            case JSONzip.zipEmptyObject /*0*/:
                mk();
                return true;
            case Std.STD_FILE /*1*/:
                mq();
                return true;
            case Std.STD_URL /*2*/:
                cV(mn());
                return true;
            case Std.STD_URI /*3*/:
                mi();
                cP(kw.m1177l(kw.df(i), 4));
                return true;
            case Std.STD_CLASS /*4*/:
                return false;
            case Std.STD_JAVA_TYPE /*5*/:
                mp();
                return true;
            default:
                throw ks.mD();
        }
    }

    public int cR(int i) throws ks {
        if (i < 0) {
            throw ks.mz();
        }
        int i2 = this.adN + i;
        int i3 = this.adP;
        if (i2 > i3) {
            throw ks.my();
        }
        this.adP = i2;
        mr();
        return i3;
    }

    public void cS(int i) {
        this.adP = i;
        mr();
    }

    public void cT(int i) {
        if (i > this.adN - this.adK) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.adN - this.adK));
        } else if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        } else {
            this.adN = this.adK + i;
        }
    }

    public byte[] cU(int i) throws IOException {
        if (i < 0) {
            throw ks.mz();
        } else if (this.adN + i > this.adP) {
            cV(this.adP - this.adN);
            throw ks.my();
        } else if (i <= this.adL - this.adN) {
            Object obj = new byte[i];
            System.arraycopy(this.buffer, this.adN, obj, 0, i);
            this.adN += i;
            return obj;
        } else {
            throw ks.my();
        }
    }

    public void cV(int i) throws IOException {
        if (i < 0) {
            throw ks.mz();
        } else if (this.adN + i > this.adP) {
            cV(this.adP - this.adN);
            throw ks.my();
        } else if (i <= this.adL - this.adN) {
            this.adN += i;
        } else {
            throw ks.my();
        }
    }

    public int getPosition() {
        return this.adN - this.adK;
    }

    public byte[] m1128h(int i, int i2) {
        if (i2 == 0) {
            return kw.aeh;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.buffer, this.adK + i, obj, 0, i2);
        return obj;
    }

    public int mh() throws IOException {
        if (mt()) {
            this.adO = 0;
            return 0;
        }
        this.adO = mn();
        if (this.adO != 0) {
            return this.adO;
        }
        throw ks.mB();
    }

    public void mi() throws IOException {
        int mh;
        do {
            mh = mh();
            if (mh == 0) {
                return;
            }
        } while (cQ(mh));
    }

    public long mj() throws IOException {
        return mo();
    }

    public int mk() throws IOException {
        return mn();
    }

    public boolean ml() throws IOException {
        return mn() != 0;
    }

    public long mm() throws IOException {
        return m1125x(mo());
    }

    public int mn() throws IOException {
        byte mu = mu();
        if (mu >= null) {
            return mu;
        }
        int i = mu & TransportMediator.KEYCODE_MEDIA_PAUSE;
        byte mu2 = mu();
        if (mu2 >= null) {
            return i | (mu2 << 7);
        }
        i |= (mu2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 7;
        mu2 = mu();
        if (mu2 >= null) {
            return i | (mu2 << 14);
        }
        i |= (mu2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 14;
        mu2 = mu();
        if (mu2 >= null) {
            return i | (mu2 << 21);
        }
        i |= (mu2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 21;
        mu2 = mu();
        i |= mu2 << 28;
        if (mu2 >= null) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (mu() >= null) {
                return i;
            }
        }
        throw ks.mA();
    }

    public long mo() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte mu = mu();
            j |= ((long) (mu & TransportMediator.KEYCODE_MEDIA_PAUSE)) << i;
            if ((mu & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0) {
                return j;
            }
        }
        throw ks.mA();
    }

    public int mp() throws IOException {
        return (((mu() & MotionEventCompat.ACTION_MASK) | ((mu() & MotionEventCompat.ACTION_MASK) << 8)) | ((mu() & MotionEventCompat.ACTION_MASK) << 16)) | ((mu() & MotionEventCompat.ACTION_MASK) << 24);
    }

    public long mq() throws IOException {
        byte mu = mu();
        byte mu2 = mu();
        return ((((((((((long) mu2) & 255) << 8) | (((long) mu) & 255)) | ((((long) mu()) & 255) << 16)) | ((((long) mu()) & 255) << 24)) | ((((long) mu()) & 255) << 32)) | ((((long) mu()) & 255) << 40)) | ((((long) mu()) & 255) << 48)) | ((((long) mu()) & 255) << 56);
    }

    public int ms() {
        if (this.adP == Integer.MAX_VALUE) {
            return -1;
        }
        return this.adP - this.adN;
    }

    public boolean mt() {
        return this.adN == this.adL;
    }

    public byte mu() throws IOException {
        if (this.adN == this.adL) {
            throw ks.my();
        }
        byte[] bArr = this.buffer;
        int i = this.adN;
        this.adN = i + 1;
        return bArr[i];
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(mp());
    }

    public String readString() throws IOException {
        int mn = mn();
        if (mn > this.adL - this.adN || mn <= 0) {
            return new String(cU(mn), Hex.DEFAULT_CHARSET_NAME);
        }
        String str = new String(this.buffer, this.adN, mn, Hex.DEFAULT_CHARSET_NAME);
        this.adN = mn + this.adN;
        return str;
    }
}
