/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.lang.ref.SoftReference;

public final class JsonStringEncoder {
    private static final byte[] HB;
    private static final char[] HC;
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder;
    protected ByteArrayBuilder _bytes;
    protected final char[] _qbuf = new char[6];
    protected TextBuffer _text;

    static {
        HC = CharTypes.copyHexChars();
        HB = CharTypes.copyHexBytes();
        _threadEncoder = new ThreadLocal();
    }

    public JsonStringEncoder() {
        this._qbuf[0] = 92;
        this._qbuf[2] = 48;
        this._qbuf[3] = 48;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int _appendByte(int n2, int n3, ByteArrayBuilder byteArrayBuilder, int n4) {
        byteArrayBuilder.setCurrentSegmentLength(n4);
        byteArrayBuilder.append(92);
        if (n3 >= 0) {
            byteArrayBuilder.append((byte)n3);
            return byteArrayBuilder.getCurrentSegmentLength();
        }
        byteArrayBuilder.append(117);
        if (n2 > 255) {
            n3 = n2 >> 8;
            byteArrayBuilder.append(HB[n3 >> 4]);
            byteArrayBuilder.append(HB[n3 & 15]);
            n2 &= 255;
        } else {
            byteArrayBuilder.append(48);
            byteArrayBuilder.append(48);
        }
        byteArrayBuilder.append(HB[n2 >> 4]);
        byteArrayBuilder.append(HB[n2 & 15]);
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    private int _appendNamed(int n2, char[] arrc) {
        arrc[1] = (char)n2;
        return 2;
    }

    private int _appendNumeric(int n2, char[] arrc) {
        arrc[1] = 117;
        arrc[4] = HC[n2 >> 4];
        arrc[5] = HC[n2 & 15];
        return 6;
    }

    private static int _convert(int n2, int n3) {
        if (n3 < 56320 || n3 > 57343) {
            throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(n2) + ", second 0x" + Integer.toHexString(n3) + "; illegal combination");
        }
        return 65536 + (n2 - 55296 << 10) + (n3 - 56320);
    }

    private static void _illegal(int n2) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JsonStringEncoder getInstance() {
        SoftReference<JsonStringEncoder> softReference = _threadEncoder.get();
        softReference = softReference == null ? null : softReference.get();
        Object object = softReference;
        if (softReference == null) {
            object = new JsonStringEncoder();
            _threadEncoder.set(new SoftReference<Object>(object));
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] encodeAsUTF8(String string2) {
        ByteArrayBuilder byteArrayBuilder;
        ByteArrayBuilder byteArrayBuilder2 = byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            this._bytes = byteArrayBuilder2 = new ByteArrayBuilder(null);
        }
        int n2 = string2.length();
        int n3 = 0;
        byteArrayBuilder = (ByteArrayBuilder)byteArrayBuilder2.resetAndGetFirstSegment();
        int n4 = byteArrayBuilder.length;
        int n5 = 0;
        while (n5 < n2) {
            int n6;
            int n7;
            int n8 = string2.charAt(n5);
            ++n5;
            while (n8 <= 127) {
                n7 = n4;
                n6 = n3;
                if (n3 >= n4) {
                    byteArrayBuilder = (ByteArrayBuilder)byteArrayBuilder2.finishCurrentSegment();
                    n7 = byteArrayBuilder.length;
                    n6 = 0;
                }
                n3 = n6 + 1;
                byteArrayBuilder[n6] = (ByteArrayBuilder)((byte)n8);
                if (n5 >= n2) {
                    return this._bytes.completeAndCoalesce(n3);
                }
                n8 = string2.charAt(n5);
                ++n5;
                n4 = n7;
            }
            if (n3 >= n4) {
                byteArrayBuilder = (ByteArrayBuilder)byteArrayBuilder2.finishCurrentSegment();
                n3 = byteArrayBuilder.length;
                n4 = 0;
            } else {
                n7 = n3;
                n3 = n4;
                n4 = n7;
            }
            if (n8 < 2048) {
                n6 = n4 + 1;
                byteArrayBuilder[n4] = (ByteArrayBuilder)((byte)(n8 >> 6 | 192));
                n7 = n5;
                n4 = n6;
                n5 = n3;
                n3 = n7;
                n7 = n8;
            } else if (n8 < 55296 || n8 > 57343) {
                n6 = n4 + 1;
                byteArrayBuilder[n4] = (ByteArrayBuilder)((byte)(n8 >> 12 | 224));
                n4 = n3;
                n7 = n6;
                if (n6 >= n3) {
                    byteArrayBuilder = (ByteArrayBuilder)byteArrayBuilder2.finishCurrentSegment();
                    n4 = byteArrayBuilder.length;
                    n7 = 0;
                }
                byteArrayBuilder[n7] = (ByteArrayBuilder)((byte)(n8 >> 6 & 63 | 128));
                n6 = n7 + 1;
                n3 = n5;
                n7 = n8;
                n5 = n4;
                n4 = n6;
            } else {
                if (n8 > 56319) {
                    JsonStringEncoder._illegal(n8);
                }
                if (n5 >= n2) {
                    JsonStringEncoder._illegal(n8);
                }
                n6 = n5 + 1;
                n7 = JsonStringEncoder._convert(n8, string2.charAt(n5));
                if (n7 > 1114111) {
                    JsonStringEncoder._illegal(n7);
                }
                n8 = n4 + 1;
                byteArrayBuilder[n4] = (ByteArrayBuilder)((byte)(n7 >> 18 | 240));
                n5 = n3;
                n4 = n8;
                if (n8 >= n3) {
                    byteArrayBuilder = (ByteArrayBuilder)byteArrayBuilder2.finishCurrentSegment();
                    n5 = byteArrayBuilder.length;
                    n4 = 0;
                }
                n3 = n4 + 1;
                byteArrayBuilder[n4] = (ByteArrayBuilder)((byte)(n7 >> 12 & 63 | 128));
                if (n3 >= n5) {
                    byteArrayBuilder = (ByteArrayBuilder)byteArrayBuilder2.finishCurrentSegment();
                    n5 = byteArrayBuilder.length;
                    n3 = 0;
                }
                byteArrayBuilder[n3] = (ByteArrayBuilder)((byte)(n7 >> 6 & 63 | 128));
                n4 = n3 + 1;
                n3 = n6;
            }
            n8 = n5;
            n6 = n4;
            if (n4 >= n5) {
                byteArrayBuilder = (ByteArrayBuilder)byteArrayBuilder2.finishCurrentSegment();
                n8 = byteArrayBuilder.length;
                n6 = 0;
            }
            byteArrayBuilder[n6] = (ByteArrayBuilder)((byte)(n7 & 63 | 128));
            n7 = n6 + 1;
            n5 = n3;
            n4 = n8;
            n3 = n7;
        }
        return this._bytes.completeAndCoalesce(n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public char[] quoteAsString(String string2) {
        TextBuffer textBuffer;
        int n2;
        TextBuffer textBuffer2 = textBuffer = this._text;
        if (textBuffer == null) {
            this._text = textBuffer2 = new TextBuffer(null);
        }
        textBuffer = (TextBuffer)textBuffer2.emptyAndGetCurrentSegment();
        int[] arrn = CharTypes.get7BitOutputEscapes();
        int n3 = arrn.length;
        int n4 = 0;
        int n5 = string2.length();
        int n6 = 0;
        block0 : do {
            block7 : {
                n2 = n6;
                if (n4 >= n5) break;
                do {
                    char c2;
                    if ((c2 = string2.charAt(n4)) < n3 && arrn[c2] != 0) {
                        n2 = string2.charAt(n4);
                        int n7 = arrn[n2];
                        n2 = n7 < 0 ? this._appendNumeric(n2, this._qbuf) : this._appendNamed(n7, this._qbuf);
                        if (n6 + n2 <= textBuffer.length) break;
                        n7 = textBuffer.length - n6;
                        if (n7 > 0) {
                            System.arraycopy(this._qbuf, 0, textBuffer, n6, n7);
                        }
                        textBuffer = (TextBuffer)textBuffer2.finishCurrentSegment();
                        n6 = n2 - n7;
                        System.arraycopy(this._qbuf, n7, textBuffer, 0, n6);
                        break block7;
                    }
                    n2 = n6;
                    TextBuffer textBuffer3 = textBuffer;
                    if (n6 >= textBuffer.length) {
                        textBuffer3 = (TextBuffer)textBuffer2.finishCurrentSegment();
                        n2 = 0;
                    }
                    n6 = n2 + 1;
                    textBuffer3[n2] = (TextBuffer)c2;
                    if (++n4 >= n5) {
                        n2 = n6;
                        break block0;
                    }
                    textBuffer = textBuffer3;
                } while (true);
                System.arraycopy(this._qbuf, 0, textBuffer, n6, n2);
                n6 += n2;
            }
            ++n4;
        } while (true);
        textBuffer2.setCurrentLength(n2);
        return textBuffer2.contentsAsArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] quoteAsUTF8(String string2) {
        ByteArrayBuilder byteArrayBuilder;
        ByteArrayBuilder byteArrayBuilder2 = byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            this._bytes = byteArrayBuilder2 = new ByteArrayBuilder(null);
        }
        int n2 = 0;
        int n3 = string2.length();
        int n4 = 0;
        byte[] arrby = byteArrayBuilder2.resetAndGetFirstSegment();
        block0 : do {
            int n5;
            void var7_4;
            void var7_8;
            void var8_33;
            void var7_13;
            int n6 = n4;
            if (n2 >= n3) return this._bytes.completeAndCoalesce(n6);
            int[] arrn = CharTypes.get7BitOutputEscapes();
            void var8_28 = var7_4;
            do {
                void var8_29;
                void var7_12;
                if ((n5 = string2.charAt(n2)) > 127 || arrn[n5] != 0) {
                    void var7_6 = var8_29;
                    n6 = n4;
                    if (n4 >= var8_29.length) {
                        byte[] arrby2 = byteArrayBuilder2.finishCurrentSegment();
                        n6 = 0;
                    }
                    n4 = n2 + 1;
                    n5 = string2.charAt(n2);
                    if (n5 > 127) break;
                    n6 = this._appendByte(n5, arrn[n5], byteArrayBuilder2, n6);
                    byte[] arrby3 = byteArrayBuilder2.getCurrentSegment();
                    n2 = n4;
                    n4 = n6;
                    continue block0;
                }
                void var7_10 = var8_29;
                n6 = n4;
                if (n4 >= var8_29.length) {
                    byte[] arrby4 = byteArrayBuilder2.finishCurrentSegment();
                    n6 = 0;
                }
                n4 = n6 + 1;
                var7_12[n6] = (byte)n5;
                if (++n2 >= n3) {
                    n6 = n4;
                    return this._bytes.completeAndCoalesce(n6);
                }
                void var8_30 = var7_12;
            } while (true);
            if (n5 <= 2047) {
                var7_8[n6] = (byte)(n5 >> 6 | 192);
                n5 = n5 & 63 | 128;
                n2 = n6 + 1;
                n6 = n5;
            } else if (n5 < 55296 || n5 > 57343) {
                n2 = n6 + 1;
                var7_8[n6] = (byte)(n5 >> 12 | 224);
                if (n2 >= var7_8.length) {
                    byte[] arrby5 = byteArrayBuilder2.finishCurrentSegment();
                    n2 = 0;
                }
                var7_16[n2] = (byte)(n5 >> 6 & 63 | 128);
                n6 = n5 & 63 | 128;
                ++n2;
            } else {
                void var7_18;
                if (n5 > 56319) {
                    JsonStringEncoder._illegal(n5);
                }
                if (n4 >= n3) {
                    JsonStringEncoder._illegal(n5);
                }
                n2 = n4 + 1;
                if ((n5 = JsonStringEncoder._convert(n5, string2.charAt(n4))) > 1114111) {
                    JsonStringEncoder._illegal(n5);
                }
                n4 = n6 + 1;
                var7_8[n6] = (byte)(n5 >> 18 | 240);
                if (n4 >= var7_8.length) {
                    byte[] arrby6 = byteArrayBuilder2.finishCurrentSegment();
                    n4 = 0;
                }
                n6 = n4 + 1;
                var7_18[n4] = (byte)(n5 >> 12 & 63 | 128);
                if (n6 >= var7_18.length) {
                    byte[] arrby7 = byteArrayBuilder2.finishCurrentSegment();
                    n4 = 0;
                } else {
                    n4 = n6;
                }
                var7_20[n4] = (byte)(n5 >> 6 & 63 | 128);
                n6 = n5 & 63 | 128;
                n5 = n4 + 1;
                n4 = n2;
                n2 = n5;
            }
            void var8_31 = var7_13;
            n5 = n2;
            if (n2 >= var7_13.length) {
                byte[] arrby8 = byteArrayBuilder2.finishCurrentSegment();
                n5 = 0;
            }
            var8_33[n5] = (byte)n6;
            n6 = n5 + 1;
            n2 = n4;
            void var7_14 = var8_33;
            n4 = n6;
        } while (true);
    }
}

