/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.hg;
import com.google.android.gms.internal.hl;
import java.util.concurrent.TimeUnit;

public final class hk
implements SafeParcelable {
    public static final hl CREATOR = new hl();
    static final long OF = TimeUnit.HOURS.toMillis(1);
    private final hg OG;
    private final long Oc;
    private final int mPriority;
    final int xH;

    public hk(int n2, hg hg2, long l2, int n3) {
        this.xH = n2;
        this.OG = hg2;
        this.Oc = l2;
        this.mPriority = n3;
    }

    public int describeContents() {
        hl hl2 = CREATOR;
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof hk)) {
            return false;
        }
        object = (hk)object;
        if (!this.OG.equals(object.OG)) return false;
        if (this.Oc != object.Oc) return false;
        if (this.mPriority == object.mPriority) return true;
        return false;
    }

    public long getInterval() {
        return this.Oc;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public hg hZ() {
        return this.OG;
    }

    public int hashCode() {
        return fo.hashCode(this.OG, this.Oc, this.mPriority);
    }

    public String toString() {
        return fo.e(this).a("filter", this.OG).a("interval", this.Oc).a("priority", this.mPriority).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        hl hl2 = CREATOR;
        hl.a(this, parcel, n2);
    }
}

