package com.google.android.gms.games.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ConnectionInfo implements SafeParcelable {
    public static final ConnectionInfoCreator CREATOR;
    private final String Is;
    private final int It;
    private final int xH;

    static {
        CREATOR = new ConnectionInfoCreator();
    }

    public ConnectionInfo(int versionCode, String clientAddress, int registrationLatency) {
        this.xH = versionCode;
        this.Is = clientAddress;
        this.It = registrationLatency;
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

    public void writeToParcel(Parcel out, int flags) {
        ConnectionInfoCreator.m363a(this, out, flags);
    }
}
