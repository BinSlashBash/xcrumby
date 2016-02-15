package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;

public class AsExternalTypeSerializer extends TypeSerializerBase {
    protected final String _typePropertyName;

    public AsExternalTypeSerializer(TypeIdResolver idRes, BeanProperty property, String propName) {
        super(idRes, property);
        this._typePropertyName = propName;
    }

    public AsExternalTypeSerializer forProperty(BeanProperty prop) {
        return this._property == prop ? this : new AsExternalTypeSerializer(this._idResolver, prop, this._typePropertyName);
    }

    public String getPropertyName() {
        return this._typePropertyName;
    }

    public As getTypeInclusion() {
        return As.EXTERNAL_PROPERTY;
    }

    public void writeTypePrefixForObject(Object value, JsonGenerator jgen) throws IOException {
        _writeObjectPrefix(value, jgen);
    }

    public void writeTypePrefixForObject(Object value, JsonGenerator jgen, Class<?> cls) throws IOException {
        _writeObjectPrefix(value, jgen);
    }

    public void writeTypePrefixForArray(Object value, JsonGenerator jgen) throws IOException {
        _writeArrayPrefix(value, jgen);
    }

    public void writeTypePrefixForArray(Object value, JsonGenerator jgen, Class<?> cls) throws IOException {
        _writeArrayPrefix(value, jgen);
    }

    public void writeTypePrefixForScalar(Object value, JsonGenerator jgen) throws IOException {
        _writeScalarPrefix(value, jgen);
    }

    public void writeTypePrefixForScalar(Object value, JsonGenerator jgen, Class<?> cls) throws IOException {
        _writeScalarPrefix(value, jgen);
    }

    public void writeTypeSuffixForObject(Object value, JsonGenerator jgen) throws IOException {
        _writeObjectSuffix(value, jgen, idFromValue(value));
    }

    public void writeTypeSuffixForArray(Object value, JsonGenerator jgen) throws IOException {
        _writeArraySuffix(value, jgen, idFromValue(value));
    }

    public void writeTypeSuffixForScalar(Object value, JsonGenerator jgen) throws IOException {
        _writeScalarSuffix(value, jgen, idFromValue(value));
    }

    public void writeCustomTypePrefixForScalar(Object value, JsonGenerator jgen, String typeId) throws IOException {
        _writeScalarPrefix(value, jgen);
    }

    public void writeCustomTypePrefixForObject(Object value, JsonGenerator jgen, String typeId) throws IOException {
        _writeObjectPrefix(value, jgen);
    }

    public void writeCustomTypePrefixForArray(Object value, JsonGenerator jgen, String typeId) throws IOException {
        _writeArrayPrefix(value, jgen);
    }

    public void writeCustomTypeSuffixForScalar(Object value, JsonGenerator jgen, String typeId) throws IOException {
        _writeScalarSuffix(value, jgen, typeId);
    }

    public void writeCustomTypeSuffixForObject(Object value, JsonGenerator jgen, String typeId) throws IOException {
        _writeObjectSuffix(value, jgen, typeId);
    }

    public void writeCustomTypeSuffixForArray(Object value, JsonGenerator jgen, String typeId) throws IOException {
        _writeArraySuffix(value, jgen, typeId);
    }

    protected final void _writeScalarPrefix(Object value, JsonGenerator jgen) throws IOException {
    }

    protected final void _writeObjectPrefix(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartObject();
    }

    protected final void _writeArrayPrefix(Object value, JsonGenerator jgen) throws IOException {
        jgen.writeStartArray();
    }

    protected final void _writeScalarSuffix(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeStringField(this._typePropertyName, typeId);
    }

    protected final void _writeObjectSuffix(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeEndObject();
        jgen.writeStringField(this._typePropertyName, typeId);
    }

    protected final void _writeArraySuffix(Object value, JsonGenerator jgen, String typeId) throws IOException {
        jgen.writeEndArray();
        jgen.writeStringField(this._typePropertyName, typeId);
    }
}
