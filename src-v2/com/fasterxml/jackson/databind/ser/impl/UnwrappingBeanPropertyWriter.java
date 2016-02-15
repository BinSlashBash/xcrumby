/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.util.Iterator;
import java.util.Map;

public class UnwrappingBeanPropertyWriter
extends BeanPropertyWriter {
    protected final NameTransformer _nameTransformer;

    public UnwrappingBeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, NameTransformer nameTransformer) {
        super(beanPropertyWriter);
        this._nameTransformer = nameTransformer;
    }

    private UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter unwrappingBeanPropertyWriter, NameTransformer nameTransformer, SerializedString serializedString) {
        super(unwrappingBeanPropertyWriter, serializedString);
        this._nameTransformer = nameTransformer;
    }

    @Override
    protected void _depositSchemaProperty(ObjectNode objectNode, JsonNode object) {
        if ((object = object.get("properties")) != null) {
            Iterator<Map.Entry<String, JsonNode>> iterator = object.fields();
            while (iterator.hasNext()) {
                String string2;
                Map.Entry<String, JsonNode> entry = iterator.next();
                object = string2 = entry.getKey();
                if (this._nameTransformer != null) {
                    object = this._nameTransformer.transform(string2);
                }
                objectNode.set((String)object, entry.getValue());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap jsonSerializer, Class<?> class_, SerializerProvider object) throws JsonMappingException {
        NameTransformer nameTransformer;
        jsonSerializer = this._nonTrivialBaseType != null ? object.findValueSerializer(object.constructSpecializedType(this._nonTrivialBaseType, class_), (BeanProperty)this) : object.findValueSerializer(class_, (BeanProperty)this);
        object = nameTransformer = this._nameTransformer;
        if (jsonSerializer.isUnwrappingSerializer()) {
            object = NameTransformer.chainedTransformer(nameTransformer, ((UnwrappingBeanSerializer)jsonSerializer)._nameTransformer);
        }
        jsonSerializer = jsonSerializer.unwrappingSerializer((NameTransformer)object);
        this._dynamicSerializers = this._dynamicSerializers.newWith(class_, jsonSerializer);
        return jsonSerializer;
    }

    @Override
    public void assignSerializer(JsonSerializer<Object> object) {
        super.assignSerializer((JsonSerializer<Object>)object);
        if (this._serializer != null) {
            NameTransformer nameTransformer;
            object = nameTransformer = this._nameTransformer;
            if (this._serializer.isUnwrappingSerializer()) {
                object = NameTransformer.chainedTransformer(nameTransformer, ((UnwrappingBeanSerializer)this._serializer)._nameTransformer);
            }
            this._serializer = this._serializer.unwrappingSerializer((NameTransformer)object);
        }
    }

    @Override
    public boolean isUnwrapping() {
        return true;
    }

    @Override
    public UnwrappingBeanPropertyWriter rename(NameTransformer nameTransformer) {
        String string2 = nameTransformer.transform(this._name.getValue());
        return new UnwrappingBeanPropertyWriter(this, NameTransformer.chainedTransformer(nameTransformer, this._nameTransformer), new SerializedString(string2));
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        JsonSerializer<Object> jsonSerializer;
        Object object2 = this.get(object);
        if (object2 == null) {
            return;
        }
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._serializer;
        if (jsonSerializer == null) {
            Class class_ = object2.getClass();
            PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
            jsonSerializer2 = jsonSerializer = propertySerializerMap.serializerFor(class_);
            if (jsonSerializer == null) {
                jsonSerializer2 = this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
            }
        }
        if (this._suppressableValue != null) {
            if (MARKER_FOR_EMPTY == this._suppressableValue) {
                if (jsonSerializer2.isEmpty(object2)) return;
            } else if (this._suppressableValue.equals(object2)) {
                return;
            }
        }
        if (object2 == object) {
            if (this._handleSelfReference(object, jsonGenerator, serializerProvider, jsonSerializer2)) return;
        }
        if (!jsonSerializer2.isUnwrappingSerializer()) {
            jsonGenerator.writeFieldName(this._name);
        }
        if (this._typeSerializer == null) {
            jsonSerializer2.serialize(object2, jsonGenerator, serializerProvider);
            return;
        }
        jsonSerializer2.serializeWithType(object2, jsonGenerator, serializerProvider, this._typeSerializer);
    }
}

