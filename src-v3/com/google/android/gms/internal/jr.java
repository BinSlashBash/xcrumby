package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class jr implements Creator<jo> {
    static void m1111a(jo joVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, joVar.getVersionCode());
        C0262b.m222a(parcel, 2, joVar.label, false);
        C0262b.m219a(parcel, 3, joVar.adg, i, false);
        C0262b.m222a(parcel, 4, joVar.type, false);
        C0262b.m219a(parcel, 5, joVar.abJ, i, false);
        C0262b.m211F(parcel, p);
    }

    public jo bt(Parcel parcel) {
        ju juVar = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        jp jpVar = null;
        String str2 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    jpVar = (jp) C0261a.m176a(parcel, n, jp.CREATOR);
                    break;
                case Std.STD_CLASS /*4*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    juVar = (ju) C0261a.m176a(parcel, n, ju.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new jo(i, str2, jpVar, str, juVar);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public jo[] cH(int i) {
        return new jo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bt(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cH(x0);
    }
}
