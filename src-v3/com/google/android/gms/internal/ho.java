package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class ho implements SafeParcelable {
    public static final Creator<ho> CREATOR;
    private final LatLng Re;
    private final String Rf;
    private final List<hm> Rg;
    private final String Rh;
    private final String Ri;
    private final String mName;
    final int xH;

    static {
        CREATOR = new hp();
    }

    ho(int i, String str, LatLng latLng, String str2, List<hm> list, String str3, String str4) {
        this.xH = i;
        this.mName = str;
        this.Re = latLng;
        this.Rf = str2;
        this.Rg = new ArrayList(list);
        this.Rh = str3;
        this.Ri = str4;
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

    public void writeToParcel(Parcel parcel, int flags) {
        hp.m1071a(this, parcel, flags);
    }
}
