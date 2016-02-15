/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import java.math.BigInteger;
import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.codec.binary.StringUtils;

public class Base64
extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
    private static final byte[] DECODE_TABLE;
    private static final int MASK_6BITS = 63;
    private static final byte[] STANDARD_ENCODE_TABLE;
    private static final byte[] URL_SAFE_ENCODE_TABLE;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    static {
        STANDARD_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        URL_SAFE_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
    }

    public Base64() {
        this(0);
    }

    public Base64(int n2) {
        this(n2, CHUNK_SEPARATOR);
    }

    public Base64(int n2, byte[] arrby) {
        this(n2, arrby, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Base64(int n2, byte[] object, boolean bl2) {
        int n3 = object == null ? 0 : object.length;
        super(3, 4, n2, n3);
        this.decodeTable = DECODE_TABLE;
        if (object != null) {
            if (this.containsAlphabetOrPad((byte[])object)) {
                object = StringUtils.newStringUtf8((byte[])object);
                throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + (String)object + "]");
            }
            if (n2 > 0) {
                this.encodeSize = object.length + 4;
                this.lineSeparator = new byte[object.length];
                System.arraycopy(object, 0, this.lineSeparator, 0, object.length);
            } else {
                this.encodeSize = 4;
                this.lineSeparator = null;
            }
        } else {
            this.encodeSize = 4;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        object = bl2 ? (Object)URL_SAFE_ENCODE_TABLE : (Object)STANDARD_ENCODE_TABLE;
        this.encodeTable = object;
    }

    public Base64(boolean bl2) {
        this(76, CHUNK_SEPARATOR, bl2);
    }

    public static byte[] decodeBase64(String string2) {
        return new Base64().decode(string2);
    }

    public static byte[] decodeBase64(byte[] arrby) {
        return new Base64().decode(arrby);
    }

    public static BigInteger decodeInteger(byte[] arrby) {
        return new BigInteger(1, Base64.decodeBase64(arrby));
    }

    public static byte[] encodeBase64(byte[] arrby) {
        return Base64.encodeBase64(arrby, false);
    }

    public static byte[] encodeBase64(byte[] arrby, boolean bl2) {
        return Base64.encodeBase64(arrby, bl2, false);
    }

    public static byte[] encodeBase64(byte[] arrby, boolean bl2, boolean bl3) {
        return Base64.encodeBase64(arrby, bl2, bl3, Integer.MAX_VALUE);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] encodeBase64(byte[] arrby, boolean bl2, boolean bl3, int n2) {
        if (arrby == null || arrby.length == 0) {
            return arrby;
        }
        Base64 base64 = bl2 ? new Base64(bl3) : new Base64(0, CHUNK_SEPARATOR, bl3);
        long l2 = base64.getEncodedLength(arrby);
        if (l2 > (long)n2) {
            throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + l2 + ") than the specified maximum size of " + n2);
        }
        return base64.encode(arrby);
    }

    public static byte[] encodeBase64Chunked(byte[] arrby) {
        return Base64.encodeBase64(arrby, true);
    }

    public static String encodeBase64String(byte[] arrby) {
        return StringUtils.newStringUtf8(Base64.encodeBase64(arrby, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] arrby) {
        return Base64.encodeBase64(arrby, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] arrby) {
        return StringUtils.newStringUtf8(Base64.encodeBase64(arrby, false, true));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new NullPointerException("encodeInteger called with null parameter");
        }
        return Base64.encodeBase64(Base64.toIntegerBytes(bigInteger), false);
    }

    @Deprecated
    public static boolean isArrayByteBase64(byte[] arrby) {
        return Base64.isBase64(arrby);
    }

    public static boolean isBase64(byte by2) {
        if (by2 == 61 || by2 >= 0 && by2 < DECODE_TABLE.length && DECODE_TABLE[by2] != -1) {
            return true;
        }
        return false;
    }

    public static boolean isBase64(String string2) {
        return Base64.isBase64(StringUtils.getBytesUtf8(string2));
    }

    public static boolean isBase64(byte[] arrby) {
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            if (Base64.isBase64(arrby[i2]) || Base64.isWhiteSpace(arrby[i2])) continue;
            return false;
        }
        return true;
    }

    static byte[] toIntegerBytes(BigInteger bigInteger) {
        int n2;
        int n3 = bigInteger.bitLength() + 7 >> 3 << 3;
        byte[] arrby = bigInteger.toByteArray();
        if (bigInteger.bitLength() % 8 != 0 && bigInteger.bitLength() / 8 + 1 == n3 / 8) {
            return arrby;
        }
        int n4 = 0;
        int n5 = n2 = arrby.length;
        if (bigInteger.bitLength() % 8 == 0) {
            n4 = 1;
            n5 = n2 - 1;
        }
        n2 = n3 / 8;
        bigInteger = (BigInteger)new byte[n3 / 8];
        System.arraycopy(arrby, n4, bigInteger, n2 - n5, n5);
        return bigInteger;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    void decode(byte[] var1_1, int var2_2, int var3_3, BaseNCodec.Context var4_4) {
        if (var4_4.eof) {
            return;
        }
        if (var3_3 < 0) {
            var4_4.eof = true;
        }
        var5_5 = 0;
        block5 : do {
            if (var5_5 >= var3_3) ** GOTO lbl12
            var7_7 = this.ensureBufferSize(this.decodeSize, var4_4);
            var6_6 = var1_1[var2_2];
            if (var6_6 != 61) ** GOTO lbl20
            var4_4.eof = true;
lbl12: // 2 sources:
            if (var4_4.eof == false) return;
            if (var4_4.modulus == 0) return;
            var1_1 = this.ensureBufferSize(this.decodeSize, var4_4);
            switch (var4_4.modulus) {
                case 1: {
                    return;
                }
                default: {
                    throw new IllegalStateException("Impossible modulus " + var4_4.modulus);
                }
lbl20: // 1 sources:
                if (var6_6 >= 0 && var6_6 < Base64.DECODE_TABLE.length && (var6_6 = Base64.DECODE_TABLE[var6_6]) >= 0) {
                    var4_4.modulus = (var4_4.modulus + 1) % 4;
                    var4_4.ibitWorkArea = (var4_4.ibitWorkArea << 6) + var6_6;
                    if (var4_4.modulus == 0) {
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.ibitWorkArea >> 16 & 255);
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.ibitWorkArea >> 8 & 255);
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.ibitWorkArea & 255);
                    }
                }
                ++var5_5;
                ++var2_2;
                continue block5;
                case 2: {
                    var4_4.ibitWorkArea >>= 4;
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.ibitWorkArea & 255);
                    return;
                }
                case 3: 
            }
            break;
        } while (true);
        var4_4.ibitWorkArea >>= 2;
        var2_2 = var4_4.pos;
        var4_4.pos = var2_2 + 1;
        var1_1[var2_2] = (byte)(var4_4.ibitWorkArea >> 8 & 255);
        var2_2 = var4_4.pos;
        var4_4.pos = var2_2 + 1;
        var1_1[var2_2] = (byte)(var4_4.ibitWorkArea & 255);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    void encode(byte[] arrby, int n2, int n3, BaseNCodec.Context context) {
        if (context.eof) {
            return;
        }
        if (n3 < 0) {
            context.eof = true;
            if (context.modulus == 0) {
                if (this.lineLength == 0) return;
            }
            arrby = this.ensureBufferSize(this.encodeSize, context);
            n2 = context.pos;
            switch (context.modulus) {
                default: {
                    throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
                case 1: {
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[context.ibitWorkArea >> 2 & 63];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[context.ibitWorkArea << 4 & 63];
                    if (this.encodeTable == STANDARD_ENCODE_TABLE) {
                        n3 = context.pos;
                        context.pos = n3 + 1;
                        arrby[n3] = 61;
                        n3 = context.pos;
                        context.pos = n3 + 1;
                        arrby[n3] = 61;
                    }
                }
                case 0: {
                    break;
                }
                case 2: {
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[context.ibitWorkArea >> 10 & 63];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[context.ibitWorkArea >> 4 & 63];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[context.ibitWorkArea << 2 & 63];
                    if (this.encodeTable != STANDARD_ENCODE_TABLE) break;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                }
            }
            context.currentLinePos += context.pos - n2;
            if (this.lineLength <= 0) return;
            if (context.currentLinePos <= 0) return;
            System.arraycopy(this.lineSeparator, 0, arrby, context.pos, this.lineSeparator.length);
            context.pos += this.lineSeparator.length;
            return;
        }
        int n4 = 0;
        while (n4 < n3) {
            int n5;
            byte[] arrby2 = this.ensureBufferSize(this.encodeSize, context);
            context.modulus = (context.modulus + 1) % 3;
            int n6 = n5 = arrby[n2];
            if (n5 < 0) {
                n6 = n5 + 256;
            }
            context.ibitWorkArea = (context.ibitWorkArea << 8) + n6;
            if (context.modulus == 0) {
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[context.ibitWorkArea >> 18 & 63];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[context.ibitWorkArea >> 12 & 63];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[context.ibitWorkArea >> 6 & 63];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[context.ibitWorkArea & 63];
                context.currentLinePos += 4;
                if (this.lineLength > 0 && this.lineLength <= context.currentLinePos) {
                    System.arraycopy(this.lineSeparator, 0, arrby2, context.pos, this.lineSeparator.length);
                    context.pos += this.lineSeparator.length;
                    context.currentLinePos = 0;
                }
            }
            ++n4;
            ++n2;
        }
    }

    @Override
    protected boolean isInAlphabet(byte by2) {
        if (by2 >= 0 && by2 < this.decodeTable.length && this.decodeTable[by2] != -1) {
            return true;
        }
        return false;
    }

    public boolean isUrlSafe() {
        if (this.encodeTable == URL_SAFE_ENCODE_TABLE) {
            return true;
        }
        return false;
    }
}

