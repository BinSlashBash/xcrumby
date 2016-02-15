package org.hamcrest.collection;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.IsEqual;

public class IsArrayWithSize<E> extends FeatureMatcher<E[], Integer> {
    public IsArrayWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an array with size", "array size");
    }

    protected Integer featureValueOf(E[] actual) {
        return Integer.valueOf(actual.length);
    }

    @Factory
    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsArrayWithSize(sizeMatcher);
    }

    @Factory
    public static <E> Matcher<E[]> arrayWithSize(int size) {
        return arrayWithSize(IsEqual.equalTo(Integer.valueOf(size)));
    }

    @Factory
    public static <E> Matcher<E[]> emptyArray() {
        return DescribedAs.describedAs("an empty array", arrayWithSize(0), new Object[0]);
    }
}
