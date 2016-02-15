/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ParserBase
extends ParserMinimalBase {
    static final BigDecimal BD_MAX_INT;
    static final BigDecimal BD_MAX_LONG;
    static final BigDecimal BD_MIN_INT;
    static final BigDecimal BD_MIN_LONG;
    static final BigInteger BI_MAX_INT;
    static final BigInteger BI_MAX_LONG;
    static final BigInteger BI_MIN_INT;
    static final BigInteger BI_MIN_LONG;
    protected static final char CHAR_NULL = '\u0000';
    protected static final int INT_0 = 48;
    protected static final int INT_9 = 57;
    protected static final int INT_MINUS = 45;
    protected static final int INT_PLUS = 43;
    static final double MAX_INT_D = 2.147483647E9;
    static final long MAX_INT_L = Integer.MAX_VALUE;
    static final double MAX_LONG_D = 9.223372036854776E18;
    static final double MIN_INT_D = -2.147483648E9;
    static final long MIN_INT_L = Integer.MIN_VALUE;
    static final double MIN_LONG_D = -9.223372036854776E18;
    protected static final int NR_BIGDECIMAL = 16;
    protected static final int NR_BIGINT = 4;
    protected static final int NR_DOUBLE = 8;
    protected static final int NR_INT = 1;
    protected static final int NR_LONG = 2;
    protected static final int NR_UNKNOWN = 0;
    protected byte[] _binaryValue;
    protected ByteArrayBuilder _byteArrayBuilder;
    protected boolean _closed;
    protected long _currInputProcessed;
    protected int _currInputRow;
    protected int _currInputRowStart;
    protected int _expLength;
    protected int _fractLength;
    protected int _inputEnd;
    protected int _inputPtr;
    protected int _intLength;
    protected final IOContext _ioContext;
    protected boolean _nameCopied;
    protected char[] _nameCopyBuffer;
    protected JsonToken _nextToken;
    protected int _numTypesValid;
    protected BigDecimal _numberBigDecimal;
    protected BigInteger _numberBigInt;
    protected double _numberDouble;
    protected int _numberInt;
    protected long _numberLong;
    protected boolean _numberNegative;
    protected JsonReadContext _parsingContext;
    protected final TextBuffer _textBuffer;
    protected int _tokenInputCol;
    protected int _tokenInputRow;
    protected long _tokenInputTotal;

    static {
        BI_MIN_INT = BigInteger.valueOf(Integer.MIN_VALUE);
        BI_MAX_INT = BigInteger.valueOf(Integer.MAX_VALUE);
        BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
        BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
        BD_MIN_INT = new BigDecimal(BI_MIN_INT);
        BD_MAX_INT = new BigDecimal(BI_MAX_INT);
    }

    protected ParserBase(IOContext object, int n2) {
        Object var3_3 = null;
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._currInputProcessed = 0;
        this._currInputRow = 1;
        this._currInputRowStart = 0;
        this._tokenInputTotal = 0;
        this._tokenInputRow = 1;
        this._tokenInputCol = 0;
        this._nameCopyBuffer = null;
        this._nameCopied = false;
        this._byteArrayBuilder = null;
        this._numTypesValid = 0;
        this._features = n2;
        this._ioContext = object;
        this._textBuffer = object.constructTextBuffer();
        object = var3_3;
        if (JsonParser.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n2)) {
            object = DupDetector.rootDetector(this);
        }
        this._parsingContext = JsonReadContext.createRootContext((DupDetector)object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void _parseSlowFloat(int var1_1) throws IOException {
        if (var1_1 != 16) ** GOTO lbl6
        try {
            this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
            this._numTypesValid = 16;
            return;
lbl6: // 1 sources:
            this._numberDouble = this._textBuffer.contentsAsDouble();
            this._numTypesValid = 8;
            return;
        }
        catch (NumberFormatException var2_2) {
            this._wrapError("Malformed numeric value '" + this._textBuffer.contentsAsString() + "'", var2_2);
            return;
        }
    }

    private void _parseSlowInt(int n2, char[] arrc, int n3, int n4) throws IOException {
        String string2 = this._textBuffer.contentsAsString();
        try {
            if (NumberInput.inLongRange(arrc, n3, n4, this._numberNegative)) {
                this._numberLong = Long.parseLong(string2);
                this._numTypesValid = 2;
                return;
            }
            this._numberBigInt = new BigInteger(string2);
            this._numTypesValid = 4;
            return;
        }
        catch (NumberFormatException var2_3) {
            this._wrapError("Malformed numeric value '" + string2 + "'", var2_3);
            return;
        }
    }

    protected abstract void _closeInput() throws IOException;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final int _decodeBase64Escape(Base64Variant base64Variant, char c2, int n2) throws IOException {
        if (c2 != '\\') {
            throw this.reportInvalidBase64Char(base64Variant, c2, n2);
        }
        char c3 = this._decodeEscaped();
        if (c3 <= ' ' && n2 == 0) {
            return (char)-1;
        }
        int n3 = base64Variant.decodeBase64Char(c3);
        c2 = (char)n3;
        if (n3 >= 0) return c2;
        throw this.reportInvalidBase64Char(base64Variant, c3, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final int _decodeBase64Escape(Base64Variant base64Variant, int n2, int n3) throws IOException {
        int n4;
        if (n2 != 92) {
            throw this.reportInvalidBase64Char(base64Variant, n2, n3);
        }
        char c2 = this._decodeEscaped();
        if (c2 <= ' ' && n3 == 0) {
            return -1;
        }
        n2 = n4 = base64Variant.decodeBase64Char((int)c2);
        if (n4 >= 0) return n2;
        throw this.reportInvalidBase64Char(base64Variant, c2, n3);
    }

    protected char _decodeEscaped() throws IOException {
        throw new UnsupportedOperationException();
    }

    protected final int _eofAsNextChar() throws JsonParseException {
        this._handleEOF();
        return -1;
    }

    protected abstract void _finishString() throws IOException;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ByteArrayBuilder _getByteArrayBuilder() {
        if (this._byteArrayBuilder == null) {
            this._byteArrayBuilder = new ByteArrayBuilder();
            do {
                return this._byteArrayBuilder;
                break;
            } while (true);
        }
        this._byteArrayBuilder.reset();
        return this._byteArrayBuilder;
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        if (!this._parsingContext.inRoot()) {
            this._reportInvalidEOF(": expected close marker for " + this._parsingContext.getTypeDesc() + " (from " + this._parsingContext.getStartLocation(this._ioContext.getSourceReference()) + ")");
        }
    }

    protected void _parseNumericValue(int n2) throws IOException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            char[] arrc = this._textBuffer.getTextBuffer();
            int n3 = this._textBuffer.getTextOffset();
            int n4 = this._intLength;
            int n5 = n3;
            if (this._numberNegative) {
                n5 = n3 + 1;
            }
            if (n4 <= 9) {
                n2 = n5 = NumberInput.parseInt(arrc, n5, n4);
                if (this._numberNegative) {
                    n2 = - n5;
                }
                this._numberInt = n2;
                this._numTypesValid = 1;
                return;
            }
            if (n4 <= 18) {
                long l2;
                long l3 = l2 = NumberInput.parseLong(arrc, n5, n4);
                if (this._numberNegative) {
                    l3 = - l2;
                }
                if (n4 == 10) {
                    if (this._numberNegative) {
                        if (l3 >= Integer.MIN_VALUE) {
                            this._numberInt = (int)l3;
                            this._numTypesValid = 1;
                            return;
                        }
                    } else if (l3 <= Integer.MAX_VALUE) {
                        this._numberInt = (int)l3;
                        this._numTypesValid = 1;
                        return;
                    }
                }
                this._numberLong = l3;
                this._numTypesValid = 2;
                return;
            }
            this._parseSlowInt(n2, arrc, n5, n4);
            return;
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            this._parseSlowFloat(n2);
            return;
        }
        this._reportError("Current token (" + (Object)((Object)this._currToken) + ") not numeric, can not use numeric value accessors");
    }

    protected void _releaseBuffers() throws IOException {
        this._textBuffer.releaseBuffers();
        char[] arrc = this._nameCopyBuffer;
        if (arrc != null) {
            this._nameCopyBuffer = null;
            this._ioContext.releaseNameCopyBuffer(arrc);
        }
    }

    protected void _reportMismatchedEndMarker(int n2, char c2) throws JsonParseException {
        String string2 = "" + this._parsingContext.getStartLocation(this._ioContext.getSourceReference());
        this._reportError("Unexpected close marker '" + (char)n2 + "': expected '" + c2 + "' (for " + this._parsingContext.getTypeDesc() + " starting at " + string2 + ")");
    }

    @Override
    public void close() throws IOException {
        if (!this._closed) {
            this._closed = true;
            this._closeInput();
        }
        return;
        finally {
            this._releaseBuffers();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToBigDecimal() throws IOException {
        if ((this._numTypesValid & 8) != 0) {
            this._numberBigDecimal = NumberInput.parseBigDecimal(this.getText());
        } else if ((this._numTypesValid & 4) != 0) {
            this._numberBigDecimal = new BigDecimal(this._numberBigInt);
        } else if ((this._numTypesValid & 2) != 0) {
            this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
        } else if ((this._numTypesValid & 1) != 0) {
            this._numberBigDecimal = BigDecimal.valueOf(this._numberInt);
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 16;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToBigInteger() throws IOException {
        if ((this._numTypesValid & 16) != 0) {
            this._numberBigInt = this._numberBigDecimal.toBigInteger();
        } else if ((this._numTypesValid & 2) != 0) {
            this._numberBigInt = BigInteger.valueOf(this._numberLong);
        } else if ((this._numTypesValid & 1) != 0) {
            this._numberBigInt = BigInteger.valueOf(this._numberInt);
        } else if ((this._numTypesValid & 8) != 0) {
            this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 4;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToDouble() throws IOException {
        if ((this._numTypesValid & 16) != 0) {
            this._numberDouble = this._numberBigDecimal.doubleValue();
        } else if ((this._numTypesValid & 4) != 0) {
            this._numberDouble = this._numberBigInt.doubleValue();
        } else if ((this._numTypesValid & 2) != 0) {
            this._numberDouble = this._numberLong;
        } else if ((this._numTypesValid & 1) != 0) {
            this._numberDouble = this._numberInt;
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 8;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToInt() throws IOException {
        if ((this._numTypesValid & 2) != 0) {
            int n2 = (int)this._numberLong;
            if ((long)n2 != this._numberLong) {
                this._reportError("Numeric value (" + this.getText() + ") out of range of int");
            }
            this._numberInt = n2;
        } else if ((this._numTypesValid & 4) != 0) {
            if (BI_MIN_INT.compareTo(this._numberBigInt) > 0 || BI_MAX_INT.compareTo(this._numberBigInt) < 0) {
                this.reportOverflowInt();
            }
            this._numberInt = this._numberBigInt.intValue();
        } else if ((this._numTypesValid & 8) != 0) {
            if (this._numberDouble < -2.147483648E9 || this._numberDouble > 2.147483647E9) {
                this.reportOverflowInt();
            }
            this._numberInt = (int)this._numberDouble;
        } else if ((this._numTypesValid & 16) != 0) {
            if (BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowInt();
            }
            this._numberInt = this._numberBigDecimal.intValue();
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToLong() throws IOException {
        if ((this._numTypesValid & 1) != 0) {
            this._numberLong = this._numberInt;
        } else if ((this._numTypesValid & 4) != 0) {
            if (BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || BI_MAX_LONG.compareTo(this._numberBigInt) < 0) {
                this.reportOverflowLong();
            }
            this._numberLong = this._numberBigInt.longValue();
        } else if ((this._numTypesValid & 8) != 0) {
            if (this._numberDouble < -9.223372036854776E18 || this._numberDouble > 9.223372036854776E18) {
                this.reportOverflowLong();
            }
            this._numberLong = (long)this._numberDouble;
        } else if ((this._numTypesValid & 16) != 0) {
            if (BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowLong();
            }
            this._numberLong = this._numberBigDecimal.longValue();
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 2;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        if ((this._numTypesValid & 4) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(4);
            }
            if ((this._numTypesValid & 4) == 0) {
                this.convertNumberToBigInteger();
            }
        }
        return this._numberBigInt;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        int n2 = this._inputPtr;
        int n3 = this._currInputRowStart;
        return new JsonLocation(this._ioContext.getSourceReference(), -1, this._currInputProcessed + (long)this._inputPtr, this._currInputRow, n2 - n3 + 1);
    }

    @Override
    public String getCurrentName() throws IOException {
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            return this._parsingContext.getParent().getCurrentName();
        }
        return this._parsingContext.getCurrentName();
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException {
        if ((this._numTypesValid & 16) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(16);
            }
            if ((this._numTypesValid & 16) == 0) {
                this.convertNumberToBigDecimal();
            }
        }
        return this._numberBigDecimal;
    }

    @Override
    public double getDoubleValue() throws IOException {
        if ((this._numTypesValid & 8) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(8);
            }
            if ((this._numTypesValid & 8) == 0) {
                this.convertNumberToDouble();
            }
        }
        return this._numberDouble;
    }

    @Override
    public Object getEmbeddedObject() throws IOException {
        return null;
    }

    @Override
    public float getFloatValue() throws IOException {
        return (float)this.getDoubleValue();
    }

    @Override
    public int getIntValue() throws IOException {
        if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(1);
            }
            if ((this._numTypesValid & 1) == 0) {
                this.convertNumberToInt();
            }
        }
        return this._numberInt;
    }

    @Override
    public long getLongValue() throws IOException {
        if ((this._numTypesValid & 2) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(2);
            }
            if ((this._numTypesValid & 2) == 0) {
                this.convertNumberToLong();
            }
        }
        return this._numberLong;
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            if ((this._numTypesValid & 1) != 0) {
                return JsonParser.NumberType.INT;
            }
            if ((this._numTypesValid & 2) != 0) {
                return JsonParser.NumberType.LONG;
            }
            return JsonParser.NumberType.BIG_INTEGER;
        }
        if ((this._numTypesValid & 16) != 0) {
            return JsonParser.NumberType.BIG_DECIMAL;
        }
        return JsonParser.NumberType.DOUBLE;
    }

    @Override
    public Number getNumberValue() throws IOException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            if ((this._numTypesValid & 1) != 0) {
                return this._numberInt;
            }
            if ((this._numTypesValid & 2) != 0) {
                return this._numberLong;
            }
            if ((this._numTypesValid & 4) != 0) {
                return this._numberBigInt;
            }
            return this._numberBigDecimal;
        }
        if ((this._numTypesValid & 16) != 0) {
            return this._numberBigDecimal;
        }
        if ((this._numTypesValid & 8) == 0) {
            this._throwInternal();
        }
        return this._numberDouble;
    }

    @Override
    public JsonReadContext getParsingContext() {
        return this._parsingContext;
    }

    public long getTokenCharacterOffset() {
        return this._tokenInputTotal;
    }

    public int getTokenColumnNr() {
        int n2 = this._tokenInputCol;
        if (n2 < 0) {
            return n2;
        }
        return n2 + 1;
    }

    public int getTokenLineNr() {
        return this._tokenInputRow;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._ioContext.getSourceReference(), -1, this.getTokenCharacterOffset(), this.getTokenLineNr(), this.getTokenColumnNr());
    }

    @Override
    public boolean hasTextCharacters() {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return true;
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nameCopied;
        }
        return false;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    protected abstract boolean loadMore() throws IOException;

    protected final void loadMoreGuaranteed() throws IOException {
        if (!this.loadMore()) {
            this._reportInvalidEOF();
        }
    }

    @Override
    public void overrideCurrentName(String string2) {
        JsonReadContext jsonReadContext;
        block4 : {
            JsonReadContext jsonReadContext2 = this._parsingContext;
            if (this._currToken != JsonToken.START_OBJECT) {
                jsonReadContext = jsonReadContext2;
                if (this._currToken != JsonToken.START_ARRAY) break block4;
            }
            jsonReadContext = jsonReadContext2.getParent();
        }
        try {
            jsonReadContext.setCurrentName(string2);
            return;
        }
        catch (IOException var1_2) {
            throw new IllegalStateException(var1_2);
        }
    }

    protected IllegalArgumentException reportInvalidBase64Char(Base64Variant base64Variant, int n2, int n3) throws IllegalArgumentException {
        return this.reportInvalidBase64Char(base64Variant, n2, n3, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected IllegalArgumentException reportInvalidBase64Char(Base64Variant object, int n2, int n3, String string2) throws IllegalArgumentException {
        object = n2 <= 32 ? "Illegal white space character (code 0x" + Integer.toHexString(n2) + ") as character #" + (n3 + 1) + " of 4-char base64 unit: can only used between units" : (object.usesPaddingChar(n2) ? "Unexpected padding character ('" + object.getPaddingChar() + "') as character #" + (n3 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character" : (!Character.isDefined(n2) || Character.isISOControl(n2) ? "Illegal character (code 0x" + Integer.toHexString(n2) + ") in base64 content" : "Illegal character '" + (char)n2 + "' (code 0x" + Integer.toHexString(n2) + ") in base64 content"));
        Object object2 = object;
        if (string2 != null) {
            object2 = (String)object + ": " + string2;
        }
        return new IllegalArgumentException((String)object2);
    }

    protected void reportInvalidNumber(String string2) throws JsonParseException {
        this._reportError("Invalid numeric value: " + string2);
    }

    protected void reportOverflowInt() throws IOException {
        this._reportError("Numeric value (" + this.getText() + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
    }

    protected void reportOverflowLong() throws IOException {
        this._reportError("Numeric value (" + this.getText() + ") out of range of long (" + Long.MIN_VALUE + " - " + Long.MAX_VALUE + ")");
    }

    protected void reportUnexpectedNumberChar(int n2, String string2) throws JsonParseException {
        String string3;
        String string4 = string3 = "Unexpected character (" + ParserBase._getCharDesc(n2) + ") in numeric value";
        if (string2 != null) {
            string4 = string3 + ": " + string2;
        }
        this._reportError(string4);
    }

    protected final JsonToken reset(boolean bl2, int n2, int n3, int n4) {
        if (n3 < 1 && n4 < 1) {
            return this.resetInt(bl2, n2);
        }
        return this.resetFloat(bl2, n2, n3, n4);
    }

    protected final JsonToken resetAsNaN(String string2, double d2) {
        this._textBuffer.resetWithString(string2);
        this._numberDouble = d2;
        this._numTypesValid = 8;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    protected final JsonToken resetFloat(boolean bl2, int n2, int n3, int n4) {
        this._numberNegative = bl2;
        this._intLength = n2;
        this._fractLength = n3;
        this._expLength = n4;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    protected final JsonToken resetInt(boolean bl2, int n2) {
        this._numberNegative = bl2;
        this._intLength = n2;
        this._fractLength = 0;
        this._expLength = 0;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_INT;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
}

