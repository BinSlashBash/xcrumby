/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
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

public final class SetterlessProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final AnnotatedMethod _annotated;
    protected final Method _getter;

    protected SetterlessProperty(SetterlessProperty setterlessProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)setterlessProperty, jsonDeserializer);
        this._annotated = setterlessProperty._annotated;
        this._getter = setterlessProperty._getter;
    }

    protected SetterlessProperty(SetterlessProperty setterlessProperty, PropertyName propertyName) {
        super((SettableBeanProperty)setterlessProperty, propertyName);
        this._annotated = setterlessProperty._annotated;
        this._getter = setterlessProperty._getter;
    }

    public SetterlessProperty(BeanPropertyDefinition beanPropertyDefinition, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedMethod annotatedMethod) {
        super(beanPropertyDefinition, javaType, typeDeserializer, annotations);
        this._annotated = annotatedMethod;
        this._getter = annotatedMethod.getAnnotated();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void deserializeAndSet(JsonParser var1_1, DeserializationContext var2_3, Object var3_4) throws IOException, JsonProcessingException {
        if (var1_1.getCurrentToken() == JsonToken.VALUE_NULL) {
            return;
        }
        try {
            var3_4 = this._getter.invoke(var3_4, new Object[0]);
            ** if (var3_4 != null) goto lbl-1000
        }
        catch (Exception var1_2) {
            this._throwAsIOE(var1_2);
            return;
        }
lbl-1000: // 1 sources:
        {
            throw new JsonMappingException("Problem deserializing 'setterless' property '" + this.getName() + "': get method returned null");
        }
lbl-1000: // 1 sources:
        {
        }
        this._valueDeserializer.deserialize(var1_1, var2_3, var3_4);
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        this.deserializeAndSet(jsonParser, deserializationContext, object);
        return object;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._annotated.getAnnotation(class_);
    }

    @Override
    public AnnotatedMember getMember() {
        return this._annotated;
    }

    @Override
    public final void set(Object object, Object object2) throws IOException {
        throw new UnsupportedOperationException("Should never call 'set' on setterless property");
    }

    @Override
    public Object setAndReturn(Object object, Object object2) throws IOException {
        this.set(object, object2);
        return null;
    }

    @Override
    public SetterlessProperty withName(PropertyName propertyName) {
        return new SetterlessProperty(this, propertyName);
    }

    @Override
    public SetterlessProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new SetterlessProperty(this, jsonDeserializer);
    }
}

