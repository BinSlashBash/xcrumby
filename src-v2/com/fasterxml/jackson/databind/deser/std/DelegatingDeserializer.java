/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.Collection;

public abstract class DelegatingDeserializer
extends StdDeserializer<Object>
implements ContextualDeserializer,
ResolvableDeserializer {
    private static final long serialVersionUID = 1;
    protected final JsonDeserializer<?> _delegatee;

    public DelegatingDeserializer(JsonDeserializer<?> jsonDeserializer) {
        super(DelegatingDeserializer._figureType(jsonDeserializer));
        this._delegatee = jsonDeserializer;
    }

    private static Class<?> _figureType(JsonDeserializer<?> object) {
        if ((object = object.handledType()) != null) {
            return object;
        }
        return Object.class;
    }

    @Deprecated
    protected JsonDeserializer<?> _createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty, JsonDeserializer<?> jsonDeserializer) {
        if (jsonDeserializer == this._delegatee) {
            return this;
        }
        return this.newDelegatingInstance(jsonDeserializer);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext object, BeanProperty beanProperty) throws JsonMappingException {
        if ((object = object.handleSecondaryContextualization(this._delegatee, beanProperty)) == this._delegatee) {
            return this;
        }
        return this.newDelegatingInstance(object);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this._delegatee.deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        return this._delegatee.deserialize(jsonParser, deserializationContext, (Object)object);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return this._delegatee.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
    }

    @Override
    public SettableBeanProperty findBackReference(String string2) {
        return this._delegatee.findBackReference(string2);
    }

    @Override
    public JsonDeserializer<?> getDelegatee() {
        return this._delegatee;
    }

    @Override
    public Object getEmptyValue() {
        return this._delegatee.getEmptyValue();
    }

    @Override
    public Collection<Object> getKnownPropertyNames() {
        return this._delegatee.getKnownPropertyNames();
    }

    @Override
    public Object getNullValue() {
        return this._delegatee.getNullValue();
    }

    @Override
    public ObjectIdReader getObjectIdReader() {
        return this._delegatee.getObjectIdReader();
    }

    @Override
    public boolean isCachable() {
        return this._delegatee.isCachable();
    }

    protected abstract JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> var1);

    @Override
    public JsonDeserializer<?> replaceDelegatee(JsonDeserializer<?> jsonDeserializer) {
        if (jsonDeserializer == this._delegatee) {
            return this;
        }
        return this.newDelegatingInstance(jsonDeserializer);
    }

    @Override
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        if (this._delegatee instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer)((Object)this._delegatee)).resolve(deserializationContext);
        }
    }
}

