package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0452v;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolygonOptions implements SafeParcelable {
    public static final PolygonOptionsCreator CREATOR;
    private float SK;
    private int SL;
    private int SM;
    private float SN;
    private boolean SO;
    private final List<LatLng> Tn;
    private final List<List<LatLng>> To;
    private boolean Tp;
    private final int xH;

    static {
        CREATOR = new PolygonOptionsCreator();
    }

    public PolygonOptions() {
        this.SK = 10.0f;
        this.SL = ViewCompat.MEASURED_STATE_MASK;
        this.SM = 0;
        this.SN = 0.0f;
        this.SO = true;
        this.Tp = false;
        this.xH = 1;
        this.Tn = new ArrayList();
        this.To = new ArrayList();
    }

    PolygonOptions(int versionCode, List<LatLng> points, List holes, float strokeWidth, int strokeColor, int fillColor, float zIndex, boolean visible, boolean geodesic) {
        this.SK = 10.0f;
        this.SL = ViewCompat.MEASURED_STATE_MASK;
        this.SM = 0;
        this.SN = 0.0f;
        this.SO = true;
        this.Tp = false;
        this.xH = versionCode;
        this.Tn = points;
        this.To = holes;
        this.SK = strokeWidth;
        this.SL = strokeColor;
        this.SM = fillColor;
        this.SN = zIndex;
        this.SO = visible;
        this.Tp = geodesic;
    }

    public PolygonOptions add(LatLng point) {
        this.Tn.add(point);
        return this;
    }

    public PolygonOptions add(LatLng... points) {
        this.Tn.addAll(Arrays.asList(points));
        return this;
    }

    public PolygonOptions addAll(Iterable<LatLng> points) {
        for (LatLng add : points) {
            this.Tn.add(add);
        }
        return this;
    }

    public PolygonOptions addHole(Iterable<LatLng> points) {
        ArrayList arrayList = new ArrayList();
        for (LatLng add : points) {
            arrayList.add(add);
        }
        this.To.add(arrayList);
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public PolygonOptions fillColor(int color) {
        this.SM = color;
        return this;
    }

    public PolygonOptions geodesic(boolean geodesic) {
        this.Tp = geodesic;
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

    public PolygonOptions strokeColor(int color) {
        this.SL = color;
        return this;
    }

    public PolygonOptions strokeWidth(float width) {
        this.SK = width;
        return this;
    }

    public PolygonOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0452v.iB()) {
            C0459g.m1275a(this, out, flags);
        } else {
            PolygonOptionsCreator.m1260a(this, out, flags);
        }
    }

    public PolygonOptions zIndex(float zIndex) {
        this.SN = zIndex;
        return this;
    }
}
