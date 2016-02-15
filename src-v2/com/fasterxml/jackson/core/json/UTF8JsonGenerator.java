/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UTF8JsonGenerator
extends JsonGeneratorImpl {
    private static final byte BYTE_0 = 48;
    private static final byte BYTE_BACKSLASH = 92;
    private static final byte BYTE_COLON = 58;
    private static final byte BYTE_COMMA = 44;
    private static final byte BYTE_LBRACKET = 91;
    private static final byte BYTE_LCURLY = 123;
    private static final byte BYTE_QUOTE = 34;
    private static final byte BYTE_RBRACKET = 93;
    private static final byte BYTE_RCURLY = 125;
    private static final byte BYTE_u = 117;
    private static final byte[] FALSE_BYTES;
    static final byte[] HEX_CHARS;
    private static final int MAX_BYTES_TO_BUFFER = 512;
    private static final byte[] NULL_BYTES;
    protected static final int SURR1_FIRST = 55296;
    protected static final int SURR1_LAST = 56319;
    protected static final int SURR2_FIRST = 56320;
    protected static final int SURR2_LAST = 57343;
    private static final byte[] TRUE_BYTES;
    protected boolean _bufferRecyclable;
    protected boolean _cfgUnqNames;
    protected char[] _charBuffer;
    protected final int _charBufferLength;
    protected byte[] _entityBuffer;
    protected byte[] _outputBuffer;
    protected final int _outputEnd;
    protected final int _outputMaxContiguous;
    protected final OutputStream _outputStream;
    protected int _outputTail;

    static {
        HEX_CHARS = CharTypes.copyHexBytes();
        NULL_BYTES = new byte[]{110, 117, 108, 108};
        TRUE_BYTES = new byte[]{116, 114, 117, 101};
        FALSE_BYTES = new byte[]{102, 97, 108, 115, 101};
    }

    /*
     * Enabled aggressive block sorting
     */
    public UTF8JsonGenerator(IOContext iOContext, int n2, ObjectCodec objectCodec, OutputStream outputStream) {
        boolean bl2 = true;
        super(iOContext, n2, objectCodec);
        this._outputTail = 0;
        this._outputStream = outputStream;
        this._bufferRecyclable = true;
        this._outputBuffer = iOContext.allocWriteEncodingBuffer();
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = iOContext.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
        if (this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
            this.setHighestNonEscapedChar(127);
        }
        if (JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(n2)) {
            bl2 = false;
        }
        this._cfgUnqNames = bl2;
    }

    public UTF8JsonGenerator(IOContext iOContext, int n2, ObjectCodec objectCodec, OutputStream outputStream, byte[] arrby, int n3, boolean bl2) {
        boolean bl3 = false;
        super(iOContext, n2, objectCodec);
        this._outputTail = 0;
        this._outputStream = outputStream;
        this._bufferRecyclable = bl2;
        this._outputTail = n3;
        this._outputBuffer = arrby;
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = iOContext.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
        bl2 = bl3;
        if (!JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(n2)) {
            bl2 = true;
        }
        this._cfgUnqNames = bl2;
    }

    private final int _handleLongCustomEscape(byte[] arrby, int n2, int n3, byte[] arrby2, int n4) throws IOException, JsonGenerationException {
        int n5 = arrby2.length;
        int n6 = n2;
        if (n2 + n5 > n3) {
            this._outputTail = n2;
            this._flushBuffer();
            n2 = this._outputTail;
            if (n5 > arrby.length) {
                this._outputStream.write(arrby2, 0, n5);
                return n2;
            }
            System.arraycopy(arrby2, 0, arrby, n2, n5);
            n6 = n2 + n5;
        }
        if (n4 * 6 + n6 > n3) {
            this._flushBuffer();
            return this._outputTail;
        }
        return n6;
    }

    private final int _outputMultiByteChar(int n2, int n3) throws IOException {
        byte[] arrby = this._outputBuffer;
        if (n2 >= 55296 && n2 <= 57343) {
            int n4 = n3 + 1;
            arrby[n3] = 92;
            n3 = n4 + 1;
            arrby[n4] = 117;
            n4 = n3 + 1;
            arrby[n3] = HEX_CHARS[n2 >> 12 & 15];
            n3 = n4 + 1;
            arrby[n4] = HEX_CHARS[n2 >> 8 & 15];
            n4 = n3 + 1;
            arrby[n3] = HEX_CHARS[n2 >> 4 & 15];
            arrby[n4] = HEX_CHARS[n2 & 15];
            return n4 + 1;
        }
        int n5 = n3 + 1;
        arrby[n3] = (byte)(n2 >> 12 | 224);
        n3 = n5 + 1;
        arrby[n5] = (byte)(n2 >> 6 & 63 | 128);
        arrby[n3] = (byte)(n2 & 63 | 128);
        return n3 + 1;
    }

    private final int _outputRawMultiByteChar(int n2, char[] arrc, int n3, int n4) throws IOException {
        if (n2 >= 55296 && n2 <= 57343) {
            if (n3 >= n4 || arrc == null) {
                this._reportError("Split surrogate on writeRaw() input (last character)");
            }
            this._outputSurrogates(n2, arrc[n3]);
            return n3 + 1;
        }
        arrc = this._outputBuffer;
        n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc[n4] = (char)(n2 >> 12 | 224);
        n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc[n4] = (char)(n2 >> 6 & 63 | 128);
        n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc[n4] = (char)(n2 & 63 | 128);
        return n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final int _readMore(InputStream inputStream, byte[] arrby, int n2, int n3, int n4) throws IOException {
        int n5 = 0;
        int n6 = n2;
        n2 = n5;
        while (n6 < n3) {
            arrby[n2] = arrby[n6];
            ++n2;
            ++n6;
        }
        n4 = Math.min(n4, arrby.length);
        do {
            if ((n3 = n4 - n2) == 0) {
                return n2;
            }
            if ((n3 = inputStream.read(arrby, n2, n3)) < 0) {
                return n2;
            }
            n2 = n3 = n2 + n3;
        } while (n3 < 3);
        return n3;
    }

    private final void _writeBytes(byte[] arrby) throws IOException {
        int n2 = arrby.length;
        if (this._outputTail + n2 > this._outputEnd) {
            this._flushBuffer();
            if (n2 > 512) {
                this._outputStream.write(arrby, 0, n2);
                return;
            }
        }
        System.arraycopy(arrby, 0, this._outputBuffer, this._outputTail, n2);
        this._outputTail += n2;
    }

    private final void _writeBytes(byte[] arrby, int n2, int n3) throws IOException {
        if (this._outputTail + n3 > this._outputEnd) {
            this._flushBuffer();
            if (n3 > 512) {
                this._outputStream.write(arrby, n2, n3);
                return;
            }
        }
        System.arraycopy(arrby, n2, this._outputBuffer, this._outputTail, n3);
        this._outputTail += n3;
    }

    private final int _writeCustomEscape(byte[] arrby, int n2, SerializableString serializableString, int n3) throws IOException, JsonGenerationException {
        int n4 = (serializableString = (SerializableString)serializableString.asUnquotedUTF8()).length;
        if (n4 > 6) {
            return this._handleLongCustomEscape(arrby, n2, this._outputEnd, (byte[])serializableString, n3);
        }
        System.arraycopy(serializableString, 0, arrby, n2, n4);
        return n2 + n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeCustomStringSegment2(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        if (this._outputTail + (n3 - n2) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n4 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n5 = this._maximumNonEscapedChar <= 0 ? 65535 : this._maximumNonEscapedChar;
        CharacterEscapes characterEscapes = this._characterEscapes;
        do {
            SerializableString serializableString;
            if (n2 >= n3) {
                this._outputTail = n4;
                return;
            }
            int n6 = n2 + 1;
            int n7 = arrc[n2];
            if (n7 <= 127) {
                if (arrn[n7] == 0) {
                    arrby[n4] = (byte)n7;
                    ++n4;
                    n2 = n6;
                    continue;
                }
                n2 = arrn[n7];
                if (n2 > 0) {
                    n7 = n4 + 1;
                    arrby[n4] = 92;
                    n4 = n7 + 1;
                    arrby[n7] = (byte)n2;
                    n2 = n6;
                    continue;
                }
                if (n2 == -2) {
                    serializableString = characterEscapes.getEscapeSequence(n7);
                    if (serializableString == null) {
                        this._reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(n7) + ", although was supposed to have one");
                    }
                    n4 = this._writeCustomEscape(arrby, n4, serializableString, n3 - n6);
                    n2 = n6;
                    continue;
                }
                n4 = this._writeGenericEscape(n7, n4);
                n2 = n6;
                continue;
            }
            if (n7 > n5) {
                n4 = this._writeGenericEscape(n7, n4);
                n2 = n6;
                continue;
            }
            serializableString = characterEscapes.getEscapeSequence(n7);
            if (serializableString != null) {
                n4 = this._writeCustomEscape(arrby, n4, serializableString, n3 - n6);
                n2 = n6;
                continue;
            }
            if (n7 <= 2047) {
                n2 = n4 + 1;
                arrby[n4] = (byte)(n7 >> 6 | 192);
                arrby[n2] = (byte)(n7 & 63 | 128);
                ++n2;
            } else {
                n2 = this._outputMultiByteChar(n7, n4);
            }
            n4 = n2;
            n2 = n6;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int _writeGenericEscape(int n2, int n3) throws IOException {
        byte[] arrby = this._outputBuffer;
        int n4 = n3 + 1;
        arrby[n3] = 92;
        n3 = n4 + 1;
        arrby[n4] = 117;
        if (n2 > 255) {
            n4 = n2 >> 8 & 255;
            int n5 = n3 + 1;
            arrby[n3] = HEX_CHARS[n4 >> 4];
            n3 = n5 + 1;
            arrby[n5] = HEX_CHARS[n4 & 15];
            n2 &= 255;
        } else {
            n4 = n3 + 1;
            arrby[n3] = 48;
            n3 = n4 + 1;
            arrby[n4] = 48;
        }
        n4 = n3 + 1;
        arrby[n3] = HEX_CHARS[n2 >> 4];
        arrby[n4] = HEX_CHARS[n2 & 15];
        return n4 + 1;
    }

    private void _writeLongString(char[] arrc, int n2, int n3) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this._writeStringSegments(this._charBuffer, 0, n3);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
    }

    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
        this._outputTail += 4;
    }

    private final void _writeQuotedInt(int n2) throws IOException {
        if (this._outputTail + 13 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = 34;
        this._outputTail = NumberOutput.outputInt(n2, this._outputBuffer, this._outputTail);
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
    }

    private final void _writeQuotedLong(long l2) throws IOException {
        if (this._outputTail + 23 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
        this._outputTail = NumberOutput.outputLong(l2, this._outputBuffer, this._outputTail);
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
    }

    private final void _writeQuotedRaw(Object object) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
        this.writeRaw(object.toString());
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        object = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        object[n2] = 34;
    }

    private final void _writeQuotedShort(short s2) throws IOException {
        if (this._outputTail + 8 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
        this._outputTail = NumberOutput.outputInt((int)s2, this._outputBuffer, this._outputTail);
        arrby = this._outputBuffer;
        s2 = (short)this._outputTail;
        this._outputTail = s2 + 1;
        arrby[s2] = 34;
    }

    private final void _writeSegmentedRaw(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        int n4 = this._outputEnd;
        byte[] arrby = this._outputBuffer;
        block0 : do {
            int n5;
            block7 : {
                if (n2 < n3) {
                    do {
                        int n6;
                        if ((n5 = arrc[n2]) >= 128) {
                            if (this._outputTail + 3 >= this._outputEnd) {
                                this._flushBuffer();
                            }
                            n5 = n2 + 1;
                            if ((n2 = arrc[n2]) < 2048) {
                                n6 = this._outputTail;
                                this._outputTail = n6 + 1;
                                arrby[n6] = (byte)(n2 >> 6 | 192);
                                n6 = this._outputTail;
                                this._outputTail = n6 + 1;
                                arrby[n6] = (byte)(n2 & 63 | 128);
                                n2 = n5;
                                continue block0;
                            }
                            break block7;
                        }
                        if (this._outputTail >= n4) {
                            this._flushBuffer();
                        }
                        n6 = this._outputTail;
                        this._outputTail = n6 + 1;
                        arrby[n6] = (byte)n5;
                        n2 = n5 = n2 + 1;
                    } while (n5 < n3);
                }
                return;
            }
            n2 = this._outputRawMultiByteChar(n2, arrc, n5, n3);
        } while (true);
    }

    private final void _writeStringSegment(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        int n4 = n3 + n2;
        int n5 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        n3 = n2;
        n2 = n5;
        do {
            if (n3 >= n4 || (n5 = arrc[n3]) > 127 || arrn[n5] != 0) {
                this._outputTail = n2;
                if (n3 < n4) {
                    if (this._characterEscapes == null) break;
                    this._writeCustomStringSegment2(arrc, n3, n4);
                }
                return;
            }
            arrby[n2] = (byte)n5;
            ++n3;
            ++n2;
        } while (true);
        if (this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(arrc, n3, n4);
            return;
        }
        this._writeStringSegmentASCII2(arrc, n3, n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeStringSegment2(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        if (this._outputTail + (n3 - n2) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n4 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n5 = n2;
        n2 = n4;
        n4 = n5;
        do {
            if (n4 >= n3) {
                this._outputTail = n2;
                return;
            }
            n5 = n4 + 1;
            int n6 = arrc[n4];
            if (n6 <= 127) {
                if (arrn[n6] == 0) {
                    arrby[n2] = (byte)n6;
                    ++n2;
                    n4 = n5;
                    continue;
                }
                n4 = arrn[n6];
                if (n4 > 0) {
                    n6 = n2 + 1;
                    arrby[n2] = 92;
                    n2 = n6 + 1;
                    arrby[n6] = (byte)n4;
                    n4 = n5;
                    continue;
                }
                n2 = this._writeGenericEscape(n6, n2);
                n4 = n5;
                continue;
            }
            if (n6 <= 2047) {
                n4 = n2 + 1;
                arrby[n2] = (byte)(n6 >> 6 | 192);
                arrby[n4] = (byte)(n6 & 63 | 128);
                n2 = n4 + 1;
            } else {
                n2 = this._outputMultiByteChar(n6, n2);
            }
            n4 = n5;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeStringSegmentASCII2(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        if (this._outputTail + (n3 - n2) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n4 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n5 = this._maximumNonEscapedChar;
        do {
            if (n2 >= n3) {
                this._outputTail = n4;
                return;
            }
            int n6 = n2 + 1;
            int n7 = arrc[n2];
            if (n7 <= 127) {
                if (arrn[n7] == 0) {
                    arrby[n4] = (byte)n7;
                    ++n4;
                    n2 = n6;
                    continue;
                }
                n2 = arrn[n7];
                if (n2 > 0) {
                    n7 = n4 + 1;
                    arrby[n4] = 92;
                    n4 = n7 + 1;
                    arrby[n7] = (byte)n2;
                    n2 = n6;
                    continue;
                }
                n4 = this._writeGenericEscape(n7, n4);
                n2 = n6;
                continue;
            }
            if (n7 > n5) {
                n4 = this._writeGenericEscape(n7, n4);
                n2 = n6;
                continue;
            }
            if (n7 <= 2047) {
                n2 = n4 + 1;
                arrby[n4] = (byte)(n7 >> 6 | 192);
                arrby[n2] = (byte)(n7 & 63 | 128);
                ++n2;
            } else {
                n2 = this._outputMultiByteChar(n7, n4);
            }
            n4 = n2;
            n2 = n6;
        } while (true);
    }

    private final void _writeStringSegments(String string2, boolean bl2) throws IOException {
        int n2;
        char[] arrc;
        int n3;
        if (bl2) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrc = this._outputBuffer;
            n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrc[n3] = 34;
        }
        int n4 = 0;
        arrc = this._charBuffer;
        for (n3 = string2.length(); n3 > 0; n3 -= n2) {
            n2 = Math.min(this._outputMaxContiguous, n3);
            string2.getChars(n4, n4 + n2, arrc, 0);
            if (this._outputTail + n2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(arrc, 0, n2);
            n4 += n2;
        }
        if (bl2) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            string2 = (String)this._outputBuffer;
            n3 = this._outputTail;
            this._outputTail = n3 + 1;
            string2[n3] = (String)34;
        }
    }

    private final void _writeStringSegments(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        int n4;
        do {
            if (this._outputTail + (n4 = Math.min(this._outputMaxContiguous, n3)) > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(arrc, n2, n4);
            n2 += n4;
            n3 = n4 = n3 - n4;
        } while (n4 > 0);
    }

    private final void _writeUTF8Segment(byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        for (int i2 = n2; i2 < n2 + n3; ++i2) {
            byte by2 = arrby[i2];
            if (by2 < 0 || arrn[by2] == 0) continue;
            this._writeUTF8Segment2(arrby, n2, n3);
            return;
        }
        if (this._outputTail + n3 > this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(arrby, n2, this._outputBuffer, this._outputTail, n3);
        this._outputTail += n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeUTF8Segment2(byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        int n4;
        int n5 = n4 = this._outputTail;
        if (n3 * 6 + n4 > this._outputEnd) {
            this._flushBuffer();
            n5 = this._outputTail;
        }
        byte[] arrby2 = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        n4 = n2;
        do {
            if (n4 >= n3 + n2) {
                this._outputTail = n5;
                return;
            }
            int n6 = n4 + 1;
            byte by2 = arrby[n4];
            if (by2 < 0 || arrn[by2] == 0) {
                arrby2[n5] = by2;
                ++n5;
                n4 = n6;
                continue;
            }
            n4 = arrn[by2];
            if (n4 > 0) {
                int n7 = n5 + 1;
                arrby2[n5] = 92;
                arrby2[n7] = (byte)n4;
                n5 = n7 + 1;
            } else {
                n5 = this._writeGenericEscape(by2, n5);
            }
            n4 = n6;
        } while (true);
    }

    private final void _writeUTF8Segments(byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        int n4;
        do {
            n4 = Math.min(this._outputMaxContiguous, n3);
            this._writeUTF8Segment(arrby, n2, n4);
            n2 += n4;
            n3 = n4 = n3 - n4;
        } while (n4 > 0);
    }

    private final void _writeUnq(SerializableString serializableString) throws IOException {
        int n2 = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (n2 < 0) {
            this._writeBytes(serializableString.asQuotedUTF8());
            return;
        }
        this._outputTail += n2;
    }

    protected final int _decodeSurrogate(int n2, int n3) throws IOException {
        if (n3 < 56320 || n3 > 57343) {
            this._reportError("Incomplete surrogate pair: first char 0x" + Integer.toHexString(n2) + ", second 0x" + Integer.toHexString(n3));
        }
        return 65536 + (n2 - 55296 << 10) + (n3 - 56320);
    }

    protected final void _flushBuffer() throws IOException {
        int n2 = this._outputTail;
        if (n2 > 0) {
            this._outputTail = 0;
            this._outputStream.write(this._outputBuffer, 0, n2);
        }
    }

    protected final void _outputSurrogates(int n2, int n3) throws IOException {
        n2 = this._decodeSurrogate(n2, n3);
        if (this._outputTail + 4 > this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = (byte)(n2 >> 18 | 240);
        n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = (byte)(n2 >> 12 & 63 | 128);
        n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = (byte)(n2 >> 6 & 63 | 128);
        n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = (byte)(n2 & 63 | 128);
    }

    @Override
    protected void _releaseBuffers() {
        char[] arrc = this._outputBuffer;
        if (arrc != null && this._bufferRecyclable) {
            this._outputBuffer = null;
            this._ioContext.releaseWriteEncodingBuffer((byte[])arrc);
        }
        if ((arrc = this._charBuffer) != null) {
            this._charBuffer = null;
            this._ioContext.releaseConcatBuffer(arrc);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _verifyPrettyValueWrite(String string2, int n2) throws IOException, JsonGenerationException {
        switch (n2) {
            default: {
                this._throwInternal();
                return;
            }
            case 1: {
                this._cfgPrettyPrinter.writeArrayValueSeparator(this);
                return;
            }
            case 2: {
                this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
                return;
            }
            case 3: {
                this._cfgPrettyPrinter.writeRootValueSeparator(this);
                return;
            }
            case 0: {
                if (this._writeContext.inArray()) {
                    this._cfgPrettyPrinter.beforeArrayValues(this);
                    return;
                }
                if (!this._writeContext.inObject()) return;
                this._cfgPrettyPrinter.beforeObjectEntries(this);
                return;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected final void _verifyValueWrite(String var1_1) throws IOException, JsonGenerationException {
        var3_3 = this._writeContext.writeValue();
        if (var3_3 == 5) {
            this._reportError("Can not " + var1_1 /* !! */  + ", expecting field name");
        }
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite(var1_1 /* !! */ , var3_3);
            return;
        }
        switch (var3_3) {
            default: {
                return;
            }
            case 1: {
                var2_4 = 44;
                ** GOTO lbl15
            }
            case 2: {
                var2_4 = 58;
lbl15: // 2 sources:
                if (this._outputTail >= this._outputEnd) {
                    this._flushBuffer();
                }
                this._outputBuffer[this._outputTail] = var2_4;
                ++this._outputTail;
                return;
            }
            case 3: 
        }
        if (this._rootValueSeparator == null) return;
        var1_2 = this._rootValueSeparator.asUnquotedUTF8();
        if (var1_2.length <= 0) return;
        this._writeBytes(var1_2);
    }

    protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] arrby) throws IOException, JsonGenerationException {
        int n2 = 0;
        int n3 = 0;
        int n4 = -3;
        int n5 = 0;
        int n6 = this._outputEnd - 6;
        int n7 = base64Variant.getMaxLineLength() >> 2;
        do {
            int n8 = n2;
            int n9 = n3;
            int n10 = n4;
            if (n2 > n4) {
                n9 = this._readMore(inputStream, arrby, n2, n3, arrby.length);
                n8 = 0;
                if (n9 < 3) {
                    n2 = n5;
                    if (n9 < 0) {
                        if (this._outputTail > n6) {
                            this._flushBuffer();
                        }
                        n4 = 0 + 1;
                        n10 = arrby[0] << 16;
                        n7 = 1;
                        n2 = n10;
                        if (n4 < n9) {
                            n2 = n10 | (arrby[n4] & 255) << 8;
                            n7 = 2;
                        }
                        this._outputTail = base64Variant.encodeBase64Partial(n2, n7, this._outputBuffer, this._outputTail);
                        n2 = n5 += n7;
                    }
                    return n2;
                }
                n10 = n9 - 3;
            }
            if (this._outputTail > n6) {
                this._flushBuffer();
            }
            n3 = n8 + 1;
            n2 = arrby[n8];
            n4 = n3 + 1;
            n3 = arrby[n3];
            n8 = arrby[n4];
            n5 += 3;
            this._outputTail = base64Variant.encodeBase64Chunk((n2 << 8 | n3 & 255) << 8 | n8 & 255, this._outputBuffer, this._outputTail);
            n7 = n2 = n7 - 1;
            if (n2 <= 0) {
                byte[] arrby2 = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrby2[n2] = 92;
                arrby2 = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrby2[n2] = 110;
                n7 = base64Variant.getMaxLineLength() >> 2;
            }
            n2 = n4 + 1;
            n3 = n9;
            n4 = n10;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected final int _writeBinary(Base64Variant var1_1, InputStream var2_2, byte[] var3_3, int var4_4) throws IOException, JsonGenerationException {
        var8_5 = 0;
        var6_6 = 0;
        var10_7 = -3;
        var12_8 = this._outputEnd - 6;
        var7_9 = var1_1.getMaxLineLength() >> 2;
        var5_10 = var4_4;
        var4_4 = var8_5;
        do {
            var9_11 = var4_4;
            var8_5 = var6_6;
            if (var5_10 <= 2) ** GOTO lbl22
            var11_12 = var4_4;
            var9_11 = var6_6;
            var8_5 = var10_7;
            if (var4_4 > var10_7) {
                var9_11 = this._readMore(var2_2, var3_3, var4_4, var6_6, var5_10);
                var11_12 = 0;
                var4_4 = 0;
                if (var9_11 < 3) {
                    var8_5 = var9_11;
                    var9_11 = var4_4;
lbl22: // 2 sources:
                    var4_4 = var5_10;
                    if (var5_10 <= 0) return var4_4;
                    var7_9 = this._readMore(var2_2, var3_3, var9_11, var8_5, var5_10);
                    var4_4 = var5_10;
                    if (var7_9 <= 0) return var4_4;
                    if (this._outputTail > var12_8) {
                        this._flushBuffer();
                    }
                    var4_4 = 0 + 1;
                    var6_6 = var3_3[0] << 16;
                    if (var4_4 < var7_9) {
                        var6_6 |= (var3_3[var4_4] & 255) << 8;
                        var4_4 = 2;
                    } else {
                        var4_4 = 1;
                    }
                    this._outputTail = var1_1.encodeBase64Partial(var6_6, var4_4, this._outputBuffer, this._outputTail);
                    return var5_10 - var4_4;
                }
                var8_5 = var9_11 - 3;
            }
            if (this._outputTail > var12_8) {
                this._flushBuffer();
            }
            var10_7 = var11_12 + 1;
            var4_4 = var3_3[var11_12];
            var6_6 = var10_7 + 1;
            var10_7 = var3_3[var10_7];
            var13_13 = var3_3[var6_6];
            var11_12 = var5_10 - 3;
            this._outputTail = var1_1.encodeBase64Chunk((var4_4 << 8 | var10_7 & 255) << 8 | var13_13 & 255, this._outputBuffer, this._outputTail);
            var5_10 = var4_4 = var7_9 - 1;
            if (var4_4 <= 0) {
                var14_14 = this._outputBuffer;
                var4_4 = this._outputTail;
                this._outputTail = var4_4 + 1;
                var14_14[var4_4] = 92;
                var14_14 = this._outputBuffer;
                var4_4 = this._outputTail;
                this._outputTail = var4_4 + 1;
                var14_14[var4_4] = 110;
                var5_10 = var1_1.getMaxLineLength() >> 2;
            }
            var4_4 = var6_6 + 1;
            var6_6 = var9_11;
            var7_9 = var5_10;
            var10_7 = var8_5;
            var5_10 = var11_12;
        } while (true);
    }

    protected final void _writeBinary(Base64Variant base64Variant, byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        int n4 = this._outputEnd - 6;
        int n5 = base64Variant.getMaxLineLength() >> 2;
        int n6 = n2;
        n2 = n5;
        while (n6 <= n3 - 3) {
            if (this._outputTail > n4) {
                this._flushBuffer();
            }
            int n7 = n6 + 1;
            n6 = arrby[n6];
            n5 = n7 + 1;
            this._outputTail = base64Variant.encodeBase64Chunk((n6 << 8 | arrby[n7] & 255) << 8 | arrby[n5] & 255, this._outputBuffer, this._outputTail);
            n2 = n6 = n2 - 1;
            if (n6 <= 0) {
                byte[] arrby2 = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrby2[n2] = 92;
                arrby2 = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrby2[n2] = 110;
                n2 = base64Variant.getMaxLineLength() >> 2;
            }
            n6 = n5 + 1;
        }
        n5 = n3 - n6;
        if (n5 > 0) {
            if (this._outputTail > n4) {
                this._flushBuffer();
            }
            n4 = n6 + 1;
            n2 = n3 = arrby[n6] << 16;
            if (n5 == 2) {
                n2 = n3 | (arrby[n4] & 255) << 8;
            }
            this._outputTail = base64Variant.encodeBase64Partial(n2, n5, this._outputBuffer, this._outputTail);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _writePPFieldName(SerializableString serializableString) throws IOException {
        int n2 = 1;
        int n3 = this._writeContext.writeFieldName(serializableString.getValue());
        if (n3 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n3 == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            n2 = 0;
        }
        if (n2 != 0) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrby[n3] = 34;
        }
        this._writeBytes(serializableString.asQuotedUTF8());
        if (n2 != 0) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby[n2] = 34;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _writePPFieldName(String string2) throws IOException {
        int n2 = this._writeContext.writeFieldName(string2);
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments(string2, false);
            return;
        }
        n2 = string2.length();
        if (n2 > this._charBufferLength) {
            this._writeStringSegments(string2, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = 34;
        string2.getChars(0, n2, this._charBuffer, 0);
        if (n2 <= this._outputMaxContiguous) {
            if (this._outputTail + n2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(this._charBuffer, 0, n2);
        } else {
            this._writeStringSegments(this._charBuffer, 0, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void close() throws IOException {
        super.close();
        if (this._outputBuffer != null && this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
            do {
                JsonStreamContext jsonStreamContext;
                if ((jsonStreamContext = this.getOutputContext()).inArray()) {
                    this.writeEndArray();
                    continue;
                }
                if (!jsonStreamContext.inObject()) break;
                this.writeEndObject();
            } while (true);
        }
        this._flushBuffer();
        if (this._outputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                this._outputStream.close();
            } else if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                this._outputStream.flush();
            }
        }
        this._releaseBuffers();
    }

    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._outputStream != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
            this._outputStream.flush();
        }
    }

    @Override
    public Object getOutputTarget() {
        return this._outputStream;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int writeBinary(Base64Variant var1_1, InputStream var2_3, int var3_4) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        var5_6 = this._outputBuffer;
        var4_7 = this._outputTail;
        this._outputTail = var4_7 + 1;
        var5_6[var4_7] = 34;
        var5_6 = this._ioContext.allocBase64Buffer();
        if (var3_5 >= 0) ** GOTO lbl13
        try {
            var3_5 = this._writeBinary(var1_1 /* !! */ , (InputStream)var2_4, var5_6);
            ** GOTO lbl16
lbl13: // 1 sources:
            var4_7 = this._writeBinary(var1_1 /* !! */ , (InputStream)var2_4, var5_6, var3_5);
            if (var4_7 > 0) {
                this._reportError("Too few bytes available: missing " + var4_7 + " bytes (out of " + var3_5 + ")");
            }
lbl16: // 4 sources:
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            var1_2 = this._outputBuffer;
            var4_7 = this._outputTail;
            this._outputTail = var4_7 + 1;
            var1_2[var4_7] = 34;
            return var3_5;
        }
        finally {
            this._ioContext.releaseBase64Buffer(var5_6);
        }
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby2[n4] = 34;
        this._writeBinary(base64Variant, arrby, n2, n2 + n3);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        base64Variant = (Base64Variant)this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        base64Variant[n2] = (Base64Variant)34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeBoolean(boolean bl2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = bl2 ? TRUE_BYTES : FALSE_BYTES;
        int n2 = arrby.length;
        System.arraycopy(arrby, 0, this._outputBuffer, this._outputTail, n2);
        this._outputTail += n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeEndArray() throws IOException {
        if (!this._writeContext.inArray()) {
            this._reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby[n2] = 93;
        }
        this._writeContext = this._writeContext.getParent();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeEndObject() throws IOException {
        if (!this._writeContext.inObject()) {
            this._reportError("Current context not an object but " + this._writeContext.getTypeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby[n2] = 125;
        }
        this._writeContext = this._writeContext.getParent();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        byte[] arrby;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(serializableString);
            return;
        }
        int n2 = this._writeContext.writeFieldName(serializableString.getValue());
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrby = this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby[n2] = 44;
        }
        if (this._cfgUnqNames) {
            this._writeUnq(serializableString);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
        n2 = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (n2 < 0) {
            this._writeBytes(serializableString.asQuotedUTF8());
        } else {
            this._outputTail += n2;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeFieldName(String string2) throws IOException {
        byte[] arrby;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(string2);
            return;
        }
        int n2 = this._writeContext.writeFieldName(string2);
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrby = this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby[n2] = 44;
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments(string2, false);
            return;
        }
        n2 = string2.length();
        if (n2 > this._charBufferLength) {
            this._writeStringSegments(string2, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = 34;
        string2.getChars(0, n2, this._charBuffer, 0);
        if (n2 <= this._outputMaxContiguous) {
            if (this._outputTail + n2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(this._charBuffer, 0, n2);
        } else {
            this._writeStringSegments(this._charBuffer, 0, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    @Override
    public void writeNull() throws IOException, JsonGenerationException {
        this._verifyValueWrite("write null value");
        this._writeNull();
    }

    @Override
    public void writeNumber(double d2) throws IOException, JsonGenerationException {
        if (this._cfgNumbersAsStrings || (Double.isNaN(d2) || Double.isInfinite(d2)) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS)) {
            this.writeString(String.valueOf(d2));
            return;
        }
        this._verifyValueWrite("write number");
        this.writeRaw(String.valueOf(d2));
    }

    @Override
    public void writeNumber(float f2) throws IOException, JsonGenerationException {
        if (this._cfgNumbersAsStrings || (Float.isNaN(f2) || Float.isInfinite(f2)) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS)) {
            this.writeString(String.valueOf(f2));
            return;
        }
        this._verifyValueWrite("write number");
        this.writeRaw(String.valueOf(f2));
    }

    @Override
    public void writeNumber(int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(n2);
            return;
        }
        this._outputTail = NumberOutput.outputInt(n2, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(long l2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedLong(l2);
            return;
        }
        if (this._outputTail + 21 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputLong(l2, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(String string2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(string2);
            return;
        }
        this.writeRaw(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (bigDecimal == null) {
            this._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(bigDecimal);
            return;
        }
        if (this.isEnabled(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)) {
            this.writeRaw(bigDecimal.toPlainString());
            return;
        }
        this.writeRaw(bigDecimal.toString());
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (bigInteger == null) {
            this._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(bigInteger);
            return;
        }
        this.writeRaw(bigInteger.toString());
    }

    @Override
    public void writeNumber(short s2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(s2);
            return;
        }
        this._outputTail = NumberOutput.outputInt((int)s2, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeRaw(char c2) throws IOException, JsonGenerationException {
        if (this._outputTail + 3 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        if (c2 <= '') {
            int n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby[n2] = (byte)c2;
            return;
        }
        if (c2 < '\u0800') {
            int n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrby[n3] = (byte)(c2 >> 6 | 192);
            n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrby[n3] = (byte)(c2 & 63 | 128);
            return;
        }
        this._outputRawMultiByteChar(c2, null, 0, 0);
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException, JsonGenerationException {
        if ((serializableString = (SerializableString)serializableString.asUnquotedUTF8()).length > 0) {
            this._writeBytes((byte[])serializableString);
        }
    }

    @Override
    public void writeRaw(String string2) throws IOException, JsonGenerationException {
        int n2;
        int n3 = 0;
        for (int i2 = string2.length(); i2 > 0; i2 -= n2) {
            char[] arrc = this._charBuffer;
            n2 = arrc.length;
            if (i2 < n2) {
                n2 = i2;
            }
            string2.getChars(n3, n3 + n2, arrc, 0);
            this.writeRaw(arrc, 0, n2);
            n3 += n2;
        }
    }

    @Override
    public void writeRaw(String string2, int n2, int n3) throws IOException, JsonGenerationException {
        while (n3 > 0) {
            char[] arrc = this._charBuffer;
            int n4 = arrc.length;
            if (n3 < n4) {
                n4 = n3;
            }
            string2.getChars(n2, n2 + n4, arrc, 0);
            this.writeRaw(arrc, 0, n4);
            n2 += n4;
            n3 -= n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeRaw(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        int n4 = n3 + n3 + n3;
        if (this._outputTail + n4 > this._outputEnd) {
            if (this._outputEnd < n4) {
                this._writeSegmentedRaw(arrc, n2, n3);
                return;
            }
            this._flushBuffer();
        }
        n4 = n3 + n2;
        block0 : do {
            block7 : {
                if (n2 >= n4) {
                    return;
                }
                do {
                    int n5;
                    byte[] arrby;
                    if ((n3 = arrc[n2]) > 127) {
                        n3 = n2 + 1;
                        if ((n2 = arrc[n2]) < 2048) {
                            arrby = this._outputBuffer;
                            n5 = this._outputTail;
                            this._outputTail = n5 + 1;
                            arrby[n5] = (byte)(n2 >> 6 | 192);
                            arrby = this._outputBuffer;
                            n5 = this._outputTail;
                            this._outputTail = n5 + 1;
                            arrby[n5] = (byte)(n2 & 63 | 128);
                            n2 = n3;
                            continue block0;
                        }
                        break block7;
                    }
                    arrby = this._outputBuffer;
                    n5 = this._outputTail;
                    this._outputTail = n5 + 1;
                    arrby[n5] = (byte)n3;
                    n2 = n3 = n2 + 1;
                } while (n3 < n4);
                return;
            }
            n2 = this._outputRawMultiByteChar(n2, arrc, n3, n4);
        } while (true);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n2, int n3) throws IOException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby2[n4] = 34;
        this._writeBytes(arrby, n2, n3);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
    }

    @Override
    public final void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 91;
    }

    @Override
    public final void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 123;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeString(SerializableString serializableString) throws IOException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
        n2 = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (n2 < 0) {
            this._writeBytes(serializableString.asQuotedUTF8());
        } else {
            this._outputTail += n2;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    @Override
    public void writeString(String string2) throws IOException {
        this._verifyValueWrite("write text value");
        if (string2 == null) {
            this._writeNull();
            return;
        }
        int n2 = string2.length();
        if (n2 > this._charBufferLength) {
            this._writeStringSegments(string2, true);
            return;
        }
        string2.getChars(0, n2, this._charBuffer, 0);
        if (n2 > this._outputMaxContiguous) {
            this._writeLongString(this._charBuffer, 0, n2);
            return;
        }
        if (this._outputTail + n2 >= this._outputEnd) {
            this._flushBuffer();
        }
        string2 = (String)this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        string2[n3] = (String)34;
        this._writeStringSegment(this._charBuffer, 0, n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        string2 = (String)this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        string2[n2] = (String)34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeString(char[] arrc, int n2, int n3) throws IOException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby[n4] = 34;
        if (n3 <= this._outputMaxContiguous) {
            if (this._outputTail + n3 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(arrc, n2, n3);
        } else {
            this._writeStringSegments(arrc, n2, n3);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeUTF8String(byte[] arrby, int n2, int n3) throws IOException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby2[n4] = 34;
        if (n3 <= this._outputMaxContiguous) {
            this._writeUTF8Segment(arrby, n2, n3);
        } else {
            this._writeUTF8Segments(arrby, n2, n3);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
    }
}

