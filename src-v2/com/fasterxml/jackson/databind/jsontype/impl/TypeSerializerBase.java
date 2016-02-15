/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public abstract class TypeSerializerBase
extends TypeSerializer {
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;

    protected TypeSerializerBase(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        this._idResolver = typeIdResolver;
        this._property = beanProperty;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }

    @Override
    public abstract JsonTypeInfo.As getTypeInclusion();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected String idFromValue(Object object) {
        String string2 = this._idResolver.idFromValue(object);
        if (string2 != null) return string2;
        if (object == null) {
            object = "NULL";
            do {
                throw new IllegalArgumentException("Can not resolve type id for " + (String)object + " (using " + this._idResolver.getClass().getName() + ")");
                break;
            } while (true);
        }
        object = object.getClass().getName();
        throw new IllegalArgumentException("Can not resolve type id for " + (String)object + " (using " + this._idResolver.getClass().getName() + ")");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected String idFromValueAndType(Object object, Class<?> object2) {
        if ((object2 = this._idResolver.idFromValueAndType(object, object2)) != null) return object2;
        if (object == null) {
            object = "NULL";
            do {
                throw new IllegalArgumentException("Can not resolve type id for " + (String)object + " (using " + this._idResolver.getClass().getName() + ")");
                break;
            } while (true);
        }
        object = object.getClass().getName();
        throw new IllegalArgumentException("Can not resolve type id for " + (String)object + " (using " + this._idResolver.getClass().getName() + ")");
    }
}

