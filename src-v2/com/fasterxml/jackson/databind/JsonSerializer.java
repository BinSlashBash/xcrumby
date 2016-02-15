/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

public abstract class JsonSerializer<T>
implements JsonFormatVisitable {
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        if (jsonFormatVisitorWrapper != null) {
            jsonFormatVisitorWrapper.expectAnyFormat(javaType);
        }
    }

    public JsonSerializer<?> getDelegatee() {
        return null;
    }

    public Class<T> handledType() {
        return null;
    }

    public boolean isEmpty(T t2) {
        if (t2 == null) {
            return true;
        }
        return false;
    }

    public boolean isUnwrappingSerializer() {
        return false;
    }

    public JsonSerializer<T> replaceDelegatee(JsonSerializer<?> jsonSerializer) {
        throw new UnsupportedOperationException();
    }

    public abstract void serialize(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonProcessingException;

    public void serializeWithType(T t2, JsonGenerator object, SerializerProvider object2, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        object = object2 = this.handledType();
        if (object2 == null) {
            object = t2.getClass();
        }
        throw new UnsupportedOperationException("Type id handling not implemented for type " + object.getName());
    }

    public JsonSerializer<T> unwrappingSerializer(NameTransformer nameTransformer) {
        return this;
    }

    public boolean usesObjectId() {
        return false;
    }

    public static abstract class None
    extends JsonSerializer<Object> {
    }

}

