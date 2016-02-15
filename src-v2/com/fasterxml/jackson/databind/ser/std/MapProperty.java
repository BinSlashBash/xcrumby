/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.io.IOException;

public class MapProperty
extends PropertyWriter {
    protected Object _key;
    protected JsonSerializer<Object> _keySerializer;
    protected TypeSerializer _typeSerializer;
    protected Object _value;
    protected JsonSerializer<Object> _valueSerializer;

    public MapProperty(TypeSerializer typeSerializer) {
        this._typeSerializer = typeSerializer;
    }

    @Override
    public void depositSchemaProperty(JsonObjectFormatVisitor jsonObjectFormatVisitor) throws JsonMappingException {
    }

    @Deprecated
    @Override
    public void depositSchemaProperty(ObjectNode objectNode, SerializerProvider serializerProvider) throws JsonMappingException {
    }

    @Override
    public PropertyName getFullName() {
        return new PropertyName(this.getName());
    }

    @Override
    public String getName() {
        if (this._key instanceof String) {
            return (String)this._key;
        }
        return String.valueOf(this._key);
    }

    public void reset(Object object, Object object2, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2) {
        this._key = object;
        this._value = object2;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
    }

    @Override
    public void serializeAsElement(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        if (this._typeSerializer == null) {
            this._valueSerializer.serialize(this._value, jsonGenerator, serializerProvider);
            return;
        }
        this._valueSerializer.serializeWithType(this._value, jsonGenerator, serializerProvider, this._typeSerializer);
    }

    @Override
    public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        this._keySerializer.serialize(this._key, jsonGenerator, serializerProvider);
        if (this._typeSerializer == null) {
            this._valueSerializer.serialize(this._value, jsonGenerator, serializerProvider);
            return;
        }
        this._valueSerializer.serializeWithType(this._value, jsonGenerator, serializerProvider, this._typeSerializer);
    }

    @Override
    public void serializeAsOmittedField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        if (!jsonGenerator.canOmitFields()) {
            jsonGenerator.writeOmittedField(this.getName());
        }
    }

    @Override
    public void serializeAsPlaceholder(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        jsonGenerator.writeNull();
    }
}

