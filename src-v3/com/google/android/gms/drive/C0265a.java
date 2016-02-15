package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.drive.a */
public class C0265a implements Creator<Contents> {
    static void m238a(Contents contents, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, contents.xH);
        C0262b.m219a(parcel, 2, contents.Cj, i, false);
        C0262b.m234c(parcel, 3, contents.Eu);
        C0262b.m234c(parcel, 4, contents.Ev);
        C0262b.m219a(parcel, 5, contents.Ew, i, false);
        C0262b.m211F(parcel, p);
    }

    public Contents[] ac(int i) {
        return new Contents[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m239y(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ac(x0);
    }

    public Contents m239y(Parcel parcel) {
        DriveId driveId = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        int i2 = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    parcelFileDescriptor = (ParcelFileDescriptor) C0261a.m176a(parcel, n, ParcelFileDescriptor.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i = C0261a.m187g(parcel, n);
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
            return new Contents(i3, parcelFileDescriptor, i2, i, driveId);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
