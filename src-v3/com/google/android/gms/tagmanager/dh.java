package com.google.android.gms.tagmanager;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class dh {
    private static final Object aaF;
    private static Long aaG;
    private static Double aaH;
    private static dg aaI;
    private static String aaJ;
    private static Boolean aaK;
    private static List<Object> aaL;
    private static Map<Object, Object> aaM;
    private static C1367a aaN;

    static {
        aaF = null;
        aaG = new Long(0);
        aaH = new Double(0.0d);
        aaI = dg.m1458w(0);
        aaJ = new String(UnsupportedUrlFragment.DISPLAY_NAME);
        aaK = new Boolean(false);
        aaL = new ArrayList(0);
        aaM = new HashMap();
        aaN = m1471r(aaJ);
    }

    public static C1367a bX(String str) {
        C1367a c1367a = new C1367a();
        c1367a.type = 5;
        c1367a.fS = str;
        return c1367a;
    }

    private static dg bY(String str) {
        try {
            return dg.bW(str);
        } catch (NumberFormatException e) {
            bh.m1384w("Failed to convert '" + str + "' to a number.");
            return aaI;
        }
    }

    private static Long bZ(String str) {
        dg bY = bY(str);
        return bY == aaI ? aaG : Long.valueOf(bY.longValue());
    }

    private static Double ca(String str) {
        dg bY = bY(str);
        return bY == aaI ? aaH : Double.valueOf(bY.doubleValue());
    }

    private static Boolean cb(String str) {
        return "true".equalsIgnoreCase(str) ? Boolean.TRUE : "false".equalsIgnoreCase(str) ? Boolean.FALSE : aaK;
    }

    private static double getDouble(Object o) {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        bh.m1384w("getDouble received non-Number");
        return 0.0d;
    }

    public static String m1460j(C1367a c1367a) {
        return m1464m(m1468o(c1367a));
    }

    public static dg m1461k(C1367a c1367a) {
        return m1465n(m1468o(c1367a));
    }

    public static Long m1462l(C1367a c1367a) {
        return m1467o(m1468o(c1367a));
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

    public static C1367a lT() {
        return aaN;
    }

    public static Double m1463m(C1367a c1367a) {
        return m1469p(m1468o(c1367a));
    }

    public static String m1464m(Object obj) {
        return obj == null ? aaJ : obj.toString();
    }

    public static dg m1465n(Object obj) {
        return obj instanceof dg ? (dg) obj : m1473t(obj) ? dg.m1458w(m1474u(obj)) : m1472s(obj) ? dg.m1457a(Double.valueOf(getDouble(obj))) : bY(m1464m(obj));
    }

    public static Boolean m1466n(C1367a c1367a) {
        return m1470q(m1468o(c1367a));
    }

    public static Long m1467o(Object obj) {
        return m1473t(obj) ? Long.valueOf(m1474u(obj)) : bZ(m1464m(obj));
    }

    public static Object m1468o(C1367a c1367a) {
        int i = 0;
        if (c1367a == null) {
            return aaF;
        }
        C1367a[] c1367aArr;
        int length;
        switch (c1367a.type) {
            case Std.STD_FILE /*1*/:
                return c1367a.fN;
            case Std.STD_URL /*2*/:
                ArrayList arrayList = new ArrayList(c1367a.fO.length);
                c1367aArr = c1367a.fO;
                length = c1367aArr.length;
                while (i < length) {
                    Object o = m1468o(c1367aArr[i]);
                    if (o == aaF) {
                        return aaF;
                    }
                    arrayList.add(o);
                    i++;
                }
                return arrayList;
            case Std.STD_URI /*3*/:
                if (c1367a.fP.length != c1367a.fQ.length) {
                    bh.m1384w("Converting an invalid value to object: " + c1367a.toString());
                    return aaF;
                }
                Map hashMap = new HashMap(c1367a.fQ.length);
                while (i < c1367a.fP.length) {
                    Object o2 = m1468o(c1367a.fP[i]);
                    Object o3 = m1468o(c1367a.fQ[i]);
                    if (o2 == aaF || o3 == aaF) {
                        return aaF;
                    }
                    hashMap.put(o2, o3);
                    i++;
                }
                return hashMap;
            case Std.STD_CLASS /*4*/:
                bh.m1384w("Trying to convert a macro reference to object");
                return aaF;
            case Std.STD_JAVA_TYPE /*5*/:
                bh.m1384w("Trying to convert a function id to object");
                return aaF;
            case Std.STD_CURRENCY /*6*/:
                return Long.valueOf(c1367a.fT);
            case Std.STD_PATTERN /*7*/:
                StringBuffer stringBuffer = new StringBuffer();
                c1367aArr = c1367a.fV;
                length = c1367aArr.length;
                while (i < length) {
                    String j = m1460j(c1367aArr[i]);
                    if (j == aaJ) {
                        return aaF;
                    }
                    stringBuffer.append(j);
                    i++;
                }
                return stringBuffer.toString();
            case Std.STD_LOCALE /*8*/:
                return Boolean.valueOf(c1367a.fU);
            default:
                bh.m1384w("Failed to convert a value of type: " + c1367a.type);
                return aaF;
        }
    }

    public static Double m1469p(Object obj) {
        return m1472s(obj) ? Double.valueOf(getDouble(obj)) : ca(m1464m(obj));
    }

    public static Boolean m1470q(Object obj) {
        return obj instanceof Boolean ? (Boolean) obj : cb(m1464m(obj));
    }

    public static C1367a m1471r(Object obj) {
        boolean z = false;
        C1367a c1367a = new C1367a();
        if (obj instanceof C1367a) {
            return (C1367a) obj;
        }
        if (obj instanceof String) {
            c1367a.type = 1;
            c1367a.fN = (String) obj;
        } else if (obj instanceof List) {
            c1367a.type = 2;
            List<Object> list = (List) obj;
            r5 = new ArrayList(list.size());
            r1 = false;
            for (Object r : list) {
                C1367a r2 = m1471r(r);
                if (r2 == aaN) {
                    return aaN;
                }
                r0 = r1 || r2.fX;
                r5.add(r2);
                r1 = r0;
            }
            c1367a.fO = (C1367a[]) r5.toArray(new C1367a[0]);
            z = r1;
        } else if (obj instanceof Map) {
            c1367a.type = 3;
            Set<Entry> entrySet = ((Map) obj).entrySet();
            r5 = new ArrayList(entrySet.size());
            List arrayList = new ArrayList(entrySet.size());
            r1 = false;
            for (Entry entry : entrySet) {
                C1367a r3 = m1471r(entry.getKey());
                C1367a r4 = m1471r(entry.getValue());
                if (r3 == aaN || r4 == aaN) {
                    return aaN;
                }
                r0 = r1 || r3.fX || r4.fX;
                r5.add(r3);
                arrayList.add(r4);
                r1 = r0;
            }
            c1367a.fP = (C1367a[]) r5.toArray(new C1367a[0]);
            c1367a.fQ = (C1367a[]) arrayList.toArray(new C1367a[0]);
            z = r1;
        } else if (m1472s(obj)) {
            c1367a.type = 1;
            c1367a.fN = obj.toString();
        } else if (m1473t(obj)) {
            c1367a.type = 6;
            c1367a.fT = m1474u(obj);
        } else if (obj instanceof Boolean) {
            c1367a.type = 8;
            c1367a.fU = ((Boolean) obj).booleanValue();
        } else {
            bh.m1384w("Converting to Value from unknown object type: " + (obj == null ? "null" : obj.getClass().toString()));
            return aaN;
        }
        c1367a.fX = z;
        return c1367a;
    }

    private static boolean m1472s(Object obj) {
        return (obj instanceof Double) || (obj instanceof Float) || ((obj instanceof dg) && ((dg) obj).lI());
    }

    private static boolean m1473t(Object obj) {
        return (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || ((obj instanceof dg) && ((dg) obj).lJ());
    }

    private static long m1474u(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        bh.m1384w("getInt64 received non-Number");
        return 0;
    }
}
