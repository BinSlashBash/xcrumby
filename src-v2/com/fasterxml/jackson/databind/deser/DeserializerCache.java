/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class DeserializerCache
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _cachedDeserializers = new ConcurrentHashMap(64, 0.75f, 2);
    protected final HashMap<JavaType, JsonDeserializer<Object>> _incompleteDeserializers = new HashMap(8);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Class<?> _verifyAsClass(Object object, String object2, Class<?> class_) {
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
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private JavaType modifyTypeByAnnotation(DeserializationContext var1_1, Annotated var2_5, JavaType var3_6) throws JsonMappingException {
        var6_7 = var1_1.getAnnotationIntrospector();
        var5_8 = var6_7.findDeserializationType(var2_5, var3_6);
        var4_23 = var3_6;
        if (var5_8 != null) {
            var4_23 = var3_6.narrowBy(var5_8);
        }
        var5_9 = var4_23;
        if (var4_23.isContainerType() == false) return var5_21;
        var5_10 = var6_7.findDeserializationKeyType(var2_5, (JavaType)var4_23.getKeyType());
        var3_6 = var4_23;
        if (var5_10 == null) ** GOTO lbl21
        if (!(var4_23 instanceof MapLikeType)) {
            throw new JsonMappingException("Illegal key-type annotation: type " + var4_23 + " is not a Map(-like) type");
        }
        ** GOTO lbl17
        catch (IllegalArgumentException var1_2) {
            throw new JsonMappingException("Failed to narrow type " + var3_6 + " with concrete-type annotation (value " + var5_8.getName() + "), method '" + var2_5.getName() + "': " + var1_2.getMessage(), null, var1_2);
        }
lbl17: // 1 sources:
        try {
            var3_6 = ((MapLikeType)var4_23).narrowKey(var5_10);
        }
        catch (IllegalArgumentException var1_3) {
            throw new JsonMappingException("Failed to narrow key type " + var4_23 + " with key-type annotation (" + var5_10.getName() + "): " + var1_3.getMessage(), null, var1_3);
        }
lbl21: // 2 sources:
        var5_11 = var3_6.getKeyType();
        var4_23 = var3_6;
        if (var5_11 != null) {
            var4_23 = var3_6;
            if (var5_11.getValueHandler() == null) {
                var5_12 = var6_7.findKeyDeserializer(var2_5);
                var4_23 = var3_6;
                if (var5_12 != null) {
                    var5_13 = var1_1.keyDeserializerInstance(var2_5, var5_12);
                    var4_23 = var3_6;
                    if (var5_13 != null) {
                        var4_23 = ((MapLikeType)var3_6).withKeyValueHandler(var5_13);
                        var4_23.getKeyType();
                    }
                }
            }
        }
        var5_15 = var6_7.findDeserializationContentType(var2_5, (JavaType)var4_23.getContentType());
        var3_6 = var4_23;
        if (var5_15 != null) {
            var3_6 = var4_23.narrowContentsBy(var5_15);
        }
        var5_16 = var3_6;
        if (var3_6.getContentType().getValueHandler() != null) return var5_21;
        var6_7 = var6_7.findContentDeserializer(var2_5);
        var5_17 = var3_6;
        if (var6_7 == null) return var5_21;
        var4_23 = null;
        if (var6_7 instanceof JsonDeserializer) {
            var1_1 = (JsonDeserializer)var6_7;
        } else {
            var5_22 = this._verifyAsClass(var6_7, "findContentDeserializer", JsonDeserializer.None.class);
            if (var5_22 != null) {
                var4_23 = var1_1.deserializerInstance(var2_5, var5_22);
            }
        }
        var5_19 = var3_6;
        if (var4_23 == null) return var5_21;
        var5_20 = var3_6.withContentValueHandler(var4_23);
        return var5_21;
        catch (IllegalArgumentException var1_4) {
            throw new JsonMappingException("Failed to narrow content type " + var4_23 + " with content-type annotation (" + var5_15.getName() + "): " + var1_4.getMessage(), null, var1_4);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonDeserializer<Object> _createAndCache2(DeserializationContext jsonDeserializer, DeserializerFactory jsonDeserializer2, JavaType javaType) throws JsonMappingException {
        try {
            jsonDeserializer2 = this._createDeserializer((DeserializationContext)((Object)jsonDeserializer), (DeserializerFactory)((Object)jsonDeserializer2), javaType);
            if (jsonDeserializer2 == null) {
                return null;
            }
        }
        catch (IllegalArgumentException var1_2) {
            throw new JsonMappingException(var1_2.getMessage(), null, var1_2);
        }
        boolean bl2 = jsonDeserializer2 instanceof ResolvableDeserializer;
        boolean bl3 = jsonDeserializer2.isCachable();
        if (bl2) {
            this._incompleteDeserializers.put(javaType, jsonDeserializer2);
            ((ResolvableDeserializer)((Object)jsonDeserializer2)).resolve((DeserializationContext)((Object)jsonDeserializer));
            this._incompleteDeserializers.remove(javaType);
        }
        jsonDeserializer = jsonDeserializer2;
        if (!bl3) return jsonDeserializer;
        this._cachedDeserializers.put(javaType, jsonDeserializer2);
        return jsonDeserializer2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected JsonDeserializer<Object> _createAndCacheValueDeserializer(DeserializationContext object, DeserializerFactory deserializerFactory, JavaType javaType) throws JsonMappingException {
        HashMap<JavaType, JsonDeserializer<Object>> hashMap = this._incompleteDeserializers;
        synchronized (hashMap) {
            JsonDeserializer<Object> jsonDeserializer = this._findCachedDeserializer(javaType);
            if (jsonDeserializer != null) {
                return jsonDeserializer;
            }
            int n2 = this._incompleteDeserializers.size();
            if (n2 > 0 && (jsonDeserializer = this._incompleteDeserializers.get(javaType)) != null) {
                return jsonDeserializer;
            }
            try {
                object = this._createAndCache2((DeserializationContext)object, deserializerFactory, javaType);
                return object;
            }
            finally {
                if (n2 == 0 && this._incompleteDeserializers.size() > 0) {
                    this._incompleteDeserializers.clear();
                }
            }
        }
    }

    protected JsonDeserializer<Object> _createDeserializer(DeserializationContext deserializationContext, DeserializerFactory deserializerFactory, JavaType javaType) throws JsonMappingException {
        Converter<Object, Object> converter;
        DeserializationConfig deserializationConfig;
        Object object;
        block7 : {
            deserializationConfig = deserializationContext.getConfig();
            if (!javaType.isAbstract() && !javaType.isMapLikeType()) {
                converter = javaType;
                if (!javaType.isCollectionLikeType()) break block7;
            }
            converter = deserializerFactory.mapAbstractType(deserializationConfig, javaType);
        }
        if ((object = this.findDeserializerFromAnnotation(deserializationContext, (javaType = deserializationConfig.introspect((JavaType)((Object)converter))).getClassInfo())) != null) {
            return object;
        }
        JavaType javaType2 = this.modifyTypeByAnnotation(deserializationContext, javaType.getClassInfo(), (JavaType)((Object)converter));
        object = converter;
        if (javaType2 != converter) {
            object = javaType2;
            javaType = deserializationConfig.introspect(javaType2);
        }
        if ((converter = javaType.findPOJOBuilder()) != null) {
            return deserializerFactory.createBuilderBasedDeserializer(deserializationContext, (JavaType)object, (BeanDescription)((Object)javaType), converter);
        }
        converter = javaType.findDeserializationConverter();
        if (converter == null) {
            return this._createDeserializer2(deserializationContext, deserializerFactory, (JavaType)object, (BeanDescription)((Object)javaType));
        }
        javaType2 = converter.getInputType(deserializationContext.getTypeFactory());
        if (!javaType2.hasRawClass(object.getRawClass())) {
            javaType = deserializationConfig.introspect(javaType2);
        }
        return new StdDelegatingDeserializer<Object>(converter, javaType2, this._createDeserializer2(deserializationContext, deserializerFactory, javaType2, (BeanDescription)((Object)javaType)));
    }

    protected JsonDeserializer<?> _createDeserializer2(DeserializationContext deserializationContext, DeserializerFactory deserializerFactory, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        DeserializationConfig deserializationConfig = deserializationContext.getConfig();
        if (javaType.isEnumType()) {
            return deserializerFactory.createEnumDeserializer(deserializationContext, javaType, beanDescription);
        }
        if (javaType.isContainerType()) {
            JsonFormat.Value value;
            if (javaType.isArrayType()) {
                return deserializerFactory.createArrayDeserializer(deserializationContext, (ArrayType)javaType, beanDescription);
            }
            if (javaType.isMapLikeType()) {
                if ((javaType = (MapLikeType)javaType).isTrueMapType()) {
                    return deserializerFactory.createMapDeserializer(deserializationContext, (MapType)javaType, beanDescription);
                }
                return deserializerFactory.createMapLikeDeserializer(deserializationContext, (MapLikeType)javaType, beanDescription);
            }
            if (javaType.isCollectionLikeType() && ((value = beanDescription.findExpectedFormat(null)) == null || value.getShape() != JsonFormat.Shape.OBJECT)) {
                if ((javaType = (CollectionLikeType)javaType).isTrueCollectionType()) {
                    return deserializerFactory.createCollectionDeserializer(deserializationContext, (CollectionType)javaType, beanDescription);
                }
                return deserializerFactory.createCollectionLikeDeserializer(deserializationContext, (CollectionLikeType)javaType, beanDescription);
            }
        }
        if (JsonNode.class.isAssignableFrom(javaType.getRawClass())) {
            return deserializerFactory.createTreeDeserializer(deserializationConfig, javaType, beanDescription);
        }
        return deserializerFactory.createBeanDeserializer(deserializationContext, javaType, beanDescription);
    }

    protected JsonDeserializer<Object> _findCachedDeserializer(JavaType javaType) {
        if (javaType == null) {
            throw new IllegalArgumentException("Null JavaType passed");
        }
        return this._cachedDeserializers.get(javaType);
    }

    protected KeyDeserializer _handleUnknownKeyDeserializer(JavaType javaType) throws JsonMappingException {
        throw new JsonMappingException("Can not find a (Map) Key deserializer for type " + javaType);
    }

    protected JsonDeserializer<Object> _handleUnknownValueDeserializer(JavaType javaType) throws JsonMappingException {
        if (!ClassUtil.isConcrete(javaType.getRawClass())) {
            throw new JsonMappingException("Can not find a Value deserializer for abstract type " + javaType);
        }
        throw new JsonMappingException("Can not find a Value deserializer for type " + javaType);
    }

    public int cachedDeserializersCount() {
        return this._cachedDeserializers.size();
    }

    protected Converter<Object, Object> findConverter(DeserializationContext deserializationContext, Annotated annotated) throws JsonMappingException {
        Object object = deserializationContext.getAnnotationIntrospector().findDeserializationConverter(annotated);
        if (object == null) {
            return null;
        }
        return deserializationContext.converterInstance(annotated, object);
    }

    protected JsonDeserializer<Object> findConvertingDeserializer(DeserializationContext deserializationContext, Annotated object, JsonDeserializer<Object> jsonDeserializer) throws JsonMappingException {
        if ((object = this.findConverter(deserializationContext, (Annotated)object)) == null) {
            return jsonDeserializer;
        }
        return new StdDelegatingDeserializer<Object>((Converter<Object, Object>)object, object.getInputType(deserializationContext.getTypeFactory()), jsonDeserializer);
    }

    protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext deserializationContext, Annotated annotated) throws JsonMappingException {
        Object object = deserializationContext.getAnnotationIntrospector().findDeserializer(annotated);
        if (object == null) {
            return null;
        }
        return this.findConvertingDeserializer(deserializationContext, annotated, deserializationContext.deserializerInstance(annotated, object));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public KeyDeserializer findKeyDeserializer(DeserializationContext deserializationContext, DeserializerFactory object, JavaType javaType) throws JsonMappingException {
        KeyDeserializer keyDeserializer = object.createKeyDeserializer(deserializationContext, javaType);
        if (keyDeserializer == null) {
            return this._handleUnknownKeyDeserializer(javaType);
        }
        object = keyDeserializer;
        if (!(keyDeserializer instanceof ResolvableDeserializer)) return object;
        ((ResolvableDeserializer)((Object)keyDeserializer)).resolve(deserializationContext);
        return keyDeserializer;
    }

    public JsonDeserializer<Object> findValueDeserializer(DeserializationContext object, DeserializerFactory deserializerFactory, JavaType javaType) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer;
        Object object2 = jsonDeserializer = this._findCachedDeserializer(javaType);
        if (jsonDeserializer == null) {
            object2 = object = this._createAndCacheValueDeserializer((DeserializationContext)object, deserializerFactory, javaType);
            if (object == null) {
                object2 = this._handleUnknownValueDeserializer(javaType);
            }
        }
        return object2;
    }

    public void flushCachedDeserializers() {
        this._cachedDeserializers.clear();
    }

    public boolean hasValueDeserializerFor(DeserializationContext deserializationContext, DeserializerFactory deserializerFactory, JavaType javaType) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer;
        JsonDeserializer<Object> jsonDeserializer2 = jsonDeserializer = this._findCachedDeserializer(javaType);
        if (jsonDeserializer == null) {
            jsonDeserializer2 = this._createAndCacheValueDeserializer(deserializationContext, deserializerFactory, javaType);
        }
        if (jsonDeserializer2 != null) {
            return true;
        }
        return false;
    }

    Object writeReplace() {
        this._incompleteDeserializers.clear();
        return this;
    }
}

