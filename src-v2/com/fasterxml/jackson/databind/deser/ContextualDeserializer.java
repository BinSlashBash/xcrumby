/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ContextualDeserializer {
    public JsonDeserializer<?> createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException;
}

