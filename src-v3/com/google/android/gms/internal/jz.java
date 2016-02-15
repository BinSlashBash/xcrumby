package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class jz implements Creator<jy> {
    static void m1115a(jy jyVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, jyVar.getVersionCode());
        C0262b.m222a(parcel, 2, jyVar.adn, false);
        C0262b.m222a(parcel, 3, jyVar.pm, false);
        C0262b.m219a(parcel, 4, jyVar.adr, i, false);
        C0262b.m219a(parcel, 5, jyVar.ads, i, false);
        C0262b.m219a(parcel, 6, jyVar.adt, i, false);
        C0262b.m211F(parcel, p);
    }

    public jy bx(Parcel parcel) {
        jw jwVar = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        jw jwVar2 = null;
        ju juVar = null;
        String str = null;
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
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    juVar = (ju) C0261a.m176a(parcel, n, ju.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    jwVar2 = (jw) C0261a.m176a(parcel, n, jw.CREATOR);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    jwVar = (jw) C0261a.m176a(parcel, n, jw.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new jy(i, str2, str, juVar, jwVar2, jwVar);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public jy[] cL(int i) {
        return new jy[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bx(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cL(x0);
    }
}
