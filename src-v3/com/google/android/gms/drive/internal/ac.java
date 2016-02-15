package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ConflictEvent;

public class ac implements Creator<OnEventResponse> {
    static void m254a(OnEventResponse onEventResponse, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, onEventResponse.xH);
        C0262b.m234c(parcel, 2, onEventResponse.ES);
        C0262b.m219a(parcel, 3, onEventResponse.FH, i, false);
        C0262b.m219a(parcel, 4, onEventResponse.FI, i, false);
        C0262b.m211F(parcel, p);
    }

    public OnEventResponse m255Q(Parcel parcel) {
        ConflictEvent conflictEvent = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        ChangeEvent changeEvent = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            ChangeEvent changeEvent2;
            int i3;
            ConflictEvent conflictEvent2;
            int n = C0261a.m194n(parcel);
            ConflictEvent conflictEvent3;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    conflictEvent3 = conflictEvent;
                    changeEvent2 = changeEvent;
                    i3 = i;
                    i = C0261a.m187g(parcel, n);
                    conflictEvent2 = conflictEvent3;
                    break;
                case Std.STD_URL /*2*/:
                    i = i2;
                    ChangeEvent changeEvent3 = changeEvent;
                    i3 = C0261a.m187g(parcel, n);
                    conflictEvent2 = conflictEvent;
                    changeEvent2 = changeEvent3;
                    break;
                case Std.STD_URI /*3*/:
                    i3 = i;
                    i = i2;
                    conflictEvent3 = conflictEvent;
                    changeEvent2 = (ChangeEvent) C0261a.m176a(parcel, n, ChangeEvent.CREATOR);
                    conflictEvent2 = conflictEvent3;
                    break;
                case Std.STD_CLASS /*4*/:
                    conflictEvent2 = (ConflictEvent) C0261a.m176a(parcel, n, ConflictEvent.CREATOR);
                    changeEvent2 = changeEvent;
                    i3 = i;
                    i = i2;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    conflictEvent2 = conflictEvent;
                    changeEvent2 = changeEvent;
                    i3 = i;
                    i = i2;
                    break;
            }
            i2 = i;
            i = i3;
            changeEvent = changeEvent2;
            conflictEvent = conflictEvent2;
        }
        if (parcel.dataPosition() == o) {
            return new OnEventResponse(i2, i, changeEvent, conflictEvent);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public OnEventResponse[] au(int i) {
        return new OnEventResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m255Q(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return au(x0);
    }
}
