/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;

public interface JsonFormatVisitable {
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException;
}

