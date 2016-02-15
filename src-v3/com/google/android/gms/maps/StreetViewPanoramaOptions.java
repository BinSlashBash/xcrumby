package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0431a;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class StreetViewPanoramaOptions implements SafeParcelable {
    public static final StreetViewPanoramaOptionsCreator CREATOR;
    private Boolean RJ;
    private Boolean RP;
    private StreetViewPanoramaCamera Sl;
    private String Sm;
    private LatLng Sn;
    private Integer So;
    private Boolean Sp;
    private Boolean Sq;
    private Boolean Sr;
    private final int xH;

    static {
        CREATOR = new StreetViewPanoramaOptionsCreator();
    }

    public StreetViewPanoramaOptions() {
        this.Sp = Boolean.valueOf(true);
        this.RP = Boolean.valueOf(true);
        this.Sq = Boolean.valueOf(true);
        this.Sr = Boolean.valueOf(true);
        this.xH = 1;
    }

    StreetViewPanoramaOptions(int versionCode, StreetViewPanoramaCamera camera, String panoId, LatLng position, Integer radius, byte userNavigationEnabled, byte zoomGesturesEnabled, byte panningGesturesEnabled, byte streetNamesEnabled, byte useViewLifecycleInFragment) {
        this.Sp = Boolean.valueOf(true);
        this.RP = Boolean.valueOf(true);
        this.Sq = Boolean.valueOf(true);
        this.Sr = Boolean.valueOf(true);
        this.xH = versionCode;
        this.Sl = camera;
        this.Sn = position;
        this.So = radius;
        this.Sm = panoId;
        this.Sp = C0431a.m1227a(userNavigationEnabled);
        this.RP = C0431a.m1227a(zoomGesturesEnabled);
        this.Sq = C0431a.m1227a(panningGesturesEnabled);
        this.Sr = C0431a.m1227a(streetNamesEnabled);
        this.RJ = C0431a.m1227a(useViewLifecycleInFragment);
    }

    public int describeContents() {
        return 0;
    }

    public Boolean getPanningGesturesEnabled() {
        return this.Sq;
    }

    public String getPanoramaId() {
        return this.Sm;
    }

    public LatLng getPosition() {
        return this.Sn;
    }

    public Integer getRadius() {
        return this.So;
    }

    public Boolean getStreetNamesEnabled() {
        return this.Sr;
    }

    public StreetViewPanoramaCamera getStreetViewPanoramaCamera() {
        return this.Sl;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.RJ;
    }

    public Boolean getUserNavigationEnabled() {
        return this.Sp;
    }

    int getVersionCode() {
        return this.xH;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.RP;
    }

    byte ih() {
        return C0431a.m1228c(this.RJ);
    }

    byte il() {
        return C0431a.m1228c(this.RP);
    }

    byte it() {
        return C0431a.m1228c(this.Sp);
    }

    byte iu() {
        return C0431a.m1228c(this.Sq);
    }

    byte iv() {
        return C0431a.m1228c(this.Sr);
    }

    public StreetViewPanoramaOptions panningGesturesEnabled(boolean enabled) {
        this.Sq = Boolean.valueOf(enabled);
        return this;
    }

    public StreetViewPanoramaOptions panoramaCamera(StreetViewPanoramaCamera camera) {
        this.Sl = camera;
        return this;
    }

    public StreetViewPanoramaOptions panoramaId(String panoId) {
        this.Sm = panoId;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng position) {
        this.Sn = position;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng position, Integer radius) {
        this.Sn = position;
        this.So = radius;
        return this;
    }

    public StreetViewPanoramaOptions streetNamesEnabled(boolean enabled) {
        this.Sr = Boolean.valueOf(enabled);
        return this;
    }

    public StreetViewPanoramaOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.RJ = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    public StreetViewPanoramaOptions userNavigationEnabled(boolean enabled) {
        this.Sp = Boolean.valueOf(enabled);
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        StreetViewPanoramaOptionsCreator.m1225a(this, out, flags);
    }

    public StreetViewPanoramaOptions zoomGesturesEnabled(boolean enabled) {
        this.RP = Boolean.valueOf(enabled);
        return this;
    }
}
