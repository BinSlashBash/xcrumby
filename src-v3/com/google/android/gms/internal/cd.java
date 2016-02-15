package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;

public class cd implements Creator<ce> {
    static void m682a(ce ceVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, ceVar.versionCode);
        C0262b.m219a(parcel, 2, ceVar.og, i, false);
        C0262b.m217a(parcel, 3, ceVar.aO(), false);
        C0262b.m217a(parcel, 4, ceVar.aP(), false);
        C0262b.m217a(parcel, 5, ceVar.aQ(), false);
        C0262b.m217a(parcel, 6, ceVar.aR(), false);
        C0262b.m222a(parcel, 7, ceVar.ol, false);
        C0262b.m225a(parcel, 8, ceVar.om);
        C0262b.m222a(parcel, 9, ceVar.on, false);
        C0262b.m217a(parcel, 10, ceVar.aT(), false);
        C0262b.m234c(parcel, 11, ceVar.orientation);
        C0262b.m234c(parcel, 12, ceVar.op);
        C0262b.m222a(parcel, 13, ceVar.nO, false);
        C0262b.m219a(parcel, 14, ceVar.kK, i, false);
        C0262b.m217a(parcel, 15, ceVar.aS(), false);
        C0262b.m222a(parcel, 16, ceVar.or, false);
        C0262b.m211F(parcel, p);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m683e(x0);
    }

    public ce m683e(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        cb cbVar = null;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        String str = null;
        boolean z = false;
        String str2 = null;
        IBinder iBinder5 = null;
        int i2 = 0;
        int i3 = 0;
        String str3 = null;
        dx dxVar = null;
        IBinder iBinder6 = null;
        String str4 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    cbVar = (cb) C0261a.m176a(parcel, n, cb.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    iBinder = C0261a.m197o(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    iBinder2 = C0261a.m197o(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    iBinder3 = C0261a.m197o(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    iBinder4 = C0261a.m197o(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    iBinder5 = C0261a.m197o(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    dxVar = (dx) C0261a.m176a(parcel, n, dx.CREATOR);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    iBinder6 = C0261a.m197o(parcel, n);
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ce(i, cbVar, iBinder, iBinder2, iBinder3, iBinder4, str, z, str2, iBinder5, i2, i3, str3, dxVar, iBinder6, str4);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ce[] m684i(int i) {
        return new ce[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m684i(x0);
    }
}
