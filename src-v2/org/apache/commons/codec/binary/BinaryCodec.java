/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

public class BinaryCodec
implements BinaryDecoder,
BinaryEncoder {
    private static final int[] BITS;
    private static final int BIT_0 = 1;
    private static final int BIT_1 = 2;
    private static final int BIT_2 = 4;
    private static final int BIT_3 = 8;
    private static final int BIT_4 = 16;
    private static final int BIT_5 = 32;
    private static final int BIT_6 = 64;
    private static final int BIT_7 = 128;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final char[] EMPTY_CHAR_ARRAY;

    static {
        EMPTY_CHAR_ARRAY = new char[0];
        EMPTY_BYTE_ARRAY = new byte[0];
        BITS = new int[]{1, 2, 4, 8, 16, 32, 64, 128};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static byte[] fromAscii(byte[] arrby) {
        if (BinaryCodec.isEmpty(arrby)) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] arrby2 = new byte[arrby.length >> 3];
        int n2 = 0;
        int n3 = arrby.length - 1;
        do {
            byte[] arrby3 = arrby2;
            if (n2 >= arrby2.length) return arrby3;
            for (int i2 = 0; i2 < BITS.length; ++i2) {
                if (arrby[n3 - i2] != 49) continue;
                arrby2[n2] = (byte)(arrby2[n2] | BITS[i2]);
            }
            ++n2;
            n3 -= 8;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static byte[] fromAscii(char[] arrc) {
        if (arrc == null) return EMPTY_BYTE_ARRAY;
        if (arrc.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] arrby = new byte[arrc.length >> 3];
        int n2 = 0;
        int n3 = arrc.length - 1;
        do {
            byte[] arrby2 = arrby;
            if (n2 >= arrby.length) return arrby2;
            for (int i2 = 0; i2 < BITS.length; ++i2) {
                if (arrc[n3 - i2] != '1') continue;
                arrby[n2] = (byte)(arrby[n2] | BITS[i2]);
            }
            ++n2;
            n3 -= 8;
        } while (true);
    }

    private static boolean isEmpty(byte[] arrby) {
        if (arrby == null || arrby.length == 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] toAsciiBytes(byte[] arrby) {
        if (BinaryCodec.isEmpty(arrby)) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] arrby2 = new byte[arrby.length << 3];
        int n2 = 0;
        int n3 = arrby2.length - 1;
        do {
            byte[] arrby3 = arrby2;
            if (n2 >= arrby.length) return arrby3;
            for (int i2 = 0; i2 < BITS.length; ++i2) {
                arrby2[n3 - i2] = (arrby[n2] & BITS[i2]) == 0 ? 48 : 49;
            }
            ++n2;
            n3 -= 8;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static char[] toAsciiChars(byte[] arrby) {
        if (BinaryCodec.isEmpty(arrby)) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] arrc = new char[arrby.length << 3];
        int n2 = 0;
        int n3 = arrc.length - 1;
        do {
            char[] arrc2 = arrc;
            if (n2 >= arrby.length) return arrc2;
            for (int i2 = 0; i2 < BITS.length; ++i2) {
                arrc[n3 - i2] = (arrby[n2] & BITS[i2]) == 0 ? 48 : 49;
            }
            ++n2;
            n3 -= 8;
        } while (true);
    }

    public static String toAsciiString(byte[] arrby) {
        return new String(BinaryCodec.toAsciiChars(arrby));
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object == null) {
            return EMPTY_BYTE_ARRAY;
        }
        if (object instanceof byte[]) {
            return BinaryCodec.fromAscii((byte[])object);
        }
        if (object instanceof char[]) {
            return BinaryCodec.fromAscii((char[])object);
        }
        if (object instanceof String) {
            return BinaryCodec.fromAscii(((String)object).toCharArray());
        }
        throw new DecoderException("argument not a byte array");
    }

    @Override
    public byte[] decode(byte[] arrby) {
        return BinaryCodec.fromAscii(arrby);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof byte[])) {
            throw new EncoderException("argument not a byte array");
        }
        return BinaryCodec.toAsciiChars((byte[])object);
    }

    @Override
    public byte[] encode(byte[] arrby) {
        return BinaryCodec.toAsciiBytes(arrby);
    }

    public byte[] toByteArray(String string2) {
        if (string2 == null) {
            return EMPTY_BYTE_ARRAY;
        }
        return BinaryCodec.fromAscii(string2.toCharArray());
    }
}

