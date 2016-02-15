/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.matchers.JUnitMatchers;

class ExpectedExceptionMatcherBuilder {
    private final List<Matcher<?>> fMatchers = new ArrayList();

    ExpectedExceptionMatcherBuilder() {
    }

    private Matcher<Throwable> allOfTheMatchers() {
        if (this.fMatchers.size() == 1) {
            return this.cast(this.fMatchers.get(0));
        }
        return CoreMatchers.allOf(this.castedMatchers());
    }

    private Matcher<Throwable> cast(Matcher<?> matcher) {
        return matcher;
    }

    private List<Matcher<? super Throwable>> castedMatchers() {
        return new ArrayList<Matcher<? super Throwable>>(this.fMatchers);
    }

    void add(Matcher<?> matcher) {
        this.fMatchers.add(matcher);
    }

    Matcher<Throwable> build() {
        return JUnitMatchers.isThrowable(this.allOfTheMatchers());
    }

    boolean expectsThrowable() {
        if (!this.fMatchers.isEmpty()) {
            return true;
        }
        return false;
    }
}

