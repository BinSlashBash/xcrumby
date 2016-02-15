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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.ListParentsRequest;

public class x
implements Parcelable.Creator<ListParentsRequest> {
    static void a(ListParentsRequest listParentsRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, listParentsRequest.xH);
        b.a(parcel, 2, listParentsRequest.FB, n2, false);
        b.F(parcel, n3);
    }

    public ListParentsRequest M(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        DriveId driveId = null;
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
            driveId = a.a(parcel, n4, DriveId.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ListParentsRequest(n3, driveId);
    }

    public ListParentsRequest[] aq(int n2) {
        return new ListParentsRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.M(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aq(n2);
    }
}

