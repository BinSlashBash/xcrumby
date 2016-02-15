package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.drive.metadata.C1316b;
import java.util.ArrayList;
import java.util.Collection;

/* renamed from: com.google.android.gms.drive.metadata.internal.g */
public class C1482g<T extends Parcelable> extends C1316b<T> {
    public C1482g(String str, int i) {
        super(str, i);
    }

    protected void m3283a(Bundle bundle, Collection<T> collection) {
        bundle.putParcelableArrayList(getName(), new ArrayList(collection));
    }

    protected /* synthetic */ Object m3284e(Bundle bundle) {
        return m3285j(bundle);
    }

    protected Collection<T> m3285j(Bundle bundle) {
        return bundle.getParcelableArrayList(getName());
    }
}
