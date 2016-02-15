package org.hamcrest.object;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class HasToString<T> extends FeatureMatcher<T, String> {
    public HasToString(Matcher<? super String> toStringMatcher) {
        super(toStringMatcher, "with toString()", "toString()");
    }

    protected String featureValueOf(T actual) {
        return String.valueOf(actual);
    }

    @Factory
    public static <T> Matcher<T> hasToString(Matcher<? super String> toStringMatcher) {
        return new HasToString(toStringMatcher);
    }

    @Factory
    public static <T> Matcher<T> hasToString(String expectedToString) {
        return new HasToString(IsEqual.equalTo(expectedToString));
    }
}
