package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;

/* renamed from: com.google.android.gms.drive.query.internal.d */
public class C0295d implements Creator<FilterHolder> {
    static void m338a(FilterHolder filterHolder, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m219a(parcel, 1, filterHolder.GK, i, false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, filterHolder.xH);
        C0262b.m219a(parcel, 2, filterHolder.GL, i, false);
        C0262b.m219a(parcel, 3, filterHolder.GM, i, false);
        C0262b.m219a(parcel, 4, filterHolder.GN, i, false);
        C0262b.m219a(parcel, 5, filterHolder.GO, i, false);
        C0262b.m219a(parcel, 6, filterHolder.GP, i, false);
        C0262b.m211F(parcel, p);
    }

    public FilterHolder[] aL(int i) {
        return new FilterHolder[i];
    }

    public FilterHolder ah(Parcel parcel) {
        MatchAllFilter matchAllFilter = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        InFilter inFilter = null;
        NotFilter notFilter = null;
        LogicalFilter logicalFilter = null;
        FieldOnlyFilter fieldOnlyFilter = null;
        ComparisonFilter comparisonFilter = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    comparisonFilter = (ComparisonFilter) C0261a.m176a(parcel, n, ComparisonFilter.CREATOR);
                    break;
                case Std.STD_URL /*2*/:
                    fieldOnlyFilter = (FieldOnlyFilter) C0261a.m176a(parcel, n, FieldOnlyFilter.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    logicalFilter = (LogicalFilter) C0261a.m176a(parcel, n, LogicalFilter.CREATOR);
                    break;
                case Std.STD_CLASS /*4*/:
                    notFilter = (NotFilter) C0261a.m176a(parcel, n, NotFilter.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    inFilter = (InFilter) C0261a.m176a(parcel, n, InFilter.CREATOR);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    matchAllFilter = (MatchAllFilter) C0261a.m176a(parcel, n, MatchAllFilter.CREATOR);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new FilterHolder(i, comparisonFilter, fieldOnlyFilter, logicalFilter, notFilter, inFilter, matchAllFilter);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ah(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aL(x0);
    }
}
