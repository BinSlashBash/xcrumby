/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

public interface ValueInstantiators {
    public ValueInstantiator findValueInstantiator(DeserializationConfig var1, BeanDescription var2, ValueInstantiator var3);

    public static class Base
    implements ValueInstantiators {
        @Override
        public ValueInstantiator findValueInstantiator(DeserializationConfig deserializationConfig, BeanDescription beanDescription, ValueInstantiator valueInstantiator) {
            return valueInstantiator;
        }
    }

}

