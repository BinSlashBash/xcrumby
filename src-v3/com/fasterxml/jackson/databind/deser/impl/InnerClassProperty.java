package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public final class InnerClassProperty extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final Constructor<?> _creator;
    protected final SettableBeanProperty _delegate;

    public InnerClassProperty(SettableBeanProperty delegate, Constructor<?> ctor) {
        super(delegate);
        this._delegate = delegate;
        this._creator = ctor;
    }

    protected InnerClassProperty(InnerClassProperty src, JsonDeserializer<?> deser) {
        super((SettableBeanProperty) src, (JsonDeserializer) deser);
        this._delegate = src._delegate.withValueDeserializer(deser);
        this._creator = src._creator;
    }

    protected InnerClassProperty(InnerClassProperty src, PropertyName newName) {
        super((SettableBeanProperty) src, newName);
        this._delegate = src._delegate.withName(newName);
        this._creator = src._creator;
    }

    public InnerClassProperty withName(PropertyName newName) {
        return new InnerClassProperty(this, newName);
    }

    public InnerClassProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new InnerClassProperty(this, (JsonDeserializer) deser);
    }

    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return this._delegate.getAnnotation(acls);
    }

    public AnnotatedMember getMember() {
        return this._delegate.getMember();
    }

    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object bean) throws IOException, JsonProcessingException {
        Object obj;
        if (jp.getCurrentToken() == JsonToken.VALUE_NULL) {
            if (this._nullProvider == null) {
                obj = null;
            } else {
                obj = this._nullProvider.nullValue(ctxt);
            }
        } else if (this._valueTypeDeserializer != null) {
            obj = this._valueDeserializer.deserializeWithType(jp, ctxt, this._valueTypeDeserializer);
        } else {
            try {
                obj = this._creator.newInstance(new Object[]{bean});
            } catch (Exception e) {
                ClassUtil.unwrapAndThrowAsIAE(e, "Failed to instantiate class " + this._creator.getDeclaringClass().getName() + ", problem: " + e.getMessage());
                obj = null;
            }
            this._valueDeserializer.deserialize(jp, ctxt, obj);
        }
        set(bean, obj);
    }

    public Object deserializeSetAndReturn(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException, JsonProcessingException {
        return setAndReturn(instance, deserialize(jp, ctxt));
    }

    public final void set(Object instance, Object value) throws IOException {
        this._delegate.set(instance, value);
    }

    public Object setAndReturn(Object instance, Object value) throws IOException {
        return this._delegate.setAndReturn(instance, value);
    }
}
