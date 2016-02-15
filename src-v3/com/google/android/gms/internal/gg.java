package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ga.C0900a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import org.json.zip.JSONzip;

public class gg extends ga implements SafeParcelable {
    public static final gh CREATOR;
    private final gd Eg;
    private final Parcel En;
    private final int Eo;
    private int Ep;
    private int Eq;
    private final String mClassName;
    private final int xH;

    static {
        CREATOR = new gh();
    }

    gg(int i, Parcel parcel, gd gdVar) {
        this.xH = i;
        this.En = (Parcel) fq.m985f(parcel);
        this.Eo = 2;
        this.Eg = gdVar;
        if (this.Eg == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.Eg.fo();
        }
        this.Ep = 2;
    }

    private gg(SafeParcelable safeParcelable, gd gdVar, String str) {
        this.xH = 1;
        this.En = Parcel.obtain();
        safeParcelable.writeToParcel(this.En, 0);
        this.Eo = 1;
        this.Eg = (gd) fq.m985f(gdVar);
        this.mClassName = (String) fq.m985f(str);
        this.Ep = 2;
    }

    public static <T extends ga & SafeParcelable> gg m2238a(T t) {
        String canonicalName = t.getClass().getCanonicalName();
        return new gg((SafeParcelable) t, m2244b(t), canonicalName);
    }

    private static void m2239a(gd gdVar, ga gaVar) {
        Class cls = gaVar.getClass();
        if (!gdVar.m2237b(cls)) {
            HashMap eY = gaVar.eY();
            gdVar.m2236a(cls, gaVar.eY());
            for (String str : eY.keySet()) {
                C0900a c0900a = (C0900a) eY.get(str);
                Class fg = c0900a.fg();
                if (fg != null) {
                    try {
                        m2239a(gdVar, (ga) fg.newInstance());
                    } catch (Throwable e) {
                        throw new IllegalStateException("Could not instantiate an object of type " + c0900a.fg().getCanonicalName(), e);
                    } catch (Throwable e2) {
                        throw new IllegalStateException("Could not access object of type " + c0900a.fg().getCanonicalName(), e2);
                    }
                }
            }
        }
    }

