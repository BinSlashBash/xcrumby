/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class ReaderBasedJsonParser
extends ParserBase {
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
    protected boolean _bufferRecyclable;
    protected final int _hashSeed;
    protected char[] _inputBuffer;
    protected ObjectCodec _objectCodec;
    protected Reader _reader;
    protected final CharsToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete = false;

    public ReaderBasedJsonParser(IOContext iOContext, int n2, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
        super(iOContext, n2);
        this._reader = reader;
        this._inputBuffer = iOContext.allocTokenBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = true;
    }

    public ReaderBasedJsonParser(IOContext iOContext, int n2, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer, char[] arrc, int n3, int n4, boolean bl2) {
        super(iOContext, n2);
        this._reader = reader;
        this._inputBuffer = arrc;
        this._inputPtr = n3;
        this._inputEnd = n4;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String _handleOddName2(int n2, int n3, int[] object) throws IOException {
        int n4;
        this._textBuffer.resetWithShared(this._inputBuffer, n2, this._inputPtr - n2);
        char[] arrc = this._textBuffer.getCurrentSegment();
        n2 = this._textBuffer.getCurrentSegmentSize();
        int n5 = object.length;
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            char c2 = this._inputBuffer[this._inputPtr];
            if (c2 <= n5) {
                if (object[c2] != false) break;
            } else if (!Character.isJavaIdentifierPart(c2)) break;
            ++this._inputPtr;
            n3 = n3 * 33 + c2;
            n4 = n2 + 1;
            arrc[n2] = c2;
            if (n4 >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n2 = 0;
                continue;
            }
            n2 = n4;
        }
        this._textBuffer.setCurrentLength(n2);
        object = this._textBuffer;
        arrc = object.getTextBuffer();
        n2 = object.getTextOffset();
        n4 = object.size();
        return this._symbols.findSymbol(arrc, n2, n4, n3);
    }

    private final void _matchFalse() throws IOException {
        char c2;
        char[] arrc;
        int n2 = this._inputPtr;
        if (n2 + 4 < this._inputEnd && (arrc = this._inputBuffer)[n2] == 'a' && arrc[++n2] == 'l' && arrc[++n2] == 's' && arrc[++n2] == 'e' && ((c2 = arrc[++n2]) < '0' || c2 == ']' || c2 == '}')) {
            this._inputPtr = n2;
            return;
        }
        this._matchToken("false", 1);
    }

    private final void _matchNull() throws IOException {
        char c2;
        char[] arrc;
        int n2 = this._inputPtr;
        if (n2 + 3 < this._inputEnd && (arrc = this._inputBuffer)[n2] == 'u' && arrc[++n2] == 'l' && arrc[++n2] == 'l' && ((c2 = arrc[++n2]) < '0' || c2 == ']' || c2 == '}')) {
            this._inputPtr = n2;
            return;
        }
        this._matchToken("null", 1);
    }

    private final void _matchTrue() throws IOException {
        char c2;
        char[] arrc;
        int n2 = this._inputPtr;
        if (n2 + 3 < this._inputEnd && (arrc = this._inputBuffer)[n2] == 'r' && arrc[++n2] == 'u' && arrc[++n2] == 'e' && ((c2 = arrc[++n2]) < '0' || c2 == ']' || c2 == '}')) {
            this._inputPtr = n2;
            return;
        }
        this._matchToken("true", 1);
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final JsonToken _parseFloat(int var1_1, int var2_2, int var3_3, boolean var4_4, int var5_5) throws IOException {
        block11 : {
            var12_6 = this._inputEnd;
            var8_7 = 0;
            var9_8 = 0;
            var6_9 = var1_1;
            var7_10 = var3_3;
            if (var1_1 != 46) ** GOTO lbl24
            var6_9 = var3_3;
            var1_1 = var9_8;
            do {
                if (var6_9 >= var12_6) {
                    return this._parseNumber2(var4_4, var2_2);
                }
                var13_11 = this._inputBuffer;
                var3_3 = var6_9 + 1;
                var9_8 = var13_11[var6_9];
                if (var9_8 >= 48 && var9_8 <= 57) ** GOTO lbl35
                var8_7 = var1_1;
                var6_9 = var9_8;
                var7_10 = var3_3;
                if (var1_1 == 0) {
                    this.reportUnexpectedNumberChar(var9_8, "Decimal point not followed by a digit");
                    var7_10 = var3_3;
                    var6_9 = var9_8;
                    var8_7 = var1_1;
                }
lbl24: // 4 sources:
                var1_1 = var7_10;
                var7_10 = 0;
                var11_12 = 0;
                if (var6_9 == 101) ** GOTO lbl-1000
                var10_13 = var1_1;
                var9_8 = var6_9;
                if (var6_9 == 69) lbl-1000: // 2 sources:
                {
                    if (var1_1 < var12_6) break;
                    this._inputPtr = var2_2;
                    return this._parseNumber2(var4_4, var2_2);
                }
                break block11;
lbl35: // 1 sources:
                ++var1_1;
                var6_9 = var3_3;
            } while (true);
            var13_11 = this._inputBuffer;
            var6_9 = var1_1 + 1;
            var3_3 = var13_11[var1_1];
            if (var3_3 == 45 || var3_3 == 43) {
                if (var6_9 >= var12_6) {
                    this._inputPtr = var2_2;
                    return this._parseNumber2(false, var2_2);
                }
                var13_11 = this._inputBuffer;
                var1_1 = var6_9 + 1;
                var3_3 = var13_11[var6_9];
                var6_9 = var11_12;
            } else {
                var1_1 = var6_9;
                var6_9 = var11_12;
            }
            while (var3_3 <= 57 && var3_3 >= 48) {
                ++var6_9;
                if (var1_1 >= var12_6) {
                    this._inputPtr = var2_2;
                    return this._parseNumber2(var4_4, var2_2);
                }
                var3_3 = this._inputBuffer[var1_1];
                ++var1_1;
            }
            var7_10 = var6_9;
            var10_13 = var1_1;
            var9_8 = var3_3;
            if (var6_9 == 0) {
                this.reportUnexpectedNumberChar(var3_3, "Exponent indicator not followed by a digit");
                var9_8 = var3_3;
                var10_13 = var1_1;
                var7_10 = var6_9;
            }
        }
        this._inputPtr = var1_1 = var10_13 - 1;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(var9_8);
        }
        this._textBuffer.resetWithShared(this._inputBuffer, var2_2, var1_1 - var2_2);
        return this.resetFloat(var4_4, var5_5, var8_7, var7_10);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String _parseName2(int n2, int n3, int n4) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, n2, this._inputPtr - n2);
        Object object = this._textBuffer.getCurrentSegment();
        n2 = this._textBuffer.getCurrentSegmentSize();
        do {
            char c2;
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(": was expecting closing '" + (char)n4 + "' for name");
            }
            char[] arrc = this._inputBuffer;
            int n5 = this._inputPtr;
            this._inputPtr = n5 + 1;
            char c3 = c2 = arrc[n5];
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c3 = this._decodeEscaped();
                } else {
                    c3 = c2;
                    if (c2 <= n4) {
                        if (c2 == n4) {
                            this._textBuffer.setCurrentLength(n2);
                            object = this._textBuffer;
                            arrc = object.getTextBuffer();
                            n2 = object.getTextOffset();
                            n4 = object.size();
                            return this._symbols.findSymbol(arrc, n2, n4, n3);
                        }
                        c3 = c2;
                        if (c2 < ' ') {
                            this._throwUnquotedSpace(c2, "name");
                            c3 = c2;
                        }
                    }
                }
            }
            n3 = n3 * 33 + c2;
            n5 = n2 + 1;
            object[n2] = c3;
            if (n5 >= object.length) {
                object = this._textBuffer.finishCurrentSegment();
                n2 = 0;
                continue;
            }
            n2 = n5;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final JsonToken _parseNumber2(boolean var1_1, int var2_2) throws IOException {
        block29 : {
            var5_3 = var2_2;
            if (var1_1) {
                var5_3 = var2_2 + 1;
            }
            this._inputPtr = var5_3;
            var16_4 = this._textBuffer.emptyAndGetCurrentSegment();
            var2_2 = 0;
            if (var1_1) {
                var16_4[0] = 45;
                var2_2 = 0 + 1;
            }
            var6_5 = 0;
            if (this._inputPtr < this._inputEnd) {
                var15_6 = this._inputBuffer;
                var5_3 = this._inputPtr;
                this._inputPtr = var5_3 + 1;
                var3_7 = var15_6[var5_3];
            } else {
                var3_7 = this.getNextChar("No digit following minus sign");
            }
            var4_8 = var3_7;
            if (var3_7 == '0') {
                var4_8 = this._verifyNoLeadingZeroes();
            }
            var7_9 = 0;
            var3_7 = var4_8;
            while (var3_7 >= '0' && var3_7 <= '9') {
                ++var6_5;
                var15_6 = var16_4;
                var5_3 = var2_2;
                if (var2_2 >= var16_4.length) {
                    var15_6 = this._textBuffer.finishCurrentSegment();
                    var5_3 = 0;
                }
                var2_2 = var5_3 + 1;
                var15_6[var5_3] = var3_7;
                if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                    var3_7 = '\u0000';
                    var5_3 = 1;
                    var10_10 = var6_5;
                    break block29;
                }
                var16_4 = this._inputBuffer;
                var5_3 = this._inputPtr;
                this._inputPtr = var5_3 + 1;
                var3_7 = var16_4[var5_3];
                var16_4 = var15_6;
            }
            var5_3 = var7_9;
            var10_10 = var6_5;
            var15_6 = var16_4;
        }
        if (var10_10 == 0) {
            this.reportInvalidNumber("Missing integer part (next char " + ReaderBasedJsonParser._getCharDesc(var3_7) + ")");
        }
        var11_11 = 0;
        var7_9 = 0;
        if (var3_7 != '.') {
            var4_8 = var3_7;
            var16_4 = var15_6;
        } else {
            var6_5 = var2_2 + 1;
            var15_6[var2_2] = var3_7;
            do {
                if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                    var8_12 = 1;
                    break;
                }
                var16_4 = this._inputBuffer;
                var2_2 = this._inputPtr;
                this._inputPtr = var2_2 + 1;
                var3_7 = var4_8 = var16_4[var2_2];
                var8_12 = var5_3;
                if (var4_8 < '0') break;
                var3_7 = var4_8;
                var8_12 = var5_3;
                if (var4_8 > '9') break;
                ++var7_9;
                var16_4 = var15_6;
                var2_2 = var6_5;
                if (var6_5 >= var15_6.length) {
                    var16_4 = this._textBuffer.finishCurrentSegment();
                    var2_2 = 0;
                }
                var16_4[var2_2] = var4_8;
                var6_5 = var2_2 + 1;
                var3_7 = var4_8;
                var15_6 = var16_4;
            } while (true);
            var4_8 = var3_7;
            var5_3 = var8_12;
            var11_11 = var7_9;
            var16_4 = var15_6;
            var2_2 = var6_5;
            if (var7_9 == 0) {
                this.reportUnexpectedNumberChar(var3_7, "Decimal point not followed by a digit");
                var2_2 = var6_5;
                var16_4 = var15_6;
                var11_11 = var7_9;
                var5_3 = var8_12;
                var4_8 = var3_7;
            }
        }
        var12_13 = 0;
        var7_9 = 0;
        if (var4_8 == 'e') ** GOTO lbl100
        var14_14 = var4_8;
        var9_15 = var5_3;
        var13_16 = var2_2;
        if (var4_8 != 'E') ** GOTO lbl160
