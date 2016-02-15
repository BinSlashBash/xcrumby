package com.crumby.lib.fragment.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JNH {
    public static final String getAttributeString(JsonObject obj, String attribute) {
        JsonElement el = obj.get(attribute);
        if (el == null || el.isJsonNull()) {
            return null;
        }
        return el.getAsString();
    }

    public static final int getAttributeInt(JsonObject obj, String attribute) {
        JsonElement el = obj.get(attribute);
        if (el == null || el.isJsonNull()) {
            return 0;
        }
        return el.getAsInt();
    }
}
