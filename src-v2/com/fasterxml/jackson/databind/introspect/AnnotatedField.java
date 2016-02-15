/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public final class AnnotatedField
extends AnnotatedMember
implements Serializable {
    private static final long serialVersionUID = 7364428299211355871L;
    protected final transient Field _field;
    protected Serialization _serialization;

    protected AnnotatedField(Serialization serialization) {
        super(null);
        this._field = null;
        this._serialization = serialization;
    }

    public AnnotatedField(Field field, AnnotationMap annotationMap) {
        super(annotationMap);
        this._field = field;
    }

    @Override
    public Field getAnnotated() {
        return this._field;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        if (this._annotations == null) {
            return null;
        }
        return this._annotations.get(class_);
    }

    public int getAnnotationCount() {
        return this._annotations.size();
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._field.getDeclaringClass();
    }

    public String getFullName() {
        return this.getDeclaringClass().getName() + "#" + this.getName();
    }

    @Override
    public Type getGenericType() {
        return this._field.getGenericType();
    }

    @Override
    public Member getMember() {
        return this._field;
    }

    @Override
    public int getModifiers() {
        return this._field.getModifiers();
    }

    @Override
    public String getName() {
        return this._field.getName();
    }

    @Override
    public Class<?> getRawType() {
        return this._field.getType();
    }

    @Override
    public Object getValue(Object object) throws IllegalArgumentException {
        try {
            object = this._field.get(object);
            return object;
        }
        catch (IllegalAccessException var1_2) {
            throw new IllegalArgumentException("Failed to getValue() for field " + this.getFullName() + ": " + var1_2.getMessage(), var1_2);
        }
    }

    Object readResolve() {
        Class class_ = this._serialization.clazz;
        try {
            Object object = class_.getDeclaredField(this._serialization.name);
            if (!object.isAccessible()) {
                ClassUtil.checkAndFixAccess((Member)object);
            }
            object = new AnnotatedField((Field)object, null);
            return object;
        }
        catch (Exception var2_3) {
            throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + class_.getName());
        }
    }

    @Override
    public void setValue(Object object, Object object2) throws IllegalArgumentException {
        try {
            this._field.set(object, object2);
            return;
        }
        catch (IllegalAccessException var1_2) {
            throw new IllegalArgumentException("Failed to setValue() for field " + this.getFullName() + ": " + var1_2.getMessage(), var1_2);
        }
    }

    public String toString() {
        return "[field " + this.getFullName() + "]";
    }

    @Override
    public AnnotatedField withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedField(this._field, annotationMap);
    }

    Object writeReplace() {
        return new AnnotatedField(new Serialization(this._field));
    }

    private static final class Serialization
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected Class<?> clazz;
        protected String name;

        public Serialization(Field field) {
            this.clazz = field.getDeclaringClass();
            this.name = field.getName();
        }
    }

}

