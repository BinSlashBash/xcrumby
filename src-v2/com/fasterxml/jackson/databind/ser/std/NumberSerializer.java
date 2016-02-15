/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

@JacksonStdImpl
public final class NumberSerializer
extends StdScalarSerializer<Number> {
    public static final NumberSerializer instance = new NumberSerializer();

    public NumberSerializer() {
        super(Number.class);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper object, JavaType javaType) throws JsonMappingException {
        if ((object = object.expectNumberFormat(javaType)) != null) {
            object.numberType(JsonParser.NumberType.BIG_DECIMAL);
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("number", true);
    }

    @Override
    public void serialize(Number number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (number instanceof BigDecimal) {
            jsonGenerator.writeNumber((BigDecimal)number);
            return;
        }
        if (number instanceof BigInteger) {
            jsonGenerator.writeNumber((BigInteger)number);
            return;
        }
        if (number instanceof Integer) {
            jsonGenerator.writeNumber(number.intValue());
            return;
        }
        if (number instanceof Long) {
            jsonGenerator.writeNumber(number.longValue());
            return;
        }
        if (number instanceof Double) {
            jsonGenerator.writeNumber(number.doubleValue());
            return;
        }
        if (number instanceof Float) {
            jsonGenerator.writeNumber(number.floatValue());
            return;
        }
        if (number instanceof Byte || number instanceof Short) {
            jsonGenerator.writeNumber(number.intValue());
            return;
        }
        jsonGenerator.writeNumber(number.toString());
    }
}

