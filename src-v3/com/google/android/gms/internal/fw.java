package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class fw implements Creator<fv> {
    static void m995a(fv fvVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, fvVar.getVersionCode());
        C0262b.m219a(parcel, 2, fvVar.eT(), i, false);
        C0262b.m211F(parcel, p);
    }

    public fv[] m996S(int i) {
        return new fv[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m997q(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m996S(x0);
    }

    public fv m997q(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        fx fxVar = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    fxVar = (fx) C0261a.m176a(parcel, n, fx.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new fv(i, fxVar);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
