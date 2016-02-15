/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import java.util.HashMap;
import java.util.Map;

public class SimpleObjectIdResolver
implements ObjectIdResolver {
    private Map<ObjectIdGenerator.IdKey, Object> _items = new HashMap<ObjectIdGenerator.IdKey, Object>();

    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object object) {
        if (this._items.containsKey(idKey)) {
            throw new IllegalStateException("Already had POJO for id (" + idKey.key.getClass().getName() + ") [" + idKey + "]");
        }
        this._items.put(idKey, object);
    }

    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        if (objectIdResolver.getClass() == this.getClass()) {
            return true;
        }
        return false;
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object object) {
        return this;
    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        return this._items.get(idKey);
    }
}

