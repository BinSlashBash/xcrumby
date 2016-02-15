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
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

@JacksonStdImpl
public class SerializableSerializer
extends StdSerializer<JsonSerializable> {
    private static final AtomicReference<ObjectMapper> _mapperReference;
    public static final SerializableSerializer instance;

    static {
        instance = new SerializableSerializer();
        _mapperReference = new AtomicReference();
    }

    protected SerializableSerializer() {
        super(JsonSerializable.class);
    }

    private static final ObjectMapper _getObjectMapper() {
        synchronized (SerializableSerializer.class) {
            ObjectMapper objectMapper;
            block5 : {
                ObjectMapper objectMapper2;
                objectMapper = objectMapper2 = _mapperReference.get();
                if (objectMapper2 != null) break block5;
                objectMapper = new ObjectMapper();
                _mapperReference.set(objectMapper);
            }
            return objectMapper;
            finally {
            }
        }
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectAnyFormat(javaType);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider object, Type object2) throws JsonMappingException {
        String string2;
        ObjectNode objectNode = this.createObjectNode();
        Object object3 = "any";
        Object object4 = null;
        object = null;
        String string3 = string2 = null;
        Object object5 = object4;
        Object object6 = object3;
        if (object2 != null) {
            object2 = TypeFactory.rawClass((Type)object2);
            string3 = string2;
            object5 = object4;
            object6 = object3;
            if (object2.isAnnotationPresent(JsonSerializableSchema.class)) {
                object3 = (JsonSerializableSchema)object2.getAnnotation(JsonSerializableSchema.class);
                object2 = object3.schemaType();
                if (!"##irrelevant".equals(object3.schemaObjectPropertiesDefinition())) {
                    object = object3.schemaObjectPropertiesDefinition();
                }
                string3 = string2;
                object5 = object;
                object6 = object2;
                if (!"##irrelevant".equals(object3.schemaItemDefinition())) {
                    string3 = object3.schemaItemDefinition();
                    object6 = object2;
                    object5 = object;
                }
            }
        }
        objectNode.put("type", (String)object6);
        if (object5 != null) {
            objectNode.put("properties", SerializableSerializer._getObjectMapper().readTree((String)object5));
        }
        if (string3 == null) return objectNode;
        try {
            objectNode.put("items", SerializableSerializer._getObjectMapper().readTree(string3));
            return objectNode;
        }
        catch (IOException iOException2) {
            throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaItemDefinition value");
        }
        catch (IOException iOException) {
            throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaObjectPropertiesDefinition value");
        }
    }

    @Override
    public void serialize(JsonSerializable jsonSerializable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonSerializable.serialize(jsonGenerator, serializerProvider);
    }

    @Override
    public final void serializeWithType(JsonSerializable jsonSerializable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        jsonSerializable.serializeWithType(jsonGenerator, serializerProvider, typeSerializer);
    }
}

