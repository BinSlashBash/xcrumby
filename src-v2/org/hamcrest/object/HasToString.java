/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.object;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class HasToString<T>
extends FeatureMatcher<T, String> {
    public HasToString(Matcher<? super String> matcher) {
        super(matcher, "with toString()", "toString()");
    }

    @Factory
    public static <T> Matcher<T> hasToString(String string2) {
        return new HasToString<T>(IsEqual.equalTo(string2));
    }

    @Factory
    public static <T> Matcher<T> hasToString(Matcher<? super String> matcher) {
        return new HasToString<T>(matcher);
    }

    @Override
    protected String featureValueOf(T t2) {
        return String.valueOf(t2);
    }
}

