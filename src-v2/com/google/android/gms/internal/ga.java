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
import com.google.android.gms.internal.fv;
import com.google.android.gms.internal.gb;
import com.google.android.gms.internal.gd;
import com.google.android.gms.internal.gg;
import com.google.android.gms.internal.gj;
import com.google.android.gms.internal.gp;
import com.google.android.gms.internal.gq;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public abstract class ga {
    private void a(StringBuilder stringBuilder, a a2, Object object) {
        if (a2.eW() == 11) {
            stringBuilder.append(a2.fg().cast(object).toString());
            return;
        }
        if (a2.eW() == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(gp.av((String)object));
            stringBuilder.append("\"");
            return;
        }
        stringBuilder.append(object);
    }

    private void a(StringBuilder stringBuilder, a a2, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Object object;
            if (i2 > 0) {
                stringBuilder.append(",");
            }
            if ((object = arrayList.get(i2)) == null) continue;
            this.a(stringBuilder, a2, object);
        }
        stringBuilder.append("]");
    }

    protected <O, I> I a(a<I, O> a2, Object object) {
        Object object2 = object;
        if (a2.Eh != null) {
            object2 = a2.g(object);
        }
        return (I)object2;
    }

    protected boolean a(a a2) {
        if (a2.eX() == 11) {
            if (a2.fd()) {
                return this.at(a2.fe());
            }
            return this.as(a2.fe());
        }
        return this.ar(a2.fe());
    }

    protected abstract Object aq(String var1);

    protected abstract boolean ar(String var1);

    protected boolean as(String string2) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean at(String string2) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object b(a hashMap) {
        boolean bl2 = true;
        String string2 = hashMap.fe();
        if (hashMap.fg() == null) {
            return this.aq(hashMap.fe());
        }
        if (this.aq(hashMap.fe()) != null) {
            bl2 = false;
        }
        fq.a(bl2, "Concrete field shouldn't be value object: " + hashMap.fe());
        hashMap = hashMap.fd() ? this.fa() : this.eZ();
        if (hashMap != null) {
            return hashMap.get(string2);
        }
        try {
            hashMap = "get" + Character.toUpperCase(string2.charAt(0)) + string2.substring(1);
            return this.getClass().getMethod((String)((Object)hashMap), new Class[0]).invoke(this, new Object[0]);
        }
        catch (Exception var1_2) {
            throw new RuntimeException(var1_2);
        }
    }

    public abstract HashMap<String, a<?, ?>> eY();

    public HashMap<String, Object> eZ() {
        return null;
    }

    public HashMap<String, Object> fa() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        HashMap<String, a<?, ?>> hashMap = this.eY();
        StringBuilder stringBuilder = new StringBuilder(100);
        block5 : for (String string2 : hashMap.keySet()) {
            a<?, ?> a2 = hashMap.get(string2);
            if (!this.a(a2)) continue;
            ? obj = this.a(a2, this.b(a2));
            if (stringBuilder.length() == 0) {
                stringBuilder.append("{");
            } else {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"").append(string2).append("\":");
            if (obj == null) {
                stringBuilder.append("null");
                continue;
            }
            switch (a2.eX()) {
                default: {
                    if (!a2.fc()) break;
                    this.a(stringBuilder, a2, (ArrayList)obj);
                    continue block5;
                }
                case 8: {
                    stringBuilder.append("\"").append(gj.d((byte[])obj)).append("\"");
                    continue block5;
                }
                case 9: {
                    stringBuilder.append("\"").append(gj.e((byte[])obj)).append("\"");
                    continue block5;
                }
                case 10: {
                    gq.a(stringBuilder, (HashMap)obj);
                    continue block5;
                }
            }
            this.a(stringBuilder, a2, obj);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        stringBuilder.append("{}");
        return stringBuilder.toString();
    }

    public static class a<I, O>
    implements SafeParcelable {
        public static final gb CREATOR = new gb();
        protected final int DY;
        protected final boolean DZ;
        protected final int Ea;
        protected final boolean Eb;
        protected final String Ec;
        protected final int Ed;
        protected final Class<? extends ga> Ee;
        protected final String Ef;
        private gd Eg;
        private b<I, O> Eh;
        private final int xH;

        /*
         * Enabled aggressive block sorting
         */
        a(int n2, int n3, boolean bl2, int n4, boolean bl3, String string2, int n5, String string3, fv fv2) {
            this.xH = n2;
            this.DY = n3;
            this.DZ = bl2;
            this.Ea = n4;
            this.Eb = bl3;
            this.Ec = string2;
            this.Ed = n5;
            if (string3 == null) {
                this.Ee = null;
                this.Ef = null;
            } else {
                this.Ee = gg.class;
                this.Ef = string3;
            }
            if (fv2 == null) {
                this.Eh = null;
                return;
            }
            this.Eh = fv2.eU();
        }

        /*
         * Enabled aggressive block sorting
         */
        protected a(int n2, boolean bl2, int n3, boolean bl3, String string2, int n4, Class<? extends ga> class_, b<I, O> b2) {
            this.xH = 1;
            this.DY = n2;
            this.DZ = bl2;
            this.Ea = n3;
            this.Eb = bl3;
            this.Ec = string2;
            this.Ed = n4;
            this.Ee = class_;
            this.Ef = class_ == null ? null : class_.getCanonicalName();
            this.Eh = b2;
        }

        public static a a(String string2, int n2, b<?, ?> b2, boolean bl2) {
            return new a(b2.eW(), bl2, b2.eX(), false, string2, n2, null, b2);
        }

        public static <T extends ga> a<T, T> a(String string2, int n2, Class<T> class_) {
            return new a<I, O>(11, false, 11, false, string2, n2, class_, null);
        }

        public static <T extends ga> a<ArrayList<T>, ArrayList<T>> b(String string2, int n2, Class<T> class_) {
            return new a<ArrayList<T>, ArrayList<T>>(11, true, 11, true, string2, n2, class_, null);
        }

        public static a<Integer, Integer> g(String string2, int n2) {
            return new a<Integer, Integer>(0, false, 0, false, string2, n2, null, null);
        }

        public static a<Double, Double> h(String string2, int n2) {
            return new a<Double, Double>(4, false, 4, false, string2, n2, null, null);
        }

        public static a<Boolean, Boolean> i(String string2, int n2) {
            return new a<Boolean, Boolean>(6, false, 6, false, string2, n2, null, null);
        }

        public static a<String, String> j(String string2, int n2) {
            return new a<String, String>(7, false, 7, false, string2, n2, null, null);
        }

        public static a<ArrayList<String>, ArrayList<String>> k(String string2, int n2) {
            return new a<ArrayList<String>, ArrayList<String>>(7, true, 7, true, string2, n2, null, null);
        }

        public void a(gd gd2) {
            this.Eg = gd2;
        }

        public int describeContents() {
            gb gb2 = CREATOR;
            return 0;
        }

        public int eW() {
            return this.DY;
        }

        public int eX() {
            return this.Ea;
        }

        public a<I, O> fb() {
            return new a<I, O>(this.xH, this.DY, this.DZ, this.Ea, this.Eb, this.Ec, this.Ed, this.Ef, this.fj());
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
            if (this.Ef == null) {
                return null;
            }
            return this.Ef;
        }

        public boolean fi() {
            if (this.Eh != null) {
                return true;
            }
            return false;
        }

        fv fj() {
            if (this.Eh == null) {
                return null;
            }
            return fv.a(this.Eh);
        }

        public HashMap<String, a<?, ?>> fk() {
            fq.f(this.Ef);
            fq.f(this.Eg);
            return this.Eg.au(this.Ef);
        }

        public I g(O o2) {
            return this.Eh.g(o2);
        }

        public int getVersionCode() {
            return this.xH;
        }

        /*
         * Enabled aggressive block sorting
         */
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
            stringBuilder.append("       concreteTypeName=").append(this.fh()).append('\n');
            if (this.fg() != null) {
                stringBuilder.append("     concreteType.class=").append(this.fg().getCanonicalName()).append('\n');
            }
            StringBuilder stringBuilder2 = stringBuilder.append("          converterName=");
            String string2 = this.Eh == null ? "null" : this.Eh.getClass().getCanonicalName();
            stringBuilder2.append(string2).append('\n');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n2) {
            gb gb2 = CREATOR;
            gb.a(this, parcel, n2);
        }
    }

    public static interface b<I, O> {
        public int eW();

        public int eX();

        public I g(O var1);
    }

}

