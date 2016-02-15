/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.nio.charset.Charset;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

public class Hex
implements BinaryEncoder,
BinaryDecoder {
    public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final Charset charset;

    public Hex() {
        this.charset = DEFAULT_CHARSET;
    }

    public Hex(String string2) {
        this(Charset.forName(string2));
    }

    public Hex(Charset charset) {
        this.charset = charset;
    }

    public static byte[] decodeHex(char[] arrc) throws DecoderException {
        int n2 = arrc.length;
        if ((n2 & 1) != 0) {
            throw new DecoderException("Odd number of characters.");
        }
        byte[] arrby = new byte[n2 >> 1];
        int n3 = 0;
        int n4 = 0;
        while (n4 < n2) {
            int n5 = Hex.toDigit(arrc[n4], n4);
            int n6 = Hex.toDigit(arrc[n4], ++n4);
            ++n4;
            arrby[n3] = (byte)((n5 << 4 | n6) & 255);
            ++n3;
        }
        return arrby;
    }

    public static char[] encodeHex(byte[] arrby) {
        return Hex.encodeHex(arrby, true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static char[] encodeHex(byte[] arrby, boolean bl2) {
        char[] arrc;
        if (bl2) {
            arrc = DIGITS_LOWER;
            do {
                return Hex.encodeHex(arrby, arrc);
                break;
            } while (true);
        }
        arrc = DIGITS_UPPER;
        return Hex.encodeHex(arrby, arrc);
    }

    protected static char[] encodeHex(byte[] arrby, char[] arrc) {
        int n2 = arrby.length;
        char[] arrc2 = new char[n2 << 1];
        int n3 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            int n4 = n3 + 1;
            arrc2[n3] = arrc[(arrby[i2] & 240) >>> 4];
            n3 = n4 + 1;
            arrc2[n4] = arrc[arrby[i2] & 15];
        }
        return arrc2;
    }

    public static String encodeHexString(byte[] arrby) {
        return new String(Hex.encodeHex(arrby));
    }

    protected static int toDigit(char c2, int n2) throws DecoderException {
        int n3 = Character.digit(c2, 16);
        if (n3 == -1) {
            throw new DecoderException("Illegal hexadecimal character " + c2 + " at index " + n2);
        }
        return n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object decode(Object object) throws DecoderException {
        try {
            if (object instanceof String) {
                object = ((String)object).toCharArray();
                do {
                    return Hex.decodeHex((char[])object);
                    break;
                } while (true);
            }
            object = (char[])object;
            return Hex.decodeHex((char[])object);
        }
        catch (ClassCastException var1_2) {
            throw new DecoderException(var1_2.getMessage(), var1_2);
        }
    }

    @Override
    public byte[] decode(byte[] arrby) throws DecoderException {
        return Hex.decodeHex(new String(arrby, this.getCharset()).toCharArray());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object encode(Object object) throws EncoderException {
        try {
            if (object instanceof String) {
                object = ((String)object).getBytes(this.getCharset());
                do {
                    return Hex.encodeHex((byte[])object);
                    break;
                } while (true);
            }
            object = (byte[])object;
            return Hex.encodeHex((byte[])object);
        }
        catch (ClassCastException var1_2) {
            throw new EncoderException(var1_2.getMessage(), var1_2);
        }
    }

    @Override
    public byte[] encode(byte[] arrby) {
        return Hex.encodeHexString(arrby).getBytes(this.getCharset());
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getCharsetName() {
        return this.charset.name();
    }

    public String toString() {
        return super.toString() + "[charsetName=" + this.charset + "]";
    }
}

