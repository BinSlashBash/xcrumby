/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.runners.model.FrameworkMember;

public class FrameworkField
extends FrameworkMember<FrameworkField> {
    private final Field fField;

    FrameworkField(Field field) {
        this.fField = field;
    }

    public Object get(Object object) throws IllegalArgumentException, IllegalAccessException {
        return this.fField.get(object);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.fField.getAnnotations();
    }

    public Field getField() {
        return this.fField;
    }

    @Override
    public String getName() {
        return this.getField().getName();
    }

    @Override
    public Class<?> getType() {
        return this.fField.getType();
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(this.fField.getModifiers());
    }

    @Override
    public boolean isShadowedBy(FrameworkField frameworkField) {
        return frameworkField.getName().equals(this.getName());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(this.fField.getModifiers());
    }
}

