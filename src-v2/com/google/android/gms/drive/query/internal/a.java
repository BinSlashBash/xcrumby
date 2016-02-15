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
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.query.internal.ComparisonFilter;
import com.google.android.gms.drive.query.internal.Operator;

public class a
implements Parcelable.Creator<ComparisonFilter> {
    static void a(ComparisonFilter comparisonFilter, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1000, comparisonFilter.xH);
        b.a(parcel, 1, comparisonFilter.GG, n2, false);
        b.a(parcel, 2, comparisonFilter.GH, n2, false);
        b.F(parcel, n3);
    }

    public ComparisonFilter[] aI(int n2) {
        return new ComparisonFilter[n2];
    }

    public ComparisonFilter ae(Parcel parcel) {
        MetadataBundle metadataBundle = null;
        int n2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n3 = 0;
        Operator operator = null;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n4);
                    continue block5;
                }
                case 1000: {
                    n3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n4);
                    continue block5;
                }
                case 1: {
                    operator = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, Operator.CREATOR);
                    continue block5;
                }
                case 2: 
            }
            metadataBundle = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, MetadataBundle.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ComparisonFilter(n3, operator, metadataBundle);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ae(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aI(n2);
    }
}

