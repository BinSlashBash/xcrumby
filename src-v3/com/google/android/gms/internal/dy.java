package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class dy implements Creator<dx> {
    static void m824a(dx dxVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, dxVar.versionCode);
        C0262b.m222a(parcel, 2, dxVar.rq, false);
        C0262b.m234c(parcel, 3, dxVar.rr);
        C0262b.m234c(parcel, 4, dxVar.rs);
        C0262b.m225a(parcel, 5, dxVar.rt);
        C0262b.m211F(parcel, p);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m825h(x0);
    }

    public dx m825h(Parcel parcel) {
        boolean z = false;
        int o = C0261a.m196o(parcel);
        String str = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new dx(i3, str, i2, i, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m826o(x0);
    }

    public dx[] m826o(int i) {
        return new dx[i];
    }
}
