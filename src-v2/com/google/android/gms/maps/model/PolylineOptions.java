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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptionsCreator;
import com.google.android.gms.maps.model.h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class PolylineOptions
implements SafeParcelable {
    public static final PolylineOptionsCreator CREATOR = new PolylineOptionsCreator();
    private int Av = -16777216;
    private float SN = 0.0f;
    private boolean SO = true;
    private float SS = 10.0f;
    private final List<LatLng> Tn;
    private boolean Tp = false;
    private final int xH;

    public PolylineOptions() {
        this.xH = 1;
        this.Tn = new ArrayList<LatLng>();
    }

    PolylineOptions(int n2, List list, float f2, int n3, float f3, boolean bl2, boolean bl3) {
        this.xH = n2;
        this.Tn = list;
        this.SS = f2;
        this.Av = n3;
        this.SN = f3;
        this.SO = bl2;
        this.Tp = bl3;
    }

    public PolylineOptions add(LatLng latLng) {
        this.Tn.add(latLng);
        return this;
    }

    public /* varargs */ PolylineOptions add(LatLng ... arrlatLng) {
        this.Tn.addAll(Arrays.asList(arrlatLng));
        return this;
    }

    public PolylineOptions addAll(Iterable<LatLng> object) {
        object = object.iterator();
        while (object.hasNext()) {
            LatLng latLng = (LatLng)object.next();
            this.Tn.add(latLng);
        }
        return this;
    }

    public PolylineOptions color(int n2) {
        this.Av = n2;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public PolylineOptions geodesic(boolean bl2) {
        this.Tp = bl2;
        return this;
    }

    public int getColor() {
        return this.Av;
    }

    public List<LatLng> getPoints() {
        return this.Tn;
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

    public boolean isGeodesic() {
        return this.Tp;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public PolylineOptions visible(boolean bl2) {
        this.SO = bl2;
        return this;
    }

    public PolylineOptions width(float f2) {
        this.SS = f2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            h.a(this, parcel, n2);
            return;
        }
        PolylineOptionsCreator.a(this, parcel, n2);
    }

    public PolylineOptions zIndex(float f2) {
        this.SN = f2;
        return this;
    }
}

