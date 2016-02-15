/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.fb;

public abstract class FilteredDataBuffer<T>
extends DataBuffer<T> {
    protected final DataBuffer<T> mDataBuffer;

    /*
     * Enabled aggressive block sorting
     */
    public FilteredDataBuffer(DataBuffer<T> dataBuffer) {
        super(null);
        fb.d(dataBuffer);
        boolean bl2 = !(dataBuffer instanceof FilteredDataBuffer);
        fb.a(bl2, "Not possible to have nested FilteredDataBuffers.");
        this.mDataBuffer = dataBuffer;
    }

    @Override
    public void close() {
        this.mDataBuffer.close();
    }

    protected abstract int computeRealPosition(int var1);

    @Override
    public T get(int n2) {
        return this.mDataBuffer.get(this.computeRealPosition(n2));
    }

    @Override
    public Bundle getMetadata() {
        return this.mDataBuffer.getMetadata();
    }

    @Override
    public boolean isClosed() {
        return this.mDataBuffer.isClosed();
    }
}

