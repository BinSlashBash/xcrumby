/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.Collections;

public abstract class AnnotatedMember
extends Annotated
implements Serializable {
    private static final long serialVersionUID = 7364428299211355871L;
    protected final transient AnnotationMap _annotations;

    protected AnnotatedMember(AnnotationMap annotationMap) {
        this._annotations = annotationMap;
    }

    public final void addIfNotPresent(Annotation annotation) {
        this._annotations.addIfNotPresent(annotation);
    }

    public final void addOrOverride(Annotation annotation) {
        this._annotations.add(annotation);
    }

    @Override
    public Iterable<Annotation> annotations() {
        if (this._annotations == null) {
            return Collections.emptyList();
        }
        return this._annotations.annotations();
    }

    public final void fixAccess() {
        ClassUtil.checkAndFixAccess(this.getMember());
    }

    @Override
    protected AnnotationMap getAllAnnotations() {
        return this._annotations;
    }

    public abstract Class<?> getDeclaringClass();

    public abstract Member getMember();

    public abstract Object getValue(Object var1) throws UnsupportedOperationException, IllegalArgumentException;

    public abstract void setValue(Object var1, Object var2) throws UnsupportedOperationException, IllegalArgumentException;
}

