/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

public final class ManagedReferenceProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 1;
    protected final SettableBeanProperty _backProperty;
    protected final boolean _isContainer;
    protected final SettableBeanProperty _managedProperty;
    protected final String _referenceName;

    public ManagedReferenceProperty(SettableBeanProperty settableBeanProperty, String string2, SettableBeanProperty settableBeanProperty2, Annotations annotations, boolean bl2) {
        super(settableBeanProperty.getFullName(), settableBeanProperty.getType(), settableBeanProperty.getWrapperName(), settableBeanProperty.getValueTypeDeserializer(), annotations, settableBeanProperty.getMetadata());
        this._referenceName = string2;
        this._managedProperty = settableBeanProperty;
        this._backProperty = settableBeanProperty2;
        this._isContainer = bl2;
    }

    protected ManagedReferenceProperty(ManagedReferenceProperty managedReferenceProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)managedReferenceProperty, jsonDeserializer);
        this._referenceName = managedReferenceProperty._referenceName;
        this._isContainer = managedReferenceProperty._isContainer;
        this._managedProperty = managedReferenceProperty._managedProperty;
        this._backProperty = managedReferenceProperty._backProperty;
    }

    protected ManagedReferenceProperty(ManagedReferenceProperty managedReferenceProperty, PropertyName propertyName) {
        super((SettableBeanProperty)managedReferenceProperty, propertyName);
        this._referenceName = managedReferenceProperty._referenceName;
        this._isContainer = managedReferenceProperty._isContainer;
        this._managedProperty = managedReferenceProperty._managedProperty;
        this._backProperty = managedReferenceProperty._backProperty;
    }

    @Override
    public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        this.set(object, this._managedProperty.deserialize(jsonParser, deserializationContext));
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        return this.setAndReturn(object, this.deserialize(jsonParser, deserializationContext));
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._managedProperty.getAnnotation(class_);
    }

    @Override
    public AnnotatedMember getMember() {
        return this._managedProperty.getMember();
    }

    @Override
    public final void set(Object object, Object object2) throws IOException {
        this.setAndReturn(object, object2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object setAndReturn(Object object, Object object2) throws IOException {
        if (object2 == null) return this._managedProperty.setAndReturn(object, object2);
        if (this._isContainer) {
            if (object2 instanceof Object[]) {
                Object[] arrobject = (Object[])object2;
                int n2 = arrobject.length;
                int n3 = 0;
                while (n3 < n2) {
                    Object object3 = arrobject[n3];
                    if (object3 != null) {
                        this._backProperty.set(object3, object);
                    }
                    ++n3;
                }
                return this._managedProperty.setAndReturn(object, object2);
            }
            if (object2 instanceof Collection) {
                for (Object e2 : (Collection)object2) {
                    if (e2 == null) continue;
                    this._backProperty.set(e2, object);
                }
                return this._managedProperty.setAndReturn(object, object2);
            } else {
                if (!(object2 instanceof Map)) throw new IllegalStateException("Unsupported container type (" + object2.getClass().getName() + ") when resolving reference '" + this._referenceName + "'");
                for (Object v2 : ((Map)object2).values()) {
                    if (v2 == null) continue;
                    this._backProperty.set(v2, object);
                }
            }
            return this._managedProperty.setAndReturn(object, object2);
        } else {
            this._backProperty.set(object2, object);
        }
        return this._managedProperty.setAndReturn(object, object2);
    }

    @Override
    public ManagedReferenceProperty withName(PropertyName propertyName) {
        return new ManagedReferenceProperty(this, propertyName);
    }

    @Override
    public ManagedReferenceProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new ManagedReferenceProperty(this, jsonDeserializer);
    }
}

