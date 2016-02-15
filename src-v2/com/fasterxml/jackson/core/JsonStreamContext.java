/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

public abstract class JsonStreamContext {
    protected static final int TYPE_ARRAY = 1;
    protected static final int TYPE_OBJECT = 2;
    protected static final int TYPE_ROOT = 0;
    protected int _index;
    protected int _type;

    protected JsonStreamContext() {
    }

    public final int getCurrentIndex() {
        if (this._index < 0) {
            return 0;
        }
        return this._index;
    }

    public abstract String getCurrentName();

    public final int getEntryCount() {
        return this._index + 1;
    }

    public abstract JsonStreamContext getParent();

    public final String getTypeDesc() {
        switch (this._type) {
            default: {
                return "?";
            }
            case 0: {
                return "ROOT";
            }
            case 1: {
                return "ARRAY";
            }
            case 2: 
        }
        return "OBJECT";
    }

    public final boolean inArray() {
        if (this._type == 1) {
            return true;
        }
        return false;
    }

    public final boolean inObject() {
        if (this._type == 2) {
            return true;
        }
        return false;
    }

    public final boolean inRoot() {
        if (this._type == 0) {
            return true;
        }
        return false;
    }
}

