/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer;

public class AsExternalTypeDeserializer
extends AsArrayTypeDeserializer {
    private static final long serialVersionUID = 1;

    public AsExternalTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, String string2, boolean bl2, Class<?> class_) {
        super(javaType, typeIdResolver, string2, bl2, class_);
    }

    public AsExternalTypeDeserializer(AsExternalTypeDeserializer asExternalTypeDeserializer, BeanProperty beanProperty) {
        super(asExternalTypeDeserializer, beanProperty);
    }

    @Override
    public TypeDeserializer forProperty(BeanProperty beanProperty) {
        if (beanProperty == this._property) {
            return this;
        }
        return new AsExternalTypeDeserializer(this, beanProperty);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.EXTERNAL_PROPERTY;
    }
}

