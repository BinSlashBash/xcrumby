/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.b;
import com.google.android.gms.drive.query.internal.e;

public class FieldOnlyFilter
implements SafeParcelable,
Filter {
    public static final Parcelable.Creator<FieldOnlyFilter> CREATOR = new b();
    final MetadataBundle GH;
    private final MetadataField<?> GI;
    final int xH;

    FieldOnlyFilter(int n2, MetadataBundle metadataBundle) {
        this.xH = n2;
        this.GH = metadataBundle;
        this.GI = e.b(metadataBundle);
    }

    public FieldOnlyFilter(SearchableMetadataField<?> searchableMetadataField) {
        this(1, MetadataBundle.a(searchableMetadataField, null));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }
}

