package org.junit.internal.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ThrowableMessageMatcher<T extends Throwable> extends TypeSafeMatcher<T> {
    private final Matcher<String> fMatcher;

    public ThrowableMessageMatcher(Matcher<String> matcher) {
        this.fMatcher = matcher;
    }

    public void describeTo(Description description) {
        description.appendText("exception with message ");
        description.appendDescriptionOf(this.fMatcher);
    }

    protected boolean matchesSafely(T item) {
        return this.fMatcher.matches(item.getMessage());
    }

    protected void describeMismatchSafely(T item, Description description) {
        description.appendText("message ");
        this.fMatcher.describeMismatch(item.getMessage(), description);
    }

    @Factory
    public static <T extends Throwable> Matcher<T> hasMessage(Matcher<String> matcher) {
        return new ThrowableMessageMatcher(matcher);
    }
}
