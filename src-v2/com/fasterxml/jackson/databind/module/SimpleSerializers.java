/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SimpleSerializers
extends Serializers.Base
implements Serializable {
    private static final long serialVersionUID = 8531646511998456779L;
    protected HashMap<ClassKey, JsonSerializer<?>> _classMappings = null;
    protected boolean _hasEnumSerializer = false;
    protected HashMap<ClassKey, JsonSerializer<?>> _interfaceMappings = null;

    public SimpleSerializers() {
    }

    public SimpleSerializers(List<JsonSerializer<?>> list) {
        this.addSerializers(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addSerializer(Class<?> class_, JsonSerializer<?> jsonSerializer) {
        ClassKey classKey = new ClassKey(class_);
        if (class_.isInterface()) {
            if (this._interfaceMappings == null) {
                this._interfaceMappings = new HashMap();
            }
            this._interfaceMappings.put(classKey, jsonSerializer);
            return;
        } else {
            if (this._classMappings == null) {
                this._classMappings = new HashMap();
            }
            this._classMappings.put(classKey, jsonSerializer);
            if (class_ != Enum.class) return;
            {
                this._hasEnumSerializer = true;
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected JsonSerializer<?> _findInterfaceMapping(Class<?> jsonSerializer, ClassKey classKey) {
        Class<?>[] arrclass = jsonSerializer.getInterfaces();
        int n2 = arrclass.length;
        int n3 = 0;
        while (n3 < n2) {
            JsonSerializer jsonSerializer2;
            Class class_ = arrclass[n3];
            classKey.reset(class_);
            jsonSerializer = this._interfaceMappings.get(classKey);
            if (jsonSerializer != null) {
                return jsonSerializer;
            }
            jsonSerializer = jsonSerializer2 = this._findInterfaceMapping(class_, classKey);
            if (jsonSerializer2 != null) return jsonSerializer;
            ++n3;
        }
        return null;
    }

    public void addSerializer(JsonSerializer<?> jsonSerializer) {
        Class class_ = jsonSerializer.handledType();
        if (class_ == null || class_ == Object.class) {
            throw new IllegalArgumentException("JsonSerializer of type " + jsonSerializer.getClass().getName() + " does not define valid handledType() -- must either register with method that takes type argument " + " or make serializer extend 'com.fasterxml.jackson.databind.ser.std.StdSerializer'");
        }
        this._addSerializer(class_, jsonSerializer);
    }

    public <T> void addSerializer(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        this._addSerializer(class_, jsonSerializer);
    }

    public void addSerializers(List<JsonSerializer<?>> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.addSerializer((JsonSerializer)object.next());
        }
    }

    @Override
    public JsonSerializer<?> findArraySerializer(SerializationConfig serializationConfig, ArrayType arrayType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, arrayType, beanDescription);
    }

    @Override
    public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig serializationConfig, CollectionLikeType collectionLikeType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, collectionLikeType, beanDescription);
    }

    @Override
    public JsonSerializer<?> findCollectionSerializer(SerializationConfig serializationConfig, CollectionType collectionType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, collectionType, beanDescription);
    }

    @Override
    public JsonSerializer<?> findMapLikeSerializer(SerializationConfig serializationConfig, MapLikeType mapLikeType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
        return this.findSerializer(serializationConfig, mapLikeType, beanDescription);
    }

    @Override
    public JsonSerializer<?> findMapSerializer(SerializationConfig serializationConfig, MapType mapType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
        return this.findSerializer(serializationConfig, mapType, beanDescription);
    }

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig serializable, JavaType jsonSerializer, BeanDescription object) {
        serializable = jsonSerializer.getRawClass();
        object = new ClassKey(serializable);
        if (serializable.isInterface()) {
            if (this._interfaceMappings != null && (jsonSerializer = this._interfaceMappings.get(object)) != null) {
                return jsonSerializer;
            }
        } else if (this._classMappings != null) {
            JsonSerializer jsonSerializer2 = this._classMappings.get(object);
            if (jsonSerializer2 != null) {
                return jsonSerializer2;
            }
            if (this._hasEnumSerializer && jsonSerializer.isEnumType()) {
                object.reset(Enum.class);
                jsonSerializer = this._classMappings.get(object);
                if (jsonSerializer != null) {
                    return jsonSerializer;
                }
            }
            for (jsonSerializer = serializable; jsonSerializer != null; jsonSerializer = jsonSerializer.getSuperclass()) {
                object.reset(jsonSerializer);
                jsonSerializer2 = this._classMappings.get(object);
                if (jsonSerializer2 == null) continue;
                return jsonSerializer2;
            }
        }
        if (this._interfaceMappings != null) {
            jsonSerializer = this._findInterfaceMapping(serializable, (ClassKey)object);
            if (jsonSerializer != null) {
                return jsonSerializer;
            }
            if (!serializable.isInterface()) {
                while ((serializable = serializable.getSuperclass()) != null) {
                    jsonSerializer = this._findInterfaceMapping(serializable, (ClassKey)object);
                    if (jsonSerializer == null) continue;
                    return jsonSerializer;
                }
            }
        }
        return null;
    }
}

