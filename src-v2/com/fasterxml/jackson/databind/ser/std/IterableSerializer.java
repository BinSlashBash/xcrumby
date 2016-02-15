/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

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
public class IterableSerializer
extends AsArraySerializerBase<Iterable<?>> {
    public IterableSerializer(JavaType javaType, boolean bl2, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        super(Iterable.class, javaType, bl2, typeSerializer, beanProperty, null);
    }

    public IterableSerializer(IterableSerializer iterableSerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        super(iterableSerializer, beanProperty, typeSerializer, jsonSerializer);
    }

    @Override
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new IterableSerializer(this._elementType, this._staticTyping, typeSerializer, this._property);
    }

    @Override
    public boolean hasSingleElement(Iterable<?> object) {
        if (object != null && (object = object.iterator()).hasNext()) {
            object.next();
            if (!object.hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty(Iterable<?> iterable) {
        if (iterable == null || !iterable.iterator().hasNext()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeContents(Iterable<?> iterable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        Iterator iterator = iterable.iterator();
        if (iterator.hasNext()) {
            TypeSerializer typeSerializer = this._valueTypeSerializer;
            iterable = null;
            Class class_ = null;
            do {
                Object obj;
                JsonSerializer jsonSerializer;
                if ((obj = iterator.next()) == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                    continue;
                }
                JsonSerializer jsonSerializer2 = jsonSerializer = this._elementSerializer;
                Class class_2 = class_;
                Object object = iterable;
                if (jsonSerializer == null) {
                    class_2 = obj.getClass();
                    if (class_2 == class_) {
                        jsonSerializer2 = iterable;
                        object = iterable;
                        class_2 = class_;
                    } else {
                        object = jsonSerializer2 = serializerProvider.findValueSerializer(class_2, this._property);
                    }
                }
                if (typeSerializer == null) {
                    jsonSerializer2.serialize(obj, jsonGenerator, serializerProvider);
                    class_ = class_2;
                    iterable = object;
                    continue;
                }
                jsonSerializer2.serializeWithType(obj, jsonGenerator, serializerProvider, typeSerializer);
                class_ = class_2;
                iterable = object;
            } while (iterator.hasNext());
        }
    }

    public IterableSerializer withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        return new IterableSerializer(this, beanProperty, typeSerializer, jsonSerializer);
    }
}

