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
import com.google.android.gms.drive.internal.OpenContentsRequest;

public class ah
implements Parcelable.Creator<OpenContentsRequest> {
    static void a(OpenContentsRequest openContentsRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, openContentsRequest.xH);
        b.a(parcel, 2, openContentsRequest.EV, n2, false);
        b.c(parcel, 3, openContentsRequest.Ev);
        b.F(parcel, n3);
    }

    public OpenContentsRequest V(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        DriveId driveId = null;
        int n4 = 0;
        block5 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block5;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block5;
                }
                case 2: {
                    driveId = a.a(parcel, n5, DriveId.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            n2 = a.g(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new OpenContentsRequest(n4, driveId, n2);
    }

    public OpenContentsRequest[] az(int n2) {
        return new OpenContentsRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.V(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.az(n2);
    }
}

