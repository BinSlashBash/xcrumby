/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import java.io.IOException;
import java.io.Serializable;

public final class PropertyValueBuffer {
    private PropertyValue _buffered;
    protected final DeserializationContext _context;
    protected final Object[] _creatorParameters;
    private Object _idValue;
    protected final ObjectIdReader _objectIdReader;
    private int _paramsNeeded;
    protected final JsonParser _parser;

    public PropertyValueBuffer(JsonParser jsonParser, DeserializationContext deserializationContext, int n2, ObjectIdReader objectIdReader) {
        this._parser = jsonParser;
        this._context = deserializationContext;
        this._paramsNeeded = n2;
        this._objectIdReader = objectIdReader;
        this._creatorParameters = new Object[n2];
    }

    public boolean assignParameter(int n2, Object object) {
        this._creatorParameters[n2] = object;
        this._paramsNeeded = n2 = this._paramsNeeded - 1;
        if (n2 <= 0) {
            return true;
        }
        return false;
    }

    public void bufferAnyProperty(SettableAnyProperty settableAnyProperty, String string2, Object object) {
        this._buffered = new PropertyValue.Any(this._buffered, object, settableAnyProperty, string2);
    }

    public void bufferMapProperty(Object object, Object object2) {
        this._buffered = new PropertyValue.Map(this._buffered, object2, object);
    }

    public void bufferProperty(SettableBeanProperty settableBeanProperty, Object object) {
        this._buffered = new PropertyValue.Regular(this._buffered, object, settableBeanProperty);
    }

    protected PropertyValue buffered() {
        return this._buffered;
    }

    protected final Object[] getParameters(Object[] arrobject) {
        if (arrobject != null) {
            int n2 = this._creatorParameters.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                Object object;
                if (this._creatorParameters[i2] != null || (object = arrobject[i2]) == null) continue;
                this._creatorParameters[i2] = object;
            }
        }
        return this._creatorParameters;
    }

    public Object handleIdValue(DeserializationContext serializable, Object object) throws IOException {
        Object object2 = object;
        if (this._objectIdReader != null) {
            object2 = object;
            if (this._idValue != null) {
                serializable.findObjectId(this._idValue, this._objectIdReader.generator, this._objectIdReader.resolver).bindItem(object);
                serializable = this._objectIdReader.idProperty;
                object2 = object;
                if (serializable != null) {
                    object2 = serializable.setAndReturn(object, this._idValue);
                }
            }
        }
        return object2;
    }

    public void inject(SettableBeanProperty[] arrsettableBeanProperty) {
        for (SettableBeanProperty settableBeanProperty : arrsettableBeanProperty) {
            if (settableBeanProperty == null) continue;
            this._creatorParameters[var2_2] = this._context.findInjectableValue(settableBeanProperty.getInjectableValueId(), settableBeanProperty, null);
        }
    }

    public boolean isComplete() {
        if (this._paramsNeeded <= 0) {
            return true;
        }
        return false;
    }

    public boolean readIdProperty(String string2) throws IOException {
        if (this._objectIdReader != null && string2.equals(this._objectIdReader.propertyName.getSimpleName())) {
            this._idValue = this._objectIdReader.readObjectReference(this._parser, this._context);
            return true;
        }
        return false;
    }
}

