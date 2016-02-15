/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.core;

import java.util.Iterator;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

abstract class ShortcutCombination<T>
extends BaseMatcher<T> {
    private final Iterable<Matcher<? super T>> matchers;

    public ShortcutCombination(Iterable<Matcher<? super T>> iterable) {
        this.matchers = iterable;
    }

    @Override
    public abstract void describeTo(Description var1);

    public void describeTo(Description description, String string2) {
        description.appendList("(", " " + string2 + " ", ")", this.matchers);
    }

    @Override
    public abstract boolean matches(Object var1);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean matches(Object object, boolean bl2) {
        Iterator<Matcher<T>> iterator = this.matchers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().matches(object) != bl2) continue;
            return bl2;
        }
        if (bl2) return false;
        return true;
    }
}

