/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.gd;
import com.google.android.gms.internal.gh;
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.gj;
import com.google.android.gms.internal.gp;
import com.google.android.gms.internal.gq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class gg
extends ga
implements SafeParcelable {
    public static final gh CREATOR = new gh();
    private final gd Eg;
    private final Parcel En;
    private final int Eo;
    private int Ep;
    private int Eq;
    private final String mClassName;
    private final int xH;

    /*
     * Enabled aggressive block sorting
     */
    gg(int n2, Parcel parcel, gd gd2) {
        this.xH = n2;
        this.En = fq.f(parcel);
        this.Eo = 2;
        this.Eg = gd2;
        this.mClassName = this.Eg == null ? null : this.Eg.fo();
        this.Ep = 2;
    }

    private gg(SafeParcelable safeParcelable, gd gd2, String string2) {
        this.xH = 1;
        this.En = Parcel.obtain();
        safeParcelable.writeToParcel(this.En, 0);
        this.Eo = 1;
        this.Eg = fq.f(gd2);
        this.mClassName = fq.f(string2);
        this.Ep = 2;
    }

    public static <T extends ga> gg a(T t2) {
        String string2 = t2.getClass().getCanonicalName();
        gd gd2 = gg.b(t2);
        return new gg((SafeParcelable)t2, gd2, string2);
    }

    private static void a(gd gd2, ga a2) {
        Class class_ = a2.getClass();
        if (!gd2.b(class_)) {
            HashMap hashMap = a2.eY();
            gd2.a(class_, a2.eY());
            class_ = hashMap.keySet().iterator();
            while (class_.hasNext()) {
                a2 = hashMap.get((String)class_.next());
                Class<ga> class_2 = a2.fg();
                if (class_2 == null) continue;
                try {
                    gg.a(gd2, class_2.newInstance());
                    continue;
                }
                catch (InstantiationException var0_1) {
                    throw new IllegalStateException("Could not instantiate an object of type " + a2.fg().getCanonicalName(), var0_1);
                }
                catch (IllegalAccessException var0_2) {
                    throw new IllegalStateException("Could not access object of type " + a2.fg().getCanonicalName(), var0_2);
                }
            }
        }
    }

    private void a(StringBuilder stringBuilder, int n2, Object object) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Unknown type = " + n2);
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                stringBuilder.append(object);
                return;
            }
            case 7: {
                stringBuilder.append("\"").append(gp.av(object.toString())).append("\"");
                return;
            }
            case 8: {
                stringBuilder.append("\"").append(gj.d((byte[])object)).append("\"");
                return;
            }
            case 9: {
                stringBuilder.append("\"").append(gj.e((byte[])object));
                stringBuilder.append("\"");
                return;
            }
            case 10: {
                gq.a(stringBuilder, (HashMap)object);
                return;
            }
            case 11: 
        }
        throw new IllegalArgumentException("Method does not accept concrete type.");
    }

    private void a(StringBuilder stringBuilder, ga.a<?, ?> a2, Parcel parcel, int n2) {
        switch (a2.eX()) {
            default: {
                throw new IllegalArgumentException("Unknown field out type = " + a2.eX());
            }
            case 0: {
                this.b(stringBuilder, a2, this.a(a2, a.g(parcel, n2)));
                return;
            }
            case 1: {
                this.b(stringBuilder, a2, this.a(a2, a.j(parcel, n2)));
                return;
            }
            case 2: {
                this.b(stringBuilder, a2, this.a(a2, a.i(parcel, n2)));
                return;
            }
            case 3: {
                this.b(stringBuilder, a2, this.a(a2, Float.valueOf(a.k(parcel, n2))));
                return;
            }
            case 4: {
                this.b(stringBuilder, a2, this.a(a2, a.l(parcel, n2)));
                return;
            }
            case 5: {
                this.b(stringBuilder, a2, this.a(a2, a.m(parcel, n2)));
                return;
            }
            case 6: {
                this.b(stringBuilder, a2, this.a(a2, a.c(parcel, n2)));
                return;
            }
            case 7: {
                this.b(stringBuilder, a2, this.a(a2, a.n(parcel, n2)));
                return;
            }
            case 8: 
            case 9: {
                this.b(stringBuilder, a2, this.a(a2, a.q(parcel, n2)));
                return;
            }
            case 10: {
                this.b(stringBuilder, a2, this.a(a2, gg.c(a.p(parcel, n2))));
                return;
            }
            case 11: 
        }
        throw new IllegalArgumentException("Method does not accept concrete type.");
    }

    private void a(StringBuilder stringBuilder, String string2, ga.a<?, ?> a2, Parcel parcel, int n2) {
        stringBuilder.append("\"").append(string2).append("\":");
        if (a2.fi()) {
            this.a(stringBuilder, a2, parcel, n2);
            return;
        }
        this.b(stringBuilder, a2, parcel, n2);
    }

    private void a(StringBuilder stringBuilder, HashMap<String, ga.a<?, ?>> hashMap, Parcel parcel) {
        hashMap = gg.c(hashMap);
        stringBuilder.append('{');
        int n2 = a.o(parcel);
        boolean bl2 = false;
        while (parcel.dataPosition() < n2) {
            int n3 = a.n(parcel);
            Map.Entry entry = (Map.Entry)hashMap.get(a.R(n3));
            if (entry == null) continue;
            if (bl2) {
                stringBuilder.append(",");
            }
            this.a(stringBuilder, (String)entry.getKey(), (ga.a)entry.getValue(), parcel, n3);
            bl2 = true;
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        stringBuilder.append('}');
    }

    private static gd b(ga ga2) {
        gd gd2 = new gd(ga2.getClass());
        gg.a(gd2, ga2);
        gd2.fm();
        gd2.fl();
        return gd2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(StringBuilder stringBuilder, ga.a<?, ?> object, Parcel object2, int n2) {
        if (object.fd()) {
            stringBuilder.append("[");
            switch (object.eX()) {
                default: {
                    throw new IllegalStateException("Unknown field type out.");
                }
                case 0: {
                    gi.a(stringBuilder, a.t((Parcel)object2, n2));
                    break;
                }
                case 1: {
                    gi.a(stringBuilder, a.v((Parcel)object2, n2));
                    break;
                }
                case 2: {
                    gi.a(stringBuilder, a.u((Parcel)object2, n2));
                    break;
                }
                case 3: {
                    gi.a(stringBuilder, a.w((Parcel)object2, n2));
                    break;
                }
                case 4: {
                    gi.a(stringBuilder, a.x((Parcel)object2, n2));
                    break;
                }
                case 5: {
                    gi.a(stringBuilder, a.y((Parcel)object2, n2));
                    break;
                }
                case 6: {
                    gi.a(stringBuilder, a.s((Parcel)object2, n2));
                    break;
                }
                case 7: {
                    gi.a(stringBuilder, a.z((Parcel)object2, n2));
                    break;
                }
                case 8: 
                case 9: 
                case 10: {
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                }
                case 11: {
                    object2 = a.C((Parcel)object2, n2);
                    int n3 = object2.length;
                    for (n2 = 0; n2 < n3; ++n2) {
                        if (n2 > 0) {
                            stringBuilder.append(",");
                        }
                        object2[n2].setDataPosition(0);
                        this.a(stringBuilder, object.fk(), (Parcel)object2[n2]);
                    }
                }
            }
            stringBuilder.append("]");
            return;
        }
        switch (object.eX()) {
            default: {
                throw new IllegalStateException("Unknown field type out");
            }
            case 0: {
                stringBuilder.append(a.g((Parcel)object2, n2));
                return;
            }
            case 1: {
                stringBuilder.append(a.j((Parcel)object2, n2));
                return;
            }
            case 2: {
                stringBuilder.append(a.i((Parcel)object2, n2));
                return;
            }
            case 3: {
                stringBuilder.append(a.k((Parcel)object2, n2));
                return;
            }
            case 4: {
                stringBuilder.append(a.l((Parcel)object2, n2));
                return;
            }
            case 5: {
                stringBuilder.append(a.m((Parcel)object2, n2));
                return;
            }
            case 6: {
                stringBuilder.append(a.c((Parcel)object2, n2));
                return;
            }
            case 7: {
                object = a.n((Parcel)object2, n2);
                stringBuilder.append("\"").append(gp.av((String)object)).append("\"");
                return;
            }
            case 8: {
                object = a.q((Parcel)object2, n2);
                stringBuilder.append("\"").append(gj.d((byte[])object)).append("\"");
                return;
            }
            case 9: {
                object = a.q((Parcel)object2, n2);
                stringBuilder.append("\"").append(gj.e((byte[])object));
                stringBuilder.append("\"");
                return;
            }
            case 10: {
                object = a.p((Parcel)object2, n2);
                object2 = object.keySet();
                object2.size();
                stringBuilder.append("{");
                object2 = object2.iterator();
                n2 = 1;
                do {
                    if (!object2.hasNext()) {
                        stringBuilder.append("}");
                        return;
                    }
                    String string2 = (String)object2.next();
                    if (n2 == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(string2).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(gp.av(object.getString(string2))).append("\"");
                    n2 = 0;
                } while (true);
            }
            case 11: 
        }
        object2 = a.B((Parcel)object2, n2);
        object2.setDataPosition(0);
        this.a(stringBuilder, object.fk(), (Parcel)object2);
    }

    private void b(StringBuilder stringBuilder, ga.a<?, ?> a2, Object object) {
        if (a2.fc()) {
            this.b(stringBuilder, a2, (ArrayList)object);
            return;
        }
        this.a(stringBuilder, a2.eW(), object);
    }

    private void b(StringBuilder stringBuilder, ga.a<?, ?> a2, ArrayList<?> arrayList) {
        stringBuilder.append("[");
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (i2 != 0) {
                stringBuilder.append(",");
            }
            this.a(stringBuilder, a2.eW(), arrayList.get(i2));
        }
        stringBuilder.append("]");
    }

    public static HashMap<String, String> c(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String string2 : bundle.keySet()) {
            hashMap.put(string2, bundle.getString(string2));
        }
        return hashMap;
    }

    private static HashMap<Integer, Map.Entry<String, ga.a<?, ?>>> c(HashMap<String, ga.a<?, ?>> object) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : object.entrySet()) {
            hashMap.put(((ga.a)entry.getValue()).ff(), entry);
        }
        return hashMap;
    }

    @Override
    protected Object aq(String string2) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override
    protected boolean ar(String string2) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public int describeContents() {
        gh gh2 = CREATOR;
        return 0;
    }

    @Override
    public HashMap<String, ga.a<?, ?>> eY() {
        if (this.Eg == null) {
            return null;
        }
        return this.Eg.au(this.mClassName);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Parcel fq() {
        switch (this.Ep) {
            case 0: {
                this.Eq = b.p(this.En);
                b.F(this.En, this.Eq);
                this.Ep = 2;
            }
            default: {
                return this.En;
            }
            case 1: 
        }
        b.F(this.En, this.Eq);
        this.Ep = 2;
        return this.En;
    }

    gd fr() {
        switch (this.Eo) {
            default: {
                throw new IllegalStateException("Invalid creation type: " + this.Eo);
            }
            case 0: {
                return null;
            }
            case 1: {
                return this.Eg;
            }
            case 2: 
        }
        return this.Eg;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override
    public String toString() {
        fq.b(this.Eg, (Object)"Cannot convert to JSON on client side.");
        Parcel parcel = this.fq();
        parcel.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        this.a(stringBuilder, this.Eg.au(this.mClassName), parcel);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        gh gh2 = CREATOR;
        gh.a(this, parcel, n2);
    }
}

