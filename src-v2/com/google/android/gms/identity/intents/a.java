/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.identity.intents.UserAddressRequest;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.List;

public class a
implements Parcelable.Creator<UserAddressRequest> {
    static void a(UserAddressRequest userAddressRequest, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, userAddressRequest.getVersionCode());
        b.b(parcel, 2, userAddressRequest.Ny, false);
        b.F(parcel, n2);
    }

    public UserAddressRequest ay(Parcel parcel) {
        int n2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n3 = 0;
        ArrayList<CountrySpecification> arrayList = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            arrayList = com.google.android.gms.common.internal.safeparcel.a.c(parcel, n4, CountrySpecification.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new UserAddressRequest(n3, arrayList);
    }

    public UserAddressRequest[] bs(int n2) {
        return new UserAddressRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ay(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bs(n2);
    }
}

