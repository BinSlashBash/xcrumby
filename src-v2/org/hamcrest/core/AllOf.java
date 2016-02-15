/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

public class AllOf<T>
extends DiagnosingMatcher<T> {
    private final Iterable<Matcher<? super T>> matchers;

    public AllOf(Iterable<Matcher<? super T>> iterable) {
        this.matchers = iterable;
    }

    @Factory
    public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> iterable) {
        return new AllOf<T>(iterable);
    }

    @Factory
    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>(2);
        arrayList.add(matcher);
        arrayList.add(matcher2);
        return AllOf.allOf(arrayList);
    }

    @Factory
    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>(3);
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        return AllOf.allOf(arrayList);
    }

    @Factory
    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>(4);
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        arrayList.add(matcher4);
        return AllOf.allOf(arrayList);
    }

    @Factory
    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>(5);
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        arrayList.add(matcher4);
        arrayList.add(matcher5);
        return AllOf.allOf(arrayList);
    }

    @Factory
    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5, Matcher<? super T> matcher6) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>(6);
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        arrayList.add(matcher4);
        arrayList.add(matcher5);
        arrayList.add(matcher6);
        return AllOf.allOf(arrayList);
    }

    @Factory
    public static /* varargs */ <T> Matcher<T> allOf(Matcher<? super T> ... arrmatcher) {
        return AllOf.allOf(Arrays.asList(arrmatcher));
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("(", " and ", ")", this.matchers);
    }

    @Override
    public boolean matches(Object object, Description description) {
        for (Matcher<T> matcher : this.matchers) {
            if (matcher.matches(object)) continue;
            description.appendDescriptionOf(matcher).appendText(" ");
            matcher.describeMismatch(object, description);
            return false;
        }
        return true;
    }
}

