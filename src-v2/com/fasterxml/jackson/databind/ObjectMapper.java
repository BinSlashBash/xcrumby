/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
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
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ObjectMapper
extends ObjectCodec
implements Versioned,
Serializable {
    protected static final AnnotationIntrospector DEFAULT_ANNOTATION_INTROSPECTOR;
    protected static final BaseSettings DEFAULT_BASE;
    protected static final ClassIntrospector DEFAULT_INTROSPECTOR;
    private static final JavaType JSON_NODE_TYPE;
    protected static final VisibilityChecker<?> STD_VISIBILITY_CHECKER;
    protected static final PrettyPrinter _defaultPrettyPrinter;
    private static final long serialVersionUID = 1;
    protected DeserializationConfig _deserializationConfig;
    protected DefaultDeserializationContext _deserializationContext;
    protected InjectableValues _injectableValues;
    protected final JsonFactory _jsonFactory;
    protected final HashMap<ClassKey, Class<?>> _mixInAnnotations;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers = new ConcurrentHashMap(64, 0.6f, 2);
    protected final RootNameLookup _rootNames;
    protected SerializationConfig _serializationConfig;
    protected SerializerFactory _serializerFactory;
    protected DefaultSerializerProvider _serializerProvider;
    protected SubtypeResolver _subtypeResolver;
    protected TypeFactory _typeFactory;

    static {
        JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
        DEFAULT_INTROSPECTOR = BasicClassIntrospector.instance;
        DEFAULT_ANNOTATION_INTROSPECTOR = new JacksonAnnotationIntrospector();
        STD_VISIBILITY_CHECKER = VisibilityChecker.Std.defaultInstance();
        _defaultPrettyPrinter = new DefaultPrettyPrinter();
        DEFAULT_BASE = new BaseSettings(DEFAULT_INTROSPECTOR, DEFAULT_ANNOTATION_INTROSPECTOR, STD_VISIBILITY_CHECKER, null, TypeFactory.defaultInstance(), null, StdDateFormat.instance, null, Locale.getDefault(), TimeZone.getTimeZone("GMT"), Base64Variants.getDefaultVariant());
    }

    public ObjectMapper() {
        this(null, null, null);
    }

    public ObjectMapper(JsonFactory jsonFactory) {
        this(jsonFactory, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectMapper(JsonFactory jsonFactory, DefaultSerializerProvider defaultSerializerProvider, DefaultDeserializationContext defaultDeserializationContext) {
        void var1_8;
        HashMap hashMap;
        void var3_10;
        void var1_5;
        void var2_9;
        if (jsonFactory == null) {
            this._jsonFactory = new MappingJsonFactory(this);
        } else {
            this._jsonFactory = jsonFactory;
            if (jsonFactory.getCodec() == null) {
                this._jsonFactory.setCodec(this);
            }
        }
        this._subtypeResolver = new StdSubtypeResolver();
        this._rootNames = new RootNameLookup();
        this._typeFactory = TypeFactory.defaultInstance();
        this._mixInAnnotations = hashMap = new HashMap();
        this._serializationConfig = new SerializationConfig(DEFAULT_BASE, this._subtypeResolver, hashMap);
        this._deserializationConfig = new DeserializationConfig(DEFAULT_BASE, this._subtypeResolver, hashMap);
        boolean bl2 = this._jsonFactory.requiresPropertyOrdering();
        if (this._serializationConfig.isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY) ^ bl2) {
            this.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, bl2);
        }
        void var1_3 = var2_9;
        if (var2_9 == null) {
            DefaultSerializerProvider.Impl impl = new DefaultSerializerProvider.Impl();
        }
        this._serializerProvider = var1_5;
        void var1_6 = var3_10;
        if (var3_10 == null) {
            DefaultDeserializationContext.Impl impl = new DefaultDeserializationContext.Impl(BeanDeserializerFactory.instance);
        }
        this._deserializationContext = var1_8;
        this._serializerFactory = BeanSerializerFactory.instance;
    }

    protected ObjectMapper(ObjectMapper objectMapper) {
        HashMap hashMap;
        this._jsonFactory = objectMapper._jsonFactory.copy();
        this._jsonFactory.setCodec(this);
        this._subtypeResolver = objectMapper._subtypeResolver;
        this._rootNames = new RootNameLookup();
        this._typeFactory = objectMapper._typeFactory;
        this._serializationConfig = objectMapper._serializationConfig;
        this._mixInAnnotations = hashMap = new HashMap(objectMapper._mixInAnnotations);
        this._serializationConfig = new SerializationConfig(objectMapper._serializationConfig, hashMap);
        this._deserializationConfig = new DeserializationConfig(objectMapper._deserializationConfig, hashMap);
        this._serializerProvider = objectMapper._serializerProvider;
        this._deserializationContext = objectMapper._deserializationContext;
        this._serializerFactory = objectMapper._serializerFactory;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void _configAndWriteCloseable(JsonGenerator var1_1, Object var2_4, SerializationConfig var3_7) throws IOException, JsonGenerationException, JsonMappingException {
        block9 : {
            var4_9 = var6_8 = (Closeable)var2_4;
            var5_10 = var1_1;
            try {
                this._serializerProvider(var3_7).serializeValue((JsonGenerator)var1_1, var2_4);
                var2_4 = null;
                var4_9 = var6_8;
                var5_10 = var2_4;
                var1_1.close();
                var4_9 = null;
                var5_10 = var2_4;
                var6_8.close();
                if (!false) break block9;
                var1_1 = JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT;
            }
            catch (Throwable var1_2) {
                if (var5_10 != null) {
                    var5_10.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
                    var5_10.close();
                }
                ** GOTO lbl21
                catch (IOException var2_5) {}
lbl21: // 2 sources:
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
        Closeable closeable2;
        Closeable closeable = closeable2 = (Closeable)object;
        try {
            this._serializerProvider(serializationConfig).serializeValue(jsonGenerator, object);
            closeable = closeable2;
            if (serializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                closeable = closeable2;
                jsonGenerator.flush();
            }
            closeable = null;
            closeable2.close();
            if (!false) return;
        }
        catch (Throwable var1_2) {
            if (closeable == null) throw var1_2;
            try {
                closeable.close();
            }
            catch (IOException iOException) {
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

    public static List<Module> findModules() {
        return ObjectMapper.findModules(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static List<Module> findModules(ClassLoader serviceLoader) {
        ArrayList<Module> arrayList = new ArrayList<Module>();
        serviceLoader = serviceLoader == null ? ServiceLoader.load(Module.class) : ServiceLoader.load(Module.class, serviceLoader);
        serviceLoader = serviceLoader.iterator();
        while (serviceLoader.hasNext()) {
            arrayList.add((Module)serviceLoader.next());
        }
        return arrayList;
    }

    protected void _checkInvalidCopy(Class<?> class_) {
        if (this.getClass() != class_) {
            throw new IllegalStateException("Failed copy(): " + this.getClass().getName() + " (version: " + this.version() + ") does not override copy(); it has to");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _configAndWriteValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig serializationConfig = this.getSerializationConfig();
        if (serializationConfig.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (serializationConfig.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN)) {
            jsonGenerator.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        }
        if (serializationConfig.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            this._configAndWriteCloseable(jsonGenerator, object, serializationConfig);
            return;
        }
        boolean bl2 = false;
        try {
            this._serializerProvider(serializationConfig).serializeValue(jsonGenerator, object);
            bl2 = true;
            jsonGenerator.close();
            if (true) return;
            jsonGenerator.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
        }
        catch (Throwable var2_5) {
            if (bl2) throw var2_5;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _configAndWriteValue(JsonGenerator jsonGenerator, Object object, Class<?> serializable) throws IOException, JsonGenerationException, JsonMappingException {
        MapperConfigBase mapperConfigBase = this.getSerializationConfig().withView((Class)serializable);
        if (mapperConfigBase.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (mapperConfigBase.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN)) {
            jsonGenerator.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        }
        if (mapperConfigBase.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            this._configAndWriteCloseable(jsonGenerator, object, (SerializationConfig)mapperConfigBase);
            return;
        }
        boolean bl2 = false;
        try {
            this._serializerProvider((SerializationConfig)mapperConfigBase).serializeValue(jsonGenerator, object);
            bl2 = true;
            jsonGenerator.close();
            if (true) return;
            jsonGenerator.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
        }
        catch (Throwable var2_5) {
            if (bl2) throw var2_5;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object _convert(Object object, JavaType javaType) throws IllegalArgumentException {
        Class class_;
        block4 : {
            block5 : {
                class_ = javaType.getRawClass();
                if (class_ != Object.class && !javaType.hasGenericTypes() && class_.isAssignableFrom(object.getClass())) {
                    return object;
                }
                class_ = new TokenBuffer(this, false);
                try {
                    this._serializerProvider(this.getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE)).serializeValue((JsonGenerator)((Object)class_), object);
                    class_ = class_.asParser();
                    object = this.getDeserializationConfig();
                    JsonToken jsonToken = this._initForReading((JsonParser)((Object)class_));
                    if (jsonToken == JsonToken.VALUE_NULL) {
                        object = this._findRootDeserializer(this.createDeserializationContext((JsonParser)((Object)class_), (DeserializationConfig)object), javaType).getNullValue();
                        break block4;
                    }
                    if (jsonToken == JsonToken.END_ARRAY || jsonToken == JsonToken.END_OBJECT) break block5;
                    object = this.createDeserializationContext((JsonParser)((Object)class_), (DeserializationConfig)object);
                    object = this._findRootDeserializer((DeserializationContext)object, javaType).deserialize((JsonParser)((Object)class_), (DeserializationContext)object);
                    break block4;
                }
                catch (IOException var1_2) {
                    throw new IllegalArgumentException(var1_2.getMessage(), var1_2);
                }
            }
            object = null;
        }
        class_.close();
        return object;
    }

    protected PrettyPrinter _defaultPrettyPrinter() {
        return _defaultPrettyPrinter;
    }

    protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext object, JavaType javaType) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer = this._rootDeserializers.get(javaType);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        if ((object = object.findRootValueDeserializer(javaType)) == null) {
            throw new JsonMappingException("Can not find a deserializer for type " + javaType);
        }
        this._rootDeserializers.put(javaType, (JsonDeserializer<Object>)object);
        return object;
    }

    protected JsonToken _initForReading(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
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
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object _readMapAndClose(JsonParser jsonParser, JavaType object) throws IOException, JsonParseException, JsonMappingException {
        block11 : {
            block12 : {
                try {
                    Object object2 = this._initForReading(jsonParser);
                    if (object2 == JsonToken.VALUE_NULL) {
                        object = this._findRootDeserializer(this.createDeserializationContext(jsonParser, this.getDeserializationConfig()), (JavaType)object).getNullValue();
                        break block11;
                    }
                    if (object2 == JsonToken.END_ARRAY || object2 == JsonToken.END_OBJECT) break block12;
                    DeserializationConfig deserializationConfig = this.getDeserializationConfig();
                    object2 = this.createDeserializationContext(jsonParser, deserializationConfig);
                    JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer((DeserializationContext)object2, (JavaType)object);
                    if (deserializationConfig.useRootWrapping()) {
                        object = this._unwrapAndDeserialize(jsonParser, (DeserializationContext)object2, deserializationConfig, (JavaType)object, jsonDeserializer);
                    } else {
                        object = jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object2);
                    }
                    object2.checkUnresolvedObjectId();
                    break block11;
                }
                catch (Throwable var2_5) {
                    try {
                        jsonParser.close();
                    }
                    catch (IOException var1_3) {
                        throw var2_5;
                    }
                    throw var2_5;
                }
            }
            object = null;
        }
        jsonParser.clearCurrentToken();
        try {
            jsonParser.close();
            return object;
        }
        catch (IOException var1_2) {
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _readValue(DeserializationConfig object, JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        Object object2 = this._initForReading(jsonParser);
        if (object2 == JsonToken.VALUE_NULL) {
            object = this._findRootDeserializer(this.createDeserializationContext(jsonParser, (DeserializationConfig)object), javaType).getNullValue();
        } else if (object2 == JsonToken.END_ARRAY || object2 == JsonToken.END_OBJECT) {
            object = null;
        } else {
            object2 = this.createDeserializationContext(jsonParser, (DeserializationConfig)object);
            JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer((DeserializationContext)object2, javaType);
            object = object.useRootWrapping() ? this._unwrapAndDeserialize(jsonParser, (DeserializationContext)object2, (DeserializationConfig)object, javaType, jsonDeserializer) : jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object2);
        }
        jsonParser.clearCurrentToken();
        return object;
    }

    protected DefaultSerializerProvider _serializerProvider(SerializationConfig serializationConfig) {
        return this._serializerProvider.createInstance(serializationConfig, this._serializerFactory);
    }

    protected Object _unwrapAndDeserialize(JsonParser jsonParser, DeserializationContext object, DeserializationConfig object2, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) throws IOException, JsonParseException, JsonMappingException {
        String string2;
        String string3 = string2 = object2.getRootName();
        if (string2 == null) {
            string3 = this._rootNames.findRootName(javaType, object2).getSimpleName();
        }
        if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not START_OBJECT (needed to unwrap root name '" + string3 + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        if (jsonParser.nextToken() != JsonToken.FIELD_NAME) {
            throw JsonMappingException.from(jsonParser, "Current token not FIELD_NAME (to contain expected root name '" + string3 + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        object2 = jsonParser.getCurrentName();
        if (!string3.equals(object2)) {
            throw JsonMappingException.from(jsonParser, "Root name '" + (String)object2 + "' does not match expected ('" + string3 + "') for type " + javaType);
        }
        jsonParser.nextToken();
        object = jsonDeserializer.deserialize(jsonParser, (DeserializationContext)object);
        if (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + string3 + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        return object;
    }

    protected void _verifySchemaType(FormatSchema formatSchema) {
        if (formatSchema != null && !this._jsonFactory.canUseSchema(formatSchema)) {
            throw new IllegalArgumentException("Can not use FormatSchema of type " + formatSchema.getClass().getName() + " for format " + this._jsonFactory.getFormatName());
        }
    }

    public void acceptJsonFormatVisitor(JavaType javaType, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType == null) {
            throw new IllegalArgumentException("type must be provided");
        }
        this._serializerProvider(this.getSerializationConfig()).acceptJsonFormatVisitor(javaType, jsonFormatVisitorWrapper);
    }

    public void acceptJsonFormatVisitor(Class<?> class_, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        this.acceptJsonFormatVisitor(this._typeFactory.constructType(class_), jsonFormatVisitorWrapper);
    }

    public ObjectMapper addHandler(DeserializationProblemHandler deserializationProblemHandler) {
        this._deserializationConfig = this._deserializationConfig.withHandler(deserializationProblemHandler);
        return this;
    }

    public final void addMixInAnnotations(Class<?> class_, Class<?> class_2) {
        this._mixInAnnotations.put(new ClassKey(class_), class_2);
    }

    public boolean canDeserialize(JavaType javaType) {
        return this.createDeserializationContext(null, this.getDeserializationConfig()).hasValueDeserializerFor(javaType, null);
    }

    public boolean canDeserialize(JavaType javaType, AtomicReference<Throwable> atomicReference) {
        return this.createDeserializationContext(null, this.getDeserializationConfig()).hasValueDeserializerFor(javaType, atomicReference);
    }

    public boolean canSerialize(Class<?> class_) {
        return this._serializerProvider(this.getSerializationConfig()).hasSerializerFor(class_, null);
    }

    public boolean canSerialize(Class<?> class_, AtomicReference<Throwable> atomicReference) {
        return this._serializerProvider(this.getSerializationConfig()).hasSerializerFor(class_, atomicReference);
    }

    public ObjectMapper clearProblemHandlers() {
        this._deserializationConfig = this._deserializationConfig.withNoProblemHandlers();
        return this;
    }

    public ObjectMapper configure(JsonGenerator.Feature feature, boolean bl2) {
        this._jsonFactory.configure(feature, bl2);
        return this;
    }

    public ObjectMapper configure(JsonParser.Feature feature, boolean bl2) {
        this._jsonFactory.configure(feature, bl2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectMapper configure(DeserializationFeature object, boolean bl2) {
        object = bl2 ? this._deserializationConfig.with((DeserializationFeature)object) : this._deserializationConfig.without((DeserializationFeature)object);
        this._deserializationConfig = object;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectMapper configure(MapperFeature object, boolean bl2) {
        SerializationConfig serializationConfig = bl2 ? this._serializationConfig.with(new MapperFeature[]{object}) : this._serializationConfig.without(new MapperFeature[]{object});
        this._serializationConfig = serializationConfig;
        object = bl2 ? this._deserializationConfig.with(new MapperFeature[]{object}) : this._deserializationConfig.without(new MapperFeature[]{object});
        this._deserializationConfig = object;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectMapper configure(SerializationFeature object, boolean bl2) {
        object = bl2 ? this._serializationConfig.with((SerializationFeature)object) : this._serializationConfig.without((SerializationFeature)object);
        this._serializationConfig = object;
        return this;
    }

    public JavaType constructType(Type type) {
        return this._typeFactory.constructType(type);
    }

    public <T> T convertValue(Object object, TypeReference<?> typeReference) throws IllegalArgumentException {
        return this.convertValue(object, this._typeFactory.constructType(typeReference));
    }

    public <T> T convertValue(Object object, JavaType javaType) throws IllegalArgumentException {
        if (object == null) {
            return null;
        }
        return (T)this._convert(object, javaType);
    }

    public <T> T convertValue(Object object, Class<T> class_) throws IllegalArgumentException {
        if (object == null) {
            return null;
        }
        return (T)this._convert(object, this._typeFactory.constructType(class_));
    }

    public ObjectMapper copy() {
        this._checkInvalidCopy(ObjectMapper.class);
        return new ObjectMapper(this);
    }

    @Override
    public ArrayNode createArrayNode() {
        return this._deserializationConfig.getNodeFactory().arrayNode();
    }

    protected DefaultDeserializationContext createDeserializationContext(JsonParser jsonParser, DeserializationConfig deserializationConfig) {
        return this._deserializationContext.createInstance(deserializationConfig, jsonParser, this._injectableValues);
    }

    @Override
    public ObjectNode createObjectNode() {
        return this._deserializationConfig.getNodeFactory().objectNode();
    }

    public ObjectMapper disable(DeserializationFeature deserializationFeature) {
        this._deserializationConfig = this._deserializationConfig.without(deserializationFeature);
        return this;
    }

    public /* varargs */ ObjectMapper disable(DeserializationFeature deserializationFeature, DeserializationFeature ... arrdeserializationFeature) {
        this._deserializationConfig = this._deserializationConfig.without(deserializationFeature, arrdeserializationFeature);
        return this;
    }

    public ObjectMapper disable(SerializationFeature serializationFeature) {
        this._serializationConfig = this._serializationConfig.without(serializationFeature);
        return this;
    }

    public /* varargs */ ObjectMapper disable(SerializationFeature serializationFeature, SerializationFeature ... arrserializationFeature) {
        this._serializationConfig = this._serializationConfig.without(serializationFeature, arrserializationFeature);
        return this;
    }

    public /* varargs */ ObjectMapper disable(MapperFeature ... arrmapperFeature) {
        this._deserializationConfig = this._deserializationConfig.without(arrmapperFeature);
        this._serializationConfig = this._serializationConfig.without(arrmapperFeature);
        return this;
    }

    public ObjectMapper disableDefaultTyping() {
        return this.setDefaultTyping(null);
    }

    public ObjectMapper enable(DeserializationFeature deserializationFeature) {
        this._deserializationConfig = this._deserializationConfig.with(deserializationFeature);
        return this;
    }

    public /* varargs */ ObjectMapper enable(DeserializationFeature deserializationFeature, DeserializationFeature ... arrdeserializationFeature) {
        this._deserializationConfig = this._deserializationConfig.with(deserializationFeature, arrdeserializationFeature);
        return this;
    }

    public ObjectMapper enable(SerializationFeature serializationFeature) {
        this._serializationConfig = this._serializationConfig.with(serializationFeature);
        return this;
    }

    public /* varargs */ ObjectMapper enable(SerializationFeature serializationFeature, SerializationFeature ... arrserializationFeature) {
        this._serializationConfig = this._serializationConfig.with(serializationFeature, arrserializationFeature);
        return this;
    }

    public /* varargs */ ObjectMapper enable(MapperFeature ... arrmapperFeature) {
        this._deserializationConfig = this._deserializationConfig.with(arrmapperFeature);
        this._serializationConfig = this._serializationConfig.with(arrmapperFeature);
        return this;
    }

    public ObjectMapper enableDefaultTyping() {
        return this.enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping defaultTyping) {
        return this.enableDefaultTyping(defaultTyping, JsonTypeInfo.As.WRAPPER_ARRAY);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping defaultTyping, JsonTypeInfo.As as2) {
        return this.setDefaultTyping(new DefaultTypeResolverBuilder(defaultTyping).init(JsonTypeInfo.Id.CLASS, null).inclusion(as2));
    }

    public ObjectMapper enableDefaultTypingAsProperty(DefaultTyping defaultTyping, String string2) {
        return this.setDefaultTyping(new DefaultTypeResolverBuilder(defaultTyping).init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY).typeProperty(string2));
    }

    public ObjectMapper findAndRegisterModules() {
        return this.registerModules(ObjectMapper.findModules());
    }

    public final Class<?> findMixInClassFor(Class<?> class_) {
        if (this._mixInAnnotations == null) {
            return null;
        }
        return this._mixInAnnotations.get(new ClassKey(class_));
    }

    public JsonSchema generateJsonSchema(Class<?> class_) throws JsonMappingException {
        return this._serializerProvider(this.getSerializationConfig()).generateJsonSchema(class_);
    }

    public DeserializationConfig getDeserializationConfig() {
        return this._deserializationConfig;
    }

    public DeserializationContext getDeserializationContext() {
        return this._deserializationContext;
    }

    @Override
    public JsonFactory getFactory() {
        return this._jsonFactory;
    }

    @Deprecated
    @Override
    public JsonFactory getJsonFactory() {
        return this.getFactory();
    }

    public JsonNodeFactory getNodeFactory() {
        return this._deserializationConfig.getNodeFactory();
    }

    public SerializationConfig getSerializationConfig() {
        return this._serializationConfig;
    }

    public SerializerFactory getSerializerFactory() {
        return this._serializerFactory;
    }

    public SerializerProvider getSerializerProvider() {
        return this._serializerProvider;
    }

    public SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }

    public TypeFactory getTypeFactory() {
        return this._typeFactory;
    }

    public VisibilityChecker<?> getVisibilityChecker() {
        return this._serializationConfig.getDefaultVisibilityChecker();
    }

    public boolean isEnabled(JsonFactory.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public boolean isEnabled(JsonGenerator.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public boolean isEnabled(JsonParser.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public boolean isEnabled(DeserializationFeature deserializationFeature) {
        return this._deserializationConfig.isEnabled(deserializationFeature);
    }

    public boolean isEnabled(MapperFeature mapperFeature) {
        return this._serializationConfig.isEnabled(mapperFeature);
    }

    public boolean isEnabled(SerializationFeature serializationFeature) {
        return this._serializationConfig.isEnabled(serializationFeature);
    }

    public final int mixInCount() {
        if (this._mixInAnnotations == null) {
            return 0;
        }
        return this._mixInAnnotations.size();
    }

    @Override
    public <T extends TreeNode> T readTree(JsonParser object) throws IOException, JsonProcessingException {
        Object object2 = this.getDeserializationConfig();
        if (object.getCurrentToken() == null && object.nextToken() == null) {
            return null;
        }
        object = object2 = (JsonNode)this._readValue((DeserializationConfig)object2, (JsonParser)object, JSON_NODE_TYPE);
        if (object2 == null) {
            object = this.getNodeFactory().nullNode();
        }
        return (T)object;
    }

    public JsonNode readTree(File object) throws IOException, JsonProcessingException {
        JsonNode jsonNode;
        object = jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createParser((File)object), JSON_NODE_TYPE);
        if (jsonNode == null) {
            object = NullNode.instance;
        }
        return object;
    }

    public JsonNode readTree(InputStream object) throws IOException, JsonProcessingException {
        JsonNode jsonNode;
        object = jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createParser((InputStream)object), JSON_NODE_TYPE);
        if (jsonNode == null) {
            object = NullNode.instance;
        }
        return object;
    }

    public JsonNode readTree(Reader object) throws IOException, JsonProcessingException {
        JsonNode jsonNode;
        object = jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createParser((Reader)object), JSON_NODE_TYPE);
        if (jsonNode == null) {
            object = NullNode.instance;
        }
        return object;
    }

    public JsonNode readTree(String object) throws IOException, JsonProcessingException {
        JsonNode jsonNode;
        object = jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createParser((String)object), JSON_NODE_TYPE);
        if (jsonNode == null) {
            object = NullNode.instance;
        }
        return object;
    }

    public JsonNode readTree(URL object) throws IOException, JsonProcessingException {
        JsonNode jsonNode;
        object = jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createParser((URL)object), JSON_NODE_TYPE);
        if (jsonNode == null) {
            object = NullNode.instance;
        }
        return object;
    }

    public JsonNode readTree(byte[] object) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createParser((byte[])object), JSON_NODE_TYPE);
        object = jsonNode;
        if (jsonNode == null) {
            object = NullNode.instance;
        }
        return object;
    }

    @Override
    public final <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.getDeserializationConfig(), jsonParser, (JavaType)resolvedType);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.getDeserializationConfig(), jsonParser, this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.getDeserializationConfig(), jsonParser, javaType);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.getDeserializationConfig(), jsonParser, this._typeFactory.constructType(class_));
    }

    public <T> T readValue(File file, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(file), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(File file, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(file), javaType);
    }

    public <T> T readValue(File file, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(file), this._typeFactory.constructType(class_));
    }

    public <T> T readValue(InputStream inputStream, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(inputStream), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(InputStream inputStream, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(inputStream), javaType);
    }

    public <T> T readValue(InputStream inputStream, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(inputStream), this._typeFactory.constructType(class_));
    }

    public <T> T readValue(Reader reader, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(reader), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(Reader reader, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(reader), javaType);
    }

    public <T> T readValue(Reader reader, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(reader), this._typeFactory.constructType(class_));
    }

    public <T> T readValue(String string2, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(string2), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(String string2, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(string2), javaType);
    }

    public <T> T readValue(String string2, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(string2), this._typeFactory.constructType(class_));
    }

    public <T> T readValue(URL uRL, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(uRL), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(URL uRL, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(uRL), javaType);
    }

    public <T> T readValue(URL uRL, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(uRL), this._typeFactory.constructType(class_));
    }

    public <T> T readValue(byte[] arrby, int n2, int n3, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(arrby, n2, n3), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(byte[] arrby, int n2, int n3, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(arrby, n2, n3), javaType);
    }

    public <T> T readValue(byte[] arrby, int n2, int n3, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(arrby, n2, n3), this._typeFactory.constructType(class_));
    }

    public <T> T readValue(byte[] arrby, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(arrby), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(byte[] arrby, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(arrby), javaType);
    }

    public <T> T readValue(byte[] arrby, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createParser(arrby), this._typeFactory.constructType(class_));
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException, JsonProcessingException {
        return this.readValues(jsonParser, (JavaType)resolvedType);
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.readValues(jsonParser, this._typeFactory.constructType(typeReference));
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, JavaType javaType) throws IOException, JsonProcessingException {
        DefaultDeserializationContext defaultDeserializationContext = this.createDeserializationContext(jsonParser, this.getDeserializationConfig());
        return new MappingIterator(javaType, jsonParser, defaultDeserializationContext, this._findRootDeserializer(defaultDeserializationContext, javaType), false, null);
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, Class<T> class_) throws IOException, JsonProcessingException {
        return this.readValues(jsonParser, this._typeFactory.constructType(class_));
    }

    public ObjectReader reader() {
        return new ObjectReader(this, this.getDeserializationConfig()).with(this._injectableValues);
    }

    public ObjectReader reader(Base64Variant base64Variant) {
        return new ObjectReader(this, this.getDeserializationConfig().with(base64Variant));
    }

    public ObjectReader reader(FormatSchema formatSchema) {
        this._verifySchemaType(formatSchema);
        return new ObjectReader(this, this.getDeserializationConfig(), null, null, formatSchema, this._injectableValues);
    }

    public ObjectReader reader(TypeReference<?> typeReference) {
        return this.reader(this._typeFactory.constructType(typeReference));
    }

    public ObjectReader reader(DeserializationFeature deserializationFeature) {
        return new ObjectReader(this, this.getDeserializationConfig().with(deserializationFeature));
    }

    public /* varargs */ ObjectReader reader(DeserializationFeature deserializationFeature, DeserializationFeature ... arrdeserializationFeature) {
        return new ObjectReader(this, this.getDeserializationConfig().with(deserializationFeature, arrdeserializationFeature));
    }

    public ObjectReader reader(InjectableValues injectableValues) {
        return new ObjectReader(this, this.getDeserializationConfig(), null, null, null, injectableValues);
    }

    public ObjectReader reader(JavaType javaType) {
        return new ObjectReader(this, this.getDeserializationConfig(), javaType, null, null, this._injectableValues);
    }

    public ObjectReader reader(ContextAttributes contextAttributes) {
        return new ObjectReader(this, this.getDeserializationConfig().with(contextAttributes));
    }

    public ObjectReader reader(JsonNodeFactory jsonNodeFactory) {
        return new ObjectReader(this, this.getDeserializationConfig()).with(jsonNodeFactory);
    }

    public ObjectReader reader(Class<?> class_) {
        return this.reader(this._typeFactory.constructType(class_));
    }

    public ObjectReader readerForUpdating(Object object) {
        JavaType javaType = this._typeFactory.constructType(object.getClass());
        return new ObjectReader(this, this.getDeserializationConfig(), javaType, object, null, this._injectableValues);
    }

    public ObjectReader readerWithView(Class<?> class_) {
        return new ObjectReader(this, (DeserializationConfig)this.getDeserializationConfig().withView(class_));
    }

    public ObjectMapper registerModule(Module module) {
        if (module.getModuleName() == null) {
            throw new IllegalArgumentException("Module without defined name");
        }
        if (module.version() == null) {
            throw new IllegalArgumentException("Module without defined version");
        }
        module.setupModule(new Module.SetupContext(){

            @Override
            public void addAbstractTypeResolver(AbstractTypeResolver object) {
                object = this._deserializationContext._factory.withAbstractTypeResolver((AbstractTypeResolver)object);
                this._deserializationContext = this._deserializationContext.with((DeserializerFactory)object);
            }

            @Override
            public void addBeanDeserializerModifier(BeanDeserializerModifier object) {
                object = this._deserializationContext._factory.withDeserializerModifier((BeanDeserializerModifier)object);
                this._deserializationContext = this._deserializationContext.with((DeserializerFactory)object);
            }

            @Override
            public void addBeanSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
                this._serializerFactory = this._serializerFactory.withSerializerModifier(beanSerializerModifier);
            }

            @Override
            public void addDeserializationProblemHandler(DeserializationProblemHandler deserializationProblemHandler) {
                this.addHandler(deserializationProblemHandler);
            }

            @Override
            public void addDeserializers(Deserializers object) {
                object = this._deserializationContext._factory.withAdditionalDeserializers((Deserializers)object);
                this._deserializationContext = this._deserializationContext.with((DeserializerFactory)object);
            }

            @Override
            public void addKeyDeserializers(KeyDeserializers object) {
                object = this._deserializationContext._factory.withAdditionalKeyDeserializers((KeyDeserializers)object);
                this._deserializationContext = this._deserializationContext.with((DeserializerFactory)object);
            }

            @Override
            public void addKeySerializers(Serializers serializers) {
                this._serializerFactory = this._serializerFactory.withAdditionalKeySerializers(serializers);
            }

            @Override
            public void addSerializers(Serializers serializers) {
                this._serializerFactory = this._serializerFactory.withAdditionalSerializers(serializers);
            }

            @Override
            public void addTypeModifier(TypeModifier object) {
                object = this._typeFactory.withModifier((TypeModifier)object);
                this.setTypeFactory((TypeFactory)object);
            }

            @Override
            public void addValueInstantiators(ValueInstantiators object) {
                object = this._deserializationContext._factory.withValueInstantiators((ValueInstantiators)object);
                this._deserializationContext = this._deserializationContext.with((DeserializerFactory)object);
            }

            @Override
            public void appendAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
                this._deserializationConfig = this._deserializationConfig.withAppendedAnnotationIntrospector(annotationIntrospector);
                this._serializationConfig = this._serializationConfig.withAppendedAnnotationIntrospector(annotationIntrospector);
            }

            @Override
            public Version getMapperVersion() {
                return ObjectMapper.this.version();
            }

            @Override
            public <C extends ObjectCodec> C getOwner() {
                return (C)this;
            }

            @Override
            public TypeFactory getTypeFactory() {
                return ObjectMapper.this._typeFactory;
            }

            @Override
            public void insertAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
                this._deserializationConfig = this._deserializationConfig.withInsertedAnnotationIntrospector(annotationIntrospector);
                this._serializationConfig = this._serializationConfig.withInsertedAnnotationIntrospector(annotationIntrospector);
            }

            @Override
            public boolean isEnabled(JsonFactory.Feature feature) {
                return this.isEnabled(feature);
            }

            @Override
            public boolean isEnabled(JsonGenerator.Feature feature) {
                return this.isEnabled(feature);
            }

            @Override
            public boolean isEnabled(JsonParser.Feature feature) {
                return this.isEnabled(feature);
            }

            @Override
            public boolean isEnabled(DeserializationFeature deserializationFeature) {
                return this.isEnabled(deserializationFeature);
            }

            @Override
            public boolean isEnabled(MapperFeature mapperFeature) {
                return this.isEnabled(mapperFeature);
            }

            @Override
            public boolean isEnabled(SerializationFeature serializationFeature) {
                return this.isEnabled(serializationFeature);
            }

            @Override
            public /* varargs */ void registerSubtypes(NamedType ... arrnamedType) {
                this.registerSubtypes(arrnamedType);
            }

            @Override
            public /* varargs */ void registerSubtypes(Class<?> ... arrclass) {
                this.registerSubtypes(arrclass);
            }

            @Override
            public void setClassIntrospector(ClassIntrospector classIntrospector) {
                this._deserializationConfig = this._deserializationConfig.with(classIntrospector);
                this._serializationConfig = this._serializationConfig.with(classIntrospector);
            }

            @Override
            public void setMixInAnnotations(Class<?> class_, Class<?> class_2) {
                this.addMixInAnnotations(class_, class_2);
            }

            @Override
            public void setNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
                this.setPropertyNamingStrategy(propertyNamingStrategy);
            }
        });
        return this;
    }

    public ObjectMapper registerModules(Iterable<Module> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.registerModule((Module)object.next());
        }
        return this;
    }

    public /* varargs */ ObjectMapper registerModules(Module ... arrmodule) {
        int n2 = arrmodule.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.registerModule(arrmodule[i2]);
        }
        return this;
    }

    public /* varargs */ void registerSubtypes(NamedType ... arrnamedType) {
        this.getSubtypeResolver().registerSubtypes(arrnamedType);
    }

    public /* varargs */ void registerSubtypes(Class<?> ... arrclass) {
        this.getSubtypeResolver().registerSubtypes(arrclass);
    }

    public ObjectMapper setAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        this._serializationConfig = this._serializationConfig.with(annotationIntrospector);
        this._deserializationConfig = this._deserializationConfig.with(annotationIntrospector);
        return this;
    }

    public ObjectMapper setAnnotationIntrospectors(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
        this._serializationConfig = this._serializationConfig.with(annotationIntrospector);
        this._deserializationConfig = this._deserializationConfig.with(annotationIntrospector2);
        return this;
    }

    public ObjectMapper setBase64Variant(Base64Variant base64Variant) {
        this._serializationConfig = this._serializationConfig.with(base64Variant);
        this._deserializationConfig = this._deserializationConfig.with(base64Variant);
        return this;
    }

    public ObjectMapper setConfig(DeserializationConfig deserializationConfig) {
        this._deserializationConfig = deserializationConfig;
        return this;
    }

    public ObjectMapper setConfig(SerializationConfig serializationConfig) {
        this._serializationConfig = serializationConfig;
        return this;
    }

    public ObjectMapper setDateFormat(DateFormat dateFormat) {
        this._deserializationConfig = this._deserializationConfig.with(dateFormat);
        this._serializationConfig = this._serializationConfig.with(dateFormat);
        return this;
    }

    public ObjectMapper setDefaultTyping(TypeResolverBuilder<?> typeResolverBuilder) {
        this._deserializationConfig = this._deserializationConfig.with(typeResolverBuilder);
        this._serializationConfig = this._serializationConfig.with(typeResolverBuilder);
        return this;
    }

    public void setFilters(FilterProvider filterProvider) {
        this._serializationConfig = this._serializationConfig.withFilters(filterProvider);
    }

    public Object setHandlerInstantiator(HandlerInstantiator handlerInstantiator) {
        this._deserializationConfig = this._deserializationConfig.with(handlerInstantiator);
        this._serializationConfig = this._serializationConfig.with(handlerInstantiator);
        return this;
    }

    public ObjectMapper setInjectableValues(InjectableValues injectableValues) {
        this._injectableValues = injectableValues;
        return this;
    }

    public ObjectMapper setLocale(Locale locale) {
        this._deserializationConfig = this._deserializationConfig.with(locale);
        this._serializationConfig = this._serializationConfig.with(locale);
        return this;
    }

    public final void setMixInAnnotations(Map<Class<?>, Class<?>> object) {
        this._mixInAnnotations.clear();
        if (object != null && object.size() > 0) {
            for (Map.Entry entry : object.entrySet()) {
                this._mixInAnnotations.put(new ClassKey((Class)entry.getKey()), entry.getValue());
            }
        }
    }

    public ObjectMapper setNodeFactory(JsonNodeFactory jsonNodeFactory) {
        this._deserializationConfig = this._deserializationConfig.with(jsonNodeFactory);
        return this;
    }

    public ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this._serializationConfig = this._serializationConfig.with(propertyNamingStrategy);
        this._deserializationConfig = this._deserializationConfig.with(propertyNamingStrategy);
        return this;
    }

    public ObjectMapper setSerializationInclusion(JsonInclude.Include include) {
        this._serializationConfig = this._serializationConfig.withSerializationInclusion(include);
        return this;
    }

    public ObjectMapper setSerializerFactory(SerializerFactory serializerFactory) {
        this._serializerFactory = serializerFactory;
        return this;
    }

    public ObjectMapper setSerializerProvider(DefaultSerializerProvider defaultSerializerProvider) {
        this._serializerProvider = defaultSerializerProvider;
        return this;
    }

    public ObjectMapper setSubtypeResolver(SubtypeResolver subtypeResolver) {
        this._subtypeResolver = subtypeResolver;
        this._deserializationConfig = this._deserializationConfig.with(subtypeResolver);
        this._serializationConfig = this._serializationConfig.with(subtypeResolver);
        return this;
    }

    public ObjectMapper setTimeZone(TimeZone timeZone) {
        this._deserializationConfig = this._deserializationConfig.with(timeZone);
        this._serializationConfig = this._serializationConfig.with(timeZone);
        return this;
    }

    public ObjectMapper setTypeFactory(TypeFactory typeFactory) {
        this._typeFactory = typeFactory;
        this._deserializationConfig = this._deserializationConfig.with(typeFactory);
        this._serializationConfig = this._serializationConfig.with(typeFactory);
        return this;
    }

    public ObjectMapper setVisibility(PropertyAccessor propertyAccessor, JsonAutoDetect.Visibility visibility) {
        this._deserializationConfig = this._deserializationConfig.withVisibility(propertyAccessor, visibility);
        this._serializationConfig = this._serializationConfig.withVisibility(propertyAccessor, visibility);
        return this;
    }

    public void setVisibilityChecker(VisibilityChecker<?> visibilityChecker) {
        this._deserializationConfig = this._deserializationConfig.with(visibilityChecker);
        this._serializationConfig = this._serializationConfig.with(visibilityChecker);
    }

    @Override
    public JsonParser treeAsTokens(TreeNode treeNode) {
        return new TreeTraversingParser((JsonNode)treeNode, this);
    }

    @Override
    public <T> T treeToValue(TreeNode treeNode, Class<T> class_) throws JsonProcessingException {
        block5 : {
            if (class_ != Object.class) {
                if (!class_.isAssignableFrom(treeNode.getClass())) break block5;
                return (T)treeNode;
            }
        }
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

    public <T extends JsonNode> T valueToTree(Object object) throws IllegalArgumentException {
        if (object == null) {
            return null;
        }
        Object object2 = new TokenBuffer(this, false);
        try {
            this.writeValue((JsonGenerator)object2, object);
            object = object2.asParser();
            object2 = (JsonNode)this.readTree((JsonParser)object);
            object.close();
        }
        catch (IOException var1_2) {
            throw new IllegalArgumentException(var1_2.getMessage(), var1_2);
        }
        return (T)object2;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public void writeTree(JsonGenerator jsonGenerator, TreeNode treeNode) throws IOException, JsonProcessingException {
        SerializationConfig serializationConfig = this.getSerializationConfig();
        this._serializerProvider(serializationConfig).serializeValue(jsonGenerator, treeNode);
        if (serializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            jsonGenerator.flush();
        }
    }

    public void writeTree(JsonGenerator jsonGenerator, JsonNode jsonNode) throws IOException, JsonProcessingException {
        SerializationConfig serializationConfig = this.getSerializationConfig();
        this._serializerProvider(serializationConfig).serializeValue(jsonGenerator, jsonNode);
        if (serializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            jsonGenerator.flush();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig serializationConfig = this.getSerializationConfig();
        if (serializationConfig.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (serializationConfig.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            this._writeCloseableValue(jsonGenerator, object, serializationConfig);
            return;
        } else {
            this._serializerProvider(serializationConfig).serializeValue(jsonGenerator, object);
            if (!serializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) return;
            {
                jsonGenerator.flush();
                return;
            }
        }
    }

    public void writeValue(File file, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createGenerator(file, JsonEncoding.UTF8), object);
    }

    public void writeValue(OutputStream outputStream, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8), object);
    }

    public void writeValue(Writer writer, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createGenerator(writer), object);
    }

    public byte[] writeValueAsBytes(Object object) throws JsonProcessingException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
        try {
            this._configAndWriteValue(this._jsonFactory.createGenerator(byteArrayBuilder, JsonEncoding.UTF8), object);
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
        SegmentedStringWriter segmentedStringWriter = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
        try {
            this._configAndWriteValue(this._jsonFactory.createGenerator(segmentedStringWriter), object);
            return segmentedStringWriter.getAndClear();
        }
        catch (JsonProcessingException var1_2) {
            throw var1_2;
        }
        catch (IOException var1_3) {
            throw JsonMappingException.fromUnexpectedIOE(var1_3);
        }
    }

    public ObjectWriter writer() {
        return new ObjectWriter(this, this.getSerializationConfig());
    }

    public ObjectWriter writer(Base64Variant base64Variant) {
        return new ObjectWriter(this, this.getSerializationConfig().with(base64Variant));
    }

    public ObjectWriter writer(FormatSchema formatSchema) {
        this._verifySchemaType(formatSchema);
        return new ObjectWriter(this, this.getSerializationConfig(), formatSchema);
    }

    public ObjectWriter writer(PrettyPrinter prettyPrinter) {
        PrettyPrinter prettyPrinter2 = prettyPrinter;
        if (prettyPrinter == null) {
            prettyPrinter2 = ObjectWriter.NULL_PRETTY_PRINTER;
        }
        return new ObjectWriter(this, this.getSerializationConfig(), null, prettyPrinter2);
    }

    public ObjectWriter writer(CharacterEscapes characterEscapes) {
        return this.writer().with(characterEscapes);
    }

    public ObjectWriter writer(SerializationFeature serializationFeature) {
        return new ObjectWriter(this, this.getSerializationConfig().with(serializationFeature));
    }

    public /* varargs */ ObjectWriter writer(SerializationFeature serializationFeature, SerializationFeature ... arrserializationFeature) {
        return new ObjectWriter(this, this.getSerializationConfig().with(serializationFeature, arrserializationFeature));
    }

    public ObjectWriter writer(ContextAttributes contextAttributes) {
        return new ObjectWriter(this, this.getSerializationConfig().with(contextAttributes));
    }

    public ObjectWriter writer(FilterProvider filterProvider) {
        return new ObjectWriter(this, this.getSerializationConfig().withFilters(filterProvider));
    }

    public ObjectWriter writer(DateFormat dateFormat) {
        return new ObjectWriter(this, this.getSerializationConfig().with(dateFormat));
    }

    public ObjectWriter writerWithDefaultPrettyPrinter() {
        return new ObjectWriter(this, this.getSerializationConfig(), null, this._defaultPrettyPrinter());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectWriter writerWithType(TypeReference<?> object) {
        SerializationConfig serializationConfig = this.getSerializationConfig();
        if (object == null) {
            object = null;
            do {
                return new ObjectWriter(this, serializationConfig, (JavaType)object, null);
                break;
            } while (true);
        }
        object = this._typeFactory.constructType(object);
        return new ObjectWriter(this, serializationConfig, (JavaType)object, null);
    }

    public ObjectWriter writerWithType(JavaType javaType) {
        return new ObjectWriter(this, this.getSerializationConfig(), javaType, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectWriter writerWithType(Class<?> type) {
        void var1_3;
        SerializationConfig serializationConfig = this.getSerializationConfig();
        if (type == null) {
            return new ObjectWriter(this, serializationConfig, (JavaType)var1_3, null);
        }
        JavaType javaType = this._typeFactory.constructType(type);
        return new ObjectWriter(this, serializationConfig, (JavaType)var1_3, null);
    }

    public ObjectWriter writerWithView(Class<?> class_) {
        return new ObjectWriter(this, (SerializationConfig)this.getSerializationConfig().withView(class_));
    }

    public static class DefaultTypeResolverBuilder
    extends StdTypeResolverBuilder
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected final DefaultTyping _appliesFor;

        public DefaultTypeResolverBuilder(DefaultTyping defaultTyping) {
            this._appliesFor = defaultTyping;
        }

        @Override
        public TypeDeserializer buildTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, Collection<NamedType> collection) {
            if (this.useForType(javaType)) {
                return super.buildTypeDeserializer(deserializationConfig, javaType, collection);
            }
            return null;
        }

        @Override
        public TypeSerializer buildTypeSerializer(SerializationConfig serializationConfig, JavaType javaType, Collection<NamedType> collection) {
            if (this.useForType(javaType)) {
                return super.buildTypeSerializer(serializationConfig, javaType, collection);
            }
            return null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean useForType(JavaType javaType) {
            boolean bl2 = false;
            JavaType javaType2 = javaType;
            JavaType javaType3 = javaType;
            JavaType javaType4 = javaType;
            switch (.$SwitchMap$com$fasterxml$jackson$databind$ObjectMapper$DefaultTyping[this._appliesFor.ordinal()]) {
                default: {
                    if (javaType.getRawClass() != Object.class) return false;
                    return true;
                }
                case 1: {
                    do {
                        void var3_4;
                        javaType3 = var3_4;
                        if (!var3_4.isArrayType()) break;
                        ResolvedType resolvedType = var3_4.getContentType();
                    } while (true);
                }
                case 2: {
                    if (javaType3.getRawClass() == Object.class) return true;
                    if (!javaType3.isConcrete()) return true;
                    if (!TreeNode.class.isAssignableFrom(javaType3.getRawClass())) return bl2;
                    return true;
                }
                case 3: {
                    while (javaType4.isArrayType()) {
                        javaType4 = javaType4.getContentType();
                    }
                    if (javaType4.isFinal()) return false;
                    if (!TreeNode.class.isAssignableFrom(javaType4.getRawClass())) return true;
                    return false;
                }
            }
        }
    }

    public static enum DefaultTyping {
        JAVA_LANG_OBJECT,
        OBJECT_AND_NON_CONCRETE,
        NON_CONCRETE_AND_ARRAYS,
        NON_FINAL;
        

        private DefaultTyping() {
        }
    }

}

