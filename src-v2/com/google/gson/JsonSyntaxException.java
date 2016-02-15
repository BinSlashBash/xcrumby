/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonParseException;

public final class JsonSyntaxException
extends JsonParseException {
    private static final long serialVersionUID = 1;

    public JsonSyntaxException(String string2) {
        super(string2);
    }

    public JsonSyntaxException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public JsonSyntaxException(Throwable throwable) {
        super(throwable);
    }
}

