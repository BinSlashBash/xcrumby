/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;

public abstract class TypeIdResolverBase
implements TypeIdResolver {
    protected final JavaType _baseType;
    protected final TypeFactory _typeFactory;

    protected TypeIdResolverBase() {
        this(null, null);
    }

    protected TypeIdResolverBase(JavaType javaType, TypeFactory typeFactory) {
        this._baseType = javaType;
        this._typeFactory = typeFactory;
    }

    @Override
    public String idFromBaseType() {
        return this.idFromValueAndType(null, this._baseType.getRawClass());
    }

    @Override
    public void init(JavaType javaType) {
    }

    public JavaType typeFromId(DatabindContext databindContext, String string2) {
        return this.typeFromId(string2);
    }

    @Deprecated
    @Override
    public abstract JavaType typeFromId(String var1);
}

