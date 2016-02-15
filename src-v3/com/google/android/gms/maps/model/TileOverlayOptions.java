package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0452v;
import com.google.android.gms.maps.model.internal.C0470i;
import com.google.android.gms.maps.model.internal.C0470i.C1032a;

public final class TileOverlayOptions implements SafeParcelable {
    public static final TileOverlayOptionsCreator CREATOR;
    private float SN;
    private boolean SO;
    private C0470i Tt;
    private TileProvider Tu;
    private boolean Tv;
    private final int xH;

    /* renamed from: com.google.android.gms.maps.model.TileOverlayOptions.1 */
    class C10121 implements TileProvider {
        private final C0470i Tw;
        final /* synthetic */ TileOverlayOptions Tx;

        C10121(TileOverlayOptions tileOverlayOptions) {
            this.Tx = tileOverlayOptions;
            this.Tw = this.Tx.Tt;
        }

        public Tile getTile(int x, int y, int zoom) {
            try {
                return this.Tw.getTile(x, y, zoom);
            } catch (RemoteException e) {
                return null;
            }
        }
    }

    /* renamed from: com.google.android.gms.maps.model.TileOverlayOptions.2 */
    class C14102 extends C1032a {
        final /* synthetic */ TileOverlayOptions Tx;
        final /* synthetic */ TileProvider Ty;

        C14102(TileOverlayOptions tileOverlayOptions, TileProvider tileProvider) {
            this.Tx = tileOverlayOptions;
            this.Ty = tileProvider;
        }

        public Tile getTile(int x, int y, int zoom) {
            return this.Ty.getTile(x, y, zoom);
        }
    }

    static {
        CREATOR = new TileOverlayOptionsCreator();
    }

    public TileOverlayOptions() {
        this.SO = true;
        this.Tv = true;
        this.xH = 1;
    }

    TileOverlayOptions(int versionCode, IBinder delegate, boolean visible, float zIndex, boolean fadeIn) {
        this.SO = true;
        this.Tv = true;
        this.xH = versionCode;
        this.Tt = C1032a.aK(delegate);
        this.Tu = this.Tt == null ? null : new C10121(this);
        this.SO = visible;
        this.SN = zIndex;
        this.Tv = fadeIn;
    }

    public int describeContents() {
        return 0;
    }

    public TileOverlayOptions fadeIn(boolean fadeIn) {
        this.Tv = fadeIn;
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

    public TileOverlayOptions tileProvider(TileProvider tileProvider) {
        this.Tu = tileProvider;
        this.Tt = this.Tu == null ? null : new C14102(this, tileProvider);
        return this;
    }

    public TileOverlayOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0452v.iB()) {
            C0471j.m1290a(this, out, flags);
        } else {
            TileOverlayOptionsCreator.m1267a(this, out, flags);
        }
    }

    public TileOverlayOptions zIndex(float zIndex) {
        this.SN = zIndex;
        return this;
    }
}
