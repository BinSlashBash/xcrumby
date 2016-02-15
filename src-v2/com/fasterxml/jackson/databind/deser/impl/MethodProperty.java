/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public final class MethodProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final AnnotatedMethod _annotated;
    protected final transient Method _setter;

    protected MethodProperty(MethodProperty methodProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)methodProperty, jsonDeserializer);
        this._annotated = methodProperty._annotated;
        this._setter = methodProperty._setter;
    }

    protected MethodProperty(MethodProperty methodProperty, PropertyName propertyName) {
        super((SettableBeanProperty)methodProperty, propertyName);
        this._annotated = methodProperty._annotated;
        this._setter = methodProperty._setter;
    }

    protected MethodProperty(MethodProperty methodProperty, Method method) {
        super(methodProperty);
        this._annotated = methodProperty._annotated;
        this._setter = method;
    }

    public MethodProperty(BeanPropertyDefinition beanPropertyDefinition, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedMethod annotatedMethod) {
        super(beanPropertyDefinition, javaType, typeDeserializer, annotations);
        this._annotated = annotatedMethod;
        this._setter = annotatedMethod.getAnnotated();
    }

    @Override
    public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        this.set(object, this.deserialize(jsonParser, deserializationContext));
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        return this.setAndReturn(object, this.deserialize(jsonParser, deserializationContext));
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._annotated.getAnnotation(class_);
    }

    @Override
    public AnnotatedMember getMember() {
        return this._annotated;
    }

    Object readResolve() {
        return new MethodProperty(this, (Method)this._annotated.getAnnotated());
    }

    @Override
    public final void set(Object object, Object object2) throws IOException {
        try {
            this._setter.invoke(object, object2);
            return;
        }
        catch (Exception var1_2) {
            this._throwAsIOE(var1_2, object2);
            return;
        }
    }

    @Override
    public Object setAndReturn(Object object, Object object2) throws IOException {
        try {
            Object object3 = this._setter.invoke(object, object2);
            if (object3 == null) {
                return object;
            }
            return object3;
        }
        catch (Exception var1_2) {
            this._throwAsIOE(var1_2, object2);
            return null;
        }
    }

    @Override
    public MethodProperty withName(PropertyName propertyName) {
        return new MethodProperty(this, propertyName);
    }

    @Override
    public MethodProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new MethodProperty(this, jsonDeserializer);
    }
}

