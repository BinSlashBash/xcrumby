/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.IsEqual;

public class IsArrayWithSize<E>
extends FeatureMatcher<E[], Integer> {
    public IsArrayWithSize(Matcher<? super Integer> matcher) {
        super(matcher, "an array with size", "array size");
    }

    @Factory
    public static <E> Matcher<E[]> arrayWithSize(int n2) {
        return IsArrayWithSize.arrayWithSize(IsEqual.equalTo(n2));
    }

    @Factory
    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> matcher) {
        return new IsArrayWithSize<E>(matcher);
    }

    @Factory
    public static <E> Matcher<E[]> emptyArray() {
        return DescribedAs.describedAs("an empty array", IsArrayWithSize.arrayWithSize(0), new Object[0]);
    }

    @Override
    protected Integer featureValueOf(E[] arrE) {
        return arrE.length;
    }
}

