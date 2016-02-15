/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;
import java.util.List;

public class b
implements Parcelable.Creator<CastDevice> {
    static void a(CastDevice castDevice, Parcel parcel, int n2) {
        n2 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, castDevice.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, castDevice.getDeviceId(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, castDevice.yb, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, castDevice.getFriendlyName(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, castDevice.getModelName(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, castDevice.getDeviceVersion(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, castDevice.getServicePort());
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 8, castDevice.getIcons(), false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.k(parcel);
    }

    public CastDevice k(Parcel parcel) {
        int n2 = 0;
        ArrayList<WebImage> arrayList = null;
        int n3 = a.o(parcel);
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        int n4 = 0;
        block10 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block10;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block10;
                }
                case 2: {
                    string6 = a.n(parcel, n5);
                    continue block10;
                }
                case 3: {
                    string5 = a.n(parcel, n5);
                    continue block10;
                }
                case 4: {
                    string4 = a.n(parcel, n5);
                    continue block10;
                }
                case 5: {
                    string3 = a.n(parcel, n5);
                    continue block10;
                }
                case 6: {
                    string2 = a.n(parcel, n5);
                    continue block10;
                }
                case 7: {
                    n2 = a.g(parcel, n5);
                    continue block10;
                }
                case 8: 
            }
            arrayList = a.c(parcel, n5, WebImage.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new CastDevice(n4, string6, string5, string4, string3, string2, n2, arrayList);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.y(n2);
    }

    public CastDevice[] y(int n2) {
        return new CastDevice[n2];
    }
}

