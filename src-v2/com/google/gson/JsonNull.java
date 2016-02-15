/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonElement;

public final class JsonNull
extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    @Override
    JsonNull deepCopy() {
        return INSTANCE;
    }

    public boolean equals(Object object) {
        if (this == object || object instanceof JsonNull) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return JsonNull.class.hashCode();
    }
}

