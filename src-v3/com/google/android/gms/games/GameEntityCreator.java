package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.squareup.okhttp.internal.http.HttpEngine;

public class GameEntityCreator implements Creator<GameEntity> {
    static void m360a(GameEntity gameEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, gameEntity.getApplicationId(), false);
        C0262b.m222a(parcel, 2, gameEntity.getDisplayName(), false);
        C0262b.m222a(parcel, 3, gameEntity.getPrimaryCategory(), false);
        C0262b.m222a(parcel, 4, gameEntity.getSecondaryCategory(), false);
        C0262b.m222a(parcel, 5, gameEntity.getDescription(), false);
        C0262b.m222a(parcel, 6, gameEntity.getDeveloperName(), false);
        C0262b.m219a(parcel, 7, gameEntity.getIconImageUri(), i, false);
        C0262b.m219a(parcel, 8, gameEntity.getHiResImageUri(), i, false);
        C0262b.m219a(parcel, 9, gameEntity.getFeaturedImageUri(), i, false);
        C0262b.m225a(parcel, 10, gameEntity.gb());
        C0262b.m225a(parcel, 11, gameEntity.gd());
        C0262b.m222a(parcel, 12, gameEntity.ge(), false);
        C0262b.m234c(parcel, 13, gameEntity.gf());
        C0262b.m234c(parcel, 14, gameEntity.getAchievementTotalCount());
        C0262b.m234c(parcel, 15, gameEntity.getLeaderboardCount());
        C0262b.m225a(parcel, 17, gameEntity.isTurnBasedMultiplayerEnabled());
        C0262b.m225a(parcel, 16, gameEntity.isRealTimeMultiplayerEnabled());
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, gameEntity.getVersionCode());
        C0262b.m222a(parcel, 19, gameEntity.getHiResImageUrl(), false);
        C0262b.m222a(parcel, 18, gameEntity.getIconImageUrl(), false);
        C0262b.m225a(parcel, 21, gameEntity.isMuted());
        C0262b.m222a(parcel, 20, gameEntity.getFeaturedImageUrl(), false);
        C0262b.m225a(parcel, 22, gameEntity.gc());
        C0262b.m211F(parcel, p);
    }

    public GameEntity[] aS(int i) {
        return new GameEntity[i];
    }

    public GameEntity an(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        boolean z = false;
        boolean z2 = false;
        String str7 = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z3 = false;
        boolean z4 = false;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        boolean z5 = false;
        boolean z6 = false;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str6 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    uri = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    break;
                case Std.STD_LOCALE /*8*/:
                    uri2 = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    break;
                case Std.STD_CHARSET /*9*/:
                    uri3 = (Uri) C0261a.m176a(parcel, n, Uri.CREATOR);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    str7 = C0261a.m195n(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    z3 = C0261a.m183c(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                    z4 = C0261a.m183c(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    str8 = C0261a.m195n(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    str9 = C0261a.m195n(parcel, n);
                    break;
                case HttpEngine.MAX_REDIRECTS /*20*/:
                    str10 = C0261a.m195n(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                    z5 = C0261a.m183c(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_fitsSystemWindows /*22*/:
                    z6 = C0261a.m183c(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new GameEntity(i, str, str2, str3, str4, str5, str6, uri, uri2, uri3, z, z2, str7, i2, i3, i4, z3, z4, str8, str9, str10, z5, z6);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return an(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aS(x0);
    }
}
