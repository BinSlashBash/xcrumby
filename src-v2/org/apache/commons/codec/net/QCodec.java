/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.BitSet;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.codec.net.RFC1522Codec;

public class QCodec
extends RFC1522Codec
implements StringEncoder,
StringDecoder {
    private static final byte BLANK = 32;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte UNDERSCORE = 95;
    private final Charset charset;
    private boolean encodeBlanks = false;

    static {
        int n2;
        PRINTABLE_CHARS = new BitSet(256);
        PRINTABLE_CHARS.set(32);
        PRINTABLE_CHARS.set(33);
        PRINTABLE_CHARS.set(34);
        PRINTABLE_CHARS.set(35);
        PRINTABLE_CHARS.set(36);
        PRINTABLE_CHARS.set(37);
        PRINTABLE_CHARS.set(38);
        PRINTABLE_CHARS.set(39);
        PRINTABLE_CHARS.set(40);
        PRINTABLE_CHARS.set(41);
        PRINTABLE_CHARS.set(42);
        PRINTABLE_CHARS.set(43);
        PRINTABLE_CHARS.set(44);
        PRINTABLE_CHARS.set(45);
        PRINTABLE_CHARS.set(46);
        PRINTABLE_CHARS.set(47);
        for (n2 = 48; n2 <= 57; ++n2) {
            PRINTABLE_CHARS.set(n2);
        }
        PRINTABLE_CHARS.set(58);
        PRINTABLE_CHARS.set(59);
        PRINTABLE_CHARS.set(60);
        PRINTABLE_CHARS.set(62);
        PRINTABLE_CHARS.set(64);
        for (n2 = 65; n2 <= 90; ++n2) {
            PRINTABLE_CHARS.set(n2);
        }
        PRINTABLE_CHARS.set(91);
        PRINTABLE_CHARS.set(92);
        PRINTABLE_CHARS.set(93);
        PRINTABLE_CHARS.set(94);
        PRINTABLE_CHARS.set(96);
        for (n2 = 97; n2 <= 122; ++n2) {
            PRINTABLE_CHARS.set(n2);
        }
        PRINTABLE_CHARS.set(123);
        PRINTABLE_CHARS.set(124);
        PRINTABLE_CHARS.set(125);
        PRINTABLE_CHARS.set(126);
    }

    public QCodec() {
        this(Charsets.UTF_8);
    }

    public QCodec(String string2) {
        this(Charset.forName(string2));
    }

    public QCodec(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return this.decode((String)object);
        }
        throw new DecoderException("Objects of type " + object.getClass().getName() + " cannot be decoded using Q codec");
    }

    @Override
    public String decode(String string2) throws DecoderException {
        if (string2 == null) {
            return null;
        }
        try {
            string2 = this.decodeText(string2);
            return string2;
        }
        catch (UnsupportedEncodingException var1_2) {
            throw new DecoderException(var1_2.getMessage(), var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected byte[] doDecoding(byte[] arrby) throws DecoderException {
        boolean bl2;
        if (arrby == null) {
            return null;
        }
        boolean bl3 = false;
        int n2 = arrby.length;
        int n3 = 0;
        do {
            bl2 = bl3;
            if (n3 >= n2) break;
            if (arrby[n3] == 95) {
                bl2 = true;
                break;
            }
            ++n3;
        } while (true);
        if (!bl2) return QuotedPrintableCodec.decodeQuotedPrintable(arrby);
        byte[] arrby2 = new byte[arrby.length];
        n3 = 0;
        while (n3 < arrby.length) {
            int n4 = arrby[n3];
            arrby2[n3] = n4 != 95 ? n4 : 32;
            ++n3;
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(arrby2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected byte[] doEncoding(byte[] object) {
        if (object == null) {
            return null;
        }
        byte[] arrby = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, (byte[])object);
        object = arrby;
        if (!this.encodeBlanks) return object;
        int n2 = 0;
        do {
            object = arrby;
            if (n2 >= arrby.length) return object;
            if (arrby[n2] == 32) {
                arrby[n2] = 95;
            }
            ++n2;
        } while (true);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return this.encode((String)object);
        }
        throw new EncoderException("Objects of type " + object.getClass().getName() + " cannot be encoded using Q codec");
    }

    @Override
    public String encode(String string2) throws EncoderException {
        if (string2 == null) {
            return null;
        }
        return this.encode(string2, this.getCharset());
    }

    public String encode(String string2, String string3) throws EncoderException {
        if (string2 == null) {
            return null;
        }
        try {
            string2 = this.encodeText(string2, string3);
            return string2;
        }
        catch (UnsupportedEncodingException var1_2) {
            throw new EncoderException(var1_2.getMessage(), var1_2);
        }
    }

    public String encode(String string2, Charset charset) throws EncoderException {
        if (string2 == null) {
            return null;
        }
        return this.encodeText(string2, charset);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getDefaultCharset() {
        return this.charset.name();
    }

    @Override
    protected String getEncoding() {
        return "Q";
    }

    public boolean isEncodeBlanks() {
        return this.encodeBlanks;
    }

    public void setEncodeBlanks(boolean bl2) {
        this.encodeBlanks = bl2;
    }
}

