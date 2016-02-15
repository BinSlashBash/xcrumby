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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.AuthorizeAccessRequest;

public class b
implements Parcelable.Creator<AuthorizeAccessRequest> {
    static void a(AuthorizeAccessRequest authorizeAccessRequest, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, authorizeAccessRequest.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, authorizeAccessRequest.EU);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, authorizeAccessRequest.Ew, n2, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n3);
    }

    public AuthorizeAccessRequest D(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        long l2 = 0;
        DriveId driveId = null;
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
                    l2 = a.i(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            driveId = a.a(parcel, n4, DriveId.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new AuthorizeAccessRequest(n3, l2, driveId);
    }

    public AuthorizeAccessRequest[] ah(int n2) {
        return new AuthorizeAccessRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.D(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ah(n2);
    }
}

