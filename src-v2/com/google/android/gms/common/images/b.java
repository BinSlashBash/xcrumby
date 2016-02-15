/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.a;

public class b
implements Parcelable.Creator<WebImage> {
    static void a(WebImage webImage, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, webImage.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable)webImage.getUrl(), n2, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, webImage.getWidth());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, webImage.getHeight());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n3);
    }

    public WebImage[] K(int n2) {
        return new WebImage[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.l(parcel);
    }

    public WebImage l(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        Uri uri = null;
        int n4 = 0;
        int n5 = 0;
        block6 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block6;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    continue block6;
                }
                case 2: {
                    uri = (Uri)a.a(parcel, n6, Uri.CREATOR);
                    continue block6;
                }
                case 3: {
                    n4 = a.g(parcel, n6);
                    continue block6;
                }
                case 4: 
            }
            n2 = a.g(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new WebImage(n5, uri, n4, n2);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.K(n2);
    }
}

