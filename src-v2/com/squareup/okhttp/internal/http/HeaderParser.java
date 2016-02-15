/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

public final class HeaderParser {
    private HeaderParser() {
    }

    public static int parseSeconds(String string2) {
        long l2;
        block3 : {
            try {
                l2 = Long.parseLong(string2);
                if (l2 > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                if (l2 >= 0) break block3;
                return 0;
            }
            catch (NumberFormatException var0_1) {
                return -1;
            }
        }
        return (int)l2;
    }

    public static int skipUntil(String string2, int n2, String string3) {
        while (n2 < string2.length() && string3.indexOf(string2.charAt(n2)) == -1) {
            ++n2;
        }
        return n2;
    }

    public static int skipWhitespace(String string2, int n2) {
        char c2;
        while (n2 < string2.length() && ((c2 = string2.charAt(n2)) == ' ' || c2 == '\t')) {
            ++n2;
        }
        return n2;
    }
}

