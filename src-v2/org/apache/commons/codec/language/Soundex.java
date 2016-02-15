/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.SoundexUtils;

public class Soundex
implements StringEncoder {
    public static final Soundex US_ENGLISH;
    private static final char[] US_ENGLISH_MAPPING;
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    @Deprecated
    private int maxLength = 4;
    private final char[] soundexMapping;

    static {
        US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
        US_ENGLISH = new Soundex();
    }

    public Soundex() {
        this.soundexMapping = US_ENGLISH_MAPPING;
    }

    public Soundex(String string2) {
        this.soundexMapping = string2.toCharArray();
    }

    public Soundex(char[] arrc) {
        this.soundexMapping = new char[arrc.length];
        System.arraycopy(arrc, 0, this.soundexMapping, 0, arrc.length);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private char getMappingCode(String string2, int n2) {
        char c2;
        char c3;
        char c4 = c2 = this.map(string2.charAt(n2));
        if (n2 <= 1) return c4;
        c4 = c2;
        if (c2 == '0') return c4;
        char c5 = string2.charAt(n2 - 1);
        if ('H' != c5) {
            c4 = c2;
            if ('W' != c5) return c4;
        }
        if (this.map(c3 = string2.charAt(n2 - 2)) == c2) return '\u0000';
        if ('H' == c3) return '\u0000';
        c4 = c2;
        if ('W' != c3) return c4;
        return '\u0000';
    }

    private char[] getSoundexMapping() {
        return this.soundexMapping;
    }

    private char map(char c2) {
        int n2 = c2 - 65;
        if (n2 < 0 || n2 >= this.getSoundexMapping().length) {
            throw new IllegalArgumentException("The character is not mapped: " + c2);
        }
        return this.getSoundexMapping()[n2];
    }

    public int difference(String string2, String string3) throws EncoderException {
        return SoundexUtils.difference(this, string2, string3);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
        }
        return this.soundex((String)object);
    }

    @Override
    public String encode(String string2) {
        return this.soundex(string2);
    }

    @Deprecated
    public int getMaxLength() {
        return this.maxLength;
    }

    @Deprecated
    public void setMaxLength(int n2) {
        this.maxLength = n2;
    }

    public String soundex(String string2) {
        char[] arrc;
        if (string2 == null) {
            return null;
        }
        if ((string2 = SoundexUtils.clean(string2)).length() == 0) {
            return string2;
        }
        char[] arrc2 = arrc = new char[4];
        arrc2[0] = 48;
        arrc2[1] = 48;
        arrc2[2] = 48;
        arrc2[3] = 48;
        int n2 = 1;
        int n3 = 1;
        arrc[0] = string2.charAt(0);
        char c2 = this.getMappingCode(string2, 0);
        do {
            int n4;
            if (n2 < string2.length() && n3 < arrc.length) {
                n4 = n2 + 1;
                char c3 = this.getMappingCode(string2, n2);
                if (c3 != '\u0000') {
                    n2 = n3;
                    if (c3 != '0') {
                        n2 = n3;
                        if (c3 != c2) {
                            arrc[n3] = c3;
                            n2 = n3 + 1;
                        }
                    }
                    c2 = c3;
                    n3 = n2;
                    n2 = n4;
                    continue;
                }
            } else {
                return new String(arrc);
            }
            n2 = n4;
        } while (true);
    }
}

