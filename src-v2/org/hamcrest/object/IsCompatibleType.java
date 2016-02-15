/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCompatibleType<T>
extends TypeSafeMatcher<Class<?>> {
    private final Class<T> type;

    public IsCompatibleType(Class<T> class_) {
        this.type = class_;
    }

    @Factory
    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> class_) {
        return new IsCompatibleType<T>(class_);
    }

    @Override
    public void describeMismatchSafely(Class<?> class_, Description description) {
        description.appendValue(class_.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("type < ").appendText(this.type.getName());
    }

    @Override
    public boolean matchesSafely(Class<?> class_) {
        return this.type.isAssignableFrom(class_);
    }
}

