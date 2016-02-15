package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class ad implements Creator<OnListEntriesResponse> {
    static void m256a(OnListEntriesResponse onListEntriesResponse, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, onListEntriesResponse.xH);
        C0262b.m219a(parcel, 2, onListEntriesResponse.FJ, i, false);
        C0262b.m225a(parcel, 3, onListEntriesResponse.Fg);
        C0262b.m211F(parcel, p);
    }

    public OnListEntriesResponse m257R(Parcel parcel) {
        boolean z = false;
        int o = C0261a.m196o(parcel);
        DataHolder dataHolder = null;
        int i = 0;
        while (parcel.dataPosition() < o) {
            DataHolder dataHolder2;
            int g;
            boolean z2;
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    boolean z3 = z;
                    dataHolder2 = dataHolder;
                    g = C0261a.m187g(parcel, n);
                    z2 = z3;
                    break;
                case Std.STD_URL /*2*/:
                    g = i;
                    DataHolder dataHolder3 = (DataHolder) C0261a.m176a(parcel, n, DataHolder.CREATOR);
                    z2 = z;
                    dataHolder2 = dataHolder3;
                    break;
                case Std.STD_URI /*3*/:
                    z2 = C0261a.m183c(parcel, n);
                    dataHolder2 = dataHolder;
                    g = i;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    z2 = z;
                    dataHolder2 = dataHolder;
                    g = i;
                    break;
            }
            i = g;
            dataHolder = dataHolder2;
            z = z2;
        }
        if (parcel.dataPosition() == o) {
            return new OnListEntriesResponse(i, dataHolder, z);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public OnListEntriesResponse[] av(int i) {
        return new OnListEntriesResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m257R(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return av(x0);
    }
}
