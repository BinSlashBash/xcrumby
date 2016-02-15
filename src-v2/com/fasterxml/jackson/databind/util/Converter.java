/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public interface Converter<IN, OUT> {
    public OUT convert(IN var1);

    public JavaType getInputType(TypeFactory var1);

    public JavaType getOutputType(TypeFactory var1);

    public static abstract class None
    implements Converter<Object, Object> {
    }

}

