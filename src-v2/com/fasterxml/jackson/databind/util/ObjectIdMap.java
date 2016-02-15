/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.util.IdentityHashMap;

public class ObjectIdMap
extends IdentityHashMap<Object, Object> {
    public ObjectIdMap() {
        super(16);
    }

    public Object findId(Object object) {
        return this.get(object);
    }

    public void insertId(Object object, Object object2) {
        this.put(object, object2);
    }
}

