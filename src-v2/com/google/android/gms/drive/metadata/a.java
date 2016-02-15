/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.fq;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class a<T>
implements MetadataField<T> {
    private final String FM;
    private final Set<String> FN;
    private final int FO;

    protected a(String string2, int n2) {
        this.FM = fq.b(string2, (Object)"fieldName");
        this.FN = Collections.singleton(string2);
        this.FO = n2;
    }

    protected a(String string2, Collection<String> collection, int n2) {
        this.FM = fq.b(string2, (Object)"fieldName");
        this.FN = Collections.unmodifiableSet(new HashSet<String>(collection));
        this.FO = n2;
    }

    @Override
    public final T a(DataHolder dataHolder, int n2, int n3) {
        Iterator<String> iterator = this.FN.iterator();
        while (iterator.hasNext()) {
            if (!dataHolder.hasNull(iterator.next(), n2, n3)) continue;
            return null;
        }
        return this.b(dataHolder, n2, n3);
    }

    protected abstract void a(Bundle var1, T var2);

    @Override
    public final void a(DataHolder dataHolder, MetadataBundle metadataBundle, int n2, int n3) {
        fq.b(dataHolder, (Object)"dataHolder");
        fq.b(metadataBundle, (Object)"bundle");
        metadataBundle.b(this, this.a(dataHolder, n2, n3));
    }

    @Override
    public final void a(T t2, Bundle bundle) {
        fq.b(bundle, (Object)"bundle");
        if (t2 == null) {
            bundle.putString(this.getName(), null);
            return;
        }
        this.a(bundle, t2);
    }

    protected abstract T b(DataHolder var1, int var2, int var3);

    @Override
    public final T d(Bundle bundle) {
        fq.b(bundle, (Object)"bundle");
        if (bundle.get(this.getName()) != null) {
            return this.e(bundle);
        }
        return null;
    }

    protected abstract T e(Bundle var1);

    @Override
    public final Collection<String> fR() {
        return this.FN;
    }

    @Override
    public final String getName() {
        return this.FM;
    }

    public String toString() {
        return this.FM;
    }
}

