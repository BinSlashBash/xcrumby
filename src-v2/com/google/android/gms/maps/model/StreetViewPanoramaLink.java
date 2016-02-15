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
import com.google.android.gms.maps.model.StreetViewPanoramaLinkCreator;

public class StreetViewPanoramaLink
implements SafeParcelable {
    public static final StreetViewPanoramaLinkCreator CREATOR = new StreetViewPanoramaLinkCreator();
    public final float bearing;
    public final String panoId;
    private final int xH;

    StreetViewPanoramaLink(int n2, String string2, float f2) {
        this.xH = n2;
        this.panoId = string2;
        float f3 = f2;
        if ((double)f2 <= 0.0) {
            f3 = f2 % 360.0f + 360.0f;
        }
        this.bearing = f3 % 360.0f;
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
        if (!(object instanceof StreetViewPanoramaLink)) {
            return false;
        }
        object = (StreetViewPanoramaLink)object;
        if (!this.panoId.equals(object.panoId)) return false;
        if (Float.floatToIntBits(this.bearing) == Float.floatToIntBits(object.bearing)) return true;
        return false;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.panoId, Float.valueOf(this.bearing));
    }

    public String toString() {
        return fo.e(this).a("panoId", this.panoId).a("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        StreetViewPanoramaLinkCreator.a(this, parcel, n2);
    }
}

