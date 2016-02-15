package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.C0192R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0431a;
import com.google.android.gms.maps.internal.C0452v;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions implements SafeParcelable {
    public static final GoogleMapOptionsCreator CREATOR;
    private Boolean RI;
    private Boolean RJ;
    private int RK;
    private CameraPosition RL;
    private Boolean RM;
    private Boolean RN;
    private Boolean RO;
    private Boolean RP;
    private Boolean RQ;
    private Boolean RR;
    private final int xH;

    static {
        CREATOR = new GoogleMapOptionsCreator();
    }

    public GoogleMapOptions() {
        this.RK = -1;
        this.xH = 1;
    }

    GoogleMapOptions(int versionCode, byte zOrderOnTop, byte useViewLifecycleInFragment, int mapType, CameraPosition camera, byte zoomControlsEnabled, byte compassEnabled, byte scrollGesturesEnabled, byte zoomGesturesEnabled, byte tiltGesturesEnabled, byte rotateGesturesEnabled) {
        this.RK = -1;
        this.xH = versionCode;
        this.RI = C0431a.m1227a(zOrderOnTop);
        this.RJ = C0431a.m1227a(useViewLifecycleInFragment);
        this.RK = mapType;
        this.RL = camera;
        this.RM = C0431a.m1227a(zoomControlsEnabled);
        this.RN = C0431a.m1227a(compassEnabled);
        this.RO = C0431a.m1227a(scrollGesturesEnabled);
        this.RP = C0431a.m1227a(zoomGesturesEnabled);
        this.RQ = C0431a.m1227a(tiltGesturesEnabled);
        this.RR = C0431a.m1227a(rotateGesturesEnabled);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attrs, C0192R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(0)) {
            googleMapOptions.mapType(obtainAttributes.getInt(0, -1));
        }
        if (obtainAttributes.hasValue(13)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(13, false));
        }
        if (obtainAttributes.hasValue(12)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(12, false));
        }
        if (obtainAttributes.hasValue(6)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(6, true));
        }
        if (obtainAttributes.hasValue(7)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(7, true));
        }
        if (obtainAttributes.hasValue(8)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(8, true));
        }
        if (obtainAttributes.hasValue(9)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(9, true));
        }
        if (obtainAttributes.hasValue(11)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(11, true));
        }
        if (obtainAttributes.hasValue(10)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(10, true));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attrs));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    public GoogleMapOptions camera(CameraPosition camera) {
        this.RL = camera;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean enabled) {
        this.RN = Boolean.valueOf(enabled);
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
        return C0431a.m1228c(this.RI);
    }

    byte ih() {
        return C0431a.m1228c(this.RJ);
    }

    byte ii() {
        return C0431a.m1228c(this.RM);
    }

    byte ij() {
        return C0431a.m1228c(this.RN);
    }

    byte ik() {
        return C0431a.m1228c(this.RO);
    }

    byte il() {
        return C0431a.m1228c(this.RP);
    }

    byte im() {
        return C0431a.m1228c(this.RQ);
    }

    byte in() {
        return C0431a.m1228c(this.RR);
    }

    public GoogleMapOptions mapType(int mapType) {
        this.RK = mapType;
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean enabled) {
        this.RR = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean enabled) {
        this.RO = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean enabled) {
        this.RQ = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.RJ = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0452v.iB()) {
            C0430a.m1226a(this, out, flags);
        } else {
            GoogleMapOptionsCreator.m1224a(this, out, flags);
        }
    }

    public GoogleMapOptions zOrderOnTop(boolean zOrderOnTop) {
        this.RI = Boolean.valueOf(zOrderOnTop);
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean enabled) {
        this.RM = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean enabled) {
        this.RP = Boolean.valueOf(enabled);
        return this;
    }
}
