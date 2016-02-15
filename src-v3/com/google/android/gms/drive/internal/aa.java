package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class aa implements Creator<OnDownloadProgressResponse> {
    static void m250a(OnDownloadProgressResponse onDownloadProgressResponse, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, onDownloadProgressResponse.xH);
        C0262b.m215a(parcel, 2, onDownloadProgressResponse.FF);
        C0262b.m215a(parcel, 3, onDownloadProgressResponse.FG);
        C0262b.m211F(parcel, p);
    }

    public OnDownloadProgressResponse m251O(Parcel parcel) {
        long j = 0;
        int o = C0261a.m196o(parcel);
        int i = 0;
        long j2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new OnDownloadProgressResponse(i, j2, j);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public OnDownloadProgressResponse[] as(int i) {
        return new OnDownloadProgressResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m251O(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return as(x0);
    }
}
