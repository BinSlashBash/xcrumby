/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;

public class ValueInjector
extends BeanProperty.Std {
    protected final Object _valueId;

    public ValueInjector(PropertyName propertyName, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object object) {
        super(propertyName, javaType, null, annotations, annotatedMember, PropertyMetadata.STD_OPTIONAL);
        this._valueId = object;
    }

    @Deprecated
    public ValueInjector(String string2, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object object) {
        this(new PropertyName(string2), javaType, annotations, annotatedMember, object);
    }

    public Object findValue(DeserializationContext deserializationContext, Object object) {
        return deserializationContext.findInjectableValue(this._valueId, this, object);
    }

    public void inject(DeserializationContext deserializationContext, Object object) throws IOException {
        this._member.setValue(object, this.findValue(deserializationContext, object));
    }
}

