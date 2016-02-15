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
import com.google.android.gms.drive.internal.TrashResourceRequest;

public class am
implements Parcelable.Creator<TrashResourceRequest> {
    static void a(TrashResourceRequest trashResourceRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, trashResourceRequest.xH);
        b.a(parcel, 2, trashResourceRequest.EV, n2, false);
        b.F(parcel, n3);
    }

    public TrashResourceRequest Z(Parcel parcel) {
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
        return new TrashResourceRequest(n3, driveId);
    }

    public TrashResourceRequest[] aD(int n2) {
        return new TrashResourceRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.Z(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aD(n2);
    }
}

