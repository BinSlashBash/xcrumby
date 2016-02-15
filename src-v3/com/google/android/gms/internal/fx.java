package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ga.C0400b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class fx implements SafeParcelable, C0400b<String, Integer> {
    public static final fy CREATOR;
    private final HashMap<String, Integer> DT;
    private final HashMap<Integer, String> DU;
    private final ArrayList<C0899a> DV;
    private final int xH;

    /* renamed from: com.google.android.gms.internal.fx.a */
    public static final class C0899a implements SafeParcelable {
        public static final fz CREATOR;
        final String DW;
        final int DX;
        final int versionCode;

        static {
            CREATOR = new fz();
        }

        C0899a(int i, String str, int i2) {
            this.versionCode = i;
            this.DW = str;
            this.DX = i2;
        }

        C0899a(String str, int i) {
            this.versionCode = 1;
            this.DW = str;
            this.DX = i;
        }

        public int describeContents() {
            fz fzVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            fz fzVar = CREATOR;
            fz.m1001a(this, out, flags);
        }
    }

    static {
        CREATOR = new fy();
    }

    public fx() {
        this.xH = 1;
        this.DT = new HashMap();
        this.DU = new HashMap();
        this.DV = null;
    }

    fx(int i, ArrayList<C0899a> arrayList) {
        this.xH = i;
        this.DT = new HashMap();
        this.DU = new HashMap();
        this.DV = null;
        m2219a((ArrayList) arrayList);
    }

    private void m2219a(ArrayList<C0899a> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C0899a c0899a = (C0899a) it.next();
            m2221f(c0899a.DW, c0899a.DX);
        }
    }

    public String m2220a(Integer num) {
        String str = (String) this.DU.get(num);
        return (str == null && this.DT.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    public int describeContents() {
        fy fyVar = CREATOR;
        return 0;
    }

    ArrayList<C0899a> eV() {
        ArrayList<C0899a> arrayList = new ArrayList();
        for (String str : this.DT.keySet()) {
            arrayList.add(new C0899a(str, ((Integer) this.DT.get(str)).intValue()));
        }
        return arrayList;
    }

    public int eW() {
        return 7;
    }

    public int eX() {
        return 0;
    }

    public fx m2221f(String str, int i) {
        this.DT.put(str, Integer.valueOf(i));
        this.DU.put(Integer.valueOf(i), str);
        return this;
    }

    public /* synthetic */ Object m2222g(Object obj) {
        return m2220a((Integer) obj);
    }

    int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel out, int flags) {
        fy fyVar = CREATOR;
        fy.m998a(this, out, flags);
    }
}
