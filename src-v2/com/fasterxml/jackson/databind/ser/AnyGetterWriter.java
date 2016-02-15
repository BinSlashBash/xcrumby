/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import java.util.Map;

public class AnyGetterWriter {
    protected final AnnotatedMember _accessor;
    protected final BeanProperty _property;
    protected MapSerializer _serializer;

    public AnyGetterWriter(BeanProperty beanProperty, AnnotatedMember annotatedMember, MapSerializer mapSerializer) {
        this._accessor = annotatedMember;
        this._property = beanProperty;
        this._serializer = mapSerializer;
    }

    public void getAndFilter(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyFilter propertyFilter) throws Exception {
        if ((object = this._accessor.getValue(object)) == null) {
            return;
        }
        if (!(object instanceof Map)) {
            throw new JsonMappingException("Value returned by 'any-getter' (" + this._accessor.getName() + "()) not java.util.Map but " + object.getClass().getName());
        }
        this._serializer.serializeFilteredFields((Map)object, jsonGenerator, serializerProvider, propertyFilter);
    }

    public void getAndSerialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        if ((object = this._accessor.getValue(object)) == null) {
            return;
        }
        if (!(object instanceof Map)) {
            throw new JsonMappingException("Value returned by 'any-getter' (" + this._accessor.getName() + "()) not java.util.Map but " + object.getClass().getName());
        }
        this._serializer.serializeFields((Map)object, jsonGenerator, serializerProvider);
    }

    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        this._serializer = (MapSerializer)serializerProvider.handlePrimaryContextualization(this._serializer, this._property);
    }
}

