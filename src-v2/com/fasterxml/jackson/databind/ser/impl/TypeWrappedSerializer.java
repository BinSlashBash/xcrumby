/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public final class TypeWrappedSerializer
extends JsonSerializer<Object> {
    protected final JsonSerializer<Object> _serializer;
    protected final TypeSerializer _typeSerializer;

    public TypeWrappedSerializer(TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        this._typeSerializer = typeSerializer;
        this._serializer = jsonSerializer;
    }

    @Override
    public Class<Object> handledType() {
        return Object.class;
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        this._serializer.serializeWithType(object, jsonGenerator, serializerProvider, this._typeSerializer);
    }

    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        this._serializer.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
    }
}

