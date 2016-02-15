/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public class BeanSerializer
extends BeanSerializerBase {
    public BeanSerializer(JavaType javaType, BeanSerializerBuilder beanSerializerBuilder, BeanPropertyWriter[] arrbeanPropertyWriter, BeanPropertyWriter[] arrbeanPropertyWriter2) {
        super(javaType, beanSerializerBuilder, arrbeanPropertyWriter, arrbeanPropertyWriter2);
    }

    protected BeanSerializer(BeanSerializerBase beanSerializerBase) {
        super(beanSerializerBase);
    }

    protected BeanSerializer(BeanSerializerBase beanSerializerBase, ObjectIdWriter objectIdWriter) {
        super(beanSerializerBase, objectIdWriter);
    }

    protected BeanSerializer(BeanSerializerBase beanSerializerBase, ObjectIdWriter objectIdWriter, Object object) {
        super(beanSerializerBase, objectIdWriter, object);
    }

    protected BeanSerializer(BeanSerializerBase beanSerializerBase, String[] arrstring) {
        super(beanSerializerBase, arrstring);
    }

    public static BeanSerializer createDummy(JavaType javaType) {
        return new BeanSerializer(javaType, null, NO_PROPS, null);
    }

    @Override
    protected BeanSerializerBase asArraySerializer() {
        BeanSerializerBase beanSerializerBase = this;
        if (this._objectIdWriter == null) {
            beanSerializerBase = this;
            if (this._anyGetterWriter == null) {
                beanSerializerBase = this;
                if (this._propertyFilterId == null) {
                    beanSerializerBase = new BeanAsArraySerializer(this);
                }
            }
        }
        return beanSerializerBase;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._objectIdWriter != null) {
            this._serializeWithObjectId(object, jsonGenerator, serializerProvider, true);
            return;
        }
        jsonGenerator.writeStartObject();
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
        } else {
            this.serializeFields(object, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndObject();
    }

    public String toString() {
        return "BeanSerializer for " + this.handledType().getName();
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer nameTransformer) {
        return new UnwrappingBeanSerializer(this, nameTransformer);
    }

    @Override
    protected BeanSerializerBase withFilterId(Object object) {
        return new BeanSerializer(this, this._objectIdWriter, object);
    }

    @Override
    protected BeanSerializerBase withIgnorals(String[] arrstring) {
        return new BeanSerializer((BeanSerializerBase)this, arrstring);
    }

    @Override
    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new BeanSerializer(this, objectIdWriter, this._propertyFilterId);
    }
}

