/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.type.TypeBindings;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public abstract class Annotated {
    protected Annotated() {
    }

    public abstract Iterable<Annotation> annotations();

    protected abstract AnnotationMap getAllAnnotations();

    public abstract AnnotatedElement getAnnotated();

    public abstract <A extends Annotation> A getAnnotation(Class<A> var1);

    public abstract Type getGenericType();

    protected abstract int getModifiers();

    public abstract String getName();

    public abstract Class<?> getRawType();

    public JavaType getType(TypeBindings typeBindings) {
        return typeBindings.resolveType(this.getGenericType());
    }

    public final <A extends Annotation> boolean hasAnnotation(Class<A> class_) {
        if (this.getAnnotation(class_) != null) {
            return true;
        }
        return false;
    }

    public final boolean isPublic() {
        return Modifier.isPublic(this.getModifiers());
    }

    public abstract Annotated withAnnotations(AnnotationMap var1);

    public final Annotated withFallBackAnnotationsFrom(Annotated annotated) {
        return this.withAnnotations(AnnotationMap.merge(this.getAllAnnotations(), annotated.getAllAnnotations()));
    }
}

