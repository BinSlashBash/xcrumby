package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class TileOverlayOptionsCreator implements Creator<TileOverlayOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1267a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, tileOverlayOptions.getVersionCode());
        C0262b.m217a(parcel, 2, tileOverlayOptions.iG(), false);
        C0262b.m225a(parcel, 3, tileOverlayOptions.isVisible());
        C0262b.m214a(parcel, 4, tileOverlayOptions.getZIndex());
        C0262b.m225a(parcel, 5, tileOverlayOptions.getFadeIn());
        C0262b.m211F(parcel, p);
    }

    public TileOverlayOptions createFromParcel(Parcel parcel) {
        boolean z = false;
        int o = C0261a.m196o(parcel);
        IBinder iBinder = null;
        float f = 0.0f;
        boolean z2 = true;
        int i = 0;
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
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new TileOverlayOptions(i, iBinder, z, f, z2);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public TileOverlayOptions[] newArray(int size) {
        return new TileOverlayOptions[size];
    }
}
