/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public abstract class BaseMatcher<T>
implements Matcher<T> {
    @Deprecated
    @Override
    public final void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
    }

    @Override
    public void describeMismatch(Object object, Description description) {
        description.appendText("was ").appendValue(object);
    }

    public String toString() {
        return StringDescription.toString(this);
    }
}

