/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Metaphone
implements StringEncoder {
    private static final String FRONTV = "EIY";
    private static final String VARSON = "CSPTG";
    private static final String VOWELS = "AEIOU";
    private int maxCodeLen = 4;

    private boolean isLastChar(int n2, int n3) {
        if (n3 + 1 == n2) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isNextChar(StringBuilder stringBuilder, int n2, char c2) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (n2 < 0) return bl3;
        bl3 = bl2;
        if (n2 >= stringBuilder.length() - 1) return bl3;
        if (stringBuilder.charAt(n2 + 1) != c2) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isPreviousChar(StringBuilder stringBuilder, int n2, char c2) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (n2 <= 0) return bl3;
        bl3 = bl2;
        if (n2 >= stringBuilder.length()) return bl3;
        if (stringBuilder.charAt(n2 - 1) != c2) return false;
        return true;
    }

    private boolean isVowel(StringBuilder stringBuilder, int n2) {
        if ("AEIOU".indexOf(stringBuilder.charAt(n2)) >= 0) {
            return true;
        }
        return false;
    }

    private boolean regionMatch(StringBuilder stringBuilder, int n2, String string2) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (n2 >= 0) {
            bl3 = bl2;
            if (string2.length() + n2 - 1 < stringBuilder.length()) {
                bl3 = stringBuilder.substring(n2, string2.length() + n2).equals(string2);
            }
        }
        return bl3;
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
        }
        return this.metaphone((String)object);
    }

    @Override
    public String encode(String string2) {
        return this.metaphone(string2);
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public boolean isMetaphoneEqual(String string2, String string3) {
        return this.metaphone(string2).equals(this.metaphone(string3));
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String metaphone(String string2) {
        if (string2 == null) return "";
        if (string2.length() == 0) {
            return "";
        }
        if (string2.length() == 1) {
            return string2.toUpperCase(Locale.ENGLISH);
        }
        char[] arrc = string2.toUpperCase(Locale.ENGLISH).toCharArray();
        StringBuilder stringBuilder = new StringBuilder(40);
        StringBuilder stringBuilder2 = new StringBuilder(10);
        switch (arrc[0]) {
            default: {
                stringBuilder.append(arrc);
                break;
            }
            case 'G': 
            case 'K': 
            case 'P': {
                if (arrc[1] == 'N') {
                    stringBuilder.append(arrc, 1, arrc.length - 1);
                    break;
                }
                stringBuilder.append(arrc);
                break;
            }
            case 'A': {
                if (arrc[1] == 'E') {
                    stringBuilder.append(arrc, 1, arrc.length - 1);
                    break;
                }
                stringBuilder.append(arrc);
                break;
            }
            case 'W': {
                if (arrc[1] == 'R') {
                    stringBuilder.append(arrc, 1, arrc.length - 1);
                    break;
                }
                if (arrc[1] == 'H') {
                    stringBuilder.append(arrc, 1, arrc.length - 1);
                    stringBuilder.setCharAt(0, 'W');
                    break;
                }
                stringBuilder.append(arrc);
                break;
            }
            case 'X': {
                arrc[0] = 83;
                stringBuilder.append(arrc);
            }
        }
        int n2 = stringBuilder.length();
        int n3 = 0;
        while (stringBuilder2.length() < this.getMaxCodeLen()) {
            int n4;
            if (n3 >= n2) return stringBuilder2.toString();
            char c2 = stringBuilder.charAt(n3);
            if (c2 != 'C' && this.isPreviousChar(stringBuilder, n3, c2)) {
                n4 = n3 + 1;
            } else {
                switch (c2) {
                    default: {
                        n4 = n3;
                        break;
                    }
                    case 'A': 
                    case 'E': 
                    case 'I': 
                    case 'O': 
                    case 'U': {
                        n4 = n3;
                        if (n3 != 0) break;
                        stringBuilder2.append(c2);
                        n4 = n3;
                        break;
                    }
                    case 'B': {
                        if (this.isPreviousChar(stringBuilder, n3, 'M')) {
                            n4 = n3;
                            if (this.isLastChar(n2, n3)) break;
                        }
                        stringBuilder2.append(c2);
                        n4 = n3;
                        break;
                    }
                    case 'C': {
                        if (this.isPreviousChar(stringBuilder, n3, 'S') && !this.isLastChar(n2, n3)) {
                            n4 = n3;
                            if ("EIY".indexOf(stringBuilder.charAt(n3 + 1)) >= 0) break;
                        }
                        if (this.regionMatch(stringBuilder, n3, "CIA")) {
                            stringBuilder2.append('X');
                            n4 = n3;
                            break;
                        }
                        if (!this.isLastChar(n2, n3) && "EIY".indexOf(stringBuilder.charAt(n3 + 1)) >= 0) {
                            stringBuilder2.append('S');
                            n4 = n3;
                            break;
                        }
                        if (this.isPreviousChar(stringBuilder, n3, 'S') && this.isNextChar(stringBuilder, n3, 'H')) {
                            stringBuilder2.append('K');
                            n4 = n3;
                            break;
                        }
                        if (this.isNextChar(stringBuilder, n3, 'H')) {
                            if (n3 == 0 && n2 >= 3 && this.isVowel(stringBuilder, 2)) {
                                stringBuilder2.append('K');
                                n4 = n3;
                                break;
                            }
                            stringBuilder2.append('X');
                            n4 = n3;
                            break;
                        }
                        stringBuilder2.append('K');
                        n4 = n3;
                        break;
                    }
                    case 'D': {
                        if (!this.isLastChar(n2, n3 + 1) && this.isNextChar(stringBuilder, n3, 'G') && "EIY".indexOf(stringBuilder.charAt(n3 + 2)) >= 0) {
                            stringBuilder2.append('J');
                            n4 = n3 + 2;
                            break;
                        }
                        stringBuilder2.append('T');
                        n4 = n3;
                        break;
                    }
                    case 'G': {
                        if (this.isLastChar(n2, n3 + 1)) {
                            n4 = n3;
                            if (this.isNextChar(stringBuilder, n3, 'H')) break;
                        }
                        if (!this.isLastChar(n2, n3 + 1) && this.isNextChar(stringBuilder, n3, 'H')) {
                            n4 = n3;
                            if (!this.isVowel(stringBuilder, n3 + 2)) break;
                        }
                        if (n3 > 0) {
                            n4 = n3;
                            if (this.regionMatch(stringBuilder, n3, "GN")) break;
                            n4 = n3;
                            if (this.regionMatch(stringBuilder, n3, "GNED")) break;
                        }
                        n4 = this.isPreviousChar(stringBuilder, n3, 'G') ? 1 : 0;
                        if (!this.isLastChar(n2, n3) && "EIY".indexOf(stringBuilder.charAt(n3 + 1)) >= 0 && n4 == 0) {
                            stringBuilder2.append('J');
                            n4 = n3;
                            break;
                        }
                        stringBuilder2.append('K');
                        n4 = n3;
                        break;
                    }
                    case 'H': {
                        n4 = n3;
                        if (this.isLastChar(n2, n3)) break;
                        if (n3 > 0) {
                            n4 = n3;
                            if ("CSPTG".indexOf(stringBuilder.charAt(n3 - 1)) >= 0) break;
                        }
                        n4 = n3;
                        if (!this.isVowel(stringBuilder, n3 + 1)) break;
                        stringBuilder2.append('H');
                        n4 = n3;
                        break;
                    }
                    case 'F': 
                    case 'J': 
                    case 'L': 
                    case 'M': 
                    case 'N': 
                    case 'R': {
                        stringBuilder2.append(c2);
                        n4 = n3;
                        break;
                    }
                    case 'K': {
                        if (n3 > 0) {
                            n4 = n3;
                            if (this.isPreviousChar(stringBuilder, n3, 'C')) break;
                            stringBuilder2.append(c2);
                            n4 = n3;
                            break;
                        }
                        stringBuilder2.append(c2);
                        n4 = n3;
                        break;
                    }
                    case 'P': {
                        if (this.isNextChar(stringBuilder, n3, 'H')) {
                            stringBuilder2.append('F');
                            n4 = n3;
                            break;
                        }
                        stringBuilder2.append(c2);
                        n4 = n3;
                        break;
                    }
                    case 'Q': {
                        stringBuilder2.append('K');
                        n4 = n3;
                        break;
                    }
                    case 'S': {
                        if (this.regionMatch(stringBuilder, n3, "SH") || this.regionMatch(stringBuilder, n3, "SIO") || this.regionMatch(stringBuilder, n3, "SIA")) {
                            stringBuilder2.append('X');
                            n4 = n3;
                            break;
                        }
                        stringBuilder2.append('S');
                        n4 = n3;
                        break;
                    }
                    case 'T': {
                        if (this.regionMatch(stringBuilder, n3, "TIA") || this.regionMatch(stringBuilder, n3, "TIO")) {
                            stringBuilder2.append('X');
                            n4 = n3;
                            break;
                        }
                        n4 = n3;
                        if (this.regionMatch(stringBuilder, n3, "TCH")) break;
                        if (this.regionMatch(stringBuilder, n3, "TH")) {
                            stringBuilder2.append('0');
                            n4 = n3;
                            break;
                        }
                        stringBuilder2.append('T');
                        n4 = n3;
                        break;
                    }
                    case 'V': {
                        stringBuilder2.append('F');
                        n4 = n3;
                        break;
                    }
                    case 'W': 
                    case 'Y': {
                        n4 = n3;
                        if (this.isLastChar(n2, n3)) break;
                        n4 = n3;
                        if (!this.isVowel(stringBuilder, n3 + 1)) break;
                        stringBuilder2.append(c2);
                        n4 = n3;
                        break;
                    }
                    case 'X': {
                        stringBuilder2.append('K');
                        stringBuilder2.append('S');
                        n4 = n3;
                        break;
                    }
                    case 'Z': {
                        stringBuilder2.append('S');
                        n4 = n3;
                    }
                }
                ++n4;
            }
            n3 = n4;
            if (stringBuilder2.length() <= this.getMaxCodeLen()) continue;
            stringBuilder2.setLength(this.getMaxCodeLen());
            n3 = n4;
        }
        return stringBuilder2.toString();
    }

    public void setMaxCodeLen(int n2) {
        this.maxCodeLen = n2;
    }
}

