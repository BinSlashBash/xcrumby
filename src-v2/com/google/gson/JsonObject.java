/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

public final class JsonObject
extends JsonElement {
    private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap();

    private JsonElement createJsonElement(Object object) {
        if (object == null) {
            return JsonNull.INSTANCE;
        }
        return new JsonPrimitive(object);
    }

    public void add(String string2, JsonElement jsonElement) {
        JsonElement jsonElement2 = jsonElement;
        if (jsonElement == null) {
            jsonElement2 = JsonNull.INSTANCE;
        }
        this.members.put(string2, jsonElement2);
    }

    public void addProperty(String string2, Boolean bl2) {
        this.add(string2, this.createJsonElement(bl2));
    }

    public void addProperty(String string2, Character c2) {
        this.add(string2, this.createJsonElement(c2));
    }

    public void addProperty(String string2, Number number) {
        this.add(string2, this.createJsonElement(number));
    }

    public void addProperty(String string2, String string3) {
        this.add(string2, this.createJsonElement(string3));
    }

    @Override
    JsonObject deepCopy() {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : this.members.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue().deepCopy());
        }
        return jsonObject;
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }

    public boolean equals(Object object) {
        if (object == this || object instanceof JsonObject && ((JsonObject)object).members.equals(this.members)) {
            return true;
        }
        return false;
    }

    public JsonElement get(String string2) {
        return this.members.get(string2);
    }

    public JsonArray getAsJsonArray(String string2) {
        return (JsonArray)this.members.get(string2);
    }

    public JsonObject getAsJsonObject(String string2) {
        return (JsonObject)this.members.get(string2);
    }

    public JsonPrimitive getAsJsonPrimitive(String string2) {
        return (JsonPrimitive)this.members.get(string2);
    }

    public boolean has(String string2) {
        return this.members.containsKey(string2);
    }

    public int hashCode() {
        return this.members.hashCode();
    }

    public JsonElement remove(String string2) {
        return this.members.remove(string2);
    }
}

