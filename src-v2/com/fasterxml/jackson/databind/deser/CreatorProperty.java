/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class CreatorProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final AnnotatedParameter _annotated;
    protected final int _creatorIndex;
    protected final SettableBeanProperty _fallbackSetter;
    protected final Object _injectableValueId;

    public CreatorProperty(PropertyName propertyName, JavaType javaType, PropertyName propertyName2, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedParameter annotatedParameter, int n2, Object object, PropertyMetadata propertyMetadata) {
        super(propertyName, javaType, propertyName2, typeDeserializer, annotations, propertyMetadata);
        this._annotated = annotatedParameter;
        this._creatorIndex = n2;
        this._injectableValueId = object;
        this._fallbackSetter = null;
    }

    protected CreatorProperty(CreatorProperty creatorProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)creatorProperty, jsonDeserializer);
        this._annotated = creatorProperty._annotated;
        this._creatorIndex = creatorProperty._creatorIndex;
        this._injectableValueId = creatorProperty._injectableValueId;
        this._fallbackSetter = creatorProperty._fallbackSetter;
    }

    protected CreatorProperty(CreatorProperty creatorProperty, PropertyName propertyName) {
        super((SettableBeanProperty)creatorProperty, propertyName);
        this._annotated = creatorProperty._annotated;
        this._creatorIndex = creatorProperty._creatorIndex;
        this._injectableValueId = creatorProperty._injectableValueId;
        this._fallbackSetter = creatorProperty._fallbackSetter;
    }

    protected CreatorProperty(CreatorProperty creatorProperty, SettableBeanProperty settableBeanProperty) {
        super(creatorProperty);
        this._annotated = creatorProperty._annotated;
        this._creatorIndex = creatorProperty._creatorIndex;
        this._injectableValueId = creatorProperty._injectableValueId;
        this._fallbackSetter = settableBeanProperty;
    }

    @Deprecated
    protected CreatorProperty(CreatorProperty creatorProperty, String string2) {
        this(creatorProperty, new PropertyName(string2));
    }

    @Deprecated
    public CreatorProperty(String string2, JavaType javaType, PropertyName propertyName, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedParameter annotatedParameter, int n2, Object object, boolean bl2) {
        this(new PropertyName(string2), javaType, propertyName, typeDeserializer, annotations, annotatedParameter, n2, object, PropertyMetadata.construct(bl2, null, null));
    }

    @Override
    public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        this.set(object, this.deserialize(jsonParser, deserializationContext));
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        return this.setAndReturn(object, this.deserialize(jsonParser, deserializationContext));
    }

    public Object findInjectableValue(DeserializationContext deserializationContext, Object object) {
        if (this._injectableValueId == null) {
            throw new IllegalStateException("Property '" + this.getName() + "' (type " + this.getClass().getName() + ") has no injectable value id configured");
        }
        return deserializationContext.findInjectableValue(this._injectableValueId, this, object);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        if (this._annotated == null) {
            return null;
        }
        return this._annotated.getAnnotation(class_);
    }

    @Override
    public int getCreatorIndex() {
        return this._creatorIndex;
    }

    @Override
    public Object getInjectableValueId() {
        return this._injectableValueId;
    }

    @Override
    public AnnotatedMember getMember() {
        return this._annotated;
    }

    public void inject(DeserializationContext deserializationContext, Object object) throws IOException {
        this.set(object, this.findInjectableValue(deserializationContext, object));
    }

    @Override
    public void set(Object object, Object object2) throws IOException {
        if (this._fallbackSetter == null) {
            throw new IllegalStateException("No fallback setter/field defined: can not use creator property for " + this.getClass().getName());
        }
        this._fallbackSetter.set(object, object2);
    }

    @Override
    public Object setAndReturn(Object object, Object object2) throws IOException {
        if (this._fallbackSetter == null) {
            throw new IllegalStateException("No fallback setter/field defined: can not use creator property for " + this.getClass().getName());
        }
        return this._fallbackSetter.setAndReturn(object, object2);
    }

    @Override
    public String toString() {
        return "[creator property, name '" + this.getName() + "'; inject id '" + this._injectableValueId + "']";
    }

    public CreatorProperty withFallbackSetter(SettableBeanProperty settableBeanProperty) {
        return new CreatorProperty(this, settableBeanProperty);
    }

    @Override
    public CreatorProperty withName(PropertyName propertyName) {
        return new CreatorProperty(this, propertyName);
    }

    @Override
    public CreatorProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new CreatorProperty(this, jsonDeserializer);
    }
}

