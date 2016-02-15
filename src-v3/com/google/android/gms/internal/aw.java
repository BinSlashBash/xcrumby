package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;

public class aw implements Creator<av> {
    static void m654a(av avVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, avVar.versionCode);
        C0262b.m234c(parcel, 2, avVar.mq);
        C0262b.m234c(parcel, 3, avVar.backgroundColor);
        C0262b.m234c(parcel, 4, avVar.mr);
        C0262b.m234c(parcel, 5, avVar.ms);
        C0262b.m234c(parcel, 6, avVar.mt);
        C0262b.m234c(parcel, 7, avVar.mu);
        C0262b.m234c(parcel, 8, avVar.mv);
        C0262b.m234c(parcel, 9, avVar.mw);
        C0262b.m222a(parcel, 10, avVar.mx, false);
        C0262b.m234c(parcel, 11, avVar.my);
        C0262b.m222a(parcel, 12, avVar.mz, false);
        C0262b.m234c(parcel, 13, avVar.mA);
        C0262b.m234c(parcel, 14, avVar.mB);
        C0262b.m222a(parcel, 15, avVar.mC, false);
        C0262b.m211F(parcel, p);
    }

    public av m655c(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        String str = null;
        int i10 = 0;
        String str2 = null;
        int i11 = 0;
        int i12 = 0;
        String str3 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    i5 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i6 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i7 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    i8 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    i9 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    i10 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    i11 = C0261a.m187g(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    i12 = C0261a.m187g(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new av(i, i2, i3, i4, i5, i6, i7, i8, i9, str, i10, str2, i11, i12, str3);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m655c(x0);
    }

    public av[] m656e(int i) {
        return new av[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m656e(x0);
    }
}
