package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class AnyOf<T> extends ShortcutCombination<T> {
    public /* bridge */ /* synthetic */ void describeTo(Description x0, String x1) {
        super.describeTo(x0, x1);
    }

    public AnyOf(Iterable<Matcher<? super T>> matchers) {
        super(matchers);
    }

    public boolean matches(Object o) {
        return matches(o, true);
    }

    public void describeTo(Description description) {
        describeTo(description, "or");
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> matchers) {
        return new AnyOf(matchers);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<? super T>... matchers) {
        return anyOf(Arrays.asList(matchers));
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second) {
        Iterable matchers = new ArrayList();
        matchers.add(first);
        matchers.add(second);
        return anyOf(matchers);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third) {
        Iterable matchers = new ArrayList();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        return anyOf(matchers);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
        Iterable matchers = new ArrayList();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(fourth);
        return anyOf(matchers);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
        Iterable matchers = new ArrayList();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(fourth);
        matchers.add(fifth);
        return anyOf(matchers);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
        Iterable matchers = new ArrayList();
        matchers.add(first);
        matchers.add(second);
        matchers.add(third);
        matchers.add(fourth);
        matchers.add(fifth);
        matchers.add(sixth);
        return anyOf(matchers);
    }
}
