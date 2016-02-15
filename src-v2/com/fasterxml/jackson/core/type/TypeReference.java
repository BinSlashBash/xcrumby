/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T>
implements Comparable<TypeReference<T>> {
    protected final Type _type;

    protected TypeReference() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        this._type = ((ParameterizedType)type).getActualTypeArguments()[0];
    }

    @Override
    public int compareTo(TypeReference<T> typeReference) {
        return 0;
    }

    public Type getType() {
        return this._type;
    }
}

