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
import com.google.android.gms.internal.ht;
import java.util.Locale;

public class hs
implements SafeParcelable {
    public static final ht CREATOR = new ht();
    public final String Rl;
    public final String Rm;
    public final int versionCode;

    public hs(int n2, String string2, String string3) {
        this.versionCode = n2;
        this.Rl = string2;
        this.Rm = string3;
    }

    public hs(String string2, Locale locale) {
        this.versionCode = 0;
        this.Rl = string2;
        this.Rm = locale.toString();
    }

    public int describeContents() {
        ht ht2 = CREATOR;
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
        if (object == null) return false;
        if (!(object instanceof hs)) {
            return false;
        }
        object = (hs)object;
        if (!this.Rm.equals(object.Rm)) return false;
        if (this.Rl.equals(object.Rl)) return true;
        return false;
    }

    public int hashCode() {
        return fo.hashCode(this.Rl, this.Rm);
    }

    public String toString() {
        return fo.e(this).a("clientPackageName", this.Rl).a("locale", this.Rm).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ht ht2 = CREATOR;
        ht.a(this, parcel, n2);
    }
}

