/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.NullifyingDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public abstract class TypeDeserializerBase
extends TypeDeserializer
implements Serializable {
    private static final long serialVersionUID = 278445030337366675L;
    protected final JavaType _baseType;
    protected final JavaType _defaultImpl;
    protected JsonDeserializer<Object> _defaultImplDeserializer;
    protected final HashMap<String, JsonDeserializer<Object>> _deserializers;
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;
    protected final boolean _typeIdVisible;
    protected final String _typePropertyName;

    /*
     * Enabled aggressive block sorting
     */
    protected TypeDeserializerBase(JavaType javaType, TypeIdResolver typeIdResolver, String string2, boolean bl2, Class<?> class_) {
        this._baseType = javaType;
        this._idResolver = typeIdResolver;
        this._typePropertyName = string2;
        this._typeIdVisible = bl2;
        this._deserializers = new HashMap();
        this._defaultImpl = class_ == null ? null : javaType.forcedNarrowBy(class_);
        this._property = null;
    }

    protected TypeDeserializerBase(TypeDeserializerBase typeDeserializerBase, BeanProperty beanProperty) {
        this._baseType = typeDeserializerBase._baseType;
        this._idResolver = typeDeserializerBase._idResolver;
        this._typePropertyName = typeDeserializerBase._typePropertyName;
        this._typeIdVisible = typeDeserializerBase._typeIdVisible;
        this._deserializers = typeDeserializerBase._deserializers;
        this._defaultImpl = typeDeserializerBase._defaultImpl;
        this._defaultImplDeserializer = typeDeserializerBase._defaultImplDeserializer;
        this._property = beanProperty;
    }

    @Deprecated
    protected Object _deserializeWithNativeTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return this._deserializeWithNativeTypeId(jsonParser, deserializationContext, jsonParser.getTypeId());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _deserializeWithNativeTypeId(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException {
        if (object == null) {
            if (this._defaultImpl == null) {
                throw deserializationContext.mappingException("No (native) type id found when one was expected for polymorphic type handling");
            }
            object = this._findDefaultImplDeserializer(deserializationContext);
            return object.deserialize(jsonParser, deserializationContext);
        }
        object = object instanceof String ? (String)object : String.valueOf(object);
        object = this._findDeserializer(deserializationContext, (String)object);
        return object.deserialize(jsonParser, deserializationContext);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final JsonDeserializer<Object> _findDefaultImplDeserializer(DeserializationContext object) throws IOException {
        if (this._defaultImpl == null) {
            if (object.isEnabled(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)) return null;
            return NullifyingDeserializer.instance;
        }
        if (ClassUtil.isBogusClass(this._defaultImpl.getRawClass())) {
            return NullifyingDeserializer.instance;
        }
        JavaType javaType = this._defaultImpl;
        synchronized (javaType) {
            if (this._defaultImplDeserializer != null) return this._defaultImplDeserializer;
            this._defaultImplDeserializer = object.findContextualValueDeserializer(this._defaultImpl, this._property);
            return this._defaultImplDeserializer;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext jsonDeserializer, String string2) throws IOException {
        HashMap<String, JsonDeserializer<Object>> hashMap = this._deserializers;
        synchronized (hashMap) {
            Object object;
            Object object2 = object = this._deserializers.get(string2);
            if (object != null) return object2;
            object2 = this._idResolver instanceof TypeIdResolverBase ? ((TypeIdResolverBase)this._idResolver).typeFromId((DatabindContext)((Object)jsonDeserializer), string2) : this._idResolver.typeFromId(string2);
            if (object2 == null) {
                if (this._defaultImpl == null) {
                    throw jsonDeserializer.unknownTypeException(this._baseType, string2);
                }
                jsonDeserializer = this._findDefaultImplDeserializer((DeserializationContext)((Object)jsonDeserializer));
            } else {
                object = object2;
                if (this._baseType != null) {
                    object = object2;
                    if (this._baseType.getClass() == object2.getClass()) {
                        object = this._baseType.narrowBy(object2.getRawClass());
                    }
                }
                jsonDeserializer = jsonDeserializer.findContextualValueDeserializer((JavaType)object, this._property);
            }
            this._deserializers.put(string2, jsonDeserializer);
            return jsonDeserializer;
        }
    }

    public String baseTypeName() {
        return this._baseType.getRawClass().getName();
    }

    @Override
    public abstract TypeDeserializer forProperty(BeanProperty var1);

    @Override
    public Class<?> getDefaultImpl() {
        if (this._defaultImpl == null) {
            return null;
        }
        return this._defaultImpl.getRawClass();
    }

    @Override
    public final String getPropertyName() {
        return this._typePropertyName;
    }

    @Override
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }

    @Override
    public abstract JsonTypeInfo.As getTypeInclusion();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[').append(this.getClass().getName());
        stringBuilder.append("; base-type:").append(this._baseType);
        stringBuilder.append("; id-resolver: ").append(this._idResolver);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

