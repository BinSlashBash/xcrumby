/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.router;

import com.google.gson.JsonPrimitive;

public class IndexAttributeValue<T> {
    T value;

    public IndexAttributeValue(T t2) {
        this.value = t2;
    }

    public IndexAttributeValue copy() {
        return new IndexAttributeValue<T>(this.value);
    }

    public boolean getAsBoolean() {
        if (!(this.value instanceof Boolean)) {
            return false;
        }
        return (Boolean)this.value;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonPrimitive getAsJsonPrimitive() {
        JsonPrimitive jsonPrimitive = null;
        if (this.value instanceof Number) {
            return new JsonPrimitive((Number)this.value);
        }
        if (this.value instanceof Boolean) {
            return new JsonPrimitive((Boolean)this.value);
        }
        if (this.value instanceof String) {
            return new JsonPrimitive((String)this.value);
        }
        if (!(this.value instanceof Character)) return jsonPrimitive;
        return new JsonPrimitive((Character)this.value);
    }

    public String getAsString() {
        if (!(this.value instanceof String)) {
            return null;
        }
        return (String)this.value;
    }

    public T getObject() {
        return this.value;
    }

    public void setValue(T t2) {
        this.value = t2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setValueFromJson(JsonPrimitive jsonPrimitive) {
        if (this.value instanceof Number) {
            this.value = jsonPrimitive.getAsNumber();
            return;
        } else {
            if (this.value instanceof Boolean) {
                this.value = jsonPrimitive.getAsBoolean();
                return;
            }
            if (this.value instanceof String) {
                this.value = jsonPrimitive.getAsString();
                return;
            }
            if (!(this.value instanceof Character)) return;
            {
                this.value = Character.valueOf(jsonPrimitive.getAsCharacter());
                return;
            }
        }
    }
}

