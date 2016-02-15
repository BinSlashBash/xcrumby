/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ai;
import com.google.android.gms.internal.av;
import java.util.List;

public final class ah
implements SafeParcelable {
    public static final ai CREATOR = new ai();
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

    public ah(int n2, long l2, Bundle bundle, int n3, List<String> list, boolean bl2, int n4, boolean bl3, String string2, av av2, Location location, String string3) {
        this.versionCode = n2;
        this.lH = l2;
        this.extras = bundle;
        this.lI = n3;
        this.lJ = list;
        this.lK = bl2;
        this.lL = n4;
        this.lM = bl3;
        this.lN = string2;
        this.lO = av2;
        this.lP = location;
        this.lQ = string3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ai.a(this, parcel, n2);
    }
}

