package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ga.C0900a;

public class gb implements Creator<C0900a> {
    static void m1010a(C0900a c0900a, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, c0900a.getVersionCode());
        C0262b.m234c(parcel, 2, c0900a.eW());
        C0262b.m225a(parcel, 3, c0900a.fc());
        C0262b.m234c(parcel, 4, c0900a.eX());
        C0262b.m225a(parcel, 5, c0900a.fd());
        C0262b.m222a(parcel, 6, c0900a.fe(), false);
        C0262b.m234c(parcel, 7, c0900a.ff());
        C0262b.m222a(parcel, 8, c0900a.fh(), false);
        C0262b.m219a(parcel, 9, c0900a.fj(), i, false);
        C0262b.m211F(parcel, p);
    }

    public C0900a[] m1011V(int i) {
        return new C0900a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m1012t(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m1011V(x0);
    }

    public C0900a m1012t(Parcel parcel) {
        fv fvVar = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        String str = null;
        String str2 = null;
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    fvVar = (fv) C0261a.m176a(parcel, n, fv.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C0900a(i4, i3, z2, i2, z, str2, i, str, fvVar);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
