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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.CreateFileRequest;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class h
implements Parcelable.Creator<CreateFileRequest> {
    static void a(CreateFileRequest createFileRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, createFileRequest.xH);
        b.a(parcel, 2, createFileRequest.Fa, n2, false);
        b.a(parcel, 3, createFileRequest.EZ, n2, false);
        b.a(parcel, 4, createFileRequest.EX, n2, false);
        b.F(parcel, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public CreateFileRequest I(Parcel parcel) {
        Contents contents = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        DriveId driveId = null;
        SafeParcelable safeParcelable = null;
        while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    DriveId driveId2 = driveId;
                    driveId = safeParcelable;
                    safeParcelable = driveId2;
                    break;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    DriveId driveId3 = safeParcelable;
                    safeParcelable = driveId;
                    driveId = driveId3;
                    break;
                }
                case 2: {
                    DriveId driveId4 = a.a(parcel, n4, DriveId.CREATOR);
                    safeParcelable = driveId;
                    driveId = driveId4;
                    break;
                }
                case 3: {
                    MetadataBundle metadataBundle = a.a(parcel, n4, MetadataBundle.CREATOR);
                    driveId = safeParcelable;
                    safeParcelable = metadataBundle;
                    break;
                }
                case 4: {
                    contents = a.a(parcel, n4, Contents.CREATOR);
                    DriveId driveId5 = safeParcelable;
                    safeParcelable = driveId;
                    driveId = driveId5;
                }
            }
            DriveId driveId6 = driveId;
            driveId = safeParcelable;
            safeParcelable = driveId6;
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new CreateFileRequest(n3, (DriveId)safeParcelable, (MetadataBundle)((Object)driveId), contents);
    }

    public CreateFileRequest[] am(int n2) {
        return new CreateFileRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.I(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.am(n2);
    }
}

