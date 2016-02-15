package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class kj implements Creator<ki> {
    static void m1120a(ki kiVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, kiVar.xH);
        C0262b.m234c(parcel, 2, kiVar.fA());
        C0262b.m222a(parcel, 3, kiVar.getPath(), false);
        C0262b.m226a(parcel, 4, kiVar.getData(), false);
        C0262b.m222a(parcel, 5, kiVar.getSource(), false);
        C0262b.m211F(parcel, p);
    }

    public ki by(Parcel parcel) {
        int i = 0;
        String str = null;
        int o = C0261a.m196o(parcel);
        byte[] bArr = null;
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    bArr = C0261a.m199q(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ki(i2, i, str2, bArr, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ki[] cN(int i) {
        return new ki[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return by(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cN(x0);
    }
}
