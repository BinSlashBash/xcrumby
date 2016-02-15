/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.internal.game.GameBadgeEntity;

public class GameBadgeEntityCreator
implements Parcelable.Creator<GameBadgeEntity> {
    static void a(GameBadgeEntity gameBadgeEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, gameBadgeEntity.getType());
        b.c(parcel, 1000, gameBadgeEntity.getVersionCode());
        b.a(parcel, 2, gameBadgeEntity.getTitle(), false);
        b.a(parcel, 3, gameBadgeEntity.getDescription(), false);
        b.a(parcel, 4, (Parcelable)gameBadgeEntity.getIconImageUri(), n2, false);
        b.F(parcel, n3);
    }

    public GameBadgeEntity ar(Parcel parcel) {
        int n2 = 0;
        Uri uri = null;
        int n3 = a.o(parcel);
        String string2 = null;
        String string3 = null;
        int n4 = 0;
        block7 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block7;
                }
                case 1: {
                    n2 = a.g(parcel, n5);
                    continue block7;
                }
                case 1000: {
                    n4 = a.g(parcel, n5);
                    continue block7;
                }
                case 2: {
                    string3 = a.n(parcel, n5);
                    continue block7;
                }
                case 3: {
                    string2 = a.n(parcel, n5);
                    continue block7;
                }
                case 4: 
            }
            uri = (Uri)a.a(parcel, n5, Uri.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new GameBadgeEntity(n4, n2, string3, string2, uri);
    }

    public GameBadgeEntity[] bg(int n2) {
        return new GameBadgeEntity[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ar(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bg(n2);
    }
}

