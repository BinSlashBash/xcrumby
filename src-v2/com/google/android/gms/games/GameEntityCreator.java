/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;

public class GameEntityCreator
implements Parcelable.Creator<GameEntity> {
    static void a(GameEntity gameEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, gameEntity.getApplicationId(), false);
        b.a(parcel, 2, gameEntity.getDisplayName(), false);
        b.a(parcel, 3, gameEntity.getPrimaryCategory(), false);
        b.a(parcel, 4, gameEntity.getSecondaryCategory(), false);
        b.a(parcel, 5, gameEntity.getDescription(), false);
        b.a(parcel, 6, gameEntity.getDeveloperName(), false);
        b.a(parcel, 7, (Parcelable)gameEntity.getIconImageUri(), n2, false);
        b.a(parcel, 8, (Parcelable)gameEntity.getHiResImageUri(), n2, false);
        b.a(parcel, 9, (Parcelable)gameEntity.getFeaturedImageUri(), n2, false);
        b.a(parcel, 10, gameEntity.gb());
        b.a(parcel, 11, gameEntity.gd());
        b.a(parcel, 12, gameEntity.ge(), false);
        b.c(parcel, 13, gameEntity.gf());
        b.c(parcel, 14, gameEntity.getAchievementTotalCount());
        b.c(parcel, 15, gameEntity.getLeaderboardCount());
        b.a(parcel, 17, gameEntity.isTurnBasedMultiplayerEnabled());
        b.a(parcel, 16, gameEntity.isRealTimeMultiplayerEnabled());
        b.c(parcel, 1000, gameEntity.getVersionCode());
        b.a(parcel, 19, gameEntity.getHiResImageUrl(), false);
        b.a(parcel, 18, gameEntity.getIconImageUrl(), false);
        b.a(parcel, 21, gameEntity.isMuted());
        b.a(parcel, 20, gameEntity.getFeaturedImageUrl(), false);
        b.a(parcel, 22, gameEntity.gc());
        b.F(parcel, n3);
    }

    public GameEntity[] aS(int n2) {
        return new GameEntity[n2];
    }

    public GameEntity an(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        boolean bl2 = false;
        boolean bl3 = false;
        String string8 = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        boolean bl4 = false;
        boolean bl5 = false;
        String string9 = null;
        String string10 = null;
        String string11 = null;
        boolean bl6 = false;
        boolean bl7 = false;
        block25 : while (parcel.dataPosition() < n2) {
            int n7 = a.n(parcel);
            switch (a.R(n7)) {
                default: {
                    a.b(parcel, n7);
                    continue block25;
                }
                case 1: {
                    string2 = a.n(parcel, n7);
                    continue block25;
                }
                case 2: {
                    string3 = a.n(parcel, n7);
                    continue block25;
                }
                case 3: {
                    string4 = a.n(parcel, n7);
                    continue block25;
                }
                case 4: {
                    string5 = a.n(parcel, n7);
                    continue block25;
                }
                case 5: {
                    string6 = a.n(parcel, n7);
                    continue block25;
                }
                case 6: {
                    string7 = a.n(parcel, n7);
                    continue block25;
                }
                case 7: {
                    uri = (Uri)a.a(parcel, n7, Uri.CREATOR);
                    continue block25;
                }
                case 8: {
                    uri2 = (Uri)a.a(parcel, n7, Uri.CREATOR);
                    continue block25;
                }
                case 9: {
                    uri3 = (Uri)a.a(parcel, n7, Uri.CREATOR);
                    continue block25;
                }
                case 10: {
                    bl2 = a.c(parcel, n7);
                    continue block25;
                }
                case 11: {
                    bl3 = a.c(parcel, n7);
                    continue block25;
                }
                case 12: {
                    string8 = a.n(parcel, n7);
                    continue block25;
                }
                case 13: {
                    n4 = a.g(parcel, n7);
                    continue block25;
                }
                case 14: {
                    n5 = a.g(parcel, n7);
                    continue block25;
                }
                case 15: {
                    n6 = a.g(parcel, n7);
                    continue block25;
                }
                case 17: {
                    bl5 = a.c(parcel, n7);
                    continue block25;
                }
                case 16: {
                    bl4 = a.c(parcel, n7);
                    continue block25;
                }
                case 1000: {
                    n3 = a.g(parcel, n7);
                    continue block25;
                }
                case 19: {
                    string10 = a.n(parcel, n7);
                    continue block25;
                }
                case 18: {
                    string9 = a.n(parcel, n7);
                    continue block25;
                }
                case 21: {
                    bl6 = a.c(parcel, n7);
                    continue block25;
                }
                case 20: {
                    string11 = a.n(parcel, n7);
                    continue block25;
                }
                case 22: 
            }
            bl7 = a.c(parcel, n7);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new GameEntity(n3, string2, string3, string4, string5, string6, string7, uri, uri2, uri3, bl2, bl3, string8, n4, n5, n6, bl4, bl5, string9, string10, string11, bl6, bl7);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.an(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aS(n2);
    }
}

