/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyIterable<E>
extends TypeSafeMatcher<Iterable<? extends E>> {
    @Factory
    public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
        return new IsEmptyIterable<E>();
    }

    @Factory
    public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> class_) {
        return IsEmptyIterable.emptyIterable();
    }

    @Override
    public void describeMismatchSafely(Iterable<? extends E> iterable, Description description) {
        description.appendValueList("[", ",", "]", iterable);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an empty iterable");
    }

    @Override
    public boolean matchesSafely(Iterable<? extends E> iterable) {
        if (!iterable.iterator().hasNext()) {
            return true;
        }
        return false;
    }
}

