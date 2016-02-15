package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Instantiatable;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public class ObjectWriter implements Versioned, Serializable {
    protected static final PrettyPrinter NULL_PRETTY_PRINTER;
    private static final long serialVersionUID = -7040667122552707164L;
    protected final boolean _cfgBigDecimalAsPlain;
    protected final CharacterEscapes _characterEscapes;
    protected final SerializationConfig _config;
    protected final JsonFactory _generatorFactory;
    protected final PrettyPrinter _prettyPrinter;
    protected final JsonSerializer<Object> _rootSerializer;
    protected final JavaType _rootType;
    protected final FormatSchema _schema;
    protected final SerializerFactory _serializerFactory;
    protected final DefaultSerializerProvider _serializerProvider;

    static {
        NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
    }

    protected ObjectWriter(ObjectMapper mapper, SerializationConfig config, JavaType rootType, PrettyPrinter pp) {
        this._config = config;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = mapper._serializerProvider;
        this._serializerFactory = mapper._serializerFactory;
        this._generatorFactory = mapper._jsonFactory;
        this._prettyPrinter = pp;
        this._schema = null;
        this._characterEscapes = null;
        if (rootType == null || rootType.hasRawClass(Object.class)) {
            this._rootType = null;
            this._rootSerializer = null;
            return;
        }
        this._rootType = rootType.withStaticTyping();
        this._rootSerializer = _prefetchRootSerializer(config, this._rootType);
    }

    protected ObjectWriter(ObjectMapper mapper, SerializationConfig config) {
        this._config = config;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = mapper._serializerProvider;
        this._serializerFactory = mapper._serializerFactory;
        this._generatorFactory = mapper._jsonFactory;
        this._rootType = null;
        this._rootSerializer = null;
        this._prettyPrinter = null;
        this._schema = null;
        this._characterEscapes = null;
    }

    protected ObjectWriter(ObjectMapper mapper, SerializationConfig config, FormatSchema s) {
        this._config = config;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = mapper._serializerProvider;
        this._serializerFactory = mapper._serializerFactory;
        this._generatorFactory = mapper._jsonFactory;
        this._rootType = null;
        this._rootSerializer = null;
        this._prettyPrinter = null;
        this._schema = s;
        this._characterEscapes = null;
    }

    protected ObjectWriter(ObjectWriter base, SerializationConfig config, JavaType rootType, JsonSerializer<Object> rootSer, PrettyPrinter pp, FormatSchema s, CharacterEscapes escapes) {
        this._config = config;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = base._serializerProvider;
        this._serializerFactory = base._serializerFactory;
        this._generatorFactory = base._generatorFactory;
        this._rootType = rootType;
        this._rootSerializer = rootSer;
        this._prettyPrinter = pp;
        this._schema = s;
        this._characterEscapes = escapes;
    }

    protected ObjectWriter(ObjectWriter base, SerializationConfig config) {
        this._config = config;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = base._serializerProvider;
        this._serializerFactory = base._serializerFactory;
        this._generatorFactory = base._generatorFactory;
        this._schema = base._schema;
        this._characterEscapes = base._characterEscapes;
        this._rootType = base._rootType;
        this._rootSerializer = base._rootSerializer;
        this._prettyPrinter = base._prettyPrinter;
    }

    protected ObjectWriter(ObjectWriter base, JsonFactory f) {
        this._config = base._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, f.requiresPropertyOrdering());
        this._cfgBigDecimalAsPlain = base._cfgBigDecimalAsPlain;
        this._serializerProvider = base._serializerProvider;
        this._serializerFactory = base._serializerFactory;
        this._generatorFactory = base._generatorFactory;
        this._schema = base._schema;
        this._characterEscapes = base._characterEscapes;
        this._rootType = base._rootType;
        this._rootSerializer = base._rootSerializer;
        this._prettyPrinter = base._prettyPrinter;
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public ObjectWriter with(SerializationFeature feature) {
        SerializationConfig newConfig = this._config.with(feature);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(SerializationFeature first, SerializationFeature... other) {
        SerializationConfig newConfig = this._config.with(first, other);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withFeatures(SerializationFeature... features) {
        SerializationConfig newConfig = this._config.withFeatures(features);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter without(SerializationFeature feature) {
        SerializationConfig newConfig = this._config.without(feature);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter without(SerializationFeature first, SerializationFeature... other) {
        SerializationConfig newConfig = this._config.without(first, other);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withoutFeatures(SerializationFeature... features) {
        SerializationConfig newConfig = this._config.withoutFeatures(features);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(DateFormat df) {
        SerializationConfig newConfig = this._config.with(df);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withDefaultPrettyPrinter() {
        return with(new DefaultPrettyPrinter());
    }

    public ObjectWriter with(FilterProvider filterProvider) {
        return filterProvider == this._config.getFilterProvider() ? this : new ObjectWriter(this, this._config.withFilters(filterProvider));
    }

    public ObjectWriter with(PrettyPrinter pp) {
        if (pp == this._prettyPrinter) {
            return this;
        }
        if (pp == null) {
            pp = NULL_PRETTY_PRINTER;
        }
        return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, pp, this._schema, this._characterEscapes);
    }

    public ObjectWriter withRootName(String rootName) {
        SerializationConfig newConfig = this._config.withRootName(rootName);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withSchema(FormatSchema schema) {
        if (this._schema == schema) {
            return this;
        }
        _verifySchemaType(schema);
        return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, this._prettyPrinter, schema, this._characterEscapes);
    }

    public ObjectWriter withType(JavaType rootType) {
        JsonSerializer<Object> rootSer;
        if (rootType == null || rootType.hasRawClass(Object.class)) {
            rootType = null;
            rootSer = null;
        } else {
            rootType = rootType.withStaticTyping();
            rootSer = _prefetchRootSerializer(this._config, rootType);
        }
        return new ObjectWriter(this, this._config, rootType, rootSer, this._prettyPrinter, this._schema, this._characterEscapes);
    }

    public ObjectWriter withType(Class<?> rootType) {
        if (rootType == Object.class) {
            return withType((JavaType) null);
        }
        return withType(this._config.constructType((Class) rootType));
    }

    public ObjectWriter withType(TypeReference<?> rootType) {
        return withType(this._config.getTypeFactory().constructType(rootType.getType()));
    }

    public ObjectWriter withView(Class<?> view) {
        SerializationConfig newConfig = this._config.withView((Class) view);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(Locale l) {
        SerializationConfig newConfig = this._config.with(l);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(TimeZone tz) {
        SerializationConfig newConfig = this._config.with(tz);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(Base64Variant b64variant) {
        SerializationConfig newConfig = this._config.with(b64variant);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(CharacterEscapes escapes) {
        return this._characterEscapes == escapes ? this : new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, this._prettyPrinter, this._schema, escapes);
    }

    public ObjectWriter with(JsonFactory f) {
        return f == this._generatorFactory ? this : new ObjectWriter(this, f);
    }

    public ObjectWriter with(ContextAttributes attrs) {
        SerializationConfig newConfig = this._config.with(attrs);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withAttributes(Map<Object, Object> attrs) {
        SerializationConfig newConfig = (SerializationConfig) this._config.withAttributes(attrs);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withAttribute(Object key, Object value) {
        SerializationConfig newConfig = (SerializationConfig) this._config.withAttribute(key, value);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withoutAttribute(Object key) {
        SerializationConfig newConfig = (SerializationConfig) this._config.withoutAttribute(key);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public boolean isEnabled(SerializationFeature f) {
        return this._config.isEnabled(f);
    }

    public boolean isEnabled(MapperFeature f) {
        return this._config.isEnabled(f);
    }

    public boolean isEnabled(Feature f) {
        return this._generatorFactory.isEnabled(f);
    }

    public SerializationConfig getConfig() {
        return this._config;
    }

    @Deprecated
    public JsonFactory getJsonFactory() {
        return this._generatorFactory;
    }

    public JsonFactory getFactory() {
        return this._generatorFactory;
    }

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public boolean hasPrefetchedSerializer() {
        return this._rootSerializer != null;
    }

    public ContextAttributes getAttributes() {
        return this._config.getAttributes();
    }

    public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        _configureJsonGenerator(jgen);
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _writeCloseableValue(jgen, value, this._config);
            return;
        }
        if (this._rootType == null) {
            _serializerProvider(this._config).serializeValue(jgen, value);
        } else {
            _serializerProvider(this._config).serializeValue(jgen, value, this._rootType, this._rootSerializer);
        }
        if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            jgen.flush();
        }
    }

    public void writeValue(File resultFile, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._generatorFactory.createGenerator(resultFile, JsonEncoding.UTF8), value);
    }

    public void writeValue(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._generatorFactory.createGenerator(out, JsonEncoding.UTF8), value);
    }

    public void writeValue(Writer w, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._generatorFactory.createGenerator(w), value);
    }

    public String writeValueAsString(Object value) throws JsonProcessingException {
        Writer sw = new SegmentedStringWriter(this._generatorFactory._getBufferRecycler());
        try {
            _configAndWriteValue(this._generatorFactory.createGenerator(sw), value);
            return sw.getAndClear();
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        OutputStream bb = new ByteArrayBuilder(this._generatorFactory._getBufferRecycler());
        try {
            _configAndWriteValue(this._generatorFactory.createGenerator(bb, JsonEncoding.UTF8), value);
            byte[] result = bb.toByteArray();
            bb.release();
            return result;
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    public void acceptJsonFormatVisitor(JavaType type, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
        if (type == null) {
            throw new IllegalArgumentException("type must be provided");
        }
        _serializerProvider(this._config).acceptJsonFormatVisitor(type, visitor);
    }

    public boolean canSerialize(Class<?> type) {
        return _serializerProvider(this._config).hasSerializerFor(type, null);
    }

    public boolean canSerialize(Class<?> type, AtomicReference<Throwable> cause) {
        return _serializerProvider(this._config).hasSerializerFor(type, cause);
    }

    protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
        return this._serializerProvider.createInstance(config, this._serializerFactory);
    }

    protected void _verifySchemaType(FormatSchema schema) {
        if (schema != null && !this._generatorFactory.canUseSchema(schema)) {
            throw new IllegalArgumentException("Can not use FormatSchema of type " + schema.getClass().getName() + " for format " + this._generatorFactory.getFormatName());
        }
    }

    protected final void _configAndWriteValue(JsonGenerator jgen, Object value) throws IOException {
        _configureJsonGenerator(jgen);
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _writeCloseable(jgen, value, this._config);
            return;
        }
        try {
            if (this._rootType == null) {
                _serializerProvider(this._config).serializeValue(jgen, value);
            } else {
                _serializerProvider(this._config).serializeValue(jgen, value, this._rootType, this._rootSerializer);
            }
            jgen.close();
            if (!true) {
                jgen.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
                try {
                    jgen.close();
                } catch (IOException e) {
                }
            }
        } catch (Throwable th) {
            if (!false) {
                jgen.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
                try {
                    jgen.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    private final void _writeCloseable(JsonGenerator jgen, Object value, SerializationConfig cfg) throws IOException, JsonGenerationException, JsonMappingException {
        Closeable toClose = (Closeable) value;
        try {
            if (this._rootType == null) {
                _serializerProvider(cfg).serializeValue(jgen, value);
            } else {
                _serializerProvider(cfg).serializeValue(jgen, value, this._rootType, this._rootSerializer);
            }
            JsonGenerator tmpJgen = jgen;
            jgen = null;
            tmpJgen.close();
            Closeable tmpToClose = toClose;
            toClose = null;
            tmpToClose.close();
            if (jgen != null) {
                jgen.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
                try {
                    jgen.close();
                } catch (IOException e) {
                }
            }
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e2) {
                }
            }
        } catch (Throwable th) {
            if (jgen != null) {
                jgen.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
                try {
                    jgen.close();
                } catch (IOException e3) {
                }
            }
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e4) {
                }
            }
        }
    }

    private final void _writeCloseableValue(JsonGenerator jgen, Object value, SerializationConfig cfg) throws IOException, JsonGenerationException, JsonMappingException {
        Closeable toClose = (Closeable) value;
        try {
            if (this._rootType == null) {
                _serializerProvider(cfg).serializeValue(jgen, value);
            } else {
                _serializerProvider(cfg).serializeValue(jgen, value, this._rootType, this._rootSerializer);
            }
            if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jgen.flush();
            }
            Closeable tmpToClose = toClose;
            toClose = null;
            tmpToClose.close();
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e) {
                }
            }
        } catch (Throwable th) {
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    protected JsonSerializer<Object> _prefetchRootSerializer(SerializationConfig config, JavaType valueType) {
        JsonSerializer<Object> jsonSerializer = null;
        if (valueType != null && this._config.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH)) {
            try {
                jsonSerializer = _serializerProvider(config).findTypedValueSerializer(valueType, true, null);
            } catch (JsonProcessingException e) {
            }
        }
        return jsonSerializer;
    }

    private void _configureJsonGenerator(JsonGenerator jgen) {
        if (this._prettyPrinter != null) {
            PrettyPrinter pp = this._prettyPrinter;
            if (pp == NULL_PRETTY_PRINTER) {
                jgen.setPrettyPrinter(null);
            } else {
                if (pp instanceof Instantiatable) {
                    pp = (PrettyPrinter) ((Instantiatable) pp).createInstance();
                }
                jgen.setPrettyPrinter(pp);
            }
        } else if (this._config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jgen.useDefaultPrettyPrinter();
        }
        if (this._characterEscapes != null) {
            jgen.setCharacterEscapes(this._characterEscapes);
        }
        if (this._schema != null) {
            jgen.setSchema(this._schema);
        }
        if (this._cfgBigDecimalAsPlain) {
            jgen.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        }
    }
}
