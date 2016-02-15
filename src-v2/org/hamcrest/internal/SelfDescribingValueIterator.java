/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.internal;

import java.util.Iterator;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

public class SelfDescribingValueIterator<T>
implements Iterator<SelfDescribing> {
    private Iterator<T> values;

    public SelfDescribingValueIterator(Iterator<T> iterator) {
        this.values = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.values.hasNext();
    }

    @Override
    public SelfDescribing next() {
        return new SelfDescribingValue<T>(this.values.next());
    }

    @Override
    public void remove() {
        this.values.remove();
    }
}

