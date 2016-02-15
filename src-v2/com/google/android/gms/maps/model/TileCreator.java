/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.Tile;

public class TileCreator
implements Parcelable.Creator<Tile> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(Tile tile, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, tile.getVersionCode());
        b.c(parcel, 2, tile.width);
        b.c(parcel, 3, tile.height);
        b.a(parcel, 4, tile.data, false);
        b.F(parcel, n2);
    }

    public Tile createFromParcel(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        Object object = null;
        int n4 = 0;
        int n5 = 0;
        block6 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block6;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    continue block6;
                }
                case 2: {
                    n4 = a.g(parcel, n6);
                    continue block6;
                }
                case 3: {
                    n2 = a.g(parcel, n6);
                    continue block6;
                }
                case 4: 
            }
            object = a.q(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new Tile(n5, n4, n2, (byte[])object);
    }

    public Tile[] newArray(int n2) {
        return new Tile[n2];
    }
}

