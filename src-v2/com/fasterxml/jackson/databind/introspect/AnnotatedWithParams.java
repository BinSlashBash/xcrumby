/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public abstract class AnnotatedWithParams
extends AnnotatedMember {
    private static final long serialVersionUID = 1;
    protected final AnnotationMap[] _paramAnnotations;

    protected AnnotatedWithParams(AnnotationMap annotationMap, AnnotationMap[] arrannotationMap) {
        super(annotationMap);
        this._paramAnnotations = arrannotationMap;
    }

    public final void addOrOverrideParam(int n2, Annotation annotation) {
        AnnotationMap annotationMap;
        AnnotationMap annotationMap2 = annotationMap = this._paramAnnotations[n2];
        if (annotationMap == null) {
            this._paramAnnotations[n2] = annotationMap2 = new AnnotationMap();
        }
        annotationMap2.add(annotation);
    }

    public abstract Object call() throws Exception;

    public abstract Object call(Object[] var1) throws Exception;

    public abstract Object call1(Object var1) throws Exception;

    @Override
    public final <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._annotations.get(class_);
    }

    public final int getAnnotationCount() {
        return this._annotations.size();
    }

    public abstract Type getGenericParameterType(int var1);

    public final AnnotatedParameter getParameter(int n2) {
        return new AnnotatedParameter(this, this.getGenericParameterType(n2), this.getParameterAnnotations(n2), n2);
    }

    public final AnnotationMap getParameterAnnotations(int n2) {
        if (this._paramAnnotations != null && n2 >= 0 && n2 <= this._paramAnnotations.length) {
            return this._paramAnnotations[n2];
        }
        return null;
    }

    public abstract int getParameterCount();

    public abstract Class<?> getRawParameterType(int var1);

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType getType(TypeBindings type, TypeVariable<?>[] arrtypeVariable) {
        TypeVariable<?> typeVariable = type;
        if (arrtypeVariable != null) {
            typeVariable = type;
            if (arrtypeVariable.length > 0) {
                TypeBindings typeBindings = type.childInstance();
                int n2 = arrtypeVariable.length;
                int n3 = 0;
                do {
                    typeVariable = typeBindings;
                    if (n3 >= n2) break;
                    typeVariable = arrtypeVariable[n3];
                    typeBindings._addPlaceholder(typeVariable.getName());
                    type = typeVariable.getBounds()[0];
                    type = type == null ? TypeFactory.unknownType() : typeBindings.resolveType(type);
                    typeBindings.addBinding(typeVariable.getName(), (JavaType)type);
                    ++n3;
                } while (true);
            }
        }
        return typeVariable.resolveType(this.getGenericType());
    }

    protected AnnotatedParameter replaceParameterAnnotations(int n2, AnnotationMap annotationMap) {
        this._paramAnnotations[n2] = annotationMap;
        return this.getParameter(n2);
    }

    public final JavaType resolveParameterType(int n2, TypeBindings typeBindings) {
        return typeBindings.resolveType(this.getGenericParameterType(n2));
    }
}

