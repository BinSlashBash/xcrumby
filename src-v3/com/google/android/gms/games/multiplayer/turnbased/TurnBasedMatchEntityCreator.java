package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import com.crumby.C0065R;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayList;

public class TurnBasedMatchEntityCreator implements Creator<TurnBasedMatchEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m580a(TurnBasedMatchEntity turnBasedMatchEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m219a(parcel, 1, turnBasedMatchEntity.getGame(), i, false);
        C0262b.m222a(parcel, 2, turnBasedMatchEntity.getMatchId(), false);
        C0262b.m222a(parcel, 3, turnBasedMatchEntity.getCreatorId(), false);
        C0262b.m215a(parcel, 4, turnBasedMatchEntity.getCreationTimestamp());
        C0262b.m222a(parcel, 5, turnBasedMatchEntity.getLastUpdaterId(), false);
        C0262b.m215a(parcel, 6, turnBasedMatchEntity.getLastUpdatedTimestamp());
        C0262b.m222a(parcel, 7, turnBasedMatchEntity.getPendingParticipantId(), false);
        C0262b.m234c(parcel, 8, turnBasedMatchEntity.getStatus());
        C0262b.m234c(parcel, 10, turnBasedMatchEntity.getVariant());
        C0262b.m234c(parcel, 11, turnBasedMatchEntity.getVersion());
        C0262b.m226a(parcel, 12, turnBasedMatchEntity.getData(), false);
        C0262b.m233b(parcel, 13, turnBasedMatchEntity.getParticipants(), false);
        C0262b.m222a(parcel, 14, turnBasedMatchEntity.getRematchId(), false);
        C0262b.m226a(parcel, 15, turnBasedMatchEntity.getPreviousMatchData(), false);
        C0262b.m216a(parcel, 17, turnBasedMatchEntity.getAutoMatchCriteria(), false);
        C0262b.m234c(parcel, 16, turnBasedMatchEntity.getMatchNumber());
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, turnBasedMatchEntity.getVersionCode());
        C0262b.m225a(parcel, 19, turnBasedMatchEntity.isLocallyModified());
        C0262b.m234c(parcel, 18, turnBasedMatchEntity.getTurnStatus());
        C0262b.m222a(parcel, 21, turnBasedMatchEntity.getDescriptionParticipantId(), false);
        C0262b.m222a(parcel, 20, turnBasedMatchEntity.getDescription(), false);
        C0262b.m211F(parcel, p);
    }

    public TurnBasedMatchEntity createFromParcel(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        GameEntity gameEntity = null;
        String str = null;
        String str2 = null;
        long j = 0;
        String str3 = null;
        long j2 = 0;
        String str4 = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        byte[] bArr = null;
        ArrayList arrayList = null;
        String str5 = null;
        byte[] bArr2 = null;
        int i5 = 0;
        Bundle bundle = null;
        int i6 = 0;
        boolean z = false;
        String str6 = null;
        String str7 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    gameEntity = (GameEntity) C0261a.m176a(parcel, n, GameEntity.CREATOR);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    bArr = C0261a.m199q(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    arrayList = C0261a.m182c(parcel, n, ParticipantEntity.CREATOR);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    bArr2 = C0261a.m199q(parcel, n);
                    break;
                case TransportMediator.FLAG_KEY_MEDIA_PAUSE /*16*/:
                    i5 = C0261a.m187g(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_paddingRight /*17*/:
                    bundle = C0261a.m198p(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    i6 = C0261a.m187g(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case HttpEngine.MAX_REDIRECTS /*20*/:
                    str6 = C0261a.m195n(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                    str7 = C0261a.m195n(parcel, n);
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
            return new TurnBasedMatchEntity(i, gameEntity, str, str2, j, str3, j2, str4, i2, i3, i4, bArr, arrayList, str5, bArr2, i5, bundle, i6, z, str6, str7);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public TurnBasedMatchEntity[] newArray(int size) {
        return new TurnBasedMatchEntity[size];
    }
}
