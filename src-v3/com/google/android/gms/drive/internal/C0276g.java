package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* renamed from: com.google.android.gms.drive.internal.g */
public class C0276g implements Creator<CreateFileIntentSenderRequest> {
    static void m283a(CreateFileIntentSenderRequest createFileIntentSenderRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, createFileIntentSenderRequest.xH);
        C0262b.m219a(parcel, 2, createFileIntentSenderRequest.EZ, i, false);
        C0262b.m234c(parcel, 3, createFileIntentSenderRequest.Eu);
        C0262b.m222a(parcel, 4, createFileIntentSenderRequest.EB, false);
        C0262b.m219a(parcel, 5, createFileIntentSenderRequest.EC, i, false);
        C0262b.m211F(parcel, p);
    }

    public CreateFileIntentSenderRequest m284H(Parcel parcel) {
        int i = 0;
        DriveId driveId = null;
        int o = C0261a.m196o(parcel);
        String str = null;
        MetadataBundle metadataBundle = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    metadataBundle = (MetadataBundle) C0261a.m176a(parcel, n, MetadataBundle.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    driveId = (DriveId) C0261a.m176a(parcel, n, DriveId.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new CreateFileIntentSenderRequest(i2, metadataBundle, i, str, driveId);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public CreateFileIntentSenderRequest[] al(int i) {
        return new CreateFileIntentSenderRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m284H(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return al(x0);
    }
}
