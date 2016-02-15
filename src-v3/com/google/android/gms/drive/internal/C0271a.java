package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.DriveId;

/* renamed from: com.google.android.gms.drive.internal.a */
public class C0271a implements Creator<AddEventListenerRequest> {
    static void m248a(AddEventListenerRequest addEventListenerRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, addEventListenerRequest.xH);
        C0262b.m219a(parcel, 2, addEventListenerRequest.Ew, i, false);
        C0262b.m234c(parcel, 3, addEventListenerRequest.ES);
        C0262b.m219a(parcel, 4, addEventListenerRequest.ET, i, false);
        C0262b.m211F(parcel, p);
    }

    public AddEventListenerRequest m249C(Parcel parcel) {
        PendingIntent pendingIntent = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        DriveId driveId = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int i3;
            DriveId driveId2;
            int g;
            PendingIntent pendingIntent2;
            int n = C0261a.m194n(parcel);
            PendingIntent pendingIntent3;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    pendingIntent3 = pendingIntent;
                    i3 = i;
                    driveId2 = driveId;
                    g = C0261a.m187g(parcel, n);
                    pendingIntent2 = pendingIntent3;
                    break;
                case Std.STD_URL /*2*/:
                    g = i2;
                    int i4 = i;
                    driveId2 = (DriveId) C0261a.m176a(parcel, n, DriveId.CREATOR);
                    pendingIntent2 = pendingIntent;
                    i3 = i4;
                    break;
                case Std.STD_URI /*3*/:
                    driveId2 = driveId;
                    g = i2;
                    pendingIntent3 = pendingIntent;
                    i3 = C0261a.m187g(parcel, n);
                    pendingIntent2 = pendingIntent3;
                    break;
                case Std.STD_CLASS /*4*/:
                    pendingIntent2 = (PendingIntent) C0261a.m176a(parcel, n, PendingIntent.CREATOR);
                    i3 = i;
                    driveId2 = driveId;
                    g = i2;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    pendingIntent2 = pendingIntent;
                    i3 = i;
                    driveId2 = driveId;
                    g = i2;
                    break;
            }
            i2 = g;
            driveId = driveId2;
            i = i3;
            pendingIntent = pendingIntent2;
        }
        if (parcel.dataPosition() == o) {
            return new AddEventListenerRequest(i2, driveId, i, pendingIntent);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public AddEventListenerRequest[] ag(int i) {
        return new AddEventListenerRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m249C(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ag(x0);
    }
}
