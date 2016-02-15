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
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.NonTypedScalarSerializerBase;
import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public final class BooleanSerializer
extends NonTypedScalarSerializerBase<Boolean> {
    protected final boolean _forPrimitive;

    public BooleanSerializer(boolean bl2) {
        super(Boolean.class);
        this._forPrimitive = bl2;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        if (jsonFormatVisitorWrapper != null) {
            jsonFormatVisitorWrapper.expectBooleanFormat(javaType);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        boolean bl2;
        if (!this._forPrimitive) {
            bl2 = true;
            do {
                return this.createSchemaNode("boolean", bl2);
                break;
            } while (true);
        }
        bl2 = false;
        return this.createSchemaNode("boolean", bl2);
    }

    @Override
    public void serialize(Boolean bl2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeBoolean(bl2);
    }
}

