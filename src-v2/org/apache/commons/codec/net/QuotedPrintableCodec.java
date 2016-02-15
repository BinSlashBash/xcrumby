/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.net.Utils;

public class QuotedPrintableCodec
implements BinaryEncoder,
BinaryDecoder,
StringEncoder,
StringDecoder {
    private static final byte ESCAPE_CHAR = 61;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte SPACE = 32;
    private static final byte TAB = 9;
    private final Charset charset;

    static {
        int n2;
        PRINTABLE_CHARS = new BitSet(256);
        for (n2 = 33; n2 <= 60; ++n2) {
            PRINTABLE_CHARS.set(n2);
        }
        for (n2 = 62; n2 <= 126; ++n2) {
            PRINTABLE_CHARS.set(n2);
        }
        PRINTABLE_CHARS.set(9);
        PRINTABLE_CHARS.set(32);
    }

    public QuotedPrintableCodec() {
        this(Charsets.UTF_8);
    }

    public QuotedPrintableCodec(String string2) throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException {
        this(Charset.forName(string2));
    }

    public QuotedPrintableCodec(Charset charset) {
        this.charset = charset;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final byte[] decodeQuotedPrintable(byte[] arrby) throws DecoderException {
        if (arrby == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = 0;
        while (n2 < arrby.length) {
            int n3 = arrby[n2];
            if (n3 == 61) {
                ++n2;
                try {
                    n3 = Utils.digit16(arrby[n2]);
                    byteArrayOutputStream.write((char)((n3 << 4) + Utils.digit16(arrby[++n2])));
                }
                catch (ArrayIndexOutOfBoundsException var0_1) {
                    throw new DecoderException("Invalid quoted-printable encoding", var0_1);
                }
            } else {
                byteArrayOutputStream.write(n3);
            }
            ++n2;
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static final void encodeQuotedPrintable(int n2, ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(61);
        char c2 = Character.toUpperCase(Character.forDigit(n2 >> 4 & 15, 16));
        n2 = Character.toUpperCase(Character.forDigit(n2 & 15, 16));
        byteArrayOutputStream.write(c2);
        byteArrayOutputStream.write(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final byte[] encodeQuotedPrintable(BitSet object, byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        Object object2 = object;
        if (object == null) {
            object2 = PRINTABLE_CHARS;
        }
        object = new ByteArrayOutputStream();
        int n2 = arrby.length;
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            int n5 = n4 = arrby[n3];
            if (n4 < 0) {
                n5 = n4 + 256;
            }
            if (object2.get(n5)) {
                object.write(n5);
            } else {
                QuotedPrintableCodec.encodeQuotedPrintable(n5, (ByteArrayOutputStream)object);
            }
            ++n3;
        }
        return object.toByteArray();
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof byte[]) {
            return this.decode((byte[])object);
        }
        if (object instanceof String) {
            return this.decode((String)object);
        }
        throw new DecoderException("Objects of type " + object.getClass().getName() + " cannot be quoted-printable decoded");
    }

    @Override
    public String decode(String string2) throws DecoderException {
        return this.decode(string2, this.getCharset());
    }

    public String decode(String string2, String string3) throws DecoderException, UnsupportedEncodingException {
        if (string2 == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(string2)), string3);
    }

    public String decode(String string2, Charset charset) throws DecoderException {
        if (string2 == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(string2)), charset);
    }

    @Override
    public byte[] decode(byte[] arrby) throws DecoderException {
        return QuotedPrintableCodec.decodeQuotedPrintable(arrby);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof byte[]) {
            return this.encode((byte[])object);
        }
        if (object instanceof String) {
            return this.encode((String)object);
        }
        throw new EncoderException("Objects of type " + object.getClass().getName() + " cannot be quoted-printable encoded");
    }

    @Override
    public String encode(String string2) throws EncoderException {
        return this.encode(string2, this.getCharset());
    }

    public String encode(String string2, String string3) throws UnsupportedEncodingException {
        if (string2 == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(string2.getBytes(string3)));
    }

    public String encode(String string2, Charset charset) {
        if (string2 == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(string2.getBytes(charset)));
    }

    @Override
    public byte[] encode(byte[] arrby) {
        return QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, arrby);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getDefaultCharset() {
        return this.charset.name();
    }
}

