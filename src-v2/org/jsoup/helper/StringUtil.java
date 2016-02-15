/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.helper;

import java.util.Collection;
import java.util.Iterator;

public final class StringUtil {
    private static final String[] padding = new String[]{"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          "};

    public static /* varargs */ boolean in(String string2, String ... arrstring) {
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (!arrstring[i2].equals(string2)) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isBlank(String string2) {
        if (string2 == null || string2.length() == 0) {
            return true;
        }
        int n2 = string2.length();
        int n3 = 0;
        while (n3 < n2) {
            if (!StringUtil.isWhitespace(string2.codePointAt(n3))) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static boolean isNumeric(String string2) {
        if (string2 == null) return false;
        if (string2.length() == 0) {
            return false;
        }
        int n2 = string2.length();
        int n3 = 0;
        while (n3 < n2) {
            if (!Character.isDigit(string2.codePointAt(n3))) return false;
            ++n3;
        }
        return true;
    }

    public static boolean isWhitespace(int n2) {
        if (n2 == 32 || n2 == 9 || n2 == 10 || n2 == 12 || n2 == 13) {
            return true;
        }
        return false;
    }

    public static String join(Collection collection, String string2) {
        return StringUtil.join(collection.iterator(), string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String join(Iterator iterator, String string2) {
        String string3;
        void var2_3;
        if (!iterator.hasNext()) {
            return var2_3;
        }
        String string4 = string3 = iterator.next().toString();
        if (!iterator.hasNext()) {
            return var2_3;
        }
        StringBuilder stringBuilder = new StringBuilder(64).append(string3);
        while (iterator.hasNext()) {
            stringBuilder.append(string2);
            stringBuilder.append(iterator.next());
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String normaliseWhitespace(String string2) {
        int n2;
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        boolean bl2 = false;
        boolean bl3 = false;
        int n3 = string2.length();
        for (int i2 = 0; i2 < n3; i2 += Character.charCount((int)n2)) {
            n2 = string2.codePointAt(i2);
            if (StringUtil.isWhitespace(n2)) {
                if (bl2) {
                    bl3 = true;
                    continue;
                }
                if (n2 != 32) {
                    bl3 = true;
                }
                stringBuilder.append(' ');
                bl2 = true;
                continue;
            }
            stringBuilder.appendCodePoint(n2);
            bl2 = false;
        }
        if (!bl3) return string2;
        return stringBuilder.toString();
    }

    public static String padding(int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("width must be > 0");
        }
        if (n2 < padding.length) {
            return padding[n2];
        }
        char[] arrc = new char[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            arrc[i2] = 32;
        }
        return String.valueOf(arrc);
    }
}

