/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class PropertyBasedObjectIdGenerator
extends ObjectIdGenerators.PropertyGenerator {
    private static final long serialVersionUID = 1;

    public PropertyBasedObjectIdGenerator(Class<?> class_) {
        super(class_);
    }

    @Override
    public ObjectIdGenerator<Object> forScope(Class<?> class_) {
        if (class_ == this._scope) {
            return this;
        }
        return new PropertyBasedObjectIdGenerator(class_);
    }

    @Override
    public Object generateId(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ObjectIdGenerator.IdKey key(Object object) {
        return new ObjectIdGenerator.IdKey(this.getClass(), this._scope, object);
    }

    @Override
    public ObjectIdGenerator<Object> newForSerialization(Object object) {
        return this;
    }
}

