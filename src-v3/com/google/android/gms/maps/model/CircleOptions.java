package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0452v;

public final class CircleOptions implements SafeParcelable {
    public static final CircleOptionsCreator CREATOR;
    private LatLng SI;
    private double SJ;
    private float SK;
    private int SL;
    private int SM;
    private float SN;
    private boolean SO;
    private final int xH;

    static {
        CREATOR = new CircleOptionsCreator();
    }

    public CircleOptions() {
        this.SI = null;
        this.SJ = 0.0d;
        this.SK = 10.0f;
        this.SL = ViewCompat.MEASURED_STATE_MASK;
        this.SM = 0;
        this.SN = 0.0f;
        this.SO = true;
        this.xH = 1;
    }

    CircleOptions(int versionCode, LatLng center, double radius, float strokeWidth, int strokeColor, int fillColor, float zIndex, boolean visible) {
        this.SI = null;
        this.SJ = 0.0d;
        this.SK = 10.0f;
        this.SL = ViewCompat.MEASURED_STATE_MASK;
        this.SM = 0;
        this.SN = 0.0f;
        this.SO = true;
        this.xH = versionCode;
        this.SI = center;
        this.SJ = radius;
        this.SK = strokeWidth;
        this.SL = strokeColor;
        this.SM = fillColor;
        this.SN = zIndex;
        this.SO = visible;
    }

    public CircleOptions center(LatLng center) {
        this.SI = center;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public CircleOptions fillColor(int color) {
        this.SM = color;
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

    public CircleOptions radius(double radius) {
        this.SJ = radius;
        return this;
    }

    public CircleOptions strokeColor(int color) {
        this.SL = color;
        return this;
    }

    public CircleOptions strokeWidth(float width) {
        this.SK = width;
        return this;
    }

    public CircleOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0452v.iB()) {
            C0454b.m1270a(this, out, flags);
        } else {
            CircleOptionsCreator.m1254a(this, out, flags);
        }
    }

    public CircleOptions zIndex(float zIndex) {
        this.SN = zIndex;
        return this;
    }
}
