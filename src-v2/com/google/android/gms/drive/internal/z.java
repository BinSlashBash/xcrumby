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
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.internal.OnContentsResponse;

public class z
implements Parcelable.Creator<OnContentsResponse> {
    static void a(OnContentsResponse onContentsResponse, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, onContentsResponse.xH);
        b.a(parcel, 2, onContentsResponse.EA, n2, false);
        b.F(parcel, n3);
    }

    public OnContentsResponse N(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        Contents contents = null;
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
            contents = a.a(parcel, n4, Contents.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new OnContentsResponse(n3, contents);
    }

    public OnContentsResponse[] ar(int n2) {
        return new OnContentsResponse[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.N(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ar(n2);
    }
}

