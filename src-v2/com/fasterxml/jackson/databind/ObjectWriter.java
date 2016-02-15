/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Instantiatable;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
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
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public class ObjectWriter
implements Versioned,
Serializable {
    protected static final PrettyPrinter NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
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

    protected ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig) {
        this._config = serializationConfig;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._rootType = null;
        this._rootSerializer = null;
        this._prettyPrinter = null;
        this._schema = null;
        this._characterEscapes = null;
    }

    protected ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig, FormatSchema formatSchema) {
        this._config = serializationConfig;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._rootType = null;
        this._rootSerializer = null;
        this._prettyPrinter = null;
        this._schema = formatSchema;
        this._characterEscapes = null;
    }

    protected ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig, JavaType javaType, PrettyPrinter prettyPrinter) {
        this._config = serializationConfig;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._generatorFactory = objectMapper._jsonFactory;
        this._prettyPrinter = prettyPrinter;
        this._schema = null;
        this._characterEscapes = null;
        if (javaType == null || javaType.hasRawClass(Object.class)) {
            this._rootType = null;
            this._rootSerializer = null;
            return;
        }
        this._rootType = javaType.withStaticTyping();
        this._rootSerializer = this._prefetchRootSerializer(serializationConfig, this._rootType);
    }

    protected ObjectWriter(ObjectWriter objectWriter, JsonFactory jsonFactory) {
        this._config = objectWriter._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, jsonFactory.requiresPropertyOrdering());
        this._cfgBigDecimalAsPlain = objectWriter._cfgBigDecimalAsPlain;
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = objectWriter._generatorFactory;
        this._schema = objectWriter._schema;
        this._characterEscapes = objectWriter._characterEscapes;
        this._rootType = objectWriter._rootType;
        this._rootSerializer = objectWriter._rootSerializer;
        this._prettyPrinter = objectWriter._prettyPrinter;
    }

    protected ObjectWriter(ObjectWriter objectWriter, SerializationConfig serializationConfig) {
        this._config = serializationConfig;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = objectWriter._generatorFactory;
        this._schema = objectWriter._schema;
        this._characterEscapes = objectWriter._characterEscapes;
        this._rootType = objectWriter._rootType;
        this._rootSerializer = objectWriter._rootSerializer;
        this._prettyPrinter = objectWriter._prettyPrinter;
    }

    protected ObjectWriter(ObjectWriter objectWriter, SerializationConfig serializationConfig, JavaType javaType, JsonSerializer<Object> jsonSerializer, PrettyPrinter prettyPrinter, FormatSchema formatSchema, CharacterEscapes characterEscapes) {
        this._config = serializationConfig;
        this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        this._serializerProvider = objectWriter._serializerProvider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._generatorFactory = objectWriter._generatorFactory;
        this._rootType = javaType;
        this._rootSerializer = jsonSerializer;
        this._prettyPrinter = prettyPrinter;
        this._schema = formatSchema;
        this._characterEscapes = characterEscapes;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _configureJsonGenerator(JsonGenerator jsonGenerator) {
        if (this._prettyPrinter != null) {
            PrettyPrinter prettyPrinter = this._prettyPrinter;
            if (prettyPrinter == NULL_PRETTY_PRINTER) {
                jsonGenerator.setPrettyPrinter(null);
            } else {
                PrettyPrinter prettyPrinter2 = prettyPrinter;
                if (prettyPrinter instanceof Instantiatable) {
                    prettyPrinter2 = (PrettyPrinter)((Instantiatable)((Object)prettyPrinter)).createInstance();
                }
                jsonGenerator.setPrettyPrinter(prettyPrinter2);
            }
        } else if (this._config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (this._characterEscapes != null) {
            jsonGenerator.setCharacterEscapes(this._characterEscapes);
        }
        if (this._schema != null) {
            jsonGenerator.setSchema(this._schema);
        }
        if (this._cfgBigDecimalAsPlain) {
            jsonGenerator.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void _writeCloseable(JsonGenerator var1_1, Object var2_4, SerializationConfig var3_7) throws IOException, JsonGenerationException, JsonMappingException {
        block12 : {
            var4_9 = var6_8 = (Closeable)var2_4;
            var5_10 = var1_1;
            try {
                if (this._rootType == null) {
                    var4_9 = var6_8;
                    var5_10 = var1_1;
                    this._serializerProvider(var3_7).serializeValue((JsonGenerator)var1_1, var2_4);
                } else {
                    var4_9 = var6_8;
                    var5_10 = var1_1;
                    this._serializerProvider(var3_7).serializeValue((JsonGenerator)var1_1, var2_4, this._rootType, this._rootSerializer);
                }
                var2_4 = null;
                var4_9 = var6_8;
                var5_10 = var2_4;
                var1_1.close();
                var4_9 = null;
                var5_10 = var2_4;
                var6_8.close();
                if (!false) break block12;
                var1_1 = JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT;
            }
            catch (Throwable var1_2) {
                if (var5_10 != null) {
                    var5_10.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
                    var5_10.close();
                }
                ** GOTO lbl29
                catch (IOException var2_5) {}
lbl29: // 2 sources:
                if (var4_9 == null) throw var1_2;
                try {
                    var4_9.close();
                }
                catch (IOException var2_6) {
                    throw var1_2;
                }
                throw var1_2;
            }
            throw new NullPointerException();
        }
        if (false == false) return;
        throw new NullPointerException();
        {
            catch (IOException var1_3) {
                return;
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void _writeCloseableValue(JsonGenerator jsonGenerator, Object object, SerializationConfig serializationConfig) throws IOException, JsonGenerationException, JsonMappingException {
        Closeable closeable;
        Closeable closeable2 = closeable = (Closeable)object;
        try {
            if (this._rootType == null) {
                closeable2 = closeable;
                this._serializerProvider(serializationConfig).serializeValue(jsonGenerator, object);
            } else {
                closeable2 = closeable;
                this._serializerProvider(serializationConfig).serializeValue(jsonGenerator, object, this._rootType, this._rootSerializer);
            }
            closeable2 = closeable;
            if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                closeable2 = closeable;
                jsonGenerator.flush();
            }
            closeable2 = null;
            closeable.close();
            if (!false) return;
        }
        catch (Throwable var1_2) {
            if (closeable2 == null) throw var1_2;
            try {
                closeable2.close();
            }
            catch (IOException var2_5) {
                throw var1_2;
            }
            throw var1_2;
        }
        throw new NullPointerException();
        {
            catch (IOException iOException) {
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _configAndWriteValue(JsonGenerator jsonGenerator, Object object) throws IOException {
        boolean bl2;
        this._configureJsonGenerator(jsonGenerator);
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            this._writeCloseable(jsonGenerator, object, this._config);
            return;
        }
        boolean bl3 = bl2 = false;
        try {
            if (this._rootType == null) {
                bl3 = bl2;
                this._serializerProvider(this._config).serializeValue(jsonGenerator, object);
            } else {
                bl3 = bl2;
                this._serializerProvider(this._config).serializeValue(jsonGenerator, object, this._rootType, this._rootSerializer);
            }
            bl3 = true;
            jsonGenerator.close();
            if (true) return;
            jsonGenerator.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
        }
        catch (Throwable var2_5) {
            if (bl3) throw var2_5;
            jsonGenerator.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
            try {
                jsonGenerator.close();
            }
            catch (IOException var1_3) {
                throw var2_5;
            }
            throw var2_5;
        }
        try {
            jsonGenerator.close();
            return;
        }
        catch (IOException var1_2) {
            return;
        }
    }

    protected JsonSerializer<Object> _prefetchRootSerializer(SerializationConfig object, JavaType javaType) {
        if (javaType == null || !this._config.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH)) {
            return null;
        }
        try {
            object = this._serializerProvider((SerializationConfig)object).findTypedValueSerializer(javaType, true, null);
            return object;
        }
        catch (JsonProcessingException var1_2) {
            return null;
        }
    }

    protected DefaultSerializerProvider _serializerProvider(SerializationConfig serializationConfig) {
        return this._serializerProvider.createInstance(serializationConfig, this._serializerFactory);
    }

    protected void _verifySchemaType(FormatSchema formatSchema) {
        if (formatSchema != null && !this._generatorFactory.canUseSchema(formatSchema)) {
            throw new IllegalArgumentException("Can not use FormatSchema of type " + formatSchema.getClass().getName() + " for format " + this._generatorFactory.getFormatName());
        }
    }

    public void acceptJsonFormatVisitor(JavaType javaType, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType == null) {
            throw new IllegalArgumentException("type must be provided");
        }
        this._serializerProvider(this._config).acceptJsonFormatVisitor(javaType, jsonFormatVisitorWrapper);
    }

    public boolean canSerialize(Class<?> class_) {
        return this._serializerProvider(this._config).hasSerializerFor(class_, null);
    }

    public boolean canSerialize(Class<?> class_, AtomicReference<Throwable> atomicReference) {
        return this._serializerProvider(this._config).hasSerializerFor(class_, atomicReference);
    }

    public ContextAttributes getAttributes() {
        return this._config.getAttributes();
    }

    public SerializationConfig getConfig() {
        return this._config;
    }

    public JsonFactory getFactory() {
        return this._generatorFactory;
    }

    @Deprecated
    public JsonFactory getJsonFactory() {
        return this._generatorFactory;
    }

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public boolean hasPrefetchedSerializer() {
        if (this._rootSerializer != null) {
            return true;
        }
        return false;
    }

    public boolean isEnabled(JsonParser.Feature feature) {
        return this._generatorFactory.isEnabled(feature);
    }

    public boolean isEnabled(MapperFeature mapperFeature) {
        return this._config.isEnabled(mapperFeature);
    }

    public boolean isEnabled(SerializationFeature serializationFeature) {
        return this._config.isEnabled(serializationFeature);
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    public ObjectWriter with(Base64Variant serializable) {
        if ((serializable = this._config.with((Base64Variant)serializable)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)serializable);
    }

    public ObjectWriter with(JsonFactory jsonFactory) {
        if (jsonFactory == this._generatorFactory) {
            return this;
        }
        return new ObjectWriter(this, jsonFactory);
    }

    public ObjectWriter with(PrettyPrinter prettyPrinter) {
        if (prettyPrinter == this._prettyPrinter) {
            return this;
        }
        PrettyPrinter prettyPrinter2 = prettyPrinter;
        if (prettyPrinter == null) {
            prettyPrinter2 = NULL_PRETTY_PRINTER;
        }
        return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, prettyPrinter2, this._schema, this._characterEscapes);
    }

    public ObjectWriter with(CharacterEscapes characterEscapes) {
        if (this._characterEscapes == characterEscapes) {
            return this;
        }
        return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, this._prettyPrinter, this._schema, characterEscapes);
    }

    public ObjectWriter with(SerializationFeature object) {
        if ((object = this._config.with((SerializationFeature)object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public /* varargs */ ObjectWriter with(SerializationFeature object, SerializationFeature ... arrserializationFeature) {
        if ((object = this._config.with((SerializationFeature)object, arrserializationFeature)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public ObjectWriter with(ContextAttributes object) {
        if ((object = this._config.with((ContextAttributes)object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public ObjectWriter with(FilterProvider filterProvider) {
        if (filterProvider == this._config.getFilterProvider()) {
            return this;
        }
        return new ObjectWriter(this, this._config.withFilters(filterProvider));
    }

    public ObjectWriter with(DateFormat serializable) {
        if ((serializable = this._config.with((DateFormat)serializable)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)serializable);
    }

    public ObjectWriter with(Locale serializable) {
        if ((serializable = this._config.with((Locale)serializable)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)serializable);
    }

    public ObjectWriter with(TimeZone serializable) {
        if ((serializable = this._config.with((TimeZone)serializable)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)serializable);
    }

    public ObjectWriter withAttribute(Object object, Object object2) {
        if ((object = (SerializationConfig)this._config.withAttribute(object, object2)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public ObjectWriter withAttributes(Map<Object, Object> object) {
        if ((object = (SerializationConfig)this._config.withAttributes((Map<Object, Object>)object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public ObjectWriter withDefaultPrettyPrinter() {
        return this.with(new DefaultPrettyPrinter());
    }

    public /* varargs */ ObjectWriter withFeatures(SerializationFeature ... object) {
        if ((object = this._config.withFeatures((SerializationFeature[])object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public ObjectWriter withRootName(String object) {
        if ((object = this._config.withRootName((String)object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public ObjectWriter withSchema(FormatSchema formatSchema) {
        if (this._schema == formatSchema) {
            return this;
        }
        this._verifySchemaType(formatSchema);
        return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, this._prettyPrinter, formatSchema, this._characterEscapes);
    }

    public ObjectWriter withType(TypeReference<?> typeReference) {
        return this.withType(this._config.getTypeFactory().constructType(typeReference.getType()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectWriter withType(JavaType jsonSerializer) {
        JavaType javaType;
        if (jsonSerializer == null || jsonSerializer.hasRawClass(Object.class)) {
            javaType = null;
            jsonSerializer = null;
            do {
                return new ObjectWriter(this, this._config, javaType, jsonSerializer, this._prettyPrinter, this._schema, this._characterEscapes);
                break;
            } while (true);
        }
        javaType = jsonSerializer.withStaticTyping();
        jsonSerializer = this._prefetchRootSerializer(this._config, javaType);
        return new ObjectWriter(this, this._config, javaType, jsonSerializer, this._prettyPrinter, this._schema, this._characterEscapes);
    }

    public ObjectWriter withType(Class<?> class_) {
        if (class_ == Object.class) {
            return this.withType((JavaType)null);
        }
        return this.withType(this._config.constructType(class_));
    }

    public ObjectWriter withView(Class<?> serializable) {
        if ((serializable = this._config.withView((Class)serializable)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)serializable);
    }

    public ObjectWriter without(SerializationFeature object) {
        if ((object = this._config.without((SerializationFeature)object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public /* varargs */ ObjectWriter without(SerializationFeature object, SerializationFeature ... arrserializationFeature) {
        if ((object = this._config.without((SerializationFeature)object, arrserializationFeature)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public ObjectWriter withoutAttribute(Object object) {
        if ((object = (SerializationConfig)this._config.withoutAttribute(object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    public /* varargs */ ObjectWriter withoutFeatures(SerializationFeature ... object) {
        if ((object = this._config.withoutFeatures((SerializationFeature[])object)) == this._config) {
            return this;
        }
        return new ObjectWriter(this, (SerializationConfig)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configureJsonGenerator(jsonGenerator);
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            this._writeCloseableValue(jsonGenerator, object, this._config);
            return;
        } else {
            if (this._rootType == null) {
                this._serializerProvider(this._config).serializeValue(jsonGenerator, object);
            } else {
                this._serializerProvider(this._config).serializeValue(jsonGenerator, object, this._rootType, this._rootSerializer);
            }
            if (!this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) return;
            {
                jsonGenerator.flush();
                return;
            }
        }
    }

    public void writeValue(File file, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._generatorFactory.createGenerator(file, JsonEncoding.UTF8), object);
    }

    public void writeValue(OutputStream outputStream, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._generatorFactory.createGenerator(outputStream, JsonEncoding.UTF8), object);
    }

    public void writeValue(Writer writer, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._generatorFactory.createGenerator(writer), object);
    }

    public byte[] writeValueAsBytes(Object object) throws JsonProcessingException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(this._generatorFactory._getBufferRecycler());
        try {
            this._configAndWriteValue(this._generatorFactory.createGenerator(byteArrayBuilder, JsonEncoding.UTF8), object);
        }
        catch (JsonProcessingException var1_2) {
            throw var1_2;
        }
        catch (IOException var1_3) {
            throw JsonMappingException.fromUnexpectedIOE(var1_3);
        }
        object = byteArrayBuilder.toByteArray();
        byteArrayBuilder.release();
        return object;
    }

    public String writeValueAsString(Object object) throws JsonProcessingException {
        SegmentedStringWriter segmentedStringWriter = new SegmentedStringWriter(this._generatorFactory._getBufferRecycler());
        try {
            this._configAndWriteValue(this._generatorFactory.createGenerator(segmentedStringWriter), object);
            return segmentedStringWriter.getAndClear();
        }
        catch (JsonProcessingException var1_2) {
            throw var1_2;
        }
        catch (IOException var1_3) {
            throw JsonMappingException.fromUnexpectedIOE(var1_3);
        }
    }
}

