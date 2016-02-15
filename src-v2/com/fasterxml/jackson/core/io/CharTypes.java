/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import java.util.Arrays;

public final class CharTypes {
    private static final byte[] HB;
    private static final char[] HC;
    static final int[] sHexValues;
    static final int[] sInputCodes;
    static final int[] sInputCodesComment;
    static final int[] sInputCodesJsNames;
    static final int[] sInputCodesUTF8;
    static final int[] sInputCodesUtf8JsNames;
    static final int[] sInputCodesWS;
    static final int[] sOutputEscapes128;

    /*
     * Enabled aggressive block sorting
     */
    static {
        int n2;
        HC = "0123456789ABCDEF".toCharArray();
        int n3 = HC.length;
        HB = new byte[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            CharTypes.HB[n2] = (byte)HC[n2];
        }
        int[] arrn = new int[256];
        for (n2 = 0; n2 < 32; ++n2) {
            arrn[n2] = -1;
        }
        arrn[34] = 1;
        arrn[92] = 1;
        sInputCodes = arrn;
        arrn = new int[sInputCodes.length];
        System.arraycopy(sInputCodes, 0, arrn, 0, arrn.length);
        for (n3 = 128; n3 < 256; ++n3) {
            n2 = (n3 & 224) == 192 ? 2 : ((n3 & 240) == 224 ? 3 : ((n3 & 248) == 240 ? 4 : -1));
            arrn[n3] = n2;
        }
        sInputCodesUTF8 = arrn;
        arrn = new int[256];
        Arrays.fill(arrn, -1);
        for (n2 = 33; n2 < 256; ++n2) {
            if (!Character.isJavaIdentifierPart((char)n2)) continue;
            arrn[n2] = 0;
        }
        arrn[64] = 0;
        arrn[35] = 0;
        arrn[42] = 0;
        arrn[45] = 0;
        arrn[43] = 0;
        sInputCodesJsNames = arrn;
        arrn = new int[256];
        System.arraycopy(sInputCodesJsNames, 0, arrn, 0, arrn.length);
        Arrays.fill(arrn, 128, 128, 0);
        sInputCodesUtf8JsNames = arrn;
        arrn = new int[256];
        System.arraycopy(sInputCodesUTF8, 128, arrn, 128, 128);
        Arrays.fill(arrn, 0, 32, -1);
        arrn[9] = 0;
        arrn[10] = 10;
        arrn[13] = 13;
        arrn[42] = 42;
        sInputCodesComment = arrn;
        arrn = new int[256];
        System.arraycopy(sInputCodesUTF8, 128, arrn, 128, 128);
        Arrays.fill(arrn, 0, 32, -1);
        arrn[32] = 1;
        arrn[9] = 1;
        arrn[10] = 10;
        arrn[13] = 13;
        arrn[47] = 47;
        arrn[35] = 35;
        sInputCodesWS = arrn;
        arrn = new int[128];
        for (n2 = 0; n2 < 32; ++n2) {
            arrn[n2] = -1;
        }
        arrn[34] = 34;
        arrn[92] = 92;
        arrn[8] = 98;
        arrn[9] = 116;
        arrn[12] = 102;
        arrn[10] = 110;
        arrn[13] = 114;
        sOutputEscapes128 = arrn;
        sHexValues = new int[128];
        Arrays.fill(sHexValues, -1);
        n2 = 0;
        while (n2 < 10) {
            CharTypes.sHexValues[n2 + 48] = n2++;
        }
        n2 = 0;
        while (n2 < 6) {
            CharTypes.sHexValues[n2 + 97] = n2 + 10;
            CharTypes.sHexValues[n2 + 65] = n2 + 10;
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void appendQuoted(StringBuilder stringBuilder, String string2) {
        int[] arrn = sOutputEscapes128;
        int n2 = arrn.length;
        int n3 = 0;
        int n4 = string2.length();
        while (n3 < n4) {
            char c2 = string2.charAt(n3);
            if (c2 >= n2 || arrn[c2] == 0) {
                stringBuilder.append(c2);
            } else {
                stringBuilder.append('\\');
                int n5 = arrn[c2];
                if (n5 < 0) {
                    stringBuilder.append('u');
                    stringBuilder.append('0');
                    stringBuilder.append('0');
                    stringBuilder.append(HC[c2 >> 4]);
                    stringBuilder.append(HC[c2 & 15]);
                } else {
                    stringBuilder.append((char)n5);
                }
            }
            ++n3;
        }
        return;
    }

    public static int charToHex(int n2) {
        if (n2 > 127) {
            return -1;
        }
        return sHexValues[n2];
    }

    public static byte[] copyHexBytes() {
        return (byte[])HB.clone();
    }

    public static char[] copyHexChars() {
        return (char[])HC.clone();
    }

    public static int[] get7BitOutputEscapes() {
        return sOutputEscapes128;
    }

    public static int[] getInputCodeComment() {
        return sInputCodesComment;
    }

    public static int[] getInputCodeLatin1() {
        return sInputCodes;
    }

    public static int[] getInputCodeLatin1JsNames() {
        return sInputCodesJsNames;
    }

    public static int[] getInputCodeUtf8() {
        return sInputCodesUTF8;
    }

    public static int[] getInputCodeUtf8JsNames() {
        return sInputCodesUtf8JsNames;
    }

    public static int[] getInputCodeWS() {
        return sInputCodesWS;
    }
}

