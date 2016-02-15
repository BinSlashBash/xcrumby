/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.SoundexUtils;

public class Nysiis
implements StringEncoder {
    private static final char[] CHARS_A = new char[]{'A'};
    private static final char[] CHARS_AF = new char[]{'A', 'F'};
    private static final char[] CHARS_C = new char[]{'C'};
    private static final char[] CHARS_FF = new char[]{'F', 'F'};
    private static final char[] CHARS_G = new char[]{'G'};
    private static final char[] CHARS_N = new char[]{'N'};
    private static final char[] CHARS_NN = new char[]{'N', 'N'};
    private static final char[] CHARS_S = new char[]{'S'};
    private static final char[] CHARS_SSS = new char[]{'S', 'S', 'S'};
    private static final Pattern PAT_DT_ETC;
    private static final Pattern PAT_EE_IE;
    private static final Pattern PAT_K;
    private static final Pattern PAT_KN;
    private static final Pattern PAT_MAC;
    private static final Pattern PAT_PH_PF;
    private static final Pattern PAT_SCH;
    private static final char SPACE = ' ';
    private static final int TRUE_LENGTH = 6;
    private final boolean strict;

    static {
        PAT_MAC = Pattern.compile("^MAC");
        PAT_KN = Pattern.compile("^KN");
        PAT_K = Pattern.compile("^K");
        PAT_PH_PF = Pattern.compile("^(PH|PF)");
        PAT_SCH = Pattern.compile("^SCH");
        PAT_EE_IE = Pattern.compile("(EE|IE)$");
        PAT_DT_ETC = Pattern.compile("(DT|RT|RD|NT|ND)$");
    }

    public Nysiis() {
        this(true);
    }

    public Nysiis(boolean bl2) {
        this.strict = bl2;
    }

    private static boolean isVowel(char c2) {
        if (c2 == 'A' || c2 == 'E' || c2 == 'I' || c2 == 'O' || c2 == 'U') {
            return true;
        }
        return false;
    }

    private static char[] transcodeRemaining(char c2, char c3, char c4, char c5) {
        if (c3 == 'E' && c4 == 'V') {
            return CHARS_AF;
        }
        if (Nysiis.isVowel(c3)) {
            return CHARS_A;
        }
        if (c3 == 'Q') {
            return CHARS_G;
        }
        if (c3 == 'Z') {
            return CHARS_S;
        }
        if (c3 == 'M') {
            return CHARS_N;
        }
        if (c3 == 'K') {
            if (c4 == 'N') {
                return CHARS_NN;
            }
            return CHARS_C;
        }
        if (c3 == 'S' && c4 == 'C' && c5 == 'H') {
            return CHARS_SSS;
        }
        if (c3 == 'P' && c4 == 'H') {
            return CHARS_FF;
        }
        if (!(c3 != 'H' || Nysiis.isVowel(c2) && Nysiis.isVowel(c4))) {
            return new char[]{c2};
        }
        if (c3 == 'W' && Nysiis.isVowel(c2)) {
            return new char[]{c2};
        }
        return new char[]{c3};
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Nysiis encode is not of type java.lang.String");
        }
        return this.nysiis((String)object);
    }

    @Override
    public String encode(String string2) {
        return this.nysiis(string2);
    }

    public boolean isStrict() {
        return this.strict;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String nysiis(String charSequence) {
        int n2;
        void var1_3;
        if (charSequence == null) {
            return var1_3;
        }
        String string2 = SoundexUtils.clean(charSequence);
        if (string2.length() == 0) {
            return string2;
        }
        String string3 = PAT_MAC.matcher(string2).replaceFirst("MCC");
        String string4 = PAT_KN.matcher(string3).replaceFirst("NN");
        String string5 = PAT_K.matcher(string4).replaceFirst("C");
        String string6 = PAT_PH_PF.matcher(string5).replaceFirst("FF");
        String string7 = PAT_SCH.matcher(string6).replaceFirst("SSS");
        String string8 = PAT_EE_IE.matcher(string7).replaceFirst("Y");
        String string9 = PAT_DT_ETC.matcher(string8).replaceFirst("D");
        StringBuilder stringBuilder = new StringBuilder(string9.length());
        stringBuilder.append(string9.charAt(0));
        string9 = (String)string9.toCharArray();
        int n3 = string9.length;
        for (n2 = 1; n2 < n3; ++n2) {
            Object object = n2 < n3 - 1 ? (Object)string9[n2 + 1] : 32;
            Object object2 = n2 < n3 - 2 ? (Object)string9[n2 + 2] : 32;
            char[] arrc = Nysiis.transcodeRemaining((char)string9[n2 - 1], (char)string9[n2], (char)object, (char)object2);
            System.arraycopy(arrc, 0, string9, n2, arrc.length);
            if (string9[n2] == string9[n2 - 1]) continue;
            stringBuilder.append((char)string9[n2]);
        }
        if (stringBuilder.length() > 1) {
            n2 = n3 = (int)stringBuilder.charAt(stringBuilder.length() - 1);
            if (n3 == 83) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                n2 = stringBuilder.charAt(stringBuilder.length() - 1);
            }
            if (stringBuilder.length() > 2 && stringBuilder.charAt(stringBuilder.length() - 2) == 'A' && n2 == 89) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 2);
            }
            if (n2 == 65) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
        String string10 = string9 = stringBuilder.toString();
        if (!this.isStrict()) {
            return var1_3;
        }
        return string9.substring(0, Math.min(6, string9.length()));
    }
}

