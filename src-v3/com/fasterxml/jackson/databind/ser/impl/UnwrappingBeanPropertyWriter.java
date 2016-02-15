package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.util.Iterator;
import java.util.Map.Entry;

public class UnwrappingBeanPropertyWriter extends BeanPropertyWriter {
    protected final NameTransformer _nameTransformer;

    public UnwrappingBeanPropertyWriter(BeanPropertyWriter base, NameTransformer unwrapper) {
        super(base);
        this._nameTransformer = unwrapper;
    }

    private UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter base, NameTransformer transformer, SerializedString name) {
        super(base, name);
        this._nameTransformer = transformer;
    }

    public UnwrappingBeanPropertyWriter rename(NameTransformer transformer) {
        return new UnwrappingBeanPropertyWriter(this, NameTransformer.chainedTransformer(transformer, this._nameTransformer), new SerializedString(transformer.transform(this._name.getValue())));
    }

    public boolean isUnwrapping() {
        return true;
    }

    public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception {
        Object value = get(bean);
        if (value != null) {
            JsonSerializer<Object> ser = this._serializer;
            if (ser == null) {
                Class<?> cls = value.getClass();
                PropertySerializerMap map = this._dynamicSerializers;
                ser = map.serializerFor(cls);
                if (ser == null) {
                    ser = _findAndAddDynamic(map, cls, prov);
                }
            }
            if (this._suppressableValue != null) {
                if (MARKER_FOR_EMPTY == this._suppressableValue) {
                    if (ser.isEmpty(value)) {
                        return;
                    }
                } else if (this._suppressableValue.equals(value)) {
                    return;
                }
            }
            if (value != bean || !_handleSelfReference(bean, jgen, prov, ser)) {
                if (!ser.isUnwrappingSerializer()) {
                    jgen.writeFieldName(this._name);
                }
                if (this._typeSerializer == null) {
                    ser.serialize(value, jgen, prov);
                } else {
                    ser.serializeWithType(value, jgen, prov, this._typeSerializer);
                }
            }
        }
    }

    public void assignSerializer(JsonSerializer<Object> ser) {
        super.assignSerializer(ser);
        if (this._serializer != null) {
            NameTransformer t = this._nameTransformer;
            if (this._serializer.isUnwrappingSerializer()) {
                t = NameTransformer.chainedTransformer(t, ((UnwrappingBeanSerializer) this._serializer)._nameTransformer);
            }
            this._serializer = this._serializer.unwrappingSerializer(t);
        }
    }

    protected void _depositSchemaProperty(ObjectNode propertiesNode, JsonNode schemaNode) {
        JsonNode props = schemaNode.get("properties");
        if (props != null) {
            Iterator<Entry<String, JsonNode>> it = props.fields();
            while (it.hasNext()) {
                Entry<String, JsonNode> entry = (Entry) it.next();
                String name = (String) entry.getKey();
                if (this._nameTransformer != null) {
                    name = this._nameTransformer.transform(name);
                }
                propertiesNode.set(name, (JsonNode) entry.getValue());
            }
        }
    }

    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
        JsonSerializer<Object> serializer;
        if (this._nonTrivialBaseType != null) {
            serializer = provider.findValueSerializer(provider.constructSpecializedType(this._nonTrivialBaseType, type), (BeanProperty) this);
        } else {
            serializer = provider.findValueSerializer((Class) type, (BeanProperty) this);
        }
        NameTransformer t = this._nameTransformer;
        if (serializer.isUnwrappingSerializer()) {
            t = NameTransformer.chainedTransformer(t, ((UnwrappingBeanSerializer) serializer)._nameTransformer);
        }
        serializer = serializer.unwrappingSerializer(t);
        this._dynamicSerializers = this._dynamicSerializers.newWith(type, serializer);
        return serializer;
    }
}
