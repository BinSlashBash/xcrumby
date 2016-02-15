package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class gh implements Creator<gg> {
    static void m1022a(gg ggVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, ggVar.getVersionCode());
        C0262b.m218a(parcel, 2, ggVar.fq(), false);
        C0262b.m219a(parcel, 3, ggVar.fr(), i, false);
        C0262b.m211F(parcel, p);
    }

    public gg[] m1023Z(int i) {
        return new gg[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m1024x(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m1023Z(x0);
    }

    public gg m1024x(Parcel parcel) {
        gd gdVar = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    parcel2 = C0261a.m172B(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    gdVar = (gd) C0261a.m176a(parcel, n, gd.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new gg(i, parcel2, gdVar);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
