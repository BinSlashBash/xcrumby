package com.crumby.lib.router;

import com.google.gson.JsonPrimitive;

public class IndexAttributeValue<T> {
    T value;

    public IndexAttributeValue(T value) {
        this.value = value;
    }

    public JsonPrimitive getAsJsonPrimitive() {
        if (this.value instanceof Number) {
            return new JsonPrimitive((Number) this.value);
        }
        if (this.value instanceof Boolean) {
            return new JsonPrimitive((Boolean) this.value);
        }
        if (this.value instanceof String) {
            return new JsonPrimitive((String) this.value);
        }
        if (this.value instanceof Character) {
            return new JsonPrimitive((Character) this.value);
        }
        return null;
    }

    public T getObject() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setValueFromJson(JsonPrimitive valueJson) {
        if (this.value instanceof Number) {
            this.value = valueJson.getAsNumber();
        } else if (this.value instanceof Boolean) {
            this.value = Boolean.valueOf(valueJson.getAsBoolean());
        } else if (this.value instanceof String) {
            this.value = valueJson.getAsString();
        } else if (this.value instanceof Character) {
            this.value = Character.valueOf(valueJson.getAsCharacter());
        }
    }

    public IndexAttributeValue copy() {
        return new IndexAttributeValue(this.value);
    }

    public boolean getAsBoolean() {
        if (this.value instanceof Boolean) {
            return ((Boolean) this.value).booleanValue();
        }
        return false;
    }

    public String getAsString() {
        if (this.value instanceof String) {
            return (String) this.value;
        }
        return null;
    }
}
