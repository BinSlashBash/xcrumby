/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class AnnotatedConstructor
extends AnnotatedWithParams {
    private static final long serialVersionUID = 1;
    protected final Constructor<?> _constructor;
    protected Serialization _serialization;

    protected AnnotatedConstructor(Serialization serialization) {
        super(null, null);
        this._constructor = null;
        this._serialization = serialization;
    }

    public AnnotatedConstructor(Constructor<?> constructor, AnnotationMap annotationMap, AnnotationMap[] arrannotationMap) {
        super(annotationMap, arrannotationMap);
        if (constructor == null) {
            throw new IllegalArgumentException("Null constructor not allowed");
        }
        this._constructor = constructor;
    }

    @Override
    public final Object call() throws Exception {
        return this._constructor.newInstance(new Object[0]);
    }

    @Override
    public final Object call(Object[] arrobject) throws Exception {
        return this._constructor.newInstance(arrobject);
    }

    @Override
    public final Object call1(Object object) throws Exception {
        return this._constructor.newInstance(object);
    }

    @Override
    public Constructor<?> getAnnotated() {
        return this._constructor;
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._constructor.getDeclaringClass();
    }

    @Override
    public Type getGenericParameterType(int n2) {
        Type[] arrtype = this._constructor.getGenericParameterTypes();
        if (n2 >= arrtype.length) {
            return null;
        }
        return arrtype[n2];
    }

    @Override
    public Type getGenericType() {
        return this.getRawType();
    }

    @Override
    public Member getMember() {
        return this._constructor;
    }

    @Override
    public int getModifiers() {
        return this._constructor.getModifiers();
    }

    @Override
    public String getName() {
        return this._constructor.getName();
    }

    @Override
    public int getParameterCount() {
        return this._constructor.getParameterTypes().length;
    }

    @Override
    public Class<?> getRawParameterType(int n2) {
        Class<?>[] arrclass = this._constructor.getParameterTypes();
        if (n2 >= arrclass.length) {
            return null;
        }
        return arrclass[n2];
    }

    @Override
    public Class<?> getRawType() {
        return this._constructor.getDeclaringClass();
    }

    @Override
    public JavaType getType(TypeBindings typeBindings) {
        return this.getType(typeBindings, this._constructor.getTypeParameters());
    }

    @Override
    public Object getValue(Object object) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call getValue() on constructor of " + this.getDeclaringClass().getName());
    }

    Object readResolve() {
        Class class_ = this._serialization.clazz;
        try {
            Object object = class_.getDeclaredConstructor(this._serialization.args);
            if (!object.isAccessible()) {
                ClassUtil.checkAndFixAccess(object);
            }
            object = new AnnotatedConstructor(object, null, null);
            return object;
        }
        catch (Exception var2_3) {
            throw new IllegalArgumentException("Could not find constructor with " + this._serialization.args.length + " args from Class '" + class_.getName());
        }
    }

    @Override
    public void setValue(Object object, Object object2) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor of " + this.getDeclaringClass().getName());
    }

    public String toString() {
        return "[constructor for " + this.getName() + ", annotations: " + this._annotations + "]";
    }

    @Override
    public AnnotatedConstructor withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedConstructor(this._constructor, annotationMap, this._paramAnnotations);
    }

    Object writeReplace() {
        return new AnnotatedConstructor(new Serialization(this._constructor));
    }

    private static final class Serialization
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected Class<?>[] args;
        protected Class<?> clazz;

        public Serialization(Constructor<?> constructor) {
            this.clazz = constructor.getDeclaringClass();
            this.args = constructor.getParameterTypes();
        }
    }

}

