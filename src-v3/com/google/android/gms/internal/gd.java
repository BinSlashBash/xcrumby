package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ga.C0900a;
import java.util.ArrayList;
import java.util.HashMap;

public class gd implements SafeParcelable {
    public static final ge CREATOR;
    private final HashMap<String, HashMap<String, C0900a<?, ?>>> Ei;
    private final ArrayList<C0901a> Ej;
    private final String Ek;
    private final int xH;

    /* renamed from: com.google.android.gms.internal.gd.a */
    public static class C0901a implements SafeParcelable {
        public static final gf CREATOR;
        final ArrayList<C0902b> El;
        final String className;
        final int versionCode;

        static {
            CREATOR = new gf();
        }

        C0901a(int i, String str, ArrayList<C0902b> arrayList) {
            this.versionCode = i;
            this.className = str;
            this.El = arrayList;
        }

        C0901a(String str, HashMap<String, C0900a<?, ?>> hashMap) {
            this.versionCode = 1;
            this.className = str;
            this.El = C0901a.m2234b(hashMap);
        }

        private static ArrayList<C0902b> m2234b(HashMap<String, C0900a<?, ?>> hashMap) {
            if (hashMap == null) {
                return null;
            }
            ArrayList<C0902b> arrayList = new ArrayList();
            for (String str : hashMap.keySet()) {
                arrayList.add(new C0902b(str, (C0900a) hashMap.get(str)));
            }
            return arrayList;
        }

        public int describeContents() {
            gf gfVar = CREATOR;
            return 0;
        }

        HashMap<String, C0900a<?, ?>> fp() {
            HashMap<String, C0900a<?, ?>> hashMap = new HashMap();
            int size = this.El.size();
            for (int i = 0; i < size; i++) {
                C0902b c0902b = (C0902b) this.El.get(i);
                hashMap.put(c0902b.eM, c0902b.Em);
            }
            return hashMap;
        }

        public void writeToParcel(Parcel out, int flags) {
            gf gfVar = CREATOR;
            gf.m1019a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.gd.b */
    public static class C0902b implements SafeParcelable {
        public static final gc CREATOR;
        final C0900a<?, ?> Em;
        final String eM;
        final int versionCode;

        static {
            CREATOR = new gc();
        }

        C0902b(int i, String str, C0900a<?, ?> c0900a) {
            this.versionCode = i;
            this.eM = str;
            this.Em = c0900a;
        }

        C0902b(String str, C0900a<?, ?> c0900a) {
            this.versionCode = 1;
            this.eM = str;
            this.Em = c0900a;
        }

        public int describeContents() {
            gc gcVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            gc gcVar = CREATOR;
            gc.m1013a(this, out, flags);
        }
    }

    static {
        CREATOR = new ge();
    }

    gd(int i, ArrayList<C0901a> arrayList, String str) {
        this.xH = i;
        this.Ej = null;
        this.Ei = m2235b((ArrayList) arrayList);
        this.Ek = (String) fq.m985f(str);
        fl();
    }

    public gd(Class<? extends ga> cls) {
        this.xH = 1;
        this.Ej = null;
        this.Ei = new HashMap();
        this.Ek = cls.getCanonicalName();
    }

    private static HashMap<String, HashMap<String, C0900a<?, ?>>> m2235b(ArrayList<C0901a> arrayList) {
        HashMap<String, HashMap<String, C0900a<?, ?>>> hashMap = new HashMap();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            C0901a c0901a = (C0901a) arrayList.get(i);
            hashMap.put(c0901a.className, c0901a.fp());
        }
        return hashMap;
    }

    public void m2236a(Class<? extends ga> cls, HashMap<String, C0900a<?, ?>> hashMap) {
        this.Ei.put(cls.getCanonicalName(), hashMap);
    }

    public HashMap<String, C0900a<?, ?>> au(String str) {
        return (HashMap) this.Ei.get(str);
    }

    public boolean m2237b(Class<? extends ga> cls) {
        return this.Ei.containsKey(cls.getCanonicalName());
    }

    public int describeContents() {
        ge geVar = CREATOR;
        return 0;
    }

    public void fl() {
        for (String str : this.Ei.keySet()) {
            HashMap hashMap = (HashMap) this.Ei.get(str);
            for (String str2 : hashMap.keySet()) {
                ((C0900a) hashMap.get(str2)).m2232a(this);
            }
        }
    }

    public void fm() {
        for (String str : this.Ei.keySet()) {
            HashMap hashMap = (HashMap) this.Ei.get(str);
            HashMap hashMap2 = new HashMap();
            for (String str2 : hashMap.keySet()) {
                hashMap2.put(str2, ((C0900a) hashMap.get(str2)).fb());
            }
            this.Ei.put(str, hashMap2);
        }
    }

    ArrayList<C0901a> fn() {
        ArrayList<C0901a> arrayList = new ArrayList();
        for (String str : this.Ei.keySet()) {
            arrayList.add(new C0901a(str, (HashMap) this.Ei.get(str)));
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
        for (String str : this.Ei.keySet()) {
            stringBuilder.append(str).append(":\n");
            HashMap hashMap = (HashMap) this.Ei.get(str);
            for (String str2 : hashMap.keySet()) {
                stringBuilder.append("  ").append(str2).append(": ");
                stringBuilder.append(hashMap.get(str2));
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        ge geVar = CREATOR;
        ge.m1016a(this, out, flags);
    }
}
