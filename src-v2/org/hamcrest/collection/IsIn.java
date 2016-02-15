/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.collection;

import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsIn<T>
extends BaseMatcher<T> {
    private final Collection<T> collection;

    public IsIn(Collection<T> collection) {
        this.collection = collection;
    }

    public IsIn(T[] arrT) {
        this.collection = Arrays.asList(arrT);
    }

    @Factory
    public static <T> Matcher<T> isIn(Collection<T> collection) {
        return new IsIn<T>(collection);
    }

    @Factory
    public static <T> Matcher<T> isIn(T[] arrT) {
        return new IsIn<T>(arrT);
    }

    @Factory
    public static /* varargs */ <T> Matcher<T> isOneOf(T ... arrT) {
        return IsIn.isIn(arrT);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("one of ");
        description.appendValueList("{", ", ", "}", this.collection);
    }

    @Override
    public boolean matches(Object object) {
        return this.collection.contains(object);
    }
}

