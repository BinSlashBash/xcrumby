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
import com.google.android.gms.internal.hh;
import com.google.android.gms.internal.hm;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class hg
implements SafeParcelable {
    public static final hh CREATOR = new hh();
    final List<hm> OA;
    private final String OB;
    private final boolean OC;
    private final Set<hm> OD;
    final int xH;

    /*
     * Enabled aggressive block sorting
     */
    hg(int n2, List<hm> list, String string2, boolean bl2) {
        this.xH = n2;
        list = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.OA = list;
        list = string2;
        if (string2 == null) {
            list = "";
        }
        this.OB = list;
        this.OC = bl2;
        if (this.OA.isEmpty()) {
            this.OD = Collections.emptySet();
            return;
        }
        this.OD = Collections.unmodifiableSet(new HashSet<hm>(this.OA));
    }

    public int describeContents() {
        hh hh2 = CREATOR;
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
        if (!(object instanceof hg)) {
            return false;
        }
        object = (hg)object;
        if (!this.OD.equals(object.OD)) return false;
        if (this.OC == object.OC) return true;
        return false;
    }

    @Deprecated
    public String hW() {
        return this.OB;
    }

    public boolean hX() {
        return this.OC;
    }

    public int hashCode() {
        return fo.hashCode(this.OD, this.OC);
    }

    public String toString() {
        return fo.e(this).a("types", this.OD).a("requireOpenNow", this.OC).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        hh hh2 = CREATOR;
        hh.a(this, parcel, n2);
    }
}

