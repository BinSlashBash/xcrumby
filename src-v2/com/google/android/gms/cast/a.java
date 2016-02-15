/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;
import java.util.List;

public class a
implements Parcelable.Creator<ApplicationMetadata> {
    static void a(ApplicationMetadata applicationMetadata, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, applicationMetadata.getVersionCode());
        b.a(parcel, 2, applicationMetadata.getApplicationId(), false);
        b.a(parcel, 3, applicationMetadata.getName(), false);
        b.b(parcel, 4, applicationMetadata.getImages(), false);
        b.a(parcel, 5, applicationMetadata.xK, false);
        b.a(parcel, 6, applicationMetadata.getSenderAppIdentifier(), false);
        b.a(parcel, 7, (Parcelable)applicationMetadata.dz(), n2, false);
        b.F(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.j(parcel);
    }

    public ApplicationMetadata j(Parcel parcel) {
        Uri uri = null;
        int n2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n3 = 0;
        String string2 = null;
        ArrayList<String> arrayList = null;
        ArrayList<WebImage> arrayList2 = null;
        String string3 = null;
        String string4 = null;
        block9 : while (parcel.dataPosition() < n2) {
            int n4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n4);
                    continue block9;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n4);
                    continue block9;
                }
                case 2: {
                    string4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, n4);
                    continue block9;
                }
                case 3: {
                    string3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, n4);
                    continue block9;
                }
                case 4: {
                    arrayList2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, n4, WebImage.CREATOR);
                    continue block9;
                }
                case 5: {
                    arrayList = com.google.android.gms.common.internal.safeparcel.a.A(parcel, n4);
                    continue block9;
                }
                case 6: {
                    string2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, n4);
                    continue block9;
                }
                case 7: 
            }
            uri = (Uri)com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, Uri.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ApplicationMetadata(n3, string4, string3, arrayList2, arrayList, string2, uri);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.w(n2);
    }

    public ApplicationMetadata[] w(int n2) {
        return new ApplicationMetadata[n2];
    }
}

