/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public final class AnnotatedParameter
extends AnnotatedMember {
    private static final long serialVersionUID = 1;
    protected final int _index;
    protected final AnnotatedWithParams _owner;
    protected final Type _type;

    public AnnotatedParameter(AnnotatedWithParams annotatedWithParams, Type type, AnnotationMap annotationMap, int n2) {
        super(annotationMap);
        this._owner = annotatedWithParams;
        this._type = type;
        this._index = n2;
    }

    @Override
    public AnnotatedElement getAnnotated() {
        return null;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        if (this._annotations == null) {
            return null;
        }
        return this._annotations.get(class_);
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._owner.getDeclaringClass();
    }

    @Override
    public Type getGenericType() {
        return this._type;
    }

    public int getIndex() {
        return this._index;
    }

    @Override
    public Member getMember() {
        return this._owner.getMember();
    }

    @Override
    public int getModifiers() {
        return this._owner.getModifiers();
    }

    @Override
    public String getName() {
        return "";
    }

    public AnnotatedWithParams getOwner() {
        return this._owner;
    }

    public Type getParameterType() {
        return this._type;
    }

    @Override
    public Class<?> getRawType() {
        if (this._type instanceof Class) {
            return (Class)this._type;
        }
        return TypeFactory.defaultInstance().constructType(this._type).getRawClass();
    }

    @Override
    public Object getValue(Object object) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call getValue() on constructor parameter of " + this.getDeclaringClass().getName());
    }

    @Override
    public void setValue(Object object, Object object2) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor parameter of " + this.getDeclaringClass().getName());
    }

    public String toString() {
        return "[parameter #" + this.getIndex() + ", annotations: " + this._annotations + "]";
    }

    @Override
    public AnnotatedParameter withAnnotations(AnnotationMap annotationMap) {
        if (annotationMap == this._annotations) {
            return this;
        }
        return this._owner.replaceParameterAnnotations(this._index, annotationMap);
    }
}

