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
import com.google.android.gms.drive.query.internal.InFilter;

public class f
implements Parcelable.Creator<InFilter> {
    static void a(InFilter inFilter, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1000, inFilter.xH);
        b.a(parcel, 1, inFilter.GH, n2, false);
        b.F(parcel, n3);
    }

    public InFilter[] aM(int n2) {
        return new InFilter[n2];
    }

    public InFilter ai(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        MetadataBundle metadataBundle = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block4;
                }
                case 1: 
            }
            metadataBundle = a.a(parcel, n4, MetadataBundle.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new InFilter(n3, metadataBundle);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ai(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aM(n2);
    }
}

