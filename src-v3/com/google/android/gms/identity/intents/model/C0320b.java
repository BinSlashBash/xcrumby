package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;

/* renamed from: com.google.android.gms.identity.intents.model.b */
public class C0320b implements Creator<UserAddress> {
    static void m588a(UserAddress userAddress, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, userAddress.getVersionCode());
        C0262b.m222a(parcel, 2, userAddress.name, false);
        C0262b.m222a(parcel, 3, userAddress.NB, false);
        C0262b.m222a(parcel, 4, userAddress.NC, false);
        C0262b.m222a(parcel, 5, userAddress.ND, false);
        C0262b.m222a(parcel, 6, userAddress.NE, false);
        C0262b.m222a(parcel, 7, userAddress.NF, false);
        C0262b.m222a(parcel, 8, userAddress.NG, false);
        C0262b.m222a(parcel, 9, userAddress.NH, false);
        C0262b.m222a(parcel, 10, userAddress.qd, false);
        C0262b.m222a(parcel, 11, userAddress.NI, false);
        C0262b.m222a(parcel, 12, userAddress.NJ, false);
        C0262b.m222a(parcel, 13, userAddress.NK, false);
        C0262b.m225a(parcel, 14, userAddress.NL);
        C0262b.m222a(parcel, 15, userAddress.NM, false);
        C0262b.m222a(parcel, 16, userAddress.NN, false);
        C0262b.m211F(parcel, p);
    }

    public UserAddress aA(Parcel parcel) {
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
        String str10 = null;
        String str11 = null;
        String str12 = null;
        boolean z = false;
        String str13 = null;
        String str14 = null;
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
                    str10 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    str11 = C0261a.m195n(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    str12 = C0261a.m195n(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    str13 = C0261a.m195n(parcel, n);
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    str14 = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new UserAddress(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, z, str13, str14);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public UserAddress[] bu(int i) {
        return new UserAddress[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aA(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bu(x0);
    }
}
