/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.internal.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.internal.multiplayer.ZInvitationCluster;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationEntity;
import java.util.ArrayList;

public class InvitationClusterCreator
implements Parcelable.Creator<ZInvitationCluster> {
    static void a(ZInvitationCluster zInvitationCluster, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.b(parcel, 1, zInvitationCluster.ho(), false);
        b.c(parcel, 1000, zInvitationCluster.getVersionCode());
        b.F(parcel, n2);
    }

    public ZInvitationCluster as(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList<InvitationEntity> arrayList = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    arrayList = a.c(parcel, n4, InvitationEntity.CREATOR);
                    continue block4;
                }
                case 1000: 
            }
            n3 = a.g(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ZInvitationCluster(n3, arrayList);
    }

    public ZInvitationCluster[] bi(int n2) {
        return new ZInvitationCluster[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.as(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bi(n2);
    }
}

