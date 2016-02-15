/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

public abstract class StdConverter<IN, OUT>
implements Converter<IN, OUT> {
    @Override
    public abstract OUT convert(IN var1);

    @Override
    public JavaType getInputType(TypeFactory arrjavaType) {
        if ((arrjavaType = arrjavaType.findTypeParameters(this.getClass(), Converter.class)) == null || arrjavaType.length < 2) {
            throw new IllegalStateException("Can not find OUT type parameter for Converter of type " + this.getClass().getName());
        }
        return arrjavaType[0];
    }

    @Override
    public JavaType getOutputType(TypeFactory arrjavaType) {
        if ((arrjavaType = arrjavaType.findTypeParameters(this.getClass(), Converter.class)) == null || arrjavaType.length < 2) {
            throw new IllegalStateException("Can not find OUT type parameter for Converter of type " + this.getClass().getName());
        }
        return arrjavaType[1];
    }
}

