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
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.VisibleRegionCreator;
import com.google.android.gms.maps.model.k;

public final class VisibleRegion
implements SafeParcelable {
    public static final VisibleRegionCreator CREATOR = new VisibleRegionCreator();
    public final LatLng farLeft;
    public final LatLng farRight;
    public final LatLngBounds latLngBounds;
    public final LatLng nearLeft;
    public final LatLng nearRight;
    private final int xH;

    VisibleRegion(int n2, LatLng latLng, LatLng latLng2, LatLng latLng3, LatLng latLng4, LatLngBounds latLngBounds) {
        this.xH = n2;
        this.nearLeft = latLng;
        this.nearRight = latLng2;
        this.farLeft = latLng3;
        this.farRight = latLng4;
        this.latLngBounds = latLngBounds;
    }

    public VisibleRegion(LatLng latLng, LatLng latLng2, LatLng latLng3, LatLng latLng4, LatLngBounds latLngBounds) {
        this(1, latLng, latLng2, latLng3, latLng4, latLngBounds);
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
        if (!(object instanceof VisibleRegion)) {
            return false;
        }
        object = (VisibleRegion)object;
        if (!this.nearLeft.equals(object.nearLeft)) return false;
        if (!this.nearRight.equals(object.nearRight)) return false;
        if (!this.farLeft.equals(object.farLeft)) return false;
        if (!this.farRight.equals(object.farRight)) return false;
        if (this.latLngBounds.equals(object.latLngBounds)) return true;
        return false;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.nearLeft, this.nearRight, this.farLeft, this.farRight, this.latLngBounds);
    }

    public String toString() {
        return fo.e(this).a("nearLeft", this.nearLeft).a("nearRight", this.nearRight).a("farLeft", this.farLeft).a("farRight", this.farRight).a("latLngBounds", this.latLngBounds).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            k.a(this, parcel, n2);
            return;
        }
        VisibleRegionCreator.a(this, parcel, n2);
    }
}

