package com.google.android.gms.tagmanager;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0339c.C1363i;
import com.google.android.gms.internal.C0355d.C1367a;
import com.google.android.gms.tagmanager.C0525l.C0524a;
import com.google.android.gms.tagmanager.C1089s.C0530a;
import com.google.android.gms.tagmanager.cq.C0507a;
import com.google.android.gms.tagmanager.cq.C0509c;
import com.google.android.gms.tagmanager.cq.C0511e;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class cs {
    private static final by<C1367a> ZE;
    private final DataLayer WK;
    private final C0509c ZF;
    private final ag ZG;
    private final Map<String, aj> ZH;
    private final Map<String, aj> ZI;
    private final Map<String, aj> ZJ;
    private final C0523k<C0507a, by<C1367a>> ZK;
    private final C0523k<String, C0515b> ZL;
    private final Set<C0511e> ZM;
    private final Map<String, C0516c> ZN;
    private volatile String ZO;
    private int ZP;

    /* renamed from: com.google.android.gms.tagmanager.cs.a */
    interface C0514a {
        void m1427a(C0511e c0511e, Set<C0507a> set, Set<C0507a> set2, cm cmVar);
    }

    /* renamed from: com.google.android.gms.tagmanager.cs.b */
    private static class C0515b {
        private by<C1367a> ZV;
        private C1367a Zq;

        public C0515b(by<C1367a> byVar, C1367a c1367a) {
            this.ZV = byVar;
            this.Zq = c1367a;
        }

        public int getSize() {
            return (this.Zq == null ? 0 : this.Zq.mF()) + ((C1367a) this.ZV.getObject()).mF();
        }

        public C1367a lf() {
            return this.Zq;
        }

        public by<C1367a> lz() {
            return this.ZV;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cs.c */
    private static class C0516c {
        private final Set<C0511e> ZM;
        private final Map<C0511e, List<C0507a>> ZW;
        private final Map<C0511e, List<C0507a>> ZX;
        private final Map<C0511e, List<String>> ZY;
        private final Map<C0511e, List<String>> ZZ;
        private C0507a aaa;

        public C0516c() {
            this.ZM = new HashSet();
            this.ZW = new HashMap();
            this.ZY = new HashMap();
            this.ZX = new HashMap();
            this.ZZ = new HashMap();
        }

        public void m1428a(C0511e c0511e, C0507a c0507a) {
            List list = (List) this.ZW.get(c0511e);
            if (list == null) {
                list = new ArrayList();
                this.ZW.put(c0511e, list);
            }
            list.add(c0507a);
        }

        public void m1429a(C0511e c0511e, String str) {
            List list = (List) this.ZY.get(c0511e);
            if (list == null) {
                list = new ArrayList();
                this.ZY.put(c0511e, list);
            }
            list.add(str);
        }

        public void m1430b(C0511e c0511e) {
            this.ZM.add(c0511e);
        }

        public void m1431b(C0511e c0511e, C0507a c0507a) {
            List list = (List) this.ZX.get(c0511e);
            if (list == null) {
                list = new ArrayList();
                this.ZX.put(c0511e, list);
            }
            list.add(c0507a);
        }

        public void m1432b(C0511e c0511e, String str) {
            List list = (List) this.ZZ.get(c0511e);
            if (list == null) {
                list = new ArrayList();
                this.ZZ.put(c0511e, list);
            }
            list.add(str);
        }

        public void m1433i(C0507a c0507a) {
            this.aaa = c0507a;
        }

        public Set<C0511e> lA() {
            return this.ZM;
        }

        public Map<C0511e, List<C0507a>> lB() {
            return this.ZW;
        }

        public Map<C0511e, List<String>> lC() {
            return this.ZY;
        }

        public Map<C0511e, List<String>> lD() {
            return this.ZZ;
        }

        public Map<C0511e, List<C0507a>> lE() {
            return this.ZX;
        }

        public C0507a lF() {
            return this.aaa;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cs.1 */
    class C10671 implements C0524a<C0507a, by<C1367a>> {
        final /* synthetic */ cs ZQ;

        C10671(cs csVar) {
            this.ZQ = csVar;
        }

        public int m2501a(C0507a c0507a, by<C1367a> byVar) {
            return ((C1367a) byVar.getObject()).mF();
        }

        public /* synthetic */ int sizeOf(Object x0, Object x1) {
            return m2501a((C0507a) x0, (by) x1);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cs.2 */
    class C10682 implements C0524a<String, C0515b> {
        final /* synthetic */ cs ZQ;

        C10682(cs csVar) {
            this.ZQ = csVar;
        }

        public int m2502a(String str, C0515b c0515b) {
            return str.length() + c0515b.getSize();
        }

        public /* synthetic */ int sizeOf(Object x0, Object x1) {
            return m2502a((String) x0, (C0515b) x1);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cs.3 */
    class C10693 implements C0514a {
        final /* synthetic */ cs ZQ;
        final /* synthetic */ Map ZR;
        final /* synthetic */ Map ZS;
        final /* synthetic */ Map ZT;
        final /* synthetic */ Map ZU;

        C10693(cs csVar, Map map, Map map2, Map map3, Map map4) {
            this.ZQ = csVar;
            this.ZR = map;
            this.ZS = map2;
            this.ZT = map3;
            this.ZU = map4;
        }

        public void m2503a(C0511e c0511e, Set<C0507a> set, Set<C0507a> set2, cm cmVar) {
            List list = (List) this.ZR.get(c0511e);
            List list2 = (List) this.ZS.get(c0511e);
            if (list != null) {
                set.addAll(list);
                cmVar.kK().m1402b(list, list2);
            }
            list = (List) this.ZT.get(c0511e);
            list2 = (List) this.ZU.get(c0511e);
            if (list != null) {
                set2.addAll(list);
                cmVar.kL().m1402b(list, list2);
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cs.4 */
    class C10704 implements C0514a {
        final /* synthetic */ cs ZQ;

        C10704(cs csVar) {
            this.ZQ = csVar;
        }

        public void m2504a(C0511e c0511e, Set<C0507a> set, Set<C0507a> set2, cm cmVar) {
            set.addAll(c0511e.lo());
            set2.addAll(c0511e.lp());
            cmVar.kM().m1402b(c0511e.lo(), c0511e.lt());
            cmVar.kN().m1402b(c0511e.lp(), c0511e.lu());
        }
    }

    static {
        ZE = new by(dh.lT(), true);
    }

    public cs(Context context, C0509c c0509c, DataLayer dataLayer, C0530a c0530a, C0530a c0530a2, ag agVar) {
        if (c0509c == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.ZF = c0509c;
        this.ZM = new HashSet(c0509c.li());
        this.WK = dataLayer;
        this.ZG = agVar;
        this.ZK = new C0525l().m1481a(AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START, new C10671(this));
        this.ZL = new C0525l().m1481a(AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START, new C10682(this));
        this.ZH = new HashMap();
        m1448b(new C1416i(context));
        m1448b(new C1089s(c0530a2));
        m1448b(new C1419w(dataLayer));
        m1448b(new di(context, dataLayer));
        this.ZI = new HashMap();
        m1449c(new C1505q());
        m1449c(new ad());
        m1449c(new ae());
        m1449c(new al());
        m1449c(new am());
        m1449c(new bd());
        m1449c(new be());
        m1449c(new ch());
        m1449c(new db());
        this.ZJ = new HashMap();
        m1447a(new C1061b(context));
        m1447a(new C1063c(context));
        m1447a(new C1074e(context));
        m1447a(new C1075f(context));
        m1447a(new C1076g(context));
        m1447a(new C1077h(context));
        m1447a(new C1080m());
        m1447a(new C1088p(this.ZF.getVersion()));
        m1447a(new C1089s(c0530a));
        m1447a(new C1090u(dataLayer));
        m1447a(new C1094z(context));
        m1447a(new aa());
        m1447a(new ac());
        m1447a(new ah(this));
        m1447a(new an());
        m1447a(new ao());
        m1447a(new ax(context));
        m1447a(new az());
        m1447a(new bc());
        m1447a(new bk(context));
        m1447a(new bz());
        m1447a(new cb());
        m1447a(new ce());
        m1447a(new cg());
        m1447a(new ci(context));
        m1447a(new ct());
        m1447a(new cu());
        m1447a(new dd());
        this.ZN = new HashMap();
        for (C0511e c0511e : this.ZM) {
            if (agVar.kA()) {
                m1439a(c0511e.lq(), c0511e.lr(), "add macro");
                m1439a(c0511e.lv(), c0511e.ls(), "remove macro");
                m1439a(c0511e.lo(), c0511e.lt(), "add tag");
                m1439a(c0511e.lp(), c0511e.lu(), "remove tag");
            }
            int i = 0;
            while (i < c0511e.lq().size()) {
                C0507a c0507a = (C0507a) c0511e.lq().get(i);
                String str = "Unknown";
                if (agVar.kA() && i < c0511e.lr().size()) {
                    str = (String) c0511e.lr().get(i);
                }
                C0516c d = m1441d(this.ZN, m1442h(c0507a));
                d.m1430b(c0511e);
                d.m1428a(c0511e, c0507a);
                d.m1429a(c0511e, str);
                i++;
            }
            i = 0;
            while (i < c0511e.lv().size()) {
                c0507a = (C0507a) c0511e.lv().get(i);
                str = "Unknown";
                if (agVar.kA() && i < c0511e.ls().size()) {
                    str = (String) c0511e.ls().get(i);
                }
                d = m1441d(this.ZN, m1442h(c0507a));
                d.m1430b(c0511e);
                d.m1431b(c0511e, c0507a);
                d.m1432b(c0511e, str);
                i++;
            }
        }
        for (Entry entry : this.ZF.lj().entrySet()) {
            for (C0507a c0507a2 : (List) entry.getValue()) {
                if (!dh.m1466n((C1367a) c0507a2.le().get(C0325b.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    m1441d(this.ZN, (String) entry.getKey()).m1433i(c0507a2);
                }
            }
        }
    }

    private by<C1367a> m1434a(C1367a c1367a, Set<String> set, dj djVar) {
        if (!c1367a.fX) {
            return new by(c1367a, true);
        }
        C1367a g;
        int i;
        by a;
        switch (c1367a.type) {
            case Std.STD_URL /*2*/:
                g = cq.m1424g(c1367a);
                g.fO = new C1367a[c1367a.fO.length];
                for (i = 0; i < c1367a.fO.length; i++) {
                    a = m1434a(c1367a.fO[i], (Set) set, djVar.cd(i));
                    if (a == ZE) {
                        return ZE;
                    }
                    g.fO[i] = (C1367a) a.getObject();
                }
                return new by(g, false);
            case Std.STD_URI /*3*/:
                g = cq.m1424g(c1367a);
                if (c1367a.fP.length != c1367a.fQ.length) {
                    bh.m1384w("Invalid serving value: " + c1367a.toString());
                    return ZE;
                }
                g.fP = new C1367a[c1367a.fP.length];
                g.fQ = new C1367a[c1367a.fP.length];
                for (i = 0; i < c1367a.fP.length; i++) {
                    a = m1434a(c1367a.fP[i], (Set) set, djVar.ce(i));
                    by a2 = m1434a(c1367a.fQ[i], (Set) set, djVar.cf(i));
                    if (a == ZE || a2 == ZE) {
                        return ZE;
                    }
                    g.fP[i] = (C1367a) a.getObject();
                    g.fQ[i] = (C1367a) a2.getObject();
                }
                return new by(g, false);
            case Std.STD_CLASS /*4*/:
                if (set.contains(c1367a.fR)) {
                    bh.m1384w("Macro cycle detected.  Current macro reference: " + c1367a.fR + "." + "  Previous macro references: " + set.toString() + ".");
                    return ZE;
                }
                set.add(c1367a.fR);
                by<C1367a> a3 = dk.m1477a(m1435a(c1367a.fR, (Set) set, djVar.kP()), c1367a.fW);
                set.remove(c1367a.fR);
                return a3;
            case Std.STD_PATTERN /*7*/:
                g = cq.m1424g(c1367a);
                g.fV = new C1367a[c1367a.fV.length];
                for (i = 0; i < c1367a.fV.length; i++) {
                    a = m1434a(c1367a.fV[i], (Set) set, djVar.cg(i));
                    if (a == ZE) {
                        return ZE;
                    }
                    g.fV[i] = (C1367a) a.getObject();
                }
                return new by(g, false);
            default:
                bh.m1384w("Unknown type: " + c1367a.type);
                return ZE;
        }
    }

    private by<C1367a> m1435a(String str, Set<String> set, bj bjVar) {
        this.ZP++;
        C0515b c0515b = (C0515b) this.ZL.get(str);
        if (c0515b == null || this.ZG.kA()) {
            C0516c c0516c = (C0516c) this.ZN.get(str);
            if (c0516c == null) {
                bh.m1384w(ly() + "Invalid macro: " + str);
                this.ZP--;
                return ZE;
            }
            C0507a lF;
            by a = m1445a(str, c0516c.lA(), c0516c.lB(), c0516c.lC(), c0516c.lE(), c0516c.lD(), set, bjVar.kr());
            if (((Set) a.getObject()).isEmpty()) {
                lF = c0516c.lF();
            } else {
                if (((Set) a.getObject()).size() > 1) {
                    bh.m1387z(ly() + "Multiple macros active for macroName " + str);
                }
                lF = (C0507a) ((Set) a.getObject()).iterator().next();
            }
            if (lF == null) {
                this.ZP--;
                return ZE;
            }
            by a2 = m1436a(this.ZJ, lF, (Set) set, bjVar.kG());
            boolean z = a.kQ() && a2.kQ();
            by<C1367a> byVar = a2 == ZE ? ZE : new by(a2.getObject(), z);
            C1367a lf = lF.lf();
            if (byVar.kQ()) {
                this.ZL.m1480e(str, new C0515b(byVar, lf));
            }
            m1438a(lf, (Set) set);
            this.ZP--;
            return byVar;
        }
        m1438a(c0515b.lf(), (Set) set);
        this.ZP--;
        return c0515b.lz();
    }

    private by<C1367a> m1436a(Map<String, aj> map, C0507a c0507a, Set<String> set, cj cjVar) {
        boolean z = true;
        C1367a c1367a = (C1367a) c0507a.le().get(C0325b.FUNCTION.toString());
        if (c1367a == null) {
            bh.m1384w("No function id in properties");
            return ZE;
        }
        String str = c1367a.fS;
        aj ajVar = (aj) map.get(str);
        if (ajVar == null) {
            bh.m1384w(str + " has no backing implementation.");
            return ZE;
        }
        by<C1367a> byVar = (by) this.ZK.get(c0507a);
        if (byVar != null && !this.ZG.kA()) {
            return byVar;
        }
        Map hashMap = new HashMap();
        boolean z2 = true;
        for (Entry entry : c0507a.le().entrySet()) {
            by a = m1434a((C1367a) entry.getValue(), (Set) set, cjVar.bH((String) entry.getKey()).m1403e((C1367a) entry.getValue()));
            if (a == ZE) {
                return ZE;
            }
            boolean z3;
            if (a.kQ()) {
                c0507a.m1407a((String) entry.getKey(), (C1367a) a.getObject());
                z3 = z2;
            } else {
                z3 = false;
            }
            hashMap.put(entry.getKey(), a.getObject());
            z2 = z3;
        }
        if (ajVar.m1365a(hashMap.keySet())) {
            if (!(z2 && ajVar.jX())) {
                z = false;
            }
            byVar = new by(ajVar.m1366x(hashMap), z);
            if (z) {
                this.ZK.m1480e(c0507a, byVar);
            }
            cjVar.m1401d((C1367a) byVar.getObject());
            return byVar;
        }
        bh.m1384w("Incorrect keys for function " + str + " required " + ajVar.kC() + " had " + hashMap.keySet());
        return ZE;
    }

    private by<Set<C0507a>> m1437a(Set<C0511e> set, Set<String> set2, C0514a c0514a, cr crVar) {
        Set hashSet = new HashSet();
        Collection hashSet2 = new HashSet();
        boolean z = true;
        for (C0511e c0511e : set) {
            cm kO = crVar.kO();
            by a = m1444a(c0511e, (Set) set2, kO);
            if (((Boolean) a.getObject()).booleanValue()) {
                c0514a.m1427a(c0511e, hashSet, hashSet2, kO);
            }
            boolean z2 = z && a.kQ();
            z = z2;
        }
        hashSet.removeAll(hashSet2);
        crVar.m1426b(hashSet);
        return new by(hashSet, z);
    }

    private void m1438a(C1367a c1367a, Set<String> set) {
        if (c1367a != null) {
            by a = m1434a(c1367a, (Set) set, new bw());
            if (a != ZE) {
                Object o = dh.m1468o((C1367a) a.getObject());
                if (o instanceof Map) {
                    this.WK.push((Map) o);
                } else if (o instanceof List) {
                    for (Object o2 : (List) o2) {
                        if (o2 instanceof Map) {
                            this.WK.push((Map) o2);
                        } else {
                            bh.m1387z("pushAfterEvaluate: value not a Map");
                        }
                    }
                } else {
                    bh.m1387z("pushAfterEvaluate: value not a Map or List");
                }
            }
        }
    }

    private static void m1439a(List<C0507a> list, List<String> list2, String str) {
        if (list.size() != list2.size()) {
            bh.m1385x("Invalid resource: imbalance of rule names of functions for " + str + " operation. Using default rule name instead");
        }
    }

    private static void m1440a(Map<String, aj> map, aj ajVar) {
        if (map.containsKey(ajVar.kB())) {
            throw new IllegalArgumentException("Duplicate function type name: " + ajVar.kB());
        }
        map.put(ajVar.kB(), ajVar);
    }

    private static C0516c m1441d(Map<String, C0516c> map, String str) {
        C0516c c0516c = (C0516c) map.get(str);
        if (c0516c != null) {
            return c0516c;
        }
        c0516c = new C0516c();
        map.put(str, c0516c);
        return c0516c;
    }

    private static String m1442h(C0507a c0507a) {
        return dh.m1460j((C1367a) c0507a.le().get(C0325b.INSTANCE_NAME.toString()));
    }

    private String ly() {
        if (this.ZP <= 1) {
            return UnsupportedUrlFragment.DISPLAY_NAME;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.ZP));
        for (int i = 2; i < this.ZP; i++) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(": ");
        return stringBuilder.toString();
    }

    by<Boolean> m1443a(C0507a c0507a, Set<String> set, cj cjVar) {
        by a = m1436a(this.ZI, c0507a, (Set) set, cjVar);
        Boolean n = dh.m1466n((C1367a) a.getObject());
        cjVar.m1401d(dh.m1471r(n));
        return new by(n, a.kQ());
    }

    by<Boolean> m1444a(C0511e c0511e, Set<String> set, cm cmVar) {
        boolean z = true;
        for (C0507a a : c0511e.ln()) {
            by a2 = m1443a(a, (Set) set, cmVar.kI());
            if (((Boolean) a2.getObject()).booleanValue()) {
                cmVar.m1404f(dh.m1471r(Boolean.valueOf(false)));
                return new by(Boolean.valueOf(false), a2.kQ());
            }
            boolean z2 = z && a2.kQ();
            z = z2;
        }
        for (C0507a a3 : c0511e.lm()) {
            a2 = m1443a(a3, (Set) set, cmVar.kJ());
            if (((Boolean) a2.getObject()).booleanValue()) {
                z = z && a2.kQ();
            } else {
                cmVar.m1404f(dh.m1471r(Boolean.valueOf(false)));
                return new by(Boolean.valueOf(false), a2.kQ());
            }
        }
        cmVar.m1404f(dh.m1471r(Boolean.valueOf(true)));
        return new by(Boolean.valueOf(true), z);
    }

    by<Set<C0507a>> m1445a(String str, Set<C0511e> set, Map<C0511e, List<C0507a>> map, Map<C0511e, List<String>> map2, Map<C0511e, List<C0507a>> map3, Map<C0511e, List<String>> map4, Set<String> set2, cr crVar) {
        return m1437a((Set) set, (Set) set2, new C10693(this, map, map2, map3, map4), crVar);
    }

    by<Set<C0507a>> m1446a(Set<C0511e> set, cr crVar) {
        return m1437a((Set) set, new HashSet(), new C10704(this), crVar);
    }

    void m1447a(aj ajVar) {
        m1440a(this.ZJ, ajVar);
    }

    void m1448b(aj ajVar) {
        m1440a(this.ZH, ajVar);
    }

    public by<C1367a> bR(String str) {
        this.ZP = 0;
        af bA = this.ZG.bA(str);
        by<C1367a> a = m1435a(str, new HashSet(), bA.kx());
        bA.kz();
        return a;
    }

    synchronized void bS(String str) {
        this.ZO = str;
    }

    public synchronized void bp(String str) {
        bS(str);
        af bB = this.ZG.bB(str);
        C0531t ky = bB.ky();
        for (C0507a a : (Set) m1446a(this.ZM, ky.kr()).getObject()) {
            m1436a(this.ZH, a, new HashSet(), ky.kq());
        }
        bB.kz();
        bS(null);
    }

    void m1449c(aj ajVar) {
        m1440a(this.ZI, ajVar);
    }

    public synchronized void m1450e(List<C1363i> list) {
        for (C1363i c1363i : list) {
            if (c1363i.name == null || !c1363i.name.startsWith("gaExperiment:")) {
                bh.m1386y("Ignored supplemental: " + c1363i);
            } else {
                ai.m1361a(this.WK, c1363i);
            }
        }
    }

    synchronized String lx() {
        return this.ZO;
    }
}
