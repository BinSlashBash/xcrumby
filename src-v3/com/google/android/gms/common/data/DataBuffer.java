package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Iterator;

public abstract class DataBuffer<T> implements Iterable<T> {
    protected final DataHolder BB;

    protected DataBuffer(DataHolder dataHolder) {
        this.BB = dataHolder;
        if (this.BB != null) {
            this.BB.m1688c(this);
        }
    }

    public void close() {
        if (this.BB != null) {
            this.BB.close();
        }
    }

    public int describeContents() {
        return 0;
    }

    public abstract T get(int i);

    public int getCount() {
        return this.BB == null ? 0 : this.BB.getCount();
    }

    public Bundle getMetadata() {
        return this.BB.getMetadata();
    }

    public boolean isClosed() {
        return this.BB == null ? true : this.BB.isClosed();
    }

    public Iterator<T> iterator() {
        return new C0250a(this);
    }
}
