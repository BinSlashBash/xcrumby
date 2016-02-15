/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.lang.reflect.Array;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsEqual<T>
extends BaseMatcher<T> {
    private final Object expectedValue;

    public IsEqual(T t2) {
        this.expectedValue = t2;
    }

    private static boolean areArrayElementsEqual(Object object, Object object2) {
        for (int i2 = 0; i2 < Array.getLength(object); ++i2) {
            if (IsEqual.areEqual(Array.get(object, i2), Array.get(object2, i2))) continue;
            return false;
        }
        return true;
    }

    private static boolean areArrayLengthsEqual(Object object, Object object2) {
        if (Array.getLength(object) == Array.getLength(object2)) {
            return true;
        }
        return false;
    }

    private static boolean areArraysEqual(Object object, Object object2) {
        if (IsEqual.areArrayLengthsEqual(object, object2) && IsEqual.areArrayElementsEqual(object, object2)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean areEqual(Object object, Object object2) {
        if (object == null) {
            if (object2 == null) return true;
            return false;
        }
        if (object2 == null || !IsEqual.isArray(object)) return object.equals(object2);
        {
            if (!IsEqual.isArray(object2) || !IsEqual.areArraysEqual(object, object2)) return false;
        }
        return true;
    }

    @Factory
    public static <T> Matcher<T> equalTo(T t2) {
        return new IsEqual<T>(t2);
    }

    private static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(this.expectedValue);
    }

    @Override
    public boolean matches(Object object) {
        return IsEqual.areEqual(object, this.expectedValue);
    }
}

