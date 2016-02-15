package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.C0810a;

/* renamed from: com.google.android.gms.drive.metadata.internal.e */
public class C1320e extends C0810a<Long> {
    public C1320e(String str, int i) {
        super(str, i);
    }

    protected void m2690a(Bundle bundle, Long l) {
        bundle.putLong(getName(), l.longValue());
    }

    protected /* synthetic */ Object m2692b(DataHolder dataHolder, int i, int i2) {
        return m2694g(dataHolder, i, i2);
    }

    protected /* synthetic */ Object m2693e(Bundle bundle) {
        return m2695i(bundle);
    }

    protected Long m2694g(DataHolder dataHolder, int i, int i2) {
        return Long.valueOf(dataHolder.getLong(getName(), i, i2));
    }

    protected Long m2695i(Bundle bundle) {
        return Long.valueOf(bundle.getLong(getName()));
    }
}
