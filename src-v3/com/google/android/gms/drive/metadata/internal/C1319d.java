package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.C0810a;

/* renamed from: com.google.android.gms.drive.metadata.internal.d */
public class C1319d extends C0810a<Integer> {
    public C1319d(String str, int i) {
        super(str, i);
    }

    protected void m2684a(Bundle bundle, Integer num) {
        bundle.putInt(getName(), num.intValue());
    }

    protected /* synthetic */ Object m2686b(DataHolder dataHolder, int i, int i2) {
        return m2688f(dataHolder, i, i2);
    }

    protected /* synthetic */ Object m2687e(Bundle bundle) {
        return m2689h(bundle);
    }

    protected Integer m2688f(DataHolder dataHolder, int i, int i2) {
        return Integer.valueOf(dataHolder.getInteger(getName(), i, i2));
    }

    protected Integer m2689h(Bundle bundle) {
        return Integer.valueOf(bundle.getInt(getName()));
    }
}
