/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsAnything<T>
extends BaseMatcher<T> {
    private final String message;

    public IsAnything() {
        this("ANYTHING");
    }

    public IsAnything(String string2) {
        this.message = string2;
    }

    @Factory
    public static Matcher<Object> anything() {
        return new IsAnything<Object>();
    }

    @Factory
    public static Matcher<Object> anything(String string2) {
        return new IsAnything<Object>(string2);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.message);
    }

    @Override
    public boolean matches(Object object) {
        return true;
    }
}

