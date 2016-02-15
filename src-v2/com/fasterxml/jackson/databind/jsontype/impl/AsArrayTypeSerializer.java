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

public class AsArrayTypeSerializer
extends TypeSerializerBase {
    public AsArrayTypeSerializer(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        super(typeIdResolver, beanProperty);
    }

    @Override
    public AsArrayTypeSerializer forProperty(BeanProperty beanProperty) {
        if (this._property == beanProperty) {
            return this;
        }
        return new AsArrayTypeSerializer(this._idResolver, beanProperty);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeCustomTypePrefixForArray(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(string2);
        } else {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(string2);
        }
        jsonGenerator.writeStartArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeCustomTypePrefixForObject(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(string2);
        } else {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(string2);
        }
        jsonGenerator.writeStartObject();
    }

    @Override
    public void writeCustomTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(string2);
            return;
        }
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString(string2);
    }

    @Override
    public void writeCustomTypeSuffixForArray(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        if (!jsonGenerator.canWriteTypeId()) {
            this.writeTypeSuffixForArray(object, jsonGenerator);
        }
    }

    @Override
    public void writeCustomTypeSuffixForObject(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        if (!jsonGenerator.canWriteTypeId()) {
            this.writeTypeSuffixForObject(object, jsonGenerator);
        }
    }

    @Override
    public void writeCustomTypeSuffixForScalar(Object object, JsonGenerator jsonGenerator, String string2) throws IOException {
        if (!jsonGenerator.canWriteTypeId()) {
            this.writeTypeSuffixForScalar(object, jsonGenerator);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator) throws IOException {
        object = this.idFromValue(object);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
        } else {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString((String)object);
        }
        jsonGenerator.writeStartArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        object = this.idFromValueAndType(object, class_);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
        } else {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString((String)object);
        }
        jsonGenerator.writeStartArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator) throws IOException {
        object = this.idFromValue(object);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
        } else {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString((String)object);
        }
        jsonGenerator.writeStartObject();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        object = this.idFromValueAndType(object, class_);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
        } else {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString((String)object);
        }
        jsonGenerator.writeStartObject();
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException {
        object = this.idFromValue(object);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
            return;
        }
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString((String)object);
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException {
        object = this.idFromValueAndType(object, class_);
        if (jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeTypeId(object);
            return;
        }
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString((String)object);
    }

    @Override
    public void writeTypeSuffixForArray(Object object, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeEndArray();
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeEndArray();
        }
    }

    @Override
    public void writeTypeSuffixForObject(Object object, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeEndObject();
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeEndArray();
        }
    }

    @Override
    public void writeTypeSuffixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException {
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeEndArray();
        }
    }
}

