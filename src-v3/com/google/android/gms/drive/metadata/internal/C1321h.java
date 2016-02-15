package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.drive.metadata.C0810a;
import java.util.Collection;

/* renamed from: com.google.android.gms.drive.metadata.internal.h */
public abstract class C1321h<T extends Parcelable> extends C0810a<T> {
    public C1321h(String str, int i) {
        super(str, i);
    }

    public C1321h(String str, Collection<String> collection, int i) {
        super(str, collection, i);
    }

    protected void m2696a(Bundle bundle, T t) {
        bundle.putParcelable(getName(), t);
    }

    protected /* synthetic */ Object m2698e(Bundle bundle) {
        return m2699k(bundle);
    }

    protected T m2699k(Bundle bundle) {
        return bundle.getParcelable(getName());
    }
}
