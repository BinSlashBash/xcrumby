/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.TileCreator;
import com.google.android.gms.maps.model.i;

public final class Tile
implements SafeParcelable {
    public static final TileCreator CREATOR = new TileCreator();
    public final byte[] data;
    public final int height;
    public final int width;
    private final int xH;

    Tile(int n2, int n3, int n4, byte[] arrby) {
        this.xH = n2;
        this.width = n3;
        this.height = n4;
        this.data = arrby;
    }

    public Tile(int n2, int n3, byte[] arrby) {
        this(1, n2, n3, arrby);
    }

    public int describeContents() {
        return 0;
    }

    int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            i.a(this, parcel, n2);
            return;
        }
        TileCreator.a(this, parcel, n2);
    }
}

