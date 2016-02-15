package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.gms.drive.query.internal.g */
public class C0298g implements Creator<LogicalFilter> {
    static void m341a(LogicalFilter logicalFilter, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, logicalFilter.xH);
        C0262b.m219a(parcel, 1, logicalFilter.GG, i, false);
        C0262b.m233b(parcel, 2, logicalFilter.GS, false);
        C0262b.m211F(parcel, p);
    }

    public LogicalFilter[] aN(int i) {
        return new LogicalFilter[i];
    }

    public LogicalFilter aj(Parcel parcel) {
        List list = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        Operator operator = null;
        while (parcel.dataPosition() < o) {
            int i2;
            Operator operator2;
            ArrayList c;
            int n = C0261a.m194n(parcel);
            List list2;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = i;
                    Operator operator3 = (Operator) C0261a.m176a(parcel, n, Operator.CREATOR);
                    list2 = list;
                    operator2 = operator3;
                    break;
                case Std.STD_URL /*2*/:
                    c = C0261a.m182c(parcel, n, FilterHolder.CREATOR);
                    operator2 = operator;
                    i2 = i;
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    List list3 = list;
                    operator2 = operator;
                    i2 = C0261a.m187g(parcel, n);
                    list2 = list3;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    c = list;
                    operator2 = operator;
                    i2 = i;
                    break;
            }
            i = i2;
            operator = operator2;
            Object obj = c;
        }
        if (parcel.dataPosition() == o) {
            return new LogicalFilter(i, operator, list);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aj(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aN(x0);
    }
}
