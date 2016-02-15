/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Every<T>
extends TypeSafeDiagnosingMatcher<Iterable<T>> {
    private final Matcher<? super T> matcher;

    public Every(Matcher<? super T> matcher) {
        this.matcher = matcher;
    }

    @Factory
    public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> matcher) {
        return new Every<U>(matcher);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("every item is ").appendDescriptionOf(this.matcher);
    }

    @Override
    public boolean matchesSafely(Iterable<T> object, Description description) {
        object = object.iterator();
        while (object.hasNext()) {
            Object e2 = object.next();
            if (this.matcher.matches(e2)) continue;
            description.appendText("an item ");
            this.matcher.describeMismatch(e2, description);
            return false;
        }
        return true;
    }
}

