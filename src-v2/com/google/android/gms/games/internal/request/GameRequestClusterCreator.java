/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.internal.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.internal.request.GameRequestCluster;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestEntity;
import com.google.android.gms.games.request.GameRequestEntityCreator;
import java.util.ArrayList;

public class GameRequestClusterCreator
implements Parcelable.Creator<GameRequestCluster> {
    static void a(GameRequestCluster gameRequestCluster, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.b(parcel, 1, gameRequestCluster.hz(), false);
        b.c(parcel, 1000, gameRequestCluster.getVersionCode());
        b.F(parcel, n2);
    }

    public GameRequestCluster at(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList arrayList = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    arrayList = a.c(parcel, n4, GameRequestEntity.CREATOR);
                    continue block4;
                }
                case 1000: 
            }
            n3 = a.g(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new GameRequestCluster(n3, arrayList);
    }

    public GameRequestCluster[] bl(int n2) {
        return new GameRequestCluster[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.at(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bl(n2);
    }
}

