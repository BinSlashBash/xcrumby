/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
import com.fasterxml.jackson.databind.ser.impl.TypeWrappedSerializer;
import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public abstract class SerializerProvider
extends DatabindContext {
    protected static final boolean CACHE_UNKNOWN_MAPPINGS = false;
    public static final JsonSerializer<Object> DEFAULT_NULL_KEY_SERIALIZER;
    protected static final JsonSerializer<Object> DEFAULT_UNKNOWN_SERIALIZER;
    @Deprecated
    protected static final JavaType TYPE_OBJECT;
    protected transient ContextAttributes _attributes;
    protected final SerializationConfig _config;
    protected DateFormat _dateFormat;
    protected JsonSerializer<Object> _keySerializer;
    protected final ReadOnlyClassToSerializerMap _knownSerializers;
    protected JsonSerializer<Object> _nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
    protected JsonSerializer<Object> _nullValueSerializer = NullSerializer.instance;
    protected final RootNameLookup _rootNames;
    protected final Class<?> _serializationView;
    protected final SerializerCache _serializerCache;
    protected final SerializerFactory _serializerFactory;
    protected final boolean _stdNullValueSerializer;
    protected JsonSerializer<Object> _unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;

    static {
        TYPE_OBJECT = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
        DEFAULT_NULL_KEY_SERIALIZER = new FailingSerializer("Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)");
        DEFAULT_UNKNOWN_SERIALIZER = new UnknownSerializer();
    }

    public SerializerProvider() {
        this._config = null;
        this._serializerFactory = null;
        this._serializerCache = new SerializerCache();
        this._knownSerializers = null;
        this._rootNames = new RootNameLookup();
        this._serializationView = null;
        this._attributes = null;
        this._stdNullValueSerializer = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SerializerProvider(SerializerProvider serializerProvider, SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
        if (serializationConfig == null) {
            throw new NullPointerException();
        }
        this._serializerFactory = serializerFactory;
        this._config = serializationConfig;
        this._serializerCache = serializerProvider._serializerCache;
        this._unknownTypeSerializer = serializerProvider._unknownTypeSerializer;
        this._keySerializer = serializerProvider._keySerializer;
        this._nullValueSerializer = serializerProvider._nullValueSerializer;
        boolean bl2 = this._nullValueSerializer == DEFAULT_NULL_KEY_SERIALIZER;
        this._stdNullValueSerializer = bl2;
        this._nullKeySerializer = serializerProvider._nullKeySerializer;
        this._rootNames = serializerProvider._rootNames;
        this._knownSerializers = this._serializerCache.getReadOnlyLookupMap();
        this._serializationView = serializationConfig.getActiveView();
        this._attributes = serializationConfig.getAttributes();
    }

    protected JsonSerializer<Object> _createAndCacheUntypedSerializer(JavaType javaType) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        block2 : {
            try {
                jsonSerializer = this._createUntypedSerializer(javaType);
                if (jsonSerializer == null) break block2;
            }
            catch (IllegalArgumentException var1_2) {
                throw new JsonMappingException(var1_2.getMessage(), null, var1_2);
            }
            this._serializerCache.addAndResolveNonTypedSerializer(javaType, jsonSerializer, this);
        }
        return jsonSerializer;
    }

    protected JsonSerializer<Object> _createAndCacheUntypedSerializer(Class<?> class_) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        block2 : {
            try {
                jsonSerializer = this._createUntypedSerializer(this._config.constructType(class_));
                if (jsonSerializer == null) break block2;
            }
            catch (IllegalArgumentException var1_2) {
                throw new JsonMappingException(var1_2.getMessage(), null, var1_2);
            }
            this._serializerCache.addAndResolveNonTypedSerializer(class_, jsonSerializer, this);
        }
        return jsonSerializer;
    }

    protected JsonSerializer<Object> _createUntypedSerializer(JavaType javaType) throws JsonMappingException {
        return this._serializerFactory.createSerializer(this, javaType);
    }

    protected final DateFormat _dateFormat() {
        DateFormat dateFormat;
        if (this._dateFormat != null) {
            return this._dateFormat;
        }
        this._dateFormat = dateFormat = (DateFormat)this._config.getDateFormat().clone();
        return dateFormat;
    }

    protected JsonSerializer<Object> _findExplicitUntypedSerializer(Class<?> class_) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = this._knownSerializers.untypedValueSerializer(class_);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        jsonSerializer = this._serializerCache.untypedValueSerializer(class_);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        return this._createAndCacheUntypedSerializer(class_);
    }

    protected JsonSerializer<Object> _handleContextualResolvable(JsonSerializer<?> jsonSerializer, BeanProperty beanProperty) throws JsonMappingException {
        if (jsonSerializer instanceof ResolvableSerializer) {
            ((ResolvableSerializer)((Object)jsonSerializer)).resolve(this);
        }
        return this.handleSecondaryContextualization(jsonSerializer, beanProperty);
    }

    protected JsonSerializer<Object> _handleResolvable(JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        if (jsonSerializer instanceof ResolvableSerializer) {
            ((ResolvableSerializer)((Object)jsonSerializer)).resolve(this);
        }
        return jsonSerializer;
    }

    protected void _reportIncompatibleRootType(Object object, JavaType javaType) throws IOException, JsonProcessingException {
        if (javaType.isPrimitive() && ClassUtil.wrapperType(javaType.getRawClass()).isAssignableFrom(object.getClass())) {
            return;
        }
        throw new JsonMappingException("Incompatible types: declared root type (" + javaType + ") vs " + object.getClass().getName());
    }

    public void defaultSerializeDateKey(long l2, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
            jsonGenerator.writeFieldName(String.valueOf(l2));
            return;
        }
        jsonGenerator.writeFieldName(this._dateFormat().format(new Date(l2)));
    }

    public void defaultSerializeDateKey(Date date, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
            jsonGenerator.writeFieldName(String.valueOf(date.getTime()));
            return;
        }
        jsonGenerator.writeFieldName(this._dateFormat().format(date));
    }

    public final void defaultSerializeDateValue(long l2, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
            jsonGenerator.writeNumber(l2);
            return;
        }
        jsonGenerator.writeString(this._dateFormat().format(new Date(l2)));
    }

    public final void defaultSerializeDateValue(Date date, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
            jsonGenerator.writeNumber(date.getTime());
            return;
        }
        jsonGenerator.writeString(this._dateFormat().format(date));
    }

    public final void defaultSerializeField(String string2, Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeFieldName(string2);
        if (object == null) {
            if (this._stdNullValueSerializer) {
                jsonGenerator.writeNull();
                return;
            }
            this._nullValueSerializer.serialize(null, jsonGenerator, this);
            return;
        }
        this.findTypedValueSerializer(object.getClass(), true, null).serialize(object, jsonGenerator, this);
    }

    public final void defaultSerializeNull(JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this._stdNullValueSerializer) {
            jsonGenerator.writeNull();
            return;
        }
        this._nullValueSerializer.serialize(null, jsonGenerator, this);
    }

    public final void defaultSerializeValue(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (object == null) {
            if (this._stdNullValueSerializer) {
                jsonGenerator.writeNull();
                return;
            }
            this._nullValueSerializer.serialize(null, jsonGenerator, this);
            return;
        }
        this.findTypedValueSerializer(object.getClass(), true, null).serialize(object, jsonGenerator, this);
    }

    public JsonSerializer<Object> findKeySerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return this._handleContextualResolvable(this._serializerFactory.createKeySerializer(this._config, javaType, this._keySerializer), beanProperty);
    }

    public JsonSerializer<Object> findNullKeySerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return this._nullKeySerializer;
    }

    public JsonSerializer<Object> findNullValueSerializer(BeanProperty beanProperty) throws JsonMappingException {
        return this._nullValueSerializer;
    }

    public abstract WritableObjectId findObjectId(Object var1, ObjectIdGenerator<?> var2);

    public JsonSerializer<Object> findPrimaryPropertySerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._knownSerializers.untypedValueSerializer(javaType);
        if (jsonSerializer == null) {
            jsonSerializer2 = jsonSerializer = this._serializerCache.untypedValueSerializer(javaType);
            if (jsonSerializer == null) {
                jsonSerializer2 = jsonSerializer = this._createAndCacheUntypedSerializer(javaType);
                if (jsonSerializer == null) {
                    return this.getUnknownTypeSerializer(javaType.getRawClass());
                }
            }
        }
        return this.handlePrimaryContextualization(jsonSerializer2, beanProperty);
    }

    public JsonSerializer<Object> findPrimaryPropertySerializer(Class<?> class_, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._knownSerializers.untypedValueSerializer(class_);
        if (jsonSerializer == null) {
            jsonSerializer2 = jsonSerializer = this._serializerCache.untypedValueSerializer(class_);
            if (jsonSerializer == null) {
                jsonSerializer2 = jsonSerializer = this._serializerCache.untypedValueSerializer(this._config.constructType(class_));
                if (jsonSerializer == null) {
                    jsonSerializer2 = jsonSerializer = this._createAndCacheUntypedSerializer(class_);
                    if (jsonSerializer == null) {
                        return this.getUnknownTypeSerializer(class_);
                    }
                }
            }
        }
        return this.handlePrimaryContextualization(jsonSerializer2, beanProperty);
    }

    public JsonSerializer<Object> findTypedValueSerializer(JavaType javaType, boolean bl2, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer jsonSerializer = this._knownSerializers.typedValueSerializer(javaType);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        jsonSerializer = this._serializerCache.typedValueSerializer(javaType);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        JsonSerializer<Object> jsonSerializer2 = this.findValueSerializer(javaType, beanProperty);
        TypeSerializer typeSerializer = this._serializerFactory.createTypeSerializer(this._config, javaType);
        jsonSerializer = jsonSerializer2;
        if (typeSerializer != null) {
            jsonSerializer = new TypeWrappedSerializer(typeSerializer.forProperty(beanProperty), jsonSerializer2);
        }
        if (bl2) {
            this._serializerCache.addTypedSerializer(javaType, jsonSerializer);
        }
        return jsonSerializer;
    }

    public JsonSerializer<Object> findTypedValueSerializer(Class<?> class_, boolean bl2, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer jsonSerializer = this._knownSerializers.typedValueSerializer(class_);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        jsonSerializer = this._serializerCache.typedValueSerializer(class_);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        JsonSerializer<Object> jsonSerializer2 = this.findValueSerializer(class_, beanProperty);
        TypeSerializer typeSerializer = this._serializerFactory.createTypeSerializer(this._config, this._config.constructType(class_));
        jsonSerializer = jsonSerializer2;
        if (typeSerializer != null) {
            jsonSerializer = new TypeWrappedSerializer(typeSerializer.forProperty(beanProperty), jsonSerializer2);
        }
        if (bl2) {
            this._serializerCache.addTypedSerializer(class_, jsonSerializer);
        }
        return jsonSerializer;
    }

    public JsonSerializer<Object> findValueSerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._knownSerializers.untypedValueSerializer(javaType);
        if (jsonSerializer == null) {
            jsonSerializer2 = jsonSerializer = this._serializerCache.untypedValueSerializer(javaType);
            if (jsonSerializer == null) {
                jsonSerializer2 = jsonSerializer = this._createAndCacheUntypedSerializer(javaType);
                if (jsonSerializer == null) {
                    return this.getUnknownTypeSerializer(javaType.getRawClass());
                }
            }
        }
        return this.handleSecondaryContextualization(jsonSerializer2, beanProperty);
    }

    public JsonSerializer<Object> findValueSerializer(Class<?> class_, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._knownSerializers.untypedValueSerializer(class_);
        if (jsonSerializer == null) {
            jsonSerializer2 = jsonSerializer = this._serializerCache.untypedValueSerializer(class_);
            if (jsonSerializer == null) {
                jsonSerializer2 = jsonSerializer = this._serializerCache.untypedValueSerializer(this._config.constructType(class_));
                if (jsonSerializer == null) {
                    jsonSerializer2 = jsonSerializer = this._createAndCacheUntypedSerializer(class_);
                    if (jsonSerializer == null) {
                        return this.getUnknownTypeSerializer(class_);
                    }
                }
            }
        }
        return this.handleSecondaryContextualization(jsonSerializer2, beanProperty);
    }

    @Override
    public final Class<?> getActiveView() {
        return this._serializationView;
    }

    @Override
    public final AnnotationIntrospector getAnnotationIntrospector() {
        return this._config.getAnnotationIntrospector();
    }

    @Override
    public Object getAttribute(Object object) {
        return this._attributes.getAttribute(object);
    }

    public final SerializationConfig getConfig() {
        return this._config;
    }

    public JsonSerializer<Object> getDefaultNullKeySerializer() {
        return this._nullKeySerializer;
    }

    public JsonSerializer<Object> getDefaultNullValueSerializer() {
        return this._nullValueSerializer;
    }

    public final FilterProvider getFilterProvider() {
        return this._config.getFilterProvider();
    }

    public Locale getLocale() {
        return this._config.getLocale();
    }

    @Deprecated
    public final Class<?> getSerializationView() {
        return this._serializationView;
    }

    public TimeZone getTimeZone() {
        return this._config.getTimeZone();
    }

    @Override
    public final TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public JsonSerializer<Object> getUnknownTypeSerializer(Class<?> class_) {
        return this._unknownTypeSerializer;
    }

    @Deprecated
    public JsonSerializer<?> handleContextualization(JsonSerializer<?> jsonSerializer, BeanProperty beanProperty) throws JsonMappingException {
        return this.handleSecondaryContextualization(jsonSerializer, beanProperty);
    }

    public JsonSerializer<?> handlePrimaryContextualization(JsonSerializer<?> jsonSerializer, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<?> jsonSerializer2 = jsonSerializer;
        if (jsonSerializer != null) {
            jsonSerializer2 = jsonSerializer;
            if (jsonSerializer instanceof ContextualSerializer) {
                jsonSerializer2 = ((ContextualSerializer)((Object)jsonSerializer)).createContextual(this, beanProperty);
            }
        }
        return jsonSerializer2;
    }

    public JsonSerializer<?> handleSecondaryContextualization(JsonSerializer<?> jsonSerializer, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<?> jsonSerializer2 = jsonSerializer;
        if (jsonSerializer != null) {
            jsonSerializer2 = jsonSerializer;
            if (jsonSerializer instanceof ContextualSerializer) {
                jsonSerializer2 = ((ContextualSerializer)((Object)jsonSerializer)).createContextual(this, beanProperty);
            }
        }
        return jsonSerializer2;
    }

    public final boolean hasSerializationFeatures(int n2) {
        return this._config.hasSerializationFeatures(n2);
    }

    public final boolean isEnabled(SerializationFeature serializationFeature) {
        return this._config.isEnabled(serializationFeature);
    }

    public abstract JsonSerializer<Object> serializerInstance(Annotated var1, Object var2) throws JsonMappingException;

    @Override
    public SerializerProvider setAttribute(Object object, Object object2) {
        this._attributes = this._attributes.withPerCallAttribute(object, object2);
        return this;
    }

    public void setDefaultKeySerializer(JsonSerializer<Object> jsonSerializer) {
        if (jsonSerializer == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._keySerializer = jsonSerializer;
    }

    public void setNullKeySerializer(JsonSerializer<Object> jsonSerializer) {
        if (jsonSerializer == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._nullKeySerializer = jsonSerializer;
    }

    public void setNullValueSerializer(JsonSerializer<Object> jsonSerializer) {
        if (jsonSerializer == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._nullValueSerializer = jsonSerializer;
    }
}

