/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.a;

public class e
extends a<Long> {
    public e(String string2, int n2) {
        super(string2, n2);
    }

    @Override
    protected void a(Bundle bundle, Long l2) {
        bundle.putLong(this.getName(), l2.longValue());
    }

    @Override
    protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
        return this.g(dataHolder, n2, n3);
    }

    @Override
    protected /* synthetic */ Object e(Bundle bundle) {
        return this.i(bundle);
    }

    protected Long g(DataHolder dataHolder, int n2, int n3) {
        return dataHolder.getLong(this.getName(), n2, n3);
    }

    protected Long i(Bundle bundle) {
        return bundle.getLong(this.getName());
    }
}

