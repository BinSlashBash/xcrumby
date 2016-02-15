package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.wallet.a */
public class C0542a implements Creator<Address> {
    static void m1488a(Address address, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, address.getVersionCode());
        C0262b.m222a(parcel, 2, address.name, false);
        C0262b.m222a(parcel, 3, address.NB, false);
        C0262b.m222a(parcel, 4, address.NC, false);
        C0262b.m222a(parcel, 5, address.ND, false);
        C0262b.m222a(parcel, 6, address.qd, false);
        C0262b.m222a(parcel, 7, address.aba, false);
        C0262b.m222a(parcel, 8, address.abb, false);
        C0262b.m222a(parcel, 9, address.NI, false);
        C0262b.m222a(parcel, 10, address.NK, false);
        C0262b.m225a(parcel, 11, address.NL);
        C0262b.m222a(parcel, 12, address.NM, false);
        C0262b.m211F(parcel, p);
    }

    public Address aX(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        boolean z = false;
        String str10 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str6 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    str7 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    str8 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    str9 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    str10 = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new Address(i, str, str2, str3, str4, str5, str6, str7, str8, str9, z, str10);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public Address[] cj(int i) {
        return new Address[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aX(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cj(x0);
    }
}
