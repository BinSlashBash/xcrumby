/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;

public interface JsonFormatVisitorWrapper
extends JsonFormatVisitorWithSerializerProvider {
    public JsonAnyFormatVisitor expectAnyFormat(JavaType var1) throws JsonMappingException;

    public JsonArrayFormatVisitor expectArrayFormat(JavaType var1) throws JsonMappingException;

    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType var1) throws JsonMappingException;

    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType var1) throws JsonMappingException;

    public JsonMapFormatVisitor expectMapFormat(JavaType var1) throws JsonMappingException;

    public JsonNullFormatVisitor expectNullFormat(JavaType var1) throws JsonMappingException;

    public JsonNumberFormatVisitor expectNumberFormat(JavaType var1) throws JsonMappingException;

    public JsonObjectFormatVisitor expectObjectFormat(JavaType var1) throws JsonMappingException;

    public JsonStringFormatVisitor expectStringFormat(JavaType var1) throws JsonMappingException;
}

