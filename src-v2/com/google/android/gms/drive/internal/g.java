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
import com.google.android.gms.drive.internal.CreateFileIntentSenderRequest;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class g
implements Parcelable.Creator<CreateFileIntentSenderRequest> {
    static void a(CreateFileIntentSenderRequest createFileIntentSenderRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, createFileIntentSenderRequest.xH);
        b.a(parcel, 2, createFileIntentSenderRequest.EZ, n2, false);
        b.c(parcel, 3, createFileIntentSenderRequest.Eu);
        b.a(parcel, 4, createFileIntentSenderRequest.EB, false);
        b.a(parcel, 5, createFileIntentSenderRequest.EC, n2, false);
        b.F(parcel, n3);
    }

    public CreateFileIntentSenderRequest H(Parcel parcel) {
        int n2 = 0;
        DriveId driveId = null;
        int n3 = a.o(parcel);
        String string2 = null;
        MetadataBundle metadataBundle = null;
        int n4 = 0;
        block7 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block7;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block7;
                }
                case 2: {
                    metadataBundle = a.a(parcel, n5, MetadataBundle.CREATOR);
                    continue block7;
                }
                case 3: {
                    n2 = a.g(parcel, n5);
                    continue block7;
                }
                case 4: {
                    string2 = a.n(parcel, n5);
                    continue block7;
                }
                case 5: 
            }
            driveId = a.a(parcel, n5, DriveId.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new CreateFileIntentSenderRequest(n4, metadataBundle, n2, string2, driveId);
    }

    public CreateFileIntentSenderRequest[] al(int n2) {
        return new CreateFileIntentSenderRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.H(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.al(n2);
    }
}

