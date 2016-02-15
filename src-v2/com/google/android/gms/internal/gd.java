/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.gc;
import com.google.android.gms.internal.ge;
import com.google.android.gms.internal.gf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class gd
implements SafeParcelable {
    public static final ge CREATOR = new ge();
    private final HashMap<String, HashMap<String, ga.a<?, ?>>> Ei;
    private final ArrayList<a> Ej;
    private final String Ek;
    private final int xH;

    gd(int n2, ArrayList<a> arrayList, String string2) {
        this.xH = n2;
        this.Ej = null;
        this.Ei = gd.b(arrayList);
        this.Ek = fq.f(string2);
        this.fl();
    }

    public gd(Class<? extends ga> class_) {
        this.xH = 1;
        this.Ej = null;
        this.Ei = new HashMap();
        this.Ek = class_.getCanonicalName();
    }

    private static HashMap<String, HashMap<String, ga.a<?, ?>>> b(ArrayList<a> arrayList) {
        HashMap hashMap = new HashMap();
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            a a2 = arrayList.get(i2);
            hashMap.put(a2.className, a2.fp());
        }
        return hashMap;
    }

    public void a(Class<? extends ga> class_, HashMap<String, ga.a<?, ?>> hashMap) {
        this.Ei.put(class_.getCanonicalName(), hashMap);
    }

    public HashMap<String, ga.a<?, ?>> au(String string2) {
        return this.Ei.get(string2);
    }

    public boolean b(Class<? extends ga> class_) {
        return this.Ei.containsKey(class_.getCanonicalName());
    }

    public int describeContents() {
        ge ge2 = CREATOR;
        return 0;
    }

    public void fl() {
        Iterator<String> iterator = this.Ei.keySet().iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            object = this.Ei.get(object);
            Iterator iterator2 = object.keySet().iterator();
            while (iterator2.hasNext()) {
                ((ga.a)object.get((String)iterator2.next())).a(this);
            }
        }
    }

    public void fm() {
        for (String string2 : this.Ei.keySet()) {
            HashMap hashMap = this.Ei.get(string2);
            HashMap hashMap2 = new HashMap();
            for (String string3 : hashMap.keySet()) {
                hashMap2.put(string3, hashMap.get(string3).fb());
            }
            this.Ei.put(string2, hashMap2);
        }
    }

    ArrayList<a> fn() {
        ArrayList<a> arrayList = new ArrayList<a>();
        for (String string2 : this.Ei.keySet()) {
            arrayList.add(new a(string2, this.Ei.get(string2)));
        }
        return arrayList;
    }

    public String fo() {
        return this.Ek;
    }

    int getVersionCode() {
        return this.xH;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator = this.Ei.keySet().iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            stringBuilder.append((String)object).append(":\n");
            object = this.Ei.get(object);
            for (String string2 : object.keySet()) {
                stringBuilder.append("  ").append(string2).append(": ");
                stringBuilder.append(object.get(string2));
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ge ge2 = CREATOR;
        ge.a(this, parcel, n2);
    }

    public static class a
    implements SafeParcelable {
        public static final gf CREATOR = new gf();
        final ArrayList<b> El;
        final String className;
        final int versionCode;

        a(int n2, String string2, ArrayList<b> arrayList) {
            this.versionCode = n2;
            this.className = string2;
            this.El = arrayList;
        }

        a(String string2, HashMap<String, ga.a<?, ?>> hashMap) {
            this.versionCode = 1;
            this.className = string2;
            this.El = a.b(hashMap);
        }

        private static ArrayList<b> b(HashMap<String, ga.a<?, ?>> hashMap) {
            if (hashMap == null) {
                return null;
            }
            ArrayList<b> arrayList = new ArrayList<b>();
            for (String string2 : hashMap.keySet()) {
                arrayList.add(new b(string2, hashMap.get(string2)));
            }
            return arrayList;
        }

        public int describeContents() {
            gf gf2 = CREATOR;
            return 0;
        }

        HashMap<String, ga.a<?, ?>> fp() {
            HashMap hashMap = new HashMap();
            int n2 = this.El.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                b b2 = this.El.get(i2);
                hashMap.put(b2.eM, b2.Em);
            }
            return hashMap;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            gf gf2 = CREATOR;
            gf.a(this, parcel, n2);
        }
    }

    public static class b
    implements SafeParcelable {
        public static final gc CREATOR = new gc();
        final ga.a<?, ?> Em;
        final String eM;
        final int versionCode;

        b(int n2, String string2, ga.a<?, ?> a2) {
            this.versionCode = n2;
            this.eM = string2;
            this.Em = a2;
        }

        b(String string2, ga.a<?, ?> a2) {
            this.versionCode = 1;
            this.eM = string2;
            this.Em = a2;
        }

        public int describeContents() {
            gc gc2 = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            gc gc2 = CREATOR;
            gc.a(this, parcel, n2);
        }
    }

}

