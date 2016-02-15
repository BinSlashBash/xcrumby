/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class dh {
    private static final Object aaF = null;
    private static Long aaG = new Long(0);
    private static Double aaH = new Double(0.0);
    private static dg aaI = dg.w(0);
    private static String aaJ = new String("");
    private static Boolean aaK = new Boolean(false);
    private static List<Object> aaL = new ArrayList<Object>(0);
    private static Map<Object, Object> aaM = new HashMap<Object, Object>();
    private static d.a aaN = dh.r(aaJ);

    public static d.a bX(String string2) {
        d.a a2 = new d.a();
        a2.type = 5;
        a2.fS = string2;
        return a2;
    }

    private static dg bY(String string2) {
        try {
            dg dg2 = dg.bW(string2);
            return dg2;
        }
        catch (NumberFormatException var1_2) {
            bh.w("Failed to convert '" + string2 + "' to a number.");
            return aaI;
        }
    }

    private static Long bZ(String object) {
        if ((object = dh.bY((String)object)) == aaI) {
            return aaG;
        }
        return object.longValue();
    }

    private static Double ca(String object) {
        if ((object = dh.bY((String)object)) == aaI) {
            return aaH;
        }
        return object.doubleValue();
    }

    private static Boolean cb(String string2) {
        if ("true".equalsIgnoreCase(string2)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(string2)) {
            return Boolean.FALSE;
        }
        return aaK;
    }

    private static double getDouble(Object object) {
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        bh.w("getDouble received non-Number");
        return 0.0;
    }

    public static String j(d.a a2) {
        return dh.m(dh.o(a2));
    }

    public static dg k(d.a a2) {
        return dh.n(dh.o(a2));
    }

    public static Long l(d.a a2) {
        return dh.o(dh.o(a2));
    }

    public static Object lN() {
        return aaF;
    }

    public static Long lO() {
        return aaG;
    }

    public static Double lP() {
        return aaH;
    }

    public static Boolean lQ() {
        return aaK;
    }

    public static dg lR() {
        return aaI;
    }

    public static String lS() {
        return aaJ;
    }

    public static d.a lT() {
        return aaN;
    }

    public static Double m(d.a a2) {
        return dh.p(dh.o(a2));
    }

    public static String m(Object object) {
        if (object == null) {
            return aaJ;
        }
        return object.toString();
    }

    public static dg n(Object object) {
        if (object instanceof dg) {
            return (dg)object;
        }
        if (dh.t(object)) {
            return dg.w(dh.u(object));
        }
        if (dh.s(object)) {
            return dg.a(dh.getDouble(object));
        }
        return dh.bY(dh.m(object));
    }

    public static Boolean n(d.a a2) {
        return dh.q(dh.o(a2));
    }

    public static Long o(Object object) {
        if (dh.t(object)) {
            return dh.u(object);
        }
        return dh.bZ(dh.m(object));
    }

    public static Object o(d.a arra) {
        int n2 = 0;
        int n3 = 0;
        if (arra == null) {
            return aaF;
        }
        switch (arra.type) {
            int n4;
            default: {
                bh.w("Failed to convert a value of type: " + arra.type);
                return aaF;
            }
            case 1: {
                return arra.fN;
            }
            case 2: {
                ArrayList<Object> arrayList = new ArrayList<Object>(arra.fO.length);
                arra = arra.fO;
                n3 = arra.length;
                for (n4 = 0; n4 < n3; ++n4) {
                    Object object = dh.o(arra[n4]);
                    if (object == aaF) {
                        return aaF;
                    }
                    arrayList.add(object);
                }
                return arrayList;
            }
            case 3: {
                if (arra.fP.length != arra.fQ.length) {
                    bh.w("Converting an invalid value to object: " + arra.toString());
                    return aaF;
                }
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>(arra.fQ.length);
                for (n4 = n2; n4 < arra.fP.length; ++n4) {
                    Object object = dh.o(arra.fP[n4]);
                    Object object2 = dh.o(arra.fQ[n4]);
                    if (object == aaF || object2 == aaF) {
                        return aaF;
                    }
                    hashMap.put(object, object2);
                }
                return hashMap;
            }
            case 4: {
                bh.w("Trying to convert a macro reference to object");
                return aaF;
            }
            case 5: {
                bh.w("Trying to convert a function id to object");
                return aaF;
            }
            case 6: {
                return arra.fT;
            }
            case 7: {
                StringBuffer stringBuffer = new StringBuffer();
                arra = arra.fV;
                n2 = arra.length;
                for (n4 = n3; n4 < n2; ++n4) {
                    String string2 = dh.j(arra[n4]);
                    if (string2 == aaJ) {
                        return aaF;
                    }
                    stringBuffer.append(string2);
                }
                return stringBuffer.toString();
            }
            case 8: 
        }
        return arra.fU;
    }

    public static Double p(Object object) {
        if (dh.s(object)) {
            return dh.getDouble(object);
        }
        return dh.ca(dh.m(object));
    }

    public static Boolean q(Object object) {
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        return dh.cb(dh.m(object));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static d.a r(Object var0) {
        var1_1 = false;
        var2_2 = new d.a();
        if (var0 instanceof d.a) {
            return (d.a)var0;
        }
        if (!(var0 instanceof String)) ** GOTO lbl9
        var2_2.type = 1;
        var2_2.fN = (String)var0;
        ** GOTO lbl40
lbl9: // 1 sources:
        if (!(var0 instanceof List)) ** GOTO lbl16
        var2_2.type = 2;
        var3_3 = (List)var0;
        var0 = new ArrayList<E>(var3_3.size());
        var3_3 = var3_3.iterator();
        var1_1 = false;
        ** GOTO lbl43
lbl16: // 1 sources:
        if (!(var0 instanceof Map)) ** GOTO lbl24
        var2_2.type = 3;
        var4_6 = ((Map)var0).entrySet();
        var0 = new ArrayList<E>(var4_6.size());
        var3_4 = new ArrayList<Object>(var4_6.size());
        var4_6 = var4_6.iterator();
        var1_1 = false;
        ** GOTO lbl52
lbl24: // 1 sources:
        if (dh.s(var0)) {
            var2_2.type = 1;
            var2_2.fN = var0.toString();
        } else if (dh.t(var0)) {
            var2_2.type = 6;
            var2_2.fT = dh.u(var0);
        } else if (var0 instanceof Boolean) {
            var2_2.type = 8;
            var2_2.fU = (Boolean)var0;
        } else {
            var2_2 = new StringBuilder().append("Converting to Value from unknown object type: ");
            var0 = var0 == null ? "null" : var0.getClass().toString();
            bh.w(var2_2.append((String)var0).toString());
            return dh.aaN;
        }
lbl40: // 6 sources:
        do {
            var2_2.fX = var1_1;
            return var2_2;
            break;
        } while (true);
lbl43: // 2 sources:
        while (var3_3.hasNext()) {
            var4_5 = dh.r(var3_3.next());
            if (var4_5 == dh.aaN) {
                return dh.aaN;
            }
            var1_1 = var1_1 != false || var4_5.fX != false;
            var0.add(var4_5);
        }
        var2_2.fO = var0.toArray(new d.a[0]);
        ** GOTO lbl40
lbl52: // 2 sources:
        while (var4_6.hasNext()) {
            var6_8 = (Map.Entry)var4_6.next();
            var5_7 = dh.r(var6_8.getKey());
            var6_8 = dh.r(var6_8.getValue());
            if (var5_7 == dh.aaN) return dh.aaN;
            if (var6_8 == dh.aaN) {
                return dh.aaN;
            }
            var1_1 = var1_1 != false || var5_7.fX != false || var6_8.fX != false;
            var0.add(var5_7);
            var3_4.add(var6_8);
        }
        var2_2.fP = var0.toArray(new d.a[0]);
        var2_2.fQ = var3_4.toArray(new d.a[0]);
        ** while (true)
    }

    private static boolean s(Object object) {
        if (object instanceof Double || object instanceof Float || object instanceof dg && ((dg)object).lI()) {
            return true;
        }
        return false;
    }

    private static boolean t(Object object) {
        if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof dg && ((dg)object).lJ()) {
            return true;
        }
        return false;
    }

    private static long u(Object object) {
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        bh.w("getInt64 received non-Number");
        return 0;
    }
}

