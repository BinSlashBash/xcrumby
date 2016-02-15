/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;

public interface Deserializers {
    public JsonDeserializer<?> findArrayDeserializer(ArrayType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer<?> var5) throws JsonMappingException;

    public JsonDeserializer<?> findBeanDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException;

    public JsonDeserializer<?> findCollectionDeserializer(CollectionType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer<?> var5) throws JsonMappingException;

    public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType var1, DeserializationConfig var2, BeanDescription var3, TypeDeserializer var4, JsonDeserializer<?> var5) throws JsonMappingException;

    public JsonDeserializer<?> findEnumDeserializer(Class<?> var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException;

    public JsonDeserializer<?> findMapDeserializer(MapType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer<?> var6) throws JsonMappingException;

    public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType var1, DeserializationConfig var2, BeanDescription var3, KeyDeserializer var4, TypeDeserializer var5, JsonDeserializer<?> var6) throws JsonMappingException;

    public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException;

    public static class Base
    implements Deserializers {
        @Override
        public JsonDeserializer<?> findArrayDeserializer(ArrayType arrayType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findBeanDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findCollectionDeserializer(CollectionType collectionType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType collectionLikeType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findEnumDeserializer(Class<?> class_, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findMapDeserializer(MapType mapType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType mapLikeType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> class_, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
            return null;
        }
    }

}

