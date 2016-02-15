/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.internal;

import java.lang.reflect.Method;

public class ReflectiveTypeFinder {
    private final int expectedNumberOfParameters;
    private final String methodName;
    private final int typedParameter;

    public ReflectiveTypeFinder(String string2, int n2, int n3) {
        this.methodName = string2;
        this.expectedNumberOfParameters = n2;
        this.typedParameter = n3;
    }

    protected boolean canObtainExpectedTypeFrom(Method method) {
        if (method.getName().equals(this.methodName) && method.getParameterTypes().length == this.expectedNumberOfParameters && !method.isSynthetic()) {
            return true;
        }
        return false;
    }

    protected Class<?> expectedTypeFrom(Method method) {
        return method.getParameterTypes()[this.typedParameter];
    }

    public Class<?> findExpectedType(Class<?> class_) {
        while (class_ != Object.class) {
            for (Method method : class_.getDeclaredMethods()) {
                if (!this.canObtainExpectedTypeFrom(method)) continue;
                return this.expectedTypeFrom(method);
            }
            class_ = class_.getSuperclass();
        }
        throw new Error("Cannot determine correct type for " + this.methodName + "() method.");
    }
}

