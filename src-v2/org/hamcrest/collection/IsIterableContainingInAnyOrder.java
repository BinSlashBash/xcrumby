/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class IsIterableContainingInAnyOrder<T>
extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
    private final Collection<Matcher<? super T>> matchers;

    public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> collection) {
        this.matchers = collection;
    }

    @Factory
    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> collection) {
        return new IsIterableContainingInAnyOrder<T>(collection);
    }

    @Deprecated
    @Factory
    public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(Matcher<? super E> matcher) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(new ArrayList<Matcher<? super T>>(Arrays.asList(matcher)));
    }

    @Factory
    public static /* varargs */ <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T ... arrT) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>();
        int n2 = arrT.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(IsEqual.equalTo(arrT[i2]));
        }
        return new IsIterableContainingInAnyOrder<T>(arrayList);
    }

    @Factory
    public static /* varargs */ <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T> ... arrmatcher) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(Arrays.asList(arrmatcher));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("iterable over ").appendList("[", ", ", "]", this.matchers).appendText(" in any order");
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends T> iterable, Description object) {
        object = new Matching(this.matchers, (Description)object);
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            if (object.matches(iterator.next())) continue;
            return false;
        }
        return object.isFinished(iterable);
    }

    private static class Matching<S> {
        private final Collection<Matcher<? super S>> matchers;
        private final Description mismatchDescription;

        public Matching(Collection<Matcher<? super S>> collection, Description description) {
            this.matchers = new ArrayList<Matcher<S>>(collection);
            this.mismatchDescription = description;
        }

        private boolean isMatched(S s2) {
            for (Matcher<S> matcher : this.matchers) {
                if (!matcher.matches(s2)) continue;
                this.matchers.remove(matcher);
                return true;
            }
            this.mismatchDescription.appendText("Not matched: ").appendValue(s2);
            return false;
        }

        private boolean isNotSurplus(S s2) {
            if (this.matchers.isEmpty()) {
                this.mismatchDescription.appendText("Not matched: ").appendValue(s2);
                return false;
            }
            return true;
        }

        public boolean isFinished(Iterable<? extends S> iterable) {
            if (this.matchers.isEmpty()) {
                return true;
            }
            this.mismatchDescription.appendText("No item matches: ").appendList("", ", ", "", this.matchers).appendText(" in ").appendValueList("[", ", ", "]", iterable);
            return false;
        }

        public boolean matches(S s2) {
            if (this.isNotSurplus(s2) && this.isMatched(s2)) {
                return true;
            }
            return false;
        }
    }

}

