package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.List;

/* renamed from: com.google.android.gms.identity.intents.a */
public class C0318a implements Creator<UserAddressRequest> {
    static void m586a(UserAddressRequest userAddressRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, userAddressRequest.getVersionCode());
        C0262b.m233b(parcel, 2, userAddressRequest.Ny, false);
        C0262b.m211F(parcel, p);
    }

    public UserAddressRequest ay(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        List list = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    list = C0261a.m182c(parcel, n, CountrySpecification.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new UserAddressRequest(i, list);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public UserAddressRequest[] bs(int i) {
        return new UserAddressRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ay(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bs(x0);
    }
}
