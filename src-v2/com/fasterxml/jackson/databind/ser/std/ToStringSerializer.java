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
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class ToStringSerializer
extends StdSerializer<Object> {
    public static final ToStringSerializer instance = new ToStringSerializer();

    public ToStringSerializer() {
        super(Object.class);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        if (jsonFormatVisitorWrapper != null) {
            jsonFormatVisitorWrapper.expectStringFormat(javaType);
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        return this.createSchemaNode("string", true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean isEmpty(Object object) {
        if (object == null || (object = object.toString()) == null || object.length() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeString(object.toString());
    }

    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForScalar(object, jsonGenerator);
        this.serialize(object, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(object, jsonGenerator);
    }
}

