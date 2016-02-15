package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class iy implements Creator<ix> {
    static void m1090a(ix ixVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, ixVar.getVersionCode());
        C0262b.m229a(parcel, 2, ixVar.act, false);
        C0262b.m230a(parcel, 3, ixVar.acu, false);
        C0262b.m211F(parcel, p);
    }

    public ix bm(Parcel parcel) {
        String[] strArr = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        byte[][] bArr = (byte[][]) null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    strArr = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    bArr = C0261a.m200r(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ix(i, strArr, bArr);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bm(x0);
    }

    public ix[] cy(int i) {
        return new ix[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cy(x0);
    }
}
