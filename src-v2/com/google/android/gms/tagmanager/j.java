/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

class j {
    public static byte[] bm(String string2) {
        int n2 = string2.length();
        if (n2 % 2 != 0) {
            throw new IllegalArgumentException("purported base16 string has odd number of characters");
        }
        byte[] arrby = new byte[n2 / 2];
        for (int i2 = 0; i2 < n2; i2 += 2) {
            int n3 = Character.digit(string2.charAt(i2), 16);
            int n4 = Character.digit(string2.charAt(i2 + 1), 16);
            if (n3 == -1 || n4 == -1) {
                throw new IllegalArgumentException("purported base16 string has illegal char");
            }
            arrby[i2 / 2] = (byte)((n3 << 4) + n4);
        }
        return arrby;
    }

    public static String d(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte by2 : arrby) {
            if ((by2 & 240) == 0) {
                stringBuilder.append("0");
            }
            stringBuilder.append(Integer.toHexString(by2 & 255));
        }
        return stringBuilder.toString().toUpperCase();
    }
}

