/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.internal.ConnectionInfo;

public class ConnectionInfoCreator
implements Parcelable.Creator<ConnectionInfo> {
    static void a(ConnectionInfo connectionInfo, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.a(parcel, 1, connectionInfo.gi(), false);
        b.c(parcel, 1000, connectionInfo.getVersionCode());
        b.c(parcel, 2, connectionInfo.gj());
        b.F(parcel, n2);
    }

    public ConnectionInfo[] aW(int n2) {
        return new ConnectionInfo[n2];
    }

    public ConnectionInfo ap(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        String string2 = null;
        int n4 = 0;
        block5 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block5;
                }
                case 1: {
                    string2 = a.n(parcel, n5);
                    continue block5;
                }
                case 1000: {
                    n4 = a.g(parcel, n5);
                    continue block5;
                }
                case 2: 
            }
            n2 = a.g(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ConnectionInfo(n4, string2, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ap(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aW(n2);
    }
}

