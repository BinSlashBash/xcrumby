/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.metadata;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.a;
import java.util.Collection;

public abstract class b<T>
extends a<Collection<T>> {
    protected b(String string2, int n2) {
        super(string2, n2);
    }

    @Override
    protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
        return this.c(dataHolder, n2, n3);
    }

    protected Collection<T> c(DataHolder dataHolder, int n2, int n3) {
        throw new UnsupportedOperationException("Cannot read collections from a dataHolder.");
    }
}

