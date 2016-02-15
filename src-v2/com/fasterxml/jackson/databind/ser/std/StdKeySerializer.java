/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

public class StdKeySerializer
extends StdSerializer<Object> {
    public StdKeySerializer() {
        super(Object.class);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectStringFormat(javaType);
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        return this.createSchemaNode("string");
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (object instanceof Date) {
            serializerProvider.defaultSerializeDateKey((Date)object, jsonGenerator);
            return;
        }
        jsonGenerator.writeFieldName(object.toString());
    }
}

