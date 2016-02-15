/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.lang.reflect.Type;
import java.util.Collection;

public abstract class StaticListSerializerBase<T extends Collection<?>>
extends StdSerializer<T> {
    protected StaticListSerializerBase(Class<?> class_) {
        super(class_, false);
    }

    protected abstract void acceptContentVisitor(JsonArrayFormatVisitor var1) throws JsonMappingException;

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        this.acceptContentVisitor(jsonFormatVisitorWrapper.expectArrayFormat(javaType));
    }

    protected abstract JsonNode contentSchema();

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("array", true).set("items", this.contentSchema());
    }

    @Override
    public boolean isEmpty(T t2) {
        if (t2 == null || t2.size() == 0) {
            return true;
        }
        return false;
    }
}

