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
import com.google.android.gms.drive.internal.CreateFolderRequest;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class i
implements Parcelable.Creator<CreateFolderRequest> {
    static void a(CreateFolderRequest createFolderRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, createFolderRequest.xH);
        b.a(parcel, 2, createFolderRequest.Fa, n2, false);
        b.a(parcel, 3, createFolderRequest.EZ, n2, false);
        b.F(parcel, n3);
    }

    public CreateFolderRequest J(Parcel parcel) {
        MetadataBundle metadataBundle = null;
        int n2 = a.o(parcel);
        int n3 = 0;
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
                    driveId = a.a(parcel, n4, DriveId.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            metadataBundle = a.a(parcel, n4, MetadataBundle.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new CreateFolderRequest(n3, driveId, metadataBundle);
    }

    public CreateFolderRequest[] an(int n2) {
        return new CreateFolderRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.J(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.an(n2);
    }
}

