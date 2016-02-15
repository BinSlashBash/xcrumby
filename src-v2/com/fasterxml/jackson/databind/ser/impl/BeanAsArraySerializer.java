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
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public class BeanAsArraySerializer
extends BeanSerializerBase {
    protected final BeanSerializerBase _defaultSerializer;

    public BeanAsArraySerializer(BeanSerializerBase beanSerializerBase) {
        super(beanSerializerBase, (ObjectIdWriter)null);
        this._defaultSerializer = beanSerializerBase;
    }

    protected BeanAsArraySerializer(BeanSerializerBase beanSerializerBase, ObjectIdWriter objectIdWriter, Object object) {
        super(beanSerializerBase, objectIdWriter, object);
        this._defaultSerializer = beanSerializerBase;
    }

    protected BeanAsArraySerializer(BeanSerializerBase beanSerializerBase, String[] arrstring) {
        super(beanSerializerBase, arrstring);
        this._defaultSerializer = beanSerializerBase;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasSingleElement(SerializerProvider arrbeanPropertyWriter) {
        arrbeanPropertyWriter = this._filteredProps != null && arrbeanPropertyWriter.getActiveView() != null ? this._filteredProps : this._props;
        if (arrbeanPropertyWriter.length == 1) {
            return true;
        }
        return false;
    }

    @Override
    protected BeanSerializerBase asArraySerializer() {
        return this;
    }

    @Override
    public boolean isUnwrappingSerializer() {
        return false;
    }

    @Override
    public final void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && this.hasSingleElement(serializerProvider)) {
            this.serializeAsArray(object, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray();
        this.serializeAsArray(object, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void serializeAsArray(Object object, JsonGenerator object2, SerializerProvider object3) throws IOException, JsonGenerationException {
        int n2;
        BeanPropertyWriter[] arrbeanPropertyWriter = this._filteredProps != null && object3.getActiveView() != null ? this._filteredProps : this._props;
        int n3 = 0;
        int n4 = 0;
        try {
            n2 = arrbeanPropertyWriter.length;
        }
        catch (StackOverflowError var2_3) {
            object3 = new JsonMappingException("Infinite recursion (StackOverflowError)", var2_3);
            String string2 = n4 == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n4].getName();
            object3.prependPath(new JsonMappingException.Reference(object, string2));
            throw object3;
        }
        for (int i2 = 0; i2 < n2; ++i2) {
            BeanPropertyWriter beanPropertyWriter = arrbeanPropertyWriter[i2];
            if (beanPropertyWriter == null) {
                n3 = i2;
                n4 = i2;
                object2.writeNull();
                continue;
            }
            n3 = i2;
            n4 = i2;
            try {
                beanPropertyWriter.serializeAsElement(object, (JsonGenerator)object2, (SerializerProvider)object3);
            }
            catch (Exception var9_12) {
                object2 = n3 == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n3].getName();
                this.wrapAndThrow((SerializerProvider)object3, (Throwable)var9_12, object, (String)object2);
                return;
            }
            continue;
        }
        return;
    }

    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        this._defaultSerializer.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
    }

    public String toString() {
        return "BeanAsArraySerializer for " + this.handledType().getName();
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer nameTransformer) {
        return this._defaultSerializer.unwrappingSerializer(nameTransformer);
    }

    @Override
    protected BeanSerializerBase withFilterId(Object object) {
        return new BeanAsArraySerializer(this, this._objectIdWriter, object);
    }

    @Override
    protected BeanAsArraySerializer withIgnorals(String[] arrstring) {
        return new BeanAsArraySerializer(this, arrstring);
    }

    @Override
    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return this._defaultSerializer.withObjectIdWriter(objectIdWriter);
    }
}

