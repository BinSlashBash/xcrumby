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

public class IsNot<T>
extends BaseMatcher<T> {
    private final Matcher<T> matcher;

    public IsNot(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Factory
    public static <T> Matcher<T> not(T t2) {
        return IsNot.not(IsEqual.equalTo(t2));
    }

    @Factory
    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return new IsNot<T>(matcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("not ").appendDescriptionOf(this.matcher);
    }

    @Override
    public boolean matches(Object object) {
        if (!this.matcher.matches(object)) {
            return true;
        }
        return false;
    }
}

