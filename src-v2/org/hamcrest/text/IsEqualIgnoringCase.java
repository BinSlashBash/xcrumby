/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringCase
extends TypeSafeMatcher<String> {
    private final String string;

    public IsEqualIgnoringCase(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
        }
        this.string = string2;
    }

    @Factory
    public static Matcher<String> equalToIgnoringCase(String string2) {
        return new IsEqualIgnoringCase(string2);
    }

    @Override
    public void describeMismatchSafely(String string2, Description description) {
        description.appendText("was ").appendText(string2);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equalToIgnoringCase(").appendValue(this.string).appendText(")");
    }

    @Override
    public boolean matchesSafely(String string2) {
        return this.string.equalsIgnoreCase(string2);
    }
}

