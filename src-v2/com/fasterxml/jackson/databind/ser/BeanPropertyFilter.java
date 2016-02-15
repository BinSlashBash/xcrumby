/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

@Deprecated
public interface BeanPropertyFilter {
    public void depositSchemaProperty(BeanPropertyWriter var1, JsonObjectFormatVisitor var2, SerializerProvider var3) throws JsonMappingException;

    @Deprecated
    public void depositSchemaProperty(BeanPropertyWriter var1, ObjectNode var2, SerializerProvider var3) throws JsonMappingException;

    public void serializeAsField(Object var1, JsonGenerator var2, SerializerProvider var3, BeanPropertyWriter var4) throws Exception;
}

