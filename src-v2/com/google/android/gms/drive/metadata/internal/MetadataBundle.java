/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.c;
import com.google.android.gms.drive.metadata.internal.f;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class MetadataBundle
implements SafeParcelable {
    public static final Parcelable.Creator<MetadataBundle> CREATOR = new f();
    final Bundle FQ;
    final int xH;

    MetadataBundle(int n2, Bundle iterator) {
        this.xH = n2;
        this.FQ = fq.f(iterator);
        this.FQ.setClassLoader(this.getClass().getClassLoader());
        iterator = new ArrayList();
        for (String string2 : this.FQ.keySet()) {
            if (c.ax(string2) != null) continue;
            iterator.add(string2);
            Log.w((String)"MetadataBundle", (String)("Ignored unknown metadata field in bundle: " + string2));
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            String string3 = (String)iterator.next();
            this.FQ.remove(string3);
        }
    }

    private MetadataBundle(Bundle bundle) {
        this(1, bundle);
    }

    public static <T> MetadataBundle a(MetadataField<T> metadataField, T t2) {
        MetadataBundle metadataBundle = MetadataBundle.fT();
        metadataBundle.b(metadataField, t2);
        return metadataBundle;
    }

    public static MetadataBundle a(MetadataBundle metadataBundle) {
        return new MetadataBundle(new Bundle(metadataBundle.FQ));
    }

    public static MetadataBundle fT() {
        return new MetadataBundle(new Bundle());
    }

    public <T> T a(MetadataField<T> metadataField) {
        return metadataField.d(this.FQ);
    }

    public <T> void b(MetadataField<T> metadataField, T t2) {
        if (c.ax(metadataField.getName()) == null) {
            throw new IllegalArgumentException("Unregistered field: " + metadataField.getName());
        }
        metadataField.a(t2, this.FQ);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MetadataBundle)) {
            return false;
        }
        object = (MetadataBundle)object;
        Object object2 = this.FQ.keySet();
        if (!object2.equals(object.FQ.keySet())) {
            return false;
        }
        object2 = object2.iterator();
        while (object2.hasNext()) {
            String string2 = (String)object2.next();
            if (fo.equal(this.FQ.get(string2), object.FQ.get(string2))) continue;
            return false;
        }
        return true;
    }

    public Set<MetadataField<?>> fU() {
        HashSet hashSet = new HashSet();
        Iterator iterator = this.FQ.keySet().iterator();
        while (iterator.hasNext()) {
            hashSet.add(c.ax((String)iterator.next()));
        }
        return hashSet;
    }

    public int hashCode() {
        Iterator iterator = this.FQ.keySet().iterator();
        int n2 = 1;
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            n2 = this.FQ.get(string2).hashCode() + n2 * 31;
        }
        return n2;
    }

    public String toString() {
        return "MetadataBundle [values=" + (Object)this.FQ + "]";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        f.a(this, parcel, n2);
    }
}

