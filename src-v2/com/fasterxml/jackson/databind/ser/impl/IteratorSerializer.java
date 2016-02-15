/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.Iterator;

@JacksonStdImpl
public class IteratorSerializer
extends AsArraySerializerBase<Iterator<?>> {
    public IteratorSerializer(JavaType javaType, boolean bl2, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        super(Iterator.class, javaType, bl2, typeSerializer, beanProperty, null);
    }

    public IteratorSerializer(IteratorSerializer iteratorSerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        super(iteratorSerializer, beanProperty, typeSerializer, jsonSerializer);
    }

    @Override
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new IteratorSerializer(this._elementType, this._staticTyping, typeSerializer, this._property);
    }

    @Override
    public boolean hasSingleElement(Iterator<?> iterator) {
        return false;
    }

    @Override
    public boolean isEmpty(Iterator<?> iterator) {
        if (iterator == null || !iterator.hasNext()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeContents(Iterator<?> iterator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (iterator.hasNext()) {
            TypeSerializer typeSerializer = this._valueTypeSerializer;
            JsonSerializer<Object> jsonSerializer = null;
            Class class_ = null;
            do {
                Object obj;
                JsonSerializer<Object> jsonSerializer2;
                if ((obj = iterator.next()) == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                    continue;
                }
                JsonSerializer<Object> jsonSerializer3 = jsonSerializer2 = this._elementSerializer;
                Class class_2 = class_;
                JsonSerializer<Object> jsonSerializer4 = jsonSerializer;
                if (jsonSerializer2 == null) {
                    class_2 = obj.getClass();
                    if (class_2 == class_) {
                        jsonSerializer3 = jsonSerializer;
                        jsonSerializer4 = jsonSerializer;
                        class_2 = class_;
                    } else {
                        jsonSerializer4 = jsonSerializer3 = serializerProvider.findValueSerializer(class_2, this._property);
                    }
                }
                if (typeSerializer == null) {
                    jsonSerializer3.serialize(obj, jsonGenerator, serializerProvider);
                    class_ = class_2;
                    jsonSerializer = jsonSerializer4;
                    continue;
                }
                jsonSerializer3.serializeWithType(obj, jsonGenerator, serializerProvider, typeSerializer);
                class_ = class_2;
                jsonSerializer = jsonSerializer4;
            } while (iterator.hasNext());
        }
    }

    public IteratorSerializer withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        return new IteratorSerializer(this, beanProperty, typeSerializer, jsonSerializer);
    }
}

