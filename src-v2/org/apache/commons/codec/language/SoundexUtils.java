/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

final class SoundexUtils {
    SoundexUtils() {
    }

    static String clean(String string2) {
        if (string2 == null || string2.length() == 0) {
            return string2;
        }
        int n2 = string2.length();
        char[] arrc = new char[n2];
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (!Character.isLetter(string2.charAt(i2))) continue;
            int n4 = n3 + 1;
            arrc[n3] = string2.charAt(i2);
            n3 = n4;
        }
        if (n3 == n2) {
            return string2.toUpperCase(Locale.ENGLISH);
        }
        return new String(arrc, 0, n3).toUpperCase(Locale.ENGLISH);
    }

    static int difference(StringEncoder stringEncoder, String string2, String string3) throws EncoderException {
        return SoundexUtils.differenceEncoded(stringEncoder.encode(string2), stringEncoder.encode(string3));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int differenceEncoded(String string2, String string3) {
        if (string2 == null) return 0;
        if (string3 == null) {
            return 0;
        }
        int n2 = Math.min(string2.length(), string3.length());
        int n3 = 0;
        int n4 = 0;
        do {
            int n5 = n3;
            if (n4 >= n2) return n5;
            n5 = n3;
            if (string2.charAt(n4) == string3.charAt(n4)) {
                n5 = n3 + 1;
            }
            ++n4;
            n3 = n5;
        } while (true);
    }
}

