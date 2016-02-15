/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;

public final class ObjectIdValueProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final ObjectIdReader _objectIdReader;

    @Deprecated
    public ObjectIdValueProperty(ObjectIdReader objectIdReader) {
        this(objectIdReader, PropertyMetadata.STD_REQUIRED);
    }

    public ObjectIdValueProperty(ObjectIdReader objectIdReader, PropertyMetadata propertyMetadata) {
        super(objectIdReader.propertyName, objectIdReader.getIdType(), propertyMetadata, objectIdReader.getDeserializer());
        this._objectIdReader = objectIdReader;
    }

    protected ObjectIdValueProperty(ObjectIdValueProperty objectIdValueProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)objectIdValueProperty, jsonDeserializer);
        this._objectIdReader = objectIdValueProperty._objectIdReader;
    }

    @Deprecated
    protected ObjectIdValueProperty(ObjectIdValueProperty objectIdValueProperty, PropertyName propertyName) {
        super((SettableBeanProperty)objectIdValueProperty, propertyName);
        this._objectIdReader = objectIdValueProperty._objectIdReader;
    }

    @Deprecated
    protected ObjectIdValueProperty(ObjectIdValueProperty objectIdValueProperty, String string2) {
        this(objectIdValueProperty, new PropertyName(string2));
    }

    @Override
    public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        this.deserializeSetAndReturn(jsonParser, deserializationContext, object);
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser object, DeserializationContext serializable, Object object2) throws IOException, JsonProcessingException {
        Object t2 = this._valueDeserializer.deserialize((JsonParser)object, (DeserializationContext)serializable);
        serializable.findObjectId(t2, this._objectIdReader.generator, this._objectIdReader.resolver).bindItem(object2);
        serializable = this._objectIdReader.idProperty;
        object = object2;
        if (serializable != null) {
            object = serializable.setAndReturn(object2, t2);
        }
        return object;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return null;
    }

    @Override
    public AnnotatedMember getMember() {
        return null;
    }

    @Override
    public void set(Object object, Object object2) throws IOException {
        this.setAndReturn(object, object2);
    }

    @Override
    public Object setAndReturn(Object object, Object object2) throws IOException {
        SettableBeanProperty settableBeanProperty = this._objectIdReader.idProperty;
        if (settableBeanProperty == null) {
            throw new UnsupportedOperationException("Should not call set() on ObjectIdProperty that has no SettableBeanProperty");
        }
        return settableBeanProperty.setAndReturn(object, object2);
    }

    @Override
    public ObjectIdValueProperty withName(PropertyName propertyName) {
        return new ObjectIdValueProperty(this, propertyName);
    }

    @Override
    public ObjectIdValueProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new ObjectIdValueProperty(this, jsonDeserializer);
    }
}

