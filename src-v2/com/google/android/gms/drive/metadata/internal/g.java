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
import com.google.android.gms.drive.metadata.b;
import java.util.ArrayList;
import java.util.Collection;

public class g<T extends Parcelable>
extends b<T> {
    public g(String string2, int n2) {
        super(string2, n2);
    }

    @Override
    protected void a(Bundle bundle, Collection<T> collection) {
        bundle.putParcelableArrayList(this.getName(), new ArrayList<T>(collection));
    }

    @Override
    protected /* synthetic */ Object e(Bundle bundle) {
        return this.j(bundle);
    }

    protected Collection<T> j(Bundle bundle) {
        return bundle.getParcelableArrayList(this.getName());
    }
}

