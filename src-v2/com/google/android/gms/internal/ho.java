/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.hm;
import com.google.android.gms.internal.hp;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ho
implements SafeParcelable {
    public static final Parcelable.Creator<ho> CREATOR = new hp();
    private final LatLng Re;
    private final String Rf;
    private final List<hm> Rg;
    private final String Rh;
    private final String Ri;
    private final String mName;
    final int xH;

    ho(int n2, String string2, LatLng latLng, String string3, List<hm> list, String string4, String string5) {
        this.xH = n2;
        this.mName = string2;
        this.Re = latLng;
        this.Rf = string3;
        this.Rg = new ArrayList<hm>(list);
        this.Rh = string4;
        this.Ri = string5;
    }

    public int describeContents() {
        return 0;
    }

    public String getAddress() {
        return this.Rf;
    }

    public String getName() {
        return this.mName;
    }

    public String getPhoneNumber() {
        return this.Rh;
    }

    public LatLng ia() {
        return this.Re;
    }

    public List<hm> ib() {
        return this.Rg;
    }

    public String ic() {
        return this.Ri;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        hp.a(this, parcel, n2);
    }
}

