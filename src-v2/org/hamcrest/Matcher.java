/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public interface Matcher<T>
extends SelfDescribing {
    @Deprecated
    public void _dont_implement_Matcher___instead_extend_BaseMatcher_();

    public void describeMismatch(Object var1, Description var2);

    public boolean matches(Object var1);
}

