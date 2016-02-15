package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import android.util.Log;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.internal.C1315y;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;

public class DriveId implements SafeParcelable {
    public static final Creator<DriveId> CREATOR;
    final String EH;
    final long EI;
    final long EJ;
    private volatile String EK;
    final int xH;

    static {
        CREATOR = new C0267d();
    }

    DriveId(int versionCode, String resourceId, long sqlId, long databaseInstanceId) {
        boolean z = false;
        this.EK = null;
        this.xH = versionCode;
        this.EH = resourceId;
        fq.m987z(!UnsupportedUrlFragment.DISPLAY_NAME.equals(resourceId));
        if (!(resourceId == null && sqlId == -1)) {
            z = true;
        }
        fq.m987z(z);
        this.EI = sqlId;
        this.EJ = databaseInstanceId;
    }

    public DriveId(String resourceId, long sqlId, long databaseInstanceId) {
        this(1, resourceId, sqlId, databaseInstanceId);
    }

    public static DriveId aw(String str) {
        fq.m985f(str);
        return new DriveId(str, -1, -1);
    }

    public static DriveId decodeFromString(String s) {
        fq.m984b(s.startsWith("DriveId:"), "Invalid DriveId: " + s);
        return m1702f(Base64.decode(s.substring("DriveId:".length()), 10));
    }

    static DriveId m1702f(byte[] bArr) {
        try {
            C1315y g = C1315y.m2671g(bArr);
            return new DriveId(g.versionCode, UnsupportedUrlFragment.DISPLAY_NAME.equals(g.FC) ? null : g.FC, g.FD, g.FE);
        } catch (ks e) {
            throw new IllegalArgumentException();
        }
    }

    public int describeContents() {
        return 0;
    }

    public final String encodeToString() {
        if (this.EK == null) {
            this.EK = "DriveId:" + Base64.encodeToString(fC(), 10);
        }
        return this.EK;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DriveId)) {
            return false;
        }
        DriveId driveId = (DriveId) obj;
        if (driveId.EJ != this.EJ) {
            Log.w("DriveId", "Attempt to compare invalid DriveId detected. Has local storage been cleared?");
            return false;
        } else if (driveId.EI == -1 && this.EI == -1) {
            return driveId.EH.equals(this.EH);
        } else {
            return driveId.EI == this.EI;
        }
    }

    final byte[] fC() {
        kt c1315y = new C1315y();
        c1315y.versionCode = this.xH;
        c1315y.FC = this.EH == null ? UnsupportedUrlFragment.DISPLAY_NAME : this.EH;
        c1315y.FD = this.EI;
        c1315y.FE = this.EJ;
        return kt.m1169d(c1315y);
    }

    public String getResourceId() {
        return this.EH;
    }

    public int hashCode() {
        return this.EI == -1 ? this.EH.hashCode() : (String.valueOf(this.EJ) + String.valueOf(this.EI)).hashCode();
    }

    public String toString() {
        return encodeToString();
    }

    public void writeToParcel(Parcel out, int flags) {
        C0267d.m240a(this, out, flags);
    }
}
