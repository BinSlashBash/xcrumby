/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import java.io.IOException;

public abstract class ArraySerializerBase<T>
extends ContainerSerializer<T> {
    protected final BeanProperty _property;

    protected ArraySerializerBase(ArraySerializerBase<?> arraySerializerBase) {
        super(arraySerializerBase._handledType, false);
        this._property = arraySerializerBase._property;
    }

    protected ArraySerializerBase(ArraySerializerBase<?> arraySerializerBase, BeanProperty beanProperty) {
        super(arraySerializerBase._handledType, false);
        this._property = beanProperty;
    }

    protected ArraySerializerBase(Class<T> class_) {
        super(class_);
        this._property = null;
    }

    protected ArraySerializerBase(Class<T> class_, BeanProperty beanProperty) {
        super(class_);
        this._property = beanProperty;
    }

    @Override
    public final void serialize(T t2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && this.hasSingleElement(t2)) {
            this.serializeContents(t2, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray();
        this.serializeContents(t2, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }

    protected abstract void serializeContents(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    @Override
    public final void serializeWithType(T t2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForArray(t2, jsonGenerator);
        this.serializeContents(t2, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForArray(t2, jsonGenerator);
    }
}

