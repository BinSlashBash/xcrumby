/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;

public class ThrowableCauseMatcher<T extends Throwable>
extends TypeSafeMatcher<T> {
    private final Matcher<T> fMatcher;

    public ThrowableCauseMatcher(Matcher<T> matcher) {
        this.fMatcher = matcher;
    }

    @Factory
    public static <T extends Throwable> Matcher<T> hasCause(Matcher<T> matcher) {
        return new ThrowableCauseMatcher<T>(matcher);
    }

    @Override
    protected void describeMismatchSafely(T t2, Description description) {
        description.appendText("cause ");
        this.fMatcher.describeMismatch(t2.getCause(), description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("exception with cause ");
        description.appendDescriptionOf(this.fMatcher);
    }

    @Override
    protected boolean matchesSafely(T t2) {
        return this.fMatcher.matches(t2.getCause());
    }
}

