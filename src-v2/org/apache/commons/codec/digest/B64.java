/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.digest;

import java.util.Random;

class B64 {
    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    B64() {
    }

    static void b64from24bit(byte by2, byte by3, byte by4, int n2, StringBuilder stringBuilder) {
        by2 = (byte)(by2 << 16 & 16777215 | by3 << 8 & 65535 | by4 & 255);
        while (n2 > 0) {
            stringBuilder.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(by2 & 63));
            by2 = (byte)(by2 >> 6);
            --n2;
        }
    }

    static String getRandomSalt(int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 1; i2 <= n2; ++i2) {
            stringBuilder.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(new Random().nextInt("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length())));
        }
        return stringBuilder.toString();
    }
}

