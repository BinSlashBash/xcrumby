package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.location.GeofenceStatusCodes;

/* renamed from: com.google.android.gms.drive.query.a */
public class C0290a implements Creator<Query> {
    static void m333a(Query query, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, query.xH);
        C0262b.m219a(parcel, 1, query.GA, i, false);
        C0262b.m222a(parcel, 3, query.GB, false);
        C0262b.m219a(parcel, 4, query.GC, i, false);
        C0262b.m211F(parcel, p);
    }

    public Query[] aG(int i) {
        return new Query[i];
    }

    public Query ac(Parcel parcel) {
        SortOrder sortOrder = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        LogicalFilter logicalFilter = null;
        while (parcel.dataPosition() < o) {
            int i2;
            LogicalFilter logicalFilter2;
            SortOrder sortOrder2;
            String str2;
            int n = C0261a.m194n(parcel);
            SortOrder sortOrder3;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = i;
                    String str3 = str;
                    logicalFilter2 = (LogicalFilter) C0261a.m176a(parcel, n, LogicalFilter.CREATOR);
                    sortOrder2 = sortOrder;
                    str2 = str3;
                    break;
                case Std.STD_URI /*3*/:
                    logicalFilter2 = logicalFilter;
                    i2 = i;
                    sortOrder3 = sortOrder;
                    str2 = C0261a.m195n(parcel, n);
                    sortOrder2 = sortOrder3;
                    break;
                case Std.STD_CLASS /*4*/:
                    sortOrder2 = (SortOrder) C0261a.m176a(parcel, n, SortOrder.CREATOR);
                    str2 = str;
                    logicalFilter2 = logicalFilter;
                    i2 = i;
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    sortOrder3 = sortOrder;
                    str2 = str;
                    logicalFilter2 = logicalFilter;
                    i2 = C0261a.m187g(parcel, n);
                    sortOrder2 = sortOrder3;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    sortOrder2 = sortOrder;
                    str2 = str;
                    logicalFilter2 = logicalFilter;
                    i2 = i;
                    break;
            }
            i = i2;
            logicalFilter = logicalFilter2;
            str = str2;
            sortOrder = sortOrder2;
        }
        if (parcel.dataPosition() == o) {
            return new Query(i, logicalFilter, str, sortOrder);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ac(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aG(x0);
    }
}
