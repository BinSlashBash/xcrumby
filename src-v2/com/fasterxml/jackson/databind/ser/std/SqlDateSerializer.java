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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;

@JacksonStdImpl
public class SqlDateSerializer
extends DateTimeSerializerBase<Date> {
    public SqlDateSerializer() {
        this(Boolean.FALSE);
    }

    protected SqlDateSerializer(Boolean bl2) {
        super(Date.class, bl2, null);
    }

    @Override
    protected long _timestamp(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        this._acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType, this._useTimestamp);
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._useTimestamp.booleanValue()) {
            jsonGenerator.writeNumber(this._timestamp(date));
            return;
        }
        jsonGenerator.writeString(date.toString());
    }

    public SqlDateSerializer withFormat(Boolean bl2, DateFormat dateFormat) {
        return new SqlDateSerializer(bl2);
    }
}

