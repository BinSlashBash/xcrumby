/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;

public class InvalidFormatException
extends JsonMappingException {
    private static final long serialVersionUID = 1;
    protected final Class<?> _targetType;
    protected final Object _value;

    public InvalidFormatException(String string2, JsonLocation jsonLocation, Object object, Class<?> class_) {
        super(string2, jsonLocation);
        this._value = object;
        this._targetType = class_;
    }

    public InvalidFormatException(String string2, Object object, Class<?> class_) {
        super(string2);
        this._value = object;
        this._targetType = class_;
    }

    public static InvalidFormatException from(JsonParser jsonParser, String string2, Object object, Class<?> class_) {
        return new InvalidFormatException(string2, jsonParser.getTokenLocation(), object, class_);
    }

    public Class<?> getTargetType() {
        return this._targetType;
    }

    public Object getValue() {
        return this._value;
    }
}

