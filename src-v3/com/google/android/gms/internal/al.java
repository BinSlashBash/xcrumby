package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class al implements Creator<ak> {
    static void m614a(ak akVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, akVar.versionCode);
        C0262b.m222a(parcel, 2, akVar.lS, false);
        C0262b.m234c(parcel, 3, akVar.height);
        C0262b.m234c(parcel, 4, akVar.heightPixels);
        C0262b.m225a(parcel, 5, akVar.lT);
        C0262b.m234c(parcel, 6, akVar.width);
        C0262b.m234c(parcel, 7, akVar.widthPixels);
        C0262b.m228a(parcel, 8, akVar.lU, i, false);
        C0262b.m211F(parcel, p);
    }

    public ak m615b(Parcel parcel) {
        ak[] akVarArr = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        int i4 = 0;
        String str = null;
        int i5 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i5 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    akVarArr = (ak[]) C0261a.m181b(parcel, n, ak.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ak(i5, str, i4, i3, z, i2, i, akVarArr);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ak[] m616c(int i) {
        return new ak[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m615b(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m616c(x0);
    }
}
