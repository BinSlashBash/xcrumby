/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.internal.$Gson$Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public final class FieldAttributes {
    private final Field field;

    public FieldAttributes(Field field) {
        $Gson$Preconditions.checkNotNull(field);
        this.field = field;
    }

    Object get(Object object) throws IllegalAccessException {
        return this.field.get(object);
    }

    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        return this.field.getAnnotation(class_);
    }

    public Collection<Annotation> getAnnotations() {
        return Arrays.asList(this.field.getAnnotations());
    }

    public Class<?> getDeclaredClass() {
        return this.field.getType();
    }

    public Type getDeclaredType() {
        return this.field.getGenericType();
    }

    public Class<?> getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    public String getName() {
        return this.field.getName();
    }

    public boolean hasModifier(int n2) {
        if ((this.field.getModifiers() & n2) != 0) {
            return true;
        }
        return false;
    }

    boolean isSynthetic() {
        return this.field.isSynthetic();
    }
}

