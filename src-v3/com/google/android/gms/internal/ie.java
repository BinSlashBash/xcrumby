package com.google.android.gms.internal;

import android.os.Parcel;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ga.C0900a;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class ie extends ga implements SafeParcelable, Moment {
    public static final C0404if CREATOR;
    private static final HashMap<String, C0900a<?, ?>> UI;
    private String Rd;
    private final Set<Integer> UJ;
    private ic VE;
    private ic VF;
    private String Vw;
    private String wp;
    private final int xH;

    static {
        CREATOR = new C0404if();
        UI = new HashMap();
        UI.put("id", C0900a.m2230j("id", 2));
        UI.put("result", C0900a.m2224a("result", 4, ic.class));
        UI.put("startDate", C0900a.m2230j("startDate", 5));
        UI.put("target", C0900a.m2224a("target", 6, ic.class));
        UI.put("type", C0900a.m2230j("type", 7));
    }

    public ie() {
        this.xH = 1;
        this.UJ = new HashSet();
    }

    ie(Set<Integer> set, int i, String str, ic icVar, String str2, ic icVar2, String str3) {
        this.UJ = set;
        this.xH = i;
        this.wp = str;
        this.VE = icVar;
        this.Vw = str2;
        this.VF = icVar2;
        this.Rd = str3;
    }

    public ie(Set<Integer> set, String str, ic icVar, String str2, ic icVar2, String str3) {
        this.UJ = set;
        this.xH = 1;
        this.wp = str;
        this.VE = icVar;
        this.Vw = str2;
        this.VF = icVar2;
        this.Rd = str3;
    }

    protected boolean m3092a(C0900a c0900a) {
        return this.UJ.contains(Integer.valueOf(c0900a.ff()));
    }

    protected Object aq(String str) {
        return null;
    }

    protected boolean ar(String str) {
        return false;
    }

    protected Object m3093b(C0900a c0900a) {
        switch (c0900a.ff()) {
            case Std.STD_URL /*2*/:
                return this.wp;
            case Std.STD_CLASS /*4*/:
                return this.VE;
            case Std.STD_JAVA_TYPE /*5*/:
                return this.Vw;
            case Std.STD_CURRENCY /*6*/:
                return this.VF;
            case Std.STD_PATTERN /*7*/:
                return this.Rd;
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + c0900a.ff());
        }
    }

    public int describeContents() {
        C0404if c0404if = CREATOR;
        return 0;
    }

    public HashMap<String, C0900a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ie)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ie ieVar = (ie) obj;
        for (C0900a c0900a : UI.values()) {
            if (m3092a(c0900a)) {
                if (!ieVar.m3092a(c0900a)) {
                    return false;
                }
                if (!m3093b(c0900a).equals(ieVar.m3093b(c0900a))) {
                    return false;
                }
            } else if (ieVar.m3092a(c0900a)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return jt();
    }

    public String getId() {
        return this.wp;
    }

    public ItemScope getResult() {
        return this.VE;
    }

    public String getStartDate() {
        return this.Vw;
    }

    public ItemScope getTarget() {
        return this.VF;
    }

    public String getType() {
        return this.Rd;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasId() {
        return this.UJ.contains(Integer.valueOf(2));
    }

    public boolean hasResult() {
        return this.UJ.contains(Integer.valueOf(4));
    }

    public boolean hasStartDate() {
        return this.UJ.contains(Integer.valueOf(5));
    }

    public boolean hasTarget() {
        return this.UJ.contains(Integer.valueOf(6));
    }

    public boolean hasType() {
        return this.UJ.contains(Integer.valueOf(7));
    }

    public int hashCode() {
        int i = 0;
        for (C0900a c0900a : UI.values()) {
            int hashCode;
            if (m3092a(c0900a)) {
                hashCode = m3093b(c0900a).hashCode() + (i + c0900a.ff());
            } else {
                hashCode = i;
            }
            i = hashCode;
        }
        return i;
    }

    public boolean isDataValid() {
        return true;
    }

    Set<Integer> ja() {
        return this.UJ;
    }

    ic jr() {
        return this.VE;
    }

    ic js() {
        return this.VF;
    }

    public ie jt() {
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0404if c0404if = CREATOR;
        C0404if.m1078a(this, out, flags);
    }
}
