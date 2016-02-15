/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetSerializer
extends AsArraySerializerBase<EnumSet<? extends Enum<?>>> {
    public EnumSetSerializer(JavaType javaType, BeanProperty beanProperty) {
        super(EnumSet.class, javaType, true, null, beanProperty, null);
    }

    public EnumSetSerializer(EnumSetSerializer enumSetSerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        super(enumSetSerializer, beanProperty, typeSerializer, jsonSerializer);
    }

    public EnumSetSerializer _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return this;
    }

    @Override
    public boolean hasSingleElement(EnumSet<? extends Enum<?>> enumSet) {
        if (enumSet.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty(EnumSet<? extends Enum<?>> enumSet) {
        if (enumSet == null || enumSet.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void serializeContents(EnumSet<? extends Enum<?>> jsonSerializer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        JsonSerializer<Object> jsonSerializer2 = this._elementSerializer;
        Iterator iterator = jsonSerializer.iterator();
        jsonSerializer = jsonSerializer2;
        while (iterator.hasNext()) {
            Enum enum_ = iterator.next();
            jsonSerializer2 = jsonSerializer;
            if (jsonSerializer == null) {
                jsonSerializer2 = serializerProvider.findValueSerializer(enum_.getDeclaringClass(), this._property);
            }
            jsonSerializer2.serialize(enum_, jsonGenerator, serializerProvider);
            jsonSerializer = jsonSerializer2;
        }
    }

    public EnumSetSerializer withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        return new EnumSetSerializer(this, beanProperty, typeSerializer, jsonSerializer);
    }
}

