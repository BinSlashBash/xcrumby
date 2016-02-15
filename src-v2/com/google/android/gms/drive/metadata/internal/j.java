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

public class j
extends a<String> {
    public j(String string2, int n2) {
        super(string2, n2);
    }

    @Override
    protected void a(Bundle bundle, String string2) {
        bundle.putString(this.getName(), string2);
    }

    @Override
    protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
        return this.h(dataHolder, n2, n3);
    }

    @Override
    protected /* synthetic */ Object e(Bundle bundle) {
        return this.l(bundle);
    }

    protected String h(DataHolder dataHolder, int n2, int n3) {
        return dataHolder.getString(this.getName(), n2, n3);
    }

    protected String l(Bundle bundle) {
        return bundle.getString(this.getName());
    }
}

