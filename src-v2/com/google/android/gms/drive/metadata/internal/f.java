/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class f
implements Parcelable.Creator<MetadataBundle> {
    static void a(MetadataBundle metadataBundle, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, metadataBundle.xH);
        b.a(parcel, 2, metadataBundle.FQ, false);
        b.F(parcel, n2);
    }

    public MetadataBundle[] aF(int n2) {
        return new MetadataBundle[n2];
    }

    public MetadataBundle ab(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        Bundle bundle = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            bundle = a.p(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new MetadataBundle(n3, bundle);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ab(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aF(n2);
    }
}

