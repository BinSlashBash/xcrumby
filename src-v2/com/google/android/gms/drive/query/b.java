/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.internal.FieldWithSortOrder;
import com.google.android.gms.drive.query.internal.c;
import java.util.ArrayList;
import java.util.List;

public class b
implements Parcelable.Creator<SortOrder> {
    static void a(SortOrder sortOrder, Parcel parcel, int n2) {
        n2 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, sortOrder.xH);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 1, sortOrder.GF, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n2);
    }

    public SortOrder[] aH(int n2) {
        return new SortOrder[n2];
    }

    public SortOrder ad(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList arrayList = null;
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
            arrayList = a.c(parcel, n4, FieldWithSortOrder.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new SortOrder(n3, arrayList);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ad(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aH(n2);
    }
}

