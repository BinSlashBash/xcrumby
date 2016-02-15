package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.jm;
import com.google.android.gms.internal.jo;
import com.google.android.gms.internal.js;
import com.google.android.gms.internal.ju;
import com.google.android.gms.internal.jw;
import com.google.android.gms.internal.jy;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayList;

/* renamed from: com.google.android.gms.wallet.j */
public class C0557j implements Creator<LoyaltyWalletObject> {
    static void m1517a(LoyaltyWalletObject loyaltyWalletObject, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, loyaltyWalletObject.getVersionCode());
        C0262b.m222a(parcel, 2, loyaltyWalletObject.eC, false);
        C0262b.m222a(parcel, 3, loyaltyWalletObject.abz, false);
        C0262b.m222a(parcel, 4, loyaltyWalletObject.abA, false);
        C0262b.m222a(parcel, 5, loyaltyWalletObject.abB, false);
        C0262b.m222a(parcel, 6, loyaltyWalletObject.abC, false);
        C0262b.m222a(parcel, 7, loyaltyWalletObject.abD, false);
        C0262b.m222a(parcel, 8, loyaltyWalletObject.abE, false);
        C0262b.m222a(parcel, 9, loyaltyWalletObject.abF, false);
        C0262b.m222a(parcel, 10, loyaltyWalletObject.abG, false);
        C0262b.m222a(parcel, 11, loyaltyWalletObject.abH, false);
        C0262b.m234c(parcel, 12, loyaltyWalletObject.state);
        C0262b.m233b(parcel, 13, loyaltyWalletObject.abI, false);
        C0262b.m219a(parcel, 14, loyaltyWalletObject.abJ, i, false);
        C0262b.m233b(parcel, 15, loyaltyWalletObject.abK, false);
        C0262b.m222a(parcel, 17, loyaltyWalletObject.abM, false);
        C0262b.m222a(parcel, 16, loyaltyWalletObject.abL, false);
        C0262b.m225a(parcel, 19, loyaltyWalletObject.abO);
        C0262b.m233b(parcel, 18, loyaltyWalletObject.abN, false);
        C0262b.m233b(parcel, 21, loyaltyWalletObject.abQ, false);
        C0262b.m233b(parcel, 20, loyaltyWalletObject.abP, false);
        C0262b.m219a(parcel, 23, loyaltyWalletObject.abS, i, false);
        C0262b.m233b(parcel, 22, loyaltyWalletObject.abR, false);
        C0262b.m211F(parcel, p);
    }

    public LoyaltyWalletObject bf(Parcel parcel) {
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
        int i2 = 0;
        ArrayList fs = gi.fs();
        ju juVar = null;
        ArrayList fs2 = gi.fs();
        String str11 = null;
        String str12 = null;
        ArrayList fs3 = gi.fs();
        boolean z = false;
        ArrayList fs4 = gi.fs();
        ArrayList fs5 = gi.fs();
        ArrayList fs6 = gi.fs();
        jo joVar = null;
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
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    fs = C0261a.m182c(parcel, n, jy.CREATOR);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    juVar = (ju) C0261a.m176a(parcel, n, ju.CREATOR);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    fs2 = C0261a.m182c(parcel, n, LatLng.CREATOR);
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    str11 = C0261a.m195n(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                    str12 = C0261a.m195n(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    fs3 = C0261a.m182c(parcel, n, jm.CREATOR);
                    break;
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case HttpEngine.MAX_REDIRECTS /*20*/:
                    fs4 = C0261a.m182c(parcel, n, jw.CREATOR);
                    break;
                case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                    fs5 = C0261a.m182c(parcel, n, js.CREATOR);
                    break;
                case C0065R.styleable.TwoWayView_android_fitsSystemWindows /*22*/:
                    fs6 = C0261a.m182c(parcel, n, jw.CREATOR);
                    break;
                case C0065R.styleable.TwoWayView_android_scrollbars /*23*/:
                    joVar = (jo) C0261a.m176a(parcel, n, jo.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new LoyaltyWalletObject(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, i2, fs, juVar, fs2, str11, str12, fs3, z, fs4, fs5, fs6, joVar);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public LoyaltyWalletObject[] cr(int i) {
        return new LoyaltyWalletObject[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bf(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cr(x0);
    }
}
