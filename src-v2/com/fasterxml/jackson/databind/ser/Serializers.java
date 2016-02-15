/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;

public interface Serializers {
    public JsonSerializer<?> findArraySerializer(SerializationConfig var1, ArrayType var2, BeanDescription var3, TypeSerializer var4, JsonSerializer<Object> var5);

    public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig var1, CollectionLikeType var2, BeanDescription var3, TypeSerializer var4, JsonSerializer<Object> var5);

    public JsonSerializer<?> findCollectionSerializer(SerializationConfig var1, CollectionType var2, BeanDescription var3, TypeSerializer var4, JsonSerializer<Object> var5);

    public JsonSerializer<?> findMapLikeSerializer(SerializationConfig var1, MapLikeType var2, BeanDescription var3, JsonSerializer<Object> var4, TypeSerializer var5, JsonSerializer<Object> var6);

    public JsonSerializer<?> findMapSerializer(SerializationConfig var1, MapType var2, BeanDescription var3, JsonSerializer<Object> var4, TypeSerializer var5, JsonSerializer<Object> var6);

    public JsonSerializer<?> findSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3);

    public static class Base
    implements Serializers {
        @Override
        public JsonSerializer<?> findArraySerializer(SerializationConfig serializationConfig, ArrayType arrayType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            return null;
        }

        @Override
        public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig serializationConfig, CollectionLikeType collectionLikeType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            return null;
        }

        @Override
        public JsonSerializer<?> findCollectionSerializer(SerializationConfig serializationConfig, CollectionType collectionType, BeanDescription beanDescription, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            return null;
        }

        @Override
        public JsonSerializer<?> findMapLikeSerializer(SerializationConfig serializationConfig, MapLikeType mapLikeType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
            return null;
        }

        @Override
        public JsonSerializer<?> findMapSerializer(SerializationConfig serializationConfig, MapType mapType, BeanDescription beanDescription, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
            return null;
        }

        @Override
        public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) {
            return null;
        }
    }

}

