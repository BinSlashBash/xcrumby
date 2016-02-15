/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

public class SimpleBeanPropertyDefinition
extends BeanPropertyDefinition {
    protected final AnnotationIntrospector _introspector;
    protected final AnnotatedMember _member;
    protected final String _name;

    @Deprecated
    public SimpleBeanPropertyDefinition(AnnotatedMember annotatedMember) {
        this(annotatedMember, annotatedMember.getName(), null);
    }

    @Deprecated
    public SimpleBeanPropertyDefinition(AnnotatedMember annotatedMember, String string2) {
        this(annotatedMember, string2, null);
    }

    private SimpleBeanPropertyDefinition(AnnotatedMember annotatedMember, String string2, AnnotationIntrospector annotationIntrospector) {
        this._introspector = annotationIntrospector;
        this._member = annotatedMember;
        this._name = string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember) {
        void var0_2;
        void var1_4;
        String string2 = var1_4.getName();
        if (mapperConfig == null) {
            return new SimpleBeanPropertyDefinition((AnnotatedMember)var1_4, string2, (AnnotationIntrospector)var0_2);
        }
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        return new SimpleBeanPropertyDefinition((AnnotatedMember)var1_4, string2, (AnnotationIntrospector)var0_2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, String string2) {
        void var2_5;
        void var0_2;
        void var1_4;
        if (mapperConfig == null) {
            return new SimpleBeanPropertyDefinition((AnnotatedMember)var1_4, (String)var2_5, (AnnotationIntrospector)var0_2);
        }
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        return new SimpleBeanPropertyDefinition((AnnotatedMember)var1_4, (String)var2_5, (AnnotationIntrospector)var0_2);
    }

    @Override
    public AnnotatedMember getAccessor() {
        AnnotatedMethod annotatedMethod;
        AnnotatedMember annotatedMember = annotatedMethod = this.getGetter();
        if (annotatedMethod == null) {
            annotatedMember = this.getField();
        }
        return annotatedMember;
    }

    @Override
    public AnnotatedParameter getConstructorParameter() {
        if (this._member instanceof AnnotatedParameter) {
            return (AnnotatedParameter)this._member;
        }
        return null;
    }

    @Override
    public AnnotatedField getField() {
        if (this._member instanceof AnnotatedField) {
            return (AnnotatedField)this._member;
        }
        return null;
    }

    @Override
    public PropertyName getFullName() {
        return new PropertyName(this._name);
    }

    @Override
    public AnnotatedMethod getGetter() {
        if (this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 0) {
            return (AnnotatedMethod)this._member;
        }
        return null;
    }

    @Override
    public String getInternalName() {
        return this.getName();
    }

    @Override
    public PropertyMetadata getMetadata() {
        return PropertyMetadata.STD_OPTIONAL;
    }

    @Override
    public AnnotatedMember getMutator() {
        AnnotatedMember annotatedMember;
        AnnotatedMember annotatedMember2 = annotatedMember = this.getConstructorParameter();
        if (annotatedMember == null) {
            annotatedMember2 = annotatedMember = this.getSetter();
            if (annotatedMember == null) {
                annotatedMember2 = this.getField();
            }
        }
        return annotatedMember2;
    }

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public AnnotatedMember getNonConstructorMutator() {
        AnnotatedMethod annotatedMethod;
        AnnotatedMember annotatedMember = annotatedMethod = this.getSetter();
        if (annotatedMethod == null) {
            annotatedMember = this.getField();
        }
        return annotatedMember;
    }

    @Override
    public AnnotatedMember getPrimaryMember() {
        return this._member;
    }

    @Override
    public AnnotatedMethod getSetter() {
        if (this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 1) {
            return (AnnotatedMethod)this._member;
        }
        return null;
    }

    @Override
    public PropertyName getWrapperName() {
        if (this._introspector == null) {
            return null;
        }
        return this._introspector.findWrapperName(this._member);
    }

    @Override
    public boolean hasConstructorParameter() {
        return this._member instanceof AnnotatedParameter;
    }

    @Override
    public boolean hasField() {
        return this._member instanceof AnnotatedField;
    }

    @Override
    public boolean hasGetter() {
        if (this.getGetter() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasSetter() {
        if (this.getSetter() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExplicitlyIncluded() {
        return false;
    }

    @Override
    public boolean isExplicitlyNamed() {
        return false;
    }

    @Override
    public SimpleBeanPropertyDefinition withName(PropertyName propertyName) {
        return this.withSimpleName(propertyName.getSimpleName());
    }

    @Deprecated
    @Override
    public SimpleBeanPropertyDefinition withName(String string2) {
        return this.withSimpleName(string2);
    }

    @Override
    public SimpleBeanPropertyDefinition withSimpleName(String string2) {
        if (this._name.equals(string2)) {
            return this;
        }
        return new SimpleBeanPropertyDefinition(this._member, string2, this._introspector);
    }
}

