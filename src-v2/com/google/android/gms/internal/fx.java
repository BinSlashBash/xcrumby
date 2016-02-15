/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fy;
import com.google.android.gms.internal.fz;
import com.google.android.gms.internal.ga;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class fx
implements SafeParcelable,
ga.b<String, Integer> {
    public static final fy CREATOR = new fy();
    private final HashMap<String, Integer> DT;
    private final HashMap<Integer, String> DU;
    private final ArrayList<a> DV;
    private final int xH;

    public fx() {
        this.xH = 1;
        this.DT = new HashMap();
        this.DU = new HashMap();
        this.DV = null;
    }

    fx(int n2, ArrayList<a> arrayList) {
        this.xH = n2;
        this.DT = new HashMap();
        this.DU = new HashMap();
        this.DV = null;
        this.a(arrayList);
    }

    private void a(ArrayList<a> object) {
        object = object.iterator();
        while (object.hasNext()) {
            a a2 = (a)object.next();
            this.f(a2.DW, a2.DX);
        }
    }

    public String a(Integer object) {
        String string2;
        object = string2 = this.DU.get(object);
        if (string2 == null) {
            object = string2;
            if (this.DT.containsKey("gms_unknown")) {
                object = "gms_unknown";
            }
        }
        return object;
    }

    public int describeContents() {
        fy fy2 = CREATOR;
        return 0;
    }

    ArrayList<a> eV() {
        ArrayList<a> arrayList = new ArrayList<a>();
        for (String string2 : this.DT.keySet()) {
            arrayList.add(new a(string2, this.DT.get(string2)));
        }
        return arrayList;
    }

    @Override
    public int eW() {
        return 7;
    }

    @Override
    public int eX() {
        return 0;
    }

    public fx f(String string2, int n2) {
        this.DT.put(string2, n2);
        this.DU.put(n2, string2);
        return this;
    }

    @Override
    public /* synthetic */ Object g(Object object) {
        return this.a((Integer)object);
    }

    int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        fy fy2 = CREATOR;
        fy.a(this, parcel, n2);
    }

    public static final class a
    implements SafeParcelable {
        public static final fz CREATOR = new fz();
        final String DW;
        final int DX;
        final int versionCode;

        a(int n2, String string2, int n3) {
            this.versionCode = n2;
            this.DW = string2;
            this.DX = n3;
        }

        a(String string2, int n2) {
            this.versionCode = 1;
            this.DW = string2;
            this.DX = n2;
        }

        public int describeContents() {
            fz fz2 = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            fz fz2 = CREATOR;
            fz.a(this, parcel, n2);
        }
    }

}

