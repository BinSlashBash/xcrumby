/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.util.ArrayList;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;

public class CombinableMatcher<T>
extends TypeSafeDiagnosingMatcher<T> {
    private final Matcher<? super T> matcher;

    public CombinableMatcher(Matcher<? super T> matcher) {
        this.matcher = matcher;
    }

    @Factory
    public static <LHS> CombinableBothMatcher<LHS> both(Matcher<? super LHS> matcher) {
        return new CombinableBothMatcher<LHS>(matcher);
    }

    @Factory
    public static <LHS> CombinableEitherMatcher<LHS> either(Matcher<? super LHS> matcher) {
        return new CombinableEitherMatcher<LHS>(matcher);
    }

    private ArrayList<Matcher<? super T>> templatedListWith(Matcher<? super T> matcher) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>();
        arrayList.add(this.matcher);
        arrayList.add(matcher);
        return arrayList;
    }

    public CombinableMatcher<T> and(Matcher<? super T> matcher) {
        return new CombinableMatcher<T>(new AllOf<T>(this.templatedListWith(matcher)));
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(this.matcher);
    }

    @Override
    protected boolean matchesSafely(T t2, Description description) {
        if (!this.matcher.matches(t2)) {
            this.matcher.describeMismatch(t2, description);
            return false;
        }
        return true;
    }

    public CombinableMatcher<T> or(Matcher<? super T> matcher) {
        return new CombinableMatcher<T>(new AnyOf<T>(this.templatedListWith(matcher)));
    }

    public static final class CombinableBothMatcher<X> {
        private final Matcher<? super X> first;

        public CombinableBothMatcher(Matcher<? super X> matcher) {
            this.first = matcher;
        }

        public CombinableMatcher<X> and(Matcher<? super X> matcher) {
            return new CombinableMatcher<X>(this.first).and(matcher);
        }
    }

    public static final class CombinableEitherMatcher<X> {
        private final Matcher<? super X> first;

        public CombinableEitherMatcher(Matcher<? super X> matcher) {
            this.first = matcher;
        }

        public CombinableMatcher<X> or(Matcher<? super X> matcher) {
            return new CombinableMatcher<X>(this.first).or(matcher);
        }
    }

}

