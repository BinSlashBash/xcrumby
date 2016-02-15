/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Collection;

public abstract class JsonDeserializer<T> {
    public abstract T deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, T t2) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Can not update object of type " + t2.getClass().getName() + " (by deserializer of type " + this.getClass().getName() + ")");
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    public SettableBeanProperty findBackReference(String string2) {
        throw new IllegalArgumentException("Can not handle managed/back reference '" + string2 + "': type: value deserializer of type " + this.getClass().getName() + " does not support them");
    }

    public JsonDeserializer<?> getDelegatee() {
        return null;
    }

    public T getEmptyValue() {
        return this.getNullValue();
    }

    public Collection<Object> getKnownPropertyNames() {
        return null;
    }

    public T getNullValue() {
        return null;
    }

    public ObjectIdReader getObjectIdReader() {
        return null;
    }

    public Class<?> handledType() {
        return null;
    }

    public boolean isCachable() {
        return false;
    }

    public JsonDeserializer<?> replaceDelegatee(JsonDeserializer<?> jsonDeserializer) {
        throw new UnsupportedOperationException();
    }

    public JsonDeserializer<T> unwrappingDeserializer(NameTransformer nameTransformer) {
        return this;
    }

    public static abstract class None
    extends JsonDeserializer<Object> {
        private None() {
        }
    }

}

