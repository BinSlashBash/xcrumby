/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsCollectionWithSize<E>
extends FeatureMatcher<Collection<? extends E>, Integer> {
    public IsCollectionWithSize(Matcher<? super Integer> matcher) {
        super(matcher, "a collection with size", "collection size");
    }

    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(int n2) {
        return IsCollectionWithSize.hasSize(IsEqual.equalTo(n2));
    }

    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> matcher) {
        return new IsCollectionWithSize<E>(matcher);
    }

    @Override
    protected Integer featureValueOf(Collection<? extends E> collection) {
        return collection.size();
    }
}

