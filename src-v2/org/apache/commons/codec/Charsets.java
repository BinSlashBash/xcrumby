/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec;

import java.nio.charset.Charset;

public class Charsets {
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static Charset toCharset(String string2) {
        if (string2 == null) {
            return Charset.defaultCharset();
        }
        return Charset.forName(string2);
    }

    public static Charset toCharset(Charset charset) {
        Charset charset2 = charset;
        if (charset == null) {
            charset2 = Charset.defaultCharset();
        }
        return charset2;
    }
}

