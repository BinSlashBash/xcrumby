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
import com.google.android.gms.drive.query.internal.FieldWithSortOrder;

public class c
implements Parcelable.Creator<FieldWithSortOrder> {
    static void a(FieldWithSortOrder fieldWithSortOrder, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1000, fieldWithSortOrder.xH);
        b.a(parcel, 1, fieldWithSortOrder.FM, false);
        b.a(parcel, 2, fieldWithSortOrder.GJ);
        b.F(parcel, n2);
    }

    public FieldWithSortOrder[] aK(int n2) {
        return new FieldWithSortOrder[n2];
    }

    public FieldWithSortOrder ag(Parcel parcel) {
        boolean bl2 = false;
        int n2 = a.o(parcel);
        String string2 = null;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block5;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block5;
                }
                case 1: {
                    string2 = a.n(parcel, n4);
                    continue block5;
                }
                case 2: 
            }
            bl2 = a.c(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new FieldWithSortOrder(n3, string2, bl2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ag(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aK(n2);
    }
}

