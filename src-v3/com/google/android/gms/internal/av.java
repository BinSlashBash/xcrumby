package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class av implements SafeParcelable {
    public static final aw CREATOR;
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

    static {
        CREATOR = new aw();
    }

    av(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str, int i10, String str2, int i11, int i12, String str3) {
        this.versionCode = i;
        this.mq = i2;
        this.backgroundColor = i3;
        this.mr = i4;
        this.ms = i5;
        this.mt = i6;
        this.mu = i7;
        this.mv = i8;
        this.mw = i9;
        this.mx = str;
        this.my = i10;
        this.mz = str2;
        this.mA = i11;
        this.mB = i12;
        this.mC = str3;
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

    public void writeToParcel(Parcel out, int flags) {
        aw.m654a(this, out, flags);
    }
}
