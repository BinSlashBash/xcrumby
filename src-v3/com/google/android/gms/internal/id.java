package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.ads.AdSize;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.zip.JSONzip;

public class id implements Creator<ic> {
    static void m1077a(ic icVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = icVar.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, icVar.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m219a(parcel, 2, icVar.jb(), i, true);
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m223a(parcel, 3, icVar.getAdditionalName(), true);
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m219a(parcel, 4, icVar.jc(), i, true);
        }
        if (ja.contains(Integer.valueOf(5))) {
            C0262b.m222a(parcel, 5, icVar.getAddressCountry(), true);
        }
        if (ja.contains(Integer.valueOf(6))) {
            C0262b.m222a(parcel, 6, icVar.getAddressLocality(), true);
        }
        if (ja.contains(Integer.valueOf(7))) {
            C0262b.m222a(parcel, 7, icVar.getAddressRegion(), true);
        }
        if (ja.contains(Integer.valueOf(8))) {
            C0262b.m233b(parcel, 8, icVar.jd(), true);
        }
        if (ja.contains(Integer.valueOf(9))) {
            C0262b.m234c(parcel, 9, icVar.getAttendeeCount());
        }
        if (ja.contains(Integer.valueOf(10))) {
            C0262b.m233b(parcel, 10, icVar.je(), true);
        }
        if (ja.contains(Integer.valueOf(11))) {
            C0262b.m219a(parcel, 11, icVar.jf(), i, true);
        }
        if (ja.contains(Integer.valueOf(12))) {
            C0262b.m233b(parcel, 12, icVar.jg(), true);
        }
        if (ja.contains(Integer.valueOf(13))) {
            C0262b.m222a(parcel, 13, icVar.getBestRating(), true);
        }
        if (ja.contains(Integer.valueOf(14))) {
            C0262b.m222a(parcel, 14, icVar.getBirthDate(), true);
        }
        if (ja.contains(Integer.valueOf(15))) {
            C0262b.m219a(parcel, 15, icVar.jh(), i, true);
        }
        if (ja.contains(Integer.valueOf(17))) {
            C0262b.m222a(parcel, 17, icVar.getContentSize(), true);
        }
        if (ja.contains(Integer.valueOf(16))) {
            C0262b.m222a(parcel, 16, icVar.getCaption(), true);
        }
        if (ja.contains(Integer.valueOf(19))) {
            C0262b.m233b(parcel, 19, icVar.ji(), true);
        }
        if (ja.contains(Integer.valueOf(18))) {
            C0262b.m222a(parcel, 18, icVar.getContentUrl(), true);
        }
        if (ja.contains(Integer.valueOf(21))) {
            C0262b.m222a(parcel, 21, icVar.getDateModified(), true);
        }
        if (ja.contains(Integer.valueOf(20))) {
            C0262b.m222a(parcel, 20, icVar.getDateCreated(), true);
        }
        if (ja.contains(Integer.valueOf(23))) {
            C0262b.m222a(parcel, 23, icVar.getDescription(), true);
        }
        if (ja.contains(Integer.valueOf(22))) {
            C0262b.m222a(parcel, 22, icVar.getDatePublished(), true);
        }
        if (ja.contains(Integer.valueOf(25))) {
            C0262b.m222a(parcel, 25, icVar.getEmbedUrl(), true);
        }
        if (ja.contains(Integer.valueOf(24))) {
            C0262b.m222a(parcel, 24, icVar.getDuration(), true);
        }
        if (ja.contains(Integer.valueOf(27))) {
            C0262b.m222a(parcel, 27, icVar.getFamilyName(), true);
        }
        if (ja.contains(Integer.valueOf(26))) {
            C0262b.m222a(parcel, 26, icVar.getEndDate(), true);
        }
        if (ja.contains(Integer.valueOf(29))) {
            C0262b.m219a(parcel, 29, icVar.jj(), i, true);
        }
        if (ja.contains(Integer.valueOf(28))) {
            C0262b.m222a(parcel, 28, icVar.getGender(), true);
        }
        if (ja.contains(Integer.valueOf(31))) {
            C0262b.m222a(parcel, 31, icVar.getHeight(), true);
        }
        if (ja.contains(Integer.valueOf(30))) {
            C0262b.m222a(parcel, 30, icVar.getGivenName(), true);
        }
        if (ja.contains(Integer.valueOf(34))) {
            C0262b.m219a(parcel, 34, icVar.jk(), i, true);
        }
        if (ja.contains(Integer.valueOf(32))) {
            C0262b.m222a(parcel, 32, icVar.getId(), true);
        }
        if (ja.contains(Integer.valueOf(33))) {
            C0262b.m222a(parcel, 33, icVar.getImage(), true);
        }
        if (ja.contains(Integer.valueOf(38))) {
            C0262b.m213a(parcel, 38, icVar.getLongitude());
        }
        if (ja.contains(Integer.valueOf(39))) {
            C0262b.m222a(parcel, 39, icVar.getName(), true);
        }
        if (ja.contains(Integer.valueOf(36))) {
            C0262b.m213a(parcel, 36, icVar.getLatitude());
        }
        if (ja.contains(Integer.valueOf(37))) {
            C0262b.m219a(parcel, 37, icVar.jl(), i, true);
        }
        if (ja.contains(Integer.valueOf(42))) {
            C0262b.m222a(parcel, 42, icVar.getPlayerType(), true);
        }
        if (ja.contains(Integer.valueOf(43))) {
            C0262b.m222a(parcel, 43, icVar.getPostOfficeBoxNumber(), true);
        }
        if (ja.contains(Integer.valueOf(40))) {
            C0262b.m219a(parcel, 40, icVar.jm(), i, true);
        }
        if (ja.contains(Integer.valueOf(41))) {
            C0262b.m233b(parcel, 41, icVar.jn(), true);
        }
        if (ja.contains(Integer.valueOf(46))) {
            C0262b.m219a(parcel, 46, icVar.jo(), i, true);
        }
        if (ja.contains(Integer.valueOf(47))) {
            C0262b.m222a(parcel, 47, icVar.getStartDate(), true);
        }
        if (ja.contains(Integer.valueOf(44))) {
            C0262b.m222a(parcel, 44, icVar.getPostalCode(), true);
        }
        if (ja.contains(Integer.valueOf(45))) {
            C0262b.m222a(parcel, 45, icVar.getRatingValue(), true);
        }
        if (ja.contains(Integer.valueOf(51))) {
            C0262b.m222a(parcel, 51, icVar.getThumbnailUrl(), true);
        }
        if (ja.contains(Integer.valueOf(50))) {
            C0262b.m219a(parcel, 50, icVar.jp(), i, true);
        }
        if (ja.contains(Integer.valueOf(49))) {
            C0262b.m222a(parcel, 49, icVar.getText(), true);
        }
        if (ja.contains(Integer.valueOf(48))) {
            C0262b.m222a(parcel, 48, icVar.getStreetAddress(), true);
        }
        if (ja.contains(Integer.valueOf(55))) {
            C0262b.m222a(parcel, 55, icVar.getWidth(), true);
        }
        if (ja.contains(Integer.valueOf(54))) {
            C0262b.m222a(parcel, 54, icVar.getUrl(), true);
        }
        if (ja.contains(Integer.valueOf(53))) {
            C0262b.m222a(parcel, 53, icVar.getType(), true);
        }
        if (ja.contains(Integer.valueOf(52))) {
            C0262b.m222a(parcel, 52, icVar.getTickerSymbol(), true);
        }
        if (ja.contains(Integer.valueOf(56))) {
            C0262b.m222a(parcel, 56, icVar.getWorstRating(), true);
        }
        C0262b.m211F(parcel, p);
    }

    public ic aL(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        ic icVar = null;
        List list = null;
        ic icVar2 = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        List list2 = null;
        int i2 = 0;
        List list3 = null;
        ic icVar3 = null;
        List list4 = null;
        String str4 = null;
        String str5 = null;
        ic icVar4 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        List list5 = null;
        String str9 = null;
        String str10 = null;
        String str11 = null;
        String str12 = null;
        String str13 = null;
        String str14 = null;
        String str15 = null;
        String str16 = null;
        String str17 = null;
        ic icVar5 = null;
        String str18 = null;
        String str19 = null;
        String str20 = null;
        String str21 = null;
        ic icVar6 = null;
        double d = 0.0d;
        ic icVar7 = null;
        double d2 = 0.0d;
        String str22 = null;
        ic icVar8 = null;
        List list6 = null;
        String str23 = null;
        String str24 = null;
        String str25 = null;
        String str26 = null;
        ic icVar9 = null;
        String str27 = null;
        String str28 = null;
        String str29 = null;
        ic icVar10 = null;
        String str30 = null;
        String str31 = null;
        String str32 = null;
        String str33 = null;
        String str34 = null;
        String str35 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            ic icVar11;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(2));
                    icVar = icVar11;
                    break;
                case Std.STD_URI /*3*/:
                    list = C0261a.m171A(parcel, n);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case Std.STD_CLASS /*4*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(4));
                    icVar2 = icVar11;
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str2 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(6));
                    break;
                case Std.STD_PATTERN /*7*/:
                    str3 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(7));
                    break;
                case Std.STD_LOCALE /*8*/:
                    list2 = C0261a.m182c(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(8));
                    break;
                case Std.STD_CHARSET /*9*/:
                    i2 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(9));
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    list3 = C0261a.m182c(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(10));
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(11));
                    icVar3 = icVar11;
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    list4 = C0261a.m182c(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(12));
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    str4 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(13));
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    str5 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(14));
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(15));
                    icVar4 = icVar11;
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    str6 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(16));
                    break;
                case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                    str7 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(17));
                    break;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    str8 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(18));
                    break;
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    list5 = C0261a.m182c(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(19));
                    break;
                case HttpEngine.MAX_REDIRECTS /*20*/:
                    str9 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(20));
                    break;
                case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                    str10 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(21));
                    break;
                case C0065R.styleable.TwoWayView_android_fitsSystemWindows /*22*/:
                    str11 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(22));
                    break;
                case C0065R.styleable.TwoWayView_android_scrollbars /*23*/:
                    str12 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(23));
                    break;
                case C0065R.styleable.TwoWayView_android_fadingEdge /*24*/:
                    str13 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(24));
                    break;
                case C0065R.styleable.TwoWayView_android_fadingEdgeLength /*25*/:
                    str14 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(25));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusLeft /*26*/:
                    str15 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(26));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusRight /*27*/:
                    str16 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(27));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusUp /*28*/:
                    str17 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(28));
                    break;
                case C0065R.styleable.TwoWayView_android_nextFocusDown /*29*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(29));
                    icVar5 = icVar11;
                    break;
                case C0065R.styleable.TwoWayView_android_clickable /*30*/:
                    str18 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(30));
                    break;
                case C0065R.styleable.TwoWayView_android_longClickable /*31*/:
                    str19 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(31));
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_STOP /*32*/:
                    str20 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(32));
                    break;
                case CharsToNameCanonicalizer.HASH_MULT /*33*/:
                    str21 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(33));
                    break;
                case C0065R.styleable.TwoWayView_android_duplicateParentState /*34*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(34));
                    icVar6 = icVar11;
                    break;
                case C0065R.styleable.TwoWayView_android_drawSelectorOnTop /*36*/:
                    d = C0261a.m192l(parcel, n);
                    hashSet.add(Integer.valueOf(36));
                    break;
                case C0065R.styleable.TwoWayView_android_choiceMode /*37*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(37));
                    icVar7 = icVar11;
                    break;
                case C0065R.styleable.TwoWayView_android_minWidth /*38*/:
                    d2 = C0261a.m192l(parcel, n);
                    hashSet.add(Integer.valueOf(38));
                    break;
                case C0065R.styleable.TwoWayView_android_minHeight /*39*/:
                    str22 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(39));
                    break;
                case JSONzip.substringLimit /*40*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(40));
                    icVar8 = icVar11;
                    break;
                case C0065R.styleable.TwoWayView_android_keepScreenOn /*41*/:
                    list6 = C0261a.m182c(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(41));
                    break;
                case C0065R.styleable.TwoWayView_android_isScrollContainer /*42*/:
                    str23 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(42));
                    break;
                case C0065R.styleable.TwoWayView_android_hapticFeedbackEnabled /*43*/:
                    str24 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(43));
                    break;
                case C0065R.styleable.TwoWayView_android_onClick /*44*/:
                    str25 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(44));
                    break;
                case C0065R.styleable.TwoWayView_android_contentDescription /*45*/:
                    str26 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(45));
                    break;
                case C0065R.styleable.TwoWayView_android_scrollbarFadeDuration /*46*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(46));
                    icVar9 = icVar11;
                    break;
                case C0065R.styleable.TwoWayView_android_scrollbarDefaultDelayBeforeFade /*47*/:
                    str27 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(47));
                    break;
                case C0065R.styleable.TwoWayView_android_fadeScrollbars /*48*/:
                    str28 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(48));
                    break;
                case C0065R.styleable.TwoWayView_android_overScrollMode /*49*/:
                    str29 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(49));
                    break;
                case AdSize.PORTRAIT_AD_HEIGHT /*50*/:
                    icVar11 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(50));
                    icVar10 = icVar11;
                    break;
                case C0065R.styleable.TwoWayView_android_alpha /*51*/:
                    str30 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(51));
                    break;
                case C0065R.styleable.TwoWayView_android_transformPivotX /*52*/:
                    str31 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(52));
                    break;
                case C0065R.styleable.TwoWayView_android_transformPivotY /*53*/:
                    str32 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(53));
                    break;
                case C0065R.styleable.TwoWayView_android_translationX /*54*/:
                    str33 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(54));
                    break;
                case C0065R.styleable.TwoWayView_android_translationY /*55*/:
                    str34 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(55));
                    break;
                case C0065R.styleable.TwoWayView_android_scaleX /*56*/:
                    str35 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(56));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new ic(hashSet, i, icVar, list, icVar2, str, str2, str3, list2, i2, list3, icVar3, list4, str4, str5, icVar4, str6, str7, str8, list5, str9, str10, str11, str12, str13, str14, str15, str16, str17, icVar5, str18, str19, str20, str21, icVar6, d, icVar7, d2, str22, icVar8, list6, str23, str24, str25, str26, icVar9, str27, str28, str29, icVar10, str30, str31, str32, str33, str34, str35);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ic[] bO(int i) {
        return new ic[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aL(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bO(x0);
    }
}
