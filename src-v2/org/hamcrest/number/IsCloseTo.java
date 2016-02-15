/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCloseTo
extends TypeSafeMatcher<Double> {
    private final double delta;
    private final double value;

    public IsCloseTo(double d2, double d3) {
        this.delta = d3;
        this.value = d2;
    }

    private double actualDelta(Double d2) {
        return Math.abs(d2 - this.value) - this.delta;
    }

    @Factory
    public static Matcher<Double> closeTo(double d2, double d3) {
        return new IsCloseTo(d2, d3);
    }

    @Override
    public void describeMismatchSafely(Double d2, Description description) {
        description.appendValue(d2).appendText(" differed by ").appendValue(this.actualDelta(d2));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ").appendValue(this.delta).appendText(" of ").appendValue(this.value);
    }

    @Override
    public boolean matchesSafely(Double d2) {
        if (this.actualDelta(d2) <= 0.0) {
            return true;
        }
        return false;
    }
}

