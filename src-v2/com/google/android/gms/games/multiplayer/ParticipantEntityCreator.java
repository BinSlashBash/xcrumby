/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.ParticipantResultCreator;

public class ParticipantEntityCreator
implements Parcelable.Creator<ParticipantEntity> {
    static void a(ParticipantEntity participantEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, participantEntity.getParticipantId(), false);
        b.a(parcel, 2, participantEntity.getDisplayName(), false);
        b.a(parcel, 3, (Parcelable)participantEntity.getIconImageUri(), n2, false);
        b.a(parcel, 4, (Parcelable)participantEntity.getHiResImageUri(), n2, false);
        b.c(parcel, 5, participantEntity.getStatus());
        b.a(parcel, 6, participantEntity.gi(), false);
        b.a(parcel, 7, participantEntity.isConnectedToRoom());
        b.a(parcel, 8, participantEntity.getPlayer(), n2, false);
        b.c(parcel, 9, participantEntity.getCapabilities());
        b.a(parcel, 10, participantEntity.getResult(), n2, false);
        b.a(parcel, 11, participantEntity.getIconImageUrl(), false);
        b.a(parcel, 12, participantEntity.getHiResImageUrl(), false);
        b.c(parcel, 1000, participantEntity.getVersionCode());
        b.F(parcel, n3);
    }

    public ParticipantEntity av(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        Uri uri = null;
        Uri uri2 = null;
        int n4 = 0;
        String string4 = null;
        boolean bl2 = false;
        PlayerEntity playerEntity = null;
        int n5 = 0;
        ParticipantResult participantResult = null;
        String string5 = null;
        String string6 = null;
        block15 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block15;
                }
                case 1: {
                    string2 = a.n(parcel, n6);
                    continue block15;
                }
                case 2: {
                    string3 = a.n(parcel, n6);
                    continue block15;
                }
                case 3: {
                    uri = (Uri)a.a(parcel, n6, Uri.CREATOR);
                    continue block15;
                }
                case 4: {
                    uri2 = (Uri)a.a(parcel, n6, Uri.CREATOR);
                    continue block15;
                }
                case 5: {
                    n4 = a.g(parcel, n6);
                    continue block15;
                }
                case 6: {
                    string4 = a.n(parcel, n6);
                    continue block15;
                }
                case 7: {
                    bl2 = a.c(parcel, n6);
                    continue block15;
                }
                case 8: {
                    playerEntity = a.a(parcel, n6, PlayerEntity.CREATOR);
                    continue block15;
                }
                case 9: {
                    n5 = a.g(parcel, n6);
                    continue block15;
                }
                case 10: {
                    participantResult = (ParticipantResult)a.a(parcel, n6, ParticipantResult.CREATOR);
                    continue block15;
                }
                case 11: {
                    string5 = a.n(parcel, n6);
                    continue block15;
                }
                case 12: {
                    string6 = a.n(parcel, n6);
                    continue block15;
                }
                case 1000: 
            }
            n3 = a.g(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ParticipantEntity(n3, string2, string3, uri, uri2, n4, string4, bl2, playerEntity, n5, participantResult, string5, string6);
    }

    public ParticipantEntity[] bo(int n2) {
        return new ParticipantEntity[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.av(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bo(n2);
    }
}

