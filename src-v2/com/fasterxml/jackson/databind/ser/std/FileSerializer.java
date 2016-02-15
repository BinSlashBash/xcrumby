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
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

public class FileSerializer
extends StdScalarSerializer<File> {
    public FileSerializer() {
        super(File.class);
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
    public void serialize(File file, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeString(file.getAbsolutePath());
    }
}

