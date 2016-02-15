/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import java.io.IOException;

public abstract class ValueInstantiator {
    protected Object _createFromStringFallbacks(DeserializationContext deserializationContext, String string2) throws IOException, JsonProcessingException {
        if (this.canCreateFromBoolean()) {
            String string3 = string2.trim();
            if ("true".equals(string3)) {
                return this.createFromBoolean(deserializationContext, true);
            }
            if ("false".equals(string3)) {
                return this.createFromBoolean(deserializationContext, false);
            }
        }
        if (string2.length() == 0 && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
            return null;
        }
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from String value ('" + string2 + "'); no single-String constructor/factory method");
    }

    public boolean canCreateFromBoolean() {
        return false;
    }

    public boolean canCreateFromDouble() {
        return false;
    }

    public boolean canCreateFromInt() {
        return false;
    }

    public boolean canCreateFromLong() {
        return false;
    }

    public boolean canCreateFromObjectWith() {
        return false;
    }

    public boolean canCreateFromString() {
        return false;
    }

    public boolean canCreateUsingDefault() {
        if (this.getDefaultCreator() != null) {
            return true;
        }
        return false;
    }

    public boolean canCreateUsingDelegate() {
        return false;
    }

    public boolean canInstantiate() {
        if (this.canCreateUsingDefault() || this.canCreateUsingDelegate() || this.canCreateFromObjectWith() || this.canCreateFromString() || this.canCreateFromInt() || this.canCreateFromLong() || this.canCreateFromDouble() || this.canCreateFromBoolean()) {
            return true;
        }
        return false;
    }

    public Object createFromBoolean(DeserializationContext deserializationContext, boolean bl2) throws IOException {
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Boolean value (" + bl2 + ")");
    }

    public Object createFromDouble(DeserializationContext deserializationContext, double d2) throws IOException {
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Floating-point number (" + d2 + ", double)");
    }

    public Object createFromInt(DeserializationContext deserializationContext, int n2) throws IOException {
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Integer number (" + n2 + ", int)");
    }

    public Object createFromLong(DeserializationContext deserializationContext, long l2) throws IOException {
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Integer number (" + l2 + ", long)");
    }

    public Object createFromObjectWith(DeserializationContext deserializationContext, Object[] arrobject) throws IOException {
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " with arguments");
    }

    public Object createFromString(DeserializationContext deserializationContext, String string2) throws IOException {
        return this._createFromStringFallbacks(deserializationContext, string2);
    }

    public Object createUsingDefault(DeserializationContext deserializationContext) throws IOException {
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + "; no default creator found");
    }

    public Object createUsingDelegate(DeserializationContext deserializationContext, Object object) throws IOException {
        throw deserializationContext.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " using delegate");
    }

    public AnnotatedWithParams getDefaultCreator() {
        return null;
    }

    public AnnotatedWithParams getDelegateCreator() {
        return null;
    }

    public JavaType getDelegateType(DeserializationConfig deserializationConfig) {
        return null;
    }

    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig deserializationConfig) {
        return null;
    }

    public AnnotatedParameter getIncompleteParameter() {
        return null;
    }

    public abstract String getValueTypeDesc();

    public AnnotatedWithParams getWithArgsCreator() {
        return null;
    }
}

