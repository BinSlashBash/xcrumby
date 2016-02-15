package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.drive.Contents;

/* renamed from: com.google.android.gms.drive.internal.e */
public class C0274e implements Creator<CloseContentsRequest> {
    static void m279a(CloseContentsRequest closeContentsRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, closeContentsRequest.xH);
        C0262b.m219a(parcel, 2, closeContentsRequest.EX, i, false);
        C0262b.m220a(parcel, 3, closeContentsRequest.EY, false);
        C0262b.m211F(parcel, p);
    }

    public CloseContentsRequest m280F(Parcel parcel) {
        Boolean bool = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        Contents contents = null;
        while (parcel.dataPosition() < o) {
            Contents contents2;
            int g;
            Boolean bool2;
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    Boolean bool3 = bool;
                    contents2 = contents;
                    g = C0261a.m187g(parcel, n);
                    bool2 = bool3;
                    break;
                case Std.STD_URL /*2*/:
                    g = i;
                    Contents contents3 = (Contents) C0261a.m176a(parcel, n, Contents.CREATOR);
                    bool2 = bool;
                    contents2 = contents3;
                    break;
                case Std.STD_URI /*3*/:
                    bool2 = C0261a.m184d(parcel, n);
                    contents2 = contents;
                    g = i;
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    bool2 = bool;
                    contents2 = contents;
                    g = i;
                    break;
            }
            i = g;
            contents = contents2;
            bool = bool2;
        }
        if (parcel.dataPosition() == o) {
            return new CloseContentsRequest(i, contents, bool);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public CloseContentsRequest[] aj(int i) {
        return new CloseContentsRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m280F(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aj(x0);
    }
}
