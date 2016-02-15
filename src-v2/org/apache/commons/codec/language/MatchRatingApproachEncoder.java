/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class MatchRatingApproachEncoder
implements StringEncoder {
    private static final String[] DOUBLE_CONSONANT = new String[]{"BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ"};
    private static final int EIGHT = 8;
    private static final int ELEVEN = 11;
    private static final String EMPTY = "";
    private static final int FIVE = 5;
    private static final int FOUR = 4;
    private static final int ONE = 1;
    private static final String PLAIN_ASCII = "AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu";
    private static final int SEVEN = 7;
    private static final int SIX = 6;
    private static final String SPACE = " ";
    private static final int THREE = 3;
    private static final int TWELVE = 12;
    private static final int TWO = 2;
    private static final String UNICODE = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171";

    String cleanName(String string2) {
        string2 = string2.toUpperCase(Locale.ENGLISH);
        String[] arrstring = new String[]{"\\-", "[&]", "\\'", "\\.", "[\\,]"};
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            string2 = string2.replaceAll(arrstring[i2], "");
        }
        return this.removeAccents(string2).replaceAll("\\s+", "");
    }

    @Override
    public final Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Match Rating Approach encoder is not of type java.lang.String");
        }
        return this.encode((String)object);
    }

    @Override
    public final String encode(String string2) {
        if (string2 == null || "".equalsIgnoreCase(string2) || " ".equalsIgnoreCase(string2) || string2.length() == 1) {
            return "";
        }
        return this.getFirst3Last3(this.removeDoubleConsonants(this.removeVowels(this.cleanName(string2))));
    }

    String getFirst3Last3(String string2) {
        int n2 = string2.length();
        String string3 = string2;
        if (n2 > 6) {
            string3 = string2.substring(0, 3);
            string2 = string2.substring(n2 - 3, n2);
            string3 = string3 + string2;
        }
        return string3;
    }

    int getMinRating(int n2) {
        if (n2 <= 4) {
            return 5;
        }
        if (n2 >= 5 && n2 <= 7) {
            return 4;
        }
        if (n2 >= 8 && n2 <= 11) {
            return 3;
        }
        if (n2 == 12) {
            return 2;
        }
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isEncodeEquals(String string2, String string3) {
        boolean bl2 = true;
        if (string2 == null) return false;
        if ("".equalsIgnoreCase(string2)) return false;
        if (" ".equalsIgnoreCase(string2)) {
            return false;
        }
        if (string3 == null) return false;
        if ("".equalsIgnoreCase(string3)) return false;
        if (" ".equalsIgnoreCase(string3)) return false;
        if (string2.length() == 1) return false;
        if (string3.length() == 1) return false;
        if (string2.equalsIgnoreCase(string3)) {
            return true;
        }
        string2 = this.cleanName(string2);
        string3 = this.cleanName(string3);
        string2 = this.removeVowels(string2);
        string3 = this.removeVowels(string3);
        string2 = this.removeDoubleConsonants(string2);
        string3 = this.removeDoubleConsonants(string3);
        string2 = this.getFirst3Last3(string2);
        string3 = this.getFirst3Last3(string3);
        if (Math.abs(string2.length() - string3.length()) >= 3) return false;
        int n2 = this.getMinRating(Math.abs(string2.length() + string3.length()));
        if (this.leftToRightThenRightToLeftProcessing(string2, string3) < n2) return false;
        return bl2;
    }

    boolean isVowel(String string2) {
        if (string2.equalsIgnoreCase("E") || string2.equalsIgnoreCase("A") || string2.equalsIgnoreCase("O") || string2.equalsIgnoreCase("I") || string2.equalsIgnoreCase("U")) {
            return true;
        }
        return false;
    }

    int leftToRightThenRightToLeftProcessing(String string2, String string3) {
        char[] arrc = string2.toCharArray();
        char[] arrc2 = string3.toCharArray();
        int n2 = string2.length() - 1;
        int n3 = string3.length() - 1;
        int n4 = 0;
        do {
            if (n4 >= arrc.length || n4 > n3) {
                string2 = new String(arrc).replaceAll("\\s+", "");
                string3 = new String(arrc2).replaceAll("\\s+", "");
                if (string2.length() <= string3.length()) break;
                return Math.abs(6 - string2.length());
            }
            String string4 = string2.substring(n4, n4 + 1);
            String string5 = string2.substring(n2 - n4, n2 - n4 + 1);
            String string6 = string3.substring(n4, n4 + 1);
            String string7 = string3.substring(n3 - n4, n3 - n4 + 1);
            if (string4.equals(string6)) {
                arrc[n4] = 32;
                arrc2[n4] = 32;
            }
            if (string5.equals(string7)) {
                arrc[n2 - n4] = 32;
                arrc2[n3 - n4] = 32;
            }
            ++n4;
        } while (true);
        return Math.abs(6 - string3.length());
    }

    /*
     * Enabled aggressive block sorting
     */
    String removeAccents(String string2) {
        if (string2 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = string2.length();
        int n3 = 0;
        while (n3 < n2) {
            char c2 = string2.charAt(n3);
            int n4 = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171".indexOf(c2);
            if (n4 > -1) {
                stringBuilder.append("AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu".charAt(n4));
            } else {
                stringBuilder.append(c2);
            }
            ++n3;
        }
        return stringBuilder.toString();
    }

    String removeDoubleConsonants(String string2) {
        string2 = string2.toUpperCase();
        for (String string3 : DOUBLE_CONSONANT) {
            String string4 = string2;
            if (string2.contains(string3)) {
                string4 = string2.replace(string3, string3.substring(0, 1));
            }
            string2 = string4;
        }
        return string2;
    }

    String removeVowels(String string2) {
        String string3;
        String string4 = string2.substring(0, 1);
        string2 = string3 = string2.replaceAll("A", "").replaceAll("E", "").replaceAll("I", "").replaceAll("O", "").replaceAll("U", "").replaceAll("\\s{2,}\\b", " ");
        if (this.isVowel(string4)) {
            string2 = string4 + string3;
        }
        return string2;
    }
}

