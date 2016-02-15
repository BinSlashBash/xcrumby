/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 */
package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.GroundOverlayOptionsCreator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.c;

public final class GroundOverlayOptions
implements SafeParcelable {
    public static final GroundOverlayOptionsCreator CREATOR = new GroundOverlayOptionsCreator();
    public static final float NO_DIMENSION = -1.0f;
    private float SG;
    private float SN;
    private boolean SO = true;
    private BitmapDescriptor SQ;
    private LatLng SR;
    private float SS;
    private float ST;
    private LatLngBounds SU;
    private float SV = 0.0f;
    private float SW = 0.5f;
    private float SX = 0.5f;
    private final int xH;

    public GroundOverlayOptions() {
        this.xH = 1;
    }

    GroundOverlayOptions(int n2, IBinder iBinder, LatLng latLng, float f2, float f3, LatLngBounds latLngBounds, float f4, float f5, boolean bl2, float f6, float f7, float f8) {
        this.xH = n2;
        this.SQ = new BitmapDescriptor(d.a.K(iBinder));
        this.SR = latLng;
        this.SS = f2;
        this.ST = f3;
        this.SU = latLngBounds;
        this.SG = f4;
        this.SN = f5;
        this.SO = bl2;
        this.SV = f6;
        this.SW = f7;
        this.SX = f8;
    }

    private GroundOverlayOptions a(LatLng latLng, float f2, float f3) {
        this.SR = latLng;
        this.SS = f2;
        this.ST = f3;
        return this;
    }

    public GroundOverlayOptions anchor(float f2, float f3) {
        this.SW = f2;
        this.SX = f3;
        return this;
    }

    public GroundOverlayOptions bearing(float f2) {
        this.SG = (f2 % 360.0f + 360.0f) % 360.0f;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public float getAnchorU() {
        return this.SW;
    }

    public float getAnchorV() {
        return this.SX;
    }

    public float getBearing() {
        return this.SG;
    }

    public LatLngBounds getBounds() {
        return this.SU;
    }

    public float getHeight() {
        return this.ST;
    }

    public BitmapDescriptor getImage() {
        return this.SQ;
    }

    public LatLng getLocation() {
        return this.SR;
    }

    public float getTransparency() {
        return this.SV;
    }

    int getVersionCode() {
        return this.xH;
    }

    public float getWidth() {
        return this.SS;
    }

    public float getZIndex() {
        return this.SN;
    }

    IBinder iD() {
        return this.SQ.id().asBinder();
    }

    public GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        this.SQ = bitmapDescriptor;
        return this;
    }

    public boolean isVisible() {
        return this.SO;
    }

    /*
     * Enabled aggressive block sorting
     */
    public GroundOverlayOptions position(LatLng latLng, float f2) {
        boolean bl2 = true;
        boolean bl3 = this.SU == null;
        fq.a(bl3, "Position has already been set using positionFromBounds");
        bl3 = latLng != null;
        fq.b(bl3, (Object)"Location must be specified");
        bl3 = f2 >= 0.0f ? bl2 : false;
        fq.b(bl3, (Object)"Width must be non-negative");
        return this.a(latLng, f2, -1.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    public GroundOverlayOptions position(LatLng latLng, float f2, float f3) {
        boolean bl2 = true;
        boolean bl3 = this.SU == null;
        fq.a(bl3, "Position has already been set using positionFromBounds");
        bl3 = latLng != null;
        fq.b(bl3, (Object)"Location must be specified");
        bl3 = f2 >= 0.0f;
        fq.b(bl3, (Object)"Width must be non-negative");
        bl3 = f3 >= 0.0f ? bl2 : false;
        fq.b(bl3, (Object)"Height must be non-negative");
        return this.a(latLng, f2, f3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        boolean bl2 = this.SR == null;
        fq.a(bl2, "Position has already been set using position: " + this.SR);
        this.SU = latLngBounds;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public GroundOverlayOptions transparency(float f2) {
        boolean bl2 = f2 >= 0.0f && f2 <= 1.0f;
        fq.b(bl2, (Object)"Transparency must be in the range [0..1]");
        this.SV = f2;
        return this;
    }

    public GroundOverlayOptions visible(boolean bl2) {
        this.SO = bl2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            c.a(this, parcel, n2);
            return;
        }
        GroundOverlayOptionsCreator.a(this, parcel, n2);
    }

    public GroundOverlayOptions zIndex(float f2) {
        this.SN = f2;
        return this;
    }
}

