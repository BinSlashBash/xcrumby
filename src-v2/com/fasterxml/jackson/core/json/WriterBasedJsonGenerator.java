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
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class WriterBasedJsonGenerator
extends JsonGeneratorImpl {
    protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
    protected static final int SHORT_WRITE = 32;
    protected SerializableString _currentEscape;
    protected char[] _entityBuffer;
    protected char[] _outputBuffer;
    protected int _outputEnd;
    protected int _outputHead = 0;
    protected int _outputTail = 0;
    protected final Writer _writer;

    public WriterBasedJsonGenerator(IOContext iOContext, int n2, ObjectCodec objectCodec, Writer writer) {
        super(iOContext, n2, objectCodec);
        this._writer = writer;
        this._outputBuffer = iOContext.allocConcatBuffer();
        this._outputEnd = this._outputBuffer.length;
    }

    private char[] _allocateEntityBuffer() {
        char[] arrc = new char[14];
        arrc[0] = 92;
        arrc[2] = 92;
        arrc[3] = 117;
        arrc[4] = 48;
        arrc[5] = 48;
        arrc[8] = 92;
        arrc[9] = 117;
        this._entityBuffer = arrc;
        return arrc;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _appendCharacterEscape(char c2, int n2) throws IOException, JsonGenerationException {
        String string2;
        if (n2 >= 0) {
            if (this._outputTail + 2 > this._outputEnd) {
                this._flushBuffer();
            }
            char[] arrc = this._outputBuffer;
            c2 = (char)this._outputTail;
            this._outputTail = c2 + '\u0001';
            arrc[c2] = 92;
            arrc = this._outputBuffer;
            c2 = (char)this._outputTail;
            this._outputTail = c2 + '\u0001';
            arrc[c2] = (char)n2;
            return;
        }
        if (n2 != -2) {
            if (this._outputTail + 2 > this._outputEnd) {
                this._flushBuffer();
            }
            n2 = this._outputTail;
            char[] arrc = this._outputBuffer;
            int n3 = n2 + 1;
            arrc[n2] = 92;
            n2 = n3 + 1;
            arrc[n3] = 117;
            if (c2 > '\u00ff') {
                n3 = c2 >> 8 & 255;
                int n4 = n2 + 1;
                arrc[n2] = HEX_CHARS[n3 >> 4];
                n2 = n4 + 1;
                arrc[n4] = HEX_CHARS[n3 & 15];
                c2 = (char)(c2 & 255);
            } else {
                n3 = n2 + 1;
                arrc[n2] = 48;
                n2 = n3 + 1;
                arrc[n3] = 48;
            }
            n3 = n2 + 1;
            arrc[n2] = HEX_CHARS[c2 >> 4];
            arrc[n3] = HEX_CHARS[c2 & 15];
            this._outputTail = n3 + 1;
            return;
        }
        if (this._currentEscape == null) {
            string2 = this._characterEscapes.getEscapeSequence(c2).getValue();
        } else {
            string2 = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        if (this._outputTail + (c2 = (char)string2.length()) > this._outputEnd) {
            this._flushBuffer();
            if (c2 > this._outputEnd) {
                this._writer.write(string2);
                return;
            }
        }
        string2.getChars(0, c2, this._outputBuffer, this._outputTail);
        this._outputTail += c2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int _prependOrWriteCharacterEscape(char[] arrc, int n2, int n3, char c2, int n4) throws IOException, JsonGenerationException {
        String string2;
        if (n4 >= 0) {
            char[] arrc2;
            if (n2 > 1 && n2 < n3) {
                arrc[n2 -= 2] = 92;
                arrc[n2 + 1] = (char)n4;
                return n2;
            }
            arrc = arrc2 = this._entityBuffer;
            if (arrc2 == null) {
                arrc = this._allocateEntityBuffer();
            }
            arrc[1] = (char)n4;
            this._writer.write(arrc, 0, 2);
            return n2;
        }
        if (n4 != -2) {
            char[] arrc3;
            if (n2 > 5 && n2 < n3) {
                n3 = (n2 -= 6) + 1;
                arrc[n2] = 92;
                n2 = n3 + 1;
                arrc[n3] = 117;
                if (c2 > '\u00ff') {
                    n3 = c2 >> 8 & 255;
                    n4 = n2 + 1;
                    arrc[n2] = HEX_CHARS[n3 >> 4];
                    n2 = n4 + 1;
                    arrc[n4] = HEX_CHARS[n3 & 15];
                    c2 = (char)(c2 & 255);
                } else {
                    n3 = n2 + 1;
                    arrc[n2] = 48;
                    n2 = n3 + 1;
                    arrc[n3] = 48;
                }
                n3 = n2 + 1;
                arrc[n2] = HEX_CHARS[c2 >> 4];
                arrc[n3] = HEX_CHARS[c2 & 15];
                return n3 - 5;
            }
            arrc = arrc3 = this._entityBuffer;
            if (arrc3 == null) {
                arrc = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c2 > '\u00ff') {
                n3 = c2 >> 8 & 255;
                c2 = (char)(c2 & 255);
                arrc[10] = HEX_CHARS[n3 >> 4];
                arrc[11] = HEX_CHARS[n3 & 15];
                arrc[12] = HEX_CHARS[c2 >> 4];
                arrc[13] = HEX_CHARS[c2 & 15];
                this._writer.write(arrc, 8, 6);
                return n2;
            }
            arrc[6] = HEX_CHARS[c2 >> 4];
            arrc[7] = HEX_CHARS[c2 & 15];
            this._writer.write(arrc, 2, 6);
            return n2;
        }
        if (this._currentEscape == null) {
            string2 = this._characterEscapes.getEscapeSequence(c2).getValue();
        } else {
            string2 = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        if (n2 >= (c2 = (char)string2.length()) && n2 < n3) {
            string2.getChars(0, c2, arrc, n2 -= c2);
            return n2;
        }
        this._writer.write(string2);
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _prependOrWriteCharacterEscape(char c2, int n2) throws IOException, JsonGenerationException {
        String string2;
        if (n2 >= 0) {
            char[] arrc;
            if (this._outputTail >= 2) {
                c2 = (char)(this._outputTail - 2);
                this._outputHead = c2;
                this._outputBuffer[c2] = 92;
                this._outputBuffer[c2 + '\u0001'] = (char)n2;
                return;
            }
            char[] arrc2 = arrc = this._entityBuffer;
            if (arrc == null) {
                arrc2 = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            arrc2[1] = (char)n2;
            this._writer.write(arrc2, 0, 2);
            return;
        }
        if (n2 != -2) {
            char[] arrc;
            if (this._outputTail >= 6) {
                char[] arrc3 = this._outputBuffer;
                n2 = this._outputTail - 6;
                this._outputHead = n2++;
                arrc3[n2] = 92;
                arrc3[n2] = 117;
                if (c2 > '\u00ff') {
                    int n3 = c2 >> 8 & 255;
                    arrc3[++n2] = HEX_CHARS[n3 >> 4];
                    arrc3[++n2] = HEX_CHARS[n3 & 15];
                    c2 = (char)(c2 & 255);
                } else {
                    arrc3[++n2] = 48;
                    arrc3[++n2] = 48;
                }
                arrc3[++n2] = HEX_CHARS[c2 >> 4];
                arrc3[n2 + 1] = HEX_CHARS[c2 & 15];
                return;
            }
            char[] arrc4 = arrc = this._entityBuffer;
            if (arrc == null) {
                arrc4 = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c2 > '\u00ff') {
                n2 = c2 >> 8 & 255;
                c2 = (char)(c2 & 255);
                arrc4[10] = HEX_CHARS[n2 >> 4];
                arrc4[11] = HEX_CHARS[n2 & 15];
                arrc4[12] = HEX_CHARS[c2 >> 4];
                arrc4[13] = HEX_CHARS[c2 & 15];
                this._writer.write(arrc4, 8, 6);
                return;
            }
            arrc4[6] = HEX_CHARS[c2 >> 4];
            arrc4[7] = HEX_CHARS[c2 & 15];
            this._writer.write(arrc4, 2, 6);
            return;
        }
        if (this._currentEscape == null) {
            string2 = this._characterEscapes.getEscapeSequence(c2).getValue();
        } else {
            string2 = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        if (this._outputTail >= (c2 = (char)string2.length())) {
            this._outputHead = n2 = this._outputTail - c2;
            string2.getChars(0, c2, this._outputBuffer, n2);
            return;
        }
        this._outputHead = this._outputTail;
        this._writer.write(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int _readMore(InputStream inputStream, byte[] arrby, int n2, int n3, int n4) throws IOException {
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

    /*
     * Enabled aggressive block sorting
     */
    private void _writeLongString(String string2) throws IOException {
        int n2;
        this._flushBuffer();
        int n3 = string2.length();
        int n4 = 0;
        do {
            if (n4 + (n2 = this._outputEnd) > n3) {
                n2 = n3 - n4;
            }
            string2.getChars(n4, n4 + n2, this._outputBuffer, 0);
            if (this._characterEscapes != null) {
                this._writeSegmentCustom(n2);
            } else if (this._maximumNonEscapedChar != 0) {
                this._writeSegmentASCII(n2, this._maximumNonEscapedChar);
            } else {
                this._writeSegment(n2);
            }
            n4 = n2 = n4 + n2;
        } while (n2 < n3);
    }

    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        int n2 = this._outputTail;
        char[] arrc = this._outputBuffer;
        arrc[n2] = 110;
        arrc[++n2] = 117;
        arrc[++n2] = 108;
        arrc[++n2] = 108;
        this._outputTail = n2 + 1;
    }

    private void _writeQuotedInt(int n2) throws IOException {
        if (this._outputTail + 13 >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc[n3] = 34;
        this._outputTail = NumberOutput.outputInt(n2, this._outputBuffer, this._outputTail);
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
    }

    private void _writeQuotedLong(long l2) throws IOException {
        if (this._outputTail + 23 >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this._outputTail = NumberOutput.outputLong(l2, this._outputBuffer, this._outputTail);
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
    }

    private void _writeQuotedRaw(Object object) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this.writeRaw(object.toString());
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        object = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        object[n2] = 34;
    }

    private void _writeQuotedShort(short s2) throws IOException {
        if (this._outputTail + 8 >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this._outputTail = NumberOutput.outputInt((int)s2, this._outputBuffer, this._outputTail);
        arrc = this._outputBuffer;
        s2 = (short)this._outputTail;
        this._outputTail = s2 + 1;
        arrc[s2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeSegment(int n2) throws IOException {
        int[] arrn = this._outputEscapes;
        int n3 = arrn.length;
        int n4 = 0;
        int n5 = 0;
        while (n4 < n2) {
            int n6;
            char c2;
            while ((c2 = this._outputBuffer[n4]) >= n3 || arrn[c2] == 0) {
                n4 = n6 = n4 + 1;
                if (n6 < n2) continue;
                n4 = n6;
                break;
            }
            if ((n6 = n4 - n5) > 0) {
                this._writer.write(this._outputBuffer, n5, n6);
                if (n4 >= n2) {
                    return;
                }
            }
            n5 = this._prependOrWriteCharacterEscape(this._outputBuffer, ++n4, n2, c2, arrn[c2]);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeSegmentASCII(int n2, int n3) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        int n4 = Math.min(arrn.length, n3 + 1);
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        while (n5 < n2) {
            char c2;
            int n8;
            block8 : {
                int n9;
                n8 = n6;
                do {
                    if ((c2 = this._outputBuffer[n5]) < n4) {
                        n6 = n8 = arrn[c2];
                        if (n8 != 0) {
                            n6 = n8;
                            break block8;
                        }
                    } else {
                        n6 = n8;
                        if (c2 > n3) {
                            n6 = -1;
                            break block8;
                        }
                    }
                    n5 = n9 = n5 + 1;
                    n8 = n6;
                } while (n9 < n2);
                n5 = n9;
            }
            if ((n8 = n5 - n7) > 0) {
                this._writer.write(this._outputBuffer, n7, n8);
                if (n5 >= n2) {
                    return;
                }
            }
            n7 = this._prependOrWriteCharacterEscape(this._outputBuffer, ++n5, n2, c2, n6);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeSegmentCustom(int n2) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        char c2 = this._maximumNonEscapedChar < 1 ? '\uffff' : this._maximumNonEscapedChar;
        int n3 = Math.min(arrn.length, c2 + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while (n4 < n2) {
            char c3;
            int n7;
            block9 : {
                int n8;
                n7 = n5;
                do {
                    if ((c3 = this._outputBuffer[n4]) < n3) {
                        n5 = n7 = arrn[c3];
                        if (n7 != 0) {
                            n5 = n7;
                            break block9;
                        }
                    } else {
                        SerializableString serializableString;
                        if (c3 > c2) {
                            n5 = -1;
                            break block9;
                        }
                        this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c3);
                        n5 = n7;
                        if (serializableString != null) {
                            n5 = -2;
                            break block9;
                        }
                    }
                    n4 = n8 = n4 + 1;
                    n7 = n5;
                } while (n8 < n2);
                n4 = n8;
            }
            if ((n7 = n4 - n6) > 0) {
                this._writer.write(this._outputBuffer, n6, n7);
                if (n4 >= n2) {
                    return;
                }
            }
            n6 = this._prependOrWriteCharacterEscape(this._outputBuffer, ++n4, n2, c3, n5);
        }
    }

    private void _writeString(String string2) throws IOException {
        int n2 = string2.length();
        if (n2 > this._outputEnd) {
            this._writeLongString(string2);
            return;
        }
        if (this._outputTail + n2 > this._outputEnd) {
            this._flushBuffer();
        }
        string2.getChars(0, n2, this._outputBuffer, this._outputTail);
        if (this._characterEscapes != null) {
            this._writeStringCustom(n2);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(n2, this._maximumNonEscapedChar);
            return;
        }
        this._writeString2(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeString(char[] arrc, int n2, int n3) throws IOException {
        if (this._characterEscapes != null) {
            this._writeStringCustom(arrc, n2, n3);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(arrc, n2, n3, this._maximumNonEscapedChar);
            return;
        }
        int n4 = n3 + n2;
        int[] arrn = this._outputEscapes;
        int n5 = arrn.length;
        while (n2 < n4) {
            int n6;
            n3 = n2;
            while ((n6 = arrc[n3]) >= n5 || arrn[n6] == 0) {
                n3 = n6 = n3 + 1;
                if (n6 < n4) continue;
                n3 = n6;
                break;
            }
            if ((n6 = n3 - n2) < 32) {
                if (this._outputTail + n6 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n6 > 0) {
                    System.arraycopy(arrc, n2, this._outputBuffer, this._outputTail, n6);
                    this._outputTail += n6;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n2, n6);
            }
            if (n3 >= n4) {
                return;
            }
            n2 = n3 + 1;
            char c2 = arrc[n3];
            this._appendCharacterEscape(c2, arrn[c2]);
        }
        return;
    }

    private void _writeString2(int n2) throws IOException {
        n2 = this._outputTail + n2;
        int[] arrn = this._outputEscapes;
        int n3 = arrn.length;
        block0 : while (this._outputTail < n2) {
            int n4;
            do {
                if ((n4 = this._outputBuffer[this._outputTail]) < n3 && arrn[n4] != 0) {
                    n4 = this._outputTail - this._outputHead;
                    if (n4 > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, n4);
                    }
                    char[] arrc = this._outputBuffer;
                    n4 = this._outputTail;
                    this._outputTail = n4 + 1;
                    char c2 = arrc[n4];
                    this._prependOrWriteCharacterEscape(c2, arrn[c2]);
                    continue block0;
                }
                this._outputTail = n4 = this._outputTail + 1;
            } while (n4 < n2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void _writeStringASCII(int var1_1, int var2_2) throws IOException, JsonGenerationException {
        var4_3 = this._outputTail + var1_1;
        var7_4 = this._outputEscapes;
        var5_5 = Math.min(var7_4.length, var2_2 + 1);
        block0 : do {
            if (this._outputTail >= var4_3) return;
            do {
                if ((var3_6 = this._outputBuffer[this._outputTail]) >= var5_5) ** GOTO lbl11
                var1_1 = var7_4[var3_6];
                if (var1_1 == 0) ** GOTO lbl18
                ** GOTO lbl13
lbl11: // 1 sources:
                if (var3_6 > var2_2) {
                    var1_1 = -1;
lbl13: // 2 sources:
                    if ((var6_7 = this._outputTail - this._outputHead) > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, var6_7);
                    }
                    ++this._outputTail;
                    this._prependOrWriteCharacterEscape(var3_6, var1_1);
                    continue block0;
                }
lbl18: // 3 sources:
                this._outputTail = var1_1 = this._outputTail + 1;
            } while (var1_1 < var4_3);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeStringASCII(char[] arrc, int n2, int n3, int n4) throws IOException, JsonGenerationException {
        int n5 = n3 + n2;
        int[] arrn = this._outputEscapes;
        int n6 = Math.min(arrn.length, n4 + 1);
        int n7 = 0;
        n3 = n2;
        n2 = n7;
        while (n3 < n5) {
            int n8;
            char c2;
            block10 : {
                int n9;
                n7 = n3;
                n8 = n2;
                do {
                    if ((c2 = arrc[n7]) < n6) {
                        n2 = n8 = arrn[c2];
                        if (n8 != 0) {
                            n2 = n8;
                            break block10;
                        }
                    } else {
                        n2 = n8;
                        if (c2 > n4) {
                            n2 = -1;
                            break block10;
                        }
                    }
                    n9 = n7 + 1;
                    n8 = n2;
                    n7 = n9;
                } while (n9 < n5);
                n7 = n9;
            }
            if ((n8 = n7 - n3) < 32) {
                if (this._outputTail + n8 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n8 > 0) {
                    System.arraycopy(arrc, n3, this._outputBuffer, this._outputTail, n8);
                    this._outputTail += n8;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n3, n8);
            }
            if (n7 >= n5) break;
            n3 = n7 + 1;
            this._appendCharacterEscape(c2, n2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void _writeStringCustom(int var1_1) throws IOException, JsonGenerationException {
        var4_2 = this._outputTail + var1_1;
        var7_3 = this._outputEscapes;
        var3_4 = this._maximumNonEscapedChar < 1 ? '\uffff' : this._maximumNonEscapedChar;
        var5_5 = Math.min(var7_3.length, var3_4 + 1);
        var8_6 = this._characterEscapes;
        block0 : do {
            if (this._outputTail >= var4_2) return;
            do {
                if ((var2_7 = this._outputBuffer[this._outputTail]) >= var5_5) ** GOTO lbl13
                var1_1 = var7_3[var2_7];
                if (var1_1 == 0) ** GOTO lbl24
                ** GOTO lbl19
lbl13: // 1 sources:
                if (var2_7 <= var3_4) ** GOTO lbl16
                var1_1 = -1;
                ** GOTO lbl19
lbl16: // 1 sources:
                this._currentEscape = var9_9 = var8_6.getEscapeSequence(var2_7);
                if (var9_9 != null) {
                    var1_1 = -2;
lbl19: // 3 sources:
                    if ((var6_8 = this._outputTail - this._outputHead) > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, var6_8);
                    }
                    ++this._outputTail;
                    this._prependOrWriteCharacterEscape(var2_7, var1_1);
                    continue block0;
                }
lbl24: // 3 sources:
                this._outputTail = var1_1 = this._outputTail + 1;
            } while (var1_1 < var4_2);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeStringCustom(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        int n4 = n3 + n2;
        int[] arrn = this._outputEscapes;
        char c2 = this._maximumNonEscapedChar < 1 ? '\uffff' : this._maximumNonEscapedChar;
        int n5 = Math.min(arrn.length, c2 + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n6 = 0;
        n3 = n2;
        n2 = n6;
        while (n3 < n4) {
            char c3;
            int n7;
            block11 : {
                int n8;
                n6 = n3;
                n7 = n2;
                do {
                    if ((c3 = arrc[n6]) < n5) {
                        n2 = n7 = arrn[c3];
                        if (n7 != 0) {
                            n2 = n7;
                            break block11;
                        }
                    } else {
                        SerializableString serializableString;
                        if (c3 > c2) {
                            n2 = -1;
                            break block11;
                        }
                        this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c3);
                        n2 = n7;
                        if (serializableString != null) {
                            n2 = -2;
                            break block11;
                        }
                    }
                    n8 = n6 + 1;
                    n7 = n2;
                    n6 = n8;
                } while (n8 < n4);
                n6 = n8;
            }
            if ((n7 = n6 - n3) < 32) {
                if (this._outputTail + n7 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n7 > 0) {
                    System.arraycopy(arrc, n3, this._outputBuffer, this._outputTail, n7);
                    this._outputTail += n7;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n3, n7);
            }
            if (n6 >= n4) break;
            n3 = n6 + 1;
            this._appendCharacterEscape(c3, n2);
        }
    }

    private void writeRawLong(String string2) throws IOException {
        int n2;
        int n3 = this._outputEnd - this._outputTail;
        string2.getChars(0, n3, this._outputBuffer, this._outputTail);
        this._outputTail += n3;
        this._flushBuffer();
        int n4 = n3;
        for (n3 = string2.length() - n3; n3 > this._outputEnd; n3 -= n2) {
            n2 = this._outputEnd;
            string2.getChars(n4, n4 + n2, this._outputBuffer, 0);
            this._outputHead = 0;
            this._outputTail = n2;
            this._flushBuffer();
            n4 += n2;
        }
        string2.getChars(n4, n4 + n3, this._outputBuffer, 0);
        this._outputHead = 0;
        this._outputTail = n3;
    }

    protected void _flushBuffer() throws IOException {
        int n2 = this._outputTail - this._outputHead;
        if (n2 > 0) {
            int n3 = this._outputHead;
            this._outputHead = 0;
            this._outputTail = 0;
            this._writer.write(this._outputBuffer, n3, n2);
        }
    }

    @Override
    protected void _releaseBuffers() {
        char[] arrc = this._outputBuffer;
        if (arrc != null) {
            this._outputBuffer = null;
            this._ioContext.releaseConcatBuffer(arrc);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _verifyPrettyValueWrite(String string2) throws IOException {
        int n2 = this._writeContext.writeValue();
        if (n2 == 5) {
            this._reportError("Can not " + string2 + ", expecting field name");
        }
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
    protected void _verifyValueWrite(String var1_1) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite(var1_1);
            return;
        }
        var3_2 = this._writeContext.writeValue();
        if (var3_2 == 5) {
            this._reportError("Can not " + var1_1 + ", expecting field name");
        }
        switch (var3_2) {
            default: {
                return;
            }
            case 1: {
                var2_3 = 44;
                ** GOTO lbl15
            }
            case 2: {
                var2_3 = 58;
lbl15: // 2 sources:
                if (this._outputTail >= this._outputEnd) {
                    this._flushBuffer();
                }
                this._outputBuffer[this._outputTail] = var2_3;
                ++this._outputTail;
                return;
            }
            case 3: 
        }
        if (this._rootValueSeparator == null) return;
        this.writeRaw(this._rootValueSeparator.getValue());
    }

    protected int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] arrby) throws IOException, JsonGenerationException {
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
                char[] arrc = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrc[n2] = 92;
                arrc = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrc[n2] = 110;
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
    protected int _writeBinary(Base64Variant var1_1, InputStream var2_2, byte[] var3_3, int var4_4) throws IOException, JsonGenerationException {
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

    protected void _writeBinary(Base64Variant base64Variant, byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
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
                char[] arrc = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrc[n2] = 92;
                arrc = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrc[n2] = 110;
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

    protected void _writeFieldName(SerializableString serializableString, boolean bl2) throws IOException {
        char[] arrc;
        int n2;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(serializableString, bl2);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (bl2) {
            arrc = this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrc[n2] = 44;
        }
        serializableString = (SerializableString)serializableString.asQuotedChars();
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this.writeRaw((char[])serializableString, 0, serializableString.length);
            return;
        }
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        n2 = serializableString.length;
        if (this._outputTail + n2 + 1 >= this._outputEnd) {
            this.writeRaw((char[])serializableString, 0, n2);
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            serializableString = (SerializableString)this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            serializableString[n2] = (SerializableString)34;
            return;
        }
        System.arraycopy(serializableString, 0, this._outputBuffer, this._outputTail, n2);
        this._outputTail += n2;
        serializableString = (SerializableString)this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        serializableString[n2] = (SerializableString)34;
    }

    protected void _writeFieldName(String string2, boolean bl2) throws IOException {
        char[] arrc;
        int n2;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(string2, bl2);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (bl2) {
            arrc = this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrc[n2] = 44;
        }
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this._writeString(string2);
            return;
        }
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this._writeString(string2);
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
    protected void _writePPFieldName(SerializableString serializableString, boolean bl2) throws IOException, JsonGenerationException {
        void var2_4;
        if (var2_4 != false) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        char[] arrc = serializableString.asQuotedChars();
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this.writeRaw(arrc, 0, arrc.length);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc2[n2] = 34;
        this.writeRaw(arrc, 0, arrc.length);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc3 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc3[n2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _writePPFieldName(String string2, boolean bl2) throws IOException, JsonGenerationException {
        void var2_3;
        if (var2_3 != false) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this._writeString(string2);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this._writeString(string2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc2[n2] = 34;
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
        if (this._writer != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                this._writer.close();
            } else if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                this._writer.flush();
            }
        }
        this._releaseBuffers();
    }

    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._writer != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
            this._writer.flush();
        }
    }

    @Override
    public Object getOutputTarget() {
        return this._writer;
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
        var5_6 = (char[])this._ioContext.allocBase64Buffer();
        if (var3_5 >= 0) ** GOTO lbl13
        try {
            var3_5 = this._writeBinary(var1_1 /* !! */ , (InputStream)var2_4, (byte[])var5_6);
            ** GOTO lbl16
lbl13: // 1 sources:
            var4_7 = this._writeBinary(var1_1 /* !! */ , (InputStream)var2_4, (byte[])var5_6, var3_5);
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
            this._ioContext.releaseBase64Buffer((byte[])var5_6);
        }
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc[n4] = 34;
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
    public void writeBoolean(boolean bl2) throws IOException {
        this._verifyValueWrite("write boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
        }
        int n2 = this._outputTail;
        char[] arrc = this._outputBuffer;
        if (bl2) {
            arrc[n2] = 116;
            arrc[++n2] = 114;
            arrc[++n2] = 117;
            arrc[++n2] = 101;
        } else {
            arrc[n2] = 102;
            arrc[++n2] = 97;
            arrc[++n2] = 108;
            arrc[++n2] = 115;
            arrc[++n2] = 101;
        }
        this._outputTail = n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeEndArray() throws IOException, JsonGenerationException {
        if (!this._writeContext.inArray()) {
            this._reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            char[] arrc = this._outputBuffer;
            int n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrc[n2] = 93;
        }
        this._writeContext = this._writeContext.getParent();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeEndObject() throws IOException, JsonGenerationException {
        if (!this._writeContext.inObject()) {
            this._reportError("Current context not an object but " + this._writeContext.getTypeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            char[] arrc = this._outputBuffer;
            int n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrc[n2] = 125;
        }
        this._writeContext = this._writeContext.getParent();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        boolean bl2 = true;
        int n2 = this._writeContext.writeFieldName(serializableString.getValue());
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 != 1) {
            bl2 = false;
        }
        this._writeFieldName(serializableString, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeFieldName(String string2) throws IOException {
        boolean bl2 = true;
        int n2 = this._writeContext.writeFieldName(string2);
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 != 1) {
            bl2 = false;
        }
        this._writeFieldName(string2, bl2);
    }

    @Override
    public void writeNull() throws IOException {
        this._verifyValueWrite("write null value");
        this._writeNull();
    }

    @Override
    public void writeNumber(double d2) throws IOException {
        if (this._cfgNumbersAsStrings || this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Double.isNaN(d2) || Double.isInfinite(d2))) {
            this.writeString(String.valueOf(d2));
            return;
        }
        this._verifyValueWrite("write number");
        this.writeRaw(String.valueOf(d2));
    }

    @Override
    public void writeNumber(float f2) throws IOException {
        if (this._cfgNumbersAsStrings || this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Float.isNaN(f2) || Float.isInfinite(f2))) {
            this.writeString(String.valueOf(f2));
            return;
        }
        this._verifyValueWrite("write number");
        this.writeRaw(String.valueOf(f2));
    }

    @Override
    public void writeNumber(int n2) throws IOException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(n2);
            return;
        }
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(n2, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(long l2) throws IOException {
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
    public void writeNumber(String string2) throws IOException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(string2);
            return;
        }
        this.writeRaw(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
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
    public void writeNumber(BigInteger bigInteger) throws IOException {
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
    public void writeNumber(short s2) throws IOException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(s2);
            return;
        }
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt((int)s2, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeRaw(char c2) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = c2;
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException {
        this.writeRaw(serializableString.getValue());
    }

    @Override
    public void writeRaw(String string2) throws IOException {
        int n2;
        int n3 = string2.length();
        int n4 = n2 = this._outputEnd - this._outputTail;
        if (n2 == 0) {
            this._flushBuffer();
            n4 = this._outputEnd - this._outputTail;
        }
        if (n4 >= n3) {
            string2.getChars(0, n3, this._outputBuffer, this._outputTail);
            this._outputTail += n3;
            return;
        }
        this.writeRawLong(string2);
    }

    @Override
    public void writeRaw(String string2, int n2, int n3) throws IOException {
        int n4;
        int n5 = n4 = this._outputEnd - this._outputTail;
        if (n4 < n3) {
            this._flushBuffer();
            n5 = this._outputEnd - this._outputTail;
        }
        if (n5 >= n3) {
            string2.getChars(n2, n2 + n3, this._outputBuffer, this._outputTail);
            this._outputTail += n3;
            return;
        }
        this.writeRawLong(string2.substring(n2, n2 + n3));
    }

    @Override
    public void writeRaw(char[] arrc, int n2, int n3) throws IOException {
        if (n3 < 32) {
            if (n3 > this._outputEnd - this._outputTail) {
                this._flushBuffer();
            }
            System.arraycopy(arrc, n2, this._outputBuffer, this._outputTail, n3);
            this._outputTail += n3;
            return;
        }
        this._flushBuffer();
        this._writer.write(arrc, n2, n3);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n2, int n3) throws IOException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeStartArray() throws IOException, JsonGenerationException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 91;
    }

    @Override
    public void writeStartObject() throws IOException, JsonGenerationException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 123;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeString(SerializableString serializableString) throws IOException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        char[] arrc2 = serializableString.asQuotedChars();
        n2 = arrc2.length;
        if (n2 < 32) {
            if (n2 > this._outputEnd - this._outputTail) {
                this._flushBuffer();
            }
            System.arraycopy(arrc2, 0, this._outputBuffer, this._outputTail, n2);
            this._outputTail += n2;
        } else {
            this._flushBuffer();
            this._writer.write(arrc2, 0, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc3 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc3[n2] = 34;
    }

    @Override
    public void writeString(String string2) throws IOException {
        this._verifyValueWrite("write text value");
        if (string2 == null) {
            this._writeNull();
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this._writeString(string2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        string2 = (String)this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        string2[n2] = (String)34;
    }

    @Override
    public void writeString(char[] arrc, int n2, int n3) throws IOException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc2[n4] = 34;
        this._writeString(arrc, n2, n3);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n2, int n3) throws IOException {
        this._reportUnsupportedOperation();
    }
}

