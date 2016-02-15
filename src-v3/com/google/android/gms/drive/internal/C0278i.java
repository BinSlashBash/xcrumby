package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* renamed from: com.google.android.gms.drive.internal.i */
public class C0278i implements Creator<CreateFolderRequest> {
    static void m287a(CreateFolderRequest createFolderRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, createFolderRequest.xH);
        C0262b.m219a(parcel, 2, createFolderRequest.Fa, i, false);
        C0262b.m219a(parcel, 3, createFolderRequest.EZ, i, false);
        C0262b.m211F(parcel, p);
    }

    public CreateFolderRequest m288J(Parcel parcel) {
        MetadataBundle metadataBundle = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        DriveId driveId = null;
        while (parcel.dataPosition() < o) {
            DriveId driveId2;
            int g;
            MetadataBundle metadataBundle2;
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    MetadataBundle metadataBundle3 = metadataBundle;
                    driveId2 = driveId;
                    g = C0261a.m187g(parcel, n);
                    metadataBundle2 = metadataBundle3;
                    break;
                case Std.STD_URL /*2*/:
                    g = i;
                    DriveId driveId3 = (DriveId) C0261a.m176a(parcel, n, DriveId.CREATOR);
                    metadataBundle2 = metadataBundle;
                    driveId2 = driveId3;
                    break;
                case Std.STD_URI /*3*/:
                    metadataBundle2 = (MetadataBundle) C0261a.m176a(parcel, n, MetadataBundle.CREATOR);
                    driveId2 = driveId;
                    g = i;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    metadataBundle2 = metadataBundle;
                    driveId2 = driveId;
                    g = i;
                    break;
            }
            i = g;
            driveId = driveId2;
            metadataBundle = metadataBundle2;
        }
        if (parcel.dataPosition() == o) {
            return new CreateFolderRequest(i, driveId, metadataBundle);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public CreateFolderRequest[] an(int i) {
        return new CreateFolderRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m288J(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return an(x0);
    }
}
