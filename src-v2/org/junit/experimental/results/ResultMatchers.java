/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.results;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.experimental.results.PrintableResult;

public class ResultMatchers {
    public static Matcher<PrintableResult> failureCountIs(final int n2) {
        return new TypeSafeMatcher<PrintableResult>(){

            @Override
            public void describeTo(Description description) {
                description.appendText("has " + n2 + " failures");
            }

            @Override
            public boolean matchesSafely(PrintableResult printableResult) {
                if (printableResult.failureCount() == n2) {
                    return true;
                }
                return false;
            }
        };
    }

    public static Matcher<PrintableResult> hasFailureContaining(final String string2) {
        return new BaseMatcher<PrintableResult>(){

            @Override
            public void describeTo(Description description) {
                description.appendText("has failure containing " + string2);
            }

            @Override
            public boolean matches(Object object) {
                return object.toString().contains(string2);
            }
        };
    }

    public static Matcher<Object> hasSingleFailureContaining(final String string2) {
        return new BaseMatcher<Object>(){

            @Override
            public void describeTo(Description description) {
                description.appendText("has single failure containing " + string2);
            }

            @Override
            public boolean matches(Object object) {
                if (object.toString().contains(string2) && ResultMatchers.failureCountIs(1).matches(object)) {
                    return true;
                }
                return false;
            }
        };
    }

    public static Matcher<PrintableResult> isSuccessful() {
        return ResultMatchers.failureCountIs(0);
    }

}

