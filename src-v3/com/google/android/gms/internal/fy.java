package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.fx.C0899a;
import java.util.ArrayList;

public class fy implements Creator<fx> {
    static void m998a(fx fxVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, fxVar.getVersionCode());
        C0262b.m233b(parcel, 2, fxVar.eV(), false);
        C0262b.m211F(parcel, p);
    }

    public fx[] m999T(int i) {
        return new fx[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m1000r(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m999T(x0);
    }

    public fx m1000r(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    arrayList = C0261a.m182c(parcel, n, C0899a.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new fx(i, arrayList);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
