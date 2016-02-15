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
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.d;

public final class LatLngBounds
implements SafeParcelable {
    public static final LatLngBoundsCreator CREATOR = new LatLngBoundsCreator();
    public final LatLng northeast;
    public final LatLng southwest;
    private final int xH;

    /*
     * Enabled aggressive block sorting
     */
    LatLngBounds(int n2, LatLng latLng, LatLng latLng2) {
        fq.b(latLng, (Object)"null southwest");
        fq.b(latLng2, (Object)"null northeast");
        boolean bl2 = latLng2.latitude >= latLng.latitude;
        fq.a(bl2, "southern latitude exceeds northern latitude (%s > %s)", latLng.latitude, latLng2.latitude);
        this.xH = n2;
        this.southwest = latLng;
        this.northeast = latLng2;
    }

    public LatLngBounds(LatLng latLng, LatLng latLng2) {
        this(1, latLng, latLng2);
    }

    private static double b(double d2, double d3) {
        return (d2 - d3 + 360.0) % 360.0;
    }

    public static Builder builder() {
        return new Builder();
    }

    private static double c(double d2, double d3) {
        return (d3 - d2 + 360.0) % 360.0;
    }

    private boolean c(double d2) {
        if (this.southwest.latitude <= d2 && d2 <= this.northeast.latitude) {
            return true;
        }
        return false;
    }

    private boolean d(double d2) {
        boolean bl2 = false;
        if (this.southwest.longitude <= this.northeast.longitude) {
            if (this.southwest.longitude <= d2 && d2 <= this.northeast.longitude) {
                return true;
            }
            return false;
        }
        if (this.southwest.longitude <= d2 || d2 <= this.northeast.longitude) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean contains(LatLng latLng) {
        if (this.c(latLng.latitude) && this.d(latLng.longitude)) {
            return true;
        }
        return false;
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
        if (!(object instanceof LatLngBounds)) {
            return false;
        }
        object = (LatLngBounds)object;
        if (!this.southwest.equals(object.southwest)) return false;
        if (this.northeast.equals(object.northeast)) return true;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public LatLng getCenter() {
        double d2 = (this.southwest.latitude + this.northeast.latitude) / 2.0;
        double d3 = this.southwest.longitude;
        double d4 = this.northeast.longitude;
        if (d3 <= d4) {
            d4 = (d4 + d3) / 2.0;
            do {
                return new LatLng(d2, d4);
                break;
            } while (true);
        }
        d4 = (d4 + 360.0 + d3) / 2.0;
        return new LatLng(d2, d4);
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.southwest, this.northeast);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public LatLngBounds including(LatLng latLng) {
        double d2 = Math.min(this.southwest.latitude, latLng.latitude);
        double d3 = Math.max(this.northeast.latitude, latLng.latitude);
        double d4 = this.northeast.longitude;
        double d5 = this.southwest.longitude;
        double d6 = latLng.longitude;
        if (!this.d(d6)) {
            if (LatLngBounds.b(d5, d6) < LatLngBounds.c(d4, d6)) {
                do {
                    return new LatLngBounds(new LatLng(d2, d6), new LatLng(d3, d4));
                    break;
                } while (true);
            }
            d4 = d6;
            d6 = d5;
            return new LatLngBounds(new LatLng(d2, d6), new LatLng(d3, d4));
        }
        d6 = d5;
        return new LatLngBounds(new LatLng(d2, d6), new LatLng(d3, d4));
    }

    public String toString() {
        return fo.e(this).a("southwest", this.southwest).a("northeast", this.northeast).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            d.a(this, parcel, n2);
            return;
        }
        LatLngBoundsCreator.a(this, parcel, n2);
    }

    public static final class Builder {
        private double Ta = Double.POSITIVE_INFINITY;
        private double Tb = Double.NEGATIVE_INFINITY;
        private double Tc = Double.NaN;
        private double Td = Double.NaN;

        private boolean d(double d2) {
            boolean bl2 = false;
            if (this.Tc <= this.Td) {
                if (this.Tc <= d2 && d2 <= this.Td) {
                    return true;
                }
                return false;
            }
            if (this.Tc <= d2 || d2 <= this.Td) {
                bl2 = true;
            }
            return bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public LatLngBounds build() {
            boolean bl2 = !Double.isNaN(this.Tc);
            fq.a(bl2, "no included points");
            return new LatLngBounds(new LatLng(this.Ta, this.Tc), new LatLng(this.Tb, this.Td));
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder include(LatLng latLng) {
            this.Ta = Math.min(this.Ta, latLng.latitude);
            this.Tb = Math.max(this.Tb, latLng.latitude);
            double d2 = latLng.longitude;
            if (Double.isNaN(this.Tc)) {
                this.Tc = d2;
                this.Td = d2;
                return this;
            }
            if (this.d(d2)) return this;
            {
                if (LatLngBounds.b(this.Tc, d2) < LatLngBounds.c(this.Td, d2)) {
                    this.Tc = d2;
                    return this;
                }
            }
            this.Td = d2;
            return this;
        }
    }

}

