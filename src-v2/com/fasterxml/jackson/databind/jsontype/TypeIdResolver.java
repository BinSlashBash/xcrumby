/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;

public interface TypeIdResolver {
    public JsonTypeInfo.Id getMechanism();

    public String idFromBaseType();

    public String idFromValue(Object var1);

    public String idFromValueAndType(Object var1, Class<?> var2);

    public void init(JavaType var1);

    public JavaType typeFromId(String var1);
}

