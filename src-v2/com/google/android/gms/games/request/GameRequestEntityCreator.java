/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.request.GameRequestEntity;
import java.util.ArrayList;
import java.util.List;

public class GameRequestEntityCreator
implements Parcelable.Creator<GameRequestEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(GameRequestEntity gameRequestEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, gameRequestEntity.getGame(), n2, false);
        b.c(parcel, 1000, gameRequestEntity.getVersionCode());
        b.a(parcel, 2, gameRequestEntity.getSender(), n2, false);
        b.a(parcel, 3, gameRequestEntity.getData(), false);
        b.a(parcel, 4, gameRequestEntity.getRequestId(), false);
        b.b(parcel, 5, gameRequestEntity.getRecipients(), false);
        b.c(parcel, 7, gameRequestEntity.getType());
        b.a(parcel, 9, gameRequestEntity.getCreationTimestamp());
        b.a(parcel, 10, gameRequestEntity.getExpirationTimestamp());
        b.a(parcel, 11, gameRequestEntity.hK(), false);
        b.c(parcel, 12, gameRequestEntity.getStatus());
        b.F(parcel, n3);
    }

    public GameRequestEntity createFromParcel(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        Object object = null;
        String string2 = null;
        ArrayList<PlayerEntity> arrayList = null;
        int n4 = 0;
        long l2 = 0;
        long l3 = 0;
        Bundle bundle = null;
        int n5 = 0;
        block13 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block13;
                }
                case 1: {
                    gameEntity = a.a(parcel, n6, GameEntity.CREATOR);
                    continue block13;
                }
                case 1000: {
                    n3 = a.g(parcel, n6);
                    continue block13;
                }
                case 2: {
                    playerEntity = a.a(parcel, n6, PlayerEntity.CREATOR);
                    continue block13;
                }
                case 3: {
                    object = a.q(parcel, n6);
                    continue block13;
                }
                case 4: {
                    string2 = a.n(parcel, n6);
                    continue block13;
                }
                case 5: {
                    arrayList = a.c(parcel, n6, PlayerEntity.CREATOR);
                    continue block13;
                }
                case 7: {
                    n4 = a.g(parcel, n6);
                    continue block13;
                }
                case 9: {
                    l2 = a.i(parcel, n6);
                    continue block13;
                }
                case 10: {
                    l3 = a.i(parcel, n6);
                    continue block13;
                }
                case 11: {
                    bundle = a.p(parcel, n6);
                    continue block13;
                }
                case 12: 
            }
            n5 = a.g(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new GameRequestEntity(n3, gameEntity, playerEntity, (byte[])object, string2, arrayList, n4, l2, l3, bundle, n5);
    }

    public GameRequestEntity[] newArray(int n2) {
        return new GameRequestEntity[n2];
    }
}

