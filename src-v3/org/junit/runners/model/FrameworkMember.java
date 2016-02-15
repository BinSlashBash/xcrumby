package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.util.List;

public abstract class FrameworkMember<T extends FrameworkMember<T>> {
    abstract Annotation[] getAnnotations();

    public abstract String getName();

    public abstract Class<?> getType();

    public abstract boolean isPublic();

    abstract boolean isShadowedBy(T t);

    public abstract boolean isStatic();

    boolean isShadowedBy(List<T> members) {
        for (T each : members) {
            if (isShadowedBy((FrameworkMember) each)) {
                return true;
            }
        }
        return false;
    }
}
