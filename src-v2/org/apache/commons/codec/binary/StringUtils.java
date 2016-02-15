/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;

public class StringUtils {
    private static byte[] getBytes(String string2, Charset charset) {
        if (string2 == null) {
            return null;
        }
        return string2.getBytes(charset);
    }

    public static byte[] getBytesIso8859_1(String string2) {
        return StringUtils.getBytes(string2, Charsets.ISO_8859_1);
    }

    public static byte[] getBytesUnchecked(String string2, String string3) {
        if (string2 == null) {
            return null;
        }
        try {
            string2 = (String)string2.getBytes(string3);
            return string2;
        }
        catch (UnsupportedEncodingException var0_1) {
            throw StringUtils.newIllegalStateException(string3, var0_1);
        }
    }

    public static byte[] getBytesUsAscii(String string2) {
        return StringUtils.getBytes(string2, Charsets.US_ASCII);
    }

    public static byte[] getBytesUtf16(String string2) {
        return StringUtils.getBytes(string2, Charsets.UTF_16);
    }

    public static byte[] getBytesUtf16Be(String string2) {
        return StringUtils.getBytes(string2, Charsets.UTF_16BE);
    }

    public static byte[] getBytesUtf16Le(String string2) {
        return StringUtils.getBytes(string2, Charsets.UTF_16LE);
    }

    public static byte[] getBytesUtf8(String string2) {
        return StringUtils.getBytes(string2, Charsets.UTF_8);
    }

    private static IllegalStateException newIllegalStateException(String string2, UnsupportedEncodingException unsupportedEncodingException) {
        return new IllegalStateException(string2 + ": " + unsupportedEncodingException);
    }

    public static String newString(byte[] object, String string2) {
        if (object == null) {
            return null;
        }
        try {
            object = new String((byte[])object, string2);
            return object;
        }
        catch (UnsupportedEncodingException var0_1) {
            throw StringUtils.newIllegalStateException(string2, var0_1);
        }
    }

    private static String newString(byte[] arrby, Charset charset) {
        if (arrby == null) {
            return null;
        }
        return new String(arrby, charset);
    }

    public static String newStringIso8859_1(byte[] arrby) {
        return new String(arrby, Charsets.ISO_8859_1);
    }

    public static String newStringUsAscii(byte[] arrby) {
        return new String(arrby, Charsets.US_ASCII);
    }

    public static String newStringUtf16(byte[] arrby) {
        return new String(arrby, Charsets.UTF_16);
    }

    public static String newStringUtf16Be(byte[] arrby) {
        return new String(arrby, Charsets.UTF_16BE);
    }

    public static String newStringUtf16Le(byte[] arrby) {
        return new String(arrby, Charsets.UTF_16LE);
    }

    public static String newStringUtf8(byte[] arrby) {
        return StringUtils.newString(arrby, Charsets.UTF_8);
    }
}

