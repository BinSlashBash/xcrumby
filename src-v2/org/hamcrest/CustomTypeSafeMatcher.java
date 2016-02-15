/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class CustomTypeSafeMatcher<T>
extends TypeSafeMatcher<T> {
    private final String fixedDescription;

    public CustomTypeSafeMatcher(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Description must be non null!");
        }
        this.fixedDescription = string2;
    }

    @Override
    public final void describeTo(Description description) {
        description.appendText(this.fixedDescription);
    }
}

