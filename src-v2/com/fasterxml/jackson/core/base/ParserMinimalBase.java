/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

public abstract class ParserMinimalBase
extends JsonParser {
    protected static final int INT_BACKSLASH = 92;
    protected static final int INT_COLON = 58;
    protected static final int INT_COMMA = 44;
    protected static final int INT_CR = 13;
    protected static final int INT_E = 69;
    protected static final int INT_HASH = 35;
    protected static final int INT_LBRACKET = 91;
    protected static final int INT_LCURLY = 123;
    protected static final int INT_LF = 10;
    protected static final int INT_PERIOD = 46;
    protected static final int INT_QUOTE = 34;
    protected static final int INT_RBRACKET = 93;
    protected static final int INT_RCURLY = 125;
    protected static final int INT_SLASH = 47;
    protected static final int INT_SPACE = 32;
    protected static final int INT_TAB = 9;
    protected static final int INT_e = 101;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;

    protected ParserMinimalBase() {
    }

    protected ParserMinimalBase(int n2) {
        super(n2);
    }

    protected static String _ascii(byte[] object) {
        try {
            object = new String((byte[])object, "US-ASCII");
            return object;
        }
        catch (IOException var0_1) {
            throw new RuntimeException(var0_1);
        }
    }

    protected static byte[] _asciiBytes(String string2) {
        byte[] arrby = new byte[string2.length()];
        int n2 = string2.length();
        for (int i2 = 0; i2 < n2; ++i2) {
            arrby[i2] = (byte)string2.charAt(i2);
        }
        return arrby;
    }

    protected static final String _getCharDesc(int n2) {
        char c2 = (char)n2;
        if (Character.isISOControl(c2)) {
            return "(CTRL-CHAR, code " + n2 + ")";
        }
        if (n2 > 255) {
            return "'" + c2 + "' (code " + n2 + " / 0x" + Integer.toHexString(n2) + ")";
        }
        return "'" + c2 + "' (code " + n2 + ")";
    }

    protected final JsonParseException _constructError(String string2, Throwable throwable) {
        return new JsonParseException(string2, this.getCurrentLocation(), throwable);
    }

    protected void _decodeBase64(String string2, ByteArrayBuilder byteArrayBuilder, Base64Variant base64Variant) throws IOException {
        try {
            base64Variant.decode(string2, byteArrayBuilder);
            return;
        }
        catch (IllegalArgumentException var1_2) {
            this._reportError(var1_2.getMessage());
            return;
        }
    }

    protected abstract void _handleEOF() throws JsonParseException;

    /*
     * Enabled aggressive block sorting
     */
    protected char _handleUnrecognizedCharacterEscape(char c2) throws JsonProcessingException {
        if (this.isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER) || c2 == '\'' && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return c2;
        }
        this._reportError("Unrecognized character escape " + ParserMinimalBase._getCharDesc(c2));
        return c2;
    }

    protected boolean _hasTextualNull(String string2) {
        return "null".equals(string2);
    }

    @Deprecated
    protected void _reportBase64EOF() throws JsonParseException {
        throw this._constructError("Unexpected end-of-String in base64 content");
    }

    protected final void _reportError(String string2) throws JsonParseException {
        throw this._constructError(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    protected void _reportInvalidBase64(Base64Variant object, char c2, int n2, String string2) throws JsonParseException {
        object = c2 <= ' ' ? "Illegal white space character (code 0x" + Integer.toHexString(c2) + ") as character #" + (n2 + 1) + " of 4-char base64 unit: can only used between units" : (object.usesPaddingChar(c2) ? "Unexpected padding character ('" + object.getPaddingChar() + "') as character #" + (n2 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character" : (!Character.isDefined(c2) || Character.isISOControl(c2) ? "Illegal character (code 0x" + Integer.toHexString(c2) + ") in base64 content" : "Illegal character '" + c2 + "' (code 0x" + Integer.toHexString(c2) + ") in base64 content"));
        Object object2 = object;
        if (string2 != null) {
            object2 = (String)object + ": " + string2;
        }
        throw this._constructError((String)object2);
    }

    protected void _reportInvalidEOF() throws JsonParseException {
        this._reportInvalidEOF(" in " + (Object)((Object)this._currToken));
    }

    protected void _reportInvalidEOF(String string2) throws JsonParseException {
        this._reportError("Unexpected end-of-input" + string2);
    }

    protected void _reportInvalidEOFInValue() throws JsonParseException {
        this._reportInvalidEOF(" in a value");
    }

    protected void _reportMissingRootWS(int n2) throws JsonParseException {
        this._reportUnexpectedChar(n2, "Expected space separating root-level values");
    }

    protected void _reportUnexpectedChar(int n2, String string2) throws JsonParseException {
        String string3;
        if (n2 < 0) {
            this._reportInvalidEOF();
        }
        String string4 = string3 = "Unexpected character (" + ParserMinimalBase._getCharDesc(n2) + ")";
        if (string2 != null) {
            string4 = string3 + ": " + string2;
        }
        this._reportError(string4);
    }

    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }

    protected void _throwInvalidSpace(int n2) throws JsonParseException {
        n2 = (char)n2;
        this._reportError("Illegal character (" + ParserMinimalBase._getCharDesc(n2) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens");
    }

    protected void _throwUnquotedSpace(int n2, String string2) throws JsonParseException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || n2 > 32) {
            n2 = (char)n2;
            this._reportError("Illegal unquoted character (" + ParserMinimalBase._getCharDesc(n2) + "): has to be escaped using backslash to be included in " + string2);
        }
    }

    protected final void _wrapError(String string2, Throwable throwable) throws JsonParseException {
        throw this._constructError(string2, throwable);
    }

    @Override
    public void clearCurrentToken() {
        if (this._currToken != null) {
            this._lastClearedToken = this._currToken;
            this._currToken = null;
        }
    }

    @Override
    public abstract void close() throws IOException;

    @Override
    public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException;

    @Override
    public abstract String getCurrentName() throws IOException;

    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }

    @Override
    public final int getCurrentTokenId() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == null) {
            return 0;
        }
        return jsonToken.id();
    }

    @Override
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }

    @Override
    public abstract JsonStreamContext getParsingContext();

    @Override
    public abstract String getText() throws IOException;

    @Override
    public abstract char[] getTextCharacters() throws IOException;

    @Override
    public abstract int getTextLength() throws IOException;

    @Override
    public abstract int getTextOffset() throws IOException;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean getValueAsBoolean(boolean var1_1) throws IOException {
        var3_2 = true;
        var4_3 = this._currToken;
        if (var4_3 == null) ** GOTO lbl26
        var2_4 = var3_2;
        switch (var4_3.id()) {
            case 6: {
                var4_3 = this.getText().trim();
                var2_4 = var3_2;
                if ("true".equals(var4_3) != false) return var2_4;
                if ("false".equals(var4_3)) {
                    return false;
                }
                if (this._hasTextualNull((String)var4_3)) {
                    return false;
                }
            }
            default: {
                ** GOTO lbl26
            }
            case 7: {
                var2_4 = var3_2;
                if (this.getIntValue() != 0) return var2_4;
                return false;
            }
            case 10: 
            case 11: {
                return false;
            }
            case 12: {
                var4_3 = this.getEmbeddedObject();
                if (var4_3 instanceof Boolean) {
                    return (Boolean)var4_3;
                }
lbl26: // 4 sources:
                var2_4 = var1_1;
            }
            case 9: 
        }
        return var2_4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public double getValueAsDouble(double d2) throws IOException {
        Object object = this._currToken;
        if (object == null) return d2;
        {
            switch (object.id()) {
                default: {
                    return d2;
                }
                case 6: {
                    object = this.getText();
                    if (!this._hasTextualNull((String)object)) return NumberInput.parseAsDouble((String)object, d2);
                    return 0.0;
                }
                case 7: 
                case 8: {
                    return this.getDoubleValue();
                }
                case 9: {
                    return 1.0;
                }
                case 10: 
                case 11: {
                    return 0.0;
                }
                case 12: {
                    object = this.getEmbeddedObject();
                    if (!(object instanceof Number)) return d2;
                    return ((Number)object).doubleValue();
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int getValueAsInt(int n2) throws IOException {
        Object object = this._currToken;
        if (object == null) return n2;
        {
            switch (object.id()) {
                default: {
                    return n2;
                }
                case 6: {
                    object = this.getText();
                    if (!this._hasTextualNull((String)object)) return NumberInput.parseAsInt((String)object, n2);
                    return 0;
                }
                case 7: 
                case 8: {
                    return this.getIntValue();
                }
                case 9: {
                    return 1;
                }
                case 10: {
                    return 0;
                }
                case 11: {
                    return 0;
                }
                case 12: {
                    object = this.getEmbeddedObject();
                    if (!(object instanceof Number)) return n2;
                    return ((Number)object).intValue();
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long getValueAsLong(long l2) throws IOException {
        Object object = this._currToken;
        if (object == null) return l2;
        {
            switch (object.id()) {
                default: {
                    return l2;
                }
                case 6: {
                    object = this.getText();
                    if (!this._hasTextualNull((String)object)) return NumberInput.parseAsLong((String)object, l2);
                    return 0;
                }
                case 7: 
                case 8: {
                    return this.getLongValue();
                }
                case 9: {
                    return 1;
                }
                case 10: 
                case 11: {
                    return 0;
                }
                case 12: {
                    object = this.getEmbeddedObject();
                    if (!(object instanceof Number)) return l2;
                    return ((Number)object).longValue();
                }
            }
        }
    }

    @Override
    public String getValueAsString(String string2) throws IOException {
        if (!(this._currToken == JsonToken.VALUE_STRING || this._currToken != null && this._currToken != JsonToken.VALUE_NULL && this._currToken.isScalarValue())) {
            return string2;
        }
        return this.getText();
    }

    @Override
    public boolean hasCurrentToken() {
        if (this._currToken != null) {
            return true;
        }
        return false;
    }

    @Override
    public abstract boolean hasTextCharacters();

    @Override
    public abstract boolean isClosed();

    @Override
    public abstract JsonToken nextToken() throws IOException, JsonParseException;

    @Override
    public JsonToken nextValue() throws IOException {
        JsonToken jsonToken;
        JsonToken jsonToken2 = jsonToken = this.nextToken();
        if (jsonToken == JsonToken.FIELD_NAME) {
            jsonToken2 = this.nextToken();
        }
        return jsonToken2;
    }

    @Override
    public abstract void overrideCurrentName(String var1);

    @Override
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int n2 = 1;
        do {
            int n3;
            JsonToken jsonToken;
            if ((jsonToken = this.nextToken()) == null) {
                this._handleEOF();
                return this;
            }
            if (jsonToken.isStructStart()) {
                ++n2;
                continue;
            }
            if (!jsonToken.isStructEnd()) continue;
            n2 = n3 = n2 - 1;
            if (n3 == 0) break;
        } while (true);
        return this;
    }
}

