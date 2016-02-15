/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.internal;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class SelfDescribingValue<T>
implements SelfDescribing {
    private T value;

    public SelfDescribingValue(T t2) {
        this.value = t2;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(this.value);
    }
}

