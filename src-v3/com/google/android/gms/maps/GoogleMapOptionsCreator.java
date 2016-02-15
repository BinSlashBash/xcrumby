package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.maps.model.CameraPosition;

public class GoogleMapOptionsCreator implements Creator<GoogleMapOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1224a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, googleMapOptions.getVersionCode());
        C0262b.m212a(parcel, 2, googleMapOptions.ig());
        C0262b.m212a(parcel, 3, googleMapOptions.ih());
        C0262b.m234c(parcel, 4, googleMapOptions.getMapType());
        C0262b.m219a(parcel, 5, googleMapOptions.getCamera(), i, false);
        C0262b.m212a(parcel, 6, googleMapOptions.ii());
        C0262b.m212a(parcel, 7, googleMapOptions.ij());
        C0262b.m212a(parcel, 8, googleMapOptions.ik());
        C0262b.m212a(parcel, 9, googleMapOptions.il());
        C0262b.m212a(parcel, 10, googleMapOptions.im());
        C0262b.m212a(parcel, 11, googleMapOptions.in());
        C0262b.m211F(parcel, p);
    }

    public GoogleMapOptions createFromParcel(Parcel parcel) {
        byte b = (byte) 0;
        int o = C0261a.m196o(parcel);
        CameraPosition cameraPosition = null;
        byte b2 = (byte) 0;
        byte b3 = (byte) 0;
        byte b4 = (byte) 0;
        byte b5 = (byte) 0;
        byte b6 = (byte) 0;
        int i = 0;
        byte b7 = (byte) 0;
        byte b8 = (byte) 0;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    b8 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    b7 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    cameraPosition = (CameraPosition) C0261a.m176a(parcel, n, CameraPosition.CREATOR);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    b6 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    b5 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    b4 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    b3 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    b2 = C0261a.m185e(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    b = C0261a.m185e(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new GoogleMapOptions(i2, b8, b7, i, cameraPosition, b6, b5, b4, b3, b2, b);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public GoogleMapOptions[] newArray(int size) {
        return new GoogleMapOptions[size];
    }
}
