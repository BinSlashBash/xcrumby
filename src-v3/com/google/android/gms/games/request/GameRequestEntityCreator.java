package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.ArrayList;

public class GameRequestEntityCreator implements Creator<GameRequestEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m581a(GameRequestEntity gameRequestEntity, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m219a(parcel, 1, gameRequestEntity.getGame(), i, false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, gameRequestEntity.getVersionCode());
        C0262b.m219a(parcel, 2, gameRequestEntity.getSender(), i, false);
        C0262b.m226a(parcel, 3, gameRequestEntity.getData(), false);
        C0262b.m222a(parcel, 4, gameRequestEntity.getRequestId(), false);
        C0262b.m233b(parcel, 5, gameRequestEntity.getRecipients(), false);
        C0262b.m234c(parcel, 7, gameRequestEntity.getType());
        C0262b.m215a(parcel, 9, gameRequestEntity.getCreationTimestamp());
        C0262b.m215a(parcel, 10, gameRequestEntity.getExpirationTimestamp());
        C0262b.m216a(parcel, 11, gameRequestEntity.hK(), false);
        C0262b.m234c(parcel, 12, gameRequestEntity.getStatus());
        C0262b.m211F(parcel, p);
    }

    public GameRequestEntity createFromParcel(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        byte[] bArr = null;
        String str = null;
        ArrayList arrayList = null;
        int i2 = 0;
        long j = 0;
        long j2 = 0;
        Bundle bundle = null;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    gameEntity = (GameEntity) C0261a.m176a(parcel, n, GameEntity.CREATOR);
                    break;
                case Std.STD_URL /*2*/:
                    playerEntity = (PlayerEntity) C0261a.m176a(parcel, n, PlayerEntity.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    bArr = C0261a.m199q(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    arrayList = C0261a.m182c(parcel, n, PlayerEntity.CREATOR);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    bundle = C0261a.m198p(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    i3 = C0261a.m187g(parcel, n);
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
            return new GameRequestEntity(i, gameEntity, playerEntity, bArr, str, arrayList, i2, j, j2, bundle, i3);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public GameRequestEntity[] newArray(int size) {
        return new GameRequestEntity[size];
    }
}
