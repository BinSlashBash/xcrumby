/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JsonFormatTypes {
    STRING,
    NUMBER,
    INTEGER,
    BOOLEAN,
    OBJECT,
    ARRAY,
    NULL,
    ANY;
    

    private JsonFormatTypes() {
    }

    @JsonCreator
    public static JsonFormatTypes forValue(String string2) {
        return JsonFormatTypes.valueOf(string2.toUpperCase());
    }

    @JsonValue
    public String value() {
        return this.name().toLowerCase();
    }
}

