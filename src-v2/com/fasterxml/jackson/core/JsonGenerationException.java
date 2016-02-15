/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonGenerationException
extends JsonProcessingException {
    private static final long serialVersionUID = 123;

    public JsonGenerationException(String string2) {
        super(string2, (JsonLocation)null);
    }

    public JsonGenerationException(String string2, Throwable throwable) {
        super(string2, null, throwable);
    }

    public JsonGenerationException(Throwable throwable) {
        super(throwable);
    }
}

