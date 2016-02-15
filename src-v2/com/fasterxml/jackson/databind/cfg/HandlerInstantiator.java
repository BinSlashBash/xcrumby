/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.util.Converter;

public abstract class HandlerInstantiator {
    public Converter<?, ?> converterInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<?> class_) {
        return null;
    }

    public abstract JsonDeserializer<?> deserializerInstance(DeserializationConfig var1, Annotated var2, Class<?> var3);

    public abstract KeyDeserializer keyDeserializerInstance(DeserializationConfig var1, Annotated var2, Class<?> var3);

    public PropertyNamingStrategy namingStrategyInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<?> class_) {
        return null;
    }

    public ObjectIdGenerator<?> objectIdGeneratorInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<?> class_) {
        return null;
    }

    public ObjectIdResolver resolverIdGeneratorInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<?> class_) {
        return null;
    }

    public abstract JsonSerializer<?> serializerInstance(SerializationConfig var1, Annotated var2, Class<?> var3);

    public abstract TypeIdResolver typeIdResolverInstance(MapperConfig<?> var1, Annotated var2, Class<?> var3);

    public abstract TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> var1, Annotated var2, Class<?> var3);

    public ValueInstantiator valueInstantiatorInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<?> class_) {
        return null;
    }
}

