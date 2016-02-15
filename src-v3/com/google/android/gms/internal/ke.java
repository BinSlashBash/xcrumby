package com.google.android.gms.internal;

import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.C1103d;

public class ke extends C0251b implements C1103d {
    public ke(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public /* synthetic */ Object freeze() {
        return mf();
    }

    public String getId() {
        return getString("asset_id");
    }

    public String mc() {
        return getString("asset_key");
    }

    public C1103d mf() {
        return new kd(this);
    }
}
