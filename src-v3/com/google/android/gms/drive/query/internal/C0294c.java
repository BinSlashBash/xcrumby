package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;

/* renamed from: com.google.android.gms.drive.query.internal.c */
public class C0294c implements Creator<FieldWithSortOrder> {
    static void m337a(FieldWithSortOrder fieldWithSortOrder, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, fieldWithSortOrder.xH);
        C0262b.m222a(parcel, 1, fieldWithSortOrder.FM, false);
        C0262b.m225a(parcel, 2, fieldWithSortOrder.GJ);
        C0262b.m211F(parcel, p);
    }

    public FieldWithSortOrder[] aK(int i) {
        return new FieldWithSortOrder[i];
    }

    public FieldWithSortOrder ag(Parcel parcel) {
        boolean z = false;
        int o = C0261a.m196o(parcel);
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new FieldWithSortOrder(i, str, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ag(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aK(x0);
    }
}
