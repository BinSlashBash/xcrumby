/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.internal.fq;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class a<T>
implements Iterator<T> {
    private int BC;
    private final DataBuffer<T> mDataBuffer;

    public a(DataBuffer<T> dataBuffer) {
        this.mDataBuffer = fq.f(dataBuffer);
        this.BC = -1;
    }

    @Override
    public boolean hasNext() {
        if (this.BC < this.mDataBuffer.getCount() - 1) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        int n2;
        if (!this.hasNext()) {
            throw new NoSuchElementException("Cannot advance the iterator beyond " + this.BC);
        }
        DataBuffer<T> dataBuffer = this.mDataBuffer;
        this.BC = n2 = this.BC + 1;
        return dataBuffer.get(n2);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}

