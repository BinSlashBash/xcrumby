/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchEntity;
import java.util.ArrayList;

public class TurnBasedMatchEntityCreator
implements Parcelable.Creator<TurnBasedMatchEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(TurnBasedMatchEntity turnBasedMatchEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, turnBasedMatchEntity.getGame(), n2, false);
        b.a(parcel, 2, turnBasedMatchEntity.getMatchId(), false);
        b.a(parcel, 3, turnBasedMatchEntity.getCreatorId(), false);
        b.a(parcel, 4, turnBasedMatchEntity.getCreationTimestamp());
        b.a(parcel, 5, turnBasedMatchEntity.getLastUpdaterId(), false);
        b.a(parcel, 6, turnBasedMatchEntity.getLastUpdatedTimestamp());
        b.a(parcel, 7, turnBasedMatchEntity.getPendingParticipantId(), false);
        b.c(parcel, 8, turnBasedMatchEntity.getStatus());
        b.c(parcel, 10, turnBasedMatchEntity.getVariant());
        b.c(parcel, 11, turnBasedMatchEntity.getVersion());
        b.a(parcel, 12, turnBasedMatchEntity.getData(), false);
        b.b(parcel, 13, turnBasedMatchEntity.getParticipants(), false);
        b.a(parcel, 14, turnBasedMatchEntity.getRematchId(), false);
        b.a(parcel, 15, turnBasedMatchEntity.getPreviousMatchData(), false);
        b.a(parcel, 17, turnBasedMatchEntity.getAutoMatchCriteria(), false);
        b.c(parcel, 16, turnBasedMatchEntity.getMatchNumber());
        b.c(parcel, 1000, turnBasedMatchEntity.getVersionCode());
        b.a(parcel, 19, turnBasedMatchEntity.isLocallyModified());
        b.c(parcel, 18, turnBasedMatchEntity.getTurnStatus());
        b.a(parcel, 21, turnBasedMatchEntity.getDescriptionParticipantId(), false);
        b.a(parcel, 20, turnBasedMatchEntity.getDescription(), false);
        b.F(parcel, n3);
    }

    public TurnBasedMatchEntity createFromParcel(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        GameEntity gameEntity = null;
        String string2 = null;
        String string3 = null;
        long l2 = 0;
        String string4 = null;
        long l3 = 0;
        String string5 = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        Object object = null;
        ArrayList<ParticipantEntity> arrayList = null;
        String string6 = null;
        Object object2 = null;
        int n7 = 0;
        Bundle bundle = null;
        int n8 = 0;
        boolean bl2 = false;
        String string7 = null;
        String string8 = null;
        block23 : while (parcel.dataPosition() < n2) {
            int n9 = a.n(parcel);
            switch (a.R(n9)) {
                default: {
                    a.b(parcel, n9);
                    continue block23;
                }
                case 1: {
                    gameEntity = a.a(parcel, n9, GameEntity.CREATOR);
                    continue block23;
                }
                case 2: {
                    string2 = a.n(parcel, n9);
                    continue block23;
                }
                case 3: {
                    string3 = a.n(parcel, n9);
                    continue block23;
                }
                case 4: {
                    l2 = a.i(parcel, n9);
                    continue block23;
                }
                case 5: {
                    string4 = a.n(parcel, n9);
                    continue block23;
                }
                case 6: {
                    l3 = a.i(parcel, n9);
                    continue block23;
                }
                case 7: {
                    string5 = a.n(parcel, n9);
                    continue block23;
                }
                case 8: {
                    n4 = a.g(parcel, n9);
                    continue block23;
                }
                case 10: {
                    n5 = a.g(parcel, n9);
                    continue block23;
                }
                case 11: {
                    n6 = a.g(parcel, n9);
                    continue block23;
                }
                case 12: {
                    object = a.q(parcel, n9);
                    continue block23;
                }
                case 13: {
                    arrayList = a.c(parcel, n9, ParticipantEntity.CREATOR);
                    continue block23;
                }
                case 14: {
                    string6 = a.n(parcel, n9);
                    continue block23;
                }
                case 15: {
                    object2 = a.q(parcel, n9);
                    continue block23;
                }
                case 17: {
                    bundle = a.p(parcel, n9);
                    continue block23;
                }
                case 16: {
                    n7 = a.g(parcel, n9);
                    continue block23;
                }
                case 1000: {
                    n3 = a.g(parcel, n9);
                    continue block23;
                }
                case 19: {
                    bl2 = a.c(parcel, n9);
                    continue block23;
                }
                case 18: {
                    n8 = a.g(parcel, n9);
                    continue block23;
                }
                case 21: {
                    string8 = a.n(parcel, n9);
                    continue block23;
                }
                case 20: 
            }
            string7 = a.n(parcel, n9);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new TurnBasedMatchEntity(n3, gameEntity, string2, string3, l2, string4, l3, string5, n4, n5, n6, (byte[])object, arrayList, string6, (byte[])object2, n7, bundle, n8, bl2, string7, string8);
    }

    public TurnBasedMatchEntity[] newArray(int n2) {
        return new TurnBasedMatchEntity[n2];
    }
}

