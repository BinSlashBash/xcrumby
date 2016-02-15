/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.ShortcutCombination;

public class AnyOf<T>
extends ShortcutCombination<T> {
    public AnyOf(Iterable<Matcher<? super T>> iterable) {
        super(iterable);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> iterable) {
        return new AnyOf<T>(iterable);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>();
        arrayList.add(matcher);
        arrayList.add(matcher2);
        return AnyOf.anyOf(arrayList);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>();
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        return AnyOf.anyOf(arrayList);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>();
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        arrayList.add(matcher4);
        return AnyOf.anyOf(arrayList);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>();
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        arrayList.add(matcher4);
        arrayList.add(matcher5);
        return AnyOf.anyOf(arrayList);
    }

    @Factory
    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5, Matcher<? super T> matcher6) {
        ArrayList<Matcher<T>> arrayList = new ArrayList<Matcher<T>>();
        arrayList.add(matcher);
        arrayList.add(matcher2);
        arrayList.add(matcher3);
        arrayList.add(matcher4);
        arrayList.add(matcher5);
        arrayList.add(matcher6);
        return AnyOf.anyOf(arrayList);
    }

    @Factory
    public static /* varargs */ <T> AnyOf<T> anyOf(Matcher<? super T> ... arrmatcher) {
        return AnyOf.anyOf(Arrays.asList(arrmatcher));
    }

    @Override
    public void describeTo(Description description) {
        this.describeTo(description, "or");
    }

    @Override
    public boolean matches(Object object) {
        return this.matches(object, true);
    }
}

