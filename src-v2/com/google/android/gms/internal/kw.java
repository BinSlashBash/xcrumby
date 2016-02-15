/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.kn;
import java.io.IOException;

public final class kw {
    public static final int[] aea = new int[0];
    public static final long[] aeb = new long[0];
    public static final float[] aec = new float[0];
    public static final double[] aed = new double[0];
    public static final boolean[] aee = new boolean[0];
    public static final String[] aef = new String[0];
    public static final byte[][] aeg = new byte[0][];
    public static final byte[] aeh = new byte[0];

    public static final int b(kn kn2, int n2) throws IOException {
        int n3 = 1;
        int n4 = kn2.getPosition();
        kn2.cQ(n2);
        do {
            if (kn2.ms() <= 0 || kn2.mh() != n2) {
                kn2.cT(n4);
                return n3;
            }
            kn2.cQ(n2);
            ++n3;
        } while (true);
    }

    static int de(int n2) {
        return n2 & 7;
    }

    public static int df(int n2) {
        return n2 >>> 3;
    }

    static int l(int n2, int n3) {
        return n2 << 3 | n3;
    }
}

