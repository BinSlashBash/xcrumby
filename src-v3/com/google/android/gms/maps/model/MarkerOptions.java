package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.crumby.GalleryViewer;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.C0306d.C0820a;
import com.google.android.gms.maps.internal.C0452v;

public final class MarkerOptions implements SafeParcelable {
    public static final MarkerOptionsCreator CREATOR;
    private String EB;
    private boolean SO;
    private float SW;
    private float SX;
    private LatLng Sn;
    private String Tf;
    private BitmapDescriptor Tg;
    private boolean Th;
    private boolean Ti;
    private float Tj;
    private float Tk;
    private float Tl;
    private float mAlpha;
    private final int xH;

    static {
        CREATOR = new MarkerOptionsCreator();
    }

    public MarkerOptions() {
        this.SW = 0.5f;
        this.SX = GalleryViewer.PROGRESS_COMPLETED;
        this.SO = true;
        this.Ti = false;
        this.Tj = 0.0f;
        this.Tk = 0.5f;
        this.Tl = 0.0f;
        this.mAlpha = GalleryViewer.PROGRESS_COMPLETED;
        this.xH = 1;
    }

    MarkerOptions(int versionCode, LatLng position, String title, String snippet, IBinder wrappedIcon, float anchorU, float anchorV, boolean draggable, boolean visible, boolean flat, float rotation, float infoWindowAnchorU, float infoWindowAnchorV, float alpha) {
        this.SW = 0.5f;
        this.SX = GalleryViewer.PROGRESS_COMPLETED;
        this.SO = true;
        this.Ti = false;
        this.Tj = 0.0f;
        this.Tk = 0.5f;
        this.Tl = 0.0f;
        this.mAlpha = GalleryViewer.PROGRESS_COMPLETED;
        this.xH = versionCode;
        this.Sn = position;
        this.EB = title;
        this.Tf = snippet;
        this.Tg = wrappedIcon == null ? null : new BitmapDescriptor(C0820a.m1755K(wrappedIcon));
        this.SW = anchorU;
        this.SX = anchorV;
        this.Th = draggable;
        this.SO = visible;
        this.Ti = flat;
        this.Tj = rotation;
        this.Tk = infoWindowAnchorU;
        this.Tl = infoWindowAnchorV;
        this.mAlpha = alpha;
    }

    public MarkerOptions alpha(float alpha) {
        this.mAlpha = alpha;
        return this;
    }

    public MarkerOptions anchor(float u, float v) {
        this.SW = u;
        this.SX = v;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public MarkerOptions draggable(boolean draggable) {
        this.Th = draggable;
        return this;
    }

    public MarkerOptions flat(boolean flat) {
        this.Ti = flat;
        return this;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getAnchorU() {
        return this.SW;
    }

    public float getAnchorV() {
        return this.SX;
    }

    public BitmapDescriptor getIcon() {
        return this.Tg;
    }

    public float getInfoWindowAnchorU() {
        return this.Tk;
    }

    public float getInfoWindowAnchorV() {
        return this.Tl;
    }

    public LatLng getPosition() {
        return this.Sn;
    }

    public float getRotation() {
        return this.Tj;
    }

    public String getSnippet() {
        return this.Tf;
    }

    public String getTitle() {
        return this.EB;
    }

    int getVersionCode() {
        return this.xH;
    }

    IBinder iE() {
        return this.Tg == null ? null : this.Tg.id().asBinder();
    }

    public MarkerOptions icon(BitmapDescriptor icon) {
        this.Tg = icon;
        return this;
    }

    public MarkerOptions infoWindowAnchor(float u, float v) {
        this.Tk = u;
        this.Tl = v;
        return this;
    }

    public boolean isDraggable() {
        return this.Th;
    }

    public boolean isFlat() {
        return this.Ti;
    }

    public boolean isVisible() {
        return this.SO;
    }

    public MarkerOptions position(LatLng position) {
        this.Sn = position;
        return this;
    }

    public MarkerOptions rotation(float rotation) {
        this.Tj = rotation;
        return this;
    }

    public MarkerOptions snippet(String snippet) {
        this.Tf = snippet;
        return this;
    }

    public MarkerOptions title(String title) {
        this.EB = title;
        return this;
    }

    public MarkerOptions visible(boolean visible) {
        this.SO = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0452v.iB()) {
            C0458f.m1274a(this, out, flags);
        } else {
            MarkerOptionsCreator.m1259a(this, out, flags);
        }
    }
}
