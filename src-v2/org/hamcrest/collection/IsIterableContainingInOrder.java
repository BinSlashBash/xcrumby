/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class IsIterableContainingInOrder<E>
extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
    private final List<Matcher<? super E>> matchers;

    public IsIterableContainingInOrder(List<Matcher<? super E>> list) {
        this.matchers = list;
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> list) {
        return new IsIterableContainingInOrder<E>(list);
    }

    @Factory
    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> matcher) {
        return IsIterableContainingInOrder.contains(new ArrayList<Matcher<? super E>>(Arrays.asList(matcher)));
    }

    @Factory
    public static /* varargs */ <E> Matcher<Iterable<? extends E>> contains(E ... arrE) {
        ArrayList<Matcher<Matcher<E>>> arrayList = new ArrayList<Matcher<Matcher<E>>>();
        int n2 = arrE.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(IsEqual.equalTo(arrE[i2]));
        }
        return IsIterableContainingInOrder.contains(arrayList);
    }

    @Factory
    public static /* varargs */ <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> ... arrmatcher) {
        return IsIterableContainingInOrder.contains(Arrays.asList(arrmatcher));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("iterable containing ").appendList("[", ", ", "]", this.matchers);
    }

    @Override
    protected boolean matchesSafely(Iterable<? extends E> object, Description object2) {
        object2 = new MatchSeries(this.matchers, (Description)object2);
        object = object.iterator();
        while (object.hasNext()) {
            if (object2.matches(object.next())) continue;
            return false;
        }
        return object2.isFinished();
    }

    private static class MatchSeries<F> {
        public final List<Matcher<? super F>> matchers;
        private final Description mismatchDescription;
        public int nextMatchIx = 0;

        public MatchSeries(List<Matcher<? super F>> list, Description description) {
            this.mismatchDescription = description;
            if (list.isEmpty()) {
                throw new IllegalArgumentException("Should specify at least one expected element");
            }
            this.matchers = list;
        }

        private void describeMismatch(Matcher<? super F> matcher, F f2) {
            this.mismatchDescription.appendText("item " + this.nextMatchIx + ": ");
            matcher.describeMismatch(f2, this.mismatchDescription);
        }

        private boolean isMatched(F f2) {
            Matcher<F> matcher = this.matchers.get(this.nextMatchIx);
            if (!matcher.matches(f2)) {
                this.describeMismatch(matcher, f2);
                return false;
            }
            ++this.nextMatchIx;
            return true;
        }

        private boolean isNotSurplus(F f2) {
            if (this.matchers.size() <= this.nextMatchIx) {
                this.mismatchDescription.appendText("Not matched: ").appendValue(f2);
                return false;
            }
            return true;
        }

        public boolean isFinished() {
            if (this.nextMatchIx < this.matchers.size()) {
                this.mismatchDescription.appendText("No item matched: ").appendDescriptionOf(this.matchers.get(this.nextMatchIx));
                return false;
            }
            return true;
        }

        public boolean matches(F f2) {
            if (this.isNotSurplus(f2) && this.isMatched(f2)) {
                return true;
            }
            return false;
        }
    }

}

