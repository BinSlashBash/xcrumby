package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.drive.d */
public class C0267d implements Creator<DriveId> {
    static void m240a(DriveId driveId, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, driveId.xH);
        C0262b.m222a(parcel, 2, driveId.EH, false);
        C0262b.m215a(parcel, 3, driveId.EI);
        C0262b.m215a(parcel, 4, driveId.EJ);
        C0262b.m211F(parcel, p);
    }

    public DriveId[] ad(int i) {
        return new DriveId[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m241z(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ad(x0);
    }

    public DriveId m241z(Parcel parcel) {
        long j = 0;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        long j2 = 0;
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
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new DriveId(i, str, j2, j);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
