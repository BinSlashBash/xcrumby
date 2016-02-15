/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class TypeSafeMatcher<T>
extends BaseMatcher<T> {
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 1, 0);
    private final Class<?> expectedType;

    protected TypeSafeMatcher() {
        this(TYPE_FINDER);
    }

    protected TypeSafeMatcher(Class<?> class_) {
        this.expectedType = class_;
    }

    protected TypeSafeMatcher(ReflectiveTypeFinder reflectiveTypeFinder) {
        this.expectedType = reflectiveTypeFinder.findExpectedType(this.getClass());
    }

    @Override
    public final void describeMismatch(Object object, Description description) {
        if (object == null) {
            super.describeMismatch(object, description);
            return;
        }
        if (!this.expectedType.isInstance(object)) {
            description.appendText("was a ").appendText(object.getClass().getName()).appendText(" (").appendValue(object).appendText(")");
            return;
        }
        this.describeMismatchSafely(object, description);
    }

    protected void describeMismatchSafely(T t2, Description description) {
        super.describeMismatch(t2, description);
    }

    @Override
    public final boolean matches(Object object) {
        if (object != null && this.expectedType.isInstance(object) && this.matchesSafely(object)) {
            return true;
        }
        return false;
    }

    protected abstract boolean matchesSafely(T var1);
}

