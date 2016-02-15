/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;

public interface JsonArrayFormatVisitor
extends JsonFormatVisitorWithSerializerProvider {
    public void itemsFormat(JsonFormatTypes var1) throws JsonMappingException;

    public void itemsFormat(JsonFormatVisitable var1, JavaType var2) throws JsonMappingException;

    public static class Base
    implements JsonArrayFormatVisitor {
        protected SerializerProvider _provider;

        public Base() {
        }

        public Base(SerializerProvider serializerProvider) {
            this._provider = serializerProvider;
        }

        @Override
        public SerializerProvider getProvider() {
            return this._provider;
        }

        @Override
        public void itemsFormat(JsonFormatTypes jsonFormatTypes) throws JsonMappingException {
        }

        @Override
        public void itemsFormat(JsonFormatVisitable jsonFormatVisitable, JavaType javaType) throws JsonMappingException {
        }

        @Override
        public void setProvider(SerializerProvider serializerProvider) {
            this._provider = serializerProvider;
        }
    }

}

