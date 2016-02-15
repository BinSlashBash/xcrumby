/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public abstract class Condition<T> {
    public static final NotMatched<Object> NOT_MATCHED = new NotMatched();

    private Condition() {
    }

    public static <T> Condition<T> matched(T t2, Description description) {
        return new Matched(t2, description);
    }

    public static <T> Condition<T> notMatched() {
        return NOT_MATCHED;
    }

    public abstract <U> Condition<U> and(Step<? super T, U> var1);

    public final boolean matching(Matcher<T> matcher) {
        return this.matching(matcher, "");
    }

    public abstract boolean matching(Matcher<T> var1, String var2);

    public final <U> Condition<U> then(Step<? super T, U> step) {
        return this.and(step);
    }

    private static final class Matched<T>
    extends Condition<T> {
        private final Description mismatch;
        private final T theValue;

        private Matched(T t2, Description description) {
            super();
            this.theValue = t2;
            this.mismatch = description;
        }

        @Override
        public <U> Condition<U> and(Step<? super T, U> step) {
            return step.apply(this.theValue, this.mismatch);
        }

        @Override
        public boolean matching(Matcher<T> matcher, String string2) {
            if (matcher.matches(this.theValue)) {
                return true;
            }
            this.mismatch.appendText(string2);
            matcher.describeMismatch(this.theValue, this.mismatch);
            return false;
        }
    }

    private static final class NotMatched<T>
    extends Condition<T> {
        private NotMatched() {
            super();
        }

        @Override
        public <U> Condition<U> and(Step<? super T, U> step) {
            return NotMatched.notMatched();
        }

        @Override
        public boolean matching(Matcher<T> matcher, String string2) {
            return false;
        }
    }

    public static interface Step<I, O> {
        public Condition<O> apply(I var1, Description var2);
    }

}

