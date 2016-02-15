/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsSame<T>
extends BaseMatcher<T> {
    private final T object;

    public IsSame(T t2) {
        this.object = t2;
    }

    @Factory
    public static <T> Matcher<T> sameInstance(T t2) {
        return new IsSame<T>(t2);
    }

    @Factory
    public static <T> Matcher<T> theInstance(T t2) {
        return new IsSame<T>(t2);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("sameInstance(").appendValue(this.object).appendText(")");
    }

    @Override
    public boolean matches(Object object) {
        if (object == this.object) {
            return true;
        }
        return false;
    }
}

