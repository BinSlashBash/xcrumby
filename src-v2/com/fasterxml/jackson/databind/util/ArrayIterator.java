/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T>
implements Iterator<T>,
Iterable<T> {
    private final T[] _a;
    private int _index;

    public ArrayIterator(T[] arrT) {
        this._a = arrT;
        this._index = 0;
    }

    @Override
    public boolean hasNext() {
        if (this._index < this._a.length) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public T next() {
        if (this._index >= this._a.length) {
            throw new NoSuchElementException();
        }
        T[] arrT = this._a;
        int n2 = this._index;
        this._index = n2 + 1;
        return arrT[n2];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

