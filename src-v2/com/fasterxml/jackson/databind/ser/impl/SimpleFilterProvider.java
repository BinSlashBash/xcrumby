/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimpleFilterProvider
extends FilterProvider
implements Serializable {
    private static final long serialVersionUID = -6305772546718366447L;
    protected boolean _cfgFailOnUnknownId = true;
    protected PropertyFilter _defaultFilter;
    protected final Map<String, PropertyFilter> _filtersById;

    public SimpleFilterProvider() {
        this(new HashMap());
    }

    public SimpleFilterProvider(Map<String, ?> map) {
        Iterator iterator = map.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof PropertyFilter) continue;
            this._filtersById = SimpleFilterProvider._convert(map);
            return;
        }
        this._filtersById = map;
    }

    private static final PropertyFilter _convert(BeanPropertyFilter beanPropertyFilter) {
        return SimpleBeanPropertyFilter.from(beanPropertyFilter);
    }

    private static final Map<String, PropertyFilter> _convert(Map<String, ?> object) {
        HashMap<String, PropertyFilter> hashMap = new HashMap<String, PropertyFilter>();
        for (Map.Entry entry : object.entrySet()) {
            Object v2 = entry.getValue();
            if (v2 instanceof PropertyFilter) {
                hashMap.put((String)entry.getKey(), (PropertyFilter)v2);
                continue;
            }
            if (v2 instanceof BeanPropertyFilter) {
                hashMap.put((String)entry.getKey(), SimpleFilterProvider._convert((BeanPropertyFilter)v2));
                continue;
            }
            throw new IllegalArgumentException("Unrecognized filter type (" + v2.getClass().getName() + ")");
        }
        return hashMap;
    }

    @Deprecated
    public SimpleFilterProvider addFilter(String string2, BeanPropertyFilter beanPropertyFilter) {
        this._filtersById.put(string2, SimpleFilterProvider._convert(beanPropertyFilter));
        return this;
    }

    public SimpleFilterProvider addFilter(String string2, PropertyFilter propertyFilter) {
        this._filtersById.put(string2, propertyFilter);
        return this;
    }

    public SimpleFilterProvider addFilter(String string2, SimpleBeanPropertyFilter simpleBeanPropertyFilter) {
        this._filtersById.put(string2, simpleBeanPropertyFilter);
        return this;
    }

    @Deprecated
    @Override
    public BeanPropertyFilter findFilter(Object object) {
        throw new UnsupportedOperationException("Access to deprecated filters not supported");
    }

    @Override
    public PropertyFilter findPropertyFilter(Object object, Object object2) {
        PropertyFilter propertyFilter;
        object2 = propertyFilter = this._filtersById.get(object);
        if (propertyFilter == null) {
            object2 = propertyFilter = this._defaultFilter;
            if (propertyFilter == null) {
                object2 = propertyFilter;
                if (this._cfgFailOnUnknownId) {
                    throw new IllegalArgumentException("No filter configured with id '" + object + "' (type " + object.getClass().getName() + ")");
                }
            }
        }
        return object2;
    }

    public PropertyFilter getDefaultFilter() {
        return this._defaultFilter;
    }

    public PropertyFilter removeFilter(String string2) {
        return this._filtersById.remove(string2);
    }

    @Deprecated
    public SimpleFilterProvider setDefaultFilter(BeanPropertyFilter beanPropertyFilter) {
        this._defaultFilter = SimpleBeanPropertyFilter.from(beanPropertyFilter);
        return this;
    }

    public SimpleFilterProvider setDefaultFilter(PropertyFilter propertyFilter) {
        this._defaultFilter = propertyFilter;
        return this;
    }

    public SimpleFilterProvider setDefaultFilter(SimpleBeanPropertyFilter simpleBeanPropertyFilter) {
        this._defaultFilter = simpleBeanPropertyFilter;
        return this;
    }

    public SimpleFilterProvider setFailOnUnknownId(boolean bl2) {
        this._cfgFailOnUnknownId = bl2;
        return this;
    }

    public boolean willFailOnUnknownId() {
        return this._cfgFailOnUnknownId;
    }
}

