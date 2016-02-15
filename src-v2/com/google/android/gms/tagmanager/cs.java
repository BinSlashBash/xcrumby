/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.c;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.aa;
import com.google.android.gms.tagmanager.ac;
import com.google.android.gms.tagmanager.ad;
import com.google.android.gms.tagmanager.ae;
import com.google.android.gms.tagmanager.af;
import com.google.android.gms.tagmanager.ag;
import com.google.android.gms.tagmanager.ah;
import com.google.android.gms.tagmanager.ai;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.al;
import com.google.android.gms.tagmanager.am;
import com.google.android.gms.tagmanager.an;
import com.google.android.gms.tagmanager.ao;
import com.google.android.gms.tagmanager.ax;
import com.google.android.gms.tagmanager.az;
import com.google.android.gms.tagmanager.bc;
import com.google.android.gms.tagmanager.bd;
import com.google.android.gms.tagmanager.be;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.bj;
import com.google.android.gms.tagmanager.bk;
import com.google.android.gms.tagmanager.bw;
import com.google.android.gms.tagmanager.by;
import com.google.android.gms.tagmanager.bz;
import com.google.android.gms.tagmanager.cb;
import com.google.android.gms.tagmanager.ce;
import com.google.android.gms.tagmanager.cg;
import com.google.android.gms.tagmanager.ch;
import com.google.android.gms.tagmanager.ci;
import com.google.android.gms.tagmanager.cj;
import com.google.android.gms.tagmanager.ck;
import com.google.android.gms.tagmanager.cl;
import com.google.android.gms.tagmanager.cm;
import com.google.android.gms.tagmanager.cq;
import com.google.android.gms.tagmanager.cr;
import com.google.android.gms.tagmanager.ct;
import com.google.android.gms.tagmanager.cu;
import com.google.android.gms.tagmanager.db;
import com.google.android.gms.tagmanager.dd;
import com.google.android.gms.tagmanager.dh;
import com.google.android.gms.tagmanager.di;
import com.google.android.gms.tagmanager.dj;
import com.google.android.gms.tagmanager.dk;
import com.google.android.gms.tagmanager.e;
import com.google.android.gms.tagmanager.f;
import com.google.android.gms.tagmanager.g;
import com.google.android.gms.tagmanager.h;
import com.google.android.gms.tagmanager.i;
import com.google.android.gms.tagmanager.k;
import com.google.android.gms.tagmanager.l;
import com.google.android.gms.tagmanager.m;
import com.google.android.gms.tagmanager.p;
import com.google.android.gms.tagmanager.q;
import com.google.android.gms.tagmanager.s;
import com.google.android.gms.tagmanager.t;
import com.google.android.gms.tagmanager.u;
import com.google.android.gms.tagmanager.w;
import com.google.android.gms.tagmanager.z;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class cs {
    private static final by<d.a> ZE = new by<d.a>(dh.lT(), true);
    private final DataLayer WK;
    private final cq.c ZF;
    private final ag ZG;
    private final Map<String, aj> ZH;
    private final Map<String, aj> ZI;
    private final Map<String, aj> ZJ;
    private final k<cq.a, by<d.a>> ZK;
    private final k<String, b> ZL;
    private final Set<cq.e> ZM;
    private final Map<String, c> ZN;
    private volatile String ZO;
    private int ZP;

    public cs(Context object, cq.c object22, DataLayer iterator, s.a object322, s.a object4, ag ag2) {
        void var6_19;
        Iterator iterator2;
        cq.a a2;
        void var4_13;
        if (object22 == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.ZF = object22;
        this.ZM = new HashSet<cq.e>(object22.li());
        this.WK = iterator2;
        this.ZG = var6_19;
        l.a<cq.a, by<d.a>> a3 = new l.a<cq.a, by<d.a>>(){

            public int a(cq.a a2, by<d.a> by2) {
                return by2.getObject().mF();
            }

            @Override
            public /* synthetic */ int sizeOf(Object object, Object object2) {
                return this.a((cq.a)object, (by)object2);
            }
        };
        this.ZK = new l().a(1048576, a3);
        l.a<String, b> a4 = new l.a<String, b>(){

            public int a(String string2, b b2) {
                return string2.length() + b2.getSize();
            }

            @Override
            public /* synthetic */ int sizeOf(Object object, Object object2) {
                return this.a((String)object, (b)object2);
            }
        };
        this.ZL = new l().a(1048576, a4);
        this.ZH = new HashMap<String, aj>();
        this.b(new i((Context)object));
        this.b(new s((s.a)((Object)a2)));
        this.b(new w((DataLayer)((Object)iterator2)));
        this.b(new di((Context)object, (DataLayer)((Object)iterator2)));
        this.ZI = new HashMap<String, aj>();
        this.c(new q());
        this.c(new ad());
        this.c(new ae());
        this.c(new al());
        this.c(new am());
        this.c(new bd());
        this.c(new be());
        this.c(new ch());
        this.c(new db());
        this.ZJ = new HashMap<String, aj>();
        this.a(new com.google.android.gms.tagmanager.b((Context)object));
        this.a(new com.google.android.gms.tagmanager.c((Context)object));
        this.a(new e((Context)object));
        this.a(new f((Context)object));
        this.a(new g((Context)object));
        this.a(new h((Context)object));
        this.a(new m());
        this.a(new p(this.ZF.getVersion()));
        this.a(new s((s.a)var4_13));
        this.a(new u((DataLayer)((Object)iterator2)));
        this.a(new z((Context)object));
        this.a(new aa());
        this.a(new ac());
        this.a(new ah(this));
        this.a(new an());
        this.a(new ao());
        this.a(new ax((Context)object));
        this.a(new az());
        this.a(new bc());
        this.a(new bk((Context)object));
        this.a(new bz());
        this.a(new cb());
        this.a(new ce());
        this.a(new cg());
        this.a(new ci((Context)object));
        this.a(new ct());
        this.a(new cu());
        this.a(new dd());
        this.ZN = new HashMap<String, c>();
        for (cq.e e2 : this.ZM) {
            int n2;
            if (var6_19.kA()) {
                cs.a(e2.lq(), e2.lr(), "add macro");
                cs.a(e2.lv(), e2.ls(), "remove macro");
                cs.a(e2.lo(), e2.lt(), "add tag");
                cs.a(e2.lp(), e2.lu(), "remove tag");
            }
            for (n2 = 0; n2 < e2.lq().size(); ++n2) {
                String string2;
                a2 = e2.lq().get(n2);
                object = string2 = "Unknown";
                if (var6_19.kA()) {
                    object = string2;
                    if (n2 < e2.lr().size()) {
                        object = e2.lr().get(n2);
                    }
                }
                c c2 = cs.d(this.ZN, cs.h(a2));
                c2.b(e2);
                c2.a(e2, a2);
                c2.a(e2, (String)object);
            }
            for (n2 = 0; n2 < e2.lv().size(); ++n2) {
                String string3;
                a2 = e2.lv().get(n2);
                object = string3 = "Unknown";
                if (var6_19.kA()) {
                    object = string3;
                    if (n2 < e2.ls().size()) {
                        object = e2.ls().get(n2);
                    }
                }
                c c3 = cs.d(this.ZN, cs.h(a2));
                c3.b(e2);
                c3.b(e2, a2);
                c3.b(e2, (String)object);
            }
        }
        for (Map.Entry<String, List<cq.a>> entry : this.ZF.lj().entrySet()) {
            for (cq.a a5 : entry.getValue()) {
                if (dh.n(a5.le().get(com.google.android.gms.internal.b.dh.toString())).booleanValue()) continue;
                cs.d(this.ZN, entry.getKey()).i(a5);
            }
        }
    }

    private by<d.a> a(d.a a2, Set<String> set, dj object) {
        if (!a2.fX) {
            return new by<d.a>(a2, true);
        }
        switch (a2.type) {
            default: {
                bh.w("Unknown type: " + a2.type);
                return ZE;
            }
            case 2: {
                d.a a3 = cq.g(a2);
                a3.fO = new d.a[a2.fO.length];
                for (int i2 = 0; i2 < a2.fO.length; ++i2) {
                    by<d.a> by2 = this.a(a2.fO[i2], set, object.cd(i2));
                    if (by2 == ZE) {
                        return ZE;
                    }
                    a3.fO[i2] = by2.getObject();
                }
                return new by<d.a>(a3, false);
            }
            case 3: {
                d.a a4 = cq.g(a2);
                if (a2.fP.length != a2.fQ.length) {
                    bh.w("Invalid serving value: " + a2.toString());
                    return ZE;
                }
                a4.fP = new d.a[a2.fP.length];
                a4.fQ = new d.a[a2.fP.length];
                for (int i3 = 0; i3 < a2.fP.length; ++i3) {
                    by<d.a> by3 = this.a(a2.fP[i3], set, object.ce(i3));
                    by<d.a> by4 = this.a(a2.fQ[i3], set, object.cf(i3));
                    if (by3 == ZE || by4 == ZE) {
                        return ZE;
                    }
                    a4.fP[i3] = by3.getObject();
                    a4.fQ[i3] = by4.getObject();
                }
                return new by<d.a>(a4, false);
            }
            case 4: {
                if (set.contains(a2.fR)) {
                    bh.w("Macro cycle detected.  Current macro reference: " + a2.fR + "." + "  Previous macro references: " + set.toString() + ".");
                    return ZE;
                }
                set.add(a2.fR);
                object = dk.a(this.a(a2.fR, set, object.kP()), a2.fW);
                set.remove(a2.fR);
                return object;
            }
            case 7: 
        }
        d.a a5 = cq.g(a2);
        a5.fV = new d.a[a2.fV.length];
        for (int i4 = 0; i4 < a2.fV.length; ++i4) {
            by<d.a> by5 = this.a(a2.fV[i4], set, object.cg(i4));
            if (by5 == ZE) {
                return ZE;
            }
            a5.fV[i4] = by5.getObject();
        }
        return new by<d.a>(a5, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private by<d.a> a(String string2, Set<String> set, bj by2) {
        ++this.ZP;
        Object object = this.ZL.get(string2);
        if (object != null && !this.ZG.kA()) {
            this.a(object.lf(), set);
            --this.ZP;
            return object.lz();
        }
        object = this.ZN.get(string2);
        if (object == null) {
            bh.w(this.ly() + "Invalid macro: " + string2);
            --this.ZP;
            return ZE;
        }
        by<Set<cq.a>> by3 = this.a(string2, object.lA(), object.lB(), object.lC(), object.lE(), object.lD(), set, by2.kr());
        if (by3.getObject().isEmpty()) {
            object = object.lF();
        } else {
            if (by3.getObject().size() > 1) {
                bh.z(this.ly() + "Multiple macros active for macroName " + string2);
            }
            object = by3.getObject().iterator().next();
        }
        if (object == null) {
            --this.ZP;
            return ZE;
        }
        by2 = this.a(this.ZJ, (cq.a)object, set, by2.kG());
        boolean bl2 = by3.kQ() && by2.kQ();
        by2 = by2 == ZE ? ZE : new by<d.a>(by2.getObject(), bl2);
        object = object.lf();
        if (by2.kQ()) {
            this.ZL.e(string2, new b(by2, (d.a)object));
        }
        this.a((d.a)object, set);
        --this.ZP;
        return by2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private by<d.a> a(Map<String, aj> by2, cq.a a2, Set<String> set, cj cj2) {
        boolean bl2 = true;
        Object object = a2.le().get(com.google.android.gms.internal.b.cx.toString());
        if (object == null) {
            bh.w("No function id in properties");
            return ZE;
        }
        object = object.fS;
        aj aj2 = (aj)by2.get(object);
        if (aj2 == null) {
            bh.w((String)object + " has no backing implementation.");
            return ZE;
        }
        by2 = this.ZK.get(a2);
        if (by2 != null) {
            if (!this.ZG.kA()) return by2;
        }
        by2 = new HashMap();
        Iterator<Map.Entry<String, d.a>> iterator = a2.le().entrySet().iterator();
        boolean bl3 = true;
        while (iterator.hasNext()) {
            Map.Entry<String, d.a> entry = iterator.next();
            by<d.a> by3 = cj2.bH(entry.getKey());
            by3 = this.a(entry.getValue(), set, by3.e(entry.getValue()));
            if (by3 == ZE) {
                return ZE;
            }
            if (by3.kQ()) {
                a2.a(entry.getKey(), (d.a)by3.getObject());
            } else {
                bl3 = false;
            }
            by2.put(entry.getKey(), by3.getObject());
        }
        if (!aj2.a(by2.keySet())) {
            bh.w("Incorrect keys for function " + (String)object + " required " + aj2.kC() + " had " + by2.keySet());
            return ZE;
        }
        if (!bl3 || !aj2.jX()) {
            bl2 = false;
        }
        by2 = new by<d.a>(aj2.x((Map<String, d.a>)((Object)by2)), bl2);
        if (bl2) {
            this.ZK.e(a2, by2);
        }
        cj2.d(by2.getObject());
        return by2;
    }

    private by<Set<cq.a>> a(Set<cq.e> object, Set<String> set, a a2, cr cr2) {
        HashSet<cq.a> hashSet = new HashSet<cq.a>();
        HashSet<cq.a> hashSet2 = new HashSet<cq.a>();
        object = object.iterator();
        boolean bl2 = true;
        while (object.hasNext()) {
            cm cm2;
            cq.e e2 = (cq.e)object.next();
            by<Boolean> by2 = this.a(e2, set, cm2 = cr2.kO());
            if (by2.getObject().booleanValue()) {
                a2.a(e2, hashSet, hashSet2, cm2);
            }
            if (bl2 && by2.kQ()) {
                bl2 = true;
                continue;
            }
            bl2 = false;
        }
        hashSet.removeAll(hashSet2);
        cr2.b(hashSet);
        return new by<Set<cq.a>>(hashSet, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(d.a iterator, Set<String> object) {
        if (iterator != null && (iterator = this.a((d.a)((Object)iterator), (Set<String>)object, new bw())) != ZE) {
            if ((iterator = dh.o((d.a)iterator.getObject())) instanceof Map) {
                iterator = (Map)((Object)iterator);
                this.WK.push((Map<String, Object>)((Object)iterator));
                return;
            }
            if (!(iterator instanceof List)) {
                bh.z("pushAfterEvaluate: value not a Map or List");
                return;
            }
            iterator = ((List)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (object instanceof Map) {
                    object = (Map)object;
                    this.WK.push((Map<String, Object>)object);
                    continue;
                }
                bh.z("pushAfterEvaluate: value not a Map");
            }
        }
    }

    private static void a(List<cq.a> list, List<String> list2, String string2) {
        if (list.size() != list2.size()) {
            bh.x("Invalid resource: imbalance of rule names of functions for " + string2 + " operation. Using default rule name instead");
        }
    }

    private static void a(Map<String, aj> map, aj aj2) {
        if (map.containsKey(aj2.kB())) {
            throw new IllegalArgumentException("Duplicate function type name: " + aj2.kB());
        }
        map.put(aj2.kB(), aj2);
    }

    private static c d(Map<String, c> map, String string2) {
        c c2;
        c c3 = c2 = map.get(string2);
        if (c2 == null) {
            c3 = new c();
            map.put(string2, c3);
        }
        return c3;
    }

    private static String h(cq.a a2) {
        return dh.j(a2.le().get(com.google.android.gms.internal.b.cI.toString()));
    }

    private String ly() {
        if (this.ZP <= 1) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.ZP));
        for (int i2 = 2; i2 < this.ZP; ++i2) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(": ");
        return stringBuilder.toString();
    }

    by<Boolean> a(cq.a object, Set<String> object2, cj cj2) {
        object = this.a(this.ZI, (cq.a)object, (Set<String>)object2, cj2);
        object2 = dh.n((d.a)object.getObject());
        cj2.d(dh.r(object2));
        return new by<Object>(object2, object.kQ());
    }

    by<Boolean> a(cq.e object, Set<String> set, cm cm2) {
        by<Boolean> by2 = object.ln().iterator();
        boolean bl2 = true;
        while (by2.hasNext()) {
            by<Boolean> by3 = this.a(by2.next(), set, cm2.kI());
            if (by3.getObject().booleanValue()) {
                cm2.f(dh.r(false));
                return new by<Boolean>(false, by3.kQ());
            }
            if (bl2 && by3.kQ()) {
                bl2 = true;
                continue;
            }
            bl2 = false;
        }
        object = object.lm().iterator();
        while (object.hasNext()) {
            by2 = this.a((cq.a)object.next(), set, cm2.kJ());
            if (!((Boolean)by2.getObject()).booleanValue()) {
                cm2.f(dh.r(false));
                return new by<Boolean>(false, by2.kQ());
            }
            if (bl2 && by2.kQ()) {
                bl2 = true;
                continue;
            }
            bl2 = false;
        }
        cm2.f(dh.r(true));
        return new by<Boolean>(true, bl2);
    }

    by<Set<cq.a>> a(String string2, Set<cq.e> set, final Map<cq.e, List<cq.a>> map, final Map<cq.e, List<String>> map2, final Map<cq.e, List<cq.a>> map3, final Map<cq.e, List<String>> map4, Set<String> set2, cr cr2) {
        return this.a(set, set2, new a(){

            @Override
            public void a(cq.e object, Set<cq.a> collection, Set<cq.a> set, cm cm2) {
                List list = (List)map.get(object);
                List list2 = (List)map2.get(object);
                if (list != null) {
                    collection.addAll(list);
                    cm2.kK().b(list, list2);
                }
                collection = (List)map3.get(object);
                object = (List)map4.get(object);
                if (collection != null) {
                    set.addAll(collection);
                    cm2.kL().b((List<cq.a>)collection, (List<String>)object);
                }
            }
        }, cr2);
    }

    by<Set<cq.a>> a(Set<cq.e> set, cr cr2) {
        return this.a(set, new HashSet<String>(), new a(){

            @Override
            public void a(cq.e e2, Set<cq.a> set, Set<cq.a> set2, cm cm2) {
                set.addAll(e2.lo());
                set2.addAll(e2.lp());
                cm2.kM().b(e2.lo(), e2.lt());
                cm2.kN().b(e2.lp(), e2.lu());
            }
        }, cr2);
    }

    void a(aj aj2) {
        cs.a(this.ZJ, aj2);
    }

    void b(aj aj2) {
        cs.a(this.ZH, aj2);
    }

    public by<d.a> bR(String object) {
        this.ZP = 0;
        af af2 = this.ZG.bA((String)object);
        object = this.a((String)object, new HashSet<String>(), af2.kx());
        af2.kz();
        return object;
    }

    void bS(String string2) {
        synchronized (this) {
            this.ZO = string2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bp(String object) {
        synchronized (this) {
            this.bS((String)object);
            object = this.ZG.bB((String)object);
            t t2 = object.ky();
            Iterator<cq.a> iterator = this.a(this.ZM, t2.kr()).getObject().iterator();
            do {
                if (!iterator.hasNext()) {
                    object.kz();
                    this.bS(null);
                    return;
                }
                cq.a a2 = iterator.next();
                this.a(this.ZH, a2, new HashSet<String>(), t2.kq());
            } while (true);
        }
    }

    void c(aj aj2) {
        cs.a(this.ZI, aj2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void e(List<c.i> object) {
        synchronized (this) {
            object = object.iterator();
            while (object.hasNext()) {
                c.i i2 = (c.i)object.next();
                if (i2.name == null || !i2.name.startsWith("gaExperiment:")) {
                    bh.y("Ignored supplemental: " + i2);
                    continue;
                }
                ai.a(this.WK, i2);
            }
            return;
        }
    }

    String lx() {
        synchronized (this) {
            String string2 = this.ZO;
            return string2;
        }
    }

    static interface a {
        public void a(cq.e var1, Set<cq.a> var2, Set<cq.a> var3, cm var4);
    }

    private static class b {
        private by<d.a> ZV;
        private d.a Zq;

        public b(by<d.a> by2, d.a a2) {
            this.ZV = by2;
            this.Zq = a2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int getSize() {
            int n2;
            int n3 = this.ZV.getObject().mF();
            if (this.Zq == null) {
                n2 = 0;
                do {
                    return n2 + n3;
                    break;
                } while (true);
            }
            n2 = this.Zq.mF();
            return n2 + n3;
        }

        public d.a lf() {
            return this.Zq;
        }

        public by<d.a> lz() {
            return this.ZV;
        }
    }

    private static class c {
        private final Set<cq.e> ZM = new HashSet<cq.e>();
        private final Map<cq.e, List<cq.a>> ZW = new HashMap<cq.e, List<cq.a>>();
        private final Map<cq.e, List<cq.a>> ZX = new HashMap<cq.e, List<cq.a>>();
        private final Map<cq.e, List<String>> ZY = new HashMap<cq.e, List<String>>();
        private final Map<cq.e, List<String>> ZZ = new HashMap<cq.e, List<String>>();
        private cq.a aaa;

        public void a(cq.e e2, cq.a a2) {
            List<cq.a> list;
            List<cq.a> list2 = list = this.ZW.get(e2);
            if (list == null) {
                list2 = new ArrayList<cq.a>();
                this.ZW.put(e2, list2);
            }
            list2.add(a2);
        }

        public void a(cq.e e2, String string2) {
            List<String> list;
            List<String> list2 = list = this.ZY.get(e2);
            if (list == null) {
                list2 = new ArrayList<String>();
                this.ZY.put(e2, list2);
            }
            list2.add(string2);
        }

        public void b(cq.e e2) {
            this.ZM.add(e2);
        }

        public void b(cq.e e2, cq.a a2) {
            List<cq.a> list;
            List<cq.a> list2 = list = this.ZX.get(e2);
            if (list == null) {
                list2 = new ArrayList<cq.a>();
                this.ZX.put(e2, list2);
            }
            list2.add(a2);
        }

        public void b(cq.e e2, String string2) {
            List<String> list;
            List<String> list2 = list = this.ZZ.get(e2);
            if (list == null) {
                list2 = new ArrayList<String>();
                this.ZZ.put(e2, list2);
            }
            list2.add(string2);
        }

        public void i(cq.a a2) {
            this.aaa = a2;
        }

        public Set<cq.e> lA() {
            return this.ZM;
        }

        public Map<cq.e, List<cq.a>> lB() {
            return this.ZW;
        }

        public Map<cq.e, List<String>> lC() {
            return this.ZY;
        }

        public Map<cq.e, List<String>> lD() {
            return this.ZZ;
        }

        public Map<cq.e, List<cq.a>> lE() {
            return this.ZX;
        }

        public cq.a lF() {
            return this.aaa;
        }
    }

}

