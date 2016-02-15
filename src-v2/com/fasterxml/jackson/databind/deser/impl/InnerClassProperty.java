/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.NullProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public final class InnerClassProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final Constructor<?> _creator;
    protected final SettableBeanProperty _delegate;

    public InnerClassProperty(SettableBeanProperty settableBeanProperty, Constructor<?> constructor) {
        super(settableBeanProperty);
        this._delegate = settableBeanProperty;
        this._creator = constructor;
    }

    protected InnerClassProperty(InnerClassProperty innerClassProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)innerClassProperty, jsonDeserializer);
        this._delegate = innerClassProperty._delegate.withValueDeserializer(jsonDeserializer);
        this._creator = innerClassProperty._creator;
    }

    protected InnerClassProperty(InnerClassProperty innerClassProperty, PropertyName propertyName) {
        super((SettableBeanProperty)innerClassProperty, propertyName);
        this._delegate = innerClassProperty._delegate.withName(propertyName);
        this._creator = innerClassProperty._creator;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void deserializeAndSet(JsonParser object, DeserializationContext deserializationContext, Object object2) throws IOException, JsonProcessingException {
        if (object.getCurrentToken() == JsonToken.VALUE_NULL) {
            object = this._nullProvider == null ? null : this._nullProvider.nullValue(deserializationContext);
        } else if (this._valueTypeDeserializer != null) {
            object = this._valueDeserializer.deserializeWithType((JsonParser)object, deserializationContext, this._valueTypeDeserializer);
        } else {
            Object obj;
            try {
                obj = this._creator.newInstance(object2);
            }
            catch (Exception var4_5) {
                ClassUtil.unwrapAndThrowAsIAE(var4_5, "Failed to instantiate class " + this._creator.getDeclaringClass().getName() + ", problem: " + var4_5.getMessage());
                obj = null;
            }
            this._valueDeserializer.deserialize((JsonParser)object, deserializationContext, obj);
            object = obj;
        }
        this.set(object2, object);
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        return this.setAndReturn(object, this.deserialize(jsonParser, deserializationContext));
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._delegate.getAnnotation(class_);
    }

    @Override
    public AnnotatedMember getMember() {
        return this._delegate.getMember();
    }

    @Override
    public final void set(Object object, Object object2) throws IOException {
        this._delegate.set(object, object2);
    }

    @Override
    public Object setAndReturn(Object object, Object object2) throws IOException {
        return this._delegate.setAndReturn(object, object2);
    }

    @Override
    public InnerClassProperty withName(PropertyName propertyName) {
        return new InnerClassProperty(this, propertyName);
    }

    @Override
    public InnerClassProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new InnerClassProperty(this, jsonDeserializer);
    }
}

