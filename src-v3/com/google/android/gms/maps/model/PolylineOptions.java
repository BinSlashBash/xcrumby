package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0452v;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions implements SafeParcelable {
    public static final PolylineOptionsCreator CREATOR;
    private int Av;
    private float SN;
    private boolean SO;
    private float SS;
    private final List<LatLng> Tn;
    private boolean Tp;
    private final int xH;

    static {
        CREATOR = new PolylineOptionsCreator();
    }

    public PolylineOptions() {
        this.SS = 10.0f;
        this.Av = ViewCompat.MEASURED_STATE_MASK;
        this.SN = 0.0f;
        this.SO = true;
        this.Tp = false;
        this.xH = 1;
        this.Tn = new ArrayList();
    }

    PolylineOptions(int versionCode, List points, float width, int color, float zIndex, boolean visible, boolean geodesic) {
        this.SS = 10.0f;
        this.Av = ViewCompat.MEASURED_STATE_MASK;
        this.SN = 0.0f;
        this.SO = true;
        this.Tp = false;
        this.xH = versionCode;
        this.Tn = points;
        this.SS = width;
        this.Av = color;
        this.SN = zIndex;
        this.SO = visible;
        this.Tp = geodesic;
    }

    public PolylineOptions add(LatLng point) {
        this.Tn.add(point);
        return this;
    }

    public PolylineOptions add(LatLng... points) {
        this.Tn.addAll(Arrays.asList(points));
        return this;
    }

    public PolylineOptions addAll(Iterable<LatLng> points) {
        for (LatLng add : points) {
            this.Tn.add(add);
        }
        return this;
    }

    public PolylineOptions color(int color) {
        this.Av = color;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public PolylineOptions geodesic(boolean geodesic) {
        this.Tp = geodesic;
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

    public PolylineOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    public PolylineOptions width(float width) {
        this.SS = width;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0452v.iB()) {
            C0460h.m1276a(this, out, flags);
        } else {
            PolylineOptionsCreator.m1261a(this, out, flags);
        }
    }

    public PolylineOptions zIndex(float zIndex) {
        this.SN = zIndex;
        return this;
    }
}
