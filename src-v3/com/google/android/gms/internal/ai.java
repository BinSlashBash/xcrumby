package com.google.android.gms.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import java.util.List;

public class ai implements Creator<ah> {
    static void m610a(ah ahVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, ahVar.versionCode);
        C0262b.m215a(parcel, 2, ahVar.lH);
        C0262b.m216a(parcel, 3, ahVar.extras, false);
        C0262b.m234c(parcel, 4, ahVar.lI);
        C0262b.m223a(parcel, 5, ahVar.lJ, false);
        C0262b.m225a(parcel, 6, ahVar.lK);
        C0262b.m234c(parcel, 7, ahVar.lL);
        C0262b.m225a(parcel, 8, ahVar.lM);
        C0262b.m222a(parcel, 9, ahVar.lN, false);
        C0262b.m219a(parcel, 10, ahVar.lO, i, false);
        C0262b.m219a(parcel, 11, ahVar.lP, i, false);
        C0262b.m222a(parcel, 12, ahVar.lQ, false);
        C0262b.m211F(parcel, p);
    }

    public ah m611a(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        long j = 0;
        Bundle bundle = null;
        int i2 = 0;
        List list = null;
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        String str = null;
        av avVar = null;
        Location location = null;
        String str2 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    bundle = C0261a.m198p(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    list = C0261a.m171A(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    avVar = (av) C0261a.m176a(parcel, n, av.CREATOR);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    location = (Location) C0261a.m176a(parcel, n, Location.CREATOR);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ah(i, j, bundle, i2, list, z, i3, z2, str, avVar, location, str2);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ah[] m612b(int i) {
        return new ah[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m611a(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m612b(x0);
    }
}
