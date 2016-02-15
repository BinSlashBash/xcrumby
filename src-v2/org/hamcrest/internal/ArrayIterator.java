/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.internal;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayIterator
implements Iterator<Object> {
    private final Object array;
    private int currentIndex = 0;

    public ArrayIterator(Object object) {
        if (!object.getClass().isArray()) {
            throw new IllegalArgumentException("not an array");
        }
        this.array = object;
    }

    @Override
    public boolean hasNext() {
        if (this.currentIndex < Array.getLength(this.array)) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        Object object = this.array;
        int n2 = this.currentIndex;
        this.currentIndex = n2 + 1;
        return Array.get(object, n2);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("cannot remove items from an array");
    }
}

