/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.Iterator;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsIterableWithSize<E>
extends FeatureMatcher<Iterable<E>, Integer> {
    public IsIterableWithSize(Matcher<? super Integer> matcher) {
        super(matcher, "an iterable with size", "iterable size");
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(int n2) {
        return IsIterableWithSize.iterableWithSize(IsEqual.equalTo(n2));
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> matcher) {
        return new IsIterableWithSize<E>(matcher);
    }

    @Override
    protected Integer featureValueOf(Iterable<E> object) {
        int n2 = 0;
        object = object.iterator();
        while (object.hasNext()) {
            ++n2;
            object.next();
        }
        return n2;
    }
}

