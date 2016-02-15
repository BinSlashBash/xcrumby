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
import com.google.android.gms.cast.a;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ApplicationMetadata
implements SafeParcelable {
    public static final Parcelable.Creator<ApplicationMetadata> CREATOR = new a();
    String mName;
    private final int xH;
    String xI;
    List<WebImage> xJ;
    List<String> xK;
    String xL;
    Uri xM;

    private ApplicationMetadata() {
        this.xH = 1;
        this.xJ = new ArrayList<WebImage>();
        this.xK = new ArrayList<String>();
    }

    ApplicationMetadata(int n2, String string2, String string3, List<WebImage> list, List<String> list2, String string4, Uri uri) {
        this.xH = n2;
        this.xI = string2;
        this.mName = string3;
        this.xJ = list;
        this.xK = list2;
        this.xL = string4;
        this.xM = uri;
    }

    public boolean areNamespacesSupported(List<String> list) {
        if (this.xK != null && this.xK.containsAll(list)) {
            return true;
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public Uri dz() {
        return this.xM;
    }

    public String getApplicationId() {
        return this.xI;
    }

    public List<WebImage> getImages() {
        return this.xJ;
    }

    public String getName() {
        return this.mName;
    }

    public String getSenderAppIdentifier() {
        return this.xL;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean isNamespaceSupported(String string2) {
        if (this.xK != null && this.xK.contains(string2)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.mName;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }
}

