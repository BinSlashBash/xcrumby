package com.google.android.gms.internal;

import android.os.Parcel;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ga {

    /* renamed from: com.google.android.gms.internal.ga.b */
    public interface C0400b<I, O> {
        int eW();

        int eX();

        I m1004g(O o);
    }

    /* renamed from: com.google.android.gms.internal.ga.a */
    public static class C0900a<I, O> implements SafeParcelable {
        public static final gb CREATOR;
        protected final int DY;
        protected final boolean DZ;
        protected final int Ea;
        protected final boolean Eb;
        protected final String Ec;
        protected final int Ed;
        protected final Class<? extends ga> Ee;
        protected final String Ef;
        private gd Eg;
        private C0400b<I, O> Eh;
        private final int xH;

        static {
            CREATOR = new gb();
        }

        C0900a(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, fv fvVar) {
            this.xH = i;
            this.DY = i2;
            this.DZ = z;
            this.Ea = i3;
            this.Eb = z2;
            this.Ec = str;
            this.Ed = i4;
            if (str2 == null) {
                this.Ee = null;
                this.Ef = null;
            } else {
                this.Ee = gg.class;
                this.Ef = str2;
            }
            if (fvVar == null) {
                this.Eh = null;
            } else {
                this.Eh = fvVar.eU();
            }
        }

        protected C0900a(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends ga> cls, C0400b<I, O> c0400b) {
            this.xH = 1;
            this.DY = i;
            this.DZ = z;
            this.Ea = i2;
            this.Eb = z2;
            this.Ec = str;
            this.Ed = i3;
            this.Ee = cls;
            if (cls == null) {
                this.Ef = null;
            } else {
                this.Ef = cls.getCanonicalName();
            }
            this.Eh = c0400b;
        }

        public static C0900a m2223a(String str, int i, C0400b<?, ?> c0400b, boolean z) {
            return new C0900a(c0400b.eW(), z, c0400b.eX(), false, str, i, null, c0400b);
        }

        public static <T extends ga> C0900a<T, T> m2224a(String str, int i, Class<T> cls) {
            return new C0900a(11, false, 11, false, str, i, cls, null);
        }

        public static <T extends ga> C0900a<ArrayList<T>, ArrayList<T>> m2225b(String str, int i, Class<T> cls) {
            return new C0900a(11, true, 11, true, str, i, cls, null);
        }

        public static C0900a<Integer, Integer> m2227g(String str, int i) {
            return new C0900a(0, false, 0, false, str, i, null, null);
        }

        public static C0900a<Double, Double> m2228h(String str, int i) {
            return new C0900a(4, false, 4, false, str, i, null, null);
        }

        public static C0900a<Boolean, Boolean> m2229i(String str, int i) {
            return new C0900a(6, false, 6, false, str, i, null, null);
        }

        public static C0900a<String, String> m2230j(String str, int i) {
            return new C0900a(7, false, 7, false, str, i, null, null);
        }

        public static C0900a<ArrayList<String>, ArrayList<String>> m2231k(String str, int i) {
            return new C0900a(7, true, 7, true, str, i, null, null);
        }

        public void m2232a(gd gdVar) {
            this.Eg = gdVar;
        }

        public int describeContents() {
            gb gbVar = CREATOR;
            return 0;
        }

        public int eW() {
            return this.DY;
        }

        public int eX() {
            return this.Ea;
        }

        public C0900a<I, O> fb() {
            return new C0900a(this.xH, this.DY, this.DZ, this.Ea, this.Eb, this.Ec, this.Ed, this.Ef, fj());
        }

        public boolean fc() {
            return this.DZ;
        }

        public boolean fd() {
            return this.Eb;
        }

        public String fe() {
            return this.Ec;
        }

        public int ff() {
            return this.Ed;
        }

        public Class<? extends ga> fg() {
            return this.Ee;
        }

        String fh() {
            return this.Ef == null ? null : this.Ef;
        }

        public boolean fi() {
            return this.Eh != null;
        }

        fv fj() {
            return this.Eh == null ? null : fv.m2218a(this.Eh);
        }

        public HashMap<String, C0900a<?, ?>> fk() {
            fq.m985f(this.Ef);
            fq.m985f(this.Eg);
            return this.Eg.au(this.Ef);
        }

        public I m2233g(O o) {
            return this.Eh.m1004g(o);
        }

        public int getVersionCode() {
            return this.xH;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field\n");
            stringBuilder.append("            versionCode=").append(this.xH).append('\n');
            stringBuilder.append("                 typeIn=").append(this.DY).append('\n');
            stringBuilder.append("            typeInArray=").append(this.DZ).append('\n');
            stringBuilder.append("                typeOut=").append(this.Ea).append('\n');
            stringBuilder.append("           typeOutArray=").append(this.Eb).append('\n');
            stringBuilder.append("        outputFieldName=").append(this.Ec).append('\n');
            stringBuilder.append("      safeParcelFieldId=").append(this.Ed).append('\n');
            stringBuilder.append("       concreteTypeName=").append(fh()).append('\n');
            if (fg() != null) {
                stringBuilder.append("     concreteType.class=").append(fg().getCanonicalName()).append('\n');
            }
            stringBuilder.append("          converterName=").append(this.Eh == null ? "null" : this.Eh.getClass().getCanonicalName()).append('\n');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel out, int flags) {
            gb gbVar = CREATOR;
            gb.m1010a(this, out, flags);
        }
    }

    private void m1005a(StringBuilder stringBuilder, C0900a c0900a, Object obj) {
        if (c0900a.eW() == 11) {
            stringBuilder.append(((ga) c0900a.fg().cast(obj)).toString());
        } else if (c0900a.eW() == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(gp.av((String) obj));
            stringBuilder.append("\"");
        } else {
            stringBuilder.append(obj);
        }
    }

    private void m1006a(StringBuilder stringBuilder, C0900a c0900a, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                m1005a(stringBuilder, c0900a, obj);
            }
        }
        stringBuilder.append("]");
    }

    protected <O, I> I m1007a(C0900a<I, O> c0900a, Object obj) {
        return c0900a.Eh != null ? c0900a.m2233g(obj) : obj;
    }

    protected boolean m1008a(C0900a c0900a) {
        return c0900a.eX() == 11 ? c0900a.fd() ? at(c0900a.fe()) : as(c0900a.fe()) : ar(c0900a.fe());
    }

    protected abstract Object aq(String str);

    protected abstract boolean ar(String str);

    protected boolean as(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean at(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    protected Object m1009b(C0900a c0900a) {
        boolean z = true;
        String fe = c0900a.fe();
        if (c0900a.fg() == null) {
            return aq(c0900a.fe());
        }
        if (aq(c0900a.fe()) != null) {
            z = false;
        }
        fq.m980a(z, "Concrete field shouldn't be value object: " + c0900a.fe());
        Map fa = c0900a.fd() ? fa() : eZ();
        if (fa != null) {
            return fa.get(fe);
        }
        try {
            return getClass().getMethod("get" + Character.toUpperCase(fe.charAt(0)) + fe.substring(1), new Class[0]).invoke(this, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public abstract HashMap<String, C0900a<?, ?>> eY();

    public HashMap<String, Object> eZ() {
        return null;
    }

    public HashMap<String, Object> fa() {
        return null;
    }

    public String toString() {
        HashMap eY = eY();
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String str : eY.keySet()) {
            C0900a c0900a = (C0900a) eY.get(str);
            if (m1008a(c0900a)) {
                Object a = m1007a(c0900a, m1009b(c0900a));
                if (stringBuilder.length() == 0) {
                    stringBuilder.append("{");
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append("\"").append(str).append("\":");
                if (a != null) {
                    switch (c0900a.eX()) {
                        case Std.STD_LOCALE /*8*/:
                            stringBuilder.append("\"").append(gj.m1032d((byte[]) a)).append("\"");
                            break;
                        case Std.STD_CHARSET /*9*/:
                            stringBuilder.append("\"").append(gj.m1033e((byte[]) a)).append("\"");
                            break;
                        case Std.STD_TIME_ZONE /*10*/:
                            gq.m1037a(stringBuilder, (HashMap) a);
                            break;
                        default:
                            if (!c0900a.fc()) {
                                m1005a(stringBuilder, c0900a, a);
                                break;
                            }
                            m1006a(stringBuilder, c0900a, (ArrayList) a);
                            break;
                    }
                }
                stringBuilder.append("null");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("}");
        } else {
            stringBuilder.append("{}");
        }
        return stringBuilder.toString();
    }
}
