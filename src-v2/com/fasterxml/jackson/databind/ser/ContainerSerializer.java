/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class ContainerSerializer<T>
extends StdSerializer<T> {
    protected ContainerSerializer(ContainerSerializer<?> containerSerializer) {
        super(containerSerializer._handledType, false);
    }

    protected ContainerSerializer(Class<T> class_) {
        super(class_);
    }

    protected ContainerSerializer(Class<?> class_, boolean bl2) {
        super(class_, bl2);
    }

    protected abstract ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer var1);

    public abstract JsonSerializer<?> getContentSerializer();

    public abstract JavaType getContentType();

    protected boolean hasContentTypeAnnotation(SerializerProvider object, BeanProperty beanProperty) {
        if (beanProperty != null && (object = object.getAnnotationIntrospector()) != null && object.findSerializationContentType(beanProperty.getMember(), beanProperty.getType()) != null) {
            return true;
        }
        return false;
    }

    public abstract boolean hasSingleElement(T var1);

    @Override
    public abstract boolean isEmpty(T var1);

    public ContainerSerializer<?> withValueTypeSerializer(TypeSerializer typeSerializer) {
        if (typeSerializer == null) {
            return this;
        }
        return this._withValueTypeSerializer(typeSerializer);
    }
}

