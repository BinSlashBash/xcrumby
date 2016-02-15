/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.beans.PropertyUtil;

public class HasProperty<T>
extends TypeSafeMatcher<T> {
    private final String propertyName;

    public HasProperty(String string2) {
        this.propertyName = string2;
    }

    @Factory
    public static <T> Matcher<T> hasProperty(String string2) {
        return new HasProperty<T>(string2);
    }

    @Override
    public void describeMismatchSafely(T t2, Description description) {
        description.appendText("no ").appendValue(this.propertyName).appendText(" in ").appendValue(t2);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(this.propertyName).appendText(")");
    }

    @Override
    public boolean matchesSafely(T object) {
        boolean bl2 = false;
        try {
            object = PropertyUtil.getPropertyDescriptor(this.propertyName, object);
            if (object != null) {
                bl2 = true;
            }
            return bl2;
        }
        catch (IllegalArgumentException var1_2) {
            return false;
        }
    }
}

