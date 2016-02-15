/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public class UnknownSerializer
extends StdSerializer<Object> {
    public UnknownSerializer() {
        super(Object.class);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectAnyFormat(javaType);
    }

    protected void failForEmpty(Object object) throws JsonMappingException {
        throw new JsonMappingException("No serializer found for class " + object.getClass().getName() + " and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) )");
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        return null;
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonMappingException {
        if (serializerProvider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(object);
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeEndObject();
    }

    @Override
    public final void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        if (serializerProvider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(object);
        }
        typeSerializer.writeTypePrefixForObject(object, jsonGenerator);
        typeSerializer.writeTypeSuffixForObject(object, jsonGenerator);
    }
}

