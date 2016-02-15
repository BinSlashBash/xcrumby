/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public class PropertyBasedObjectIdGenerator
extends ObjectIdGenerators.PropertyGenerator {
    private static final long serialVersionUID = 1;
    protected final BeanPropertyWriter _property;

    public PropertyBasedObjectIdGenerator(ObjectIdInfo objectIdInfo, BeanPropertyWriter beanPropertyWriter) {
        this(objectIdInfo.getScope(), beanPropertyWriter);
    }

    protected PropertyBasedObjectIdGenerator(Class<?> class_, BeanPropertyWriter beanPropertyWriter) {
        super(class_);
        this._property = beanPropertyWriter;
    }

    @Override
    public boolean canUseFor(ObjectIdGenerator<?> propertyBasedObjectIdGenerator) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (propertyBasedObjectIdGenerator.getClass() == this.getClass()) {
            propertyBasedObjectIdGenerator = propertyBasedObjectIdGenerator;
            bl3 = bl2;
            if (propertyBasedObjectIdGenerator.getScope() == this._scope) {
                bl3 = bl2;
                if (propertyBasedObjectIdGenerator._property == this._property) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }

    @Override
    public ObjectIdGenerator<Object> forScope(Class<?> class_) {
        if (class_ == this._scope) {
            return this;
        }
        return new PropertyBasedObjectIdGenerator(class_, this._property);
    }

    @Override
    public Object generateId(Object object) {
        try {
            object = this._property.get(object);
            return object;
        }
        catch (RuntimeException var1_2) {
            throw var1_2;
        }
        catch (Exception var1_3) {
            throw new IllegalStateException("Problem accessing property '" + this._property.getName() + "': " + var1_3.getMessage(), var1_3);
        }
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

