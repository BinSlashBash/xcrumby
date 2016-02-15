/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.b;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.e;
import com.google.android.gms.drive.query.internal.f;
import java.util.Collections;

public class InFilter<T>
implements SafeParcelable,
Filter {
    public static final f CREATOR = new f();
    final MetadataBundle GH;
    private final b<T> GR;
    final int xH;

    InFilter(int n2, MetadataBundle metadataBundle) {
        this.xH = n2;
        this.GH = metadataBundle;
        this.GR = (b)e.b(metadataBundle);
    }

    public InFilter(SearchableCollectionMetadataField<T> searchableCollectionMetadataField, T t2) {
        this(1, MetadataBundle.a(searchableCollectionMetadataField, Collections.singleton(t2)));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        f.a(this, parcel, n2);
    }
}

