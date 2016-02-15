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
import com.google.android.gms.maps.model.StreetViewPanoramaOrientationCreator;

public class StreetViewPanoramaOrientation
implements SafeParcelable {
    public static final StreetViewPanoramaOrientationCreator CREATOR = new StreetViewPanoramaOrientationCreator();
    public final float bearing;
    public final float tilt;
    private final int xH;

    public StreetViewPanoramaOrientation(float f2, float f3) {
        this(1, f2, f3);
    }

    /*
     * Enabled aggressive block sorting
     */
    StreetViewPanoramaOrientation(int n2, float f2, float f3) {
        boolean bl2 = -90.0f <= f2 && f2 <= 90.0f;
        fq.b(bl2, (Object)"Tilt needs to be between -90 and 90 inclusive");
        this.xH = n2;
        this.tilt = 0.0f + f2;
        f2 = f3;
        if ((double)f3 <= 0.0) {
            f2 = f3 % 360.0f + 360.0f;
        }
        this.bearing = f2 % 360.0f;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        return new Builder(streetViewPanoramaOrientation);
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
        if (!(object instanceof StreetViewPanoramaOrientation)) {
            return false;
        }
        object = (StreetViewPanoramaOrientation)object;
        if (Float.floatToIntBits(this.tilt) != Float.floatToIntBits(object.tilt)) return false;
        if (Float.floatToIntBits(this.bearing) == Float.floatToIntBits(object.bearing)) return true;
        return false;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    public String toString() {
        return fo.e(this).a("tilt", Float.valueOf(this.tilt)).a("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        StreetViewPanoramaOrientationCreator.a(this, parcel, n2);
    }

    public static final class Builder {
        public float bearing;
        public float tilt;

        public Builder() {
        }

        public Builder(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
            this.bearing = streetViewPanoramaOrientation.bearing;
            this.tilt = streetViewPanoramaOrientation.tilt;
        }

        public Builder bearing(float f2) {
            this.bearing = f2;
            return this;
        }

        public StreetViewPanoramaOrientation build() {
            return new StreetViewPanoramaOrientation(this.tilt, this.bearing);
        }

        public Builder tilt(float f2) {
            this.tilt = f2;
            return this;
        }
    }

}

