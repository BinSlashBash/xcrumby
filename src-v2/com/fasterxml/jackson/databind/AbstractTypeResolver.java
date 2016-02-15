/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;

public abstract class AbstractTypeResolver {
    public JavaType findTypeMapping(DeserializationConfig deserializationConfig, JavaType javaType) {
        return null;
    }

    public JavaType resolveAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) {
        return null;
    }
}

