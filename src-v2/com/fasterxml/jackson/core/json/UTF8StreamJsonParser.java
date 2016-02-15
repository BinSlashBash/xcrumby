/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class UTF8StreamJsonParser
extends ParserBase {
    static final byte BYTE_LF = 10;
    protected static final int[] _icLatin1;
    private static final int[] _icUTF8;
    protected boolean _bufferRecyclable;
    protected byte[] _inputBuffer;
    protected InputStream _inputStream;
    protected ObjectCodec _objectCodec;
    private int _quad1;
    protected int[] _quadBuffer = new int[16];
    protected final BytesToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete = false;

    static {
        _icUTF8 = CharTypes.getInputCodeUtf8();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }

    public UTF8StreamJsonParser(IOContext iOContext, int n2, InputStream inputStream, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, byte[] arrby, int n3, int n4, boolean bl2) {
        super(iOContext, n2);
        this._inputStream = inputStream;
        this._objectCodec = objectCodec;
        this._symbols = bytesToNameCanonicalizer;
        this._inputBuffer = arrby;
        this._inputPtr = n3;
        this._inputEnd = n4;
        this._currInputRowStart = n3;
        this._currInputProcessed = - n3;
        this._bufferRecyclable = bl2;
    }

    private final void _checkMatchEnd(String string2, int n2, int n3) throws IOException {
        if (Character.isJavaIdentifierPart((char)this._decodeCharForError(n3))) {
            this._reportInvalidToken(string2.substring(0, n2));
        }
    }

    private final int _decodeUtf8_2(int n2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (((n3 = arrby[n3]) & 192) != 128) {
            this._reportInvalidOther(n3 & 255, this._inputPtr);
        }
        return (n2 & 31) << 6 | n3 & 63;
    }

    private final int _decodeUtf8_3(int n2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (((n3 = arrby[n3]) & 192) != 128) {
            this._reportInvalidOther(n3 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        int n4 = this._inputPtr;
        this._inputPtr = n4 + 1;
        if (((n4 = arrby[n4]) & 192) != 128) {
            this._reportInvalidOther(n4 & 255, this._inputPtr);
        }
        return ((n2 & 15) << 6 | n3 & 63) << 6 | n4 & 63;
    }

    private final int _decodeUtf8_3fast(int n2) throws IOException {
        byte[] arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (((n3 = arrby[n3]) & 192) != 128) {
            this._reportInvalidOther(n3 & 255, this._inputPtr);
        }
        arrby = this._inputBuffer;
        int n4 = this._inputPtr;
        this._inputPtr = n4 + 1;
        if (((n4 = arrby[n4]) & 192) != 128) {
            this._reportInvalidOther(n4 & 255, this._inputPtr);
        }
        return ((n2 & 15) << 6 | n3 & 63) << 6 | n4 & 63;
    }

    private final int _decodeUtf8_4(int n2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (((n3 = arrby[n3]) & 192) != 128) {
            this._reportInvalidOther(n3 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        int n4 = this._inputPtr;
        this._inputPtr = n4 + 1;
        if (((n4 = arrby[n4]) & 192) != 128) {
            this._reportInvalidOther(n4 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        int n5 = this._inputPtr;
        this._inputPtr = n5 + 1;
        if (((n5 = arrby[n5]) & 192) != 128) {
            this._reportInvalidOther(n5 & 255, this._inputPtr);
        }
        return ((((n2 & 7) << 6 | n3 & 63) << 6 | n4 & 63) << 6 | n5 & 63) - 65536;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final void _finishString2(char[] var1_1, int var2_2) throws IOException {
        var7_3 = UTF8StreamJsonParser._icUTF8;
        var8_4 = this._inputBuffer;
        var6_5 = var1_1;
        do {
            block14 : {
                var3_6 = var4_7 = this._inputPtr;
                if (var4_7 >= this._inputEnd) {
                    this.loadMoreGuaranteed();
                    var3_6 = this._inputPtr;
                }
                var1_1 = var6_5;
                var4_7 = var2_2;
                if (var2_2 >= var6_5.length) {
                    var1_1 = this._textBuffer.finishCurrentSegment();
                    var4_7 = 0;
                }
                var5_8 = Math.min(this._inputEnd, var1_1.length - var4_7 + var3_6);
                var2_2 = var4_7;
                while (var3_6 < var5_8) {
                    var4_7 = var3_6 + 1;
                    if (var7_3[var3_6 = var8_4[var3_6] & 255] != 0) {
                        this._inputPtr = var4_7;
                        if (var3_6 == 34) {
                            this._textBuffer.setCurrentLength(var2_2);
                            return;
                        }
                        break block14;
                    }
                    var1_1[var2_2] = (char)var3_6;
                    var3_6 = var4_7;
                    ++var2_2;
                }
                this._inputPtr = var3_6;
                var6_5 = var1_1;
                continue;
            }
            switch (var7_3[var3_6]) {
                default: {
                    if (var3_6 >= 32) break;
                    this._throwUnquotedSpace(var3_6, "string value");
                    ** GOTO lbl59
                }
                case 1: {
                    var3_6 = this._decodeEscaped();
                    ** GOTO lbl59
                }
                case 2: {
                    var3_6 = this._decodeUtf8_2(var3_6);
                    ** GOTO lbl59
                }
                case 3: {
                    var3_6 = this._inputEnd - this._inputPtr >= 2 ? this._decodeUtf8_3fast(var3_6) : this._decodeUtf8_3(var3_6);
                    ** GOTO lbl59
                }
                case 4: {
                    var4_7 = this._decodeUtf8_4(var3_6);
                    var3_6 = var2_2 + 1;
                    var1_1[var2_2] = (char)(55296 | var4_7 >> 10);
                    var6_5 = var1_1;
                    var2_2 = var3_6;
                    if (var3_6 >= var1_1.length) {
                        var6_5 = this._textBuffer.finishCurrentSegment();
                        var2_2 = 0;
                    }
                    var3_6 = 56320 | var4_7 & 1023;
                    var1_1 = var6_5;
                    ** GOTO lbl59
                }
            }
            this._reportInvalidChar(var3_6);
lbl59: // 6 sources:
            var6_5 = var1_1;
            var4_7 = var2_2;
            if (var2_2 >= var1_1.length) {
                var6_5 = this._textBuffer.finishCurrentSegment();
                var4_7 = 0;
            }
            var6_5[var4_7] = (char)var3_6;
            var2_2 = var4_7 + 1;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final boolean _isNextTokenNameMaybe(int n2, SerializableString object) throws IOException {
        String string2 = this._parseName(n2).getName();
        this._parsingContext.setCurrentName(string2);
        boolean bl2 = string2.equals(object.getValue());
        this._currToken = JsonToken.FIELD_NAME;
        n2 = this._skipColon();
        if (n2 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return bl2;
        }
        switch (n2) {
            default: {
                object = this._handleUnexpectedValue(n2);
                break;
            }
            case 91: {
                object = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                object = JsonToken.START_OBJECT;
                break;
            }
            case 116: {
                this._matchToken("true", 1);
                object = JsonToken.VALUE_TRUE;
                break;
            }
            case 102: {
                this._matchToken("false", 1);
                object = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchToken("null", 1);
                object = JsonToken.VALUE_NULL;
                break;
            }
            case 45: {
                object = this._parseNegNumber();
                break;
            }
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                object = this._parsePosNumber(n2);
            }
        }
        this._nextToken = object;
        return bl2;
    }

    private final void _isNextTokenNameYes(int n2) throws IOException {
        this._currToken = JsonToken.FIELD_NAME;
        switch (n2) {
            default: {
                this._nextToken = this._handleUnexpectedValue(n2);
                return;
            }
            case 34: {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
                return;
            }
            case 91: {
                this._nextToken = JsonToken.START_ARRAY;
                return;
            }
            case 123: {
                this._nextToken = JsonToken.START_OBJECT;
                return;
            }
            case 116: {
                this._matchToken("true", 1);
                this._nextToken = JsonToken.VALUE_TRUE;
                return;
            }
            case 102: {
                this._matchToken("false", 1);
                this._nextToken = JsonToken.VALUE_FALSE;
                return;
            }
            case 110: {
                this._matchToken("null", 1);
                this._nextToken = JsonToken.VALUE_NULL;
                return;
            }
            case 45: {
                this._nextToken = this._parseNegNumber();
                return;
            }
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: 
        }
        this._nextToken = this._parsePosNumber(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _matchToken2(String string2, int n2) throws IOException {
        int n3;
        int n4 = string2.length();
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore() || this._inputBuffer[this._inputPtr] != string2.charAt(n2)) {
                this._reportInvalidToken(string2.substring(0, n2));
            }
            ++this._inputPtr;
            n2 = n3 = n2 + 1;
        } while (n3 < n4);
        if (this._inputPtr >= this._inputEnd && !this.loadMore() || (n2 = this._inputBuffer[this._inputPtr] & 255) < 48 || n2 == 93 || n2 == 125) {
            return;
        }
        this._checkMatchEnd(string2, n3, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken _nextTokenNotInObject(int n2) throws IOException {
        JsonToken jsonToken;
        if (n2 == 34) {
            JsonToken jsonToken2;
            this._tokenIncomplete = true;
            this._currToken = jsonToken2 = JsonToken.VALUE_STRING;
            return jsonToken2;
        }
        switch (n2) {
            default: {
                JsonToken jsonToken3;
                this._currToken = jsonToken3 = this._handleUnexpectedValue(n2);
                return jsonToken3;
            }
            case 91: {
                JsonToken jsonToken4;
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                this._currToken = jsonToken4 = JsonToken.START_ARRAY;
                return jsonToken4;
            }
            case 123: {
                JsonToken jsonToken5;
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                this._currToken = jsonToken5 = JsonToken.START_OBJECT;
                return jsonToken5;
            }
            case 116: {
                JsonToken jsonToken6;
                this._matchToken("true", 1);
                this._currToken = jsonToken6 = JsonToken.VALUE_TRUE;
                return jsonToken6;
            }
            case 102: {
                JsonToken jsonToken7;
                this._matchToken("false", 1);
                this._currToken = jsonToken7 = JsonToken.VALUE_FALSE;
                return jsonToken7;
            }
            case 110: {
                JsonToken jsonToken8;
                this._matchToken("null", 1);
                this._currToken = jsonToken8 = JsonToken.VALUE_NULL;
                return jsonToken8;
            }
            case 45: {
                JsonToken jsonToken9;
                this._currToken = jsonToken9 = this._parseNegNumber();
                return jsonToken9;
            }
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: 
        }
        this._currToken = jsonToken = this._parsePosNumber(n2);
        return jsonToken;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final JsonToken _parseFloat(char[] var1_1, int var2_2, int var3_3, boolean var4_4, int var5_5) throws IOException {
        var11_6 = 0;
        var10_7 = 0;
        var6_8 = 0;
        var12_9 = 0;
        var14_10 = var1_1;
        var7_11 = var2_2++;
        var8_12 = var3_3;
        if (var3_3 == 46) {
            var1_1[var2_2] = (char)var3_3;
            var9_13 = var3_3;
            var3_3 = var10_7;
            do {
                if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                    var10_7 = 1;
                    break;
                }
                var14_10 = this._inputBuffer;
                var6_8 = this._inputPtr;
                this._inputPtr = var6_8 + 1;
                var6_8 = var14_10[var6_8] & 255;
                var10_7 = var12_9;
                var9_13 = var6_8;
                if (var6_8 < 48) break;
                var10_7 = var12_9;
                var9_13 = var6_8;
                if (var6_8 > 57) break;
                var7_11 = var3_3 + 1;
                var14_10 = var1_1;
                var3_3 = var2_2;
                if (var2_2 >= var1_1.length) {
                    var14_10 = this._textBuffer.finishCurrentSegment();
                    var3_3 = 0;
                }
                var14_10[var3_3] = (char)var6_8;
                var2_2 = var3_3 + 1;
                var3_3 = var7_11;
                var1_1 = var14_10;
                var9_13 = var6_8;
            } while (true);
            var6_8 = var10_7;
            var11_6 = var3_3;
            var14_10 = var1_1;
            var7_11 = var2_2;
            var8_12 = var9_13;
            if (var3_3 == 0) {
                this.reportUnexpectedNumberChar(var9_13, "Decimal point not followed by a digit");
                var8_12 = var9_13;
                var7_11 = var2_2;
                var14_10 = var1_1;
                var11_6 = var3_3;
                var6_8 = var10_7;
            }
        }
        var9_13 = 0;
        var10_7 = 0;
        if (var8_12 == 101) ** GOTO lbl57
        var2_2 = var6_8;
        var12_9 = var7_11;
        var13_14 = var8_12;
        if (var8_12 != 69) ** GOTO lbl114
lbl57: // 2 sources:
        var1_1 = var14_10;
        var3_3 = var7_11;
        if (var7_11 >= var14_10.length) {
            var1_1 = this._textBuffer.finishCurrentSegment();
            var3_3 = 0;
        }
        var2_2 = var3_3 + 1;
        var1_1[var3_3] = (char)var8_12;
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        var14_10 = this._inputBuffer;
        var3_3 = this._inputPtr;
        this._inputPtr = var3_3 + 1;
        if ((var3_3 = var14_10[var3_3] & 255) == 45 || var3_3 == 43) {
            if (var2_2 >= var1_1.length) {
                var1_1 = this._textBuffer.finishCurrentSegment();
                var2_2 = 0;
            }
            var1_1[var2_2] = (char)var3_3;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            var14_10 = this._inputBuffer;
            var3_3 = this._inputPtr;
            this._inputPtr = var3_3 + 1;
            var3_3 = var14_10[var3_3] & 255;
            ++var2_2;
            var9_13 = var10_7;
        } else {
            var9_13 = var10_7;
        }
        do {
            var10_7 = var6_8;
            var8_12 = var9_13;
            var7_11 = var2_2;
            if (var3_3 > 57) ** GOTO lbl104
            var10_7 = var6_8;
            var8_12 = var9_13;
            var7_11 = var2_2;
            if (var3_3 < 48) ** GOTO lbl104
            var8_12 = var9_13 + 1;
            var14_10 = var1_1;
            var7_11 = var2_2;
            if (var2_2 >= var1_1.length) {
                var14_10 = this._textBuffer.finishCurrentSegment();
                var7_11 = 0;
            }
            var2_2 = var7_11 + 1;
            var14_10[var7_11] = (char)var3_3;
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                var10_7 = 1;
                var7_11 = var2_2;
lbl104: // 3 sources:
                var2_2 = var10_7;
                var9_13 = var8_12;
                var12_9 = var7_11;
                var13_14 = var3_3;
                if (var8_12 == 0) {
                    this.reportUnexpectedNumberChar(var3_3, "Exponent indicator not followed by a digit");
                    var13_14 = var3_3;
                    var12_9 = var7_11;
                    var9_13 = var8_12;
                    var2_2 = var10_7;
                }
lbl114: // 4 sources:
                if (var2_2 == 0) {
                    --this._inputPtr;
                    if (this._parsingContext.inRoot()) {
                        this._verifyRootSpace(var13_14);
                    }
                }
                this._textBuffer.setCurrentLength(var12_9);
                return this.resetFloat(var4_4, var5_5, var11_6, var9_13);
            }
            var1_1 = this._inputBuffer;
            var3_3 = this._inputPtr;
            this._inputPtr = var3_3 + 1;
            var3_3 = var1_1[var3_3] & 255;
            var9_13 = var8_12;
            var1_1 = var14_10;
        } while (true);
    }

    private final JsonToken _parseNumber2(char[] arrc, int n2, boolean bl2, int n3) throws IOException {
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._textBuffer.setCurrentLength(n2);
                return this.resetInt(bl2, n3);
            }
            char[] arrc2 = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            int n5 = arrc2[n4] & 255;
            if (n5 > 57 || n5 < 48) {
                if (n5 != 46 && n5 != 101 && n5 != 69) break;
                return this._parseFloat(arrc, n2, n5, bl2, n3);
            }
            arrc2 = arrc;
            n4 = n2;
            if (n2 >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n4 = 0;
            }
            arrc2[n4] = (char)n5;
            ++n3;
            n2 = n4 + 1;
            arrc = arrc2;
        } while (true);
        --this._inputPtr;
        this._textBuffer.setCurrentLength(n2);
        if (this._parsingContext.inRoot()) {
            arrc = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            this._verifyRootSpace(arrc[n2] & 255);
        }
        return this.resetInt(bl2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _skipCComment() throws IOException {
        int[] arrn = CharTypes.getInputCodeComment();
        block8 : while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n3 = arrn[n2 = arrby[n2] & 255];
            if (n3 == 0) continue;
            switch (n3) {
                default: {
                    this._reportInvalidChar(n2);
                    continue block8;
                }
                case 42: {
                    if (this._inputPtr >= this._inputEnd && !this.loadMore()) break block8;
                    if (this._inputBuffer[this._inputPtr] != 47) continue block8;
                    ++this._inputPtr;
                    return;
                }
                case 10: {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                    continue block8;
                }
                case 13: {
                    this._skipCR();
                    continue block8;
                }
                case 2: {
                    this._skipUtf8_2(n2);
                    continue block8;
                }
                case 3: {
                    this._skipUtf8_3(n2);
                    continue block8;
                }
                case 4: {
                    this._skipUtf8_4(n2);
                    continue block8;
                }
            }
        }
        this._reportInvalidEOF(" in a comment");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final int _skipColon() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return this._skipColon2(false);
        }
        var2_1 = this._inputBuffer[this._inputPtr];
        if (var2_1 == 58) {
            var3_2 = this._inputBuffer;
            this._inputPtr = var1_4 = this._inputPtr + 1;
            if ((var1_4 = var3_2[var1_4]) > 32) {
                if (var1_4 == 47) return this._skipColon2(true);
                if (var1_4 == 35) {
                    return this._skipColon2(true);
                }
                ++this._inputPtr;
                return var1_4;
            }
            if (var1_4 != 32) {
                if (var1_4 != 9) return this._skipColon2(true);
            }
            var3_2 = this._inputBuffer;
            this._inputPtr = var1_4 = this._inputPtr + 1;
            if ((var1_4 = var3_2[var1_4]) <= 32) return this._skipColon2(true);
            if (var1_4 == 47) return this._skipColon2(true);
            if (var1_4 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return var1_4;
        }
        if (var2_1 == 32) ** GOTO lbl-1000
        var1_5 = var2_1;
        if (var2_1 == 9) lbl-1000: // 2 sources:
        {
            var3_3 = this._inputBuffer;
            this._inputPtr = var1_5 = this._inputPtr + 1;
            var1_5 = var3_3[var1_5];
        }
        if (var1_5 != 58) return this._skipColon2(false);
        var3_3 = this._inputBuffer;
        this._inputPtr = var1_5 = this._inputPtr + 1;
        if ((var1_5 = var3_3[var1_5]) > 32) {
            if (var1_5 == 47) return this._skipColon2(true);
            if (var1_5 == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return var1_5;
        }
        if (var1_5 != 32) {
            if (var1_5 != 9) return this._skipColon2(true);
        }
        var3_3 = this._inputBuffer;
        this._inputPtr = var1_5 = this._inputPtr + 1;
        if ((var1_5 = var3_3[var1_5]) <= 32) return this._skipColon2(true);
        if (var1_5 == 47) return this._skipColon2(true);
        if (var1_5 == 35) {
            return this._skipColon2(true);
        }
        ++this._inputPtr;
        return var1_5;
    }

    private final int _skipColon2(boolean bl2) throws IOException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrby[n2] & 255) > 32) {
                if (n2 == 47) {
                    this._skipComment();
                    continue;
                }
                if (n2 == 35 && this._skipYAMLComment()) continue;
                if (bl2) {
                    return n2;
                }
                if (n2 != 58) {
                    if (n2 < 32) {
                        this._throwInvalidSpace(n2);
                    }
                    this._reportUnexpectedChar(n2, "was expecting a colon to separate field name and value");
                }
                bl2 = true;
                continue;
            }
            if (n2 == 32) continue;
            if (n2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n2 == 13) {
                this._skipCR();
                continue;
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final int _skipColonFast(int var1_1) throws IOException {
        var5_2 = this._inputBuffer;
        var2_3 = var1_1 + 1;
        var4_4 = var5_2[var1_1];
        if (var4_4 != 58) ** GOTO lbl26
        var5_2 = this._inputBuffer;
        var3_5 = var2_3 + 1;
        if ((var2_3 = var5_2[var2_3]) <= 32) ** GOTO lbl15
        var1_1 = var3_5;
        if (var2_3 != 47) {
            var1_1 = var3_5;
            if (var2_3 != 35) {
                this._inputPtr = var3_5;
                return var2_3;
            }
        }
        ** GOTO lbl24
lbl15: // 1 sources:
        if (var2_3 == 32) ** GOTO lbl-1000
        var1_1 = var3_5;
        if (var2_3 == 9) lbl-1000: // 2 sources:
        {
            var5_2 = this._inputBuffer;
            var1_1 = var3_5 + 1;
            var2_3 = var5_2[var3_5];
            if (var2_3 > 32 && var2_3 != 47 && var2_3 != 35) {
                this._inputPtr = var1_1;
                return var2_3;
            }
        }
lbl24: // 5 sources:
        this._inputPtr = var1_1 - 1;
        return this._skipColon2(true);
lbl26: // 1 sources:
        if (var4_4 == 32) ** GOTO lbl-1000
        var3_6 = var4_4;
        var1_1 = var2_3;
        if (var4_4 == 9) lbl-1000: // 2 sources:
        {
            var3_6 = this._inputBuffer[var2_3];
            var1_1 = var2_3 + 1;
        }
        var2_3 = var1_1;
        if (var3_6 != 58) ** GOTO lbl60
        var5_2 = this._inputBuffer;
        var2_3 = var1_1 + 1;
        var3_6 = var5_2[var1_1];
        if (var3_6 <= 32) ** GOTO lbl45
        var1_1 = var2_3;
        if (var3_6 != 47) {
            var1_1 = var2_3;
            if (var3_6 != 35) {
                this._inputPtr = var2_3;
                return var3_6;
            }
        }
        ** GOTO lbl61
lbl45: // 1 sources:
        if (var3_6 == 32) ** GOTO lbl-1000
        var1_1 = var2_3;
        if (var3_6 == 9) lbl-1000: // 2 sources:
        {
            var5_2 = this._inputBuffer;
            var1_1 = var2_3 + 1;
            var3_6 = var5_2[var2_3];
            var2_3 = var1_1;
            if (var3_6 > 32) {
                var2_3 = var1_1;
                if (var3_6 != 47) {
                    var2_3 = var1_1;
                    if (var3_6 != 35) {
                        this._inputPtr = var1_1;
                        return var3_6;
                    } else {
                        ** GOTO lbl60
                    }
                } else {
                    ** GOTO lbl60
                }
            } else {
                ** GOTO lbl60
            }
        }
        ** GOTO lbl61
lbl60: // 7 sources:
        var1_1 = var2_3;
lbl61: // 3 sources:
        this._inputPtr = var1_1 - 1;
        return this._skipColon2(false);
    }

    private final void _skipComment() throws IOException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in a comment");
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if ((n2 = arrby[n2] & 255) == 47) {
            this._skipLine();
            return;
        }
        if (n2 == 42) {
            this._skipCComment();
            return;
        }
        this._reportUnexpectedChar(n2, "was expecting either '*' or '/' for a comment");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void _skipLine() throws IOException {
        int[] arrn = CharTypes.getInputCodeComment();
        block8 : while (!(this._inputPtr >= this._inputEnd && !this.loadMore())) {
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n3 = arrn[n2 = arrby[n2] & 255];
            if (n3 == 0) continue;
            switch (n3) {
                case 42: {
                    continue block8;
                }
                default: {
                    if (n3 >= 0) continue block8;
                    this._reportInvalidChar(n2);
                    continue block8;
                }
                case 10: {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                    return;
                }
                case 13: {
                    this._skipCR();
                    return;
                }
                case 2: {
                    this._skipUtf8_2(n2);
                    continue block8;
                }
                case 3: {
                    this._skipUtf8_3(n2);
                    continue block8;
                }
                case 4: 
            }
            this._skipUtf8_4(n2);
        }
    }

    private final void _skipUtf8_2(int n2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
    }

    private final void _skipUtf8_3(int n2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
    }

    private final void _skipUtf8_4(int n2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final int _skipWS() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n3 = arrby[n2] & 255;
            if (n3 > 32) {
                if (n3 != 47) {
                    n2 = n3;
                    if (n3 != 35) return n2;
                }
                --this._inputPtr;
                return this._skipWS2();
            }
            if (n3 == 32) continue;
            if (n3 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n3 == 13) {
                this._skipCR();
                continue;
            }
            if (n3 == 9) continue;
            this._throwInvalidSpace(n3);
        }
        return this._skipWS2();
    }

    private final int _skipWS2() throws IOException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrby[n2] & 255) > 32) {
                if (n2 == 47) {
                    this._skipComment();
                    continue;
                }
                if (n2 == 35 && this._skipYAMLComment()) continue;
                return n2;
            }
            if (n2 == 32) continue;
            if (n2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n2 == 13) {
                this._skipCR();
                continue;
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            return this._eofAsNextChar();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        int n3 = arrby[n2] & 255;
        if (n3 > 32) {
            if (n3 != 47) {
                n2 = n3;
                if (n3 != 35) return n2;
            }
            --this._inputPtr;
            return this._skipWSOrEnd2();
        }
        if (n3 != 32) {
            if (n3 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
            } else if (n3 == 13) {
                this._skipCR();
            } else if (n3 != 9) {
                this._throwInvalidSpace(n3);
            }
        }
        while (this._inputPtr < this._inputEnd) {
            arrby = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n3 = arrby[n2] & 255;
            if (n3 > 32) {
                if (n3 != 47) {
                    n2 = n3;
                    if (n3 != 35) return n2;
                }
                --this._inputPtr;
                return this._skipWSOrEnd2();
            }
            if (n3 == 32) continue;
            if (n3 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n3 == 13) {
                this._skipCR();
                continue;
            }
            if (n3 == 9) continue;
            this._throwInvalidSpace(n3);
        }
        return this._skipWSOrEnd2();
    }

    private final int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrby[n2] & 255) > 32) {
                if (n2 == 47) {
                    this._skipComment();
                    continue;
                }
                if (n2 == 35 && this._skipYAMLComment()) continue;
                return n2;
            }
            if (n2 == 32) continue;
            if (n2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n2 == 13) {
                this._skipCR();
                continue;
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        }
        return this._eofAsNextChar();
    }

    private final boolean _skipYAMLComment() throws IOException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        this._skipLine();
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final int _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            return 48;
        }
        int n2 = this._inputBuffer[this._inputPtr] & 255;
        if (n2 < 48) return 48;
        if (n2 > 57) {
            return 48;
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        int n3 = n2;
        if (n2 != 48) return n3;
        n3 = n2;
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this.loadMore()) return n3;
            }
            if ((n2 = this._inputBuffer[this._inputPtr] & 255) < 48) return 48;
            if (n2 > 57) {
                return 48;
            }
            ++this._inputPtr;
            n3 = n2;
        } while (n2 == 48);
        return n2;
    }

    private final void _verifyRootSpace(int n2) throws IOException {
        ++this._inputPtr;
        switch (n2) {
            default: {
                this._reportMissingRootWS(n2);
            }
            case 9: 
            case 32: {
                return;
            }
            case 13: {
                this._skipCR();
                return;
            }
            case 10: 
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final Name addName(int[] var1_1, int var2_2, int var3_3) throws JsonParseException {
        var11_4 = (var2_2 << 2) - 4 + var3_3;
        if (var3_3 < 4) {
            var10_5 = var1_1[var2_2 - 1];
            var1_1[var2_2 - 1] = var10_5 << (4 - var3_3 << 3);
        } else {
            var10_5 = 0;
        }
        var12_6 = this._textBuffer.emptyAndGetCurrentSegment();
        var4_7 = 0;
        var7_8 = 0;
        do {
            if (var4_7 >= var11_4) {
                var12_6 = new String((char[])var12_6, 0, var7_8);
                if (var3_3 >= 4) return this._symbols.addName((String)var12_6, var1_1, var2_2);
                var1_1[var2_2 - 1] = var10_5;
                return this._symbols.addName((String)var12_6, var1_1, var2_2);
            }
            var5_9 = var1_1[var4_7 >> 2] >> (3 - (var4_7 & 3) << 3) & 255;
            var6_10 = var4_7 + 1;
            var9_12 = var5_9;
            var8_11 = var6_10;
            if (var5_9 <= 127) ** GOTO lbl-1000
            if ((var5_9 & 224) == 192) {
                var4_7 = var5_9 & 31;
                var5_9 = 1;
            } else if ((var5_9 & 240) == 224) {
                var4_7 = var5_9 & 15;
                var5_9 = 2;
            } else if ((var5_9 & 248) == 240) {
                var4_7 = var5_9 & 7;
                var5_9 = 3;
            } else {
                this._reportInvalidInitial(var5_9);
                var4_7 = 1;
                var5_9 = 1;
            }
            if (var6_10 + var5_9 > var11_4) {
                this._reportInvalidEOF(" in field name");
            }
            var9_12 = var1_1[var6_10 >> 2] >> (3 - (var6_10 & 3) << 3);
            var8_11 = var6_10 + 1;
            if ((var9_12 & 192) != 128) {
                this._reportInvalidOther(var9_12);
            }
            var6_10 = var9_12 = var4_7 << 6 | var9_12 & 63;
            var4_7 = var8_11;
            if (var5_9 > 1) {
                var4_7 = var1_1[var8_11 >> 2] >> (3 - (var8_11 & 3) << 3);
                ++var8_11;
                if ((var4_7 & 192) != 128) {
                    this._reportInvalidOther(var4_7);
                }
                var6_10 = var9_12 = var9_12 << 6 | var4_7 & 63;
                var4_7 = var8_11;
                if (var5_9 > 2) {
                    var6_10 = var1_1[var8_11 >> 2] >> (3 - (var8_11 & 3) << 3);
                    var4_7 = var8_11 + 1;
                    if ((var6_10 & 192) != 128) {
                        this._reportInvalidOther(var6_10 & 255);
                    }
                    var6_10 = var9_12 << 6 | var6_10 & 63;
                }
            }
            var9_12 = var6_10;
            var8_11 = var4_7;
            if (var5_9 > 2) {
                var6_10 -= 65536;
                var13_13 = var12_6;
                if (var7_8 >= var12_6.length) {
                    var13_13 = this._textBuffer.expandCurrentSegment();
                }
                var5_9 = var7_8 + 1;
                var13_13[var7_8] = (char)(55296 + (var6_10 >> 10));
                var9_12 = 56320 | var6_10 & 1023;
                var12_6 = var13_13;
            } else lbl-1000: // 2 sources:
            {
                var5_9 = var7_8;
                var4_7 = var8_11;
            }
            var13_13 = var12_6;
            if (var5_9 >= var12_6.length) {
                var13_13 = this._textBuffer.expandCurrentSegment();
            }
            var7_8 = var5_9 + 1;
            var13_13[var5_9] = (char)var9_12;
            var12_6 = var13_13;
        } while (true);
    }

    private final Name findName(int n2, int n3) throws JsonParseException {
        Name name = this._symbols.findName(n2);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = n2;
        return this.addName(this._quadBuffer, 1, n3);
    }

    private final Name findName(int n2, int n3, int n4) throws JsonParseException {
        Name name = this._symbols.findName(n2, n3);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = n2;
        this._quadBuffer[1] = n3;
        return this.addName(this._quadBuffer, 2, n4);
    }

    private final Name findName(int[] object, int n2, int n3, int n4) throws JsonParseException {
        int[] arrn = object;
        if (n2 >= object.length) {
            this._quadBuffer = arrn = UTF8StreamJsonParser.growArrayBy((int[])object, object.length);
        }
        int n5 = n2 + 1;
        arrn[n2] = n3;
        Name name = this._symbols.findName(arrn, n5);
        object = name;
        if (name == null) {
            object = this.addName(arrn, n5, n4);
        }
        return object;
    }

    public static int[] growArrayBy(int[] arrn, int n2) {
        if (arrn == null) {
            return new int[n2];
        }
        return Arrays.copyOf(arrn, arrn.length + n2);
    }

    private int nextByte() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        return arrby[n2] & 255;
    }

    private final Name parseName(int n2, int n3, int n4) throws IOException {
        return this.parseEscapedName(this._quadBuffer, 0, n2, n3, n4);
    }

    private final Name parseName(int n2, int n3, int n4, int n5) throws IOException {
        this._quadBuffer[0] = n2;
        return this.parseEscapedName(this._quadBuffer, 1, n3, n4, n5);
    }

    @Override
    protected void _closeInput() throws IOException {
        if (this._inputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                this._inputStream.close();
            }
            this._inputStream = null;
        }
    }

    protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder byteArrayBuilder = this._getByteArrayBuilder();
        do {
            int n2;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            int n4 = arrby[n3] & 255;
            if (n4 <= 32) continue;
            n3 = n2 = base64Variant.decodeBase64Char(n4);
            if (n2 < 0) {
                if (n4 == 34) {
                    return byteArrayBuilder.toByteArray();
                }
                n3 = this._decodeBase64Escape(base64Variant, n4, 0);
                if (n3 < 0) continue;
            }
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrby = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n5 = arrby[n2] & 255;
            n2 = n4 = base64Variant.decodeBase64Char(n5);
            if (n4 < 0) {
                n2 = this._decodeBase64Escape(base64Variant, n5, 1);
            }
            n5 = n3 << 6 | n2;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrby = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            int n6 = arrby[n3] & 255;
            n4 = n2 = base64Variant.decodeBase64Char(n6);
            if (n2 < 0) {
                n3 = n2;
                if (n2 != -2) {
                    if (n6 == 34 && !base64Variant.usesPadding()) {
                        byteArrayBuilder.append(n5 >> 4);
                        return byteArrayBuilder.toByteArray();
                    }
                    n3 = this._decodeBase64Escape(base64Variant, n6, 2);
                }
                n4 = n3;
                if (n3 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this.loadMoreGuaranteed();
                    }
                    arrby = this._inputBuffer;
                    n3 = this._inputPtr;
                    this._inputPtr = n3 + 1;
                    if (!base64Variant.usesPaddingChar(n3 = arrby[n3] & 255)) {
                        throw this.reportInvalidBase64Char(base64Variant, n3, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                    }
                    byteArrayBuilder.append(n5 >> 4);
                    continue;
                }
            }
            n5 = n5 << 6 | n4;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrby = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            n6 = arrby[n3] & 255;
            n4 = n2 = base64Variant.decodeBase64Char(n6);
            if (n2 < 0) {
                n3 = n2;
                if (n2 != -2) {
                    if (n6 == 34 && !base64Variant.usesPadding()) {
                        byteArrayBuilder.appendTwoBytes(n5 >> 2);
                        return byteArrayBuilder.toByteArray();
                    }
                    n3 = this._decodeBase64Escape(base64Variant, n6, 3);
                }
                n4 = n3;
                if (n3 == -2) {
                    byteArrayBuilder.appendTwoBytes(n5 >> 2);
                    continue;
                }
            }
            byteArrayBuilder.appendThreeBytes(n5 << 6 | n4);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int _decodeCharForError(int n2) throws IOException {
        int n3;
        int n4 = n2 &= 255;
        if (n2 <= 127) return n4;
        if ((n2 & 224) == 192) {
            n4 = n2 & 31;
            n2 = 1;
        } else if ((n2 & 240) == 224) {
            n4 = n2 & 15;
            n2 = 2;
        } else if ((n2 & 248) == 240) {
            n4 = n2 & 7;
            n2 = 3;
        } else {
            this._reportInvalidInitial(n2 & 255);
            n3 = 1;
            n4 = n2;
            n2 = n3;
        }
        if (((n3 = this.nextByte()) & 192) != 128) {
            this._reportInvalidOther(n3 & 255);
        }
        n4 = n3 = n4 << 6 | n3 & 63;
        if (n2 <= 1) return n4;
        n4 = this.nextByte();
        if ((n4 & 192) != 128) {
            this._reportInvalidOther(n4 & 255);
        }
        n4 = n3 = n3 << 6 | n4 & 63;
        if (n2 <= 2) return n4;
        n2 = this.nextByte();
        if ((n2 & 192) == 128) return n3 << 6 | n2 & 63;
        this._reportInvalidOther(n2 & 255);
        return n3 << 6 | n2 & 63;
    }

    @Override
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in character escape sequence");
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        n2 = arrby[n2];
        switch (n2) {
            default: {
                return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(n2));
            }
            case 98: {
                return '\b';
            }
            case 116: {
                return '\t';
            }
            case 110: {
                return '\n';
            }
            case 102: {
                return '\f';
            }
            case 114: {
                return '\r';
            }
            case 34: 
            case 47: 
            case 92: {
                return (char)n2;
            }
            case 117: 
        }
        int n3 = 0;
        for (n2 = 0; n2 < 4; ++n2) {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in character escape sequence");
            }
            arrby = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            int n5 = CharTypes.charToHex(n4 = arrby[n4]);
            if (n5 < 0) {
                this._reportUnexpectedChar(n4, "expected a hex-digit for character escape sequence");
            }
            n3 = n3 << 4 | n5;
        }
        return (char)n3;
    }

    @Override
    protected void _finishString() throws IOException {
        int n2;
        int n3 = n2 = this._inputPtr;
        if (n2 >= this._inputEnd) {
            this.loadMoreGuaranteed();
            n3 = this._inputPtr;
        }
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        int n4 = Math.min(this._inputEnd, arrc.length + n3);
        byte[] arrby = this._inputBuffer;
        int n5 = 0;
        n2 = n3;
        n3 = n5;
        while (n2 < n4) {
            n5 = arrby[n2] & 255;
            if (arrn[n5] != 0) {
                if (n5 != 34) break;
                this._inputPtr = n2 + 1;
                this._textBuffer.setCurrentLength(n3);
                return;
            }
            ++n2;
            arrc[n3] = (char)n5;
            ++n3;
        }
        this._inputPtr = n2;
        this._finishString2(arrc, n3);
    }

    protected final String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (jsonToken.id()) {
            default: {
                return jsonToken.asString();
            }
            case 5: {
                return this._parsingContext.getCurrentName();
            }
            case 6: 
            case 7: 
            case 8: 
        }
        return this._textBuffer.contentsAsString();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonToken _handleApos() throws IOException {
        int n2 = 0;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        byte[] arrby = this._inputBuffer;
        block6 : do {
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc2 = arrc;
            int n3 = n2;
            if (n2 >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n3 = 0;
            }
            n2 = this._inputEnd;
            int n4 = this._inputPtr + (arrc2.length - n3);
            int n5 = n2;
            int n6 = n3;
            if (n4 < n2) {
                n5 = n4;
                n6 = n3;
            }
            do {
                arrc = arrc2;
                n2 = n6;
                if (this._inputPtr >= n5) continue block6;
                n2 = this._inputPtr;
                this._inputPtr = n2 + 1;
                n4 = arrby[n2] & 255;
                if (n4 == 39 || arrn[n4] != 0) {
                    if (n4 != 39) break;
                    this._textBuffer.setCurrentLength(n6);
                    return JsonToken.VALUE_STRING;
                }
                arrc2[n6] = (char)n4;
                ++n6;
            } while (true);
            switch (arrn[n4]) {
                default: {
                    if (n4 < 32) {
                        this._throwUnquotedSpace(n4, "string value");
                    }
                    this._reportInvalidChar(n4);
                    n3 = n6;
                    arrc = arrc2;
                    n2 = n4;
                    break;
                }
                case 1: {
                    n2 = n4;
                    arrc = arrc2;
                    n3 = n6;
                    if (n4 == 39) break;
                    n2 = this._decodeEscaped();
                    arrc = arrc2;
                    n3 = n6;
                    break;
                }
                case 2: {
                    n2 = this._decodeUtf8_2(n4);
                    arrc = arrc2;
                    n3 = n6;
                    break;
                }
                case 3: {
                    if (this._inputEnd - this._inputPtr >= 2) {
                        n2 = this._decodeUtf8_3fast(n4);
                        arrc = arrc2;
                        n3 = n6;
                        break;
                    }
                    n2 = this._decodeUtf8_3(n4);
                    arrc = arrc2;
                    n3 = n6;
                    break;
                }
                case 4: {
                    n2 = this._decodeUtf8_4(n4);
                    n3 = n6 + 1;
                    arrc2[n6] = (char)(55296 | n2 >> 10);
                    if (n3 >= arrc2.length) {
                        arrc2 = this._textBuffer.finishCurrentSegment();
                        n3 = 0;
                    }
                    n2 = 56320 | n2 & 1023;
                    arrc = arrc2;
                }
            }
            arrc2 = arrc;
            n6 = n3;
            if (n3 >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n6 = 0;
            }
            arrc2[n6] = (char)n2;
            n2 = n6 + 1;
            arrc = arrc2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonToken _handleInvalidNumberStart(int n2, boolean bl2) throws IOException {
        int n3;
        do {
            n3 = n2;
            if (n2 != 73) break;
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOFInValue();
            }
            Object object = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = object[n2]) == 78) {
                object = bl2 ? (Object)"-INF" : "+INF";
            } else {
                n3 = n2;
                if (n2 != 110) break;
                object = bl2 ? "-Infinity" : "+Infinity";
            }
            this._matchToken((String)object, 3);
            if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                double d2;
                if (bl2) {
                    d2 = Double.NEGATIVE_INFINITY;
                    return this.resetAsNaN((String)object, d2);
                }
                d2 = Double.POSITIVE_INFINITY;
                return this.resetAsNaN((String)object, d2);
            }
            this._reportError("Non-standard token '" + (String)object + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
        } while (true);
        this.reportUnexpectedNumberChar(n3, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Name _handleOddName(int n2) throws IOException {
        Object object;
        if (n2 == 39 && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseAposName();
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar((char)this._decodeCharForError(n2), "was expecting double-quote to start field name");
        }
        if ((object = CharTypes.getInputCodeUtf8JsNames())[n2] != 0) {
            this._reportUnexpectedChar(n2, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        Object object2 = this._quadBuffer;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = n2;
        n2 = n5;
        do {
            if (n4 < 4) {
                n3 = n3 << 8 | n6;
                n6 = ++n4;
            } else {
                void var7_8;
                Name name = object2;
                if (n2 >= object2.length) {
                    int[] arrn;
                    this._quadBuffer = arrn = UTF8StreamJsonParser.growArrayBy((int[])object2, object2.length);
                }
                n4 = n2 + 1;
                var7_8[n2] = n3;
                n2 = 1;
                n3 = n6;
                n6 = n2;
                n2 = n4;
                object2 = var7_8;
            }
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in field name");
            }
            if (object[n5 = this._inputBuffer[this._inputPtr] & 255] != 0) {
                void var7_13;
                n4 = n2;
                Name name = object2;
                if (n6 > 0) {
                    Name name2 = object2;
                    if (n2 >= object2.length) {
                        int[] arrn;
                        this._quadBuffer = arrn = UTF8StreamJsonParser.growArrayBy((int[])object2, object2.length);
                    }
                    var7_12[n2] = n3;
                    n4 = n2 + 1;
                }
                object2 = object = (Object)this._symbols.findName((int[])var7_13, n4);
                if (object != null) return object2;
                return this.addName((int[])var7_13, n4, n6);
            }
            ++this._inputPtr;
            n4 = n6;
            n6 = n5;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected JsonToken _handleUnexpectedValue(int var1_1) throws IOException {
        switch (var1_1) {
            case 93: 
            case 125: {
                this._reportUnexpectedChar(var1_1, "expected a value");
            }
            case 39: {
                if (this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApos();
                }
                ** GOTO lbl19
            }
            case 78: {
                this._matchToken("NaN", 1);
                if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("NaN", Double.NaN);
                }
                this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                ** break;
            }
            case 73: {
                this._matchToken("Infinity", 1);
                if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
lbl19: // 4 sources:
            default: {
                if (Character.isJavaIdentifierStart(var1_1)) {
                    this._reportInvalidToken("" + (char)var1_1, "('true', 'false' or 'null')");
                }
                this._reportUnexpectedChar(var1_1, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
                return null;
            }
            case 43: 
        }
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOFInValue();
        }
        var2_2 = this._inputBuffer;
        var1_1 = this._inputPtr;
        this._inputPtr = var1_1 + 1;
        return this._handleInvalidNumberStart(var2_2[var1_1] & 255, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final boolean _loadToHaveAtLeast(int n2) throws IOException {
        if (this._inputStream == null) return false;
        int n3 = this._inputEnd - this._inputPtr;
        if (n3 > 0 && this._inputPtr > 0) {
            this._currInputProcessed += (long)this._inputPtr;
            this._currInputRowStart -= this._inputPtr;
            System.arraycopy(this._inputBuffer, this._inputPtr, this._inputBuffer, 0, n3);
            this._inputEnd = n3;
        } else {
            this._inputEnd = 0;
        }
        this._inputPtr = 0;
        while (this._inputEnd < n2) {
            int n4 = this._inputStream.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
            if (n4 < 1) {
                this._closeInput();
                if (n4 != 0) return false;
                throw new IOException("InputStream.read() returned 0 characters when trying to read " + n3 + " bytes");
            }
            this._inputEnd += n4;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _matchToken(String string2, int n2) throws IOException {
        int n3 = string2.length();
        int n4 = n2;
        if (this._inputPtr + n3 >= this._inputEnd) {
            this._matchToken2(string2, n2);
            return;
        } else {
            do {
                if (this._inputBuffer[this._inputPtr] != string2.charAt(n4)) {
                    this._reportInvalidToken(string2.substring(0, n4));
                }
                ++this._inputPtr;
                n4 = n2 = n4 + 1;
            } while (n2 < n3);
            n4 = this._inputBuffer[this._inputPtr] & 255;
            if (n4 < 48 || n4 == 93 || n4 == 125) return;
            {
                this._checkMatchEnd(string2, n2, n4);
                return;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected Name _parseAposName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing ''' for name");
        }
        var10_1 = this._inputBuffer;
        var1_2 = this._inputPtr;
        this._inputPtr = var1_2 + 1;
        var4_3 = var10_1[var1_2] & 255;
        if (var4_3 == 39) {
            return BytesToNameCanonicalizer.getEmptyName();
        }
        var10_1 = this._quadBuffer;
        var3_4 = 0;
        var2_5 = 0;
        var12_6 = UTF8StreamJsonParser._icLatin1;
        var1_2 = 0;
        do {
            if (var4_3 != 39) ** GOTO lbl25
            if (var2_5 <= 0) ** GOTO lbl78
            var11_12 = var10_1;
            if (var1_2 >= var10_1.length) {
                var11_12 = UTF8StreamJsonParser.growArrayBy((int[])var10_1, var10_1.length);
                this._quadBuffer = var11_12;
            }
            var4_3 = var1_2 + 1;
            var11_12[var1_2] = var3_4;
            var1_2 = var4_3;
            ** GOTO lbl79
lbl25: // 1 sources:
            var5_7 = var4_3;
            var8_10 = var3_4;
            var9_11 = var2_5;
            var7_9 = var1_2;
            var11_12 = var10_1;
            if (var4_3 == 34) ** GOTO lbl93
            var5_7 = var4_3;
            var8_10 = var3_4;
            var9_11 = var2_5;
            var7_9 = var1_2;
            var11_12 = var10_1;
            if (var12_6[var4_3] == false) ** GOTO lbl93
            if (var4_3 != 92) {
                this._throwUnquotedSpace(var4_3, "name");
                var6_8 = var4_3;
            } else {
                var6_8 = this._decodeEscaped();
            }
            var5_7 = var6_8;
            var8_10 = var3_4;
            var9_11 = var2_5;
            var7_9 = var1_2;
            var11_12 = var10_1;
            if (var6_8 <= 127) ** GOTO lbl93
            var7_9 = var3_4;
            var5_7 = var2_5;
            var4_3 = var1_2;
            var11_12 = var10_1;
            if (var2_5 >= 4) {
                var11_12 = var10_1;
                if (var1_2 >= var10_1.length) {
                    var11_12 = UTF8StreamJsonParser.growArrayBy((int[])var10_1, var10_1.length);
                    this._quadBuffer = var11_12;
                }
                var11_12[var1_2] = var3_4;
                var7_9 = 0;
                var5_7 = 0;
                var4_3 = var1_2 + 1;
            }
            if (var6_8 >= 2048) ** GOTO lbl66
            var1_2 = var7_9 << 8 | (var6_8 >> 6 | 192);
            var3_4 = var5_7 + 1;
            var2_5 = var4_3;
            ** GOTO lbl89
lbl66: // 1 sources:
            var3_4 = var7_9 << 8 | (var6_8 >> 12 | 224);
            var1_2 = var5_7 + 1;
            if (var1_2 < 4) ** GOTO lbl82
            var10_1 = var11_12;
            if (var4_3 >= var11_12.length) {
                var10_1 = UTF8StreamJsonParser.growArrayBy((int[])var11_12, var11_12.length);
                this._quadBuffer = var10_1;
            }
            var2_5 = var4_3 + 1;
            var10_1[var4_3] = var3_4;
            var3_4 = 0;
            var1_2 = 0;
            ** GOTO lbl84
lbl78: // 1 sources:
            var11_12 = var10_1;
lbl79: // 2 sources:
            var10_1 = var12_6 = (Object)this._symbols.findName((int[])var11_12, var1_2);
            if (var12_6 != null) return var10_1;
            return this.addName((int[])var11_12, var1_2, var2_5);
lbl82: // 1 sources:
            var2_5 = var4_3;
            var10_1 = var11_12;
lbl84: // 2 sources:
            var3_4 = var3_4 << 8 | (var6_8 >> 6 & 63 | 128);
            var4_3 = var1_2 + 1;
            var1_2 = var3_4;
            var3_4 = var4_3;
            var11_12 = var10_1;
lbl89: // 2 sources:
            var5_7 = var6_8 & 63 | 128;
            var7_9 = var2_5;
            var9_11 = var3_4;
            var8_10 = var1_2;
lbl93: // 4 sources:
            if (var9_11 < 4) {
                var2_5 = var9_11 + 1;
                var3_4 = var8_10 << 8 | var5_7;
                var1_2 = var7_9;
                var10_1 = var11_12;
            } else {
                var10_1 = var11_12;
                if (var7_9 >= var11_12.length) {
                    var10_1 = UTF8StreamJsonParser.growArrayBy((int[])var11_12, var11_12.length);
                    this._quadBuffer = var10_1;
                }
                var1_2 = var7_9 + 1;
                var10_1[var7_9] = var8_10;
                var3_4 = var5_7;
                var2_5 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in field name");
            }
            var11_12 = this._inputBuffer;
            var4_3 = this._inputPtr;
            this._inputPtr = var4_3 + 1;
            var4_3 = var11_12[var4_3] & 255;
        } while (true);
    }

    protected final Name _parseName(int n2) throws IOException {
        if (n2 != 34) {
            return this._handleOddName(n2);
        }
        if (this._inputPtr + 9 > this._inputEnd) {
            return this.slowParseName();
        }
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] == 0) {
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            if (arrn[n3 = arrby[n3] & 255] == 0) {
                n2 = n2 << 8 | n3;
                n3 = this._inputPtr;
                this._inputPtr = n3 + 1;
                if (arrn[n3 = arrby[n3] & 255] == 0) {
                    n2 = n2 << 8 | n3;
                    n3 = this._inputPtr;
                    this._inputPtr = n3 + 1;
                    if (arrn[n3 = arrby[n3] & 255] == 0) {
                        n2 = n2 << 8 | n3;
                        n3 = this._inputPtr;
                        this._inputPtr = n3 + 1;
                        if (arrn[n3 = arrby[n3] & 255] == 0) {
                            this._quad1 = n2;
                            return this.parseMediumName(n3);
                        }
                        if (n3 == 34) {
                            return this.findName(n2, 4);
                        }
                        return this.parseName(n2, n3, 4);
                    }
                    if (n3 == 34) {
                        return this.findName(n2, 3);
                    }
                    return this.parseName(n2, n3, 3);
                }
                if (n3 == 34) {
                    return this.findName(n2, 2);
                }
                return this.parseName(n2, n3, 2);
            }
            if (n3 == 34) {
                return this.findName(n2, 1);
            }
            return this.parseName(n2, n3, 1);
        }
        if (n2 == 34) {
            return BytesToNameCanonicalizer.getEmptyName();
        }
        return this.parseName(0, n2, 0);
    }

    protected JsonToken _parseNegNumber() throws IOException {
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n2 = 0 + 1;
        arrc[0] = 45;
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        int n4 = arrby[n3] & 255;
        if (n4 < 48 || n4 > 57) {
            return this._handleInvalidNumberStart(n4, true);
        }
        n3 = n4;
        if (n4 == 48) {
            n3 = this._verifyNoLeadingZeroes();
        }
        int n5 = n2 + 1;
        arrc[n2] = (char)n3;
        int n6 = 1;
        int n7 = this._inputPtr + arrc.length;
        n4 = n5;
        n2 = n6;
        n3 = n7;
        if (n7 > this._inputEnd) {
            n3 = this._inputEnd;
            n2 = n6;
            n4 = n5;
        }
        do {
            if (this._inputPtr >= n3) {
                return this._parseNumber2(arrc, n4, true, n2);
            }
            arrby = this._inputBuffer;
            n5 = this._inputPtr;
            this._inputPtr = n5 + 1;
            if ((n5 = arrby[n5] & 255) < 48 || n5 > 57) {
                if (n5 != 46 && n5 != 101 && n5 != 69) break;
                return this._parseFloat(arrc, n4, n5, true, n2);
            }
            ++n2;
            arrc[n4] = (char)n5;
            ++n4;
        } while (true);
        --this._inputPtr;
        this._textBuffer.setCurrentLength(n4);
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(n5);
        }
        return this.resetInt(true, n2);
    }

    protected JsonToken _parsePosNumber(int n2) throws IOException {
        int n3;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n4 = n2;
        if (n2 == 48) {
            n4 = this._verifyNoLeadingZeroes();
        }
        n2 = 0 + 1;
        arrc[0] = (char)n4;
        int n5 = 1;
        n4 = this._inputPtr + arrc.length;
        if (n4 > this._inputEnd) {
            n4 = this._inputEnd;
        }
        do {
            if (this._inputPtr >= n4) {
                return this._parseNumber2(arrc, n2, false, n5);
            }
            byte[] arrby = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            if ((n3 = arrby[n3] & 255) < 48 || n3 > 57) {
                if (n3 != 46 && n3 != 101 && n3 != 69) break;
                return this._parseFloat(arrc, n2, n3, false, n5);
            }
            ++n5;
            arrc[n2] = (char)n3;
            ++n2;
        } while (true);
        --this._inputPtr;
        this._textBuffer.setCurrentLength(n2);
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(n3);
        }
        return this.resetInt(false, n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int _readBinary(Base64Variant base64Variant, OutputStream outputStream, byte[] arrby) throws IOException, JsonParseException {
        int n2;
        int n3 = 0;
        int n4 = arrby.length;
        int n5 = 0;
        do {
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby2 = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n6 = arrby2[n2] & 255;
            if (n6 <= 32) continue;
            int n7 = n2 = base64Variant.decodeBase64Char(n6);
            if (n2 < 0) {
                if (n6 == 34) {
                    n2 = n5;
                    break;
                }
                n7 = this._decodeBase64Escape(base64Variant, n6, 0);
                if (n7 < 0) continue;
            }
            n2 = n5;
            n6 = n3;
            if (n3 > n4 - 3) {
                n2 = n5 + n3;
                outputStream.write(arrby, 0, n3);
                n6 = 0;
            }
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrby2 = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            int n8 = arrby2[n3] & 255;
            n3 = n5 = base64Variant.decodeBase64Char(n8);
            if (n5 < 0) {
                n3 = this._decodeBase64Escape(base64Variant, n8, 1);
            }
            n8 = n7 << 6 | n3;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrby2 = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            int n9 = arrby2[n3] & 255;
            n7 = n5 = base64Variant.decodeBase64Char(n9);
            if (n5 < 0) {
                n3 = n5;
                if (n5 != -2) {
                    if (n9 == 34 && !base64Variant.usesPadding()) {
                        arrby[n6] = (byte)(n8 >> 4);
                        n3 = n6 + 1;
                        break;
                    }
                    n3 = this._decodeBase64Escape(base64Variant, n9, 2);
                }
                n7 = n3;
                if (n3 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this.loadMoreGuaranteed();
                    }
                    arrby2 = this._inputBuffer;
                    n3 = this._inputPtr;
                    this._inputPtr = n3 + 1;
                    if (!base64Variant.usesPaddingChar(n3 = arrby2[n3] & 255)) {
                        throw this.reportInvalidBase64Char(base64Variant, n3, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                    }
                    arrby[n6] = (byte)(n8 >> 4);
                    n3 = n6 + 1;
                    n5 = n2;
                    continue;
                }
            }
            n8 = n8 << 6 | n7;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrby2 = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            n9 = arrby2[n3] & 255;
            n7 = n5 = base64Variant.decodeBase64Char(n9);
            if (n5 < 0) {
                n3 = n5;
                if (n5 != -2) {
                    if (n9 == 34 && !base64Variant.usesPadding()) {
                        n5 = n8 >> 2;
                        n7 = n6 + 1;
                        arrby[n6] = (byte)(n5 >> 8);
                        n3 = n7 + 1;
                        arrby[n7] = (byte)n5;
                        break;
                    }
                    n3 = this._decodeBase64Escape(base64Variant, n9, 3);
                }
                n7 = n3;
                if (n3 == -2) {
                    n5 = n8 >> 2;
                    n7 = n6 + 1;
                    arrby[n6] = (byte)(n5 >> 8);
                    n3 = n7 + 1;
                    arrby[n7] = (byte)n5;
                    n5 = n2;
                    continue;
                }
            }
            n3 = n8 << 6 | n7;
            n5 = n6 + 1;
            arrby[n6] = (byte)(n3 >> 16);
            n6 = n5 + 1;
            arrby[n5] = (byte)(n3 >> 8);
            arrby[n6] = (byte)n3;
            n3 = n6 + 1;
            n5 = n2;
        } while (true);
        this._tokenIncomplete = false;
        n5 = n2;
        if (n3 > 0) {
            n5 = n2 + n3;
            outputStream.write(arrby, 0, n3);
        }
        return n5;
    }

    @Override
    protected void _releaseBuffers() throws IOException {
        byte[] arrby;
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable && (arrby = this._inputBuffer) != null) {
            this._inputBuffer = null;
            this._ioContext.releaseReadIOBuffer(arrby);
        }
    }

    protected void _reportInvalidChar(int n2) throws JsonParseException {
        if (n2 < 32) {
            this._throwInvalidSpace(n2);
        }
        this._reportInvalidInitial(n2);
    }

    protected void _reportInvalidInitial(int n2) throws JsonParseException {
        this._reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString(n2));
    }

    protected void _reportInvalidOther(int n2) throws JsonParseException {
        this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString(n2));
    }

    protected void _reportInvalidOther(int n2, int n3) throws JsonParseException {
        this._inputPtr = n3;
        this._reportInvalidOther(n2);
    }

    protected void _reportInvalidToken(String string2) throws IOException {
        this._reportInvalidToken(string2, "'null', 'true', 'false' or NaN");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void _reportInvalidToken(String var1_1, String var2_2) throws IOException {
        var1_2 = new StringBuilder(var1_1 /* !! */ );
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) ** GOTO lbl-1000
            var5_6 = this._inputBuffer;
            var4_5 = this._inputPtr;
            this._inputPtr = var4_5 + 1;
            var3_4 = (char)this._decodeCharForError(var5_6[var4_5]);
            if (!Character.isJavaIdentifierPart(var3_4)) lbl-1000: // 2 sources:
            {
                this._reportError("Unrecognized token '" + var1_2.toString() + "': was expecting " + (String)var2_3);
                return;
            }
            var1_2.append(var3_4);
        } while (true);
    }

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this.loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }

    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int[] arrn = _icUTF8;
        byte[] arrby = this._inputBuffer;
        block6 : do {
            int n2;
            block10 : {
                int n3 = this._inputPtr;
                int n4 = n2 = this._inputEnd;
                int n5 = n3;
                if (n3 >= n2) {
                    this.loadMoreGuaranteed();
                    n5 = this._inputPtr;
                    n4 = this._inputEnd;
                }
                while (n5 < n4) {
                    n3 = n5 + 1;
                    n2 = arrby[n5] & 255;
                    n5 = n3;
                    if (arrn[n2] == 0) continue;
                    this._inputPtr = n3;
                    if (n2 == 34) {
                        return;
                    }
                    break block10;
                }
                this._inputPtr = n5;
                continue;
            }
            switch (arrn[n2]) {
                default: {
                    if (n2 >= 32) break;
                    this._throwUnquotedSpace(n2, "string value");
                    continue block6;
                }
                case 1: {
                    this._decodeEscaped();
                    continue block6;
                }
                case 2: {
                    this._skipUtf8_2(n2);
                    continue block6;
                }
                case 3: {
                    this._skipUtf8_3(n2);
                    continue block6;
                }
                case 4: {
                    this._skipUtf8_4(n2);
                    continue block6;
                }
            }
            this._reportInvalidChar(n2);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
            this._reportError("Current token (" + (Object)((Object)this._currToken) + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (!this._tokenIncomplete) {
            if (this._binaryValue != null) return this._binaryValue;
            ByteArrayBuilder byteArrayBuilder = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), byteArrayBuilder, base64Variant);
            this._binaryValue = byteArrayBuilder.toByteArray();
            return this._binaryValue;
        }
        try {
            this._binaryValue = this._decodeBase64(base64Variant);
        }
        catch (IllegalArgumentException var2_2) {
            throw this._constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + var2_2.getMessage());
        }
        this._tokenIncomplete = false;
        return this._binaryValue;
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        int n2 = this._inputPtr;
        int n3 = this._currInputRowStart;
        return new JsonLocation(this._ioContext.getSourceReference(), this._currInputProcessed + (long)this._inputPtr, -1, this._currInputRow, n2 - n3 + 1);
    }

    @Override
    public Object getInputSource() {
        return this._inputStream;
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(this._currToken);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken.id()) {
            default: {
                return this._currToken.asCharArray();
            }
            case 5: {
                if (!this._nameCopied) {
                    String string2 = this._parsingContext.getCurrentName();
                    int n2 = string2.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(n2);
                    } else if (this._nameCopyBuffer.length < n2) {
                        this._nameCopyBuffer = new char[n2];
                    }
                    string2.getChars(0, n2, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            }
            case 6: {
                if (!this._tokenIncomplete) break;
                this._tokenIncomplete = false;
                this._finishString();
            }
            case 7: 
            case 8: 
        }
        return this._textBuffer.getTextBuffer();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int getTextLength() throws IOException, JsonParseException {
        int n2 = 0;
        if (this._currToken == null) return n2;
        switch (this._currToken.id()) {
            default: {
                return this._currToken.asCharArray().length;
            }
            case 5: {
                return this._parsingContext.getCurrentName().length();
            }
            case 6: {
                if (!this._tokenIncomplete) return this._textBuffer.size();
                this._tokenIncomplete = false;
                this._finishString();
            }
            case 7: 
            case 8: 
        }
        return this._textBuffer.size();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        if (this._currToken == null) return 0;
        switch (this._currToken.id()) {
            default: {
                return 0;
            }
            case 6: {
                if (!this._tokenIncomplete) return this._textBuffer.getTextOffset();
                this._tokenIncomplete = false;
                this._finishString();
            }
            case 7: 
            case 8: 
        }
        return this._textBuffer.getTextOffset();
    }

    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._ioContext.getSourceReference(), this.getTokenCharacterOffset(), -1, this.getTokenLineNr(), this.getTokenColumnNr());
    }

    @Override
    public String getValueAsString() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return super.getValueAsString(null);
    }

    @Override
    public String getValueAsString(String string2) throws IOException, JsonParseException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return super.getValueAsString(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected final boolean loadMore() throws IOException {
        boolean bl2 = false;
        this._currInputProcessed += (long)this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        boolean bl3 = bl2;
        if (this._inputStream == null) return bl3;
        int n2 = this._inputStream.read(this._inputBuffer, 0, this._inputBuffer.length);
        if (n2 > 0) {
            this._inputPtr = 0;
            this._inputEnd = n2;
            return true;
        }
        this._closeInput();
        bl3 = bl2;
        if (n2 != 0) return bl3;
        throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Boolean nextBooleanValue() throws IOException {
        Boolean bl2 = null;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (jsonToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
            if (jsonToken != JsonToken.START_OBJECT) return bl2;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return null;
        }
        switch (this.nextToken().id()) {
            default: {
                return null;
            }
            case 9: {
                return Boolean.TRUE;
            }
            case 10: 
        }
        return Boolean.FALSE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean nextFieldName(SerializableString serializableString) throws IOException {
        int n2;
        int n3;
        byte[] arrby;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return false;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        if ((n3 = this._skipWSOrEnd()) < 0) {
            this.close();
            this._currToken = null;
            return false;
        }
        this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr - 1;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = this._inputPtr - this._currInputRowStart - 1;
        this._binaryValue = null;
        if (n3 == 93) {
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n3, '}');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = JsonToken.END_ARRAY;
            return false;
        }
        if (n3 == 125) {
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(n3, ']');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = JsonToken.END_OBJECT;
            return false;
        }
        int n4 = n3;
        if (this._parsingContext.expectComma()) {
            if (n3 != 44) {
                this._reportUnexpectedChar(n3, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
            }
            n4 = this._skipWS();
        }
        if (!this._parsingContext.inObject()) {
            this._nextTokenNotInObject(n4);
            return false;
        }
        if (n4 != 34 || this._inputPtr + (n3 = (arrby = serializableString.asQuotedUTF8()).length) + 4 >= this._inputEnd || this._inputBuffer[n2 = this._inputPtr + n3] != 34) return this._isNextTokenNameMaybe(n4, serializableString);
        int n5 = 0;
        n3 = this._inputPtr;
        do {
            if (n3 == n2) {
                this._parsingContext.setCurrentName(serializableString.getValue());
                this._isNextTokenNameYes(this._skipColonFast(n3 + 1));
                return true;
            }
            if (arrby[n5] != this._inputBuffer[n3]) {
                return this._isNextTokenNameMaybe(n4, serializableString);
            }
            ++n5;
            ++n3;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int nextIntValue(int n2) throws IOException {
        int n3;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return this.getIntValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return n2;
            }
            n3 = n2;
            if (jsonToken != JsonToken.START_OBJECT) return n3;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return n2;
        }
        n3 = n2;
        if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return n3;
        return this.getIntValue();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public long nextLongValue(long l2) throws IOException {
        long l3;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return this.getLongValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return l2;
            }
            l3 = l2;
            if (jsonToken != JsonToken.START_OBJECT) return l3;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return l2;
        }
        l3 = l2;
        if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return l3;
        return this.getLongValue();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String nextTextValue() throws IOException {
        String string2 = null;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_STRING) {
                if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
                this._tokenIncomplete = false;
                this._finishString();
                return this._textBuffer.contentsAsString();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
            if (jsonToken != JsonToken.START_OBJECT) return string2;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return null;
        }
        if (this.nextToken() != JsonToken.VALUE_STRING) return string2;
        return this.getText();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonToken nextToken() throws IOException {
        int n2;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nextAfterName();
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        if ((n2 = this._skipWSOrEnd()) < 0) {
            this.close();
            this._currToken = null;
            return null;
        }
        this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr - 1;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = this._inputPtr - this._currInputRowStart - 1;
        this._binaryValue = null;
        if (n2 == 93) {
            JsonToken jsonToken;
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n2, '}');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = jsonToken = JsonToken.END_ARRAY;
            return jsonToken;
        }
        if (n2 == 125) {
            JsonToken jsonToken;
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(n2, ']');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = jsonToken = JsonToken.END_OBJECT;
            return jsonToken;
        }
        int n3 = n2;
        if (this._parsingContext.expectComma()) {
            if (n2 != 44) {
                this._reportUnexpectedChar(n2, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
            }
            n3 = this._skipWS();
        }
        if (!this._parsingContext.inObject()) {
            return this._nextTokenNotInObject(n3);
        }
        Object object = this._parseName(n3);
        this._parsingContext.setCurrentName(object.getName());
        this._currToken = JsonToken.FIELD_NAME;
        n3 = this._skipColon();
        if (n3 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return this._currToken;
        }
        switch (n3) {
            default: {
                object = this._handleUnexpectedValue(n3);
                break;
            }
            case 45: {
                object = this._parseNegNumber();
                break;
            }
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                object = this._parsePosNumber(n3);
                break;
            }
            case 102: {
                this._matchToken("false", 1);
                object = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchToken("null", 1);
                object = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchToken("true", 1);
                object = JsonToken.VALUE_TRUE;
                break;
            }
            case 91: {
                object = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                object = JsonToken.START_OBJECT;
            }
        }
        this._nextToken = object;
        return this._currToken;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected final Name parseEscapedName(int[] var1_1, int var2_2, int var3_3, int var4_4, int var5_5) throws IOException {
        var8_6 = UTF8StreamJsonParser._icLatin1;
        do {
            var6_7 = var4_4;
            if (var8_6[var4_4] == 0) ** GOTO lbl-1000
            if (var4_4 == 34) {
                var7_8 = var1_1;
                var4_4 = var2_2;
                if (var5_5 > 0) {
                    var7_8 = var1_1;
                    if (var2_2 >= var1_1.length) {
                        var7_8 = UTF8StreamJsonParser.growArrayBy((int[])var1_1, var1_1.length);
                        this._quadBuffer = var7_8;
                    }
                    var7_8[var2_2] = var3_3;
                    var4_4 = var2_2 + 1;
                }
                var1_1 = var8_6 = (Object)this._symbols.findName((int[])var7_8, var4_4);
                if (var8_6 != null) return var1_1;
                return this.addName((int[])var7_8, var4_4, var5_5);
            }
            if (var4_4 != 92) {
                this._throwUnquotedSpace(var4_4, "name");
            } else {
                var4_4 = this._decodeEscaped();
            }
            var6_7 = var4_4;
            if (var4_4 > 127) {
                if (var5_5 >= 4) {
                    var7_8 = var1_1;
                    if (var2_2 >= var1_1.length) {
                        var7_8 = UTF8StreamJsonParser.growArrayBy((int[])var1_1, var1_1.length);
                        this._quadBuffer = var7_8;
                    }
                    var6_7 = var2_2 + 1;
                    var7_8[var2_2] = var3_3;
                    var3_3 = 0;
                    var5_5 = 0;
                    var1_1 = var7_8;
                    var2_2 = var6_7;
                }
                if (var4_4 < 2048) {
                    var6_7 = var3_3 << 8 | (var4_4 >> 6 | 192);
                    var3_3 = var5_5 + 1;
                    var5_5 = var2_2;
                    var2_2 = var6_7;
                } else {
                    var3_3 = var3_3 << 8 | (var4_4 >> 12 | 224);
                    var6_7 = var5_5 + 1;
                    if (var6_7 >= 4) {
                        var7_8 = var1_1;
                        if (var2_2 >= var1_1.length) {
                            var7_8 = UTF8StreamJsonParser.growArrayBy((int[])var1_1, var1_1.length);
                            this._quadBuffer = var7_8;
                        }
                        var6_7 = var2_2 + 1;
                        var7_8[var2_2] = var3_3;
                        var5_5 = 0;
                        var3_3 = 0;
                        var2_2 = var6_7;
                        var1_1 = var7_8;
                    } else {
                        var5_5 = var3_3;
                        var3_3 = var6_7;
                    }
                    var6_7 = var5_5 << 8 | (var4_4 >> 6 & 63 | 128);
                    ++var3_3;
                    var5_5 = var2_2;
                    var2_2 = var6_7;
                }
                var4_4 = var4_4 & 63 | 128;
                var6_7 = var3_3;
                var3_3 = var4_4;
                var4_4 = var2_2;
                var2_2 = var5_5;
            } else lbl-1000: // 2 sources:
            {
                var4_4 = var3_3;
                var3_3 = var6_7;
                var6_7 = var5_5;
            }
            if (var6_7 < 4) {
                var5_5 = var6_7 + 1;
                var3_3 = var4_4 << 8 | var3_3;
            } else {
                var7_8 = var1_1;
                if (var2_2 >= var1_1.length) {
                    var7_8 = UTF8StreamJsonParser.growArrayBy((int[])var1_1, var1_1.length);
                    this._quadBuffer = var7_8;
                }
                var5_5 = var2_2 + 1;
                var7_8[var2_2] = var4_4;
                var4_4 = 1;
                var1_1 = var7_8;
                var2_2 = var5_5;
                var5_5 = var4_4;
            }
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in field name");
            }
            var7_8 = this._inputBuffer;
            var4_4 = this._inputPtr;
            this._inputPtr = var4_4 + 1;
            var4_4 = var7_8[var4_4] & 255;
        } while (true);
    }

    protected final Name parseLongName(int n2, int n3) throws IOException {
        this._quadBuffer[0] = this._quad1;
        this._quadBuffer[1] = n3;
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        int n4 = 2;
        n3 = n2;
        n2 = n4;
        while (this._inputPtr + 4 <= this._inputEnd) {
            n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            if (arrn[n4 = arrby[n4] & 255] != 0) {
                if (n4 == 34) {
                    return this.findName(this._quadBuffer, n2, n3, 1);
                }
                return this.parseEscapedName(this._quadBuffer, n2, n3, n4, 1);
            }
            n3 = n3 << 8 | n4;
            n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            if (arrn[n4 = arrby[n4] & 255] != 0) {
                if (n4 == 34) {
                    return this.findName(this._quadBuffer, n2, n3, 2);
                }
                return this.parseEscapedName(this._quadBuffer, n2, n3, n4, 2);
            }
            n3 = n3 << 8 | n4;
            n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            if (arrn[n4 = arrby[n4] & 255] != 0) {
                if (n4 == 34) {
                    return this.findName(this._quadBuffer, n2, n3, 3);
                }
                return this.parseEscapedName(this._quadBuffer, n2, n3, n4, 3);
            }
            n4 = n3 << 8 | n4;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            if (arrn[n3 = arrby[n3] & 255] != 0) {
                if (n3 == 34) {
                    return this.findName(this._quadBuffer, n2, n4, 4);
                }
                return this.parseEscapedName(this._quadBuffer, n2, n4, n3, 4);
            }
            if (n2 >= this._quadBuffer.length) {
                this._quadBuffer = UTF8StreamJsonParser.growArrayBy(this._quadBuffer, n2);
            }
            this._quadBuffer[n2] = n4;
            ++n2;
        }
        return this.parseEscapedName(this._quadBuffer, n2, 0, n3, 0);
    }

    protected final Name parseMediumName(int n2) throws IOException {
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 == 34) {
                return this.findName(this._quad1, n2, 1);
            }
            return this.parseName(this._quad1, n2, n3, 1);
        }
        n2 = n2 << 8 | n3;
        n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 == 34) {
                return this.findName(this._quad1, n2, 2);
            }
            return this.parseName(this._quad1, n2, n3, 2);
        }
        n2 = n2 << 8 | n3;
        n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 == 34) {
                return this.findName(this._quad1, n2, 3);
            }
            return this.parseName(this._quad1, n2, n3, 3);
        }
        n2 = n2 << 8 | n3;
        n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 == 34) {
                return this.findName(this._quad1, n2, 4);
            }
            return this.parseName(this._quad1, n2, n3, 4);
        }
        return this.parseLongName(n3, n2);
    }

    @Override
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException, JsonParseException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            base64Variant = (Base64Variant)this.getBinaryValue(base64Variant);
            outputStream.write((byte[])base64Variant);
            return base64Variant.length;
        }
        byte[] arrby = this._ioContext.allocBase64Buffer();
        try {
            int n2 = this._readBinary(base64Variant, outputStream, arrby);
            return n2;
        }
        finally {
            this._ioContext.releaseBase64Buffer(arrby);
        }
    }

    @Override
    public int releaseBuffered(OutputStream outputStream) throws IOException {
        int n2 = this._inputEnd - this._inputPtr;
        if (n2 < 1) {
            return 0;
        }
        int n3 = this._inputPtr;
        outputStream.write(this._inputBuffer, n3, n2);
        return n2;
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    protected Name slowParseName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing '\"' for name");
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if ((n2 = arrby[n2] & 255) == 34) {
            return BytesToNameCanonicalizer.getEmptyName();
        }
        return this.parseEscapedName(this._quadBuffer, 0, 0, n2, 0);
    }
}

