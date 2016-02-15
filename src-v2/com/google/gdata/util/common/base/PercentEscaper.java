/*
 * Decompiled with CFR 0_110.
 */
package com.google.gdata.util.common.base;

import com.google.gdata.util.common.base.UnicodeEscaper;

public class PercentEscaper
extends UnicodeEscaper {
    public static final String SAFECHARS_URLENCODER = "-_.*";
    public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=";
    public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
    private static final char[] UPPER_HEX_DIGITS;
    private static final char[] URI_ESCAPED_SPACE;
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    static {
        URI_ESCAPED_SPACE = new char[]{'+'};
        UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    }

    public PercentEscaper(String string2, boolean bl2) {
        if (string2.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        if (bl2 && string2.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        if (string2.contains("%")) {
            throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
        }
        this.plusForSpace = bl2;
        this.safeOctets = PercentEscaper.createSafeOctets(string2);
    }

    private static boolean[] createSafeOctets(String string2) {
        int n2;
        int n3 = 122;
        string2 = (String)string2.toCharArray();
        int n4 = string2.length;
        for (n2 = 0; n2 < n4; ++n2) {
            n3 = Math.max((int)string2[n2], n3);
        }
        boolean[] arrbl = new boolean[n3 + 1];
        for (n2 = 48; n2 <= 57; ++n2) {
            arrbl[n2] = true;
        }
        for (n2 = 65; n2 <= 90; ++n2) {
            arrbl[n2] = true;
        }
        for (n2 = 97; n2 <= 122; ++n2) {
            arrbl[n2] = true;
        }
        n3 = string2.length;
        for (n2 = 0; n2 < n3; ++n2) {
            arrbl[string2[n2]] = true;
        }
        return arrbl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String escape(String string2) {
        int n2 = string2.length();
        int n3 = 0;
        do {
            String string3 = string2;
            if (n3 >= n2) return string3;
            char c2 = string2.charAt(n3);
            if (c2 >= this.safeOctets.length) return this.escapeSlow(string2, n3);
            if (!this.safeOctets[c2]) {
                return this.escapeSlow(string2, n3);
            }
            ++n3;
        } while (true);
    }

    @Override
    protected char[] escape(int n2) {
        if (n2 < this.safeOctets.length && this.safeOctets[n2]) {
            return null;
        }
        if (n2 == 32 && this.plusForSpace) {
            return URI_ESCAPED_SPACE;
        }
        if (n2 <= 127) {
            char c2 = UPPER_HEX_DIGITS[n2 & 15];
            return new char[]{'%', UPPER_HEX_DIGITS[n2 >>> 4], c2};
        }
        if (n2 <= 2047) {
            char c3 = UPPER_HEX_DIGITS[n2 & 15];
            char c4 = UPPER_HEX_DIGITS[(n2 >>>= 4) & 3 | 8];
            char c5 = UPPER_HEX_DIGITS[(n2 >>>= 2) & 15];
            return new char[]{'%', UPPER_HEX_DIGITS[n2 >>> 4 | 12], c5, '%', c4, c3};
        }
        if (n2 <= 65535) {
            char c6 = UPPER_HEX_DIGITS[n2 & 15];
            char c7 = UPPER_HEX_DIGITS[(n2 >>>= 4) & 3 | 8];
            char c8 = UPPER_HEX_DIGITS[(n2 >>>= 2) & 15];
            char c9 = UPPER_HEX_DIGITS[(n2 >>>= 4) & 3 | 8];
            return new char[]{'%', 'E', UPPER_HEX_DIGITS[n2 >>> 2], '%', c9, c8, '%', c7, c6};
        }
        if (n2 <= 1114111) {
            char c10 = UPPER_HEX_DIGITS[n2 & 15];
            char c11 = UPPER_HEX_DIGITS[(n2 >>>= 4) & 3 | 8];
            char c12 = UPPER_HEX_DIGITS[(n2 >>>= 2) & 15];
            char c13 = UPPER_HEX_DIGITS[(n2 >>>= 4) & 3 | 8];
            char c14 = UPPER_HEX_DIGITS[(n2 >>>= 2) & 15];
            char c15 = UPPER_HEX_DIGITS[(n2 >>>= 4) & 3 | 8];
            return new char[]{'%', 'F', UPPER_HEX_DIGITS[n2 >>> 2 & 7], '%', c15, c14, '%', c13, c12, '%', c11, c10};
        }
        throw new IllegalArgumentException("Invalid unicode character value " + n2);
    }

    @Override
    protected int nextEscapeIndex(CharSequence charSequence, int n2, int n3) {
        char c2;
        while (n2 < n3 && (c2 = charSequence.charAt(n2)) < this.safeOctets.length && this.safeOctets[c2]) {
            ++n2;
        }
        return n2;
    }
}

