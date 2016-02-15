/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.StreetViewPanoramaOptionsCreator;
import com.google.android.gms.maps.internal.a;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class StreetViewPanoramaOptions
implements SafeParcelable {
    public static final StreetViewPanoramaOptionsCreator CREATOR = new StreetViewPanoramaOptionsCreator();
    private Boolean RJ;
    private Boolean RP = true;
    private StreetViewPanoramaCamera Sl;
    private String Sm;
    private LatLng Sn;
    private Integer So;
    private Boolean Sp = true;
    private Boolean Sq = true;
    private Boolean Sr = true;
    private final int xH;

    public StreetViewPanoramaOptions() {
        this.xH = 1;
    }

    StreetViewPanoramaOptions(int n2, StreetViewPanoramaCamera streetViewPanoramaCamera, String string2, LatLng latLng, Integer n3, byte by2, byte by3, byte by4, byte by5, byte by6) {
        this.xH = n2;
        this.Sl = streetViewPanoramaCamera;
        this.Sn = latLng;
        this.So = n3;
        this.Sm = string2;
        this.Sp = a.a(by2);
        this.RP = a.a(by3);
        this.Sq = a.a(by4);
        this.Sr = a.a(by5);
        this.RJ = a.a(by6);
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
        return a.c(this.RJ);
    }

    byte il() {
        return a.c(this.RP);
    }

    byte it() {
        return a.c(this.Sp);
    }

    byte iu() {
        return a.c(this.Sq);
    }

    byte iv() {
        return a.c(this.Sr);
    }

    public StreetViewPanoramaOptions panningGesturesEnabled(boolean bl2) {
        this.Sq = bl2;
        return this;
    }

    public StreetViewPanoramaOptions panoramaCamera(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        this.Sl = streetViewPanoramaCamera;
        return this;
    }

    public StreetViewPanoramaOptions panoramaId(String string2) {
        this.Sm = string2;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng latLng) {
        this.Sn = latLng;
        return this;
    }

    public StreetViewPanoramaOptions position(LatLng latLng, Integer n2) {
        this.Sn = latLng;
        this.So = n2;
        return this;
    }

    public StreetViewPanoramaOptions streetNamesEnabled(boolean bl2) {
        this.Sr = bl2;
        return this;
    }

    public StreetViewPanoramaOptions useViewLifecycleInFragment(boolean bl2) {
        this.RJ = bl2;
        return this;
    }

    public StreetViewPanoramaOptions userNavigationEnabled(boolean bl2) {
        this.Sp = bl2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        StreetViewPanoramaOptionsCreator.a(this, parcel, n2);
    }

    public StreetViewPanoramaOptions zoomGesturesEnabled(boolean bl2) {
        this.RP = bl2;
        return this;
    }
}

