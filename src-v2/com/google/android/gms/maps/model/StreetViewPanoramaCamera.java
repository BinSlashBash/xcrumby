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
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.model.StreetViewPanoramaCameraCreator;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public class StreetViewPanoramaCamera
implements SafeParcelable {
    public static final StreetViewPanoramaCameraCreator CREATOR = new StreetViewPanoramaCameraCreator();
    private StreetViewPanoramaOrientation Tr;
    public final float bearing;
    public final float tilt;
    private final int xH;
    public final float zoom;

    public StreetViewPanoramaCamera(float f2, float f3, float f4) {
        this(1, f2, f3, f4);
    }

    /*
     * Enabled aggressive block sorting
     */
    StreetViewPanoramaCamera(int n2, float f2, float f3, float f4) {
        boolean bl2 = -90.0f <= f3 && f3 <= 90.0f;
        fq.b(bl2, (Object)"Tilt needs to be between -90 and 90 inclusive");
        this.xH = n2;
        this.zoom = f2;
        this.tilt = 0.0f + f3;
        f2 = (double)f4 <= 0.0 ? f4 % 360.0f + 360.0f : f4;
        this.bearing = f2 % 360.0f;
        this.Tr = new StreetViewPanoramaOrientation.Builder().tilt(f3).bearing(f4).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        return new Builder(streetViewPanoramaCamera);
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
        if (!(object instanceof StreetViewPanoramaCamera)) {
            return false;
        }
        object = (StreetViewPanoramaCamera)object;
        if (Float.floatToIntBits(this.zoom) != Float.floatToIntBits(object.zoom)) return false;
        if (Float.floatToIntBits(this.tilt) != Float.floatToIntBits(object.tilt)) return false;
        if (Float.floatToIntBits(this.bearing) == Float.floatToIntBits(object.bearing)) return true;
        return false;
    }

    public StreetViewPanoramaOrientation getOrientation() {
        return this.Tr;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    public String toString() {
        return fo.e(this).a("zoom", Float.valueOf(this.zoom)).a("tilt", Float.valueOf(this.tilt)).a("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        StreetViewPanoramaCameraCreator.a(this, parcel, n2);
    }

    public static final class Builder {
        public float bearing;
        public float tilt;
        public float zoom;

        public Builder() {
        }

        public Builder(StreetViewPanoramaCamera streetViewPanoramaCamera) {
            this.zoom = streetViewPanoramaCamera.zoom;
            this.bearing = streetViewPanoramaCamera.bearing;
            this.tilt = streetViewPanoramaCamera.tilt;
        }

        public Builder bearing(float f2) {
            this.bearing = f2;
            return this;
        }

        public StreetViewPanoramaCamera build() {
            return new StreetViewPanoramaCamera(this.zoom, this.tilt, this.bearing);
        }

        public Builder orientation(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
            this.tilt = streetViewPanoramaOrientation.tilt;
            this.bearing = streetViewPanoramaOrientation.bearing;
            return this;
        }

        public Builder tilt(float f2) {
            this.tilt = f2;
            return this;
        }

        public Builder zoom(float f2) {
            this.zoom = f2;
            return this;
        }
    }

}

