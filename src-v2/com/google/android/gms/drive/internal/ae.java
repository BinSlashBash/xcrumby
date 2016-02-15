/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.internal.OnListParentsResponse;

public class ae
implements Parcelable.Creator<OnListParentsResponse> {
    static void a(OnListParentsResponse onListParentsResponse, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, onListParentsResponse.xH);
        b.a(parcel, 2, onListParentsResponse.FK, n2, false);
        b.F(parcel, n3);
    }

    public OnListParentsResponse S(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        DataHolder dataHolder = null;
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
            dataHolder = (DataHolder)a.a(parcel, n4, DataHolder.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new OnListParentsResponse(n3, dataHolder);
    }

    public OnListParentsResponse[] aw(int n2) {
        return new OnListParentsResponse[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.S(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aw(n2);
    }
}

