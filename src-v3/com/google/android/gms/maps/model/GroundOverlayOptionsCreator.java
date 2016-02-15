package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class GroundOverlayOptionsCreator implements Creator<GroundOverlayOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1255a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
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

    public GroundOverlayOptions createFromParcel(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        IBinder iBinder = null;
        LatLng latLng = null;
        float f = 0.0f;
        float f2 = 0.0f;
        LatLngBounds latLngBounds = null;
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean z = false;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    iBinder = C0261a.m197o(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    latLng = (LatLng) C0261a.m176a(parcel, n, LatLng.CREATOR);
                    break;
                case Std.STD_CLASS /*4*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    f2 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    latLngBounds = (LatLngBounds) C0261a.m176a(parcel, n, LatLngBounds.CREATOR);
                    break;
                case Std.STD_PATTERN /*7*/:
                    f3 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    f4 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    f5 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    f6 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    f7 = C0261a.m191k(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new GroundOverlayOptions(i, iBinder, latLng, f, f2, latLngBounds, f3, f4, z, f5, f6, f7);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public GroundOverlayOptions[] newArray(int size) {
        return new GroundOverlayOptions[size];
    }
}
