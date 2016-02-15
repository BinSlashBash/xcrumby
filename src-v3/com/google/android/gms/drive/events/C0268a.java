package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.DriveId;

/* renamed from: com.google.android.gms.drive.events.a */
public class C0268a implements Creator<ChangeEvent> {
    static void m243a(ChangeEvent changeEvent, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, changeEvent.xH);
        C0262b.m219a(parcel, 2, changeEvent.Ew, i, false);
        C0262b.m234c(parcel, 3, changeEvent.ER);
        C0262b.m211F(parcel, p);
    }

    public ChangeEvent m244A(Parcel parcel) {
        int i = 0;
        int o = C0261a.m196o(parcel);
        DriveId driveId = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            DriveId driveId2;
            int g;
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    int i3 = i;
                    driveId2 = driveId;
                    g = C0261a.m187g(parcel, n);
                    n = i3;
                    break;
                case Std.STD_URL /*2*/:
                    g = i2;
                    DriveId driveId3 = (DriveId) C0261a.m176a(parcel, n, DriveId.CREATOR);
                    n = i;
                    driveId2 = driveId3;
                    break;
                case Std.STD_URI /*3*/:
                    n = C0261a.m187g(parcel, n);
                    driveId2 = driveId;
                    g = i2;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    n = i;
                    driveId2 = driveId;
                    g = i2;
                    break;
            }
            i2 = g;
            driveId = driveId2;
            i = n;
        }
        if (parcel.dataPosition() == o) {
            return new ChangeEvent(i2, driveId, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ChangeEvent[] ae(int i) {
        return new ChangeEvent[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m244A(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ae(x0);
    }
}
