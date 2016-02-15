/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlayOptionsCreator;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.internal.i;
import com.google.android.gms.maps.model.j;

public final class TileOverlayOptions
implements SafeParcelable {
    public static final TileOverlayOptionsCreator CREATOR = new TileOverlayOptionsCreator();
    private float SN;
    private boolean SO = true;
    private i Tt;
    private TileProvider Tu;
    private boolean Tv = true;
    private final int xH;

    public TileOverlayOptions() {
        this.xH = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    TileOverlayOptions(int n2, IBinder object, boolean bl2, float f2, boolean bl3) {
        this.xH = n2;
        this.Tt = i.a.aK((IBinder)object);
        object = this.Tt == null ? null : new TileProvider(){
            private final i Tw;

            @Override
            public Tile getTile(int n2, int n3, int n4) {
                try {
                    Tile tile = this.Tw.getTile(n2, n3, n4);
                    return tile;
                }
                catch (RemoteException var4_5) {
                    return null;
                }
            }
        };
        this.Tu = object;
        this.SO = bl2;
        this.SN = f2;
        this.Tv = bl3;
    }

    public int describeContents() {
        return 0;
    }

    public TileOverlayOptions fadeIn(boolean bl2) {
        this.Tv = bl2;
        return this;
    }

    public boolean getFadeIn() {
        return this.Tv;
    }

    public TileProvider getTileProvider() {
        return this.Tu;
    }

    int getVersionCode() {
        return this.xH;
    }

    public float getZIndex() {
        return this.SN;
    }

    IBinder iG() {
        return this.Tt.asBinder();
    }

    public boolean isVisible() {
        return this.SO;
    }

    /*
     * Enabled aggressive block sorting
     */
    public TileOverlayOptions tileProvider(TileProvider object) {
        this.Tu = object;
        object = this.Tu == null ? null : new i.a((TileProvider)object){
            final /* synthetic */ TileProvider Ty;

            @Override
            public Tile getTile(int n2, int n3, int n4) {
                return this.Ty.getTile(n2, n3, n4);
            }
        };
        this.Tt = object;
        return this;
    }

    public TileOverlayOptions visible(boolean bl2) {
        this.SO = bl2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            j.a(this, parcel, n2);
            return;
        }
        TileOverlayOptionsCreator.a(this, parcel, n2);
    }

    public TileOverlayOptions zIndex(float f2) {
        this.SN = f2;
        return this;
    }

}

