/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;

public class IsCollectionContaining<T>
extends TypeSafeDiagnosingMatcher<Iterable<? super T>> {
    private final Matcher<? super T> elementMatcher;

    public IsCollectionContaining(Matcher<? super T> matcher) {
        this.elementMatcher = matcher;
    }

    @Factory
    public static <T> Matcher<Iterable<? super T>> hasItem(T t2) {
        return new IsCollectionContaining<T>(IsEqual.equalTo(t2));
    }

    @Factory
    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> matcher) {
        return new IsCollectionContaining<T>(matcher);
    }

    @Factory
    public static /* varargs */ <T> Matcher<Iterable<T>> hasItems(T ... arrT) {
        ArrayList arrayList = new ArrayList(arrT.length);
        int n2 = arrT.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(IsCollectionContaining.hasItem(arrT[i2]));
        }
        return AllOf.allOf(arrayList);
    }

    @Factory
    public static /* varargs */ <T> Matcher<Iterable<T>> hasItems(Matcher<? super T> ... arrmatcher) {
        ArrayList arrayList = new ArrayList(arrmatcher.length);
        int n2 = arrmatcher.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(new IsCollectionContaining<T>(arrmatcher[i2]));
        }
        return AllOf.allOf(arrayList);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a collection containing ").appendDescriptionOf(this.elementMatcher);
    }

    @Override
    protected boolean matchesSafely(Iterable<? super T> object, Description description) {
        boolean bl2 = false;
        object = object.iterator();
        while (object.hasNext()) {
            Object e2 = object.next();
            if (this.elementMatcher.matches(e2)) {
                return true;
            }
            if (bl2) {
                description.appendText(", ");
            }
            this.elementMatcher.describeMismatch(e2, description);
            bl2 = true;
        }
        return false;
    }
}

