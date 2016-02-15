/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class JsonGenerator
implements Closeable,
Flushable,
Versioned {
    protected PrettyPrinter _cfgPrettyPrinter;

    protected JsonGenerator() {
    }

    protected void _reportError(String string2) throws JsonGenerationException {
        throw new JsonGenerationException(string2);
    }

    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Operation not supported by generator of type " + this.getClass().getName());
    }

    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }

    protected void _writeSimpleObject(Object object) throws IOException {
        if (object == null) {
            this.writeNull();
            return;
        }
        if (object instanceof String) {
            this.writeString((String)object);
            return;
        }
        if (object instanceof Number) {
            Number number = (Number)object;
            if (number instanceof Integer) {
                this.writeNumber(number.intValue());
                return;
            }
            if (number instanceof Long) {
                this.writeNumber(number.longValue());
                return;
            }
            if (number instanceof Double) {
                this.writeNumber(number.doubleValue());
                return;
            }
            if (number instanceof Float) {
                this.writeNumber(number.floatValue());
                return;
            }
            if (number instanceof Short) {
                this.writeNumber(number.shortValue());
                return;
            }
            if (number instanceof Byte) {
                this.writeNumber(number.byteValue());
                return;
            }
            if (number instanceof BigInteger) {
                this.writeNumber((BigInteger)number);
                return;
            }
            if (number instanceof BigDecimal) {
                this.writeNumber((BigDecimal)number);
                return;
            }
            if (number instanceof AtomicInteger) {
                this.writeNumber(((AtomicInteger)number).get());
                return;
            }
            if (number instanceof AtomicLong) {
                this.writeNumber(((AtomicLong)number).get());
                return;
            }
        } else {
            if (object instanceof byte[]) {
                this.writeBinary((byte[])object);
                return;
            }
            if (object instanceof Boolean) {
                this.writeBoolean((Boolean)object);
                return;
            }
            if (object instanceof AtomicBoolean) {
                this.writeBoolean(((AtomicBoolean)object).get());
                return;
            }
        }
        throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + object.getClass().getName() + ")");
    }

    public boolean canOmitFields() {
        return true;
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        return false;
    }

    public boolean canWriteBinaryNatively() {
        return false;
    }

    public boolean canWriteObjectId() {
        return false;
    }

    public boolean canWriteTypeId() {
        return false;
    }

    @Override
    public abstract void close() throws IOException;

    public final JsonGenerator configure(Feature feature, boolean bl2) {
        if (bl2) {
            this.enable(feature);
            return this;
        }
        this.disable(feature);
        return this;
    }

    public void copyCurrentEvent(JsonParser jsonParser) throws IOException {
        Enum enum_ = jsonParser.getCurrentToken();
        if (enum_ == null) {
            this._reportError("No current event to copy");
        }
        switch (enum_.id()) {
            default: {
                this._throwInternal();
                return;
            }
            case -1: {
                this._reportError("No current event to copy");
            }
            case 1: {
                this.writeStartObject();
                return;
            }
            case 2: {
                this.writeEndObject();
                return;
            }
            case 3: {
                this.writeStartArray();
                return;
            }
            case 4: {
                this.writeEndArray();
                return;
            }
            case 5: {
                this.writeFieldName(jsonParser.getCurrentName());
                return;
            }
            case 6: {
                if (jsonParser.hasTextCharacters()) {
                    this.writeString(jsonParser.getTextCharacters(), jsonParser.getTextOffset(), jsonParser.getTextLength());
                    return;
                }
                this.writeString(jsonParser.getText());
                return;
            }
            case 7: {
                enum_ = jsonParser.getNumberType();
                if (enum_ == JsonParser.NumberType.INT) {
                    this.writeNumber(jsonParser.getIntValue());
                    return;
                }
                if (enum_ == JsonParser.NumberType.BIG_INTEGER) {
                    this.writeNumber(jsonParser.getBigIntegerValue());
                    return;
                }
                this.writeNumber(jsonParser.getLongValue());
                return;
            }
            case 8: {
                enum_ = jsonParser.getNumberType();
                if (enum_ == JsonParser.NumberType.BIG_DECIMAL) {
                    this.writeNumber(jsonParser.getDecimalValue());
                    return;
                }
                if (enum_ == JsonParser.NumberType.FLOAT) {
                    this.writeNumber(jsonParser.getFloatValue());
                    return;
                }
                this.writeNumber(jsonParser.getDoubleValue());
                return;
            }
            case 9: {
                this.writeBoolean(true);
                return;
            }
            case 10: {
                this.writeBoolean(false);
                return;
            }
            case 11: {
                this.writeNull();
                return;
            }
            case 12: 
        }
        this.writeObject(jsonParser.getEmbeddedObject());
    }

    public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
        int n2;
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == null) {
            this._reportError("No current event to copy");
        }
        int n3 = n2 = jsonToken.id();
        if (n2 == 5) {
            this.writeFieldName(jsonParser.getCurrentName());
            n3 = jsonParser.nextToken().id();
        }
        switch (n3) {
            default: {
                this.copyCurrentEvent(jsonParser);
                return;
            }
            case 1: {
                this.writeStartObject();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    this.copyCurrentStructure(jsonParser);
                }
                this.writeEndObject();
                return;
            }
            case 3: 
        }
        this.writeStartArray();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            this.copyCurrentStructure(jsonParser);
        }
        this.writeEndArray();
    }

    public abstract JsonGenerator disable(Feature var1);

    public abstract JsonGenerator enable(Feature var1);

    @Override
    public abstract void flush() throws IOException;

    public CharacterEscapes getCharacterEscapes() {
        return null;
    }

    public abstract ObjectCodec getCodec();

    public abstract int getFeatureMask();

    public int getHighestEscapedChar() {
        return 0;
    }

    public abstract JsonStreamContext getOutputContext();

    public Object getOutputTarget() {
        return null;
    }

    public PrettyPrinter getPrettyPrinter() {
        return this._cfgPrettyPrinter;
    }

    public FormatSchema getSchema() {
        return null;
    }

    public abstract boolean isClosed();

    public abstract boolean isEnabled(Feature var1);

    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        return this;
    }

    public abstract JsonGenerator setCodec(ObjectCodec var1);

    public abstract JsonGenerator setFeatureMask(int var1);

    public JsonGenerator setHighestNonEscapedChar(int n2) {
        return this;
    }

    public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
        this._cfgPrettyPrinter = prettyPrinter;
        return this;
    }

    public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
        throw new UnsupportedOperationException();
    }

    public void setSchema(FormatSchema formatSchema) {
        throw new UnsupportedOperationException("Generator of type " + this.getClass().getName() + " does not support schema of type '" + formatSchema.getSchemaType() + "'");
    }

    public abstract JsonGenerator useDefaultPrettyPrinter();

    @Override
    public abstract Version version();

    public final void writeArrayFieldStart(String string2) throws IOException {
        this.writeFieldName(string2);
        this.writeStartArray();
    }

    public abstract int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException;

    public int writeBinary(InputStream inputStream, int n2) throws IOException {
        return this.writeBinary(Base64Variants.getDefaultVariant(), inputStream, n2);
    }

    public abstract void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException;

    public void writeBinary(byte[] arrby) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), arrby, 0, arrby.length);
    }

    public void writeBinary(byte[] arrby, int n2, int n3) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), arrby, n2, n3);
    }

    public final void writeBinaryField(String string2, byte[] arrby) throws IOException {
        this.writeFieldName(string2);
        this.writeBinary(arrby);
    }

    public abstract void writeBoolean(boolean var1) throws IOException;

    public final void writeBooleanField(String string2, boolean bl2) throws IOException {
        this.writeFieldName(string2);
        this.writeBoolean(bl2);
    }

    public abstract void writeEndArray() throws IOException;

    public abstract void writeEndObject() throws IOException;

    public abstract void writeFieldName(SerializableString var1) throws IOException;

    public abstract void writeFieldName(String var1) throws IOException;

    public abstract void writeNull() throws IOException;

    public final void writeNullField(String string2) throws IOException {
        this.writeFieldName(string2);
        this.writeNull();
    }

    public abstract void writeNumber(double var1) throws IOException;

    public abstract void writeNumber(float var1) throws IOException;

    public abstract void writeNumber(int var1) throws IOException;

    public abstract void writeNumber(long var1) throws IOException;

    public abstract void writeNumber(String var1) throws IOException;

    public abstract void writeNumber(BigDecimal var1) throws IOException;

    public abstract void writeNumber(BigInteger var1) throws IOException;

    public void writeNumber(short s2) throws IOException {
        this.writeNumber((int)s2);
    }

    public final void writeNumberField(String string2, double d2) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(d2);
    }

    public final void writeNumberField(String string2, float f2) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(f2);
    }

    public final void writeNumberField(String string2, int n2) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(n2);
    }

    public final void writeNumberField(String string2, long l2) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(l2);
    }

    public final void writeNumberField(String string2, BigDecimal bigDecimal) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(bigDecimal);
    }

    public abstract void writeObject(Object var1) throws IOException;

    public final void writeObjectField(String string2, Object object) throws IOException {
        this.writeFieldName(string2);
        this.writeObject(object);
    }

    public final void writeObjectFieldStart(String string2) throws IOException {
        this.writeFieldName(string2);
        this.writeStartObject();
    }

    public void writeObjectId(Object object) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids");
    }

    public void writeObjectRef(Object object) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids");
    }

    public void writeOmittedField(String string2) throws IOException {
    }

    public abstract void writeRaw(char var1) throws IOException;

    public void writeRaw(SerializableString serializableString) throws IOException {
        this.writeRaw(serializableString.getValue());
    }

    public abstract void writeRaw(String var1) throws IOException;

    public abstract void writeRaw(String var1, int var2, int var3) throws IOException;

    public abstract void writeRaw(char[] var1, int var2, int var3) throws IOException;

    public abstract void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException;

    public abstract void writeRawValue(String var1) throws IOException;

    public abstract void writeRawValue(String var1, int var2, int var3) throws IOException;

    public abstract void writeRawValue(char[] var1, int var2, int var3) throws IOException;

    public abstract void writeStartArray() throws IOException;

    public void writeStartArray(int n2) throws IOException {
        this.writeStartArray();
    }

    public abstract void writeStartObject() throws IOException;

    public abstract void writeString(SerializableString var1) throws IOException;

    public abstract void writeString(String var1) throws IOException;

    public abstract void writeString(char[] var1, int var2, int var3) throws IOException;

    public void writeStringField(String string2, String string3) throws IOException {
        this.writeFieldName(string2);
        this.writeString(string3);
    }

    public abstract void writeTree(TreeNode var1) throws IOException;

    public void writeTypeId(Object object) throws IOException {
        throw new JsonGenerationException("No native support for writing Type Ids");
    }

    public abstract void writeUTF8String(byte[] var1, int var2, int var3) throws IOException;

    public static enum Feature {
        AUTO_CLOSE_TARGET(true),
        AUTO_CLOSE_JSON_CONTENT(true),
        QUOTE_FIELD_NAMES(true),
        QUOTE_NON_NUMERIC_NUMBERS(true),
        WRITE_NUMBERS_AS_STRINGS(false),
        WRITE_BIGDECIMAL_AS_PLAIN(false),
        FLUSH_PASSED_TO_STREAM(true),
        ESCAPE_NON_ASCII(false),
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
            return this._mask;
        }
    }

}

