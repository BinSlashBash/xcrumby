/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class ReadableObjectId {
    private final ObjectIdGenerator.IdKey _key;
    private LinkedList<Referring> _referringProperties;
    private ObjectIdResolver _resolver;
    @Deprecated
    public final Object id;
    @Deprecated
    public Object item;

    public ReadableObjectId(ObjectIdGenerator.IdKey idKey) {
        this._key = idKey;
        this.id = idKey.key;
    }

    @Deprecated
    public ReadableObjectId(Object object) {
        this.id = object;
        this._key = null;
    }

    public void appendReferring(Referring referring) {
        if (this._referringProperties == null) {
            this._referringProperties = new LinkedList();
        }
        this._referringProperties.add(referring);
    }

    public void bindItem(Object object) throws IOException {
        this._resolver.bindItem(this._key, object);
        this.item = object;
        if (this._referringProperties != null) {
            Iterator<Referring> iterator = this._referringProperties.iterator();
            this._referringProperties = null;
            while (iterator.hasNext()) {
                iterator.next().handleResolvedForwardReference(this.id, object);
            }
        }
    }

    public ObjectIdGenerator.IdKey getKey() {
        return this._key;
    }

    public boolean hasReferringProperties() {
        if (this._referringProperties != null && !this._referringProperties.isEmpty()) {
            return true;
        }
        return false;
    }

    public Iterator<Referring> referringProperties() {
        if (this._referringProperties == null) {
            return Collections.emptyList().iterator();
        }
        return this._referringProperties.iterator();
    }

    public Object resolve() {
        Object object;
        this.item = object = this._resolver.resolveId(this._key);
        return object;
    }

    public void setResolver(ObjectIdResolver objectIdResolver) {
        this._resolver = objectIdResolver;
    }

    public static abstract class Referring {
        private final Class<?> _beanType;
        private final UnresolvedForwardReference _reference;

        public Referring(UnresolvedForwardReference unresolvedForwardReference, Class<?> class_) {
            this._reference = unresolvedForwardReference;
            this._beanType = class_;
        }

        public Class<?> getBeanType() {
            return this._beanType;
        }

        public JsonLocation getLocation() {
            return this._reference.getLocation();
        }

        public abstract void handleResolvedForwardReference(Object var1, Object var2) throws IOException;

        public boolean hasId(Object object) {
            return object.equals(this._reference.getUnresolvedId());
        }
    }

}

