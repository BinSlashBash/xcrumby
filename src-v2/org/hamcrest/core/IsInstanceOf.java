/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsInstanceOf
extends DiagnosingMatcher<Object> {
    private final Class<?> expectedClass;
    private final Class<?> matchableClass;

    public IsInstanceOf(Class<?> class_) {
        this.expectedClass = class_;
        this.matchableClass = IsInstanceOf.matchableClass(class_);
    }

    @Factory
    public static <T> Matcher<T> any(Class<T> class_) {
        return new IsInstanceOf(class_);
    }

    @Factory
    public static <T> Matcher<T> instanceOf(Class<?> class_) {
        return new IsInstanceOf(class_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Class<?> matchableClass(Class<?> class_) {
        if (Boolean.TYPE.equals(class_)) {
            return Boolean.class;
        }
        if (Byte.TYPE.equals(class_)) {
            return Byte.class;
        }
        if (Character.TYPE.equals(class_)) {
            return Character.class;
        }
        if (Double.TYPE.equals(class_)) {
            return Double.class;
        }
        if (Float.TYPE.equals(class_)) {
            return Float.class;
        }
        if (Integer.TYPE.equals(class_)) {
            return Integer.class;
        }
        if (Long.TYPE.equals(class_)) {
            return Long.class;
        }
        Class class_2 = class_;
        if (!Short.TYPE.equals(class_)) return class_2;
        return Short.class;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an instance of ").appendText(this.expectedClass.getName());
    }

    @Override
    protected boolean matches(Object object, Description description) {
        if (object == null) {
            description.appendText("null");
            return false;
        }
        if (!this.matchableClass.isInstance(object)) {
            description.appendValue(object).appendText(" is a " + object.getClass().getName());
            return false;
        }
        return true;
    }
}

