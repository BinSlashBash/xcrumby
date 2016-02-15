/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.c;
import com.google.android.gms.internal.d;
import com.google.android.gms.internal.kq;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dh;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class cq {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static d.a a(int var0, c.f var1_1, d.a[] var2_2, Set<Integer> var3_3) throws g {
        var6_4 = 0;
        var7_5 = 0;
        var5_6 = 0;
        if (var3_3.contains(var0)) {
            cq.bL("Value cycle detected.  Current value reference: " + var0 + "." + "  Previous value references: " + var3_3 + ".");
        }
        var10_7 = cq.a(var1_1.eX, var0, "values");
        if (var2_2[var0] != null) {
            return var2_2[var0];
        }
        var9_8 = null;
        var3_3.add(var0);
        switch (var10_7.type) {
            case 2: {
                var9_10 = cq.h(var10_7);
                var11_21 = cq.g(var10_7);
                var11_21.fO = new d.a[var9_10.fz.length];
                var12_24 = var9_10.fz;
                var6_4 = var12_24.length;
                var4_27 = 0;
                do {
                    var9_12 = var11_21;
                    if (var5_6 < var6_4) {
                        var7_5 = var12_24[var5_6];
                        var11_21.fO[var4_27] = cq.a(var7_5, var1_1, var2_2, var3_3);
                        ++var5_6;
                        ++var4_27;
                        continue;
                    }
                    ** GOTO lbl75
                    break;
                } while (true);
            }
            case 3: {
                var11_22 = cq.g(var10_7);
                var9_13 = cq.h(var10_7);
                if (var9_13.fA.length != var9_13.fB.length) {
                    cq.bL("Uneven map keys (" + var9_13.fA.length + ") and map values (" + var9_13.fB.length + ")");
                }
                var11_22.fP = new d.a[var9_13.fA.length];
                var11_22.fQ = new d.a[var9_13.fA.length];
                var12_25 = var9_13.fA;
                var7_5 = var12_25.length;
                var4_28 = 0;
                for (var5_6 = 0; var5_6 < var7_5; ++var5_6, ++var4_28) {
                    var8_30 = var12_25[var5_6];
                    var11_22.fP[var4_28] = cq.a(var8_30, var1_1, var2_2, var3_3);
                }
                var12_25 = var9_13.fB;
                var7_5 = var12_25.length;
                var4_28 = 0;
                var5_6 = var6_4;
                do {
                    var9_15 = var11_22;
                    if (var5_6 < var7_5) {
                        var6_4 = var12_25[var5_6];
                        var11_22.fQ[var4_28] = cq.a(var6_4, var1_1, var2_2, var3_3);
                        ++var5_6;
                        ++var4_28;
                        continue;
                    }
                    ** GOTO lbl75
                    break;
                } while (true);
            }
            case 4: {
                var9_16 = cq.g(var10_7);
                var9_16.fR = dh.j(cq.a(cq.h((d.a)var10_7).fE, var1_1, var2_2, var3_3));
                ** break;
            }
            case 7: {
                var11_23 = cq.g(var10_7);
                var9_17 = cq.h(var10_7);
                var11_23.fV = new d.a[var9_17.fD.length];
                var12_26 = var9_17.fD;
                var6_4 = var12_26.length;
                var4_29 = 0;
                var5_6 = var7_5;
                do {
                    var9_19 = var11_23;
                    if (var5_6 >= var6_4) break;
                    var7_5 = var12_26[var5_6];
                    var11_23.fV[var4_29] = cq.a(var7_5, var1_1, var2_2, var3_3);
                    ++var5_6;
                    ++var4_29;
                } while (true);
            }
lbl75: // 5 sources:
            default: {
                ** GOTO lbl79
            }
            case 1: 
            case 5: 
            case 6: 
            case 8: 
        }
        var9_20 = var10_7;
lbl79: // 2 sources:
        if (var9_9 == null) {
            cq.bL("Invalid value: " + var10_7);
        }
        var2_2[var0] = var9_9;
        var3_3.remove(var0);
        return var9_9;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static a a(c.b b2, c.f f2, d.a[] arra, int n2) throws g {
        b b3 = a.ld();
        int[] arrn = b2.eH;
        int n3 = arrn.length;
        int n4 = 0;
        while (n4 < n3) {
            void var2_3;
            int n5 = arrn[n4];
            c.e e2 = cq.a(var1_2.eY, n5, "properties");
            String string2 = cq.a(var1_2.eW, e2.key, "keys");
            d.a a2 = (d.a)cq.a(var2_3, e2.value, "values");
            if (com.google.android.gms.internal.b.dB.toString().equals(string2)) {
                b3.i(a2);
            } else {
                b3.b(string2, a2);
            }
            ++n4;
        }
        return b3.lg();
    }

    private static e a(c.g g2, List<a> list, List<a> list2, List<a> list3, c.f f2) {
        int n2;
        f f3 = e.ll();
        int[] arrn = g2.fn;
        int n3 = arrn.length;
        for (n2 = 0; n2 < n3; ++n2) {
            f3.b((a)list3.get(arrn[n2]));
        }
        arrn = g2.fo;
        n3 = arrn.length;
        for (n2 = 0; n2 < n3; ++n2) {
            f3.c((a)list3.get(arrn[n2]));
        }
        list3 = (List)g2.fp;
        n3 = list3.length;
        for (n2 = 0; n2 < n3; ++n2) {
            f3.d((a)list.get(Integer.valueOf(list3[n2])));
        }
        for (Object object222222 : (List)g2.fr) {
            f3.bN(f2.eX[Integer.valueOf(object222222).intValue()].fN);
        }
        list3 = (List)g2.fq;
        n3 = list3.length;
        for (n2 = 0; n2 < n3; ++n2) {
            f3.e((a)list.get(Integer.valueOf(list3[n2])));
        }
        for (Object object222222 : (List)g2.fs) {
            f3.bO(f2.eX[Integer.valueOf(object222222).intValue()].fN);
        }
        list = (List)g2.ft;
        n3 = list.length;
        for (n2 = 0; n2 < n3; ++n2) {
            f3.f(list2.get(Integer.valueOf(list[n2])));
        }
        for (Object object222222 : (List)g2.fv) {
            f3.bP(f2.eX[Integer.valueOf(object222222).intValue()].fN);
        }
        list = (List)g2.fu;
        n3 = list.length;
        for (n2 = 0; n2 < n3; ++n2) {
            f3.g(list2.get(Integer.valueOf(list[n2])));
        }
        for (Object object222222 : (c.g)g2.fw) {
            f3.bQ(f2.eX[Integer.valueOf((int)object222222).intValue()].fN);
        }
        return f3.lw();
    }

    private static <T> T a(T[] arrT, int n2, String string2) throws g {
        if (n2 < 0 || n2 >= arrT.length) {
            cq.bL("Index out of bounds detected: " + n2 + " in " + string2);
        }
        return arrT[n2];
    }

    public static c b(c.f f2) throws g {
        int n2;
        int n3 = 0;
        d.a[] arra = new d.a[f2.eX.length];
        for (n2 = 0; n2 < f2.eX.length; ++n2) {
            cq.a(n2, f2, arra, new HashSet<Integer>(0));
        }
        d d2 = c.lh();
        ArrayList<a> arrayList = new ArrayList<a>();
        for (n2 = 0; n2 < f2.fa.length; ++n2) {
            arrayList.add(cq.a(f2.fa[n2], f2, arra, n2));
        }
        ArrayList<a> arrayList2 = new ArrayList<a>();
        for (n2 = 0; n2 < f2.fb.length; ++n2) {
            arrayList2.add(cq.a(f2.fb[n2], f2, arra, n2));
        }
        ArrayList<a> arrayList3 = new ArrayList<a>();
        for (n2 = 0; n2 < f2.eZ.length; ++n2) {
            a a2 = cq.a(f2.eZ[n2], f2, arra, n2);
            d2.a(a2);
            arrayList3.add(a2);
        }
        arra = f2.fc;
        int n4 = arra.length;
        for (n2 = n3; n2 < n4; ++n2) {
            d2.a(cq.a((c.g)((Object)arra[n2]), arrayList, arrayList3, arrayList2, f2));
        }
        d2.bM(f2.fg);
        d2.ch(f2.fl);
        return d2.lk();
    }

    public static void b(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] arrby = new byte[1024];
        int n2;
        while ((n2 = inputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n2);
        }
        return;
    }

    private static void bL(String string2) throws g {
        bh.w(string2);
        throw new g(string2);
    }

    public static d.a g(d.a a2) {
        d.a a3 = new d.a();
        a3.type = a2.type;
        a3.fW = (int[])a2.fW.clone();
        if (a2.fX) {
            a3.fX = a2.fX;
        }
        return a3;
    }

    private static c.h h(d.a a2) throws g {
        if (a2.a(c.h.fx) == null) {
            cq.bL("Expected a ServingValue and didn't get one. Value is: " + a2);
        }
        return a2.a(c.h.fx);
    }

    public static class a {
        private final Map<String, d.a> Zp;
        private final d.a Zq;

        private a(Map<String, d.a> map, d.a a2) {
            this.Zp = map;
            this.Zq = a2;
        }

        public static b ld() {
            return new b();
        }

        public void a(String string2, d.a a2) {
            this.Zp.put(string2, a2);
        }

        public Map<String, d.a> le() {
            return Collections.unmodifiableMap(this.Zp);
        }

        public d.a lf() {
            return this.Zq;
        }

        public String toString() {
            return "Properties: " + this.le() + " pushAfterEvaluate: " + this.Zq;
        }
    }

    public static class b {
        private final Map<String, d.a> Zp = new HashMap<String, d.a>();
        private d.a Zq;

        private b() {
        }

        public b b(String string2, d.a a2) {
            this.Zp.put(string2, a2);
            return this;
        }

        public b i(d.a a2) {
            this.Zq = a2;
            return this;
        }

        public a lg() {
            return new a(this.Zp, this.Zq);
        }
    }

    public static class c {
        private final String Xl;
        private final List<e> Zr;
        private final Map<String, List<a>> Zs;
        private final int Zt;

        private c(List<e> list, Map<String, List<a>> map, String string2, int n2) {
            this.Zr = Collections.unmodifiableList(list);
            this.Zs = Collections.unmodifiableMap(map);
            this.Xl = string2;
            this.Zt = n2;
        }

        public static d lh() {
            return new d();
        }

        public String getVersion() {
            return this.Xl;
        }

        public List<e> li() {
            return this.Zr;
        }

        public Map<String, List<a>> lj() {
            return this.Zs;
        }

        public String toString() {
            return "Rules: " + this.li() + "  Macros: " + this.Zs;
        }
    }

    public static class d {
        private String Xl = "";
        private final List<e> Zr = new ArrayList<e>();
        private final Map<String, List<a>> Zs = new HashMap<String, List<a>>();
        private int Zt = 0;

        private d() {
        }

        public d a(a a2) {
            List<a> list;
            String string2 = dh.j(a2.le().get(com.google.android.gms.internal.b.cI.toString()));
            List<a> list2 = list = this.Zs.get(string2);
            if (list == null) {
                list2 = new ArrayList<a>();
                this.Zs.put(string2, list2);
            }
            list2.add(a2);
            return this;
        }

        public d a(e e2) {
            this.Zr.add(e2);
            return this;
        }

        public d bM(String string2) {
            this.Xl = string2;
            return this;
        }

        public d ch(int n2) {
            this.Zt = n2;
            return this;
        }

        public c lk() {
            return new c(this.Zr, this.Zs, this.Xl, this.Zt);
        }
    }

    public static class e {
        private final List<String> ZA;
        private final List<String> ZB;
        private final List<String> ZC;
        private final List<String> ZD;
        private final List<a> Zu;
        private final List<a> Zv;
        private final List<a> Zw;
        private final List<a> Zx;
        private final List<a> Zy;
        private final List<a> Zz;

        private e(List<a> list, List<a> list2, List<a> list3, List<a> list4, List<a> list5, List<a> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.Zu = Collections.unmodifiableList(list);
            this.Zv = Collections.unmodifiableList(list2);
            this.Zw = Collections.unmodifiableList(list3);
            this.Zx = Collections.unmodifiableList(list4);
            this.Zy = Collections.unmodifiableList(list5);
            this.Zz = Collections.unmodifiableList(list6);
            this.ZA = Collections.unmodifiableList(list7);
            this.ZB = Collections.unmodifiableList(list8);
            this.ZC = Collections.unmodifiableList(list9);
            this.ZD = Collections.unmodifiableList(list10);
        }

        public static f ll() {
            return new f();
        }

        public List<a> lm() {
            return this.Zu;
        }

        public List<a> ln() {
            return this.Zv;
        }

        public List<a> lo() {
            return this.Zw;
        }

        public List<a> lp() {
            return this.Zx;
        }

        public List<a> lq() {
            return this.Zy;
        }

        public List<String> lr() {
            return this.ZA;
        }

        public List<String> ls() {
            return this.ZB;
        }

        public List<String> lt() {
            return this.ZC;
        }

        public List<String> lu() {
            return this.ZD;
        }

        public List<a> lv() {
            return this.Zz;
        }

        public String toString() {
            return "Positive predicates: " + this.lm() + "  Negative predicates: " + this.ln() + "  Add tags: " + this.lo() + "  Remove tags: " + this.lp() + "  Add macros: " + this.lq() + "  Remove macros: " + this.lv();
        }
    }

    public static class f {
        private final List<String> ZA = new ArrayList<String>();
        private final List<String> ZB = new ArrayList<String>();
        private final List<String> ZC = new ArrayList<String>();
        private final List<String> ZD = new ArrayList<String>();
        private final List<a> Zu = new ArrayList<a>();
        private final List<a> Zv = new ArrayList<a>();
        private final List<a> Zw = new ArrayList<a>();
        private final List<a> Zx = new ArrayList<a>();
        private final List<a> Zy = new ArrayList<a>();
        private final List<a> Zz = new ArrayList<a>();

        private f() {
        }

        public f b(a a2) {
            this.Zu.add(a2);
            return this;
        }

        public f bN(String string2) {
            this.ZC.add(string2);
            return this;
        }

        public f bO(String string2) {
            this.ZD.add(string2);
            return this;
        }

        public f bP(String string2) {
            this.ZA.add(string2);
            return this;
        }

        public f bQ(String string2) {
            this.ZB.add(string2);
            return this;
        }

        public f c(a a2) {
            this.Zv.add(a2);
            return this;
        }

        public f d(a a2) {
            this.Zw.add(a2);
            return this;
        }

        public f e(a a2) {
            this.Zx.add(a2);
            return this;
        }

        public f f(a a2) {
            this.Zy.add(a2);
            return this;
        }

        public f g(a a2) {
            this.Zz.add(a2);
            return this;
        }

        public e lw() {
            return new e(this.Zu, this.Zv, this.Zw, this.Zx, this.Zy, this.Zz, this.ZA, this.ZB, this.ZC, this.ZD);
        }
    }

    public static class g
    extends Exception {
        public g(String string2) {
            super(string2);
        }
    }

}

