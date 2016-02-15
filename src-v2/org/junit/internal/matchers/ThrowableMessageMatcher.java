/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;

public class ThrowableMessageMatcher<T extends Throwable>
extends TypeSafeMatcher<T> {
    private final Matcher<String> fMatcher;

    public ThrowableMessageMatcher(Matcher<String> matcher) {
        this.fMatcher = matcher;
    }

    @Factory
    public static <T extends Throwable> Matcher<T> hasMessage(Matcher<String> matcher) {
        return new ThrowableMessageMatcher<T>(matcher);
    }

    @Override
    protected void describeMismatchSafely(T t2, Description description) {
        description.appendText("message ");
        this.fMatcher.describeMismatch(t2.getMessage(), description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("exception with message ");
        description.appendDescriptionOf(this.fMatcher);
    }

    @Override
    protected boolean matchesSafely(T t2) {
        return this.fMatcher.matches(t2.getMessage());
    }
}