    private void m2240a(StringBuilder stringBuilder, int i, Object obj) {
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
            case Std.STD_FILE /*1*/:
            case Std.STD_URL /*2*/:
            case Std.STD_URI /*3*/:
            case Std.STD_CLASS /*4*/:
            case Std.STD_JAVA_TYPE /*5*/:
            case Std.STD_CURRENCY /*6*/:
                stringBuilder.append(obj);
            case Std.STD_PATTERN /*7*/:
                stringBuilder.append("\"").append(gp.av(obj.toString())).append("\"");
            case Std.STD_LOCALE /*8*/:
                stringBuilder.append("\"").append(gj.m1032d((byte[]) obj)).append("\"");
            case Std.STD_CHARSET /*9*/:
                stringBuilder.append("\"").append(gj.m1033e((byte[]) obj));
                stringBuilder.append("\"");
            case Std.STD_TIME_ZONE /*10*/:
                gq.m1037a(stringBuilder, (HashMap) obj);
            case Std.STD_INET_ADDRESS /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + i);
        }
    }

    private void m2241a(StringBuilder stringBuilder, C0900a<?, ?> c0900a, Parcel parcel, int i) {
        switch (c0900a.eX()) {
            case JSONzip.zipEmptyObject /*0*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, Integer.valueOf(C0261a.m187g(parcel, i))));
            case Std.STD_FILE /*1*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, C0261a.m190j(parcel, i)));
            case Std.STD_URL /*2*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, Long.valueOf(C0261a.m189i(parcel, i))));
            case Std.STD_URI /*3*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, Float.valueOf(C0261a.m191k(parcel, i))));
            case Std.STD_CLASS /*4*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, Double.valueOf(C0261a.m192l(parcel, i))));
            case Std.STD_JAVA_TYPE /*5*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, C0261a.m193m(parcel, i)));
            case Std.STD_CURRENCY /*6*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, Boolean.valueOf(C0261a.m183c(parcel, i))));
            case Std.STD_PATTERN /*7*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, C0261a.m195n(parcel, i)));
            case Std.STD_LOCALE /*8*/:
            case Std.STD_CHARSET /*9*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, C0261a.m199q(parcel, i)));
            case Std.STD_TIME_ZONE /*10*/:
                m2246b(stringBuilder, (C0900a) c0900a, m1007a(c0900a, m2248c(C0261a.m198p(parcel, i))));
            case Std.STD_INET_ADDRESS /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown field out type = " + c0900a.eX());
        }
    }

    private void m2242a(StringBuilder stringBuilder, String str, C0900a<?, ?> c0900a, Parcel parcel, int i) {
        stringBuilder.append("\"").append(str).append("\":");
        if (c0900a.fi()) {
            m2241a(stringBuilder, c0900a, parcel, i);
        } else {
            m2245b(stringBuilder, c0900a, parcel, i);
        }
    }

    private void m2243a(StringBuilder stringBuilder, HashMap<String, C0900a<?, ?>> hashMap, Parcel parcel) {
        HashMap c = m2249c((HashMap) hashMap);
        stringBuilder.append('{');
        int o = C0261a.m196o(parcel);
        Object obj = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            Entry entry = (Entry) c.get(Integer.valueOf(C0261a.m174R(n)));
            if (entry != null) {
                if (obj != null) {
                    stringBuilder.append(",");
                }
                m2242a(stringBuilder, (String) entry.getKey(), (C0900a) entry.getValue(), parcel, n);
                obj = 1;
            }
        }
        if (parcel.dataPosition() != o) {
            throw new C0260a("Overread allowed size end=" + o, parcel);
        }
        stringBuilder.append('}');
    }

    private static gd m2244b(ga gaVar) {
        gd gdVar = new gd(gaVar.getClass());
        m2239a(gdVar, gaVar);
        gdVar.fm();
        gdVar.fl();
        return gdVar;
    }

    private void m2245b(StringBuilder stringBuilder, C0900a<?, ?> c0900a, Parcel parcel, int i) {
        if (c0900a.fd()) {
            stringBuilder.append("[");
            switch (c0900a.eX()) {
                case JSONzip.zipEmptyObject /*0*/:
                    gi.m1027a(stringBuilder, C0261a.m202t(parcel, i));
                    break;
                case Std.STD_FILE /*1*/:
                    gi.m1029a(stringBuilder, C0261a.m204v(parcel, i));
                    break;
                case Std.STD_URL /*2*/:
                    gi.m1028a(stringBuilder, C0261a.m203u(parcel, i));
                    break;
                case Std.STD_URI /*3*/:
                    gi.m1026a(stringBuilder, C0261a.m205w(parcel, i));
                    break;
                case Std.STD_CLASS /*4*/:
                    gi.m1025a(stringBuilder, C0261a.m206x(parcel, i));
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    gi.m1029a(stringBuilder, C0261a.m207y(parcel, i));
                    break;
                case Std.STD_CURRENCY /*6*/:
                    gi.m1031a(stringBuilder, C0261a.m201s(parcel, i));
                    break;
                case Std.STD_PATTERN /*7*/:
                    gi.m1030a(stringBuilder, C0261a.m208z(parcel, i));
                    break;
                case Std.STD_LOCALE /*8*/:
                case Std.STD_CHARSET /*9*/:
                case Std.STD_TIME_ZONE /*10*/:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case Std.STD_INET_ADDRESS /*11*/:
                    Parcel[] C = C0261a.m173C(parcel, i);
                    int length = C.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (i2 > 0) {
                            stringBuilder.append(",");
                        }
                        C[i2].setDataPosition(0);
                        m2243a(stringBuilder, c0900a.fk(), C[i2]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            stringBuilder.append("]");
            return;
        }
        switch (c0900a.eX()) {
            case JSONzip.zipEmptyObject /*0*/:
                stringBuilder.append(C0261a.m187g(parcel, i));
            case Std.STD_FILE /*1*/:
                stringBuilder.append(C0261a.m190j(parcel, i));
            case Std.STD_URL /*2*/:
                stringBuilder.append(C0261a.m189i(parcel, i));
            case Std.STD_URI /*3*/:
                stringBuilder.append(C0261a.m191k(parcel, i));
            case Std.STD_CLASS /*4*/:
                stringBuilder.append(C0261a.m192l(parcel, i));
            case Std.STD_JAVA_TYPE /*5*/:
                stringBuilder.append(C0261a.m193m(parcel, i));
            case Std.STD_CURRENCY /*6*/:
                stringBuilder.append(C0261a.m183c(parcel, i));
            case Std.STD_PATTERN /*7*/:
                stringBuilder.append("\"").append(gp.av(C0261a.m195n(parcel, i))).append("\"");
            case Std.STD_LOCALE /*8*/:
                stringBuilder.append("\"").append(gj.m1032d(C0261a.m199q(parcel, i))).append("\"");
            case Std.STD_CHARSET /*9*/:
                stringBuilder.append("\"").append(gj.m1033e(C0261a.m199q(parcel, i)));
                stringBuilder.append("\"");
            case Std.STD_TIME_ZONE /*10*/:
                Bundle p = C0261a.m198p(parcel, i);
                Set<String> keySet = p.keySet();
                keySet.size();
                stringBuilder.append("{");
                int i3 = 1;
                for (String str : keySet) {
                    if (i3 == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(str).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(gp.av(p.getString(str))).append("\"");
                    i3 = 0;
                }
                stringBuilder.append("}");
            case Std.STD_INET_ADDRESS /*11*/:
                Parcel B = C0261a.m172B(parcel, i);
                B.setDataPosition(0);
                m2243a(stringBuilder, c0900a.fk(), B);
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private void m2246b(StringBuilder stringBuilder, C0900a<?, ?> c0900a, Object obj) {
        if (c0900a.fc()) {
            m2247b(stringBuilder, (C0900a) c0900a, (ArrayList) obj);
        } else {
            m2240a(stringBuilder, c0900a.eW(), obj);
        }
    }

    private void m2247b(StringBuilder stringBuilder, C0900a<?, ?> c0900a, ArrayList<?> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            m2240a(stringBuilder, c0900a.eW(), arrayList.get(i));
        }
        stringBuilder.append("]");
    }

    public static HashMap<String, String> m2248c(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    private static HashMap<Integer, Entry<String, C0900a<?, ?>>> m2249c(HashMap<String, C0900a<?, ?>> hashMap) {
        HashMap<Integer, Entry<String, C0900a<?, ?>>> hashMap2 = new HashMap();
        for (Entry entry : hashMap.entrySet()) {
            hashMap2.put(Integer.valueOf(((C0900a) entry.getValue()).ff()), entry);
        }
        return hashMap2;
    }

    protected Object aq(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    protected boolean ar(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public int describeContents() {
        gh ghVar = CREATOR;
        return 0;
    }

    public HashMap<String, C0900a<?, ?>> eY() {
        return this.Eg == null ? null : this.Eg.au(this.mClassName);
    }

    public Parcel fq() {
        switch (this.Ep) {
            case JSONzip.zipEmptyObject /*0*/:
                this.Eq = C0262b.m236p(this.En);
                C0262b.m211F(this.En, this.Eq);
                this.Ep = 2;
                break;
            case Std.STD_FILE /*1*/:
                C0262b.m211F(this.En, this.Eq);
                this.Ep = 2;
                break;
        }
        return this.En;
    }

    gd fr() {
        switch (this.Eo) {
            case JSONzip.zipEmptyObject /*0*/:
                return null;
            case Std.STD_FILE /*1*/:
                return this.Eg;
            case Std.STD_URL /*2*/:
                return this.Eg;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.Eo);
        }
    }

    public int getVersionCode() {
        return this.xH;
    }

    public String toString() {
        fq.m982b(this.Eg, (Object) "Cannot convert to JSON on client side.");
        Parcel fq = fq();
        fq.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        m2243a(stringBuilder, this.Eg.au(this.mClassName), fq);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        gh ghVar = CREATOR;
        gh.m1022a(this, out, flags);
    }
}
