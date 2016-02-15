/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class ObjectIdReferenceProperty
extends SettableBeanProperty {
    private static final long serialVersionUID = 8465266677345565407L;
    private SettableBeanProperty _forward;

    public ObjectIdReferenceProperty(SettableBeanProperty settableBeanProperty, ObjectIdInfo objectIdInfo) {
        super(settableBeanProperty);
        this._forward = settableBeanProperty;
        this._objectIdInfo = objectIdInfo;
    }

    public ObjectIdReferenceProperty(ObjectIdReferenceProperty objectIdReferenceProperty, JsonDeserializer<?> jsonDeserializer) {
        super((SettableBeanProperty)objectIdReferenceProperty, jsonDeserializer);
        this._forward = objectIdReferenceProperty._forward;
        this._objectIdInfo = objectIdReferenceProperty._objectIdInfo;
    }

    public ObjectIdReferenceProperty(ObjectIdReferenceProperty objectIdReferenceProperty, PropertyName propertyName) {
        super((SettableBeanProperty)objectIdReferenceProperty, propertyName);
        this._forward = objectIdReferenceProperty._forward;
        this._objectIdInfo = objectIdReferenceProperty._objectIdInfo;
    }

    @Override
    public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        this.deserializeSetAndReturn(jsonParser, deserializationContext, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deserializeSetAndReturn(JsonParser jsonParser, DeserializationContext object, Object object2) throws IOException, JsonProcessingException {
        boolean bl2 = this._objectIdInfo != null || this._valueDeserializer.getObjectIdReader() != null;
        try {
            return this.setAndReturn(object2, this.deserialize(jsonParser, (DeserializationContext)object));
        }
        catch (UnresolvedForwardReference var2_3) {
            if (!bl2) {
                throw JsonMappingException.from(jsonParser, "Unresolved forward reference but no identity info.", var2_3);
            }
            var2_3.getRoid().appendReferring(new PropertyReferring(this, var2_3, this._type.getRawClass(), object2));
            return null;
        }
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._forward.getAnnotation(class_);
    }

    @Override
    public AnnotatedMember getMember() {
        return this._forward.getMember();
    }

    @Override
    public void set(Object object, Object object2) throws IOException {
        this._forward.set(object, object2);
    }

    @Override
    public Object setAndReturn(Object object, Object object2) throws IOException {
        return this._forward.setAndReturn(object, object2);
    }

    @Override
    public SettableBeanProperty withName(PropertyName propertyName) {
        return new ObjectIdReferenceProperty(this, propertyName);
    }

    @Override
    public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> jsonDeserializer) {
        return new ObjectIdReferenceProperty(this, jsonDeserializer);
    }

    public static final class PropertyReferring
    extends ReadableObjectId.Referring {
        private final ObjectIdReferenceProperty _parent;
        public final Object _pojo;

        public PropertyReferring(ObjectIdReferenceProperty objectIdReferenceProperty, UnresolvedForwardReference unresolvedForwardReference, Class<?> class_, Object object) {
            super(unresolvedForwardReference, class_);
            this._parent = objectIdReferenceProperty;
            this._pojo = object;
        }

        @Override
        public void handleResolvedForwardReference(Object object, Object object2) throws IOException {
            if (!this.hasId(object)) {
                throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + object + "] that wasn't previously seen as unresolved.");
            }
            this._parent.set(this._pojo, object2);
        }
    }

}

