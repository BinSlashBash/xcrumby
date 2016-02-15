package org.hamcrest.collection;

import java.util.Iterator;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsIterableWithSize<E> extends FeatureMatcher<Iterable<E>, Integer> {
    public IsIterableWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an iterable with size", "iterable size");
    }

    protected Integer featureValueOf(Iterable<E> actual) {
        int size = 0;
        Iterator<E> iterator = actual.iterator();
        while (iterator.hasNext()) {
            size++;
            iterator.next();
        }
        return Integer.valueOf(size);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsIterableWithSize(sizeMatcher);
    }

    @Factory
    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return iterableWithSize(IsEqual.equalTo(Integer.valueOf(size)));
    }
}
