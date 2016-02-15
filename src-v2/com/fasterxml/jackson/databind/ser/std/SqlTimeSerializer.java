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
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;

@JacksonStdImpl
public class SqlTimeSerializer
extends StdScalarSerializer<Time> {
    public SqlTimeSerializer() {
        super(Time.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper object, JavaType javaType) throws JsonMappingException {
        if (object == null) {
            return;
        }
        if ((object = object.expectStringFormat(javaType)) != null) {
            object.format(JsonValueFormat.DATE_TIME);
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeString(time.toString());
    }
}

