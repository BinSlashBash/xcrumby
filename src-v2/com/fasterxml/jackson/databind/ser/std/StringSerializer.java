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
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.NonTypedScalarSerializerBase;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public final class StringSerializer
extends NonTypedScalarSerializerBase<String> {
    public StringSerializer() {
        super(String.class);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        if (jsonFormatVisitorWrapper != null) {
            jsonFormatVisitorWrapper.expectStringFormat(javaType);
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("string", true);
    }

    @Override
    public boolean isEmpty(String string2) {
        if (string2 == null || string2.length() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void serialize(String string2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(string2);
    }
}

