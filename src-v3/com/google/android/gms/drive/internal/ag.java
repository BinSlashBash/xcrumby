package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class ag implements Creator<OnSyncMoreResponse> {
    static void m262a(OnSyncMoreResponse onSyncMoreResponse, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, onSyncMoreResponse.xH);
        C0262b.m225a(parcel, 2, onSyncMoreResponse.Fg);
        C0262b.m211F(parcel, p);
    }

    public OnSyncMoreResponse m263U(Parcel parcel) {
        boolean z = false;
        int o = C0261a.m196o(parcel);
        int i = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new OnSyncMoreResponse(i, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public OnSyncMoreResponse[] ay(int i) {
        return new OnSyncMoreResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m263U(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ay(x0);
    }
}
