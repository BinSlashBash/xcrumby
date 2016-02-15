/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public abstract class CustomMatcher<T>
extends BaseMatcher<T> {
    private final String fixedDescription;

    public CustomMatcher(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Description should be non null!");
        }
        this.fixedDescription = string2;
    }

    @Override
    public final void describeTo(Description description) {
        description.appendText(this.fixedDescription);
    }
}

