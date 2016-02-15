package org.hamcrest.number;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OrderingComparison<T extends Comparable<T>> extends TypeSafeMatcher<T> {
    private static final int EQUAL = 0;
    private static final int GREATER_THAN = 1;
    private static final int LESS_THAN = -1;
    private static final String[] comparisonDescriptions;
    private final T expected;
    private final int maxCompare;
    private final int minCompare;

    static {
        comparisonDescriptions = new String[]{"less than", "equal to", "greater than"};
    }

    private OrderingComparison(T expected, int minCompare, int maxCompare) {
        this.expected = expected;
        this.minCompare = minCompare;
        this.maxCompare = maxCompare;
    }

    public boolean matchesSafely(T actual) {
        int compare = Integer.signum(actual.compareTo(this.expected));
        return this.minCompare <= compare && compare <= this.maxCompare;
    }

    public void describeMismatchSafely(T actual, Description mismatchDescription) {
        mismatchDescription.appendValue(actual).appendText(" was ").appendText(asText(actual.compareTo(this.expected))).appendText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).appendValue(this.expected);
    }

    public void describeTo(Description description) {
        description.appendText("a value ").appendText(asText(this.minCompare));
        if (this.minCompare != this.maxCompare) {
            description.appendText(" or ").appendText(asText(this.maxCompare));
        }
        description.appendText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).appendValue(this.expected);
    }

    private static String asText(int comparison) {
        return comparisonDescriptions[Integer.signum(comparison) + GREATER_THAN];
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T value) {
        return new OrderingComparison(value, EQUAL, EQUAL);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return new OrderingComparison(value, GREATER_THAN, GREATER_THAN);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return new OrderingComparison(value, EQUAL, GREATER_THAN);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return new OrderingComparison(value, LESS_THAN, LESS_THAN);
    }

    @Factory
    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return new OrderingComparison(value, LESS_THAN, EQUAL);
    }
}
