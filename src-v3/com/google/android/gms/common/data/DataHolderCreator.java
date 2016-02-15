package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;

public class DataHolderCreator implements Creator<DataHolder> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m141a(DataHolder dataHolder, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m229a(parcel, 1, dataHolder.er(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, dataHolder.getVersionCode());
        C0262b.m228a(parcel, 2, dataHolder.es(), i, false);
        C0262b.m234c(parcel, 3, dataHolder.getStatusCode());
        C0262b.m216a(parcel, 4, dataHolder.getMetadata(), false);
        C0262b.m211F(parcel, p);
    }

    public DataHolder createFromParcel(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int o = C0261a.m196o(parcel);
        CursorWindow[] cursorWindowArr = null;
        String[] strArr = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    strArr = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    cursorWindowArr = (CursorWindow[]) C0261a.m181b(parcel, n, CursorWindow.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    bundle = C0261a.m198p(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() != o) {
            throw new C0260a("Overread allowed size end=" + o, parcel);
        }
        DataHolder dataHolder = new DataHolder(i2, strArr, cursorWindowArr, i, bundle);
        dataHolder.validateContents();
        return dataHolder;
    }

    public DataHolder[] newArray(int size) {
        return new DataHolder[size];
    }
}
