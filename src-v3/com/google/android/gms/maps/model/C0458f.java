package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.maps.model.f */
public class C0458f {
    static void m1274a(MarkerOptions markerOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, markerOptions.getVersionCode());
        C0262b.m219a(parcel, 2, markerOptions.getPosition(), i, false);
        C0262b.m222a(parcel, 3, markerOptions.getTitle(), false);
        C0262b.m222a(parcel, 4, markerOptions.getSnippet(), false);
        C0262b.m217a(parcel, 5, markerOptions.iE(), false);
        C0262b.m214a(parcel, 6, markerOptions.getAnchorU());
        C0262b.m214a(parcel, 7, markerOptions.getAnchorV());
        C0262b.m225a(parcel, 8, markerOptions.isDraggable());
        C0262b.m225a(parcel, 9, markerOptions.isVisible());
        C0262b.m211F(parcel, p);
    }
}
