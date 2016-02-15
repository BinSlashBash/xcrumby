package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.C0810a;

/* renamed from: com.google.android.gms.drive.metadata.internal.j */
public class C1322j extends C0810a<String> {
    public C1322j(String str, int i) {
        super(str, i);
    }

    protected void m2701a(Bundle bundle, String str) {
        bundle.putString(getName(), str);
    }

    protected /* synthetic */ Object m2702b(DataHolder dataHolder, int i, int i2) {
        return m2704h(dataHolder, i, i2);
    }

    protected /* synthetic */ Object m2703e(Bundle bundle) {
        return m2705l(bundle);
    }

    protected String m2704h(DataHolder dataHolder, int i, int i2) {
        return dataHolder.getString(getName(), i, i2);
    }

    protected String m2705l(Bundle bundle) {
        return bundle.getString(getName());
    }
}
