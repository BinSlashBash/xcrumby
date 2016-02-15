/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public class UnwrappingBeanSerializer
extends BeanSerializerBase {
    protected final NameTransformer _nameTransformer;

    public UnwrappingBeanSerializer(UnwrappingBeanSerializer unwrappingBeanSerializer, ObjectIdWriter objectIdWriter) {
        super((BeanSerializerBase)unwrappingBeanSerializer, objectIdWriter);
        this._nameTransformer = unwrappingBeanSerializer._nameTransformer;
    }

    public UnwrappingBeanSerializer(UnwrappingBeanSerializer unwrappingBeanSerializer, ObjectIdWriter objectIdWriter, Object object) {
        super((BeanSerializerBase)unwrappingBeanSerializer, objectIdWriter, object);
        this._nameTransformer = unwrappingBeanSerializer._nameTransformer;
    }

    protected UnwrappingBeanSerializer(UnwrappingBeanSerializer unwrappingBeanSerializer, String[] arrstring) {
        super((BeanSerializerBase)unwrappingBeanSerializer, arrstring);
        this._nameTransformer = unwrappingBeanSerializer._nameTransformer;
    }

    public UnwrappingBeanSerializer(BeanSerializerBase beanSerializerBase, NameTransformer nameTransformer) {
        super(beanSerializerBase, nameTransformer);
        this._nameTransformer = nameTransformer;
    }

    @Override
    protected BeanSerializerBase asArraySerializer() {
        return this;
    }

    @Override
    public boolean isUnwrappingSerializer() {
        return true;
    }

    @Override
    public final void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._objectIdWriter != null) {
            this._serializeWithObjectId(object, jsonGenerator, serializerProvider, false);
            return;
        }
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
            return;
        }
        this.serializeFields(object, jsonGenerator, serializerProvider);
    }

    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        if (serializerProvider.isEnabled(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS)) {
            throw new JsonMappingException("Unwrapped property requires use of type information: can not serialize without disabling `SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS`");
        }
        if (this._objectIdWriter != null) {
            this._serializeWithObjectId(object, jsonGenerator, serializerProvider, typeSerializer);
            return;
        }
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
            return;
        }
        this.serializeFields(object, jsonGenerator, serializerProvider);
    }

    public String toString() {
        return "UnwrappingBeanSerializer for " + this.handledType().getName();
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer nameTransformer) {
        return new UnwrappingBeanSerializer((BeanSerializerBase)this, nameTransformer);
    }

    @Override
    protected BeanSerializerBase withFilterId(Object object) {
        return new UnwrappingBeanSerializer(this, this._objectIdWriter, object);
    }

    @Override
    protected BeanSerializerBase withIgnorals(String[] arrstring) {
        return new UnwrappingBeanSerializer(this, arrstring);
    }

    @Override
    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new UnwrappingBeanSerializer(this, objectIdWriter);
    }
}

