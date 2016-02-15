/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.UnsupportedEncodingException;

final class Base64 {
    private static final byte[] MAP = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    private Base64() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static byte[] decode(String var0) {
        var6_5 = var0.length();
        do {
            if (var6_5 <= 0 || (var1_6 = var0.charAt(var6_5 - 1)) != 61 && var1_6 != 10 && var1_6 != 13 && var1_6 != 32 && var1_6 != 9) break;
            --var6_5;
        } while (true);
        var9_7 = new byte[(int)((long)var6_5 * 6 / 8)];
        var4_8 = 0;
        var3_9 = 0;
        var7_10 = 0;
        var1_6 = 0;
        do {
            if (var7_10 >= var6_5) ** GOTO lbl52
            var8_13 = var0.charAt(var7_10);
            if (var8_13 < 'A' || var8_13 > 'Z') ** GOTO lbl17
            var2_11 = var8_13 - 65;
            ** GOTO lbl28
lbl17: // 1 sources:
            if (var8_13 < 'a' || var8_13 > 'z') ** GOTO lbl20
            var2_11 = var8_13 - 71;
            ** GOTO lbl28
lbl20: // 1 sources:
            if (var8_13 < '0' || var8_13 > '9') ** GOTO lbl23
            var2_11 = var8_13 + 4;
            ** GOTO lbl28
lbl23: // 1 sources:
            if (var8_13 != '+') ** GOTO lbl26
            var2_11 = 62;
            ** GOTO lbl28
lbl26: // 1 sources:
            if (var8_13 != '/') ** GOTO lbl40
            var2_11 = 63;
lbl28: // 5 sources:
            var3_9 = var3_9 << 6 | (byte)var2_11;
            var5_12 = ++var4_8;
            var2_11 = var3_9;
            if (var4_8 % 4 != 0) ** GOTO lbl71
            var2_11 = var1_6 + 1;
            var9_7[var1_6] = (byte)(var3_9 >> 16);
            var5_12 = var2_11 + 1;
            var9_7[var2_11] = (byte)(var3_9 >> 8);
            var1_6 = var5_12 + 1;
            var9_7[var5_12] = (byte)var3_9;
            var2_11 = var3_9;
            ** GOTO lbl72
lbl40: // 1 sources:
            var5_12 = var4_8;
            var2_11 = var3_9;
            if (var8_13 == '\n') ** GOTO lbl71
            var5_12 = var4_8;
            var2_11 = var3_9;
            if (var8_13 == '\r') ** GOTO lbl71
            var5_12 = var4_8;
            var2_11 = var3_9;
            if (var8_13 == ' ') ** GOTO lbl71
            if (var8_13 != '\t') return var0_2;
            var2_11 = var3_9;
            ** GOTO lbl72
lbl52: // 1 sources:
            if ((var4_8 %= 4) == 1) {
                return null;
            }
            if (var4_8 == 2) {
                var2_11 = var1_6 + '\u0001';
                var9_7[var1_6] = (byte)(var3_9 << 12 >> 16);
                var1_6 = var2_11;
            } else {
                var2_11 = var1_6;
                if (var4_8 == 3) {
                    var4_8 = var1_6 + 1;
                    var9_7[var1_6] = (byte)((var3_9 <<= 6) >> 16);
                    var2_11 = var4_8 + 1;
                    var9_7[var4_8] = (byte)(var3_9 >> 8);
                }
                var1_6 = var2_11;
            }
            var0_3 = var9_7;
            if (var1_6 == var9_7.length) return var0_2;
            var0_4 = new byte[var1_6];
            System.arraycopy(var9_7, 0, var0_4, 0, var1_6);
            return var0_4;
lbl71: // 4 sources:
            var4_8 = var5_12;
lbl72: // 3 sources:
            ++var7_10;
            var3_9 = var2_11;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String encode(byte[] object) {
        int n2;
        byte[] arrby = new byte[(object.length + 2) * 4 / 3];
        int n3 = object.length - object.length % 3;
        int n4 = 0;
        for (n2 = 0; n2 < n3; n2 += 3) {
            int n5 = n4 + 1;
            arrby[n4] = MAP[(object[n2] & 255) >> 2];
            n4 = n5 + 1;
            arrby[n5] = MAP[(object[n2] & 3) << 4 | (object[n2 + 1] & 255) >> 4];
            n5 = n4 + 1;
            arrby[n4] = MAP[(object[n2 + 1] & 15) << 2 | (object[n2 + 2] & 255) >> 6];
            n4 = n5 + 1;
            arrby[n5] = MAP[object[n2 + 2] & 63];
        }
        switch (object.length % 3) {
            case 1: {
                n2 = n4 + 1;
                arrby[n4] = MAP[(object[n3] & 255) >> 2];
                n4 = n2 + 1;
                arrby[n2] = MAP[(object[n3] & 3) << 4];
                n2 = n4 + 1;
                arrby[n4] = 61;
                arrby[n2] = 61;
                n4 = n2 + 1;
            }
            default: {
                break;
            }
            case 2: {
                n2 = n4 + 1;
                arrby[n4] = MAP[(object[n3] & 255) >> 2];
                n4 = n2 + 1;
                arrby[n2] = MAP[(object[n3] & 3) << 4 | (object[n3 + 1] & 255) >> 4];
                n2 = n4 + 1;
                arrby[n4] = MAP[(object[n3 + 1] & 15) << 2];
                n4 = n2 + 1;
                arrby[n2] = 61;
            }
        }
        try {
            return new String(arrby, 0, n4, "US-ASCII");
        }
        catch (UnsupportedEncodingException var0_1) {
            throw new AssertionError(var0_1);
        }
    }
}

