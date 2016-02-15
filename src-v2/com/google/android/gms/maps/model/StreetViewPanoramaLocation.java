/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLink;
import com.google.android.gms.maps.model.StreetViewPanoramaLocationCreator;

public class StreetViewPanoramaLocation
implements SafeParcelable {
    public static final StreetViewPanoramaLocationCreator CREATOR = new StreetViewPanoramaLocationCreator();
    public final StreetViewPanoramaLink[] links;
    public final String panoId;
    public final LatLng position;
    private final int xH;

    StreetViewPanoramaLocation(int n2, StreetViewPanoramaLink[] arrstreetViewPanoramaLink, LatLng latLng, String string2) {
        this.xH = n2;
        this.links = arrstreetViewPanoramaLink;
        this.position = latLng;
        this.panoId = string2;
    }

    public StreetViewPanoramaLocation(StreetViewPanoramaLink[] arrstreetViewPanoramaLink, LatLng latLng, String string2) {
        this(1, arrstreetViewPanoramaLink, latLng, string2);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StreetViewPanoramaLocation)) {
            return false;
        }
        object = (StreetViewPanoramaLocation)object;
        if (!this.panoId.equals(object.panoId)) return false;
        if (this.position.equals(object.position)) return true;
        return false;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.position, this.panoId);
    }

    public String toString() {
        return fo.e(this).a("panoId", this.panoId).a("position", this.position.toString()).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        StreetViewPanoramaLocationCreator.a(this, parcel, n2);
    }
}

