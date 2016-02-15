package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class ObjectIdReferenceProperty extends SettableBeanProperty {
    private static final long serialVersionUID = 8465266677345565407L;
    private SettableBeanProperty _forward;

    public static final class PropertyReferring extends Referring {
        private final ObjectIdReferenceProperty _parent;
        public final Object _pojo;

        public PropertyReferring(ObjectIdReferenceProperty parent, UnresolvedForwardReference ref, Class<?> type, Object ob) {
            super(ref, type);
            this._parent = parent;
            this._pojo = ob;
        }

        public void handleResolvedForwardReference(Object id, Object value) throws IOException {
            if (hasId(id)) {
                this._parent.set(this._pojo, value);
                return;
            }
            throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + id + "] that wasn't previously seen as unresolved.");
        }
    }

    public ObjectIdReferenceProperty(SettableBeanProperty forward, ObjectIdInfo objectIdInfo) {
        super(forward);
        this._forward = forward;
        this._objectIdInfo = objectIdInfo;
    }

    public ObjectIdReferenceProperty(ObjectIdReferenceProperty src, JsonDeserializer<?> deser) {
        super((SettableBeanProperty) src, (JsonDeserializer) deser);
        this._forward = src._forward;
        this._objectIdInfo = src._objectIdInfo;
    }

    public ObjectIdReferenceProperty(ObjectIdReferenceProperty src, PropertyName newName) {
        super((SettableBeanProperty) src, newName);
        this._forward = src._forward;
        this._objectIdInfo = src._objectIdInfo;
    }

    public SettableBeanProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new ObjectIdReferenceProperty(this, (JsonDeserializer) deser);
    }

    public SettableBeanProperty withName(PropertyName newName) {
        return new ObjectIdReferenceProperty(this, newName);
    }

    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return this._forward.getAnnotation(acls);
    }

    public AnnotatedMember getMember() {
        return this._forward.getMember();
    }

    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException, JsonProcessingException {
        deserializeSetAndReturn(jp, ctxt, instance);
    }

    public Object deserializeSetAndReturn(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException, JsonProcessingException {
        boolean usingIdentityInfo = (this._objectIdInfo == null && this._valueDeserializer.getObjectIdReader() == null) ? false : true;
        try {
            return setAndReturn(instance, deserialize(jp, ctxt));
        } catch (UnresolvedForwardReference reference) {
            if (usingIdentityInfo) {
                reference.getRoid().appendReferring(new PropertyReferring(this, reference, this._type.getRawClass(), instance));
                return null;
            }
            throw JsonMappingException.from(jp, "Unresolved forward reference but no identity info.", reference);
        }
    }

    public void set(Object instance, Object value) throws IOException {
        this._forward.set(instance, value);
    }

    public Object setAndReturn(Object instance, Object value) throws IOException {
        return this._forward.setAndReturn(instance, value);
    }
}
