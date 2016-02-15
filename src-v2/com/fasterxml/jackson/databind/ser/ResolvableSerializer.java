/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;

public interface ResolvableSerializer {
    public void resolve(SerializerProvider var1) throws JsonMappingException;
}

