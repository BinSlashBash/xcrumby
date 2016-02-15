/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import java.io.IOException;

public class JsonProcessingException
extends IOException {
    static final long serialVersionUID = 123;
    protected JsonLocation _location;

    protected JsonProcessingException(String string2) {
        super(string2);
    }

    protected JsonProcessingException(String string2, JsonLocation jsonLocation) {
        this(string2, jsonLocation, null);
    }

    protected JsonProcessingException(String string2, JsonLocation jsonLocation, Throwable throwable) {
        super(string2);
        if (throwable != null) {
            this.initCause(throwable);
        }
        this._location = jsonLocation;
    }

    protected JsonProcessingException(String string2, Throwable throwable) {
        this(string2, null, throwable);
    }

    protected JsonProcessingException(Throwable throwable) {
        this(null, null, throwable);
    }

    public JsonLocation getLocation() {
        return this._location;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String getMessage() {
        String string2;
        void var2_6;
        String string3 = string2 = super.getMessage();
        if (string2 == null) {
            string3 = "N/A";
        }
        JsonLocation jsonLocation = this.getLocation();
        String string4 = this.getMessageSuffix();
        if (jsonLocation == null) {
            String string5 = string3;
            if (string4 == null) return var2_6;
        }
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append(string3);
        if (string4 != null) {
            stringBuilder.append(string4);
        }
        if (jsonLocation != null) {
            stringBuilder.append('\n');
            stringBuilder.append(" at ");
            stringBuilder.append(jsonLocation.toString());
        }
        String string6 = stringBuilder.toString();
        return var2_6;
    }

    protected String getMessageSuffix() {
        return null;
    }

    public String getOriginalMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ": " + this.getMessage();
    }
}

