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
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.internal.OnSyncMoreResponse;

public class ag
implements Parcelable.Creator<OnSyncMoreResponse> {
    static void a(OnSyncMoreResponse onSyncMoreResponse, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, onSyncMoreResponse.xH);
        b.a(parcel, 2, onSyncMoreResponse.Fg);
        b.F(parcel, n2);
    }

    public OnSyncMoreResponse U(Parcel parcel) {
        boolean bl2 = false;
        int n2 = a.o(parcel);
        int n3 = 0;
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
            bl2 = a.c(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new OnSyncMoreResponse(n3, bl2);
    }

    public OnSyncMoreResponse[] ay(int n2) {
        return new OnSyncMoreResponse[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.U(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ay(n2);
    }
}

