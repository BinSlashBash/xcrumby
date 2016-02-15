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

public class MapProperty extends PropertyWriter {
    protected Object _key;
    protected JsonSerializer<Object> _keySerializer;
    protected TypeSerializer _typeSerializer;
    protected Object _value;
    protected JsonSerializer<Object> _valueSerializer;

    public MapProperty(TypeSerializer typeSer) {
        this._typeSerializer = typeSer;
    }

    public void reset(Object key, Object value, JsonSerializer<Object> keySer, JsonSerializer<Object> valueSer) {
        this._key = key;
        this._value = value;
        this._keySerializer = keySer;
        this._valueSerializer = valueSer;
    }

    public String getName() {
        if (this._key instanceof String) {
            return (String) this._key;
        }
        return String.valueOf(this._key);
    }

    public PropertyName getFullName() {
        return new PropertyName(getName());
    }

    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        this._keySerializer.serialize(this._key, jgen, provider);
        if (this._typeSerializer == null) {
            this._valueSerializer.serialize(this._value, jgen, provider);
        } else {
            this._valueSerializer.serializeWithType(this._value, jgen, provider, this._typeSerializer);
        }
    }

    public void serializeAsOmittedField(Object pojo, JsonGenerator jgen, SerializerProvider provider) throws Exception {
        if (!jgen.canOmitFields()) {
            jgen.writeOmittedField(getName());
        }
    }

    public void serializeAsElement(Object pojo, JsonGenerator jgen, SerializerProvider provider) throws Exception {
        if (this._typeSerializer == null) {
            this._valueSerializer.serialize(this._value, jgen, provider);
        } else {
            this._valueSerializer.serializeWithType(this._value, jgen, provider, this._typeSerializer);
        }
    }

    public void serializeAsPlaceholder(Object pojo, JsonGenerator jgen, SerializerProvider provider) throws Exception {
        jgen.writeNull();
    }

    public void depositSchemaProperty(JsonObjectFormatVisitor objectVisitor) throws JsonMappingException {
    }

    @Deprecated
    public void depositSchemaProperty(ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
    }
}
