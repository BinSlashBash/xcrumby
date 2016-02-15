package com.google.android.gms.drive.metadata;

import com.google.android.gms.common.data.DataHolder;
import java.util.Collection;

/* renamed from: com.google.android.gms.drive.metadata.b */
public abstract class C1316b<T> extends C0810a<Collection<T>> {
    protected C1316b(String str, int i) {
        super(str, i);
    }

    protected /* synthetic */ Object m2676b(DataHolder dataHolder, int i, int i2) {
        return m2677c(dataHolder, i, i2);
    }

    protected Collection<T> m2677c(DataHolder dataHolder, int i, int i2) {
        throw new UnsupportedOperationException("Cannot read collections from a dataHolder.");
    }
}
