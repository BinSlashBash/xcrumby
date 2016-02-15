/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.util.Named;

public abstract class BeanPropertyDefinition
implements Named {
    public boolean couldDeserialize() {
        if (this.getMutator() != null) {
            return true;
        }
        return false;
    }

    public boolean couldSerialize() {
        if (this.getAccessor() != null) {
            return true;
        }
        return false;
    }

    public ObjectIdInfo findObjectIdInfo() {
        return null;
    }

    public AnnotationIntrospector.ReferenceProperty findReferenceType() {
        return null;
    }

    public Class<?>[] findViews() {
        return null;
    }

    public abstract AnnotatedMember getAccessor();

    public abstract AnnotatedParameter getConstructorParameter();

    public abstract AnnotatedField getField();

    public abstract PropertyName getFullName();

    public abstract AnnotatedMethod getGetter();

    public abstract String getInternalName();

    public abstract PropertyMetadata getMetadata();

    public abstract AnnotatedMember getMutator();

    @Override
    public abstract String getName();

    public abstract AnnotatedMember getNonConstructorMutator();

    public AnnotatedMember getPrimaryMember() {
        return null;
    }

    public abstract AnnotatedMethod getSetter();

    public abstract PropertyName getWrapperName();

    public abstract boolean hasConstructorParameter();

    public abstract boolean hasField();

    public abstract boolean hasGetter();

    public abstract boolean hasSetter();

    public abstract boolean isExplicitlyIncluded();

    public boolean isExplicitlyNamed() {
        return this.isExplicitlyIncluded();
    }

    public final boolean isRequired() {
        PropertyMetadata propertyMetadata = this.getMetadata();
        if (propertyMetadata != null && propertyMetadata.isRequired()) {
            return true;
        }
        return false;
    }

    public boolean isTypeId() {
        return false;
    }

    public abstract BeanPropertyDefinition withName(PropertyName var1);

    @Deprecated
    public BeanPropertyDefinition withName(String string2) {
        return this.withSimpleName(string2);
    }

    public abstract BeanPropertyDefinition withSimpleName(String var1);
}

