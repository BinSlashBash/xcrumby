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
import com.google.android.gms.drive.internal.UpdateMetadataRequest;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class an
implements Parcelable.Creator<UpdateMetadataRequest> {
    static void a(UpdateMetadataRequest updateMetadataRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, updateMetadataRequest.xH);
        b.a(parcel, 2, updateMetadataRequest.EV, n2, false);
        b.a(parcel, 3, updateMetadataRequest.EW, n2, false);
        b.F(parcel, n3);
    }

    public UpdateMetadataRequest[] aE(int n2) {
        return new UpdateMetadataRequest[n2];
    }

    public UpdateMetadataRequest aa(Parcel parcel) {
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
        return new UpdateMetadataRequest(n3, driveId, metadataBundle);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aa(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aE(n2);
    }
}

