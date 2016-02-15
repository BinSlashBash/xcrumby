/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterator<T>
implements Iterator<T> {
    private static final EmptyIterator<?> instance = new EmptyIterator<T>();

    public static <T> Iterator<T> instance() {
        return instance;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

