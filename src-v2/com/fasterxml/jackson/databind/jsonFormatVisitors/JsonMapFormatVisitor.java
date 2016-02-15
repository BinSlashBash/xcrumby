/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;

public interface JsonMapFormatVisitor
extends JsonFormatVisitorWithSerializerProvider {
    public void keyFormat(JsonFormatVisitable var1, JavaType var2) throws JsonMappingException;

    public void valueFormat(JsonFormatVisitable var1, JavaType var2) throws JsonMappingException;

    public static class Base
    implements JsonMapFormatVisitor {
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
        public void keyFormat(JsonFormatVisitable jsonFormatVisitable, JavaType javaType) throws JsonMappingException {
        }

        @Override
        public void setProvider(SerializerProvider serializerProvider) {
            this._provider = serializerProvider;
        }

        @Override
        public void valueFormat(JsonFormatVisitable jsonFormatVisitable, JavaType javaType) throws JsonMappingException {
        }
    }

}

