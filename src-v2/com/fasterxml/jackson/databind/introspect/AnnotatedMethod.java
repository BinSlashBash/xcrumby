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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class AnnotatedMethod
extends AnnotatedWithParams
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final transient Method _method;
    protected Class<?>[] _paramClasses;
    protected Serialization _serialization;

    protected AnnotatedMethod(Serialization serialization) {
        super(null, null);
        this._method = null;
        this._serialization = serialization;
    }

    public AnnotatedMethod(Method method, AnnotationMap annotationMap, AnnotationMap[] arrannotationMap) {
        super(annotationMap, arrannotationMap);
        if (method == null) {
            throw new IllegalArgumentException("Can not construct AnnotatedMethod with null Method");
        }
        this._method = method;
    }

    @Override
    public final Object call() throws Exception {
        return this._method.invoke(null, new Object[0]);
    }

    @Override
    public final Object call(Object[] arrobject) throws Exception {
        return this._method.invoke(null, arrobject);
    }

    @Override
    public final Object call1(Object object) throws Exception {
        return this._method.invoke(null, object);
    }

    @Override
    public Method getAnnotated() {
        return this._method;
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._method.getDeclaringClass();
    }

    public String getFullName() {
        return this.getDeclaringClass().getName() + "#" + this.getName() + "(" + this.getParameterCount() + " params)";
    }

    @Override
    public Type getGenericParameterType(int n2) {
        Type[] arrtype = this._method.getGenericParameterTypes();
        if (n2 >= arrtype.length) {
            return null;
        }
        return arrtype[n2];
    }

    public Type[] getGenericParameterTypes() {
        return this._method.getGenericParameterTypes();
    }

    public Type getGenericReturnType() {
        return this._method.getGenericReturnType();
    }

    @Override
    public Type getGenericType() {
        return this._method.getGenericReturnType();
    }

    @Override
    public Method getMember() {
        return this._method;
    }

    @Override
    public int getModifiers() {
        return this._method.getModifiers();
    }

    @Override
    public String getName() {
        return this._method.getName();
    }

    @Override
    public int getParameterCount() {
        return this.getRawParameterTypes().length;
    }

    @Override
    public Class<?> getRawParameterType(int n2) {
        Class<?>[] arrclass = this.getRawParameterTypes();
        if (n2 >= arrclass.length) {
            return null;
        }
        return arrclass[n2];
    }

    public Class<?>[] getRawParameterTypes() {
        if (this._paramClasses == null) {
            this._paramClasses = this._method.getParameterTypes();
        }
        return this._paramClasses;
    }

    public Class<?> getRawReturnType() {
        return this._method.getReturnType();
    }

    @Override
    public Class<?> getRawType() {
        return this._method.getReturnType();
    }

    @Override
    public JavaType getType(TypeBindings typeBindings) {
        return this.getType(typeBindings, this._method.getTypeParameters());
    }

    @Override
    public Object getValue(Object object) throws IllegalArgumentException {
        try {
            object = this._method.invoke(object, new Object[0]);
            return object;
        }
        catch (IllegalAccessException var1_2) {
            throw new IllegalArgumentException("Failed to getValue() with method " + this.getFullName() + ": " + var1_2.getMessage(), var1_2);
        }
        catch (InvocationTargetException var1_3) {
            throw new IllegalArgumentException("Failed to getValue() with method " + this.getFullName() + ": " + var1_3.getMessage(), var1_3);
        }
    }

    public boolean hasReturnType() {
        Class class_ = this.getRawReturnType();
        if (class_ != Void.TYPE && class_ != Void.class) {
            return true;
        }
        return false;
    }

    Object readResolve() {
        Class class_ = this._serialization.clazz;
        try {
            Object object = class_.getDeclaredMethod(this._serialization.name, this._serialization.args);
            if (!object.isAccessible()) {
                ClassUtil.checkAndFixAccess((Member)object);
            }
            object = new AnnotatedMethod((Method)object, null, null);
            return object;
        }
        catch (Exception var2_3) {
            throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + class_.getName());
        }
    }

    @Override
    public void setValue(Object object, Object object2) throws IllegalArgumentException {
        try {
            this._method.invoke(object, object2);
            return;
        }
        catch (IllegalAccessException var1_2) {
            throw new IllegalArgumentException("Failed to setValue() with method " + this.getFullName() + ": " + var1_2.getMessage(), var1_2);
        }
        catch (InvocationTargetException var1_3) {
            throw new IllegalArgumentException("Failed to setValue() with method " + this.getFullName() + ": " + var1_3.getMessage(), var1_3);
        }
    }

    public String toString() {
        return "[method " + this.getFullName() + "]";
    }

    @Override
    public AnnotatedMethod withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedMethod(this._method, annotationMap, this._paramAnnotations);
    }

    public AnnotatedMethod withMethod(Method method) {
        return new AnnotatedMethod(method, this._annotations, this._paramAnnotations);
    }

    Object writeReplace() {
        return new AnnotatedMethod(new Serialization(this._method));
    }

    private static final class Serialization
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected Class<?>[] args;
        protected Class<?> clazz;
        protected String name;

        public Serialization(Method method) {
            this.clazz = method.getDeclaringClass();
            this.name = method.getName();
            this.args = method.getParameterTypes();
        }
    }

}

