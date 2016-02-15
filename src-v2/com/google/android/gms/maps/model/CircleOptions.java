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
import com.google.android.gms.maps.model.CircleOptionsCreator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.b;

public final class CircleOptions
implements SafeParcelable {
    public static final CircleOptionsCreator CREATOR = new CircleOptionsCreator();
    private LatLng SI = null;
    private double SJ = 0.0;
    private float SK = 10.0f;
    private int SL = -16777216;
    private int SM = 0;
    private float SN = 0.0f;
    private boolean SO = true;
    private final int xH;

    public CircleOptions() {
        this.xH = 1;
    }

    CircleOptions(int n2, LatLng latLng, double d2, float f2, int n3, int n4, float f3, boolean bl2) {
        this.xH = n2;
        this.SI = latLng;
        this.SJ = d2;
        this.SK = f2;
        this.SL = n3;
        this.SM = n4;
        this.SN = f3;
        this.SO = bl2;
    }

    public CircleOptions center(LatLng latLng) {
        this.SI = latLng;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public CircleOptions fillColor(int n2) {
        this.SM = n2;
        return this;
    }

    public LatLng getCenter() {
        return this.SI;
    }

    public int getFillColor() {
        return this.SM;
    }

    public double getRadius() {
        return this.SJ;
    }

    public int getStrokeColor() {
        return this.SL;
    }

    public float getStrokeWidth() {
        return this.SK;
    }

    int getVersionCode() {
        return this.xH;
    }

    public float getZIndex() {
        return this.SN;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public CircleOptions radius(double d2) {
        this.SJ = d2;
        return this;
    }

    public CircleOptions strokeColor(int n2) {
        this.SL = n2;
        return this;
    }

    public CircleOptions strokeWidth(float f2) {
        this.SK = f2;
        return this;
    }

    public CircleOptions visible(boolean bl2) {
        this.SO = bl2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            b.a(this, parcel, n2);
            return;
        }
        CircleOptionsCreator.a(this, parcel, n2);
    }

    public CircleOptions zIndex(float f2) {
        this.SN = f2;
        return this;
    }
}

