package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class StreetViewPanoramaOptionsCreator implements Creator<StreetViewPanoramaOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1225a(StreetViewPanoramaOptions streetViewPanoramaOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, streetViewPanoramaOptions.getVersionCode());
        C0262b.m219a(parcel, 2, streetViewPanoramaOptions.getStreetViewPanoramaCamera(), i, false);
        C0262b.m222a(parcel, 3, streetViewPanoramaOptions.getPanoramaId(), false);
        C0262b.m219a(parcel, 4, streetViewPanoramaOptions.getPosition(), i, false);
        C0262b.m221a(parcel, 5, streetViewPanoramaOptions.getRadius(), false);
        C0262b.m212a(parcel, 6, streetViewPanoramaOptions.it());
        C0262b.m212a(parcel, 7, streetViewPanoramaOptions.il());
        C0262b.m212a(parcel, 8, streetViewPanoramaOptions.iu());
        C0262b.m212a(parcel, 9, streetViewPanoramaOptions.iv());
        C0262b.m212a(parcel, 10, streetViewPanoramaOptions.ih());
        C0262b.m211F(parcel, p);
    }

    public StreetViewPanoramaOptions createFromParcel(Parcel parcel) {
        Integer num = null;
        byte b = (byte) 0;
        int o = C0261a.m196o(parcel);
        byte b2 = (byte) 0;
        byte b3 = (byte) 0;
        byte b4 = (byte) 0;
        byte b5 = (byte) 0;
        LatLng latLng = null;
        String str = null;
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        int i = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    streetViewPanoramaCamera = (StreetViewPanoramaCamera) C0261a.m176a(parcel, n, StreetViewPanoramaCamera.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    latLng = (LatLng) C0261a.m176a(parcel, n, LatLng.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    num = C0261a.m188h(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    b5 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    b4 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    b3 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    b2 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    b = C0261a.m185e(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new StreetViewPanoramaOptions(i, streetViewPanoramaCamera, str, latLng, num, b5, b4, b3, b2, b);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public StreetViewPanoramaOptions[] newArray(int size) {
        return new StreetViewPanoramaOptions[size];
    }
}
