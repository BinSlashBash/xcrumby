/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.util.Collection;

public interface TypeResolverBuilder<T extends TypeResolverBuilder<T>> {
    public TypeDeserializer buildTypeDeserializer(DeserializationConfig var1, JavaType var2, Collection<NamedType> var3);

    public TypeSerializer buildTypeSerializer(SerializationConfig var1, JavaType var2, Collection<NamedType> var3);

    public T defaultImpl(Class<?> var1);

    public Class<?> getDefaultImpl();

    public T inclusion(JsonTypeInfo.As var1);

    public T init(JsonTypeInfo.Id var1, TypeIdResolver var2);

    public T typeIdVisibility(boolean var1);

    public T typeProperty(String var1);
}

