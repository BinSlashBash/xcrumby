/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.drive.metadata.a;
import java.util.Collection;

public abstract class h<T extends Parcelable>
extends a<T> {
    public h(String string2, int n2) {
        super(string2, n2);
    }

    public h(String string2, Collection<String> collection, int n2) {
        super(string2, collection, n2);
    }

    @Override
    protected void a(Bundle bundle, T t2) {
        bundle.putParcelable(this.getName(), t2);
    }

    @Override
    protected /* synthetic */ Object e(Bundle bundle) {
        return this.k(bundle);
    }

    protected T k(Bundle bundle) {
        return (T)bundle.getParcelable(this.getName());
    }
}

