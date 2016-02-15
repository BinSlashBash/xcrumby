/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.SoundexUtils;

public class RefinedSoundex
implements StringEncoder {
    public static final RefinedSoundex US_ENGLISH;
    private static final char[] US_ENGLISH_MAPPING;
    public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
    private final char[] soundexMapping;

    static {
        US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
        US_ENGLISH = new RefinedSoundex();
    }

    public RefinedSoundex() {
        this.soundexMapping = US_ENGLISH_MAPPING;
    }

    public RefinedSoundex(String string2) {
        this.soundexMapping = string2.toCharArray();
    }

    public RefinedSoundex(char[] arrc) {
        this.soundexMapping = new char[arrc.length];
        System.arraycopy(arrc, 0, this.soundexMapping, 0, arrc.length);
    }

    public int difference(String string2, String string3) throws EncoderException {
        return SoundexUtils.difference(this, string2, string3);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
        }
        return this.soundex((String)object);
    }

    @Override
    public String encode(String string2) {
        return this.soundex(string2);
    }

    char getMappingCode(char c2) {
        if (!Character.isLetter(c2)) {
            return '\u0000';
        }
        return this.soundexMapping[Character.toUpperCase(c2) - 65];
    }

    /*
     * Enabled aggressive block sorting
     */
    public String soundex(String string2) {
        if (string2 == null) {
            return null;
        }
        if ((string2 = SoundexUtils.clean(string2)).length() == 0) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2.charAt(0));
        char c2 = '*';
        int n2 = 0;
        while (n2 < string2.length()) {
            char c3 = this.getMappingCode(string2.charAt(n2));
            if (c3 != c2) {
                if (c3 != '\u0000') {
                    stringBuilder.append(c3);
                }
                c2 = c3;
            }
            ++n2;
        }
        return stringBuilder.toString();
    }
}

