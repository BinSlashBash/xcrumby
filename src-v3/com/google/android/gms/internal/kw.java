package com.google.android.gms.internal;

import java.io.IOException;

public final class kw {
    public static final int[] aea;
    public static final long[] aeb;
    public static final float[] aec;
    public static final double[] aed;
    public static final boolean[] aee;
    public static final String[] aef;
    public static final byte[][] aeg;
    public static final byte[] aeh;

    static {
        aea = new int[0];
        aeb = new long[0];
        aec = new float[0];
        aed = new double[0];
        aee = new boolean[0];
        aef = new String[0];
        aeg = new byte[0][];
        aeh = new byte[0];
    }

    public static final int m1176b(kn knVar, int i) throws IOException {
        int i2 = 1;
        int position = knVar.getPosition();
        knVar.cQ(i);
        while (knVar.ms() > 0 && knVar.mh() == i) {
            knVar.cQ(i);
            i2++;
        }
        knVar.cT(position);
        return i2;
    }

    static int de(int i) {
        return i & 7;
    }

    public static int df(int i) {
        return i >>> 3;
    }

    static int m1177l(int i, int i2) {
        return (i << 3) | i2;
    }
}
