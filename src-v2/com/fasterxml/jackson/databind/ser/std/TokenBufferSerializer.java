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
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class TokenBufferSerializer
extends StdSerializer<TokenBuffer> {
    public TokenBufferSerializer() {
        super(TokenBuffer.class);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectAnyFormat(javaType);
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("any", true);
    }

    @Override
    public void serialize(TokenBuffer tokenBuffer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        tokenBuffer.serialize(jsonGenerator);
    }

    @Override
    public final void serializeWithType(TokenBuffer tokenBuffer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForScalar(tokenBuffer, jsonGenerator);
        this.serialize(tokenBuffer, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(tokenBuffer, jsonGenerator);
    }
}

