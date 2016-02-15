package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.fq;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.google.android.gms.drive.metadata.a */
public abstract class C0810a<T> implements MetadataField<T> {
    private final String FM;
    private final Set<String> FN;
    private final int FO;

    protected C0810a(String str, int i) {
        this.FM = (String) fq.m982b((Object) str, (Object) "fieldName");
        this.FN = Collections.singleton(str);
        this.FO = i;
    }

    protected C0810a(String str, Collection<String> collection, int i) {
        this.FM = (String) fq.m982b((Object) str, (Object) "fieldName");
        this.FN = Collections.unmodifiableSet(new HashSet(collection));
        this.FO = i;
    }

    public final T m1735a(DataHolder dataHolder, int i, int i2) {
        for (String hasNull : this.FN) {
            if (dataHolder.hasNull(hasNull, i, i2)) {
                return null;
            }
        }
        return m1739b(dataHolder, i, i2);
    }

    protected abstract void m1736a(Bundle bundle, T t);

    public final void m1737a(DataHolder dataHolder, MetadataBundle metadataBundle, int i, int i2) {
        fq.m982b((Object) dataHolder, (Object) "dataHolder");
        fq.m982b((Object) metadataBundle, (Object) "bundle");
        metadataBundle.m1745b(this, m1735a(dataHolder, i, i2));
    }

    public final void m1738a(T t, Bundle bundle) {
        fq.m982b((Object) bundle, (Object) "bundle");
        if (t == null) {
            bundle.putString(getName(), null);
        } else {
            m1736a(bundle, (Object) t);
        }
    }

    protected abstract T m1739b(DataHolder dataHolder, int i, int i2);

    public final T m1740d(Bundle bundle) {
        fq.m982b((Object) bundle, (Object) "bundle");
        return bundle.get(getName()) != null ? m1741e(bundle) : null;
    }

    protected abstract T m1741e(Bundle bundle);

    public final Collection<String> fR() {
        return this.FN;
    }

    public final String getName() {
        return this.FM;
    }

    public String toString() {
        return this.FM;
    }
}
