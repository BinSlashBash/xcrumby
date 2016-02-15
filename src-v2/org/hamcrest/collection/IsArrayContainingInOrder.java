/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;

public class IsArrayContainingInOrder<E>
extends TypeSafeMatcher<E[]> {
    private final IsIterableContainingInOrder<E> iterableMatcher;
    private final Collection<Matcher<? super E>> matchers;

    public IsArrayContainingInOrder(List<Matcher<? super E>> list) {
        this.iterableMatcher = new IsIterableContainingInOrder<E>(list);
        this.matchers = list;
    }

    @Factory
    public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> list) {
        return new IsArrayContainingInOrder<E>(list);
    }

    @Factory
    public static /* varargs */ <E> Matcher<E[]> arrayContaining(E ... arrE) {
        ArrayList<Matcher<Matcher<E>>> arrayList = new ArrayList<Matcher<Matcher<E>>>();
        int n2 = arrE.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(IsEqual.equalTo(arrE[i2]));
        }
        return IsArrayContainingInOrder.arrayContaining(arrayList);
    }

    @Factory
    public static /* varargs */ <E> Matcher<E[]> arrayContaining(Matcher<? super E> ... arrmatcher) {
        return IsArrayContainingInOrder.arrayContaining(Arrays.asList(arrmatcher));
    }

    @Override
    public void describeMismatchSafely(E[] arrE, Description description) {
        this.iterableMatcher.describeMismatch(Arrays.asList(arrE), description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("[", ", ", "]", this.matchers);
    }

    @Override
    public boolean matchesSafely(E[] arrE) {
        return this.iterableMatcher.matches(Arrays.asList(arrE));
    }
}

