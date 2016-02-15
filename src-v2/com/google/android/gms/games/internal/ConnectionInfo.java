/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.games.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.internal.ConnectionInfoCreator;

public class ConnectionInfo
implements SafeParcelable {
    public static final ConnectionInfoCreator CREATOR = new ConnectionInfoCreator();
    private final String Is;
    private final int It;
    private final int xH;

    public ConnectionInfo(int n2, String string2, int n3) {
        this.xH = n2;
        this.Is = string2;
        this.It = n3;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public String gi() {
        return this.Is;
    }

    public int gj() {
        return this.It;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ConnectionInfoCreator.a(this, parcel, n2);
    }
}

