package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.DriveId;

public class ai implements Creator<OpenFileIntentSenderRequest> {
    static void m266a(OpenFileIntentSenderRequest openFileIntentSenderRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, openFileIntentSenderRequest.xH);
        C0262b.m222a(parcel, 2, openFileIntentSenderRequest.EB, false);
        C0262b.m229a(parcel, 3, openFileIntentSenderRequest.EQ, false);
        C0262b.m219a(parcel, 4, openFileIntentSenderRequest.EC, i, false);
        C0262b.m211F(parcel, p);
    }

    public OpenFileIntentSenderRequest m267W(Parcel parcel) {
        DriveId driveId = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String[] strArr = null;
        String str = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    strArr = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    driveId = (DriveId) C0261a.m176a(parcel, n, DriveId.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new OpenFileIntentSenderRequest(i, str, strArr, driveId);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public OpenFileIntentSenderRequest[] aA(int i) {
        return new OpenFileIntentSenderRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m267W(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aA(x0);
    }
}
