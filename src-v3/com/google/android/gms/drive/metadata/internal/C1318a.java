package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.C0810a;

/* renamed from: com.google.android.gms.drive.metadata.internal.a */
public class C1318a extends C0810a<Boolean> {
    public C1318a(String str, int i) {
        super(str, i);
    }

    protected void m2678a(Bundle bundle, Boolean bool) {
        bundle.putBoolean(getName(), bool.booleanValue());
    }

    protected /* synthetic */ Object m2680b(DataHolder dataHolder, int i, int i2) {
        return m2681d(dataHolder, i, i2);
    }

    protected Boolean m2681d(DataHolder dataHolder, int i, int i2) {
        return Boolean.valueOf(dataHolder.getBoolean(getName(), i, i2));
    }

    protected /* synthetic */ Object m2682e(Bundle bundle) {
        return m2683f(bundle);
    }

    protected Boolean m2683f(Bundle bundle) {
        return Boolean.valueOf(bundle.getBoolean(getName()));
    }
}
