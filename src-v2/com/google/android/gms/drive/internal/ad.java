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
import com.google.android.gms.drive.internal.OnListEntriesResponse;

public class ad
implements Parcelable.Creator<OnListEntriesResponse> {
    static void a(OnListEntriesResponse onListEntriesResponse, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, onListEntriesResponse.xH);
        b.a(parcel, 2, onListEntriesResponse.FJ, n2, false);
        b.a(parcel, 3, onListEntriesResponse.Fg);
        b.F(parcel, n3);
    }

    public OnListEntriesResponse R(Parcel parcel) {
        boolean bl2 = false;
        int n2 = a.o(parcel);
        DataHolder dataHolder = null;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block5;
                }
                case 2: {
                    dataHolder = (DataHolder)a.a(parcel, n4, DataHolder.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            bl2 = a.c(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new OnListEntriesResponse(n3, dataHolder, bl2);
    }

    public OnListEntriesResponse[] av(int n2) {
        return new OnListEntriesResponse[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.R(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.av(n2);
    }
}

