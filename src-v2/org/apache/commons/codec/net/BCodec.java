/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.RFC1522Codec;

public class BCodec
extends RFC1522Codec
implements StringEncoder,
StringDecoder {
    private final Charset charset;

    public BCodec() {
        this(Charsets.UTF_8);
    }

    public BCodec(String string2) {
        this(Charset.forName(string2));
    }

    public BCodec(Charset charset) {
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
        throw new DecoderException("Objects of type " + object.getClass().getName() + " cannot be decoded using BCodec");
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

    @Override
    protected byte[] doDecoding(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return Base64.decodeBase64(arrby);
    }

    @Override
    protected byte[] doEncoding(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return Base64.encodeBase64(arrby);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return this.encode((String)object);
        }
        throw new EncoderException("Objects of type " + object.getClass().getName() + " cannot be encoded using BCodec");
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
        return "B";
    }
}

