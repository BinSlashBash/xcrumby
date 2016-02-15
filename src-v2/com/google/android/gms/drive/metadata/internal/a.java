/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;

public class a
extends com.google.android.gms.drive.metadata.a<Boolean> {
    public a(String string2, int n2) {
        super(string2, n2);
    }

    @Override
    protected void a(Bundle bundle, Boolean bl2) {
        bundle.putBoolean(this.getName(), bl2.booleanValue());
    }

    @Override
    protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
        return this.d(dataHolder, n2, n3);
    }

    protected Boolean d(DataHolder dataHolder, int n2, int n3) {
        return dataHolder.getBoolean(this.getName(), n2, n3);
    }

    @Override
    protected /* synthetic */ Object e(Bundle bundle) {
        return this.f(bundle);
    }

    protected Boolean f(Bundle bundle) {
        return bundle.getBoolean(this.getName());
    }
}

