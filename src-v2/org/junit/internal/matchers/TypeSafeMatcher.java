/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.matchers;

import java.lang.reflect.Method;
import org.hamcrest.BaseMatcher;
import org.junit.internal.MethodSorter;

@Deprecated
public abstract class TypeSafeMatcher<T>
extends BaseMatcher<T> {
    private Class<?> expectedType;

    protected TypeSafeMatcher() {
        this.expectedType = TypeSafeMatcher.findExpectedType(this.getClass());
    }

    protected TypeSafeMatcher(Class<T> class_) {
        this.expectedType = class_;
    }

    private static Class<?> findExpectedType(Class<?> class_) {
        while (class_ != Object.class) {
            for (Method method : MethodSorter.getDeclaredMethods(class_)) {
                if (!TypeSafeMatcher.isMatchesSafelyMethod(method)) continue;
                return method.getParameterTypes()[0];
            }
            class_ = class_.getSuperclass();
        }
        throw new Error("Cannot determine correct type for matchesSafely() method.");
    }

    private static boolean isMatchesSafelyMethod(Method method) {
        if (method.getName().equals("matchesSafely") && method.getParameterTypes().length == 1 && !method.isSynthetic()) {
            return true;
        }
        return false;
    }

    @Override
    public final boolean matches(Object object) {
        if (object != null && this.expectedType.isInstance(object) && this.matchesSafely(object)) {
            return true;
        }
        return false;
    }

    public abstract boolean matchesSafely(T var1);
}

