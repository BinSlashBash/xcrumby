/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import java.util.Arrays;

public final class kr {
    public static final Object adX = new Object();

    public static boolean equals(int[] arrn, int[] arrn2) {
        if (arrn == null || arrn.length == 0) {
            if (arrn2 == null || arrn2.length == 0) {
                return true;
            }
            return false;
        }
        return Arrays.equals(arrn, arrn2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean equals(Object[] arrobject, Object[] arrobject2) {
        boolean bl2 = false;
        int n2 = arrobject == null ? 0 : arrobject.length;
        int n3 = arrobject2 == null ? 0 : arrobject2.length;
        int n4 = 0;
        int n5 = 0;
        do {
            if (n5 < n2 && arrobject[n5] == null) {
                ++n5;
                continue;
            }
            while (n4 < n3 && arrobject2[n4] == null) {
                ++n4;
            }
            boolean bl3 = n5 >= n2;
            boolean bl4 = n4 >= n3;
            if (bl3 && bl4) {
                return true;
            }
            boolean bl5 = bl2;
            if (bl3 != bl4) return bl5;
            bl5 = bl2;
            if (!arrobject[n5].equals(arrobject2[n4])) return bl5;
            ++n4;
            ++n5;
        } while (true);
    }

    public static int hashCode(int[] arrn) {
        if (arrn == null || arrn.length == 0) {
            return 0;
        }
        return Arrays.hashCode(arrn);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int hashCode(Object[] arrobject) {
        int n2 = 0;
        int n3 = arrobject == null ? 0 : arrobject.length;
        int n4 = 0;
        while (n4 < n3) {
            Object object = arrobject[n4];
            int n5 = n2;
            if (object != null) {
                n5 = n2 * 31 + object.hashCode();
            }
            ++n4;
            n2 = n5;
        }
        return n2;
    }
}

