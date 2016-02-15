/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.az;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.cb;
import com.google.android.gms.internal.cd;
import com.google.android.gms.internal.cf;
import com.google.android.gms.internal.ci;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dz;
import com.google.android.gms.internal.u;

public final class ce
implements SafeParcelable {
    public static final cd CREATOR = new cd();
    public final dx kK;
    public final String nO;
    public final cb og;
    public final u oh;
    public final cf oi;
    public final dz oj;
    public final az ok;
    public final String ol;
    public final boolean om;
    public final String on;
    public final ci oo;
    public final int op;
    public final bc oq;
    public final String or;
    public final int orientation;
    public final int versionCode;

    ce(int n2, cb cb2, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, IBinder iBinder4, String string2, boolean bl2, String string3, IBinder iBinder5, int n3, int n4, String string4, dx dx2, IBinder iBinder6, String string5) {
        this.versionCode = n2;
        this.og = cb2;
        this.oh = (u)e.d(d.a.K(iBinder));
        this.oi = (cf)e.d(d.a.K(iBinder2));
        this.oj = (dz)((Object)e.d(d.a.K(iBinder3)));
        this.ok = (az)e.d(d.a.K(iBinder4));
        this.ol = string2;
        this.om = bl2;
        this.on = string3;
        this.oo = (ci)e.d(d.a.K(iBinder5));
        this.orientation = n3;
        this.op = n4;
        this.nO = string4;
        this.kK = dx2;
        this.oq = (bc)e.d(d.a.K(iBinder6));
        this.or = string5;
    }

    public ce(cb cb2, u u2, cf cf2, ci ci2, dx dx2) {
        this.versionCode = 3;
        this.og = cb2;
        this.oh = u2;
        this.oi = cf2;
        this.oj = null;
        this.ok = null;
        this.ol = null;
        this.om = false;
        this.on = null;
        this.oo = ci2;
        this.orientation = -1;
        this.op = 4;
        this.nO = null;
        this.kK = dx2;
        this.oq = null;
        this.or = null;
    }

    public ce(u u2, cf cf2, az az2, ci ci2, dz dz2, boolean bl2, int n2, String string2, dx dx2, bc bc2) {
        this.versionCode = 3;
        this.og = null;
        this.oh = u2;
        this.oi = cf2;
        this.oj = dz2;
        this.ok = az2;
        this.ol = null;
        this.om = bl2;
        this.on = null;
        this.oo = ci2;
        this.orientation = n2;
        this.op = 3;
        this.nO = string2;
        this.kK = dx2;
        this.oq = bc2;
        this.or = null;
    }

    public ce(u u2, cf cf2, az az2, ci ci2, dz dz2, boolean bl2, int n2, String string2, String string3, dx dx2, bc bc2) {
        this.versionCode = 3;
        this.og = null;
        this.oh = u2;
        this.oi = cf2;
        this.oj = dz2;
        this.ok = az2;
        this.ol = string3;
        this.om = bl2;
        this.on = string2;
        this.oo = ci2;
        this.orientation = n2;
        this.op = 3;
        this.nO = null;
        this.kK = dx2;
        this.oq = bc2;
        this.or = null;
    }

    public ce(u u2, cf cf2, ci ci2, dz dz2, int n2, dx dx2, String string2) {
        this.versionCode = 3;
        this.og = null;
        this.oh = u2;
        this.oi = cf2;
        this.oj = dz2;
        this.ok = null;
        this.ol = null;
        this.om = false;
        this.on = null;
        this.oo = ci2;
        this.orientation = n2;
        this.op = 1;
        this.nO = null;
        this.kK = dx2;
        this.oq = null;
        this.or = string2;
    }

    public ce(u u2, cf cf2, ci ci2, dz dz2, boolean bl2, int n2, dx dx2) {
        this.versionCode = 3;
        this.og = null;
        this.oh = u2;
        this.oi = cf2;
        this.oj = dz2;
        this.ok = null;
        this.ol = null;
        this.om = bl2;
        this.on = null;
        this.oo = ci2;
        this.orientation = n2;
        this.op = 2;
        this.nO = null;
        this.kK = dx2;
        this.oq = null;
        this.or = null;
    }

    public static ce a(Intent object) {
        try {
            object = object.getBundleExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
            object.setClassLoader(ce.class.getClassLoader());
            object = (ce)object.getParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
            return object;
        }
        catch (Exception var0_1) {
            return null;
        }
    }

    public static void a(Intent intent, ce ce2) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", (Parcelable)ce2);
        intent.putExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", bundle);
    }

    IBinder aO() {
        return e.h(this.oh).asBinder();
    }

    IBinder aP() {
        return e.h(this.oi).asBinder();
    }

    IBinder aQ() {
        return e.h(this.oj).asBinder();
    }

    IBinder aR() {
        return e.h(this.ok).asBinder();
    }

    IBinder aS() {
        return e.h(this.oq).asBinder();
    }

    IBinder aT() {
        return e.h(this.oo).asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        cd.a(this, parcel, n2);
    }
}

