package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyIterable<E> extends TypeSafeMatcher<Iterable<? extends E>> {
    public boolean matchesSafely(Iterable<? extends E> iterable) {
        return !iterable.iterator().hasNext();
    }

    public void describeMismatchSafely(Iterable<? extends E> iter, Description mismatchDescription) {
        mismatchDescription.appendValueList("[", ",", "]", (Iterable) iter);
    }

    public void describeTo(Description description) {
        description.appendText("an empty iterable");
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
        return new IsEmptyIterable();
    }

    @Factory
    public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> cls) {
        return emptyIterable();
    }
}
