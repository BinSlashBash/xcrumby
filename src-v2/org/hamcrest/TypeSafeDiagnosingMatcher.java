/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class TypeSafeDiagnosingMatcher<T>
extends BaseMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 2, 0);
    private final Class<?> expectedType;

    protected TypeSafeDiagnosingMatcher() {
        this(TYPE_FINDER);
    }

    protected TypeSafeDiagnosingMatcher(Class<?> class_) {
        this.expectedType = class_;
    }

    protected TypeSafeDiagnosingMatcher(ReflectiveTypeFinder reflectiveTypeFinder) {
        this.expectedType = reflectiveTypeFinder.findExpectedType(this.getClass());
    }

    @Override
    public final void describeMismatch(Object object, Description description) {
        if (object == null || !this.expectedType.isInstance(object)) {
            super.describeMismatch(object, description);
            return;
        }
        this.matchesSafely(object, description);
    }

    @Override
    public final boolean matches(Object object) {
        if (object != null && this.expectedType.isInstance(object) && this.matchesSafely(object, new Description.NullDescription())) {
            return true;
        }
        return false;
    }

    protected abstract boolean matchesSafely(T var1, Description var2);
}

