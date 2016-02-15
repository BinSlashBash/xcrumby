/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.aw;

public final class av
implements SafeParcelable {
    public static final aw CREATOR = new aw();
    public final int backgroundColor;
    public final int mA;
    public final int mB;
    public final String mC;
    public final int mq;
    public final int mr;
    public final int ms;
    public final int mt;
    public final int mu;
    public final int mv;
    public final int mw;
    public final String mx;
    public final int my;
    public final String mz;
    public final int versionCode;

    av(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, String string2, int n11, String string3, int n12, int n13, String string4) {
        this.versionCode = n2;
        this.mq = n3;
        this.backgroundColor = n4;
        this.mr = n5;
        this.ms = n6;
        this.mt = n7;
        this.mu = n8;
        this.mv = n9;
        this.mw = n10;
        this.mx = string2;
        this.my = n11;
        this.mz = string3;
        this.mA = n12;
        this.mB = n13;
        this.mC = string4;
    }

    public av(SearchAdRequest searchAdRequest) {
        this.versionCode = 1;
        this.mq = searchAdRequest.getAnchorTextColor();
        this.backgroundColor = searchAdRequest.getBackgroundColor();
        this.mr = searchAdRequest.getBackgroundGradientBottom();
        this.ms = searchAdRequest.getBackgroundGradientTop();
        this.mt = searchAdRequest.getBorderColor();
        this.mu = searchAdRequest.getBorderThickness();
        this.mv = searchAdRequest.getBorderType();
        this.mw = searchAdRequest.getCallButtonColor();
        this.mx = searchAdRequest.getCustomChannels();
        this.my = searchAdRequest.getDescriptionTextColor();
        this.mz = searchAdRequest.getFontFace();
        this.mA = searchAdRequest.getHeaderTextColor();
        this.mB = searchAdRequest.getHeaderTextSize();
        this.mC = searchAdRequest.getQuery();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        aw.a(this, parcel, n2);
    }
}

