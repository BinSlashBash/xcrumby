package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.C1317c;
import java.util.Date;

/* renamed from: com.google.android.gms.drive.metadata.internal.b */
public class C1481b extends C1317c<Date> {
    public C1481b(String str, int i) {
        super(str, i);
    }

    protected void m3277a(Bundle bundle, Date date) {
        bundle.putLong(getName(), date.getTime());
    }

    protected /* synthetic */ Object m3278b(DataHolder dataHolder, int i, int i2) {
        return m3280e(dataHolder, i, i2);
    }

    protected /* synthetic */ Object m3279e(Bundle bundle) {
        return m3281g(bundle);
    }

    protected Date m3280e(DataHolder dataHolder, int i, int i2) {
        return new Date(dataHolder.getLong(getName(), i, i2));
    }

    protected Date m3281g(Bundle bundle) {
        return new Date(bundle.getLong(getName()));
    }
}
