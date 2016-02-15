package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsCollectionWithSize<E> extends FeatureMatcher<Collection<? extends E>, Integer> {
    public IsCollectionWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "a collection with size", "collection size");
    }

    protected Integer featureValueOf(Collection<? extends E> actual) {
        return Integer.valueOf(actual.size());
    }

    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> sizeMatcher) {
        return new IsCollectionWithSize(sizeMatcher);
    }

    @Factory
    public static <E> Matcher<Collection<? extends E>> hasSize(int size) {
        return hasSize(IsEqual.equalTo(Integer.valueOf(size)));
    }
}
