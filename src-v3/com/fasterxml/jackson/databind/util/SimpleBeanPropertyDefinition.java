package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

public class SimpleBeanPropertyDefinition extends BeanPropertyDefinition {
    protected final AnnotationIntrospector _introspector;
    protected final AnnotatedMember _member;
    protected final String _name;

    @Deprecated
    public SimpleBeanPropertyDefinition(AnnotatedMember member) {
        this(member, member.getName(), null);
    }

    @Deprecated
    public SimpleBeanPropertyDefinition(AnnotatedMember member, String name) {
        this(member, name, null);
    }

    private SimpleBeanPropertyDefinition(AnnotatedMember member, String name, AnnotationIntrospector intr) {
        this._introspector = intr;
        this._member = member;
        this._name = name;
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member) {
        return new SimpleBeanPropertyDefinition(member, member.getName(), config == null ? null : config.getAnnotationIntrospector());
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member, String name) {
        return new SimpleBeanPropertyDefinition(member, name, config == null ? null : config.getAnnotationIntrospector());
    }

    @Deprecated
    public SimpleBeanPropertyDefinition withName(String newName) {
        return withSimpleName(newName);
    }

    public SimpleBeanPropertyDefinition withSimpleName(String newName) {
        return this._name.equals(newName) ? this : new SimpleBeanPropertyDefinition(this._member, newName, this._introspector);
    }

    public SimpleBeanPropertyDefinition withName(PropertyName newName) {
        return withSimpleName(newName.getSimpleName());
    }

    public String getName() {
        return this._name;
    }

    public PropertyName getFullName() {
        return new PropertyName(this._name);
    }

    public String getInternalName() {
        return getName();
    }

    public PropertyName getWrapperName() {
        return this._introspector == null ? null : this._introspector.findWrapperName(this._member);
    }

    public boolean isExplicitlyIncluded() {
        return false;
    }

    public boolean isExplicitlyNamed() {
        return false;
    }

    public PropertyMetadata getMetadata() {
        return PropertyMetadata.STD_OPTIONAL;
    }

    public boolean hasGetter() {
        return getGetter() != null;
    }

    public boolean hasSetter() {
        return getSetter() != null;
    }

    public boolean hasField() {
        return this._member instanceof AnnotatedField;
    }

    public boolean hasConstructorParameter() {
        return this._member instanceof AnnotatedParameter;
    }

    public AnnotatedMethod getGetter() {
        if ((this._member instanceof AnnotatedMethod) && ((AnnotatedMethod) this._member).getParameterCount() == 0) {
            return (AnnotatedMethod) this._member;
        }
        return null;
    }

    public AnnotatedMethod getSetter() {
        if ((this._member instanceof AnnotatedMethod) && ((AnnotatedMethod) this._member).getParameterCount() == 1) {
            return (AnnotatedMethod) this._member;
        }
        return null;
    }

    public AnnotatedField getField() {
        return this._member instanceof AnnotatedField ? (AnnotatedField) this._member : null;
    }

    public AnnotatedParameter getConstructorParameter() {
        return this._member instanceof AnnotatedParameter ? (AnnotatedParameter) this._member : null;
    }

    public AnnotatedMember getAccessor() {
        AnnotatedMember acc = getGetter();
        if (acc == null) {
            return getField();
        }
        return acc;
    }

    public AnnotatedMember getMutator() {
        AnnotatedMember acc = getConstructorParameter();
        if (acc != null) {
            return acc;
        }
        acc = getSetter();
        if (acc == null) {
            return getField();
        }
        return acc;
    }

    public AnnotatedMember getNonConstructorMutator() {
        AnnotatedMember acc = getSetter();
        if (acc == null) {
            return getField();
        }
        return acc;
    }

    public AnnotatedMember getPrimaryMember() {
        return this._member;
    }
}
