/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.Operator;
import com.google.android.gms.drive.query.internal.a;
import com.google.android.gms.drive.query.internal.e;

public class ComparisonFilter<T>
implements SafeParcelable,
Filter {
    public static final a CREATOR = new a();
    final Operator GG;
    final MetadataBundle GH;
    final MetadataField<T> GI;
    final int xH;

    ComparisonFilter(int n2, Operator operator, MetadataBundle metadataBundle) {
        this.xH = n2;
        this.GG = operator;
        this.GH = metadataBundle;
        this.GI = e.b(metadataBundle);
    }

    public ComparisonFilter(Operator operator, SearchableMetadataField<T> searchableMetadataField, T t2) {
        this(1, operator, MetadataBundle.a(searchableMetadataField, t2));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }
}

