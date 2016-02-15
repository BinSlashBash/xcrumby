package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Hex;

public final class ko {
    private final int adT;
    private final byte[] buffer;
    private int position;

    /* renamed from: com.google.android.gms.internal.ko.a */
    public static class C0409a extends IOException {
        C0409a(int i, int i2) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
        }
    }

    private ko(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.position = i;
        this.adT = i + i2;
    }

    public static int m1129A(long j) {
        return m1131D(j);
    }

    public static int m1130B(long j) {
        return m1131D(m1133E(j));
    }

    public static int m1131D(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public static int m1132E(boolean z) {
        return 1;
    }

    public static long m1133E(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int m1134b(int i, kt ktVar) {
        return cZ(i) + m1138c(ktVar);
    }

    public static int m1135b(int i, boolean z) {
        return cZ(i) + m1132E(z);
    }

    public static ko m1136b(byte[] bArr, int i, int i2) {
        return new ko(bArr, i, i2);
    }

    public static int m1137c(int i, float f) {
        return cZ(i) + m1140e(f);
    }

    public static int m1138c(kt ktVar) {
        int c = ktVar.m1172c();
        return c + db(c);
    }

    public static int cX(int i) {
        return i >= 0 ? db(i) : 10;
    }

    public static int cZ(int i) {
        return db(kw.m1177l(i, 0));
    }

    public static int cf(String str) {
        try {
            byte[] bytes = str.getBytes(Hex.DEFAULT_CHARSET_NAME);
            return bytes.length + db(bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static int m1139d(int i, long j) {
        return cZ(i) + m1129A(j);
    }

    public static int db(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    public static int m1140e(float f) {
        return 4;
    }

    public static int m1141e(int i, long j) {
        return cZ(i) + m1130B(j);
    }

    public static int m1142g(int i, String str) {
        return cZ(i) + cf(str);
    }

    public static int m1143j(int i, int i2) {
        return cZ(i) + cX(i2);
    }

    public static ko m1144o(byte[] bArr) {
        return m1136b(bArr, 0, bArr.length);
    }

    public void m1145C(long j) throws IOException {
        while ((-128 & j) != 0) {
            cY((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            j >>>= 7;
        }
        cY((int) j);
    }

    public void m1146D(boolean z) throws IOException {
        cY(z ? 1 : 0);
    }

    public void m1147a(int i, kt ktVar) throws IOException {
        m1158k(i, 2);
        m1153b(ktVar);
    }

    public void m1148a(int i, boolean z) throws IOException {
        m1158k(i, 0);
        m1146D(z);
    }

    public void m1149b(byte b) throws IOException {
        if (this.position == this.adT) {
            throw new C0409a(this.position, this.adT);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = b;
    }

    public void m1150b(int i, float f) throws IOException {
        m1158k(i, 5);
        m1156d(f);
    }

    public void m1151b(int i, long j) throws IOException {
        m1158k(i, 0);
        m1160y(j);
    }

    public void m1152b(int i, String str) throws IOException {
        m1158k(i, 2);
        ce(str);
    }

    public void m1153b(kt ktVar) throws IOException {
        da(ktVar.mF());
        ktVar.m1170a(this);
    }

    public void m1154c(int i, long j) throws IOException {
        m1158k(i, 0);
        m1161z(j);
    }

    public void m1155c(byte[] bArr, int i, int i2) throws IOException {
        if (this.adT - this.position >= i2) {
            System.arraycopy(bArr, i, this.buffer, this.position, i2);
            this.position += i2;
            return;
        }
        throw new C0409a(this.position, this.adT);
    }

    public void cW(int i) throws IOException {
        if (i >= 0) {
            da(i);
        } else {
            m1145C((long) i);
        }
    }

    public void cY(int i) throws IOException {
        m1149b((byte) i);
    }

    public void ce(String str) throws IOException {
        byte[] bytes = str.getBytes(Hex.DEFAULT_CHARSET_NAME);
        da(bytes.length);
        m1159p(bytes);
    }

    public void m1156d(float f) throws IOException {
        dc(Float.floatToIntBits(f));
    }

    public void da(int i) throws IOException {
        while ((i & -128) != 0) {
            cY((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            i >>>= 7;
        }
        cY(i);
    }

    public void dc(int i) throws IOException {
        cY(i & MotionEventCompat.ACTION_MASK);
        cY((i >> 8) & MotionEventCompat.ACTION_MASK);
        cY((i >> 16) & MotionEventCompat.ACTION_MASK);
        cY((i >> 24) & MotionEventCompat.ACTION_MASK);
    }

    public void m1157i(int i, int i2) throws IOException {
        m1158k(i, 0);
        cW(i2);
    }

    public void m1158k(int i, int i2) throws IOException {
        da(kw.m1177l(i, i2));
    }

    public int mv() {
        return this.adT - this.position;
    }

    public void mw() {
        if (mv() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void m1159p(byte[] bArr) throws IOException {
        m1155c(bArr, 0, bArr.length);
    }

    public void m1160y(long j) throws IOException {
        m1145C(j);
    }

    public void m1161z(long j) throws IOException {
        m1145C(m1133E(j));
    }
}
