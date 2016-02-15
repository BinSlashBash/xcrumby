/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;

public interface ContextualKeyDeserializer {
    public KeyDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException;
}

