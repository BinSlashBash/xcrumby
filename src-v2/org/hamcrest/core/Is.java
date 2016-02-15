/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;

public class Is<T>
extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    public Is(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Deprecated
    @Factory
    public static <T> Matcher<T> is(Class<T> class_) {
        return Is.is(IsInstanceOf.instanceOf(class_));
    }

    @Factory
    public static <T> Matcher<T> is(T t2) {
        return Is.is(IsEqual.equalTo(t2));
    }

    @Factory
    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return new Is<T>(matcher);
    }

    @Factory
    public static <T> Matcher<T> isA(Class<T> class_) {
        return Is.is(IsInstanceOf.instanceOf(class_));
    }

    @Override
    public void describeMismatch(Object object, Description description) {
        this.matcher.describeMismatch(object, description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is ").appendDescriptionOf(this.matcher);
    }

    @Override
    public boolean matches(Object object) {
        return this.matcher.matches(object);
    }
}

