/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public abstract class ContainerDeserializerBase<T>
extends StdDeserializer<T> {
    protected ContainerDeserializerBase(JavaType javaType) {
        super(javaType);
    }

    @Deprecated
    protected ContainerDeserializerBase(Class<?> class_) {
        super(class_);
    }

    @Override
    public SettableBeanProperty findBackReference(String string2) {
        JsonDeserializer<Object> jsonDeserializer = this.getContentDeserializer();
        if (jsonDeserializer == null) {
            throw new IllegalArgumentException("Can not handle managed/back reference '" + string2 + "': type: container deserializer of type " + this.getClass().getName() + " returned null for 'getContentDeserializer()'");
        }
        return jsonDeserializer.findBackReference(string2);
    }

    public abstract JsonDeserializer<Object> getContentDeserializer();

    public abstract JavaType getContentType();
}

