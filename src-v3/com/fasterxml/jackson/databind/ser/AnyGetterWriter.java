package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import java.util.Map;

public class AnyGetterWriter {
    protected final AnnotatedMember _accessor;
    protected final BeanProperty _property;
    protected MapSerializer _serializer;

    public AnyGetterWriter(BeanProperty property, AnnotatedMember accessor, MapSerializer serializer) {
        this._accessor = accessor;
        this._property = property;
        this._serializer = serializer;
    }

    public void getAndSerialize(Object bean, JsonGenerator jgen, SerializerProvider provider) throws Exception {
        Object value = this._accessor.getValue(bean);
        if (value != null) {
            if (value instanceof Map) {
                this._serializer.serializeFields((Map) value, jgen, provider);
                return;
            }
            throw new JsonMappingException("Value returned by 'any-getter' (" + this._accessor.getName() + "()) not java.util.Map but " + value.getClass().getName());
        }
    }

    public void getAndFilter(Object bean, JsonGenerator jgen, SerializerProvider provider, PropertyFilter filter) throws Exception {
        Object value = this._accessor.getValue(bean);
        if (value != null) {
            if (value instanceof Map) {
                this._serializer.serializeFilteredFields((Map) value, jgen, provider, filter);
                return;
            }
            throw new JsonMappingException("Value returned by 'any-getter' (" + this._accessor.getName() + "()) not java.util.Map but " + value.getClass().getName());
        }
    }

    public void resolve(SerializerProvider provider) throws JsonMappingException {
        this._serializer = (MapSerializer) provider.handlePrimaryContextualization(this._serializer, this._property);
    }
}
