/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.net.Utils;

public class URLCodec
implements BinaryEncoder,
BinaryDecoder,
StringEncoder,
StringDecoder {
    protected static final byte ESCAPE_CHAR = 37;
    static final int RADIX = 16;
    protected static final BitSet WWW_FORM_URL;
    @Deprecated
    protected String charset;

    static {
        int n2;
        WWW_FORM_URL = new BitSet(256);
        for (n2 = 97; n2 <= 122; ++n2) {
            WWW_FORM_URL.set(n2);
        }
        for (n2 = 65; n2 <= 90; ++n2) {
            WWW_FORM_URL.set(n2);
        }
        for (n2 = 48; n2 <= 57; ++n2) {
            WWW_FORM_URL.set(n2);
        }
        WWW_FORM_URL.set(45);
        WWW_FORM_URL.set(95);
        WWW_FORM_URL.set(46);
        WWW_FORM_URL.set(42);
        WWW_FORM_URL.set(32);
    }

    public URLCodec() {
        this("UTF-8");
    }

    public URLCodec(String string2) {
        this.charset = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final byte[] decodeUrl(byte[] arrby) throws DecoderException {
        if (arrby == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = 0;
        while (n2 < arrby.length) {
            int n3 = arrby[n2];
            if (n3 == 43) {
                byteArrayOutputStream.write(32);
            } else if (n3 == 37) {
                ++n2;
                try {
                    n3 = Utils.digit16(arrby[n2]);
                    byteArrayOutputStream.write((char)((n3 << 4) + Utils.digit16(arrby[++n2])));
                }
                catch (ArrayIndexOutOfBoundsException var0_1) {
                    throw new DecoderException("Invalid URL encoding: ", var0_1);
                }
            } else {
                byteArrayOutputStream.write(n3);
            }
            ++n2;
        }
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final byte[] encodeUrl(BitSet object, byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        Object object2 = object;
        if (object == null) {
            object2 = WWW_FORM_URL;
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
                n4 = n5;
                if (n5 == 32) {
                    n4 = 43;
                }
                object.write(n4);
            } else {
                object.write(37);
                n4 = Character.toUpperCase(Character.forDigit(n5 >> 4 & 15, 16));
                n5 = Character.toUpperCase(Character.forDigit(n5 & 15, 16));
                object.write(n4);
                object.write(n5);
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
        throw new DecoderException("Objects of type " + object.getClass().getName() + " cannot be URL decoded");
    }

    @Override
    public String decode(String string2) throws DecoderException {
        if (string2 == null) {
            return null;
        }
        try {
            string2 = this.decode(string2, this.getDefaultCharset());
            return string2;
        }
        catch (UnsupportedEncodingException var1_2) {
            throw new DecoderException(var1_2.getMessage(), var1_2);
        }
    }

    public String decode(String string2, String string3) throws DecoderException, UnsupportedEncodingException {
        if (string2 == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(string2)), string3);
    }

    @Override
    public byte[] decode(byte[] arrby) throws DecoderException {
        return URLCodec.decodeUrl(arrby);
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
        throw new EncoderException("Objects of type " + object.getClass().getName() + " cannot be URL encoded");
    }

    @Override
    public String encode(String string2) throws EncoderException {
        if (string2 == null) {
            return null;
        }
        try {
            string2 = this.encode(string2, this.getDefaultCharset());
            return string2;
        }
        catch (UnsupportedEncodingException var1_2) {
            throw new EncoderException(var1_2.getMessage(), var1_2);
        }
    }

    public String encode(String string2, String string3) throws UnsupportedEncodingException {
        if (string2 == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(string2.getBytes(string3)));
    }

    @Override
    public byte[] encode(byte[] arrby) {
        return URLCodec.encodeUrl(WWW_FORM_URL, arrby);
    }

    public String getDefaultCharset() {
        return this.charset;
    }

    @Deprecated
    public String getEncoding() {
        return this.charset;
    }
}

