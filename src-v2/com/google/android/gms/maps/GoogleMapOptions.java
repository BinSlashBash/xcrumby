/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.util.AttributeSet
 */
package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.GoogleMapOptionsCreator;
import com.google.android.gms.maps.a;
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions
implements SafeParcelable {
    public static final GoogleMapOptionsCreator CREATOR = new GoogleMapOptionsCreator();
    private Boolean RI;
    private Boolean RJ;
    private int RK = -1;
    private CameraPosition RL;
    private Boolean RM;
    private Boolean RN;
    private Boolean RO;
    private Boolean RP;
    private Boolean RQ;
    private Boolean RR;
    private final int xH;

    public GoogleMapOptions() {
        this.xH = 1;
    }

    GoogleMapOptions(int n2, byte by2, byte by3, int n3, CameraPosition cameraPosition, byte by4, byte by5, byte by6, byte by7, byte by8, byte by9) {
        this.xH = n2;
        this.RI = com.google.android.gms.maps.internal.a.a(by2);
        this.RJ = com.google.android.gms.maps.internal.a.a(by3);
        this.RK = n3;
        this.RL = cameraPosition;
        this.RM = com.google.android.gms.maps.internal.a.a(by4);
        this.RN = com.google.android.gms.maps.internal.a.a(by5);
        this.RO = com.google.android.gms.maps.internal.a.a(by6);
        this.RP = com.google.android.gms.maps.internal.a.a(by7);
        this.RQ = com.google.android.gms.maps.internal.a.a(by8);
        this.RR = com.google.android.gms.maps.internal.a.a(by9);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray typedArray = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (typedArray.hasValue(0)) {
            googleMapOptions.mapType(typedArray.getInt(0, -1));
        }
        if (typedArray.hasValue(13)) {
            googleMapOptions.zOrderOnTop(typedArray.getBoolean(13, false));
        }
        if (typedArray.hasValue(12)) {
            googleMapOptions.useViewLifecycleInFragment(typedArray.getBoolean(12, false));
        }
        if (typedArray.hasValue(6)) {
            googleMapOptions.compassEnabled(typedArray.getBoolean(6, true));
        }
        if (typedArray.hasValue(7)) {
            googleMapOptions.rotateGesturesEnabled(typedArray.getBoolean(7, true));
        }
        if (typedArray.hasValue(8)) {
            googleMapOptions.scrollGesturesEnabled(typedArray.getBoolean(8, true));
        }
        if (typedArray.hasValue(9)) {
            googleMapOptions.tiltGesturesEnabled(typedArray.getBoolean(9, true));
        }
        if (typedArray.hasValue(11)) {
            googleMapOptions.zoomGesturesEnabled(typedArray.getBoolean(11, true));
        }
        if (typedArray.hasValue(10)) {
            googleMapOptions.zoomControlsEnabled(typedArray.getBoolean(10, true));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attributeSet));
        typedArray.recycle();
        return googleMapOptions;
    }

    public GoogleMapOptions camera(CameraPosition cameraPosition) {
        this.RL = cameraPosition;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean bl2) {
        this.RN = bl2;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public CameraPosition getCamera() {
        return this.RL;
    }

    public Boolean getCompassEnabled() {
        return this.RN;
    }

    public int getMapType() {
        return this.RK;
    }

    public Boolean getRotateGesturesEnabled() {
        return this.RR;
    }

    public Boolean getScrollGesturesEnabled() {
        return this.RO;
    }

    public Boolean getTiltGesturesEnabled() {
        return this.RQ;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.RJ;
    }

    int getVersionCode() {
        return this.xH;
    }

    public Boolean getZOrderOnTop() {
        return this.RI;
    }

    public Boolean getZoomControlsEnabled() {
        return this.RM;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.RP;
    }

    byte ig() {
        return com.google.android.gms.maps.internal.a.c(this.RI);
    }

    byte ih() {
        return com.google.android.gms.maps.internal.a.c(this.RJ);
    }

    byte ii() {
        return com.google.android.gms.maps.internal.a.c(this.RM);
    }

    byte ij() {
        return com.google.android.gms.maps.internal.a.c(this.RN);
    }

    byte ik() {
        return com.google.android.gms.maps.internal.a.c(this.RO);
    }

    byte il() {
        return com.google.android.gms.maps.internal.a.c(this.RP);
    }

    byte im() {
        return com.google.android.gms.maps.internal.a.c(this.RQ);
    }

    byte in() {
        return com.google.android.gms.maps.internal.a.c(this.RR);
    }

    public GoogleMapOptions mapType(int n2) {
        this.RK = n2;
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean bl2) {
        this.RR = bl2;
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean bl2) {
        this.RO = bl2;
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean bl2) {
        this.RQ = bl2;
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean bl2) {
        this.RJ = bl2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            a.a(this, parcel, n2);
            return;
        }
        GoogleMapOptionsCreator.a(this, parcel, n2);
    }

    public GoogleMapOptions zOrderOnTop(boolean bl2) {
        this.RI = bl2;
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean bl2) {
        this.RM = bl2;
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean bl2) {
        this.RP = bl2;
        return this;
    }
}

