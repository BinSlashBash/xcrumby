/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public final class AnnotationMap
implements Annotations {
    protected HashMap<Class<? extends Annotation>, Annotation> _annotations;

    public AnnotationMap() {
    }

    private AnnotationMap(HashMap<Class<? extends Annotation>, Annotation> hashMap) {
        this._annotations = hashMap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static AnnotationMap merge(AnnotationMap iterator, AnnotationMap hashMap) {
        if (iterator == null) return hashMap;
        if (iterator._annotations == null) return hashMap;
        if (iterator._annotations.isEmpty()) {
            return hashMap;
        }
        Object object = iterator;
        if (hashMap == null) return object;
        object = iterator;
        if (hashMap._annotations == null) return object;
        object = iterator;
        if (hashMap._annotations.isEmpty()) return object;
        object = new HashMap();
        for (Annotation annotation : hashMap._annotations.values()) {
            object.put(annotation.annotationType(), annotation);
        }
        iterator = iterator._annotations.values().iterator();
        while (iterator.hasNext()) {
            Annotation annotation2 = iterator.next();
            object.put(annotation2.annotationType(), annotation2);
        }
        return new AnnotationMap((HashMap<Class<? extends Annotation>, Annotation>)object);
    }

    protected final void _add(Annotation annotation) {
        if (this._annotations == null) {
            this._annotations = new HashMap();
        }
        this._annotations.put(annotation.annotationType(), annotation);
    }

    public void add(Annotation annotation) {
        this._add(annotation);
    }

    public void addIfNotPresent(Annotation annotation) {
        if (this._annotations == null || !this._annotations.containsKey(annotation.annotationType())) {
            this._add(annotation);
        }
    }

    public Iterable<Annotation> annotations() {
        if (this._annotations == null || this._annotations.size() == 0) {
            return Collections.emptyList();
        }
        return this._annotations.values();
    }

    @Override
    public <A extends Annotation> A get(Class<A> class_) {
        if (this._annotations == null) {
            return null;
        }
        return (A)this._annotations.get(class_);
    }

    @Override
    public int size() {
        if (this._annotations == null) {
            return 0;
        }
        return this._annotations.size();
    }

    public String toString() {
        if (this._annotations == null) {
            return "[null]";
        }
        return this._annotations.toString();
    }
}

