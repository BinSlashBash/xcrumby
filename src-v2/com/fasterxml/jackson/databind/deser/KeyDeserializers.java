/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;

public interface KeyDeserializers {
    public KeyDeserializer findKeyDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException;
}

