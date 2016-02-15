/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.codec.binary.StringUtils;

public class Base32
extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 5;
    private static final int BYTES_PER_ENCODED_BLOCK = 8;
    private static final int BYTES_PER_UNENCODED_BLOCK = 5;
    private static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
    private static final byte[] DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    private static final byte[] ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 50, 51, 52, 53, 54, 55};
    private static final byte[] HEX_DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
    private static final byte[] HEX_ENCODE_TABLE = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86};
    private static final int MASK_5BITS = 31;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    public Base32() {
        this(false);
    }

    public Base32(int n2) {
        this(n2, CHUNK_SEPARATOR);
    }

    public Base32(int n2, byte[] arrby) {
        this(n2, arrby, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Base32(int n2, byte[] object, boolean bl2) {
        int n3 = object == null ? 0 : object.length;
        super(5, 8, n2, n3);
        if (bl2) {
            this.encodeTable = HEX_ENCODE_TABLE;
            this.decodeTable = HEX_DECODE_TABLE;
        } else {
            this.encodeTable = ENCODE_TABLE;
            this.decodeTable = DECODE_TABLE;
        }
        if (n2 > 0) {
            if (object == null) {
                throw new IllegalArgumentException("lineLength " + n2 + " > 0, but lineSeparator is null");
            }
            if (this.containsAlphabetOrPad((byte[])object)) {
                object = StringUtils.newStringUtf8((byte[])object);
                throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + (String)object + "]");
            }
            this.encodeSize = object.length + 8;
            this.lineSeparator = new byte[object.length];
            System.arraycopy(object, 0, this.lineSeparator, 0, object.length);
        } else {
            this.encodeSize = 8;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
    }

    public Base32(boolean bl2) {
        this(0, null, bl2);
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
        block8 : do {
            if (var5_5 >= var3_3) ** GOTO lbl11
            var6_6 = var1_1[var2_2];
            if (var6_6 != 61) ** GOTO lbl17
            var4_4.eof = true;
lbl11: // 2 sources:
            if (var4_4.eof == false) return;
            if (var4_4.modulus < 2) return;
            var1_1 = this.ensureBufferSize(this.decodeSize, var4_4);
            switch (var4_4.modulus) {
                default: {
                    throw new IllegalStateException("Impossible modulus " + var4_4.modulus);
                }
lbl17: // 1 sources:
                var7_7 = this.ensureBufferSize(this.decodeSize, var4_4);
                if (var6_6 >= 0 && var6_6 < this.decodeTable.length && (var6_6 = this.decodeTable[var6_6]) >= 0) {
                    var4_4.modulus = (var4_4.modulus + 1) % 8;
                    var4_4.lbitWorkArea = (var4_4.lbitWorkArea << 5) + (long)var6_6;
                    if (var4_4.modulus == 0) {
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.lbitWorkArea >> 32 & 255);
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.lbitWorkArea >> 24 & 255);
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.lbitWorkArea >> 16 & 255);
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.lbitWorkArea >> 8 & 255);
                        var6_6 = var4_4.pos;
                        var4_4.pos = var6_6 + 1;
                        var7_7[var6_6] = (byte)(var4_4.lbitWorkArea & 255);
                    }
                }
                ++var5_5;
                ++var2_2;
                continue block8;
                case 2: {
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 2 & 255);
                    return;
                }
                case 3: {
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 7 & 255);
                    return;
                }
                case 4: {
                    var4_4.lbitWorkArea >>= 4;
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 8 & 255);
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea & 255);
                    return;
                }
                case 5: {
                    var4_4.lbitWorkArea >>= 1;
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 16 & 255);
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 8 & 255);
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea & 255);
                    return;
                }
                case 6: {
                    var4_4.lbitWorkArea >>= 6;
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 16 & 255);
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 8 & 255);
                    var2_2 = var4_4.pos;
                    var4_4.pos = var2_2 + 1;
                    var1_1[var2_2] = (byte)(var4_4.lbitWorkArea & 255);
                    return;
                }
                case 7: 
            }
            break;
        } while (true);
        var4_4.lbitWorkArea >>= 3;
        var2_2 = var4_4.pos;
        var4_4.pos = var2_2 + 1;
        var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 24 & 255);
        var2_2 = var4_4.pos;
        var4_4.pos = var2_2 + 1;
        var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 16 & 255);
        var2_2 = var4_4.pos;
        var4_4.pos = var2_2 + 1;
        var1_1[var2_2] = (byte)(var4_4.lbitWorkArea >> 8 & 255);
        var2_2 = var4_4.pos;
        var4_4.pos = var2_2 + 1;
        var1_1[var2_2] = (byte)(var4_4.lbitWorkArea & 255);
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
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 3) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea << 2) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    break;
                }
                case 2: {
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 11) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 6) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 1) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea << 4) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    break;
                }
                case 3: {
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 19) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 14) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 9) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 4) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea << 1) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = 61;
                }
                case 0: {
                    break;
                }
                case 4: {
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 27) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 22) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 17) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 12) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 7) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea >> 2) & 31];
                    n3 = context.pos;
                    context.pos = n3 + 1;
                    arrby[n3] = this.encodeTable[(int)(context.lbitWorkArea << 3) & 31];
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
            context.modulus = (context.modulus + 1) % 5;
            int n6 = n5 = arrby[n2];
            if (n5 < 0) {
                n6 = n5 + 256;
            }
            context.lbitWorkArea = (context.lbitWorkArea << 8) + (long)n6;
            if (context.modulus == 0) {
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)(context.lbitWorkArea >> 35) & 31];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)(context.lbitWorkArea >> 30) & 31];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)(context.lbitWorkArea >> 25) & 31];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)(context.lbitWorkArea >> 20) & 31];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)(context.lbitWorkArea >> 15) & 31];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)(context.lbitWorkArea >> 10) & 31];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)(context.lbitWorkArea >> 5) & 31];
                n6 = context.pos;
                context.pos = n6 + 1;
                arrby2[n6] = this.encodeTable[(int)context.lbitWorkArea & 31];
                context.currentLinePos += 8;
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
    public boolean isInAlphabet(byte by2) {
        if (by2 >= 0 && by2 < this.decodeTable.length && this.decodeTable[by2] != -1) {
            return true;
        }
        return false;
    }
}

