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
import com.google.android.gms.drive.internal.CloseContentsRequest;

public class e
implements Parcelable.Creator<CloseContentsRequest> {
    static void a(CloseContentsRequest closeContentsRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, closeContentsRequest.xH);
        b.a(parcel, 2, closeContentsRequest.EX, n2, false);
        b.a(parcel, 3, closeContentsRequest.EY, false);
        b.F(parcel, n3);
    }

    public CloseContentsRequest F(Parcel parcel) {
        Boolean bl2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        Contents contents = null;
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
                    contents = a.a(parcel, n4, Contents.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            bl2 = a.d(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new CloseContentsRequest(n3, contents, bl2);
    }

    public CloseContentsRequest[] aj(int n2) {
        return new CloseContentsRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.F(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aj(n2);
    }
}

