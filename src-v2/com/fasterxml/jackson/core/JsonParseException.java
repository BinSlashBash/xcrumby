/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonParseException
extends JsonProcessingException {
    private static final long serialVersionUID = 1;

    public JsonParseException(String string2, JsonLocation jsonLocation) {
        super(string2, jsonLocation);
    }

    public JsonParseException(String string2, JsonLocation jsonLocation, Throwable throwable) {
        super(string2, jsonLocation, throwable);
    }
}

