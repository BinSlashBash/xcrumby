/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContaining<T>
extends TypeSafeMatcher<T[]> {
    private final Matcher<? super T> elementMatcher;

    public IsArrayContaining(Matcher<? super T> matcher) {
        this.elementMatcher = matcher;
    }

    @Factory
    public static <T> Matcher<T[]> hasItemInArray(T t2) {
        return IsArrayContaining.hasItemInArray(IsEqual.equalTo(t2));
    }

    @Factory
    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> matcher) {
        return new IsArrayContaining<T>(matcher);
    }

    @Override
    public void describeMismatchSafely(T[] arrT, Description description) {
        super.describeMismatch(Arrays.asList(arrT), description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an array containing ").appendDescriptionOf(this.elementMatcher);
    }

    @Override
    public boolean matchesSafely(T[] arrT) {
        for (T t2 : arrT) {
            if (!this.elementMatcher.matches(t2)) continue;
            return true;
        }
        return false;
    }
}

