/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

abstract class RFC1522Codec {
    protected static final String POSTFIX = "?=";
    protected static final String PREFIX = "=?";
    protected static final char SEP = '?';

    RFC1522Codec() {
    }

    protected String decodeText(String string2) throws DecoderException, UnsupportedEncodingException {
        int n2;
        if (string2 == null) {
            return null;
        }
        if (!string2.startsWith("=?") || !string2.endsWith("?=")) {
            throw new DecoderException("RFC 1522 violation: malformed encoded content");
        }
        int n3 = string2.length() - 2;
        int n4 = string2.indexOf(63, 2);
        if (n4 == n3) {
            throw new DecoderException("RFC 1522 violation: charset token not found");
        }
        String string3 = string2.substring(2, n4);
        if (string3.equals("")) {
            throw new DecoderException("RFC 1522 violation: charset not specified");
        }
        if ((n2 = string2.indexOf(63, ++n4)) == n3) {
            throw new DecoderException("RFC 1522 violation: encoding token not found");
        }
        String string4 = string2.substring(n4, n2);
        if (!this.getEncoding().equalsIgnoreCase(string4)) {
            throw new DecoderException("This codec cannot decode " + string4 + " encoded content");
        }
        n3 = n2 + 1;
        return new String(this.doDecoding(StringUtils.getBytesUsAscii(string2.substring(n3, string2.indexOf(63, n3)))), string3);
    }

    protected abstract byte[] doDecoding(byte[] var1) throws DecoderException;

    protected abstract byte[] doEncoding(byte[] var1) throws EncoderException;

    protected String encodeText(String string2, String string3) throws EncoderException, UnsupportedEncodingException {
        if (string2 == null) {
            return null;
        }
        return this.encodeText(string2, Charset.forName(string3));
    }

    protected String encodeText(String string2, Charset charset) throws EncoderException {
        if (string2 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=?");
        stringBuilder.append(charset);
        stringBuilder.append('?');
        stringBuilder.append(this.getEncoding());
        stringBuilder.append('?');
        stringBuilder.append(StringUtils.newStringUsAscii(this.doEncoding(string2.getBytes(charset))));
        stringBuilder.append("?=");
        return stringBuilder.toString();
    }

    protected abstract String getEncoding();
}

