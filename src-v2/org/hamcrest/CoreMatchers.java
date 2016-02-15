/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.CombinableMatcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.Every;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;

public class CoreMatchers {
    public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> iterable) {
        return AllOf.allOf(iterable);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2) {
        return AllOf.allOf(matcher, matcher2);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3) {
        return AllOf.allOf(matcher, matcher2, matcher3);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4) {
        return AllOf.allOf(matcher, matcher2, matcher3, matcher4);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5) {
        return AllOf.allOf(matcher, matcher2, matcher3, matcher4, matcher5);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5, Matcher<? super T> matcher6) {
        return AllOf.allOf(matcher, matcher2, matcher3, matcher4, matcher5, matcher6);
    }

    public static /* varargs */ <T> Matcher<T> allOf(Matcher<? super T> ... arrmatcher) {
        return AllOf.allOf(arrmatcher);
    }

    public static <T> Matcher<T> any(Class<T> class_) {
        return IsInstanceOf.any(class_);
    }

    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> iterable) {
        return AnyOf.anyOf(iterable);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2) {
        return AnyOf.anyOf(matcher, matcher2);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3) {
        return AnyOf.anyOf(matcher, matcher2, matcher3);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4) {
        return AnyOf.anyOf(matcher, matcher2, matcher3, matcher4);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5) {
        return AnyOf.anyOf(matcher, matcher2, matcher3, matcher4, matcher5);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5, Matcher<? super T> matcher6) {
        return AnyOf.anyOf(matcher, matcher2, matcher3, matcher4, matcher5, matcher6);
    }

    public static /* varargs */ <T> AnyOf<T> anyOf(Matcher<? super T> ... arrmatcher) {
        return AnyOf.anyOf(arrmatcher);
    }

    public static Matcher<Object> anything() {
        return IsAnything.anything();
    }

    public static Matcher<Object> anything(String string2) {
        return IsAnything.anything(string2);
    }

    public static <LHS> CombinableMatcher.CombinableBothMatcher<LHS> both(Matcher<? super LHS> matcher) {
        return CombinableMatcher.both(matcher);
    }

    public static Matcher<String> containsString(String string2) {
        return StringContains.containsString(string2);
    }

    public static /* varargs */ <T> Matcher<T> describedAs(String string2, Matcher<T> matcher, Object ... arrobject) {
        return DescribedAs.describedAs(string2, matcher, arrobject);
    }

    public static <LHS> CombinableMatcher.CombinableEitherMatcher<LHS> either(Matcher<? super LHS> matcher) {
        return CombinableMatcher.either(matcher);
    }

    public static Matcher<String> endsWith(String string2) {
        return StringEndsWith.endsWith(string2);
    }

    public static <T> Matcher<T> equalTo(T t2) {
        return IsEqual.equalTo(t2);
    }

    public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> matcher) {
        return Every.everyItem(matcher);
    }

    public static <T> Matcher<Iterable<? super T>> hasItem(T t2) {
        return IsCollectionContaining.hasItem(t2);
    }

    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> matcher) {
        return IsCollectionContaining.hasItem(matcher);
    }

    public static /* varargs */ <T> Matcher<Iterable<T>> hasItems(T ... arrT) {
        return IsCollectionContaining.hasItems(arrT);
    }

    public static /* varargs */ <T> Matcher<Iterable<T>> hasItems(Matcher<? super T> ... arrmatcher) {
        return IsCollectionContaining.hasItems(arrmatcher);
    }

    public static <T> Matcher<T> instanceOf(Class<?> class_) {
        return IsInstanceOf.instanceOf(class_);
    }

    public static <T> Matcher<T> is(Class<T> class_) {
        return Is.is(class_);
    }

    public static <T> Matcher<T> is(T t2) {
        return Is.is(t2);
    }

    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return Is.is(matcher);
    }

    public static <T> Matcher<T> isA(Class<T> class_) {
        return Is.isA(class_);
    }

    public static <T> Matcher<T> not(T t2) {
        return IsNot.not(t2);
    }

    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return IsNot.not(matcher);
    }

    public static Matcher<Object> notNullValue() {
        return IsNull.notNullValue();
    }

    public static <T> Matcher<T> notNullValue(Class<T> class_) {
        return IsNull.notNullValue(class_);
    }

    public static Matcher<Object> nullValue() {
        return IsNull.nullValue();
    }

    public static <T> Matcher<T> nullValue(Class<T> class_) {
        return IsNull.nullValue(class_);
    }

    public static <T> Matcher<T> sameInstance(T t2) {
        return IsSame.sameInstance(t2);
    }

    public static Matcher<String> startsWith(String string2) {
        return StringStartsWith.startsWith(string2);
    }

    public static <T> Matcher<T> theInstance(T t2) {
        return IsSame.theInstance(t2);
    }
}

