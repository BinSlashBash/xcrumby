/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;

public class SimpleValueInstantiators
extends ValueInstantiators.Base
implements Serializable {
    private static final long serialVersionUID = -8929386427526115130L;
    protected HashMap<ClassKey, ValueInstantiator> _classMappings = new HashMap();

    public SimpleValueInstantiators addValueInstantiator(Class<?> class_, ValueInstantiator valueInstantiator) {
        this._classMappings.put(new ClassKey(class_), valueInstantiator);
        return this;
    }

    @Override
    public ValueInstantiator findValueInstantiator(DeserializationConfig object, BeanDescription beanDescription, ValueInstantiator valueInstantiator) {
        object = this._classMappings.get(new ClassKey(beanDescription.getBeanClass()));
        if (object == null) {
            return valueInstantiator;
        }
        return object;
    }
}

