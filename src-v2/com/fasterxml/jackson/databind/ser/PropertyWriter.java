/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class PropertyWriter {
    public abstract void depositSchemaProperty(JsonObjectFormatVisitor var1) throws JsonMappingException;

    @Deprecated
    public abstract void depositSchemaProperty(ObjectNode var1, SerializerProvider var2) throws JsonMappingException;

    public abstract PropertyName getFullName();

    public abstract String getName();

    public abstract void serializeAsElement(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception;

    public abstract void serializeAsField(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception;

    public abstract void serializeAsOmittedField(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception;

    public abstract void serializeAsPlaceholder(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception;
}

