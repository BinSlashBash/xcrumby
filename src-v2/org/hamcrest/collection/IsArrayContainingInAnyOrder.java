/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;

public class IsArrayContainingInAnyOrder<E>
extends TypeSafeMatcher<E[]> {
    private final IsIterableContainingInAnyOrder<E> iterableMatcher;
    private final Collection<Matcher<? super E>> matchers;

    public IsArrayContainingInAnyOrder(Collection<Matcher<? super E>> collection) {
        this.iterableMatcher = new IsIterableContainingInAnyOrder(collection);
        this.matchers = collection;
    }

    @Factory
    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> collection) {
        return new IsArrayContainingInAnyOrder<E>(collection);
    }

    @Factory
    public static /* varargs */ <E> Matcher<E[]> arrayContainingInAnyOrder(E ... arrE) {
        ArrayList<Matcher<Matcher<E>>> arrayList = new ArrayList<Matcher<Matcher<E>>>();
        int n2 = arrE.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(IsEqual.equalTo(arrE[i2]));
        }
        return new IsArrayContainingInAnyOrder<E>(arrayList);
    }

    @Factory
    public static /* varargs */ <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E> ... arrmatcher) {
        return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(Arrays.asList(arrmatcher));
    }

    @Override
    public void describeMismatchSafely(E[] arrE, Description description) {
        this.iterableMatcher.describeMismatch(Arrays.asList(arrE), description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("[", ", ", "]", this.matchers).appendText(" in any order");
    }

    @Override
    public boolean matchesSafely(E[] arrE) {
        return this.iterableMatcher.matches(Arrays.asList(arrE));
    }
}

