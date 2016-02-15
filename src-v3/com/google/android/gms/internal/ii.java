package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.ih.C1384a;
import com.google.android.gms.internal.ih.C1387b;
import com.google.android.gms.internal.ih.C1388c;
import com.google.android.gms.internal.ih.C1389d;
import com.google.android.gms.internal.ih.C1390f;
import com.google.android.gms.internal.ih.C1391g;
import com.google.android.gms.internal.ih.C1392h;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ii implements Creator<ih> {
    static void m1079a(ih ihVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = ihVar.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, ihVar.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m222a(parcel, 2, ihVar.getAboutMe(), true);
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m219a(parcel, 3, ihVar.jv(), i, true);
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m222a(parcel, 4, ihVar.getBirthday(), true);
        }
        if (ja.contains(Integer.valueOf(5))) {
            C0262b.m222a(parcel, 5, ihVar.getBraggingRights(), true);
        }
        if (ja.contains(Integer.valueOf(6))) {
            C0262b.m234c(parcel, 6, ihVar.getCircledByCount());
        }
        if (ja.contains(Integer.valueOf(7))) {
            C0262b.m219a(parcel, 7, ihVar.jw(), i, true);
        }
        if (ja.contains(Integer.valueOf(8))) {
            C0262b.m222a(parcel, 8, ihVar.getCurrentLocation(), true);
        }
        if (ja.contains(Integer.valueOf(9))) {
            C0262b.m222a(parcel, 9, ihVar.getDisplayName(), true);
        }
        if (ja.contains(Integer.valueOf(12))) {
            C0262b.m234c(parcel, 12, ihVar.getGender());
        }
        if (ja.contains(Integer.valueOf(14))) {
            C0262b.m222a(parcel, 14, ihVar.getId(), true);
        }
        if (ja.contains(Integer.valueOf(15))) {
            C0262b.m219a(parcel, 15, ihVar.jx(), i, true);
        }
        if (ja.contains(Integer.valueOf(16))) {
            C0262b.m225a(parcel, 16, ihVar.isPlusUser());
        }
        if (ja.contains(Integer.valueOf(19))) {
            C0262b.m219a(parcel, 19, ihVar.jy(), i, true);
        }
        if (ja.contains(Integer.valueOf(18))) {
            C0262b.m222a(parcel, 18, ihVar.getLanguage(), true);
        }
        if (ja.contains(Integer.valueOf(21))) {
            C0262b.m234c(parcel, 21, ihVar.getObjectType());
        }
        if (ja.contains(Integer.valueOf(20))) {
            C0262b.m222a(parcel, 20, ihVar.getNickname(), true);
        }
        if (ja.contains(Integer.valueOf(23))) {
            C0262b.m233b(parcel, 23, ihVar.jA(), true);
        }
        if (ja.contains(Integer.valueOf(22))) {
            C0262b.m233b(parcel, 22, ihVar.jz(), true);
        }
        if (ja.contains(Integer.valueOf(25))) {
            C0262b.m234c(parcel, 25, ihVar.getRelationshipStatus());
        }
        if (ja.contains(Integer.valueOf(24))) {
            C0262b.m234c(parcel, 24, ihVar.getPlusOneCount());
        }
        if (ja.contains(Integer.valueOf(27))) {
            C0262b.m222a(parcel, 27, ihVar.getUrl(), true);
        }
        if (ja.contains(Integer.valueOf(26))) {
            C0262b.m222a(parcel, 26, ihVar.getTagline(), true);
        }
        if (ja.contains(Integer.valueOf(29))) {
            C0262b.m225a(parcel, 29, ihVar.isVerified());
        }
        if (ja.contains(Integer.valueOf(28))) {
            C0262b.m233b(parcel, 28, ihVar.jB(), true);
        }
        C0262b.m211F(parcel, p);
    }

    public ih aN(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        String str = null;
        C1384a c1384a = null;
        String str2 = null;
        String str3 = null;
        int i2 = 0;
        C1387b c1387b = null;
        String str4 = null;
        String str5 = null;
        int i3 = 0;
        String str6 = null;
        C1388c c1388c = null;
        boolean z = false;
        String str7 = null;
        C1389d c1389d = null;
        String str8 = null;
        int i4 = 0;
        List list = null;
        List list2 = null;
        int i5 = 0;
        int i6 = 0;
        String str9 = null;
        String str10 = null;
        List list3 = null;
        boolean z2 = false;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Std.STD_URI /*3*/:
                    C1384a c1384a2 = (C1384a) C0261a.m176a(parcel, n, C1384a.CREATOR);
                    hashSet.add(Integer.valueOf(3));
                    c1384a = c1384a2;
                    break;
                case Std.STD_CLASS /*4*/:
                    str2 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str3 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i2 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(6));
                    break;
                case Std.STD_PATTERN /*7*/:
                    C1387b c1387b2 = (C1387b) C0261a.m176a(parcel, n, C1387b.CREATOR);
                    hashSet.add(Integer.valueOf(7));
                    c1387b = c1387b2;
                    break;
                case Std.STD_LOCALE /*8*/:
                    str4 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(8));
                    break;
                case Std.STD_CHARSET /*9*/:
                    str5 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(9));
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    i3 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(12));
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    str6 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(14));
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    C1388c c1388c2 = (C1388c) C0261a.m176a(parcel, n, C1388c.CREATOR);
                    hashSet.add(Integer.valueOf(15));
                    c1388c = c1388c2;
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    z = C0261a.m183c(parcel, n);
                    hashSet.add(Integer.valueOf(16));
                    break;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    str7 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(18));
                    break;
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    C1389d c1389d2 = (C1389d) C0261a.m176a(parcel, n, C1389d.CREATOR);
                    hashSet.add(Integer.valueOf(19));
                    c1389d = c1389d2;
                    break;
                case HttpEngine.MAX_REDIRECTS /*20*/:
                    str8 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(20));
                    break;
                case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                    i4 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(21));
                    break;
                case C0065R.styleable.TwoWayView_android_fitsSystemWindows /*22*/:
                    list = C0261a.m182c(parcel, n, C1390f.CREATOR);
                    hashSet.add(Integer.valueOf(22));
                    break;
                case C0065R.styleable.TwoWayView_android_scrollbars /*23*/:
                    list2 = C0261a.m182c(parcel, n, C1391g.CREATOR);
                    hashSet.add(Integer.valueOf(23));
                    break;
                case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                    i5 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(24));
                    break;
                case C0065R.styleable.TwoWayView_android_fadingEdgeLength /*25*/:
                    i6 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(25));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                    str9 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(26));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusRight /*27*/:
                    str10 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(27));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusUp /*28*/:
                    list3 = C0261a.m182c(parcel, n, C1392h.CREATOR);
                    hashSet.add(Integer.valueOf(28));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusDown /*29*/:
                    z2 = C0261a.m183c(parcel, n);
                    hashSet.add(Integer.valueOf(29));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ih(hashSet, i, str, c1384a, str2, str3, i2, c1387b, str4, str5, i3, str6, c1388c, z, str7, c1389d, str8, i4, list, list2, i5, i6, str9, str10, list3, z2);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ih[] bQ(int i) {
        return new ih[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aN(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bQ(x0);
    }
}
