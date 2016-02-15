/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.d;
import com.google.android.gms.drive.internal.y;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;

public class DriveId
implements SafeParcelable {
    public static final Parcelable.Creator<DriveId> CREATOR = new d();
    final String EH;
    final long EI;
    final long EJ;
    private volatile String EK;
    final int xH;

    /*
     * Enabled aggressive block sorting
     */
    DriveId(int n2, String string2, long l2, long l3) {
        boolean bl2;
        block2 : {
            boolean bl3 = false;
            this.EK = null;
            this.xH = n2;
            this.EH = string2;
            bl2 = !"".equals(string2);
            fq.z(bl2);
            if (string2 == null) {
                bl2 = bl3;
                if (l2 == -1) break block2;
            }
            bl2 = true;
        }
        fq.z(bl2);
        this.EI = l2;
        this.EJ = l3;
    }

    public DriveId(String string2, long l2, long l3) {
        this(1, string2, l2, l3);
    }

    public static DriveId aw(String string2) {
        fq.f(string2);
        return new DriveId(string2, -1, -1);
    }

    public static DriveId decodeFromString(String string2) {
        fq.b(string2.startsWith("DriveId:"), (Object)("Invalid DriveId: " + string2));
        return DriveId.f(Base64.decode((String)string2.substring("DriveId:".length()), (int)10));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static DriveId f(byte[] object) {
        try {
            y y2 = y.g((byte[])object);
            if ("".equals(y2.FC)) {
                object = null;
                return new DriveId(y2.versionCode, (String)object, y2.FD, y2.FE);
            }
        }
        catch (ks var0_1) {
            throw new IllegalArgumentException();
        }
        object = y2.FC;
        return new DriveId(y2.versionCode, (String)object, y2.FD, y2.FE);
    }

    public int describeContents() {
        return 0;
    }

    public final String encodeToString() {
        if (this.EK == null) {
            String string2 = Base64.encodeToString((byte[])this.fC(), (int)10);
            this.EK = "DriveId:" + string2;
        }
        return this.EK;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof DriveId)) {
            return false;
        }
        object = (DriveId)object;
        if (object.EJ != this.EJ) {
            Log.w((String)"DriveId", (String)"Attempt to compare invalid DriveId detected. Has local storage been cleared?");
            return false;
        }
        if (object.EI == -1 && this.EI == -1) {
            return object.EH.equals(this.EH);
        }
        if (object.EI != this.EI) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    final byte[] fC() {
        y y2 = new y();
        y2.versionCode = this.xH;
        String string2 = this.EH == null ? "" : this.EH;
        y2.FC = string2;
        y2.FD = this.EI;
        y2.FE = this.EJ;
        return kt.d(y2);
    }

    public String getResourceId() {
        return this.EH;
    }

    public int hashCode() {
        if (this.EI == -1) {
            return this.EH.hashCode();
        }
        return (String.valueOf(this.EJ) + String.valueOf(this.EI)).hashCode();
    }

    public String toString() {
        return this.encodeToString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        d.a(this, parcel, n2);
    }
}

