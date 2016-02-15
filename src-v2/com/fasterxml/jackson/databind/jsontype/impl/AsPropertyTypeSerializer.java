/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer;
import java.io.IOException;

public class AsPropertyTypeSerializer
extends AsArrayTypeSerializer {
    protected final String _typePropertyName;

    public AsPropertyTypeSerializer(TypeIdResolver typeIdResolver, BeanProperty beanProperty, String string2) {
        super(typeIdResolver, beanProperty);
        this._typePropertyName = string2;
    }

    @Override
    public AsPropertyTypeSerializer forProperty(BeanProperty beanProperty) {
        if (this._property == beanProperty) {
            return this;
        }
        return new AsPropertyTypeSerializer(this._idResolver, beanProperty, this._typePropertyName);
    }

    @Override
    public String getPropertyName() {
        return this._typePropertyName;
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.PROPERTY;
    }

    @Override
    public void writeCustomTypePrefixForObject(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(string2);
            jsonGenerator.writeStartObject();
            return;
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(this._typePropertyName, string2);
    }

    @Override
    public void writeCustomTypeSuffixForObject(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        jsonGenerator.writeEndObject();
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator) throws IOException {
        object = this.idFromValue(object);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
            jsonGenerator.writeStartObject();
            return;
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(this._typePropertyName, (String)object);
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        object = this.idFromValueAndType(object, class_);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
            jsonGenerator.writeStartObject();
            return;
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(this._typePropertyName, (String)object);
    }

    @Override
    public void writeTypeSuffixForObject(Object object, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeEndObject();
    }
}

