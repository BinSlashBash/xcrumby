/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import java.util.ArrayList;

public final class gi {
    public static void a(StringBuilder stringBuilder, double[] arrd) {
        int n2 = arrd.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Double.toString(arrd[i2]));
        }
    }

    public static void a(StringBuilder stringBuilder, float[] arrf) {
        int n2 = arrf.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Float.toString(arrf[i2]));
        }
    }

    public static void a(StringBuilder stringBuilder, int[] arrn) {
        int n2 = arrn.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Integer.toString(arrn[i2]));
        }
    }

    public static void a(StringBuilder stringBuilder, long[] arrl) {
        int n2 = arrl.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Long.toString(arrl[i2]));
        }
    }

    public static <T> void a(StringBuilder stringBuilder, T[] arrT) {
        int n2 = arrT.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(arrT[i2].toString());
        }
    }

    public static void a(StringBuilder stringBuilder, String[] arrstring) {
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"").append(arrstring[i2]).append("\"");
        }
    }

    public static void a(StringBuilder stringBuilder, boolean[] arrbl) {
        int n2 = arrbl.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Boolean.toString(arrbl[i2]));
        }
    }

    public static <T> ArrayList<T> fs() {
        return new ArrayList();
    }
}

