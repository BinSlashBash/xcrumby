/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;

public class UnresolvedId {
    private final Object _id;
    private final JsonLocation _location;
    private final Class<?> _type;

    public UnresolvedId(Object object, Class<?> class_, JsonLocation jsonLocation) {
        this._id = object;
        this._type = class_;
        this._location = jsonLocation;
    }

    public Object getId() {
        return this._id;
    }

    public JsonLocation getLocation() {
        return this._location;
    }

    public Class<?> getType() {
        return this._type;
    }

    public String toString() {
        return String.format("Object id [%s] (for %s) at %s", this._id, this._type, this._location);
    }
}

