/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.LatLngCreator;

public class GroundOverlayOptionsCreator
implements Parcelable.Creator<GroundOverlayOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, groundOverlayOptions.getVersionCode());
        b.a(parcel, 2, groundOverlayOptions.iD(), false);
        b.a(parcel, 3, groundOverlayOptions.getLocation(), n2, false);
        b.a(parcel, 4, groundOverlayOptions.getWidth());
        b.a(parcel, 5, groundOverlayOptions.getHeight());
        b.a(parcel, 6, groundOverlayOptions.getBounds(), n2, false);
        b.a(parcel, 7, groundOverlayOptions.getBearing());
        b.a(parcel, 8, groundOverlayOptions.getZIndex());
        b.a(parcel, 9, groundOverlayOptions.isVisible());
        b.a(parcel, 10, groundOverlayOptions.getTransparency());
        b.a(parcel, 11, groundOverlayOptions.getAnchorU());
        b.a(parcel, 12, groundOverlayOptions.getAnchorV());
        b.F(parcel, n3);
    }

    public GroundOverlayOptions createFromParcel(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        IBinder iBinder = null;
        LatLng latLng = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        LatLngBounds latLngBounds = null;
        float f4 = 0.0f;
        float f5 = 0.0f;
        boolean bl2 = false;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        block14 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block14;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block14;
                }
                case 2: {
                    iBinder = a.o(parcel, n4);
                    continue block14;
                }
                case 3: {
                    latLng = (LatLng)a.a(parcel, n4, LatLng.CREATOR);
                    continue block14;
                }
                case 4: {
                    f2 = a.k(parcel, n4);
                    continue block14;
                }
                case 5: {
                    f3 = a.k(parcel, n4);
                    continue block14;
                }
                case 6: {
                    latLngBounds = (LatLngBounds)a.a(parcel, n4, LatLngBounds.CREATOR);
                    continue block14;
                }
                case 7: {
                    f4 = a.k(parcel, n4);
                    continue block14;
                }
                case 8: {
                    f5 = a.k(parcel, n4);
                    continue block14;
                }
                case 9: {
                    bl2 = a.c(parcel, n4);
                    continue block14;
                }
                case 10: {
                    f6 = a.k(parcel, n4);
                    continue block14;
                }
                case 11: {
                    f7 = a.k(parcel, n4);
                    continue block14;
                }
                case 12: 
            }
            f8 = a.k(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new GroundOverlayOptions(n3, iBinder, latLng, f2, f3, latLngBounds, f4, f5, bl2, f6, f7, f8);
    }

    public GroundOverlayOptions[] newArray(int n2) {
        return new GroundOverlayOptions[n2];
    }
}

