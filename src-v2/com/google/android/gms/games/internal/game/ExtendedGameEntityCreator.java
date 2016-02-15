/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.internal.game;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.game.ExtendedGameEntity;
import com.google.android.gms.games.internal.game.GameBadge;
import com.google.android.gms.games.internal.game.GameBadgeEntity;
import com.google.android.gms.games.internal.game.GameBadgeEntityCreator;
import java.util.ArrayList;

public class ExtendedGameEntityCreator
implements Parcelable.Creator<ExtendedGameEntity> {
    static void a(ExtendedGameEntity extendedGameEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, extendedGameEntity.hf(), n2, false);
        b.c(parcel, 1000, extendedGameEntity.getVersionCode());
        b.c(parcel, 2, extendedGameEntity.gX());
        b.a(parcel, 3, extendedGameEntity.gY());
        b.c(parcel, 4, extendedGameEntity.gZ());
        b.a(parcel, 5, extendedGameEntity.ha());
        b.a(parcel, 6, extendedGameEntity.hb());
        b.a(parcel, 7, extendedGameEntity.hc(), false);
        b.a(parcel, 8, extendedGameEntity.hd());
        b.a(parcel, 9, extendedGameEntity.he(), false);
        b.b(parcel, 10, extendedGameEntity.gW(), false);
        b.F(parcel, n3);
    }

    public ExtendedGameEntity aq(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        GameEntity gameEntity = null;
        int n4 = 0;
        boolean bl2 = false;
        int n5 = 0;
        long l2 = 0;
        long l3 = 0;
        String string2 = null;
        long l4 = 0;
        String string3 = null;
        ArrayList<GameBadgeEntity> arrayList = null;
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
                    n4 = a.g(parcel, n6);
                    continue block13;
                }
                case 3: {
                    bl2 = a.c(parcel, n6);
                    continue block13;
                }
                case 4: {
                    n5 = a.g(parcel, n6);
                    continue block13;
                }
                case 5: {
                    l2 = a.i(parcel, n6);
                    continue block13;
                }
                case 6: {
                    l3 = a.i(parcel, n6);
                    continue block13;
                }
                case 7: {
                    string2 = a.n(parcel, n6);
                    continue block13;
                }
                case 8: {
                    l4 = a.i(parcel, n6);
                    continue block13;
                }
                case 9: {
                    string3 = a.n(parcel, n6);
                    continue block13;
                }
                case 10: 
            }
            arrayList = a.c(parcel, n6, GameBadgeEntity.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ExtendedGameEntity(n3, gameEntity, n4, bl2, n5, l2, l3, string2, l4, string3, arrayList);
    }

    public ExtendedGameEntity[] be(int n2) {
        return new ExtendedGameEntity[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aq(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.be(n2);
    }
}

