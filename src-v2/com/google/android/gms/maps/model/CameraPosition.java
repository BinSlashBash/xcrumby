/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.util.AttributeSet
 */
package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.v;
import com.google.android.gms.maps.model.CameraPositionCreator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.a;

public final class CameraPosition
implements SafeParcelable {
    public static final CameraPositionCreator CREATOR = new CameraPositionCreator();
    public final float bearing;
    public final LatLng target;
    public final float tilt;
    private final int xH;
    public final float zoom;

    /*
     * Enabled aggressive block sorting
     */
    CameraPosition(int n2, LatLng latLng, float f2, float f3, float f4) {
        fq.b(latLng, (Object)"null camera target");
        boolean bl2 = 0.0f <= f3 && f3 <= 90.0f;
        fq.b(bl2, (Object)"Tilt needs to be between 0 and 90 inclusive");
        this.xH = n2;
        this.target = latLng;
        this.zoom = f2;
        this.tilt = f3 + 0.0f;
        f2 = f4;
        if ((double)f4 <= 0.0) {
            f2 = f4 % 360.0f + 360.0f;
        }
        this.bearing = f2 % 360.0f;
    }

    public CameraPosition(LatLng latLng, float f2, float f3, float f4) {
        this(1, latLng, f2, f3, f4);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition cameraPosition) {
        return new Builder(cameraPosition);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static CameraPosition createFromAttributes(Context context, AttributeSet object) {
        if (object == null) {
            return null;
        }
        float f2 = (context = context.getResources().obtainAttributes((AttributeSet)object, R.styleable.MapAttrs)).hasValue(2) ? context.getFloat(2, 0.0f) : 0.0f;
        float f3 = context.hasValue(3) ? context.getFloat(3, 0.0f) : 0.0f;
        object = new LatLng(f2, f3);
        Builder builder = CameraPosition.builder();
        builder.target((LatLng)object);
        if (context.hasValue(5)) {
            builder.zoom(context.getFloat(5, 0.0f));
        }
        if (context.hasValue(1)) {
            builder.bearing(context.getFloat(1, 0.0f));
        }
        if (context.hasValue(4)) {
            builder.tilt(context.getFloat(4, 0.0f));
        }
        return builder.build();
    }

    public static final CameraPosition fromLatLngZoom(LatLng latLng, float f2) {
        return new CameraPosition(latLng, f2, 0.0f, 0.0f);
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
        if (!(object instanceof CameraPosition)) {
            return false;
        }
        object = (CameraPosition)object;
        if (!this.target.equals(object.target)) return false;
        if (Float.floatToIntBits(this.zoom) != Float.floatToIntBits(object.zoom)) return false;
        if (Float.floatToIntBits(this.tilt) != Float.floatToIntBits(object.tilt)) return false;
        if (Float.floatToIntBits(this.bearing) == Float.floatToIntBits(object.bearing)) return true;
        return false;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    public String toString() {
        return fo.e(this).a("target", this.target).a("zoom", Float.valueOf(this.zoom)).a("tilt", Float.valueOf(this.tilt)).a("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (v.iB()) {
            a.a(this, parcel, n2);
            return;
        }
        CameraPositionCreator.a(this, parcel, n2);
    }

    public static final class Builder {
        private LatLng SD;
        private float SE;
        private float SF;
        private float SG;

        public Builder() {
        }

        public Builder(CameraPosition cameraPosition) {
            this.SD = cameraPosition.target;
            this.SE = cameraPosition.zoom;
            this.SF = cameraPosition.tilt;
            this.SG = cameraPosition.bearing;
        }

        public Builder bearing(float f2) {
            this.SG = f2;
            return this;
        }

        public CameraPosition build() {
            return new CameraPosition(this.SD, this.SE, this.SF, this.SG);
        }

        public Builder target(LatLng latLng) {
            this.SD = latLng;
            return this;
        }

        public Builder tilt(float f2) {
            this.SF = f2;
            return this;
        }

        public Builder zoom(float f2) {
            this.SE = f2;
            return this;
        }
    }

}

