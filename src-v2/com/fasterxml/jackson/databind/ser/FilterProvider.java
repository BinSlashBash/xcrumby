/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

public abstract class FilterProvider {
    @Deprecated
    public abstract BeanPropertyFilter findFilter(Object var1);

    public PropertyFilter findPropertyFilter(Object object, Object object2) {
        if ((object = this.findFilter(object)) == null) {
            return null;
        }
        return SimpleBeanPropertyFilter.from((BeanPropertyFilter)object);
    }
}

