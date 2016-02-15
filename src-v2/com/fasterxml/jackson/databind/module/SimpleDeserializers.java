/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimpleDeserializers
implements Deserializers,
Serializable {
    private static final long serialVersionUID = -3006673354353448880L;
    protected HashMap<ClassKey, JsonDeserializer<?>> _classMappings = null;
    protected boolean _hasEnumDeserializer = false;

    public SimpleDeserializers() {
    }

    public SimpleDeserializers(Map<Class<?>, JsonDeserializer<?>> map) {
        this.addDeserializers(map);
    }

    public <T> void addDeserializer(Class<T> class_, JsonDeserializer<? extends T> jsonDeserializer) {
        ClassKey classKey = new ClassKey(class_);
        if (this._classMappings == null) {
            this._classMappings = new HashMap();
        }
        this._classMappings.put(classKey, jsonDeserializer);
        if (class_ == Enum.class) {
            this._hasEnumDeserializer = true;
        }
    }

    public void addDeserializers(Map<Class<?>, JsonDeserializer<?>> object) {
        for (Map.Entry entry : object.entrySet()) {
            this.addDeserializer((Class)entry.getKey(), (JsonDeserializer)entry.getValue());
        }
    }

    @Override
    public JsonDeserializer<?> findArrayDeserializer(ArrayType arrayType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(arrayType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(javaType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findCollectionDeserializer(CollectionType collectionType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(collectionType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType collectionLikeType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(collectionLikeType.getRawClass()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonDeserializer<?> findEnumDeserializer(Class<?> class_, DeserializationConfig jsonDeserializer, BeanDescription jsonDeserializer2) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        jsonDeserializer = jsonDeserializer2 = this._classMappings.get(new ClassKey(class_));
        if (jsonDeserializer2 != null) return jsonDeserializer;
        jsonDeserializer = jsonDeserializer2;
        if (!this._hasEnumDeserializer) return jsonDeserializer;
        jsonDeserializer = jsonDeserializer2;
        if (!class_.isEnum()) return jsonDeserializer;
        return this._classMappings.get(new ClassKey(Enum.class));
    }

    @Override
    public JsonDeserializer<?> findMapDeserializer(MapType mapType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(mapType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType mapLikeType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(mapLikeType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> class_, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(class_));
    }
}

