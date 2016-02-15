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
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

public final class FieldProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final AnnotatedField _annotated;
    protected final transient Field _field;

    protected FieldProperty(FieldProperty fieldProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)fieldProperty, jsonDeserializer);
        this._annotated = fieldProperty._annotated;
        this._field = fieldProperty._field;
    }

    protected FieldProperty(FieldProperty fieldProperty, PropertyName propertyName) {
        super((SettableBeanProperty)fieldProperty, propertyName);
        this._annotated = fieldProperty._annotated;
        this._field = fieldProperty._field;
    }

    protected FieldProperty(FieldProperty fieldProperty, Field field) {
        super(fieldProperty);
        this._annotated = fieldProperty._annotated;
        if (field == null) {
            throw new IllegalArgumentException("No Field passed for property '" + fieldProperty.getName() + "' (class " + fieldProperty.getDeclaringClass().getName() + ")");
        }
        this._field = field;
    }

    public FieldProperty(BeanPropertyDefinition beanPropertyDefinition, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedField annotatedField) {
        super(beanPropertyDefinition, javaType, typeDeserializer, annotations);
        this._annotated = annotatedField;
        this._field = annotatedField.getAnnotated();
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
        return new FieldProperty(this, (Field)this._annotated.getAnnotated());
    }

    @Override
    public final void set(Object object, Object object2) throws IOException {
        try {
            this._field.set(object, object2);
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
            this._field.set(object, object2);
            return object;
        }
        catch (Exception var3_3) {
            this._throwAsIOE(var3_3, object2);
            return object;
        }
    }

    @Override
    public FieldProperty withName(PropertyName propertyName) {
        return new FieldProperty(this, propertyName);
    }

    @Override
    public FieldProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new FieldProperty(this, jsonDeserializer);
    }
}

