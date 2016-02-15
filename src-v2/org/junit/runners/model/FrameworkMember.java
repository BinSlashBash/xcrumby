/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

public abstract class FrameworkMember<T extends FrameworkMember<T>> {
    abstract Annotation[] getAnnotations();

    public abstract String getName();

    public abstract Class<?> getType();

    public abstract boolean isPublic();

    boolean isShadowedBy(List<T> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.isShadowedBy((FrameworkMember)object.next())) continue;
            return true;
        }
        return false;
    }

    abstract boolean isShadowedBy(T var1);

    public abstract boolean isStatic();
}

