package com.google.android.gms.tagmanager;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0339c.C1356b;
import com.google.android.gms.internal.C0339c.C1359e;
import com.google.android.gms.internal.C0339c.C1360f;
import com.google.android.gms.internal.C0339c.C1361g;
import com.google.android.gms.internal.C0339c.C1362h;
import com.google.android.gms.internal.C0355d.C1367a;
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

    /* renamed from: com.google.android.gms.tagmanager.cq.a */
    public static class C0507a {
        private final Map<String, C1367a> Zp;
        private final C1367a Zq;

        private C0507a(Map<String, C1367a> map, C1367a c1367a) {
            this.Zp = map;
            this.Zq = c1367a;
        }

        public static C0508b ld() {
            return new C0508b();
        }

        public void m1407a(String str, C1367a c1367a) {
            this.Zp.put(str, c1367a);
        }

        public Map<String, C1367a> le() {
            return Collections.unmodifiableMap(this.Zp);
        }

        public C1367a lf() {
            return this.Zq;
        }

        public String toString() {
            return "Properties: " + le() + " pushAfterEvaluate: " + this.Zq;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cq.b */
    public static class C0508b {
        private final Map<String, C1367a> Zp;
        private C1367a Zq;

        private C0508b() {
            this.Zp = new HashMap();
        }

        public C0508b m1408b(String str, C1367a c1367a) {
            this.Zp.put(str, c1367a);
            return this;
        }

        public C0508b m1409i(C1367a c1367a) {
            this.Zq = c1367a;
            return this;
        }

        public C0507a lg() {
            return new C0507a(this.Zq, null);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cq.c */
    public static class C0509c {
        private final String Xl;
        private final List<C0511e> Zr;
        private final Map<String, List<C0507a>> Zs;
        private final int Zt;

        private C0509c(List<C0511e> list, Map<String, List<C0507a>> map, String str, int i) {
            this.Zr = Collections.unmodifiableList(list);
            this.Zs = Collections.unmodifiableMap(map);
            this.Xl = str;
            this.Zt = i;
        }

        public static C0510d lh() {
            return new C0510d();
        }

        public String getVersion() {
            return this.Xl;
        }

        public List<C0511e> li() {
            return this.Zr;
        }

        public Map<String, List<C0507a>> lj() {
            return this.Zs;
        }

        public String toString() {
            return "Rules: " + li() + "  Macros: " + this.Zs;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cq.d */
    public static class C0510d {
        private String Xl;
        private final List<C0511e> Zr;
        private final Map<String, List<C0507a>> Zs;
        private int Zt;

        private C0510d() {
            this.Zr = new ArrayList();
            this.Zs = new HashMap();
            this.Xl = UnsupportedUrlFragment.DISPLAY_NAME;
            this.Zt = 0;
        }

        public C0510d m1410a(C0507a c0507a) {
            String j = dh.m1460j((C1367a) c0507a.le().get(C0325b.INSTANCE_NAME.toString()));
            List list = (List) this.Zs.get(j);
            if (list == null) {
                list = new ArrayList();
                this.Zs.put(j, list);
            }
            list.add(c0507a);
            return this;
        }

        public C0510d m1411a(C0511e c0511e) {
            this.Zr.add(c0511e);
            return this;
        }

        public C0510d bM(String str) {
            this.Xl = str;
            return this;
        }

        public C0510d ch(int i) {
            this.Zt = i;
            return this;
        }

        public C0509c lk() {
            return new C0509c(this.Zs, this.Xl, this.Zt, null);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cq.e */
    public static class C0511e {
        private final List<String> ZA;
        private final List<String> ZB;
        private final List<String> ZC;
        private final List<String> ZD;
        private final List<C0507a> Zu;
        private final List<C0507a> Zv;
        private final List<C0507a> Zw;
        private final List<C0507a> Zx;
        private final List<C0507a> Zy;
        private final List<C0507a> Zz;

        private C0511e(List<C0507a> list, List<C0507a> list2, List<C0507a> list3, List<C0507a> list4, List<C0507a> list5, List<C0507a> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
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

        public static C0512f ll() {
            return new C0512f();
        }

        public List<C0507a> lm() {
            return this.Zu;
        }

        public List<C0507a> ln() {
            return this.Zv;
        }

        public List<C0507a> lo() {
            return this.Zw;
        }

        public List<C0507a> lp() {
            return this.Zx;
        }

        public List<C0507a> lq() {
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

        public List<C0507a> lv() {
            return this.Zz;
        }

        public String toString() {
            return "Positive predicates: " + lm() + "  Negative predicates: " + ln() + "  Add tags: " + lo() + "  Remove tags: " + lp() + "  Add macros: " + lq() + "  Remove macros: " + lv();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cq.f */
    public static class C0512f {
        private final List<String> ZA;
        private final List<String> ZB;
        private final List<String> ZC;
        private final List<String> ZD;
        private final List<C0507a> Zu;
        private final List<C0507a> Zv;
        private final List<C0507a> Zw;
        private final List<C0507a> Zx;
        private final List<C0507a> Zy;
        private final List<C0507a> Zz;

        private C0512f() {
            this.Zu = new ArrayList();
            this.Zv = new ArrayList();
            this.Zw = new ArrayList();
            this.Zx = new ArrayList();
            this.Zy = new ArrayList();
            this.Zz = new ArrayList();
            this.ZA = new ArrayList();
            this.ZB = new ArrayList();
            this.ZC = new ArrayList();
            this.ZD = new ArrayList();
        }

        public C0512f m1412b(C0507a c0507a) {
            this.Zu.add(c0507a);
            return this;
        }

        public C0512f bN(String str) {
            this.ZC.add(str);
            return this;
        }

        public C0512f bO(String str) {
            this.ZD.add(str);
            return this;
        }

        public C0512f bP(String str) {
            this.ZA.add(str);
            return this;
        }

        public C0512f bQ(String str) {
            this.ZB.add(str);
            return this;
        }

        public C0512f m1413c(C0507a c0507a) {
            this.Zv.add(c0507a);
            return this;
        }

        public C0512f m1414d(C0507a c0507a) {
            this.Zw.add(c0507a);
            return this;
        }

        public C0512f m1415e(C0507a c0507a) {
            this.Zx.add(c0507a);
            return this;
        }

        public C0512f m1416f(C0507a c0507a) {
            this.Zy.add(c0507a);
            return this;
        }

        public C0512f m1417g(C0507a c0507a) {
            this.Zz.add(c0507a);
            return this;
        }

        public C0511e lw() {
            return new C0511e(this.Zv, this.Zw, this.Zx, this.Zy, this.Zz, this.ZA, this.ZB, this.ZC, this.ZD, null);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cq.g */
    public static class C0513g extends Exception {
        public C0513g(String str) {
            super(str);
        }
    }

    private static C1367a m1418a(int i, C1360f c1360f, C1367a[] c1367aArr, Set<Integer> set) throws C0513g {
        int i2 = 0;
        if (set.contains(Integer.valueOf(i))) {
            bL("Value cycle detected.  Current value reference: " + i + "." + "  Previous value references: " + set + ".");
        }
        C1367a c1367a = (C1367a) m1421a(c1360f.eX, i, "values");
        if (c1367aArr[i] != null) {
            return c1367aArr[i];
        }
        C1367a c1367a2 = null;
        set.add(Integer.valueOf(i));
        C1362h h;
        int[] iArr;
        int length;
        int i3;
        int i4;
        switch (c1367a.type) {
            case Std.STD_FILE /*1*/:
            case Std.STD_JAVA_TYPE /*5*/:
            case Std.STD_CURRENCY /*6*/:
            case Std.STD_LOCALE /*8*/:
                c1367a2 = c1367a;
                break;
            case Std.STD_URL /*2*/:
                h = m1425h(c1367a);
                c1367a2 = m1424g(c1367a);
                c1367a2.fO = new C1367a[h.fz.length];
                iArr = h.fz;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    c1367a2.fO[i3] = m1418a(iArr[i2], c1360f, c1367aArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
            case Std.STD_URI /*3*/:
                c1367a2 = m1424g(c1367a);
                C1362h h2 = m1425h(c1367a);
                if (h2.fA.length != h2.fB.length) {
                    bL("Uneven map keys (" + h2.fA.length + ") and map values (" + h2.fB.length + ")");
                }
                c1367a2.fP = new C1367a[h2.fA.length];
                c1367a2.fQ = new C1367a[h2.fA.length];
                int[] iArr2 = h2.fA;
                int length2 = iArr2.length;
                i3 = 0;
                i4 = 0;
                while (i3 < length2) {
                    int i5 = i4 + 1;
                    c1367a2.fP[i4] = m1418a(iArr2[i3], c1360f, c1367aArr, (Set) set);
                    i3++;
                    i4 = i5;
                }
                iArr = h2.fB;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    c1367a2.fQ[i3] = m1418a(iArr[i2], c1360f, c1367aArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
            case Std.STD_CLASS /*4*/:
                c1367a2 = m1424g(c1367a);
                c1367a2.fR = dh.m1460j(m1418a(m1425h(c1367a).fE, c1360f, c1367aArr, (Set) set));
                break;
            case Std.STD_PATTERN /*7*/:
                c1367a2 = m1424g(c1367a);
                h = m1425h(c1367a);
                c1367a2.fV = new C1367a[h.fD.length];
                iArr = h.fD;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    c1367a2.fV[i3] = m1418a(iArr[i2], c1360f, c1367aArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
        }
        if (c1367a2 == null) {
            bL("Invalid value: " + c1367a);
        }
        c1367aArr[i] = c1367a2;
        set.remove(Integer.valueOf(i));
        return c1367a2;
    }

    private static C0507a m1419a(C1356b c1356b, C1360f c1360f, C1367a[] c1367aArr, int i) throws C0513g {
        C0508b ld = C0507a.ld();
        for (int valueOf : c1356b.eH) {
            C1359e c1359e = (C1359e) m1421a(c1360f.eY, Integer.valueOf(valueOf).intValue(), "properties");
            String str = (String) m1421a(c1360f.eW, c1359e.key, "keys");
            C1367a c1367a = (C1367a) m1421a(c1367aArr, c1359e.value, "values");
            if (C0325b.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                ld.m1409i(c1367a);
            } else {
                ld.m1408b(str, c1367a);
            }
        }
        return ld.lg();
    }

    private static C0511e m1420a(C1361g c1361g, List<C0507a> list, List<C0507a> list2, List<C0507a> list3, C1360f c1360f) {
        C0512f ll = C0511e.ll();
        for (int valueOf : c1361g.fn) {
            ll.m1412b((C0507a) list3.get(Integer.valueOf(valueOf).intValue()));
        }
        for (int valueOf2 : c1361g.fo) {
            ll.m1413c((C0507a) list3.get(Integer.valueOf(valueOf2).intValue()));
        }
        for (int valueOf22 : c1361g.fp) {
            ll.m1414d((C0507a) list.get(Integer.valueOf(valueOf22).intValue()));
        }
        for (int valueOf3 : c1361g.fr) {
            ll.bN(c1360f.eX[Integer.valueOf(valueOf3).intValue()].fN);
        }
        for (int valueOf222 : c1361g.fq) {
            ll.m1415e((C0507a) list.get(Integer.valueOf(valueOf222).intValue()));
        }
        for (int valueOf32 : c1361g.fs) {
            ll.bO(c1360f.eX[Integer.valueOf(valueOf32).intValue()].fN);
        }
        for (int valueOf2222 : c1361g.ft) {
            ll.m1416f((C0507a) list2.get(Integer.valueOf(valueOf2222).intValue()));
        }
        for (int valueOf322 : c1361g.fv) {
            ll.bP(c1360f.eX[Integer.valueOf(valueOf322).intValue()].fN);
        }
        for (int valueOf22222 : c1361g.fu) {
            ll.m1417g((C0507a) list2.get(Integer.valueOf(valueOf22222).intValue()));
        }
        for (int valueOf4 : c1361g.fw) {
            ll.bQ(c1360f.eX[Integer.valueOf(valueOf4).intValue()].fN);
        }
        return ll.lw();
    }

    private static <T> T m1421a(T[] tArr, int i, String str) throws C0513g {
        if (i < 0 || i >= tArr.length) {
            bL("Index out of bounds detected: " + i + " in " + str);
        }
        return tArr[i];
    }

    public static C0509c m1422b(C1360f c1360f) throws C0513g {
        int i;
        int i2 = 0;
        C1367a[] c1367aArr = new C1367a[c1360f.eX.length];
        for (i = 0; i < c1360f.eX.length; i++) {
            m1418a(i, c1360f, c1367aArr, new HashSet(0));
        }
        C0510d lh = C0509c.lh();
        List arrayList = new ArrayList();
        for (i = 0; i < c1360f.fa.length; i++) {
            arrayList.add(m1419a(c1360f.fa[i], c1360f, c1367aArr, i));
        }
        List arrayList2 = new ArrayList();
        for (i = 0; i < c1360f.fb.length; i++) {
            arrayList2.add(m1419a(c1360f.fb[i], c1360f, c1367aArr, i));
        }
        List arrayList3 = new ArrayList();
        for (i = 0; i < c1360f.eZ.length; i++) {
            C0507a a = m1419a(c1360f.eZ[i], c1360f, c1367aArr, i);
            lh.m1410a(a);
            arrayList3.add(a);
        }
        C1361g[] c1361gArr = c1360f.fc;
        int length = c1361gArr.length;
        while (i2 < length) {
            lh.m1411a(m1420a(c1361gArr[i2], arrayList, arrayList3, arrayList2, c1360f));
            i2++;
        }
        lh.bM(c1360f.fg);
        lh.ch(c1360f.fl);
        return lh.lk();
    }

    public static void m1423b(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    private static void bL(String str) throws C0513g {
        bh.m1384w(str);
        throw new C0513g(str);
    }

    public static C1367a m1424g(C1367a c1367a) {
        C1367a c1367a2 = new C1367a();
        c1367a2.type = c1367a.type;
        c1367a2.fW = (int[]) c1367a.fW.clone();
        if (c1367a.fX) {
            c1367a2.fX = c1367a.fX;
        }
        return c1367a2;
    }

    private static C1362h m1425h(C1367a c1367a) throws C0513g {
        if (((C1362h) c1367a.m2323a(C1362h.fx)) == null) {
            bL("Expected a ServingValue and didn't get one. Value is: " + c1367a);
        }
        return (C1362h) c1367a.m2323a(C1362h.fx);
    }
}
