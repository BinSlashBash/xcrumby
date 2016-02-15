package com.google.android.gms.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public final class ah implements SafeParcelable {
    public static final ai CREATOR;
    public final Bundle extras;
    public final long lH;
    public final int lI;
    public final List<String> lJ;
    public final boolean lK;
    public final int lL;
    public final boolean lM;
    public final String lN;
    public final av lO;
    public final Location lP;
    public final String lQ;
    public final int versionCode;

    static {
        CREATOR = new ai();
    }

    public ah(int i, long j, Bundle bundle, int i2, List<String> list, boolean z, int i3, boolean z2, String str, av avVar, Location location, String str2) {
        this.versionCode = i;
        this.lH = j;
        this.extras = bundle;
        this.lI = i2;
        this.lJ = list;
        this.lK = z;
        this.lL = i3;
        this.lM = z2;
        this.lN = str;
        this.lO = avVar;
        this.lP = location;
        this.lQ = str2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        ai.m610a(this, out, flags);
    }
}
