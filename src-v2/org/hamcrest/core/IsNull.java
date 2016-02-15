/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNot;

public class IsNull<T>
extends BaseMatcher<T> {
    @Factory
    public static Matcher<Object> notNullValue() {
        return IsNot.not(IsNull.nullValue());
    }

    @Factory
    public static <T> Matcher<T> notNullValue(Class<T> class_) {
        return IsNot.not(IsNull.nullValue(class_));
    }

    @Factory
    public static Matcher<Object> nullValue() {
        return new IsNull<Object>();
    }

    @Factory
    public static <T> Matcher<T> nullValue(Class<T> class_) {
        return new IsNull<T>();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("null");
    }

    @Override
    public boolean matches(Object object) {
        if (object == null) {
            return true;
        }
        return false;
    }
}

