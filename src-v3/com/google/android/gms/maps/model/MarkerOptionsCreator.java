package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.crumby.GalleryViewer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;

public class MarkerOptionsCreator implements Creator<MarkerOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1259a(MarkerOptions markerOptions, Parcel parcel, int i) {
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
        C0262b.m225a(parcel, 10, markerOptions.isFlat());
        C0262b.m214a(parcel, 11, markerOptions.getRotation());
        C0262b.m214a(parcel, 12, markerOptions.getInfoWindowAnchorU());
        C0262b.m214a(parcel, 13, markerOptions.getInfoWindowAnchorV());
        C0262b.m214a(parcel, 14, markerOptions.getAlpha());
        C0262b.m211F(parcel, p);
    }

    public MarkerOptions createFromParcel(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        LatLng latLng = null;
        String str = null;
        String str2 = null;
        IBinder iBinder = null;
        float f = 0.0f;
        float f2 = 0.0f;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        float f3 = 0.0f;
        float f4 = 0.5f;
        float f5 = 0.0f;
        float f6 = GalleryViewer.PROGRESS_COMPLETED;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    latLng = (LatLng) C0261a.m176a(parcel, n, LatLng.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    iBinder = C0261a.m197o(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    f2 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    z3 = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    f3 = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    f4 = C0261a.m191k(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    f5 = C0261a.m191k(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    f6 = C0261a.m191k(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new MarkerOptions(i, latLng, str, str2, iBinder, f, f2, z, z2, z3, f3, f4, f5, f6);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public MarkerOptions[] newArray(int size) {
        return new MarkerOptions[size];
    }
}
