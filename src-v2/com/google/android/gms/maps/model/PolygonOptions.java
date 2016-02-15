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
import com.google.android.gms.maps.model.PolygonOptionsCreator;
import com.google.android.gms.maps.model.g;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class PolygonOptions
implements SafeParcelable {
    public static final PolygonOptionsCreator CREATOR = new PolygonOptionsCreator();
    private float SK = 10.0f;
    private int SL = -16777216;
    private int SM = 0;
    private float SN = 0.0f;
    private boolean SO = true;
    private final List<LatLng> Tn;
    private final List<List<LatLng>> To;
    private boolean Tp = false;
    private final int xH;

    public PolygonOptions() {
        this.xH = 1;
        this.Tn = new ArrayList<LatLng>();
        this.To = new ArrayList<List<LatLng>>();
    }

    PolygonOptions(int n2, List<LatLng> list, List list2, float f2, int n3, int n4, float f3, boolean bl2, boolean bl3) {
        this.xH = n2;
        this.Tn = list;
        this.To = list2;
        this.SK = f2;
        this.SL = n3;
        this.SM = n4;
        this.SN = f3;
        this.SO = bl2;
        this.Tp = bl3;
    }

    public PolygonOptions add(LatLng latLng) {
        this.Tn.add(latLng);
        return this;
    }

    public /* varargs */ PolygonOptions add(LatLng ... arrlatLng) {
        this.Tn.addAll(Arrays.asList(arrlatLng));
        return this;
    }

    public PolygonOptions addAll(Iterable<LatLng> object) {
        object = object.iterator();
        while (object.hasNext()) {
            LatLng latLng = (LatLng)object.next();
            this.Tn.add(latLng);
        }
        return this;
    }

    public PolygonOptions addHole(Iterable<LatLng> object) {
        ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add((LatLng)object.next());
        }
        this.To.add(arrayList);
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public PolygonOptions fillColor(int n2) {
        this.SM = n2;
        return this;
    }

    public PolygonOptions geodesic(boolean bl2) {
        this.Tp = bl2;
        return this;
    }

    public int getFillColor() {
        return this.SM;
    }

    public List<List<LatLng>> getHoles() {
        return this.To;
    }

    public List<LatLng> getPoints() {
        return this.Tn;
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

    List iF() {
        return this.To;
    }

    public boolean isGeodesic() {
        return this.Tp;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public PolygonOptions strokeColor(int n2) {
        this.SL = n2;
        return this;
    }

    public PolygonOptions strokeWidth(float f2) {
        this.SK = f2;
        return this;
    }

    public PolygonOptions visible(boolean bl2) {
        this.SO = bl2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            g.a(this, parcel, n2);
            return;
        }
        PolygonOptionsCreator.a(this, parcel, n2);
    }

    public PolygonOptions zIndex(float f2) {
        this.SN = f2;
        return this;
    }
}

