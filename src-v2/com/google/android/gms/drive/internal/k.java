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
import com.google.android.gms.drive.internal.DisconnectRequest;

public class k
implements Parcelable.Creator<DisconnectRequest> {
    static void a(DisconnectRequest disconnectRequest, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, disconnectRequest.xH);
        b.F(parcel, n2);
    }

    public DisconnectRequest K(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        block3 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block3;
                }
                case 1: 
            }
            n3 = a.g(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new DisconnectRequest(n3);
    }

    public DisconnectRequest[] ao(int n2) {
        return new DisconnectRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.K(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ao(n2);
    }
}

