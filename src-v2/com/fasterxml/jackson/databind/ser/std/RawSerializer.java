/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public class RawSerializer<T>
extends StdSerializer<T> {
    public RawSerializer(Class<?> class_) {
        super(class_, false);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectStringFormat(javaType);
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void serialize(T t2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeRawValue(t2.toString());
    }

    @Override
    public void serializeWithType(T t2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForScalar(t2, jsonGenerator);
        this.serialize(t2, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(t2, jsonGenerator);
    }
}

