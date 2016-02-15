/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public abstract class DiagnosingMatcher<T>
extends BaseMatcher<T> {
    @Override
    public final void describeMismatch(Object object, Description description) {
        this.matches(object, description);
    }

    @Override
    public final boolean matches(Object object) {
        return this.matches(object, Description.NONE);
    }

    protected abstract boolean matches(Object var1, Description var2);
}

