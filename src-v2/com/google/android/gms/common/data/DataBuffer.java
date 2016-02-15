/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.a;
import java.util.Iterator;

public abstract class DataBuffer<T>
implements Iterable<T> {
    protected final DataHolder BB;

    protected DataBuffer(DataHolder dataHolder) {
        this.BB = dataHolder;
        if (this.BB != null) {
            this.BB.c(this);
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

    public abstract T get(int var1);

    public int getCount() {
        if (this.BB == null) {
            return 0;
        }
        return this.BB.getCount();
    }

    public Bundle getMetadata() {
        return this.BB.getMetadata();
    }

    public boolean isClosed() {
        if (this.BB == null) {
            return true;
        }
        return this.BB.isClosed();
    }

    @Override
    public Iterator<T> iterator() {
        return new a<T>(this);
    }
}

