/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.fragment.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JNH {
    public static final int getAttributeInt(JsonObject jsonElement, String string2) {
        if ((jsonElement = jsonElement.get(string2)) == null || jsonElement.isJsonNull()) {
            return 0;
        }
        return jsonElement.getAsInt();
    }

    public static final String getAttributeString(JsonObject jsonElement, String string2) {
        if ((jsonElement = jsonElement.get(string2)) == null || jsonElement.isJsonNull()) {
            return null;
        }
        return jsonElement.getAsString();
    }
}

