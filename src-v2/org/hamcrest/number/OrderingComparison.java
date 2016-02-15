/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OrderingComparison<T extends Comparable<T>>
extends TypeSafeMatcher<T> {
    private static final int EQUAL = 0;
    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final String[] comparisonDescriptions = new String[]{"less than", "equal to", "greater than"};
    private final T expected;
    private final int maxCompare;
    private final int minCompare;

    private OrderingComparison(T t2, int n2, int n3) {
        this.expected = t2;
        this.minCompare = n2;
        this.maxCompare = n3;
    }

    private static String asText(int n2) {
        return comparisonDescriptions[Integer.signum(n2) + 1];
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T t2) {
        return new OrderingComparison<T>(t2, 0, 0);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T t2) {
        return new OrderingComparison<T>(t2, 1, 1);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T t2) {
        return new OrderingComparison<T>(t2, 0, 1);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThan(T t2) {
        return new OrderingComparison<T>(t2, -1, -1);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T t2) {
        return new OrderingComparison<T>(t2, -1, 0);
    }

    @Override
    public void describeMismatchSafely(T t2, Description description) {
        description.appendValue(t2).appendText(" was ").appendText(OrderingComparison.asText(t2.compareTo(this.expected))).appendText(" ").appendValue(this.expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a value ").appendText(OrderingComparison.asText(this.minCompare));
        if (this.minCompare != this.maxCompare) {
            description.appendText(" or ").appendText(OrderingComparison.asText(this.maxCompare));
        }
        description.appendText(" ").appendValue(this.expected);
    }

    @Override
    public boolean matchesSafely(T t2) {
        int n2 = Integer.signum(t2.compareTo(this.expected));
        if (this.minCompare <= n2 && n2 <= this.maxCompare) {
            return true;
        }
        return false;
    }
}

