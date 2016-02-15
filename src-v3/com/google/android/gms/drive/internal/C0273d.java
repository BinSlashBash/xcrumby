package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* renamed from: com.google.android.gms.drive.internal.d */
public class C0273d implements Creator<CloseContentsAndUpdateMetadataRequest> {
    static void m277a(CloseContentsAndUpdateMetadataRequest closeContentsAndUpdateMetadataRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, closeContentsAndUpdateMetadataRequest.xH);
        C0262b.m219a(parcel, 2, closeContentsAndUpdateMetadataRequest.EV, i, false);
        C0262b.m219a(parcel, 3, closeContentsAndUpdateMetadataRequest.EW, i, false);
        C0262b.m219a(parcel, 4, closeContentsAndUpdateMetadataRequest.EX, i, false);
        C0262b.m211F(parcel, p);
    }

    public CloseContentsAndUpdateMetadataRequest m278E(Parcel parcel) {
        Contents contents = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        MetadataBundle metadataBundle = null;
        DriveId driveId = null;
        while (parcel.dataPosition() < o) {
            MetadataBundle metadataBundle2;
            DriveId driveId2;
            int g;
            Contents contents2;
            int n = C0261a.m194n(parcel);
            Contents contents3;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    contents3 = contents;
                    metadataBundle2 = metadataBundle;
                    driveId2 = driveId;
                    g = C0261a.m187g(parcel, n);
                    contents2 = contents3;
                    break;
                case Std.STD_URL /*2*/:
                    g = i;
                    MetadataBundle metadataBundle3 = metadataBundle;
                    driveId2 = (DriveId) C0261a.m176a(parcel, n, DriveId.CREATOR);
                    contents2 = contents;
                    metadataBundle2 = metadataBundle3;
                    break;
                case Std.STD_URI /*3*/:
                    driveId2 = driveId;
                    g = i;
                    contents3 = contents;
                    metadataBundle2 = (MetadataBundle) C0261a.m176a(parcel, n, MetadataBundle.CREATOR);
                    contents2 = contents3;
                    break;
                case Std.STD_CLASS /*4*/:
                    contents2 = (Contents) C0261a.m176a(parcel, n, Contents.CREATOR);
                    metadataBundle2 = metadataBundle;
                    driveId2 = driveId;
                    g = i;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    contents2 = contents;
                    metadataBundle2 = metadataBundle;
                    driveId2 = driveId;
                    g = i;
                    break;
            }
            i = g;
            driveId = driveId2;
            metadataBundle = metadataBundle2;
            contents = contents2;
        }
        if (parcel.dataPosition() == o) {
            return new CloseContentsAndUpdateMetadataRequest(i, driveId, metadataBundle, contents);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public CloseContentsAndUpdateMetadataRequest[] ai(int i) {
        return new CloseContentsAndUpdateMetadataRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m278E(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ai(x0);
    }
}
