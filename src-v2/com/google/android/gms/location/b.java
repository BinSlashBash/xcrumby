/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.location.c;

public class b
implements SafeParcelable {
    public static final c CREATOR = new c();
    int Oh;
    int Oi;
    long Oj;
    private final int xH;

    b(int n2, int n3, int n4, long l2) {
        this.xH = n2;
        this.Oh = n3;
        this.Oi = n4;
        this.Oj = l2;
    }

    private String by(int n2) {
        switch (n2) {
            default: {
                return "STATUS_UNKNOWN";
            }
            case 0: {
                return "STATUS_SUCCESSFUL";
            }
            case 2: {
                return "STATUS_TIMED_OUT_ON_SCAN";
            }
            case 3: {
                return "STATUS_NO_INFO_IN_DATABASE";
            }
            case 4: {
                return "STATUS_INVALID_SCAN";
            }
            case 5: {
                return "STATUS_UNABLE_TO_QUERY_DATABASE";
            }
            case 6: {
                return "STATUS_SCANS_DISABLED_IN_SETTINGS";
            }
            case 7: {
                return "STATUS_LOCATION_DISABLED_IN_SETTINGS";
            }
            case 8: 
        }
        return "STATUS_IN_PROGRESS";
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof b)) {
            return false;
        }
        object = (b)object;
        if (this.Oh != object.Oh) return false;
        if (this.Oi != object.Oi) return false;
        if (this.Oj != object.Oj) return false;
        return true;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.Oh, this.Oi, this.Oj);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LocationStatus[cell status: ").append(this.by(this.Oh));
        stringBuilder.append(", wifi status: ").append(this.by(this.Oi));
        stringBuilder.append(", elapsed realtime ns: ").append(this.Oj);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        c.a(this, parcel, n2);
    }
}