lbl100: // 2 sources:
        var15_6 = var16_4;
        var6_5 = var2_2;
        if (var2_2 >= var16_4.length) {
            var15_6 = this._textBuffer.finishCurrentSegment();
            var6_5 = 0;
        }
        var2_2 = var6_5 + 1;
        var15_6[var6_5] = var4_8;
        if (this._inputPtr < this._inputEnd) {
            var16_4 = this._inputBuffer;
            var6_5 = this._inputPtr;
            this._inputPtr = var6_5 + 1;
            var3_7 = var16_4[var6_5];
        } else {
            var3_7 = this.getNextChar("expected a digit for number exponent");
        }
        if (var3_7 == '-' || var3_7 == '+') {
            if (var2_2 >= var15_6.length) {
                var15_6 = this._textBuffer.finishCurrentSegment();
                var2_2 = 0;
            }
            var15_6[var2_2] = var3_7;
            if (this._inputPtr < this._inputEnd) {
                var16_4 = this._inputBuffer;
                var6_5 = this._inputPtr;
                this._inputPtr = var6_5 + 1;
                var3_7 = var16_4[var6_5];
            } else {
                var3_7 = this.getNextChar("expected a digit for number exponent");
            }
            ++var2_2;
            var9_15 = var7_9;
        } else {
            var9_15 = var7_9;
        }
        do {
            var8_12 = var5_3;
            var7_9 = var9_15;
            var6_5 = var2_2;
            if (var3_7 > '9') ** GOTO lbl150
            var8_12 = var5_3;
            var7_9 = var9_15;
            var6_5 = var2_2;
            if (var3_7 < '0') ** GOTO lbl150
            var7_9 = var9_15 + 1;
            var16_4 = var15_6;
            var6_5 = var2_2;
            if (var2_2 >= var15_6.length) {
                var16_4 = this._textBuffer.finishCurrentSegment();
                var6_5 = 0;
            }
            var2_2 = var6_5 + 1;
            var16_4[var6_5] = var3_7;
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                var8_12 = 1;
                var6_5 = var2_2;
lbl150: // 3 sources:
                var14_14 = var3_7;
                var9_15 = var8_12;
                var12_13 = var7_9;
                var13_16 = var6_5;
                if (var7_9 == 0) {
                    this.reportUnexpectedNumberChar(var3_7, "Exponent indicator not followed by a digit");
                    var13_16 = var6_5;
                    var12_13 = var7_9;
                    var9_15 = var8_12;
                    var14_14 = var3_7;
                }
lbl160: // 4 sources:
                if (var9_15 == 0) {
                    --this._inputPtr;
                    if (this._parsingContext.inRoot()) {
                        this._verifyRootSpace(var14_14);
                    }
                }
                this._textBuffer.setCurrentLength(var13_16);
                return this.reset(var1_1, var10_10, var11_11, var12_13);
            }
            var15_6 = this._inputBuffer;
            var6_5 = this._inputPtr;
            this._inputPtr = var6_5 + 1;
            var3_7 = var15_6[var6_5];
            var9_15 = var7_9;
            var15_6 = var16_4;
        } while (true);
    }

    private final int _skipAfterComma2() throws IOException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            char[] arrc = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrc[n2]) > 32) {
                if (n2 == 47) {
                    this._skipComment();
                    continue;
                }
                if (n2 == 35 && this._skipYAMLComment()) continue;
                return n2;
            }
            if (n2 >= 32) continue;
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
     * Exception decompiling
     */
    private void _skipCComment() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:412)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:219)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:619)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:45)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:669)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
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
        do {
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrc[n2]) > 32) {
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
            if (n2 >= 32) continue;
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
        } while (true);
    }

    private final int _skipComma(int n2) throws IOException {
        if (n2 != 44) {
            this._reportUnexpectedChar(n2, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
        }
        while (this._inputPtr < this._inputEnd) {
            char[] arrc = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrc[n2]) > 32) {
                if (n2 == 47 || n2 == 35) {
                    --this._inputPtr;
                    return this._skipAfterComma2();
                }
                return n2;
            }
            if (n2 >= 32) continue;
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
        return this._skipAfterComma2();
    }

    private void _skipComment() throws IOException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in a comment");
        }
        char[] arrc = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if ((n2 = arrc[n2]) == 47) {
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
    private void _skipLine() throws IOException {
        while (!(this._inputPtr >= this._inputEnd && !this.loadMore())) {
            char[] arrc = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrc[n2]) >= 32) continue;
            if (n2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                return;
            }
            if (n2 == 13) {
                this._skipCR();
                return;
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            return this._eofAsNextChar();
        }
        char[] arrc = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        int n3 = arrc[n2];
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
            arrc = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n3 = arrc[n2];
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            char[] arrc = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n3 = arrc[n2];
            if (n3 > 32) {
                if (n3 == 47) {
                    this._skipComment();
                    continue;
                }
                n2 = n3;
                if (n3 != 35) return n2;
                n2 = n3;
                if (!this._skipYAMLComment()) return n2;
                continue;
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
        return this._eofAsNextChar();
    }

    private boolean _skipYAMLComment() throws IOException {
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
    private char _verifyNLZ2() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            return '0';
        }
        char c2 = this._inputBuffer[this._inputPtr];
        if (c2 < '0') return '0';
        if (c2 > '9') {
            return '0';
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        char c3 = c2;
        if (c2 != 48) return c3;
        c3 = c2;
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this.loadMore()) return c3;
            }
            if ((c2 = this._inputBuffer[this._inputPtr]) < 48) return '0';
            if (c2 > '9') {
                return '0';
            }
            ++this._inputPtr;
            c3 = c2;
        } while (c2 == 48);
        return c2;
    }

    private final char _verifyNoLeadingZeroes() throws IOException {
        char c2;
        if (this._inputPtr < this._inputEnd && ((c2 = this._inputBuffer[this._inputPtr]) < '0' || c2 > '9')) {
            return '0';
        }
        return this._verifyNLZ2();
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

    @Override
    protected void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }

    protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder byteArrayBuilder = this._getByteArrayBuilder();
        do {
            int n2;
            int n3;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            char c2 = arrc[n4];
            if (c2 <= ' ') continue;
            n4 = n3 = base64Variant.decodeBase64Char(c2);
            if (n3 < 0) {
                if (c2 == '\"') {
                    return byteArrayBuilder.toByteArray();
                }
                n4 = this._decodeBase64Escape(base64Variant, c2, 0);
                if (n4 < 0) continue;
            }
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            c2 = arrc[n3];
            n3 = n2 = base64Variant.decodeBase64Char(c2);
            if (n2 < 0) {
                n3 = this._decodeBase64Escape(base64Variant, c2, 1);
            }
            int n5 = n4 << 6 | n3;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            c2 = arrc[n4];
            n2 = n3 = base64Variant.decodeBase64Char(c2);
            if (n3 < 0) {
                n4 = n3;
                if (n3 != -2) {
                    if (c2 == '\"' && !base64Variant.usesPadding()) {
                        byteArrayBuilder.append(n5 >> 4);
                        return byteArrayBuilder.toByteArray();
                    }
                    n4 = this._decodeBase64Escape(base64Variant, c2, 2);
                }
                n2 = n4;
                if (n4 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this.loadMoreGuaranteed();
                    }
                    arrc = this._inputBuffer;
                    n4 = this._inputPtr;
                    this._inputPtr = n4 + 1;
                    c2 = arrc[n4];
                    if (!base64Variant.usesPaddingChar(c2)) {
                        throw this.reportInvalidBase64Char(base64Variant, c2, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                    }
                    byteArrayBuilder.append(n5 >> 4);
                    continue;
                }
            }
            n5 = n5 << 6 | n2;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            c2 = arrc[n4];
            n2 = n3 = base64Variant.decodeBase64Char(c2);
            if (n3 < 0) {
                n4 = n3;
                if (n3 != -2) {
                    if (c2 == '\"' && !base64Variant.usesPadding()) {
                        byteArrayBuilder.appendTwoBytes(n5 >> 2);
                        return byteArrayBuilder.toByteArray();
                    }
                    n4 = this._decodeBase64Escape(base64Variant, c2, 3);
                }
                n2 = n4;
                if (n4 == -2) {
                    byteArrayBuilder.appendTwoBytes(n5 >> 2);
                    continue;
                }
            }
            byteArrayBuilder.appendThreeBytes(n5 << 6 | n2);
        } while (true);
    }

    @Override
    protected char _decodeEscaped() throws IOException {
        char c2;
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in character escape sequence");
        }
        char[] arrc = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        char c3 = c2 = arrc[n2];
        switch (c2) {
            default: {
                c3 = this._handleUnrecognizedCharacterEscape(c2);
            }
            case '\"': 
            case '/': 
            case '\\': {
                return c3;
            }
            case 'b': {
                return '\b';
            }
            case 't': {
                return '\t';
            }
            case 'n': {
                return '\n';
            }
            case 'f': {
                return '\f';
            }
            case 'r': {
                return '\r';
            }
            case 'u': 
        }
        int n3 = 0;
        for (n2 = 0; n2 < 4; ++n2) {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in character escape sequence");
            }
            arrc = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            int n5 = CharTypes.charToHex(n4 = arrc[n4]);
            if (n5 < 0) {
                this._reportUnexpectedChar(n4, "expected a hex-digit for character escape sequence");
            }
            n3 = n3 << 4 | n5;
        }
        return (char)n3;
    }

    @Override
    protected final void _finishString() throws IOException {
        int n2 = this._inputPtr;
        int n3 = this._inputEnd;
        int n4 = n2;
        if (n2 < n3) {
            int[] arrn = _icLatin1;
            int n5 = arrn.length;
            do {
                char c2;
                if ((c2 = this._inputBuffer[n2]) < n5 && arrn[c2] != 0) {
                    n4 = n2;
                    if (c2 != '\"') break;
                    this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, n2 - this._inputPtr);
                    this._inputPtr = n2 + 1;
                    return;
                }
                n2 = n4 = n2 + 1;
            } while (n4 < n3);
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, n4 - this._inputPtr);
        this._inputPtr = n4;
        this._finishString2();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _finishString2() throws IOException {
        char[] arrc = this._textBuffer.getCurrentSegment();
        int n2 = this._textBuffer.getCurrentSegmentSize();
        int[] arrn = _icLatin1;
        int n3 = arrn.length;
        do {
            char c2;
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] arrc2 = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            char c3 = c2 = arrc2[n4];
            if (c2 < n3) {
                c3 = c2;
                if (arrn[c2] != 0) {
                    if (c2 == '\"') {
                        this._textBuffer.setCurrentLength(n2);
                        return;
                    }
                    if (c2 == '\\') {
                        c3 = this._decodeEscaped();
                    } else {
                        c3 = c2;
                        if (c2 < ' ') {
                            this._throwUnquotedSpace(c2, "string value");
                            c3 = c2;
                        }
                    }
                }
            }
            arrc2 = arrc;
            n4 = n2;
            if (n2 >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n4 = 0;
            }
            arrc2[n4] = c3;
            n2 = n4 + 1;
            arrc = arrc2;
        } while (true);
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
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n2 = this._textBuffer.getCurrentSegmentSize();
        do {
            char c2;
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] arrc2 = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            char c3 = c2 = arrc2[n3];
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c3 = this._decodeEscaped();
                } else {
                    c3 = c2;
                    if (c2 <= '\'') {
                        if (c2 == '\'') {
                            this._textBuffer.setCurrentLength(n2);
                            return JsonToken.VALUE_STRING;
                        }
                        c3 = c2;
                        if (c2 < ' ') {
                            this._throwUnquotedSpace(c2, "string value");
                            c3 = c2;
                        }
                    }
                }
            }
            arrc2 = arrc;
            n3 = n2;
            if (n2 >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n3 = 0;
            }
            arrc2[n3] = c3;
            n2 = n3 + 1;
            arrc = arrc2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonToken _handleInvalidNumberStart(int n2, boolean bl2) throws IOException {
        double d2 = Double.NEGATIVE_INFINITY;
        int n3 = n2;
        if (n2 == 73) {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOFInValue();
            }
            Object object = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = object[n2]) == 78) {
                object = bl2 ? (Object)"-INF" : "+INF";
                this._matchToken((String)object, 3);
                if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    if (bl2) {
                        return this.resetAsNaN((String)object, d2);
                    }
                    d2 = Double.POSITIVE_INFINITY;
                    return this.resetAsNaN((String)object, d2);
                }
                this._reportError("Non-standard token '" + (String)object + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                n3 = n2;
            } else {
                n3 = n2;
                if (n2 == 110) {
                    object = bl2 ? "-Infinity" : "+Infinity";
                    this._matchToken((String)object, 3);
                    if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                        if (bl2) {
                            return this.resetAsNaN((String)object, d2);
                        }
                        d2 = Double.POSITIVE_INFINITY;
                        return this.resetAsNaN((String)object, d2);
                    }
                    this._reportError("Non-standard token '" + (String)object + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    n3 = n2;
                }
            }
        }
        this.reportUnexpectedNumberChar(n3, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected String _handleOddName(int n2) throws IOException {
        int n3;
        int[] arrn;
        if (n2 == 39 && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseAposName();
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(n2, "was expecting double-quote to start field name");
        }
        boolean bl2 = n2 < (n3 = (arrn = CharTypes.getInputCodeLatin1JsNames()).length) ? arrn[n2] == 0 : Character.isJavaIdentifierPart((char)n2);
        if (!bl2) {
            this._reportUnexpectedChar(n2, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int n4 = this._inputPtr;
        int n5 = this._hashSeed;
        int n6 = this._inputEnd;
        int n7 = n5;
        n2 = n4;
        if (n4 < n6) {
            n2 = n4;
            do {
                if ((n7 = this._inputBuffer[n2]) < n3) {
                    if (arrn[n7] != 0) {
                        n7 = this._inputPtr - 1;
                        this._inputPtr = n2;
                        return this._symbols.findSymbol(this._inputBuffer, n7, n2 - n7, n5);
                    }
                } else if (!Character.isJavaIdentifierPart((char)n7)) {
                    n7 = this._inputPtr - 1;
                    this._inputPtr = n2;
                    return this._symbols.findSymbol(this._inputBuffer, n7, n2 - n7, n5);
                }
                n7 = n5 * 33 + n7;
                n4 = n2 + 1;
                n5 = n7;
                n2 = n4;
            } while (n4 < n6);
            n2 = n4;
        }
        n5 = this._inputPtr;
        this._inputPtr = n2;
        return this._handleOddName2(n5 - 1, n7, arrn);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected JsonToken _handleOddValue(int var1_1) throws IOException {
        switch (var1_1) {
            case 39: {
                if (this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApos();
                }
                ** GOTO lbl17
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
lbl17: // 4 sources:
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
        return this._handleInvalidNumberStart(var2_2[var1_1], false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _matchToken(String string2, int n2) throws IOException {
        char c2;
        int n3;
        int n4 = string2.length();
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidToken(string2.substring(0, n2));
            }
            if (this._inputBuffer[this._inputPtr] != string2.charAt(n2)) {
                this._reportInvalidToken(string2.substring(0, n2));
            }
            ++this._inputPtr;
            n2 = n3 = n2 + 1;
        } while (n3 < n4);
        if (this._inputPtr >= this._inputEnd && !this.loadMore() || (c2 = this._inputBuffer[this._inputPtr]) < '0' || c2 == ']' || c2 == '}' || !Character.isJavaIdentifierPart(c2)) {
            return;
        }
        this._reportInvalidToken(string2.substring(0, n3));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected String _parseAposName() throws IOException {
        int n2;
        int n3;
        int n4;
        block3 : {
            int n5 = this._inputPtr;
            n3 = this._hashSeed;
            int n6 = this._inputEnd;
            n2 = n3;
            n4 = n5;
            if (n5 < n6) {
                int[] arrn = _icLatin1;
                int n7 = arrn.length;
                n4 = n5;
                n2 = n3;
                do {
                    if ((n3 = this._inputBuffer[n4]) == 39) {
                        n3 = this._inputPtr;
                        this._inputPtr = n4 + 1;
                        return this._symbols.findSymbol(this._inputBuffer, n3, n4 - n3, n2);
                    }
                    if (n3 < n7 && arrn[n3] != 0) break block3;
                    n5 = n2 * 33 + n3;
                    n3 = n4 + 1;
                    n2 = n5;
                    n4 = n3;
                } while (n3 < n6);
                n2 = n5;
                n4 = n3;
            }
        }
        n3 = this._inputPtr;
        this._inputPtr = n4;
        return this._parseName2(n3, n2, 39);
    }

    protected final String _parseName() throws IOException {
        int n2;
        int n3;
        int n4 = this._hashSeed;
        int[] arrn = _icLatin1;
        for (n2 = this._inputPtr; n2 < this._inputEnd; ++n2) {
            n3 = this._inputBuffer[n2];
            if (n3 < arrn.length && arrn[n3] != 0) {
                if (n3 != 34) break;
                n3 = this._inputPtr;
                this._inputPtr = n2 + 1;
                return this._symbols.findSymbol(this._inputBuffer, n3, n2 - n3, n4);
            }
            n4 = n4 * 33 + n3;
        }
        n3 = this._inputPtr;
        this._inputPtr = n2;
        return this._parseName2(n3, n4, 34);
    }

    protected final JsonToken _parseNegNumber() throws IOException {
        int n2;
        int n3 = this._inputPtr;
        int n4 = n3 - 1;
        int n5 = this._inputEnd;
        if (n3 >= n5) {
            return this._parseNumber2(true, n4);
        }
        char[] arrc = this._inputBuffer;
        int n6 = n3 + 1;
        if ((n3 = arrc[n3]) > 57 || n3 < 48) {
            this._inputPtr = n6;
            return this._handleInvalidNumberStart(n3, true);
        }
        if (n3 == 48) {
            return this._parseNumber2(true, n4);
        }
        n3 = 1;
        do {
            if (n6 >= n5) {
                return this._parseNumber2(true, n4);
            }
            arrc = this._inputBuffer;
            n2 = n6 + 1;
            if ((n6 = arrc[n6]) < 48 || n6 > 57) {
                if (n6 != 46 && n6 != 101 && n6 != 69) break;
                this._inputPtr = n2;
                return this._parseFloat(n6, n4, n2, true, n3);
            }
            ++n3;
            n6 = n2;
        } while (true);
        this._inputPtr = --n2;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(n6);
        }
        this._textBuffer.resetWithShared(this._inputBuffer, n4, n2 - n4);
        return this.resetInt(true, n3);
    }

    protected final JsonToken _parsePosNumber(int n2) throws IOException {
        int n3;
        int n4 = this._inputPtr;
        int n5 = n4 - 1;
        int n6 = this._inputEnd;
        if (n2 == 48) {
            return this._parseNumber2(false, n5);
        }
        n2 = 1;
        do {
            if (n4 >= n6) {
                this._inputPtr = n5;
                return this._parseNumber2(false, n5);
            }
            char[] arrc = this._inputBuffer;
            n3 = n4 + 1;
            if ((n4 = arrc[n4]) < 48 || n4 > 57) {
                if (n4 != 46 && n4 != 101 && n4 != 69) break;
                this._inputPtr = n3;
                return this._parseFloat(n4, n5, n3, false, n2);
            }
            ++n2;
            n4 = n3;
        } while (true);
        this._inputPtr = --n3;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(n4);
        }
        this._textBuffer.resetWithShared(this._inputBuffer, n5, n3 - n5);
        return this.resetInt(false, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int _readBinary(Base64Variant base64Variant, OutputStream outputStream, byte[] arrby) throws IOException {
        int n2;
        int n3 = 0;
        int n4 = arrby.length;
        int n5 = 0;
        do {
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            char c2 = arrc[n2];
            if (c2 <= ' ') continue;
            int n6 = n2 = base64Variant.decodeBase64Char(c2);
            if (n2 < 0) {
                if (c2 == '\"') {
                    n2 = n5;
                    break;
                }
                n6 = this._decodeBase64Escape(base64Variant, c2, 0);
                if (n6 < 0) continue;
            }
            n2 = n5;
            int n7 = n3;
            if (n3 > n4 - 3) {
                n2 = n5 + n3;
                outputStream.write(arrby, 0, n3);
                n7 = 0;
            }
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            c2 = arrc[n3];
            n3 = n5 = base64Variant.decodeBase64Char(c2);
            if (n5 < 0) {
                n3 = this._decodeBase64Escape(base64Variant, c2, 1);
            }
            int n8 = n6 << 6 | n3;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            c2 = arrc[n3];
            n6 = n5 = base64Variant.decodeBase64Char(c2);
            if (n5 < 0) {
                n3 = n5;
                if (n5 != -2) {
                    if (c2 == '\"' && !base64Variant.usesPadding()) {
                        arrby[n7] = (byte)(n8 >> 4);
                        n3 = n7 + 1;
                        break;
                    }
                    n3 = this._decodeBase64Escape(base64Variant, c2, 2);
                }
                n6 = n3;
                if (n3 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this.loadMoreGuaranteed();
                    }
                    arrc = this._inputBuffer;
                    n3 = this._inputPtr;
                    this._inputPtr = n3 + 1;
                    c2 = arrc[n3];
                    if (!base64Variant.usesPaddingChar(c2)) {
                        throw this.reportInvalidBase64Char(base64Variant, c2, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                    }
                    arrby[n7] = (byte)(n8 >> 4);
                    n3 = n7 + 1;
                    n5 = n2;
                    continue;
                }
            }
            n8 = n8 << 6 | n6;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            c2 = arrc[n3];
            n6 = n5 = base64Variant.decodeBase64Char(c2);
            if (n5 < 0) {
                n3 = n5;
                if (n5 != -2) {
                    if (c2 == '\"' && !base64Variant.usesPadding()) {
                        n5 = n8 >> 2;
                        n6 = n7 + 1;
                        arrby[n7] = (byte)(n5 >> 8);
                        n3 = n6 + 1;
                        arrby[n6] = (byte)n5;
                        break;
                    }
                    n3 = this._decodeBase64Escape(base64Variant, c2, 3);
                }
                n6 = n3;
                if (n3 == -2) {
                    n5 = n8 >> 2;
                    n6 = n7 + 1;
                    arrby[n7] = (byte)(n5 >> 8);
                    n3 = n6 + 1;
                    arrby[n6] = (byte)n5;
                    n5 = n2;
                    continue;
                }
            }
            n3 = n8 << 6 | n6;
            n5 = n7 + 1;
            arrby[n7] = (byte)(n3 >> 16);
            n7 = n5 + 1;
            arrby[n5] = (byte)(n3 >> 8);
            arrby[n7] = (byte)n3;
            n3 = n7 + 1;
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
        char[] arrc;
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable && (arrc = this._inputBuffer) != null) {
            this._inputBuffer = null;
            this._ioContext.releaseTokenBuffer(arrc);
        }
    }

    protected void _reportInvalidToken(String string2) throws IOException {
        this._reportInvalidToken(string2, "'null', 'true', 'false' or NaN");
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _reportInvalidToken(String charSequence, String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder((String)charSequence);
        do {
            char c2;
            if (this._inputPtr >= this._inputEnd && !this.loadMore() || !Character.isJavaIdentifierPart(c2 = this._inputBuffer[this._inputPtr])) {
                void var2_3;
                this._reportError("Unrecognized token '" + stringBuilder.toString() + "': was expecting " + (String)var2_3);
                return;
            }
            ++this._inputPtr;
            stringBuilder.append(c2);
        } while (true);
    }

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this.loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }

    protected final void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int n2 = this._inputPtr;
        int n3 = this._inputEnd;
        char[] arrc = this._inputBuffer;
        do {
            int n4 = n3;
            int n5 = n2;
            if (n2 >= n3) {
                this._inputPtr = n2;
                if (!this.loadMore()) {
                    this._reportInvalidEOF(": was expecting closing quote for a string value");
                }
                n5 = this._inputPtr;
                n4 = this._inputEnd;
            }
            n2 = n5 + 1;
            n3 = arrc[n5];
            if (n3 <= 92) {
                if (n3 == 92) {
                    this._inputPtr = n2;
                    this._decodeEscaped();
                    n2 = this._inputPtr;
                    n3 = this._inputEnd;
                    continue;
                }
                if (n3 <= 34) {
                    if (n3 == 34) {
                        this._inputPtr = n2;
                        return;
                    }
                    if (n3 < 32) {
                        this._inputPtr = n2;
                        this._throwUnquotedSpace(n3, "string value");
                    }
                }
            }
            n3 = n4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
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
    public Object getInputSource() {
        return this._reader;
    }

    protected char getNextChar(String string2) throws IOException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(string2);
        }
        string2 = (String)this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        return (char)string2[n2];
    }

    @Override
    public final String getText() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(jsonToken);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final char[] getTextCharacters() throws IOException {
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
    public final int getTextLength() throws IOException {
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
    public final int getTextOffset() throws IOException {
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
    public final String getValueAsString() throws IOException {
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
    public final String getValueAsString(String string2) throws IOException {
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
    protected boolean loadMore() throws IOException {
        boolean bl2 = false;
        this._currInputProcessed += (long)this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        boolean bl3 = bl2;
        if (this._reader == null) return bl3;
        int n2 = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
        if (n2 > 0) {
            this._inputPtr = 0;
            this._inputEnd = n2;
            return true;
        }
        this._closeInput();
        bl3 = bl2;
        if (n2 != 0) return bl3;
        throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final Boolean nextBooleanValue() throws IOException {
        Boolean bl2;
        Boolean bl3 = null;
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
            bl2 = bl3;
            if (jsonToken != JsonToken.START_OBJECT) return bl2;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return null;
        }
        JsonToken jsonToken = this.nextToken();
        bl2 = bl3;
        if (jsonToken == null) return bl2;
        int n2 = jsonToken.id();
        if (n2 == 9) {
            return Boolean.TRUE;
        }
        bl2 = bl3;
        if (n2 != 10) return bl2;
        return Boolean.FALSE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final int nextIntValue(int n2) throws IOException {
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
    public final long nextLongValue(long l2) throws IOException {
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
    public final String nextTextValue() throws IOException {
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
    public final JsonToken nextToken() throws IOException {
        Object object;
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
            n3 = this._skipComma(n2);
        }
        boolean bl2 = this._parsingContext.inObject();
        n2 = n3;
        if (bl2) {
            object = n3 == 34 ? this._parseName() : this._handleOddName(n3);
            this._parsingContext.setCurrentName((String)object);
            this._currToken = JsonToken.FIELD_NAME;
            n2 = this._skipColon();
        }
        switch (n2) {
            default: {
                object = this._handleOddValue(n2);
                break;
            }
            case 34: {
                this._tokenIncomplete = true;
                object = JsonToken.VALUE_STRING;
                break;
            }
            case 91: {
                if (!bl2) {
                    this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                }
                object = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                if (!bl2) {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                }
                object = JsonToken.START_OBJECT;
                break;
            }
            case 93: 
            case 125: {
                this._reportUnexpectedChar(n2, "expected a value");
            }
            case 116: {
                this._matchTrue();
                object = JsonToken.VALUE_TRUE;
                break;
            }
            case 102: {
                this._matchFalse();
                object = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
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
        if (bl2) {
            this._nextToken = object;
            return this._currToken;
        }
        this._currToken = object;
        return object;
    }

    @Override
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
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
    public int releaseBuffered(Writer writer) throws IOException {
        int n2 = this._inputEnd - this._inputPtr;
        if (n2 < 1) {
            return 0;
        }
        int n3 = this._inputPtr;
        writer.write(this._inputBuffer, n3, n2);
        return n2;
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }
}

