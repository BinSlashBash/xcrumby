/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public abstract class JsonParser
implements Closeable,
Versioned {
    private static final int MAX_BYTE_I = 255;
    private static final int MAX_SHORT_I = 32767;
    private static final int MIN_BYTE_I = -128;
    private static final int MIN_SHORT_I = -32768;
    protected int _features;

    protected JsonParser() {
    }

    protected JsonParser(int n2) {
        this._features = n2;
    }

    protected ObjectCodec _codec() {
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined for parser, needed for deserialization");
        }
        return objectCodec;
    }

    protected JsonParseException _constructError(String string2) {
        return new JsonParseException(string2, this.getCurrentLocation());
    }

    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Operation not supported by parser of type " + this.getClass().getName());
    }

    public boolean canReadObjectId() {
        return false;
    }

    public boolean canReadTypeId() {
        return false;
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        return false;
    }

    public abstract void clearCurrentToken();

    @Override
    public abstract void close() throws IOException;

    public JsonParser configure(Feature feature, boolean bl2) {
        if (bl2) {
            this.enable(feature);
            return this;
        }
        this.disable(feature);
        return this;
    }

    public JsonParser disable(Feature feature) {
        this._features &= ~ feature.getMask();
        return this;
    }

    public JsonParser enable(Feature feature) {
        this._features |= feature.getMask();
        return this;
    }

    public abstract BigInteger getBigIntegerValue() throws IOException;

    public byte[] getBinaryValue() throws IOException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
    }

    public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException;

    public boolean getBooleanValue() throws IOException {
        JsonToken jsonToken = this.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (jsonToken == JsonToken.VALUE_FALSE) {
            return false;
        }
        throw new JsonParseException("Current token (" + (Object)((Object)jsonToken) + ") not of boolean type", this.getCurrentLocation());
    }

    public byte getByteValue() throws IOException {
        int n2 = this.getIntValue();
        if (n2 < -128 || n2 > 255) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java byte");
        }
        return (byte)n2;
    }

    public abstract ObjectCodec getCodec();

    public abstract JsonLocation getCurrentLocation();

    public abstract String getCurrentName() throws IOException;

    public abstract JsonToken getCurrentToken();

    public abstract int getCurrentTokenId();

    public abstract BigDecimal getDecimalValue() throws IOException;

    public abstract double getDoubleValue() throws IOException;

    public abstract Object getEmbeddedObject() throws IOException;

    public int getFeatureMask() {
        return this._features;
    }

    public abstract float getFloatValue() throws IOException;

    public Object getInputSource() {
        return null;
    }

    public abstract int getIntValue() throws IOException;

    public abstract JsonToken getLastClearedToken();

    public abstract long getLongValue() throws IOException;

    public abstract NumberType getNumberType() throws IOException;

    public abstract Number getNumberValue() throws IOException;

    public Object getObjectId() throws IOException {
        return null;
    }

    public abstract JsonStreamContext getParsingContext();

    public FormatSchema getSchema() {
        return null;
    }

    public short getShortValue() throws IOException {
        int n2 = this.getIntValue();
        if (n2 < -32768 || n2 > 32767) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java short");
        }
        return (short)n2;
    }

    public abstract String getText() throws IOException;

    public abstract char[] getTextCharacters() throws IOException;

    public abstract int getTextLength() throws IOException;

    public abstract int getTextOffset() throws IOException;

    public abstract JsonLocation getTokenLocation();

    public Object getTypeId() throws IOException {
        return null;
    }

    public boolean getValueAsBoolean() throws IOException {
        return this.getValueAsBoolean(false);
    }

    public boolean getValueAsBoolean(boolean bl2) throws IOException {
        return bl2;
    }

    public double getValueAsDouble() throws IOException {
        return this.getValueAsDouble(0.0);
    }

    public double getValueAsDouble(double d2) throws IOException {
        return d2;
    }

    public int getValueAsInt() throws IOException {
        return this.getValueAsInt(0);
    }

    public int getValueAsInt(int n2) throws IOException {
        return n2;
    }

    public long getValueAsLong() throws IOException {
        return this.getValueAsLong(0);
    }

    public long getValueAsLong(long l2) throws IOException {
        return l2;
    }

    public String getValueAsString() throws IOException {
        return this.getValueAsString(null);
    }

    public abstract String getValueAsString(String var1) throws IOException;

    public abstract boolean hasCurrentToken();

    public abstract boolean hasTextCharacters();

    public abstract boolean isClosed();

    public boolean isEnabled(Feature feature) {
        if ((this._features & feature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    public boolean isExpectedStartArrayToken() {
        if (this.getCurrentToken() == JsonToken.START_ARRAY) {
            return true;
        }
        return false;
    }

    public Boolean nextBooleanValue() throws IOException, JsonParseException {
        JsonToken jsonToken = this.nextToken();
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (jsonToken == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        return null;
    }

    public boolean nextFieldName(SerializableString serializableString) throws IOException, JsonParseException {
        if (this.nextToken() == JsonToken.FIELD_NAME && serializableString.getValue().equals(this.getCurrentName())) {
            return true;
        }
        return false;
    }

    public int nextIntValue(int n2) throws IOException, JsonParseException {
        if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            n2 = this.getIntValue();
        }
        return n2;
    }

    public long nextLongValue(long l2) throws IOException, JsonParseException {
        if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            l2 = this.getLongValue();
        }
        return l2;
    }

    public String nextTextValue() throws IOException, JsonParseException {
        if (this.nextToken() == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        return null;
    }

    public abstract JsonToken nextToken() throws IOException, JsonParseException;

    public abstract JsonToken nextValue() throws IOException, JsonParseException;

    public abstract void overrideCurrentName(String var1);

    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }

    public int readBinaryValue(OutputStream outputStream) throws IOException {
        return this.readBinaryValue(Base64Variants.getDefaultVariant(), outputStream);
    }

    public <T> T readValueAs(TypeReference<?> typeReference) throws IOException {
        return this._codec().readValue(this, typeReference);
    }

    public <T> T readValueAs(Class<T> class_) throws IOException {
        return this._codec().readValue(this, class_);
    }

    public <T extends TreeNode> T readValueAsTree() throws IOException {
        return this._codec().readTree(this);
    }

    public <T> Iterator<T> readValuesAs(TypeReference<?> typeReference) throws IOException {
        return this._codec().readValues(this, typeReference);
    }

    public <T> Iterator<T> readValuesAs(Class<T> class_) throws IOException {
        return this._codec().readValues(this, class_);
    }

    public int releaseBuffered(OutputStream outputStream) throws IOException {
        return -1;
    }

    public int releaseBuffered(Writer writer) throws IOException {
        return -1;
    }

    public boolean requiresCustomCodec() {
        return false;
    }

    public abstract void setCodec(ObjectCodec var1);

    public JsonParser setFeatureMask(int n2) {
        this._features = n2;
        return this;
    }

    public void setSchema(FormatSchema formatSchema) {
        throw new UnsupportedOperationException("Parser of type " + this.getClass().getName() + " does not support schema of type '" + formatSchema.getSchemaType() + "'");
    }

    public abstract JsonParser skipChildren() throws IOException, JsonParseException;

    @Override
    public abstract Version version();

    public static enum Feature {
        AUTO_CLOSE_SOURCE(true),
        ALLOW_COMMENTS(false),
        ALLOW_YAML_COMMENTS(false),
        ALLOW_UNQUOTED_FIELD_NAMES(false),
        ALLOW_SINGLE_QUOTES(false),
        ALLOW_UNQUOTED_CONTROL_CHARS(false),
        ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false),
        ALLOW_NUMERIC_LEADING_ZEROS(false),
        ALLOW_NON_NUMERIC_NUMBERS(false),
        STRICT_DUPLICATE_DETECTION(false);
        
        private final boolean _defaultState;
        private final int _mask;

        private Feature(boolean bl2) {
            this._mask = 1 << this.ordinal();
            this._defaultState = bl2;
        }

        public static int collectDefaults() {
            int n2 = 0;
            for (Feature feature : Feature.values()) {
                int n3 = n2;
                if (feature.enabledByDefault()) {
                    n3 = n2 | feature.getMask();
                }
                n2 = n3;
            }
            return n2;
        }

        public boolean enabledByDefault() {
            return this._defaultState;
        }

        public boolean enabledIn(int n2) {
            if ((this._mask & n2) != 0) {
                return true;
            }
            return false;
        }

        public int getMask() {
            return 1 << this.ordinal();
        }
    }

    public static enum NumberType {
        INT,
        LONG,
        BIG_INTEGER,
        FLOAT,
        DOUBLE,
        BIG_DECIMAL;
        

        private NumberType() {
        }
    }

}

