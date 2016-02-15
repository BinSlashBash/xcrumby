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
import com.google.android.gms.games.PlayerEntity;

public class PlayerEntityCreator
implements Parcelable.Creator<PlayerEntity> {
    static void a(PlayerEntity playerEntity, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, playerEntity.getPlayerId(), false);
        b.c(parcel, 1000, playerEntity.getVersionCode());
        b.a(parcel, 2, playerEntity.getDisplayName(), false);
        b.a(parcel, 3, (Parcelable)playerEntity.getIconImageUri(), n2, false);
        b.a(parcel, 4, (Parcelable)playerEntity.getHiResImageUri(), n2, false);
        b.a(parcel, 5, playerEntity.getRetrievedTimestamp());
        b.c(parcel, 6, playerEntity.gh());
        b.a(parcel, 7, playerEntity.getLastPlayedWithTimestamp());
        b.a(parcel, 8, playerEntity.getIconImageUrl(), false);
        b.a(parcel, 9, playerEntity.getHiResImageUrl(), false);
        b.F(parcel, n3);
    }

    public PlayerEntity[] aT(int n2) {
        return new PlayerEntity[n2];
    }

    public PlayerEntity ao(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        Uri uri = null;
        Uri uri2 = null;
        long l2 = 0;
        int n4 = 0;
        long l3 = 0;
        String string4 = null;
        String string5 = null;
        block12 : while (parcel.dataPosition() < n2) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block12;
                }
                case 1: {
                    string2 = a.n(parcel, n5);
                    continue block12;
                }
                case 1000: {
                    n3 = a.g(parcel, n5);
                    continue block12;
                }
                case 2: {
                    string3 = a.n(parcel, n5);
                    continue block12;
                }
                case 3: {
                    uri = (Uri)a.a(parcel, n5, Uri.CREATOR);
                    continue block12;
                }
                case 4: {
                    uri2 = (Uri)a.a(parcel, n5, Uri.CREATOR);
                    continue block12;
                }
                case 5: {
                    l2 = a.i(parcel, n5);
                    continue block12;
                }
                case 6: {
                    n4 = a.g(parcel, n5);
                    continue block12;
                }
                case 7: {
                    l3 = a.i(parcel, n5);
                    continue block12;
                }
                case 8: {
                    string4 = a.n(parcel, n5);
                    continue block12;
                }
                case 9: 
            }
            string5 = a.n(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new PlayerEntity(n3, string2, string3, uri, uri2, l2, n4, l3, string4, string5);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ao(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aT(n2);
    }
}

