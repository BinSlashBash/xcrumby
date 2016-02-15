/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import com.google.android.gms.maps.model.StreetViewPanoramaLink;
import com.google.android.gms.maps.model.StreetViewPanoramaLinkCreator;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public class StreetViewPanoramaLocationCreator
implements Parcelable.Creator<StreetViewPanoramaLocation> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(StreetViewPanoramaLocation streetViewPanoramaLocation, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, streetViewPanoramaLocation.getVersionCode());
        b.a((Parcel)parcel, (int)2, (Parcelable[])streetViewPanoramaLocation.links, (int)n2, (boolean)false);
        b.a(parcel, 3, streetViewPanoramaLocation.position, n2, false);
        b.a(parcel, 4, streetViewPanoramaLocation.panoId, false);
        b.F(parcel, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public StreetViewPanoramaLocation createFromParcel(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        StreetViewPanoramaLink[] arrstreetViewPanoramaLink = null;
        StreetViewPanoramaLink[] arrstreetViewPanoramaLink2 = null;
        while (parcel.dataPosition() < n2) {
            StreetViewPanoramaLink[] arrstreetViewPanoramaLink3;
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    arrstreetViewPanoramaLink3 = arrstreetViewPanoramaLink;
                    arrstreetViewPanoramaLink = arrstreetViewPanoramaLink2;
                    arrstreetViewPanoramaLink2 = arrstreetViewPanoramaLink3;
                    break;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    arrstreetViewPanoramaLink3 = arrstreetViewPanoramaLink2;
                    arrstreetViewPanoramaLink2 = arrstreetViewPanoramaLink;
                    arrstreetViewPanoramaLink = arrstreetViewPanoramaLink3;
                    break;
                }
                case 2: {
                    arrstreetViewPanoramaLink3 = (StreetViewPanoramaLink[])a.b(parcel, n4, StreetViewPanoramaLink.CREATOR);
                    arrstreetViewPanoramaLink2 = arrstreetViewPanoramaLink;
                    arrstreetViewPanoramaLink = arrstreetViewPanoramaLink3;
                    break;
                }
                case 3: {
                    arrstreetViewPanoramaLink3 = (LatLng)a.a(parcel, n4, LatLng.CREATOR);
                    arrstreetViewPanoramaLink = arrstreetViewPanoramaLink2;
                    arrstreetViewPanoramaLink2 = arrstreetViewPanoramaLink3;
                    break;
                }
                case 4: {
                    string2 = a.n(parcel, n4);
                    arrstreetViewPanoramaLink3 = arrstreetViewPanoramaLink2;
                    arrstreetViewPanoramaLink2 = arrstreetViewPanoramaLink;
                    arrstreetViewPanoramaLink = arrstreetViewPanoramaLink3;
                }
            }
            arrstreetViewPanoramaLink3 = arrstreetViewPanoramaLink;
            arrstreetViewPanoramaLink = arrstreetViewPanoramaLink2;
            arrstreetViewPanoramaLink2 = arrstreetViewPanoramaLink3;
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new StreetViewPanoramaLocation(n3, arrstreetViewPanoramaLink2, (LatLng)arrstreetViewPanoramaLink, string2);
    }

    public StreetViewPanoramaLocation[] newArray(int n2) {
        return new StreetViewPanoramaLocation[n2];
    }
}

