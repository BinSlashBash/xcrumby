/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectReader
extends ObjectCodec
implements Versioned,
Serializable {
    private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
    private static final long serialVersionUID = -4251443320039569153L;
    protected final DeserializationConfig _config;
    protected final DefaultDeserializationContext _context;
    protected final DataFormatReaders _dataFormatReaders;
    protected final InjectableValues _injectableValues;
    protected final JsonFactory _parserFactory;
    protected final JsonDeserializer<Object> _rootDeserializer;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
    protected final RootNameLookup _rootNames;
    protected final FormatSchema _schema;
    protected final boolean _unwrapRoot;
    protected final Object _valueToUpdate;
    protected final JavaType _valueType;

    protected ObjectReader(ObjectMapper objectMapper, DeserializationConfig deserializationConfig) {
        this(objectMapper, deserializationConfig, null, null, null, null);
    }

    protected ObjectReader(ObjectMapper objectMapper, DeserializationConfig deserializationConfig, JavaType javaType, Object object, FormatSchema formatSchema, InjectableValues injectableValues) {
        this._config = deserializationConfig;
        this._context = objectMapper._deserializationContext;
        this._rootDeserializers = objectMapper._rootDeserializers;
        this._parserFactory = objectMapper._jsonFactory;
        this._rootNames = objectMapper._rootNames;
        this._valueType = javaType;
        this._valueToUpdate = object;
        if (object != null && javaType.isArrayType()) {
            throw new IllegalArgumentException("Can not update an array value");
        }
        this._schema = formatSchema;
        this._injectableValues = injectableValues;
        this._unwrapRoot = deserializationConfig.useRootWrapping();
        this._rootDeserializer = this._prefetchRootDeserializer(deserializationConfig, javaType);
        this._dataFormatReaders = null;
    }

    protected ObjectReader(ObjectReader objectReader, JsonFactory jsonFactory) {
        this._config = objectReader._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, jsonFactory.requiresPropertyOrdering());
        this._context = objectReader._context;
        this._rootDeserializers = objectReader._rootDeserializers;
        this._parserFactory = jsonFactory;
        this._rootNames = objectReader._rootNames;
        this._valueType = objectReader._valueType;
        this._rootDeserializer = objectReader._rootDeserializer;
        this._valueToUpdate = objectReader._valueToUpdate;
        this._schema = objectReader._schema;
        this._injectableValues = objectReader._injectableValues;
        this._unwrapRoot = objectReader._unwrapRoot;
        this._dataFormatReaders = objectReader._dataFormatReaders;
    }

    protected ObjectReader(ObjectReader objectReader, DeserializationConfig deserializationConfig) {
        this._config = deserializationConfig;
        this._context = objectReader._context;
        this._rootDeserializers = objectReader._rootDeserializers;
        this._parserFactory = objectReader._parserFactory;
        this._rootNames = objectReader._rootNames;
        this._valueType = objectReader._valueType;
        this._rootDeserializer = objectReader._rootDeserializer;
        this._valueToUpdate = objectReader._valueToUpdate;
        this._schema = objectReader._schema;
        this._injectableValues = objectReader._injectableValues;
        this._unwrapRoot = deserializationConfig.useRootWrapping();
        this._dataFormatReaders = objectReader._dataFormatReaders;
    }

    protected ObjectReader(ObjectReader objectReader, DeserializationConfig deserializationConfig, JavaType javaType, JsonDeserializer<Object> jsonDeserializer, Object object, FormatSchema formatSchema, InjectableValues injectableValues, DataFormatReaders dataFormatReaders) {
        this._config = deserializationConfig;
        this._context = objectReader._context;
        this._rootDeserializers = objectReader._rootDeserializers;
        this._parserFactory = objectReader._parserFactory;
        this._rootNames = objectReader._rootNames;
        this._valueType = javaType;
        this._rootDeserializer = jsonDeserializer;
        this._valueToUpdate = object;
        if (object != null && javaType.isArrayType()) {
            throw new IllegalArgumentException("Can not update an array value");
        }
        this._schema = formatSchema;
        this._injectableValues = injectableValues;
        this._unwrapRoot = deserializationConfig.useRootWrapping();
        this._dataFormatReaders = dataFormatReaders;
    }

    protected static JsonToken _initForReading(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        JsonToken jsonToken;
        JsonToken jsonToken2 = jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == null) {
            jsonToken2 = jsonToken = jsonParser.nextToken();
            if (jsonToken == null) {
                throw JsonMappingException.from(jsonParser, "No content to map due to end-of-input");
            }
        }
        return jsonToken2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _bind(JsonParser jsonParser, Object object) throws IOException, JsonParseException, JsonMappingException {
        Object object2 = ObjectReader._initForReading(jsonParser);
        if (object2 == JsonToken.VALUE_NULL) {
            if (object == null) {
                object = this._findRootDeserializer(this.createDeserializationContext(jsonParser, this._config), this._valueType).getNullValue();
            }
        } else if (object2 != JsonToken.END_ARRAY && object2 != JsonToken.END_OBJECT) {
            object2 = this.createDeserializationContext(jsonParser, this._config);
            JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer((DeserializationContext)object2, this._valueType);
            if (this._unwrapRoot) {
                object = this._unwrapAndDeserialize(jsonParser, (DeserializationContext)object2, this._valueType, jsonDeserializer);
            } else if (object == null) {
                object = jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object2);
            } else {
                jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object2, object);
            }
        }
        jsonParser.clearCurrentToken();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object _bindAndClose(JsonParser jsonParser, Object object) throws IOException, JsonParseException, JsonMappingException {
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        try {
            Object object2 = ObjectReader._initForReading(jsonParser);
            if (object2 == JsonToken.VALUE_NULL) {
                if (object != null) return object;
                object = this._findRootDeserializer(this.createDeserializationContext(jsonParser, this._config), this._valueType).getNullValue();
                return object;
            }
            if (object2 == JsonToken.END_ARRAY) return object;
            if (object2 == JsonToken.END_OBJECT) {
                return object;
            }
            object2 = this.createDeserializationContext(jsonParser, this._config);
            JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer((DeserializationContext)object2, this._valueType);
            if (this._unwrapRoot) {
                object = this._unwrapAndDeserialize(jsonParser, (DeserializationContext)object2, this._valueType, jsonDeserializer);
                return object;
            }
            if (object == null) {
                object = jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object2);
                return object;
            }
            jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object2, object);
            return object;
        }
        finally {
            jsonParser.close();
        }
    }

    protected JsonNode _bindAndCloseAsTree(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        try {
            JsonNode jsonNode = this._bindAsTree(jsonParser);
            return jsonNode;
        }
        finally {
            jsonParser.close();
        }
    }

    protected <T> MappingIterator<T> _bindAndReadValues(JsonParser jsonParser, Object object) throws IOException, JsonProcessingException {
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        jsonParser.nextToken();
        object = this.createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, (DeserializationContext)object, this._findRootDeserializer((DeserializationContext)object, this._valueType), true, this._valueToUpdate);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonNode _bindAsTree(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        Object object = ObjectReader._initForReading(jsonParser);
        if (object == JsonToken.VALUE_NULL || object == JsonToken.END_ARRAY || object == JsonToken.END_OBJECT) {
            object = NullNode.instance;
        } else {
            object = this.createDeserializationContext(jsonParser, this._config);
            JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer((DeserializationContext)object, JSON_NODE_TYPE);
            object = this._unwrapRoot ? (JsonNode)this._unwrapAndDeserialize(jsonParser, (DeserializationContext)object, JSON_NODE_TYPE, jsonDeserializer) : (JsonNode)jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object);
        }
        jsonParser.clearCurrentToken();
        return object;
    }

    protected Object _detectBindAndClose(DataFormatReaders.Match match, boolean bl2) throws IOException {
        if (!match.hasMatch()) {
            this._reportUnkownFormat(this._dataFormatReaders, match);
        }
        JsonParser jsonParser = match.createParserWithMatch();
        if (bl2) {
            jsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
        }
        return match.getReader()._bindAndClose(jsonParser, this._valueToUpdate);
    }

    protected Object _detectBindAndClose(byte[] object, int n2, int n3) throws IOException {
        if (!(object = (Object)this._dataFormatReaders.findFormat((byte[])object, n2, n3)).hasMatch()) {
            this._reportUnkownFormat(this._dataFormatReaders, (DataFormatReaders.Match)object);
        }
        JsonParser jsonParser = object.createParserWithMatch();
        return object.getReader()._bindAndClose(jsonParser, this._valueToUpdate);
    }

    protected JsonNode _detectBindAndCloseAsTree(InputStream object) throws IOException {
        if (!(object = this._dataFormatReaders.findFormat((InputStream)object)).hasMatch()) {
            this._reportUnkownFormat(this._dataFormatReaders, (DataFormatReaders.Match)object);
        }
        JsonParser jsonParser = object.createParserWithMatch();
        jsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
        return object.getReader()._bindAndCloseAsTree(jsonParser);
    }

    protected <T> MappingIterator<T> _detectBindAndReadValues(DataFormatReaders.Match match, boolean bl2) throws IOException, JsonProcessingException {
        if (!match.hasMatch()) {
            this._reportUnkownFormat(this._dataFormatReaders, match);
        }
        JsonParser jsonParser = match.createParserWithMatch();
        if (bl2) {
            jsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
        }
        return match.getReader()._bindAndReadValues(jsonParser, this._valueToUpdate);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext jsonDeserializer, JavaType javaType) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer2;
        if (this._rootDeserializer != null) {
            return this._rootDeserializer;
        }
        if (javaType == null) {
            throw new JsonMappingException("No value type configured for ObjectReader");
        }
        JsonDeserializer<Object> jsonDeserializer3 = jsonDeserializer2 = this._rootDeserializers.get(javaType);
        if (jsonDeserializer2 != null) return jsonDeserializer3;
        if ((jsonDeserializer = jsonDeserializer.findRootValueDeserializer(javaType)) == null) {
            throw new JsonMappingException("Can not find a deserializer for type " + javaType);
        }
        this._rootDeserializers.put(javaType, jsonDeserializer);
        return jsonDeserializer;
    }

    protected InputStream _inputStream(File file) throws IOException {
        return new FileInputStream(file);
    }

    protected InputStream _inputStream(URL uRL) throws IOException {
        return uRL.openStream();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonDeserializer<Object> _prefetchRootDeserializer(DeserializationConfig jsonDeserializer, JavaType javaType) {
        JsonDeserializer<Object> jsonDeserializer2;
        jsonDeserializer = jsonDeserializer2 = null;
        if (javaType == null) return jsonDeserializer;
        if (!this._config.isEnabled(DeserializationFeature.EAGER_DESERIALIZER_FETCH)) {
            return jsonDeserializer2;
        }
        jsonDeserializer = jsonDeserializer2 = this._rootDeserializers.get(javaType);
        if (jsonDeserializer2 != null) return jsonDeserializer;
        jsonDeserializer = jsonDeserializer2;
        try {
            jsonDeserializer = jsonDeserializer2 = this.createDeserializationContext(null, this._config).findRootValueDeserializer(javaType);
            if (jsonDeserializer2 == null) return jsonDeserializer;
            jsonDeserializer = jsonDeserializer2;
        }
        catch (JsonProcessingException var2_3) {
            return jsonDeserializer;
        }
        this._rootDeserializers.put(javaType, jsonDeserializer2);
        return jsonDeserializer2;
    }

    protected void _reportUndetectableSource(Object object) throws JsonProcessingException {
        throw new JsonParseException("Can not use source of type " + object.getClass().getName() + " with format auto-detection: must be byte- not char-based", JsonLocation.NA);
    }

    protected void _reportUnkownFormat(DataFormatReaders dataFormatReaders, DataFormatReaders.Match match) throws JsonProcessingException {
        throw new JsonParseException("Can not detect format from input, does not look like any of detectable formats " + dataFormatReaders.toString(), JsonLocation.NA);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _unwrapAndDeserialize(JsonParser jsonParser, DeserializationContext object, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) throws IOException, JsonParseException, JsonMappingException {
        String string2;
        String string3 = string2 = this._config.getRootName();
        if (string2 == null) {
            string3 = this._rootNames.findRootName(javaType, this._config).getSimpleName();
        }
        if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not START_OBJECT (needed to unwrap root name '" + string3 + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        if (jsonParser.nextToken() != JsonToken.FIELD_NAME) {
            throw JsonMappingException.from(jsonParser, "Current token not FIELD_NAME (to contain expected root name '" + string3 + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        string2 = jsonParser.getCurrentName();
        if (!string3.equals(string2)) {
            throw JsonMappingException.from(jsonParser, "Root name '" + string2 + "' does not match expected ('" + string3 + "') for type " + javaType);
        }
        jsonParser.nextToken();
        if (this._valueToUpdate == null) {
            object = jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object);
        } else {
            jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object, this._valueToUpdate);
            object = this._valueToUpdate;
        }
        if (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + string3 + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        return object;
    }

    protected void _verifySchemaType(FormatSchema formatSchema) {
        if (formatSchema != null && !this._parserFactory.canUseSchema(formatSchema)) {
            throw new IllegalArgumentException("Can not use FormatSchema of type " + formatSchema.getClass().getName() + " for format " + this._parserFactory.getFormatName());
        }
    }

    protected ObjectReader _with(DeserializationConfig deserializationConfig) {
        if (deserializationConfig == this._config) {
            return this;
        }
        if (this._dataFormatReaders != null) {
            return new ObjectReader(this, deserializationConfig).withFormatDetection(this._dataFormatReaders.with(deserializationConfig));
        }
        return new ObjectReader(this, deserializationConfig);
    }

    @Override
    public JsonNode createArrayNode() {
        return this._config.getNodeFactory().arrayNode();
    }

    protected DefaultDeserializationContext createDeserializationContext(JsonParser jsonParser, DeserializationConfig deserializationConfig) {
        return this._context.createInstance(deserializationConfig, jsonParser, this._injectableValues);
    }

    @Override
    public JsonNode createObjectNode() {
        return this._config.getNodeFactory().objectNode();
    }

    public ContextAttributes getAttributes() {
        return this._config.getAttributes();
    }

    public DeserializationConfig getConfig() {
        return this._config;
    }

    @Override
    public JsonFactory getFactory() {
        return this._parserFactory;
    }

    @Deprecated
    @Override
    public JsonFactory getJsonFactory() {
        return this._parserFactory;
    }

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public boolean isEnabled(JsonParser.Feature feature) {
        return this._parserFactory.isEnabled(feature);
    }

    public boolean isEnabled(DeserializationFeature deserializationFeature) {
        return this._config.isEnabled(deserializationFeature);
    }

    public boolean isEnabled(MapperFeature mapperFeature) {
        return this._config.isEnabled(mapperFeature);
    }

    @Override
    public <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException, JsonProcessingException {
        return (T)this._bindAsTree(jsonParser);
    }

    public JsonNode readTree(InputStream inputStream) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return this._detectBindAndCloseAsTree(inputStream);
        }
        return this._bindAndCloseAsTree(this._parserFactory.createParser(inputStream));
    }

    public JsonNode readTree(Reader reader) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            this._reportUndetectableSource(reader);
        }
        return this._bindAndCloseAsTree(this._parserFactory.createParser(reader));
    }

    public JsonNode readTree(String string2) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            this._reportUndetectableSource(string2);
        }
        return this._bindAndCloseAsTree(this._parserFactory.createParser(string2));
    }

    public <T> T readValue(JsonParser jsonParser) throws IOException, JsonProcessingException {
        return (T)this._bind(jsonParser, this._valueToUpdate);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException, JsonProcessingException {
        return this.withType((JavaType)resolvedType).readValue(jsonParser);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.withType(typeReference).readValue(jsonParser);
    }

    public <T> T readValue(JsonParser jsonParser, JavaType javaType) throws IOException, JsonProcessingException {
        return this.withType(javaType).readValue(jsonParser);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, Class<T> class_) throws IOException, JsonProcessingException {
        return this.withType(class_).readValue(jsonParser);
    }

    public <T> T readValue(JsonNode jsonNode) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            this._reportUndetectableSource(jsonNode);
        }
        return (T)this._bindAndClose(this.treeAsTokens(jsonNode), this._valueToUpdate);
    }

    public <T> T readValue(File file) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return (T)this._detectBindAndClose(this._dataFormatReaders.findFormat(this._inputStream(file)), true);
        }
        return (T)this._bindAndClose(this._parserFactory.createParser(file), this._valueToUpdate);
    }

    public <T> T readValue(InputStream inputStream) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return (T)this._detectBindAndClose(this._dataFormatReaders.findFormat(inputStream), false);
        }
        return (T)this._bindAndClose(this._parserFactory.createParser(inputStream), this._valueToUpdate);
    }

    public <T> T readValue(Reader reader) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            this._reportUndetectableSource(reader);
        }
        return (T)this._bindAndClose(this._parserFactory.createParser(reader), this._valueToUpdate);
    }

    public <T> T readValue(String string2) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            this._reportUndetectableSource(string2);
        }
        return (T)this._bindAndClose(this._parserFactory.createParser(string2), this._valueToUpdate);
    }

    public <T> T readValue(URL uRL) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return (T)this._detectBindAndClose(this._dataFormatReaders.findFormat(this._inputStream(uRL)), true);
        }
        return (T)this._bindAndClose(this._parserFactory.createParser(uRL), this._valueToUpdate);
    }

    public <T> T readValue(byte[] arrby) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return (T)this._detectBindAndClose(arrby, 0, arrby.length);
        }
        return (T)this._bindAndClose(this._parserFactory.createParser(arrby), this._valueToUpdate);
    }

    public <T> T readValue(byte[] arrby, int n2, int n3) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return (T)this._detectBindAndClose(arrby, n2, n3);
        }
        return (T)this._bindAndClose(this._parserFactory.createParser(arrby, n2, n3), this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser) throws IOException, JsonProcessingException {
        DefaultDeserializationContext defaultDeserializationContext = this.createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, defaultDeserializationContext, this._findRootDeserializer(defaultDeserializationContext, this._valueType), false, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(File file) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return this._detectBindAndReadValues(this._dataFormatReaders.findFormat(this._inputStream(file)), false);
        }
        return this._bindAndReadValues(this._parserFactory.createParser(file), this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(InputStream inputStream) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return this._detectBindAndReadValues(this._dataFormatReaders.findFormat(inputStream), false);
        }
        return this._bindAndReadValues(this._parserFactory.createParser(inputStream), this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(Reader closeable) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            this._reportUndetectableSource(closeable);
        }
        closeable = this._parserFactory.createParser((Reader)closeable);
        if (this._schema != null) {
            closeable.setSchema(this._schema);
        }
        closeable.nextToken();
        DefaultDeserializationContext defaultDeserializationContext = this.createDeserializationContext((JsonParser)closeable, this._config);
        return new MappingIterator(this._valueType, (JsonParser)closeable, defaultDeserializationContext, this._findRootDeserializer(defaultDeserializationContext, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(String object) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            this._reportUndetectableSource(object);
        }
        object = this._parserFactory.createParser((String)object);
        if (this._schema != null) {
            object.setSchema(this._schema);
        }
        object.nextToken();
        DefaultDeserializationContext defaultDeserializationContext = this.createDeserializationContext((JsonParser)object, this._config);
        return new MappingIterator(this._valueType, (JsonParser)object, defaultDeserializationContext, this._findRootDeserializer(defaultDeserializationContext, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(URL uRL) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return this._detectBindAndReadValues(this._dataFormatReaders.findFormat(this._inputStream(uRL)), true);
        }
        return this._bindAndReadValues(this._parserFactory.createParser(uRL), this._valueToUpdate);
    }

    public final <T> MappingIterator<T> readValues(byte[] arrby) throws IOException, JsonProcessingException {
        return this.readValues(arrby, 0, arrby.length);
    }

    public <T> MappingIterator<T> readValues(byte[] arrby, int n2, int n3) throws IOException, JsonProcessingException {
        if (this._dataFormatReaders != null) {
            return this._detectBindAndReadValues(this._dataFormatReaders.findFormat(arrby, n2, n3), false);
        }
        return this._bindAndReadValues(this._parserFactory.createParser(arrby), this._valueToUpdate);
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException, JsonProcessingException {
        return this.readValues(jsonParser, (JavaType)resolvedType);
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.withType(typeReference).readValues(jsonParser);
    }

    public <T> Iterator<T> readValues(JsonParser jsonParser, JavaType javaType) throws IOException, JsonProcessingException {
        return this.withType(javaType).readValues(jsonParser);
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, Class<T> class_) throws IOException, JsonProcessingException {
        return this.withType(class_).readValues(jsonParser);
    }

    @Override
    public JsonParser treeAsTokens(TreeNode treeNode) {
        return new TreeTraversingParser((JsonNode)treeNode, this);
    }

    @Override
    public <T> T treeToValue(TreeNode treeNode, Class<T> class_) throws JsonProcessingException {
        try {
            treeNode = this.readValue(this.treeAsTokens(treeNode), class_);
        }
        catch (JsonProcessingException var1_2) {
            throw var1_2;
        }
        catch (IOException var1_3) {
            throw new IllegalArgumentException(var1_3.getMessage(), var1_3);
        }
        return (T)treeNode;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    public ObjectReader with(Base64Variant base64Variant) {
        return this._with(this._config.with(base64Variant));
    }

    public ObjectReader with(FormatSchema formatSchema) {
        if (this._schema == formatSchema) {
            return this;
        }
        this._verifySchemaType(formatSchema);
        return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, formatSchema, this._injectableValues, this._dataFormatReaders);
    }

    public ObjectReader with(JsonFactory jsonFactory) {
        if (jsonFactory == this._parserFactory) {
            return this;
        }
        ObjectReader objectReader = new ObjectReader(this, jsonFactory);
        if (jsonFactory.getCodec() == null) {
            jsonFactory.setCodec(objectReader);
        }
        return objectReader;
    }

    public ObjectReader with(DeserializationConfig deserializationConfig) {
        return this._with(deserializationConfig);
    }

    public ObjectReader with(DeserializationFeature deserializationFeature) {
        return this._with(this._config.with(deserializationFeature));
    }

    public /* varargs */ ObjectReader with(DeserializationFeature deserializationFeature, DeserializationFeature ... arrdeserializationFeature) {
        return this._with(this._config.with(deserializationFeature, arrdeserializationFeature));
    }

    public ObjectReader with(InjectableValues injectableValues) {
        if (this._injectableValues == injectableValues) {
            return this;
        }
        return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, injectableValues, this._dataFormatReaders);
    }

    public ObjectReader with(ContextAttributes object) {
        if ((object = this._config.with((ContextAttributes)object)) == this._config) {
            return this;
        }
        return new ObjectReader(this, (DeserializationConfig)object);
    }

    public ObjectReader with(JsonNodeFactory jsonNodeFactory) {
        return this._with(this._config.with(jsonNodeFactory));
    }

    public ObjectReader with(Locale locale) {
        return this._with(this._config.with(locale));
    }

    public ObjectReader with(TimeZone timeZone) {
        return this._with(this._config.with(timeZone));
    }

    public ObjectReader withAttribute(Object object, Object object2) {
        if ((object = (DeserializationConfig)this._config.withAttribute(object, object2)) == this._config) {
            return this;
        }
        return new ObjectReader(this, (DeserializationConfig)object);
    }

    public ObjectReader withAttributes(Map<Object, Object> object) {
        if ((object = (DeserializationConfig)this._config.withAttributes((Map<Object, Object>)object)) == this._config) {
            return this;
        }
        return new ObjectReader(this, (DeserializationConfig)object);
    }

    public /* varargs */ ObjectReader withFeatures(DeserializationFeature ... arrdeserializationFeature) {
        return this._with(this._config.withFeatures(arrdeserializationFeature));
    }

    public ObjectReader withFormatDetection(DataFormatReaders dataFormatReaders) {
        return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, this._injectableValues, dataFormatReaders);
    }

    public /* varargs */ ObjectReader withFormatDetection(ObjectReader ... arrobjectReader) {
        return this.withFormatDetection(new DataFormatReaders(arrobjectReader));
    }

    public ObjectReader withHandler(DeserializationProblemHandler deserializationProblemHandler) {
        return this._with(this._config.withHandler(deserializationProblemHandler));
    }

    public ObjectReader withRootName(String string2) {
        return this._with(this._config.withRootName(string2));
    }

    public ObjectReader withType(TypeReference<?> typeReference) {
        return this.withType(this._config.getTypeFactory().constructType(typeReference.getType()));
    }

    public ObjectReader withType(JavaType javaType) {
        DataFormatReaders dataFormatReaders;
        if (javaType != null && javaType.equals(this._valueType)) {
            return this;
        }
        JsonDeserializer<Object> jsonDeserializer = this._prefetchRootDeserializer(this._config, javaType);
        DataFormatReaders dataFormatReaders2 = dataFormatReaders = this._dataFormatReaders;
        if (dataFormatReaders != null) {
            dataFormatReaders2 = dataFormatReaders.withType(javaType);
        }
        return new ObjectReader(this, this._config, javaType, jsonDeserializer, this._valueToUpdate, this._schema, this._injectableValues, dataFormatReaders2);
    }

    public ObjectReader withType(Class<?> class_) {
        return this.withType(this._config.constructType(class_));
    }

    public ObjectReader withType(Type type) {
        return this.withType(this._config.getTypeFactory().constructType(type));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectReader withValueToUpdate(Object object) {
        JavaType javaType;
        if (object == this._valueToUpdate) {
            return this;
        }
        if (object == null) {
            throw new IllegalArgumentException("cat not update null value");
        }
        if (this._valueType == null) {
            javaType = this._config.constructType(object.getClass());
            do {
                return new ObjectReader(this, this._config, javaType, this._rootDeserializer, object, this._schema, this._injectableValues, this._dataFormatReaders);
                break;
            } while (true);
        }
        javaType = this._valueType;
        return new ObjectReader(this, this._config, javaType, this._rootDeserializer, object, this._schema, this._injectableValues, this._dataFormatReaders);
    }

    public ObjectReader withView(Class<?> class_) {
        return this._with((DeserializationConfig)this._config.withView(class_));
    }

    public ObjectReader without(DeserializationFeature deserializationFeature) {
        return this._with(this._config.without(deserializationFeature));
    }

    public /* varargs */ ObjectReader without(DeserializationFeature deserializationFeature, DeserializationFeature ... arrdeserializationFeature) {
        return this._with(this._config.without(deserializationFeature, arrdeserializationFeature));
    }

    public ObjectReader withoutAttribute(Object object) {
        if ((object = (DeserializationConfig)this._config.withoutAttribute(object)) == this._config) {
            return this;
        }
        return new ObjectReader(this, (DeserializationConfig)object);
    }

    public /* varargs */ ObjectReader withoutFeatures(DeserializationFeature ... arrdeserializationFeature) {
        return this._with(this._config.withoutFeatures(arrdeserializationFeature));
    }

    @Override
    public void writeTree(JsonGenerator jsonGenerator, TreeNode treeNode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Not implemented for ObjectReader");
    }
}

