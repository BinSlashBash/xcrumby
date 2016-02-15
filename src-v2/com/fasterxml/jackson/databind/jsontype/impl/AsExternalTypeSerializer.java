/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase;
import java.io.IOException;

public class AsExternalTypeSerializer
extends TypeSerializerBase {
    protected final String _typePropertyName;

    public AsExternalTypeSerializer(TypeIdResolver typeIdResolver, BeanProperty beanProperty, String string2) {
        super(typeIdResolver, beanProperty);
        this._typePropertyName = string2;
    }

    protected final void _writeArrayPrefix(Object object, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartArray();
    }

    protected final void _writeArraySuffix(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        jsonGenerator.writeEndArray();
        jsonGenerator.writeStringField(this._typePropertyName, string2);
    }

    protected final void _writeObjectPrefix(Object object, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartObject();
    }

    protected final void _writeObjectSuffix(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField(this._typePropertyName, string2);
    }

    protected final void _writeScalarPrefix(Object object, JsonGenerator jsonGenerator) throws IOException {
    }

    protected final void _writeScalarSuffix(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        jsonGenerator.writeStringField(this._typePropertyName, string2);
    }

    @Override
    public AsExternalTypeSerializer forProperty(BeanProperty beanProperty) {
        if (this._property == beanProperty) {
            return this;
        }
        return new AsExternalTypeSerializer(this._idResolver, beanProperty, this._typePropertyName);
    }

    @Override
    public String getPropertyName() {
        return this._typePropertyName;
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.EXTERNAL_PROPERTY;
    }

    @Override
    public void writeCustomTypePrefixForArray(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        this._writeArrayPrefix(object, jsonGenerator);
    }

    @Override
    public void writeCustomTypePrefixForObject(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        this._writeObjectPrefix(object, jsonGenerator);
    }

    @Override
    public void writeCustomTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        this._writeScalarPrefix(object, jsonGenerator);
    }

    @Override
    public void writeCustomTypeSuffixForArray(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        this._writeArraySuffix(object, jsonGenerator, string2);
    }

    @Override
    public void writeCustomTypeSuffixForObject(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        this._writeObjectSuffix(object, jsonGenerator, string2);
    }

    @Override
    public void writeCustomTypeSuffixForScalar(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        this._writeScalarSuffix(object, jsonGenerator, string2);
    }

    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator) throws IOException {
        this._writeArrayPrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        this._writeArrayPrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator) throws IOException {
        this._writeObjectPrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        this._writeObjectPrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException {
        this._writeScalarPrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        this._writeScalarPrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypeSuffixForArray(Object object, JsonGenerator jsonGenerator) throws IOException {
        this._writeArraySuffix(object, jsonGenerator, this.idFromValue(object));
    }

    @Override
    public void writeTypeSuffixForObject(Object object, JsonGenerator jsonGenerator) throws IOException {
        this._writeObjectSuffix(object, jsonGenerator, this.idFromValue(object));
    }

    @Override
    public void writeTypeSuffixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException {
        this._writeScalarSuffix(object, jsonGenerator, this.idFromValue(object));
    }
}

