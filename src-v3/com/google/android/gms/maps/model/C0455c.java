package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.c */
public class C0455c {
    static void m1271a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, groundOverlayOptions.getVersionCode());
        C0262b.m217a(parcel, 2, groundOverlayOptions.iD(), false);
        C0262b.m219a(parcel, 3, groundOverlayOptions.getLocation(), i, false);
        C0262b.m214a(parcel, 4, groundOverlayOptions.getWidth());
        C0262b.m214a(parcel, 5, groundOverlayOptions.getHeight());
        C0262b.m219a(parcel, 6, groundOverlayOptions.getBounds(), i, false);
        C0262b.m214a(parcel, 7, groundOverlayOptions.getBearing());
        C0262b.m214a(parcel, 8, groundOverlayOptions.getZIndex());
        C0262b.m225a(parcel, 9, groundOverlayOptions.isVisible());
        C0262b.m214a(parcel, 10, groundOverlayOptions.getTransparency());
        C0262b.m214a(parcel, 11, groundOverlayOptions.getAnchorU());
        C0262b.m214a(parcel, 12, groundOverlayOptions.getAnchorV());
        C0262b.m211F(parcel, p);
    }
}
