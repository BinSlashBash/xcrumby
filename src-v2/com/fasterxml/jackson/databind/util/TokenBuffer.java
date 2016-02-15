/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.TreeMap;

public class TokenBuffer
extends JsonGenerator {
    protected static final int DEFAULT_GENERATOR_FEATURES = JsonGenerator.Feature.collectDefaults();
    protected int _appendAt;
    protected boolean _closed;
    protected Segment _first;
    protected int _generatorFeatures;
    protected boolean _hasNativeId = false;
    protected boolean _hasNativeObjectIds;
    protected boolean _hasNativeTypeIds;
    protected Segment _last;
    protected boolean _mayHaveNativeIds;
    protected ObjectCodec _objectCodec;
    protected Object _objectId;
    protected Object _typeId;
    protected JsonWriteContext _writeContext;

    public TokenBuffer(JsonParser jsonParser) {
        Segment segment;
        this._objectCodec = jsonParser.getCodec();
        this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
        this._writeContext = JsonWriteContext.createRootContext(null);
        this._last = segment = new Segment();
        this._first = segment;
        this._appendAt = 0;
        this._hasNativeTypeIds = jsonParser.canReadTypeId();
        this._hasNativeObjectIds = jsonParser.canReadObjectId();
        this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
    }

    @Deprecated
    public TokenBuffer(ObjectCodec objectCodec) {
        this(objectCodec, false);
    }

    public TokenBuffer(ObjectCodec object, boolean bl2) {
        this._objectCodec = object;
        this._generatorFeatures = DEFAULT_GENERATOR_FEATURES;
        this._writeContext = JsonWriteContext.createRootContext(null);
        this._last = object = new Segment();
        this._first = object;
        this._appendAt = 0;
        this._hasNativeTypeIds = bl2;
        this._hasNativeObjectIds = bl2;
        this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
    }

    private final void _appendNativeIds(StringBuilder stringBuilder) {
        Object object = this._last.findObjectId(this._appendAt - 1);
        if (object != null) {
            stringBuilder.append("[objectId=").append(String.valueOf(object)).append(']');
        }
        if ((object = this._last.findTypeId(this._appendAt - 1)) != null) {
            stringBuilder.append("[typeId=").append(String.valueOf(object)).append(']');
        }
    }

    private final void _checkNativeIds(JsonParser object) throws IOException, JsonProcessingException {
        Object object2;
        this._typeId = object2 = object.getTypeId();
        if (object2 != null) {
            this._hasNativeId = true;
        }
        this._objectId = object = object.getObjectId();
        if (object != null) {
            this._hasNativeId = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _append(JsonToken object) {
        object = this._hasNativeId ? this._last.append(this._appendAt, (JsonToken)((Object)object), this._objectId, this._typeId) : this._last.append(this._appendAt, (JsonToken)((Object)object));
        if (object == null) {
            ++this._appendAt;
            return;
        }
        this._last = object;
        this._appendAt = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _append(JsonToken object, Object object2) {
        object = this._hasNativeId ? this._last.append(this._appendAt, (JsonToken)((Object)object), object2, this._objectId, this._typeId) : this._last.append(this._appendAt, (JsonToken)((Object)object), object2);
        if (object == null) {
            ++this._appendAt;
            return;
        }
        this._last = object;
        this._appendAt = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _appendRaw(int n2, Object object) {
        object = this._hasNativeId ? this._last.appendRaw(this._appendAt, n2, object, this._objectId, this._typeId) : this._last.appendRaw(this._appendAt, n2, object);
        if (object == null) {
            ++this._appendAt;
            return;
        }
        this._last = object;
        this._appendAt = 1;
    }

    @Override
    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Called operation not supported for TokenBuffer");
    }

    public TokenBuffer append(TokenBuffer closeable) throws IOException, JsonGenerationException {
        if (!this._hasNativeTypeIds) {
            this._hasNativeTypeIds = closeable.canWriteTypeId();
        }
        if (!this._hasNativeObjectIds) {
            this._hasNativeObjectIds = closeable.canWriteObjectId();
        }
        this._mayHaveNativeIds = this._hasNativeTypeIds | this._hasNativeObjectIds;
        closeable = closeable.asParser();
        while (closeable.nextToken() != null) {
            this.copyCurrentStructure((JsonParser)closeable);
        }
        return this;
    }

    public JsonParser asParser() {
        return this.asParser(this._objectCodec);
    }

    public JsonParser asParser(JsonParser jsonParser) {
        Parser parser = new Parser(this._first, jsonParser.getCodec(), this._hasNativeTypeIds, this._hasNativeObjectIds);
        parser.setLocation(jsonParser.getTokenLocation());
        return parser;
    }

    public JsonParser asParser(ObjectCodec objectCodec) {
        return new Parser(this._first, objectCodec, this._hasNativeTypeIds, this._hasNativeObjectIds);
    }

    @Override
    public boolean canWriteBinaryNatively() {
        return true;
    }

    @Override
    public boolean canWriteObjectId() {
        return this._hasNativeObjectIds;
    }

    @Override
    public boolean canWriteTypeId() {
        return this._hasNativeTypeIds;
    }

    @Override
    public void close() throws IOException {
        this._closed = true;
    }

    @Override
    public void copyCurrentEvent(JsonParser jsonParser) throws IOException, JsonProcessingException {
        if (this._mayHaveNativeIds) {
            this._checkNativeIds(jsonParser);
        }
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
            default: {
                throw new RuntimeException("Internal error: should never end up through this code path");
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
                switch (.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[jsonParser.getNumberType().ordinal()]) {
                    default: {
                        this.writeNumber(jsonParser.getLongValue());
                        return;
                    }
                    case 1: {
                        this.writeNumber(jsonParser.getIntValue());
                        return;
                    }
                    case 2: 
                }
                this.writeNumber(jsonParser.getBigIntegerValue());
                return;
            }
            case 8: {
                switch (.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[jsonParser.getNumberType().ordinal()]) {
                    default: {
                        this.writeNumber(jsonParser.getDoubleValue());
                        return;
                    }
                    case 3: {
                        this.writeNumber(jsonParser.getDecimalValue());
                        return;
                    }
                    case 4: 
                }
                this.writeNumber(jsonParser.getFloatValue());
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

    @Override
    public void copyCurrentStructure(JsonParser jsonParser) throws IOException, JsonProcessingException {
        JsonToken jsonToken;
        JsonToken jsonToken2 = jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.FIELD_NAME) {
            if (this._mayHaveNativeIds) {
                this._checkNativeIds(jsonParser);
            }
            this.writeFieldName(jsonParser.getCurrentName());
            jsonToken2 = jsonParser.nextToken();
        }
        if (this._mayHaveNativeIds) {
            this._checkNativeIds(jsonParser);
        }
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonToken2.ordinal()]) {
            default: {
                this.copyCurrentEvent(jsonParser);
                return;
            }
            case 3: {
                this.writeStartArray();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    this.copyCurrentStructure(jsonParser);
                }
                this.writeEndArray();
                return;
            }
            case 1: 
        }
        this.writeStartObject();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            this.copyCurrentStructure(jsonParser);
        }
        this.writeEndObject();
    }

    public TokenBuffer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        this.copyCurrentStructure(jsonParser);
        return this;
    }

    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        this._generatorFeatures &= ~ feature.getMask();
        return this;
    }

    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        this._generatorFeatures |= feature.getMask();
        return this;
    }

    public JsonToken firstToken() {
        if (this._first != null) {
            return this._first.type(0);
        }
        return null;
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public int getFeatureMask() {
        return this._generatorFeatures;
    }

    @Override
    public final JsonWriteContext getOutputContext() {
        return this._writeContext;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    @Override
    public boolean isEnabled(JsonGenerator.Feature feature) {
        if ((this._generatorFeatures & feature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void serialize(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        Object object = this._first;
        int n2 = -1;
        boolean bl2 = this._mayHaveNativeIds;
        boolean bl3 = bl2 && object.hasIds();
        block14 : do {
            int n3;
            n2 = n3 = n2 + 1;
            Segment segment = object;
            if (n3 >= 16) {
                n2 = 0;
                segment = object.next();
                if (segment == null) {
                    return;
                }
                bl3 = bl2 && segment.hasIds();
            }
            if ((object = segment.type(n2)) == null) return;
            if (bl3) {
                Object object2 = segment.findObjectId(n2);
                if (object2 != null) {
                    jsonGenerator.writeObjectId(object2);
                }
                if ((object2 = segment.findTypeId(n2)) != null) {
                    jsonGenerator.writeTypeId(object2);
                }
            }
            switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[object.ordinal()]) {
                default: {
                    throw new RuntimeException("Internal error: should never end up through this code path");
                }
                case 1: {
                    jsonGenerator.writeStartObject();
                    object = segment;
                    continue block14;
                }
                case 2: {
                    jsonGenerator.writeEndObject();
                    object = segment;
                    continue block14;
                }
                case 3: {
                    jsonGenerator.writeStartArray();
                    object = segment;
                    continue block14;
                }
                case 4: {
                    jsonGenerator.writeEndArray();
                    object = segment;
                    continue block14;
                }
                case 5: {
                    object = segment.get(n2);
                    if (object instanceof SerializableString) {
                        jsonGenerator.writeFieldName((SerializableString)object);
                        object = segment;
                        continue block14;
                    }
                    jsonGenerator.writeFieldName((String)object);
                    object = segment;
                    continue block14;
                }
                case 6: {
                    object = segment.get(n2);
                    if (object instanceof SerializableString) {
                        jsonGenerator.writeString((SerializableString)object);
                        object = segment;
                        continue block14;
                    }
                    jsonGenerator.writeString((String)object);
                    object = segment;
                    continue block14;
                }
                case 7: {
                    object = segment.get(n2);
                    if (object instanceof Integer) {
                        jsonGenerator.writeNumber((Integer)object);
                        object = segment;
                        continue block14;
                    }
                    if (object instanceof BigInteger) {
                        jsonGenerator.writeNumber((BigInteger)object);
                        object = segment;
                        continue block14;
                    }
                    if (object instanceof Long) {
                        jsonGenerator.writeNumber((Long)object);
                        object = segment;
                        continue block14;
                    }
                    if (object instanceof Short) {
                        jsonGenerator.writeNumber((Short)object);
                        object = segment;
                        continue block14;
                    }
                    jsonGenerator.writeNumber(((Number)object).intValue());
                    object = segment;
                    continue block14;
                }
                case 8: {
                    object = segment.get(n2);
                    if (object instanceof Double) {
                        jsonGenerator.writeNumber((Double)object);
                        object = segment;
                        continue block14;
                    }
                    if (object instanceof BigDecimal) {
                        jsonGenerator.writeNumber((BigDecimal)object);
                        object = segment;
                        continue block14;
                    }
                    if (object instanceof Float) {
                        jsonGenerator.writeNumber(((Float)object).floatValue());
                        object = segment;
                        continue block14;
                    }
                    if (object == null) {
                        jsonGenerator.writeNull();
                        object = segment;
                        continue block14;
                    }
                    if (!(object instanceof String)) {
                        throw new JsonGenerationException("Unrecognized value type for VALUE_NUMBER_FLOAT: " + object.getClass().getName() + ", can not serialize");
                    }
                    jsonGenerator.writeNumber((String)object);
                    object = segment;
                    continue block14;
                }
                case 9: {
                    jsonGenerator.writeBoolean(true);
                    object = segment;
                    continue block14;
                }
                case 10: {
                    jsonGenerator.writeBoolean(false);
                    object = segment;
                    continue block14;
                }
                case 11: {
                    jsonGenerator.writeNull();
                    object = segment;
                    continue block14;
                }
                case 12: 
            }
            jsonGenerator.writeObject(segment.get(n2));
            object = segment;
        } while (true);
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    @Override
    public JsonGenerator setFeatureMask(int n2) {
        this._generatorFeatures = n2;
        return this;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String toString() {
        var3_1 = new StringBuilder();
        var3_1.append("[TokenBuffer: ");
        var4_3 = this.asParser();
        var2_4 = 0;
        var1_5 = this._hasNativeTypeIds != false || this._hasNativeObjectIds != false;
        do lbl-1000: // 2 sources:
        {
            var5_6 = var4_3.nextToken();
            if (var5_6 == null) {
                if (var2_4 >= 100) {
                    var3_1.append(" ... (truncated ").append(var2_4 - 100).append(" entries)");
                }
                var3_1.append(']');
                return var3_1.toString();
            }
            if (!var1_5) ** break block7
            this._appendNativeIds(var3_1);
            break;
        } while (true);
        catch (IOException var3_2) {
            throw new IllegalStateException(var3_2);
        }
        {
            block8 : {
                
                if (var2_4 >= 100) ** GOTO lbl28
                if (var2_4 <= 0) ** GOTO lbl23
                var3_1.append(", ");
lbl23: // 2 sources:
                var3_1.append(var5_6.toString());
                if (var5_6 != JsonToken.FIELD_NAME) break block8;
                var3_1.append('(');
                var3_1.append(var4_3.getCurrentName());
                var3_1.append(')');
            }
            ++var2_4;
            ** while (true)
        }
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        return this;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        base64Variant = (Base64Variant)new byte[n3];
        System.arraycopy(arrby, n2, base64Variant, 0, n3);
        this.writeObject(base64Variant);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeBoolean(boolean bl2) throws IOException, JsonGenerationException {
        JsonToken jsonToken = bl2 ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
        this._append(jsonToken);
    }

    @Override
    public final void writeEndArray() throws IOException, JsonGenerationException {
        this._append(JsonToken.END_ARRAY);
        JsonStreamContext jsonStreamContext = this._writeContext.getParent();
        if (jsonStreamContext != null) {
            this._writeContext = jsonStreamContext;
        }
    }

    @Override
    public final void writeEndObject() throws IOException, JsonGenerationException {
        this._append(JsonToken.END_OBJECT);
        JsonStreamContext jsonStreamContext = this._writeContext.getParent();
        if (jsonStreamContext != null) {
            this._writeContext = jsonStreamContext;
        }
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException, JsonGenerationException {
        this._append(JsonToken.FIELD_NAME, serializableString);
        this._writeContext.writeFieldName(serializableString.getValue());
    }

    @Override
    public final void writeFieldName(String string2) throws IOException, JsonGenerationException {
        this._append(JsonToken.FIELD_NAME, string2);
        this._writeContext.writeFieldName(string2);
    }

    @Override
    public void writeNull() throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NULL);
    }

    @Override
    public void writeNumber(double d2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_FLOAT, d2);
    }

    @Override
    public void writeNumber(float f2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_FLOAT, Float.valueOf(f2));
    }

    @Override
    public void writeNumber(int n2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_INT, n2);
    }

    @Override
    public void writeNumber(long l2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_INT, l2);
    }

    @Override
    public void writeNumber(String string2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_FLOAT, string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException, JsonGenerationException {
        if (bigDecimal == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_NUMBER_FLOAT, bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException, JsonGenerationException {
        if (bigInteger == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_NUMBER_INT, bigInteger);
    }

    @Override
    public void writeNumber(short s2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_INT, s2);
    }

    @Override
    public void writeObject(Object object) throws IOException {
        if (object == null) {
            this.writeNull();
            return;
        }
        if (object.getClass() == byte[].class) {
            this._append(JsonToken.VALUE_EMBEDDED_OBJECT, object);
            return;
        }
        if (this._objectCodec == null) {
            this._append(JsonToken.VALUE_EMBEDDED_OBJECT, object);
            return;
        }
        this._objectCodec.writeValue(this, object);
    }

    @Override
    public void writeObjectId(Object object) {
        this._objectId = object;
        this._hasNativeId = true;
    }

    @Override
    public void writeRaw(char c2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRaw(String string2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRaw(String string2, int n2, int n3) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRaw(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawValue(String string2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawValue(String string2, int n2, int n3) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawValue(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public final void writeStartArray() throws IOException, JsonGenerationException {
        this._append(JsonToken.START_ARRAY);
        this._writeContext = this._writeContext.createChildArrayContext();
    }

    @Override
    public final void writeStartObject() throws IOException, JsonGenerationException {
        this._append(JsonToken.START_OBJECT);
        this._writeContext = this._writeContext.createChildObjectContext();
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException, JsonGenerationException {
        if (serializableString == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_STRING, serializableString);
    }

    @Override
    public void writeString(String string2) throws IOException, JsonGenerationException {
        if (string2 == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_STRING, string2);
    }

    @Override
    public void writeString(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        this.writeString(new String(arrc, n2, n3));
    }

    @Override
    public void writeTree(TreeNode treeNode) throws IOException {
        if (treeNode == null) {
            this.writeNull();
            return;
        }
        if (this._objectCodec == null) {
            this._append(JsonToken.VALUE_EMBEDDED_OBJECT, treeNode);
            return;
        }
        this._objectCodec.writeTree(this, treeNode);
    }

    @Override
    public void writeTypeId(Object object) {
        this._typeId = object;
        this._hasNativeId = true;
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    protected static final class Parser
    extends ParserMinimalBase {
        protected transient ByteArrayBuilder _byteBuilder;
        protected boolean _closed;
        protected ObjectCodec _codec;
        protected final boolean _hasNativeIds;
        protected final boolean _hasNativeObjectIds;
        protected final boolean _hasNativeTypeIds;
        protected JsonLocation _location = null;
        protected JsonReadContext _parsingContext;
        protected Segment _segment;
        protected int _segmentPtr;

        @Deprecated
        protected Parser(Segment segment, ObjectCodec objectCodec) {
            this(segment, objectCodec, false, false);
        }

        public Parser(Segment segment, ObjectCodec objectCodec, boolean bl2, boolean bl3) {
            super(0);
            this._segment = segment;
            this._segmentPtr = -1;
            this._codec = objectCodec;
            this._parsingContext = JsonReadContext.createRootContext(null);
            this._hasNativeTypeIds = bl2;
            this._hasNativeObjectIds = bl3;
            this._hasNativeIds = bl2 | bl3;
        }

        protected final void _checkIsNumber() throws JsonParseException {
            if (this._currToken == null || !this._currToken.isNumeric()) {
                throw this._constructError("Current token (" + (Object)((Object)this._currToken) + ") not numeric, can not use numeric value accessors");
            }
        }

        protected final Object _currentObject() {
            return this._segment.get(this._segmentPtr);
        }

        @Override
        protected void _handleEOF() throws JsonParseException {
            this._throwInternal();
        }

        @Override
        public boolean canReadObjectId() {
            return this._hasNativeObjectIds;
        }

        @Override
        public boolean canReadTypeId() {
            return this._hasNativeTypeIds;
        }

        @Override
        public void close() throws IOException {
            if (!this._closed) {
                this._closed = true;
            }
        }

        @Override
        public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
            Number number = this.getNumberValue();
            if (number instanceof BigInteger) {
                return (BigInteger)number;
            }
            if (this.getNumberType() == JsonParser.NumberType.BIG_DECIMAL) {
                return ((BigDecimal)number).toBigInteger();
            }
            return BigInteger.valueOf(number.longValue());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
            Object object;
            if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && (object = this._currentObject()) instanceof byte[]) {
                return (byte[])object;
            }
            if (this._currToken != JsonToken.VALUE_STRING) {
                throw this._constructError("Current token (" + (Object)((Object)this._currToken) + ") not VALUE_STRING (or VALUE_EMBEDDED_OBJECT with byte[]), can not access as binary");
            }
            String string2 = this.getText();
            if (string2 == null) {
                return null;
            }
            object = this._byteBuilder;
            if (object == null) {
                this._byteBuilder = object = new ByteArrayBuilder(100);
            } else {
                this._byteBuilder.reset();
            }
            this._decodeBase64(string2, (ByteArrayBuilder)object, base64Variant);
            return object.toByteArray();
        }

        @Override
        public ObjectCodec getCodec() {
            return this._codec;
        }

        @Override
        public JsonLocation getCurrentLocation() {
            if (this._location == null) {
                return JsonLocation.NA;
            }
            return this._location;
        }

        @Override
        public String getCurrentName() {
            return this._parsingContext.getCurrentName();
        }

        @Override
        public BigDecimal getDecimalValue() throws IOException, JsonParseException {
            Number number = this.getNumberValue();
            if (number instanceof BigDecimal) {
                return (BigDecimal)number;
            }
            switch (.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[this.getNumberType().ordinal()]) {
                default: {
                    return BigDecimal.valueOf(number.doubleValue());
                }
                case 1: 
                case 5: {
                    return BigDecimal.valueOf(number.longValue());
                }
                case 2: 
            }
            return new BigDecimal((BigInteger)number);
        }

        @Override
        public double getDoubleValue() throws IOException, JsonParseException {
            return this.getNumberValue().doubleValue();
        }

        @Override
        public Object getEmbeddedObject() {
            if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                return this._currentObject();
            }
            return null;
        }

        @Override
        public float getFloatValue() throws IOException, JsonParseException {
            return this.getNumberValue().floatValue();
        }

        @Override
        public int getIntValue() throws IOException, JsonParseException {
            if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
                return ((Number)this._currentObject()).intValue();
            }
            return this.getNumberValue().intValue();
        }

        @Override
        public long getLongValue() throws IOException, JsonParseException {
            return this.getNumberValue().longValue();
        }

        @Override
        public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
            Number number = this.getNumberValue();
            if (number instanceof Integer) {
                return JsonParser.NumberType.INT;
            }
            if (number instanceof Long) {
                return JsonParser.NumberType.LONG;
            }
            if (number instanceof Double) {
                return JsonParser.NumberType.DOUBLE;
            }
            if (number instanceof BigDecimal) {
                return JsonParser.NumberType.BIG_DECIMAL;
            }
            if (number instanceof BigInteger) {
                return JsonParser.NumberType.BIG_INTEGER;
            }
            if (number instanceof Float) {
                return JsonParser.NumberType.FLOAT;
            }
            if (number instanceof Short) {
                return JsonParser.NumberType.INT;
            }
            return null;
        }

        @Override
        public final Number getNumberValue() throws IOException, JsonParseException {
            this._checkIsNumber();
            Object object = this._currentObject();
            if (object instanceof Number) {
                return (Number)object;
            }
            if (object instanceof String) {
                if ((object = (String)object).indexOf(46) >= 0) {
                    return Double.parseDouble((String)object);
                }
                return Long.parseLong((String)object);
            }
            if (object == null) {
                return null;
            }
            throw new IllegalStateException("Internal error: entry should be a Number, but is of type " + object.getClass().getName());
        }

        @Override
        public Object getObjectId() {
            return this._segment.findObjectId(this._segmentPtr);
        }

        @Override
        public JsonStreamContext getParsingContext() {
            return this._parsingContext;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String getText() {
            String string2;
            String string3 = null;
            if (this._currToken == JsonToken.VALUE_STRING || this._currToken == JsonToken.FIELD_NAME) {
                Object object = this._currentObject();
                if (object instanceof String) {
                    return (String)object;
                }
                string2 = string3;
                if (object == null) return string2;
                return object.toString();
            }
            string2 = string3;
            if (this._currToken == null) return string2;
            switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
                default: {
                    return this._currToken.asString();
                }
                case 7: 
                case 8: 
            }
            Object object = this._currentObject();
            string2 = string3;
            if (object == null) return string2;
            return object.toString();
        }

        @Override
        public char[] getTextCharacters() {
            String string2 = this.getText();
            if (string2 == null) {
                return null;
            }
            return string2.toCharArray();
        }

        @Override
        public int getTextLength() {
            String string2 = this.getText();
            if (string2 == null) {
                return 0;
            }
            return string2.length();
        }

        @Override
        public int getTextOffset() {
            return 0;
        }

        @Override
        public JsonLocation getTokenLocation() {
            return this.getCurrentLocation();
        }

        @Override
        public Object getTypeId() {
            return this._segment.findTypeId(this._segmentPtr);
        }

        @Override
        public boolean hasTextCharacters() {
            return false;
        }

        @Override
        public boolean isClosed() {
            return this._closed;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public JsonToken nextToken() throws IOException, JsonParseException {
            int n2;
            if (this._closed) return null;
            if (this._segment == null) {
                return null;
            }
            this._segmentPtr = n2 = this._segmentPtr + 1;
            if (n2 >= 16) {
                this._segmentPtr = 0;
                this._segment = this._segment.next();
                if (this._segment == null) return null;
            }
            this._currToken = this._segment.type(this._segmentPtr);
            if (this._currToken == JsonToken.FIELD_NAME) {
                Object object = this._currentObject();
                object = object instanceof String ? (String)object : object.toString();
                this._parsingContext.setCurrentName((String)object);
                return this._currToken;
            }
            if (this._currToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
                return this._currToken;
            }
            if (this._currToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
                return this._currToken;
            }
            if (this._currToken != JsonToken.END_OBJECT) {
                if (this._currToken != JsonToken.END_ARRAY) return this._currToken;
            }
            this._parsingContext = this._parsingContext.getParent();
            if (this._parsingContext != null) return this._currToken;
            this._parsingContext = JsonReadContext.createRootContext(null);
            return this._currToken;
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
                throw new RuntimeException(var1_2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public JsonToken peekNextToken() throws IOException, JsonParseException {
            int n2;
            if (this._closed) {
                return null;
            }
            Segment segment = this._segment;
            int n3 = n2 = this._segmentPtr + 1;
            Segment segment2 = segment;
            if (n2 >= 16) {
                n3 = 0;
                if (segment == null) {
                    return null;
                }
                segment2 = segment.next();
            }
            if (segment2 == null) return null;
            return segment2.type(n3);
        }

        @Override
        public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException, JsonParseException {
            int n2 = 0;
            if ((base64Variant = (Base64Variant)this.getBinaryValue(base64Variant)) != null) {
                outputStream.write((byte[])base64Variant, 0, base64Variant.length);
                n2 = base64Variant.length;
            }
            return n2;
        }

        @Override
        public void setCodec(ObjectCodec objectCodec) {
            this._codec = objectCodec;
        }

        public void setLocation(JsonLocation jsonLocation) {
            this._location = jsonLocation;
        }

        @Override
        public Version version() {
            return PackageVersion.VERSION;
        }
    }

    protected static final class Segment {
        public static final int TOKENS_PER_SEGMENT = 16;
        private static final JsonToken[] TOKEN_TYPES_BY_INDEX = new JsonToken[16];
        protected TreeMap<Integer, Object> _nativeIds;
        protected Segment _next;
        protected long _tokenTypes;
        protected final Object[] _tokens = new Object[16];

        static {
            JsonToken[] arrjsonToken = JsonToken.values();
            System.arraycopy(arrjsonToken, 1, TOKEN_TYPES_BY_INDEX, 1, Math.min(15, arrjsonToken.length - 1));
        }

        private final int _objectIdIndex(int n2) {
            return n2 + n2 + 1;
        }

        private final int _typeIdIndex(int n2) {
            return n2 + n2;
        }

        private final void assignNativeIds(int n2, Object object, Object object2) {
            if (this._nativeIds == null) {
                this._nativeIds = new TreeMap();
            }
            if (object != null) {
                this._nativeIds.put(this._objectIdIndex(n2), object);
            }
            if (object2 != null) {
                this._nativeIds.put(this._typeIdIndex(n2), object2);
            }
        }

        private void set(int n2, int n3, Object object) {
            long l2;
            this._tokens[n2] = object;
            long l3 = l2 = (long)n3;
            if (n2 > 0) {
                l3 = l2 << (n2 << 2);
            }
            this._tokenTypes |= l3;
        }

        private void set(int n2, int n3, Object object, Object object2, Object object3) {
            long l2;
            this._tokens[n2] = object;
            long l3 = l2 = (long)n3;
            if (n2 > 0) {
                l3 = l2 << (n2 << 2);
            }
            this._tokenTypes |= l3;
            this.assignNativeIds(n2, object2, object3);
        }

        private void set(int n2, JsonToken jsonToken) {
            long l2;
            long l3 = l2 = (long)jsonToken.ordinal();
            if (n2 > 0) {
                l3 = l2 << (n2 << 2);
            }
            this._tokenTypes |= l3;
        }

        private void set(int n2, JsonToken jsonToken, Object object) {
            long l2;
            this._tokens[n2] = object;
            long l3 = l2 = (long)jsonToken.ordinal();
            if (n2 > 0) {
                l3 = l2 << (n2 << 2);
            }
            this._tokenTypes |= l3;
        }

        private void set(int n2, JsonToken jsonToken, Object object, Object object2) {
            long l2;
            long l3 = l2 = (long)jsonToken.ordinal();
            if (n2 > 0) {
                l3 = l2 << (n2 << 2);
            }
            this._tokenTypes |= l3;
            this.assignNativeIds(n2, object, object2);
        }

        private void set(int n2, JsonToken jsonToken, Object object, Object object2, Object object3) {
            long l2;
            this._tokens[n2] = object;
            long l3 = l2 = (long)jsonToken.ordinal();
            if (n2 > 0) {
                l3 = l2 << (n2 << 2);
            }
            this._tokenTypes |= l3;
            this.assignNativeIds(n2, object2, object3);
        }

        public Segment append(int n2, JsonToken jsonToken) {
            if (n2 < 16) {
                this.set(n2, jsonToken);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken);
            return this._next;
        }

        public Segment append(int n2, JsonToken jsonToken, Object object) {
            if (n2 < 16) {
                this.set(n2, jsonToken, object);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken, object);
            return this._next;
        }

        public Segment append(int n2, JsonToken jsonToken, Object object, Object object2) {
            if (n2 < 16) {
                this.set(n2, jsonToken, object, object2);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken, object, object2);
            return this._next;
        }

        public Segment append(int n2, JsonToken jsonToken, Object object, Object object2, Object object3) {
            if (n2 < 16) {
                this.set(n2, jsonToken, object, object2, object3);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken, object, object2, object3);
            return this._next;
        }

        public Segment appendRaw(int n2, int n3, Object object) {
            if (n2 < 16) {
                this.set(n2, n3, object);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, n3, object);
            return this._next;
        }

        public Segment appendRaw(int n2, int n3, Object object, Object object2, Object object3) {
            if (n2 < 16) {
                this.set(n2, n3, object, object2, object3);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, n3, object, object2, object3);
            return this._next;
        }

        public Object findObjectId(int n2) {
            if (this._nativeIds == null) {
                return null;
            }
            return this._nativeIds.get(this._objectIdIndex(n2));
        }

        public Object findTypeId(int n2) {
            if (this._nativeIds == null) {
                return null;
            }
            return this._nativeIds.get(this._typeIdIndex(n2));
        }

        public Object get(int n2) {
            return this._tokens[n2];
        }

        public boolean hasIds() {
            if (this._nativeIds != null) {
                return true;
            }
            return false;
        }

        public Segment next() {
            return this._next;
        }

        public int rawType(int n2) {
            long l2;
            long l3 = l2 = this._tokenTypes;
            if (n2 > 0) {
                l3 = l2 >> (n2 << 2);
            }
            return (int)l3 & 15;
        }

        public JsonToken type(int n2) {
            long l2;
            long l3 = l2 = this._tokenTypes;
            if (n2 > 0) {
                l3 = l2 >> (n2 << 2);
            }
            n2 = (int)l3;
            return TOKEN_TYPES_BY_INDEX[n2 & 15];
        }
    }

}

