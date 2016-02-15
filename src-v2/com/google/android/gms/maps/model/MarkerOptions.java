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
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptionsCreator;
import com.google.android.gms.maps.model.f;

public final class MarkerOptions
implements SafeParcelable {
    public static final MarkerOptionsCreator CREATOR = new MarkerOptionsCreator();
    private String EB;
    private boolean SO = true;
    private float SW = 0.5f;
    private float SX = 1.0f;
    private LatLng Sn;
    private String Tf;
    private BitmapDescriptor Tg;
    private boolean Th;
    private boolean Ti = false;
    private float Tj = 0.0f;
    private float Tk = 0.5f;
    private float Tl = 0.0f;
    private float mAlpha = 1.0f;
    private final int xH;

    public MarkerOptions() {
        this.xH = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    MarkerOptions(int n2, LatLng object, String string2, String string3, IBinder iBinder, float f2, float f3, boolean bl2, boolean bl3, boolean bl4, float f4, float f5, float f6, float f7) {
        this.xH = n2;
        this.Sn = object;
        this.EB = string2;
        this.Tf = string3;
        object = iBinder == null ? null : new BitmapDescriptor(d.a.K(iBinder));
        this.Tg = object;
        this.SW = f2;
        this.SX = f3;
        this.Th = bl2;
        this.SO = bl3;
        this.Ti = bl4;
        this.Tj = f4;
        this.Tk = f5;
        this.Tl = f6;
        this.mAlpha = f7;
    }

    public MarkerOptions alpha(float f2) {
        this.mAlpha = f2;
        return this;
    }

    public MarkerOptions anchor(float f2, float f3) {
        this.SW = f2;
        this.SX = f3;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public MarkerOptions draggable(boolean bl2) {
        this.Th = bl2;
        return this;
    }

    public MarkerOptions flat(boolean bl2) {
        this.Ti = bl2;
        return this;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getAnchorU() {
        return this.SW;
    }

    public float getAnchorV() {
        return this.SX;
    }

    public BitmapDescriptor getIcon() {
        return this.Tg;
    }

    public float getInfoWindowAnchorU() {
        return this.Tk;
    }

    public float getInfoWindowAnchorV() {
        return this.Tl;
    }

    public LatLng getPosition() {
        return this.Sn;
    }

    public float getRotation() {
        return this.Tj;
    }

    public String getSnippet() {
        return this.Tf;
    }

    public String getTitle() {
        return this.EB;
    }

    int getVersionCode() {
        return this.xH;
    }

    IBinder iE() {
        if (this.Tg == null) {
            return null;
        }
        return this.Tg.id().asBinder();
    }

    public MarkerOptions icon(BitmapDescriptor bitmapDescriptor) {
        this.Tg = bitmapDescriptor;
        return this;
    }

    public MarkerOptions infoWindowAnchor(float f2, float f3) {
        this.Tk = f2;
        this.Tl = f3;
        return this;
    }

    public boolean isDraggable() {
        return this.Th;
    }

    public boolean isFlat() {
        return this.Ti;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public MarkerOptions position(LatLng latLng) {
        this.Sn = latLng;
        return this;
    }

    public MarkerOptions rotation(float f2) {
        this.Tj = f2;
        return this;
    }

    public MarkerOptions snippet(String string2) {
        this.Tf = string2;
        return this;
    }

    public MarkerOptions title(String string2) {
        this.EB = string2;
        return this;
    }

    public MarkerOptions visible(boolean bl2) {
        this.SO = bl2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            f.a(this, parcel, n2);
            return;
        }
        MarkerOptionsCreator.a(this, parcel, n2);
    }
}

