/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;
import com.fasterxml.jackson.databind.ser.impl.IteratorSerializer;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
import com.fasterxml.jackson.databind.ser.std.ByteBufferSerializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumMapSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSetSerializer;
import com.fasterxml.jackson.databind.ser.std.InetAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.InetSocketAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.IterableSerializer;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlDateSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlTimeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.EnumValues;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.TimeZone;

public abstract class BasicSerializerFactory
extends SerializerFactory
implements Serializable {
    protected static final HashMap<String, JsonSerializer<?>> _concrete = new HashMap();
    protected static final HashMap<String, Class<? extends JsonSerializer<?>>> _concreteLazy = new HashMap();
    protected final SerializerFactoryConfig _factoryConfig;

    static {
        _concrete.put(String.class.getName(), new StringSerializer());
        Object object = ToStringSerializer.instance;
        _concrete.put(StringBuffer.class.getName(), (ToStringSerializer)object);
        _concrete.put(StringBuilder.class.getName(), (ToStringSerializer)object);
        _concrete.put(Character.class.getName(), (ToStringSerializer)object);
        _concrete.put(Character.TYPE.getName(), (ToStringSerializer)object);
        NumberSerializers.addAll(_concrete);
        _concrete.put(Boolean.TYPE.getName(), new BooleanSerializer(true));
        _concrete.put(Boolean.class.getName(), new BooleanSerializer(false));
        object = NumberSerializer.instance;
        _concrete.put(BigInteger.class.getName(), (ToStringSerializer)object);
        _concrete.put(BigDecimal.class.getName(), (ToStringSerializer)object);
        _concrete.put(Calendar.class.getName(), CalendarSerializer.instance);
        object = DateSerializer.instance;
        _concrete.put(Date.class.getName(), (ToStringSerializer)object);
        _concrete.put(Timestamp.class.getName(), (ToStringSerializer)object);
        _concreteLazy.put(java.sql.Date.class.getName(), SqlDateSerializer.class);
        _concreteLazy.put(Time.class.getName(), SqlTimeSerializer.class);
        for (Map.Entry entry : StdJdkSerializers.all()) {
            Object object2 = entry.getValue();
            if (object2 instanceof JsonSerializer) {
                _concrete.put(((Class)entry.getKey()).getName(), (JsonSerializer)object2);
                continue;
            }
            if (object2 instanceof Class) {
                object2 = (Class)object2;
                _concreteLazy.put(((Class)entry.getKey()).getName(), (Object)object2);
                continue;
            }
            throw new IllegalStateException("Internal error: unrecognized value of type " + entry.getClass().getName());
        }
        _concreteLazy.put(TokenBuffer.class.getName(), TokenBufferSerializer.class);
    }

    protected BasicSerializerFactory(SerializerFactoryConfig serializerFactoryConfig) {
        SerializerFactoryConfig serializerFactoryConfig2 = serializerFactoryConfig;
        if (serializerFactoryConfig == null) {
            serializerFactoryConfig2 = new SerializerFactoryConfig();
        }
        this._factoryConfig = serializerFactoryConfig2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static <T extends JavaType> T modifySecondaryTypesByAnnotation(SerializationConfig serializable, Annotated object, T t2) {
        AnnotationIntrospector annotationIntrospector = serializable.getAnnotationIntrospector();
        Object object2 = t2;
        if (!t2.isContainerType()) return (T)object2;
        object2 = annotationIntrospector.findSerializationKeyType((Annotated)((Object)object), (JavaType)t2.getKeyType());
        serializable = t2;
        if (object2 != null) {
            if (!(t2 instanceof MapType)) {
                throw new IllegalArgumentException("Illegal key-type annotation: type " + t2 + " is not a Map type");
            }
            serializable = ((MapType)t2).widenKey(object2);
        }
        object = annotationIntrospector.findSerializationContentType((Annotated)((Object)object), (JavaType)serializable.getContentType());
        object2 = serializable;
        if (object == null) return (T)object2;
        try {
            object2 = serializable.widenContentsBy(object);
        }
        catch (IllegalArgumentException illegalArgumentException2) {
            throw new IllegalArgumentException("Failed to narrow content type " + serializable + " with content-type annotation (" + object.getName() + "): " + illegalArgumentException2.getMessage());
        }
        return (T)object2;
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Failed to narrow key type " + t2 + " with key-type annotation (" + object2.getName() + "): " + illegalArgumentException.getMessage());
        }
    }

    protected JsonSerializer<Object> _findContentSerializer(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object object = serializerProvider.getAnnotationIntrospector().findContentSerializer(annotated);
        if (object != null) {
            return serializerProvider.serializerInstance(annotated, object);
        }
        return null;
    }

    protected JsonSerializer<Object> _findKeySerializer(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object object = serializerProvider.getAnnotationIntrospector().findKeySerializer(annotated);
        if (object != null) {
            return serializerProvider.serializerInstance(annotated, object);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Class<?> _verifyAsClass(Object object, String object2, Class<?> class_) {
        if (object == null) {
            return null;
        }
        if (!(object instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector." + (String)object2 + "() returned value of type " + object.getClass().getName() + ": expected type JsonSerializer or Class<JsonSerializer> instead");
        }
        object2 = (Class)object;
        if (object2 == class_) return null;
        object = object2;
        if (!ClassUtil.isBogusClass(object2)) return object;
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<?> buildArraySerializer(SerializationConfig serializationConfig, ArrayType arrayType, BeanDescription beanDescription, boolean bl2, TypeSerializer object, JsonSerializer<Object> iterator) throws JsonMappingException {
        StringArraySerializer stringArraySerializer;
        StringArraySerializer stringArraySerializer2 = null;
        Iterator<Serializers> iterator2 = this.customSerializers().iterator();
        while (iterator2.hasNext()) {
            stringArraySerializer2 = stringArraySerializer = iterator2.next().findArraySerializer(serializationConfig, arrayType, beanDescription, (TypeSerializer)object, (JsonSerializer<Object>)((Object)iterator));
            if (stringArraySerializer == null) continue;
            stringArraySerializer2 = stringArraySerializer;
            break;
        }
        stringArraySerializer = stringArraySerializer2;
        if (stringArraySerializer2 == null) {
            stringArraySerializer = arrayType.getRawClass();
            if (iterator == null || ClassUtil.isJacksonStdImpl(iterator)) {
                stringArraySerializer2 = String[].class == stringArraySerializer ? StringArraySerializer.instance : StdArraySerializers.findStandardImpl(stringArraySerializer);
            }
            stringArraySerializer = stringArraySerializer2;
            if (stringArraySerializer2 == null) {
                stringArraySerializer = new ObjectArraySerializer((JavaType)arrayType.getContentType(), bl2, (TypeSerializer)object, (JsonSerializer<Object>)((Object)iterator));
            }
        }
        object = stringArraySerializer;
        if (this._factoryConfig.hasSerializerModifiers()) {
            iterator = this._factoryConfig.serializerModifiers().iterator();
            do {
                object = stringArraySerializer;
                if (!iterator.hasNext()) break;
                stringArraySerializer = iterator.next().modifyArraySerializer(serializationConfig, arrayType, beanDescription, stringArraySerializer);
            } while (true);
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected JsonSerializer<?> buildCollectionSerializer(SerializationConfig var1_1, CollectionType var2_2, BeanDescription var3_3, boolean var4_4, TypeSerializer var5_5, JsonSerializer<Object> var6_6) throws JsonMappingException {
        var9_7 = null;
        var8_8 = this.customSerializers().iterator();
        while (var8_8.hasNext()) {
            var9_7 = var7_9 = var8_8.next().findCollectionSerializer(var1_1, var2_2, var3_3, (TypeSerializer)var5_5, (JsonSerializer<Object>)var6_6);
            if (var7_9 == null) continue;
            var9_7 = var7_9;
            break;
        }
        var7_9 = var9_7;
        if (var9_7 != null) ** GOTO lbl39
        var7_9 = var3_3.findExpectedFormat(null);
        if (var7_9 != null && var7_9.getShape() == JsonFormat.Shape.OBJECT) {
            return null;
        }
        var8_8 = var2_2.getRawClass();
        if (!EnumSet.class.isAssignableFrom(var8_8)) ** GOTO lbl20
        var5_5 = var6_6 = var2_2.getContentType();
        if (!var6_6.isEnumType()) {
            var5_5 = null;
        }
        var7_9 = this.buildEnumSetSerializer((JavaType)var5_5);
        ** GOTO lbl39
lbl20: // 1 sources:
        var7_9 = var2_2.getContentType().getRawClass();
        if (!this.isIndexedList(var8_8)) ** GOTO lbl30
        if (var7_9 != String.class) ** GOTO lbl28
        if (var6_6 == null) ** GOTO lbl-1000
        var8_8 = var9_7;
        if (ClassUtil.isJacksonStdImpl(var6_6)) lbl-1000: // 2 sources:
        {
            var8_8 = IndexedStringListSerializer.instance;
        }
        ** GOTO lbl36
lbl28: // 1 sources:
        var8_8 = this.buildIndexedListSerializer((JavaType)var2_2.getContentType(), var4_4, (TypeSerializer)var5_5, (JsonSerializer<Object>)var6_6);
        ** GOTO lbl36
lbl30: // 1 sources:
        var8_8 = var9_7;
        if (var7_9 != String.class) ** GOTO lbl36
        if (var6_6 == null) ** GOTO lbl-1000
        var8_8 = var9_7;
        if (ClassUtil.isJacksonStdImpl(var6_6)) lbl-1000: // 2 sources:
        {
            var8_8 = StringCollectionSerializer.instance;
        }
lbl36: // 6 sources:
        var7_9 = var8_8;
        if (var8_8 == null) {
            var7_9 = this.buildCollectionSerializer((JavaType)var2_2.getContentType(), var4_4, (TypeSerializer)var5_5, (JsonSerializer<Object>)var6_6);
        }
lbl39: // 5 sources:
        var5_5 = var7_9;
        if (this._factoryConfig.hasSerializerModifiers() == false) return var5_5;
        var6_6 = this._factoryConfig.serializerModifiers().iterator();
        do {
            var5_5 = var7_9;
            if (var6_6.hasNext() == false) return var5_5;
            var7_9 = ((BeanSerializerModifier)var6_6.next()).modifyCollectionSerializer(var1_1, var2_2, var3_3, var7_9);
        } while (true);
    }

    public ContainerSerializer<?> buildCollectionSerializer(JavaType javaType, boolean bl2, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return new CollectionSerializer(javaType, bl2, typeSerializer, null, jsonSerializer);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected JsonSerializer<?> buildContainerSerializer(SerializerProvider var1_1, JavaType var2_2, BeanDescription var3_3, boolean var4_4) throws JsonMappingException {
        var7_5 = var1_1.getConfig();
        var5_6 = var4_4;
        if (var4_4) ** GOTO lbl10
        var5_6 = var4_4;
        if (!var2_2.useStaticType()) ** GOTO lbl10
        if (!var2_2.isContainerType()) ** GOTO lbl-1000
        var5_6 = var4_4;
        if (var2_2.getContentType().getRawClass() != Object.class) lbl-1000: // 2 sources:
        {
            var5_6 = true;
        }
lbl10: // 5 sources:
        if ((var8_7 = this.createTypeSerializer(var7_5, (JavaType)var2_2.getContentType())) != null) {
            var5_6 = false;
        }
        var9_8 = this._findContentSerializer((SerializerProvider)var1_1, var3_3.getClassInfo());
        if (var2_2.isMapLikeType()) {
            var6_9 = (MapLikeType)var2_2;
            var1_1 = this._findKeySerializer((SerializerProvider)var1_1, var3_3.getClassInfo());
            if (var6_9.isTrueMapType()) {
                return this.buildMapSerializer(var7_5, (MapType)var6_9, var3_3, var5_6, (JsonSerializer<Object>)var1_1, (TypeSerializer)var8_7, var9_8);
            }
            var11_14 = this.customSerializers().iterator();
            do {
                if (var11_14.hasNext() == false) return null;
            } while ((var6_12 = (var6_11 = var11_14.next()).findMapLikeSerializer(var7_5, var10_15 = (MapLikeType)var2_2, var3_3, (JsonSerializer<Object>)var1_1, (TypeSerializer)var8_7, var9_8)) == null);
            var1_1 = var6_12;
            if (this._factoryConfig.hasSerializerModifiers() == false) return var1_1;
            var8_7 = this._factoryConfig.serializerModifiers().iterator();
            var2_2 = var6_12;
            do {
                var1_1 = var2_2;
                if (var8_7.hasNext() == false) return var1_1;
                var2_2 = var8_7.next().modifyMapLikeSerializer(var7_5, var10_15, var3_3, var2_2);
            } while (true);
        }
        if (!var2_2.isCollectionLikeType()) {
            if (var2_2.isArrayType() == false) return null;
            return this.buildArraySerializer(var7_5, (ArrayType)var2_2, var3_3, var5_6, (TypeSerializer)var8_7, var9_8);
        }
        var1_1 = (CollectionLikeType)var2_2;
        if (var1_1.isTrueCollectionType()) {
            return this.buildCollectionSerializer(var7_5, (CollectionType)var1_1, var3_3, var5_6, (TypeSerializer)var8_7, var9_8);
        }
        var6_13 = (CollectionLikeType)var2_2;
        var1_1 = this.customSerializers().iterator();
        do {
            if (var1_1.hasNext() == false) return null;
        } while ((var2_2 = ((Serializers)var1_1.next()).findCollectionLikeSerializer(var7_5, var6_13, var3_3, (TypeSerializer)var8_7, var9_8)) == null);
        var1_1 = var2_2;
        if (this._factoryConfig.hasSerializerModifiers() == false) return var1_1;
        var8_7 = this._factoryConfig.serializerModifiers().iterator();
        do {
            var1_1 = var2_2;
            if (var8_7.hasNext() == false) return var1_1;
            var2_2 = var8_7.next().modifyCollectionLikeSerializer(var7_5, var6_13, var3_3, var2_2);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<?> buildEnumSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        JsonSerializer jsonSerializer = null;
        Object object = beanDescription.findExpectedFormat(null);
        if (object != null && object.getShape() == JsonFormat.Shape.OBJECT) {
            ((BasicBeanDescription)beanDescription).removeProperty("declaringClass");
            return jsonSerializer;
        } else {
            jsonSerializer = object = EnumSerializer.construct(javaType.getRawClass(), serializationConfig, beanDescription, (JsonFormat.Value)object);
            if (!this._factoryConfig.hasSerializerModifiers()) return jsonSerializer;
            {
                Iterator<BeanSerializerModifier> iterator = this._factoryConfig.serializerModifiers().iterator();
                do {
                    jsonSerializer = object;
                    if (!iterator.hasNext()) return jsonSerializer;
                    object = iterator.next().modifyEnumSerializer(serializationConfig, javaType, beanDescription, object);
                } while (true);
            }
        }
    }

    public JsonSerializer<?> buildEnumSetSerializer(JavaType javaType) {
        return new EnumSetSerializer(javaType, null);
    }

    public ContainerSerializer<?> buildIndexedListSerializer(JavaType javaType, boolean bl2, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return new IndexedListSerializer(javaType, bl2, typeSerializer, null, jsonSerializer);
    }

    protected JsonSerializer<?> buildIterableSerializer(SerializationConfig serializationConfig, JavaType object, BeanDescription object2, boolean bl2) throws JsonMappingException {
        object = object2 = object.containedType(0);
        if (object2 == null) {
            object = TypeFactory.unknownType();
        }
        return new IterableSerializer((JavaType)object, bl2, this.createTypeSerializer(serializationConfig, (JavaType)object), null);
    }

    protected JsonSerializer<?> buildIteratorSerializer(SerializationConfig serializationConfig, JavaType object, BeanDescription object2, boolean bl2) throws JsonMappingException {
        object = object2 = object.containedType(0);
        if (object2 == null) {
            object = TypeFactory.unknownType();
        }
        return new IteratorSerializer((JavaType)object, bl2, this.createTypeSerializer(serializationConfig, (JavaType)object), null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<?> buildMapSerializer(SerializationConfig serializationConfig, MapType mapType, BeanDescription beanDescription, boolean bl2, JsonSerializer<Object> object, TypeSerializer iterator, JsonSerializer<Object> jsonSerializer) throws JsonMappingException {
        JsonSerializer jsonSerializer2;
        JsonSerializer jsonSerializer3 = null;
        Iterator<Serializers> iterator2 = this.customSerializers().iterator();
        do {
            jsonSerializer2 = jsonSerializer3;
            if (!iterator2.hasNext()) break;
            jsonSerializer3 = jsonSerializer2 = iterator2.next().findMapSerializer(serializationConfig, mapType, beanDescription, (JsonSerializer<Object>)object, (TypeSerializer)((Object)iterator), jsonSerializer);
        } while (jsonSerializer2 == null);
        jsonSerializer3 = jsonSerializer2;
        if (jsonSerializer2 == null) {
            if (EnumMap.class.isAssignableFrom(mapType.getRawClass())) {
                jsonSerializer3 = mapType.getKeyType();
                object = null;
                if (jsonSerializer3.isEnumType()) {
                    object = EnumValues.construct(serializationConfig, jsonSerializer3.getRawClass());
                }
                jsonSerializer3 = new EnumMapSerializer((JavaType)mapType.getContentType(), bl2, (EnumValues)object, (TypeSerializer)((Object)iterator), jsonSerializer);
            } else {
                jsonSerializer3 = this.findFilterId(serializationConfig, beanDescription);
                jsonSerializer3 = MapSerializer.construct(serializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(beanDescription.getClassInfo()), mapType, bl2, iterator, object, jsonSerializer, jsonSerializer3);
            }
        }
        object = jsonSerializer3;
        if (this._factoryConfig.hasSerializerModifiers()) {
            iterator = this._factoryConfig.serializerModifiers().iterator();
            do {
                object = jsonSerializer3;
                if (!iterator.hasNext()) break;
                jsonSerializer3 = iterator.next().modifyMapSerializer(serializationConfig, mapType, beanDescription, jsonSerializer3);
            } while (true);
        }
        return object;
    }

    @Override
    public JsonSerializer<Object> createKeySerializer(SerializationConfig serializationConfig, JavaType javaType, JsonSerializer<Object> jsonSerializer) {
        BeanDescription beanDescription = serializationConfig.introspectClassAnnotations(javaType.getRawClass());
        JsonSerializer jsonSerializer2 = null;
        JsonSerializer jsonSerializer3 = null;
        if (this._factoryConfig.hasKeySerializers()) {
            Iterator<Serializers> iterator = this._factoryConfig.keySerializers().iterator();
            jsonSerializer2 = jsonSerializer3;
            while (iterator.hasNext()) {
                jsonSerializer2 = jsonSerializer3 = iterator.next().findSerializer(serializationConfig, javaType, beanDescription);
                if (jsonSerializer3 == null) continue;
                jsonSerializer2 = jsonSerializer3;
                break;
            }
        }
        jsonSerializer3 = jsonSerializer2;
        if (jsonSerializer2 == null) {
            jsonSerializer3 = jsonSerializer;
            if (jsonSerializer == null) {
                jsonSerializer3 = StdKeySerializers.getStdKeySerializer(javaType);
            }
        }
        jsonSerializer = jsonSerializer3;
        if (this._factoryConfig.hasSerializerModifiers()) {
            jsonSerializer2 = this._factoryConfig.serializerModifiers().iterator();
            do {
                jsonSerializer = jsonSerializer3;
                if (!jsonSerializer2.hasNext()) break;
                jsonSerializer3 = ((BeanSerializerModifier)jsonSerializer2.next()).modifyKeySerializer(serializationConfig, javaType, beanDescription, jsonSerializer3);
            } while (true);
        }
        return jsonSerializer;
    }

    @Override
    public abstract JsonSerializer<Object> createSerializer(SerializerProvider var1, JavaType var2) throws JsonMappingException;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public TypeSerializer createTypeSerializer(SerializationConfig serializationConfig, JavaType javaType) {
        AnnotatedClass annotatedClass = serializationConfig.introspectClassAnnotations(javaType.getRawClass()).getClassInfo();
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> typeResolverBuilder = annotationIntrospector.findTypeResolver(serializationConfig, annotatedClass, javaType);
        Collection<NamedType> collection = null;
        if (typeResolverBuilder == null) {
            typeResolverBuilder = serializationConfig.getDefaultTyper(javaType);
        } else {
            collection = serializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedClass, serializationConfig, annotationIntrospector);
        }
        if (typeResolverBuilder == null) {
            return null;
        }
        return typeResolverBuilder.buildTypeSerializer(serializationConfig, javaType, collection);
    }

    protected abstract Iterable<Serializers> customSerializers();

    protected Converter<Object, Object> findConverter(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object object = serializerProvider.getAnnotationIntrospector().findSerializationConverter(annotated);
        if (object == null) {
            return null;
        }
        return serializerProvider.converterInstance(annotated, object);
    }

    protected JsonSerializer<?> findConvertingSerializer(SerializerProvider serializerProvider, Annotated object, JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        if ((object = this.findConverter(serializerProvider, (Annotated)object)) == null) {
            return jsonSerializer;
        }
        return new StdDelegatingSerializer(object, object.getOutputType(serializerProvider.getTypeFactory()), jsonSerializer);
    }

    protected Object findFilterId(SerializationConfig serializationConfig, BeanDescription beanDescription) {
        return serializationConfig.getAnnotationIntrospector().findFilterId((Annotated)beanDescription.getClassInfo());
    }

    protected JsonSerializer<?> findOptionalStdSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription, boolean bl2) throws JsonMappingException {
        return OptionalHandlerFactory.instance.findSerializer(serializerProvider.getConfig(), javaType, beanDescription);
    }

    protected final JsonSerializer<?> findSerializerByAddonType(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription, boolean bl2) throws JsonMappingException {
        Class<?> class_ = javaType.getRawClass();
        if (Iterator.class.isAssignableFrom(class_)) {
            return this.buildIteratorSerializer(serializationConfig, javaType, beanDescription, bl2);
        }
        if (Iterable.class.isAssignableFrom(class_)) {
            return this.buildIterableSerializer(serializationConfig, javaType, beanDescription, bl2);
        }
        if (CharSequence.class.isAssignableFrom(class_)) {
            return ToStringSerializer.instance;
        }
        return null;
    }

    protected final JsonSerializer<?> findSerializerByAnnotations(SerializerProvider serializerProvider, JavaType serializable, BeanDescription object) throws JsonMappingException {
        if (JsonSerializable.class.isAssignableFrom(serializable.getRawClass())) {
            return SerializableSerializer.instance;
        }
        serializable = object.findJsonValueMethod();
        if (serializable != null) {
            object = serializable.getAnnotated();
            if (serializerProvider.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess((Member)object);
            }
            return new JsonValueSerializer((Method)object, this.findSerializerFromAnnotation(serializerProvider, (Annotated)((Object)serializable)));
        }
        return null;
    }

    protected final JsonSerializer<?> findSerializerByLookup(JavaType class_, SerializationConfig jsonSerializer, BeanDescription beanDescription, boolean bl2) {
        jsonSerializer = _concrete.get(class_ = class_.getRawClass().getName());
        if (jsonSerializer == null && (class_ = _concreteLazy.get(class_)) != null) {
            try {
                jsonSerializer = class_.newInstance();
                return jsonSerializer;
            }
            catch (Exception var2_3) {
                throw new IllegalStateException("Failed to instantiate standard serializer (of type " + class_.getName() + "): " + var2_3.getMessage(), var2_3);
            }
        }
        return jsonSerializer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final JsonSerializer<?> findSerializerByPrimaryType(SerializerProvider object, JavaType javaType, BeanDescription beanDescription, boolean bl2) throws JsonMappingException {
        Class<?> class_ = javaType.getRawClass();
        JsonSerializer<?> jsonSerializer = this.findOptionalStdSerializer((SerializerProvider)object, javaType, beanDescription, bl2);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        if (Calendar.class.isAssignableFrom(class_)) {
            return CalendarSerializer.instance;
        }
        if (Date.class.isAssignableFrom(class_)) {
            return DateSerializer.instance;
        }
        if (ByteBuffer.class.isAssignableFrom(class_)) {
            return new ByteBufferSerializer();
        }
        if (InetAddress.class.isAssignableFrom(class_)) {
            return new InetAddressSerializer();
        }
        if (InetSocketAddress.class.isAssignableFrom(class_)) {
            return new InetSocketAddressSerializer();
        }
        if (TimeZone.class.isAssignableFrom(class_)) {
            return new TimeZoneSerializer();
        }
        if (Charset.class.isAssignableFrom(class_)) {
            return ToStringSerializer.instance;
        }
        if (Number.class.isAssignableFrom(class_)) {
            object = beanDescription.findExpectedFormat(null);
            if (object == null) return NumberSerializer.instance;
            switch (.$SwitchMap$com$fasterxml$jackson$annotation$JsonFormat$Shape[object.getShape().ordinal()]) {
                default: {
                    return NumberSerializer.instance;
                }
                case 1: {
                    return ToStringSerializer.instance;
                }
                case 2: 
                case 3: 
            }
            return null;
        }
        if (!Enum.class.isAssignableFrom(class_)) return null;
        return this.buildEnumSerializer(object.getConfig(), javaType, beanDescription);
    }

    protected JsonSerializer<Object> findSerializerFromAnnotation(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object object = serializerProvider.getAnnotationIntrospector().findSerializer(annotated);
        if (object == null) {
            return null;
        }
        return this.findConvertingSerializer(serializerProvider, annotated, serializerProvider.serializerInstance(annotated, object));
    }

    public SerializerFactoryConfig getFactoryConfig() {
        return this._factoryConfig;
    }

    protected boolean isIndexedList(Class<?> class_) {
        return RandomAccess.class.isAssignableFrom(class_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected <T extends JavaType> T modifyTypeByAnnotation(SerializationConfig serializationConfig, Annotated annotated, T t2) {
        Class<?> class_ = serializationConfig.getAnnotationIntrospector().findSerializationType(annotated);
        Object object = t2;
        if (class_ == null) return BasicSerializerFactory.modifySecondaryTypesByAnnotation(serializationConfig, annotated, object);
        try {
            object = t2.widenBy(class_);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Failed to widen type " + t2 + " with concrete-type annotation (value " + class_.getName() + "), method '" + annotated.getName() + "': " + illegalArgumentException.getMessage());
        }
        return BasicSerializerFactory.modifySecondaryTypesByAnnotation(serializationConfig, annotated, object);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected boolean usesStaticTyping(SerializationConfig serializationConfig, BeanDescription object, TypeSerializer typeSerializer) {
        if (typeSerializer != null) {
            return false;
        }
        object = serializationConfig.getAnnotationIntrospector().findSerializationTyping(object.getClassInfo());
        if (object == null) return serializationConfig.isEnabled(MapperFeature.USE_STATIC_TYPING);
        if (object == JsonSerialize.Typing.DEFAULT_TYPING) return serializationConfig.isEnabled(MapperFeature.USE_STATIC_TYPING);
        if (object != JsonSerialize.Typing.STATIC) return false;
        return true;
    }

    @Override
    public final SerializerFactory withAdditionalKeySerializers(Serializers serializers) {
        return this.withConfig(this._factoryConfig.withAdditionalKeySerializers(serializers));
    }

    @Override
    public final SerializerFactory withAdditionalSerializers(Serializers serializers) {
        return this.withConfig(this._factoryConfig.withAdditionalSerializers(serializers));
    }

    public abstract SerializerFactory withConfig(SerializerFactoryConfig var1);

    @Override
    public final SerializerFactory withSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
        return this.withConfig(this._factoryConfig.withSerializerModifier(beanSerializerModifier));
    }

}

