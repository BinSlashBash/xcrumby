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

public class d
extends a<Integer> {
    public d(String string2, int n2) {
        super(string2, n2);
    }

    @Override
    protected void a(Bundle bundle, Integer n2) {
        bundle.putInt(this.getName(), n2.intValue());
    }

    @Override
    protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
        return this.f(dataHolder, n2, n3);
    }

    @Override
    protected /* synthetic */ Object e(Bundle bundle) {
        return this.h(bundle);
    }

    protected Integer f(DataHolder dataHolder, int n2, int n3) {
        return dataHolder.getInteger(this.getName(), n2, n3);
    }

    protected Integer h(Bundle bundle) {
        return bundle.getInt(this.getName());
    }
}

