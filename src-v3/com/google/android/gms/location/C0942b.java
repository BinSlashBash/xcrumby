package com.google.android.gms.location;

import android.os.Parcel;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.location.b */
public class C0942b implements SafeParcelable {
    public static final C0429c CREATOR;
    int Oh;
    int Oi;
    long Oj;
    private final int xH;

    static {
        CREATOR = new C0429c();
    }

    C0942b(int i, int i2, int i3, long j) {
        this.xH = i;
        this.Oh = i2;
        this.Oi = i3;
        this.Oj = j;
    }

    private String by(int i) {
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
                return "STATUS_SUCCESSFUL";
            case Std.STD_URL /*2*/:
                return "STATUS_TIMED_OUT_ON_SCAN";
            case Std.STD_URI /*3*/:
                return "STATUS_NO_INFO_IN_DATABASE";
            case Std.STD_CLASS /*4*/:
                return "STATUS_INVALID_SCAN";
            case Std.STD_JAVA_TYPE /*5*/:
                return "STATUS_UNABLE_TO_QUERY_DATABASE";
            case Std.STD_CURRENCY /*6*/:
                return "STATUS_SCANS_DISABLED_IN_SETTINGS";
            case Std.STD_PATTERN /*7*/:
                return "STATUS_LOCATION_DISABLED_IN_SETTINGS";
            case Std.STD_LOCALE /*8*/:
                return "STATUS_IN_PROGRESS";
            default:
                return "STATUS_UNKNOWN";
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof C0942b)) {
            return false;
        }
        C0942b c0942b = (C0942b) other;
        return this.Oh == c0942b.Oh && this.Oi == c0942b.Oi && this.Oj == c0942b.Oj;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Integer.valueOf(this.Oh), Integer.valueOf(this.Oi), Long.valueOf(this.Oj));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LocationStatus[cell status: ").append(by(this.Oh));
        stringBuilder.append(", wifi status: ").append(by(this.Oi));
        stringBuilder.append(", elapsed realtime ns: ").append(this.Oj);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        C0429c.m1221a(this, parcel, flags);
    }
}
