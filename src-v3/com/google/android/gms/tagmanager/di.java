package com.google.android.gms.tagmanager;

import android.content.Context;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class di extends df {
    private static final String ID;
    private static final String aaO;
    private static final String aaP;
    private static final String aaQ;
    private static final String aaR;
    private static final String aaS;
    private static final String aaT;
    private static Map<String, String> aaU;
    private static Map<String, String> aaV;
    private final DataLayer WK;
    private final Set<String> aaW;
    private final de aaX;

    static {
        ID = C0321a.UNIVERSAL_ANALYTICS.toString();
        aaO = C0325b.ACCOUNT.toString();
        aaP = C0325b.ANALYTICS_PASS_THROUGH.toString();
        aaQ = C0325b.ANALYTICS_FIELDS.toString();
        aaR = C0325b.TRACK_TRANSACTION.toString();
        aaS = C0325b.TRANSACTION_DATALAYER_MAP.toString();
        aaT = C0325b.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    }

    public di(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new de(context));
    }

    di(Context context, DataLayer dataLayer, de deVar) {
        super(ID, new String[0]);
        this.WK = dataLayer;
        this.aaX = deVar;
        this.aaW = new HashSet();
        this.aaW.add(UnsupportedUrlFragment.DISPLAY_NAME);
        this.aaW.add("0");
        this.aaW.add("false");
    }

    private Map<String, String> m3218H(Map<String, C1367a> map) {
        C1367a c1367a = (C1367a) map.get(aaS);
        if (c1367a != null) {
            return m3222c(c1367a);
        }
        if (aaU == null) {
            Map hashMap = new HashMap();
            hashMap.put("transactionId", "&ti");
            hashMap.put("transactionAffiliation", "&ta");
            hashMap.put("transactionTax", "&tt");
            hashMap.put("transactionShipping", "&ts");
            hashMap.put("transactionTotal", "&tr");
            hashMap.put("transactionCurrency", "&cu");
            aaU = hashMap;
        }
        return aaU;
    }

    private Map<String, String> m3219I(Map<String, C1367a> map) {
        C1367a c1367a = (C1367a) map.get(aaT);
        if (c1367a != null) {
            return m3222c(c1367a);
        }
        if (aaV == null) {
            Map hashMap = new HashMap();
            hashMap.put("name", "&in");
            hashMap.put("sku", "&ic");
            hashMap.put("category", "&iv");
            hashMap.put("price", "&ip");
            hashMap.put("quantity", "&iq");
            hashMap.put("currency", "&cu");
            aaV = hashMap;
        }
        return aaV;
    }

    private void m3220a(Tracker tracker, Map<String, C1367a> map) {
        String cc = cc("transactionId");
        if (cc == null) {
            bh.m1384w("Cannot find transactionId in data layer.");
            return;
        }
        List<Map> linkedList = new LinkedList();
        try {
            Map p = m3224p((C1367a) map.get(aaQ));
            p.put("&t", "transaction");
            for (Entry entry : m3218H(map).entrySet()) {
                m3221b(p, (String) entry.getValue(), cc((String) entry.getKey()));
            }
            linkedList.add(p);
            List<Map> lU = lU();
            if (lU != null) {
                for (Map map2 : lU) {
                    if (map2.get("name") == null) {
                        bh.m1384w("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map p2 = m3224p((C1367a) map.get(aaQ));
                    p2.put("&t", "item");
                    p2.put("&ti", cc);
                    for (Entry entry2 : m3219I(map).entrySet()) {
                        m3221b(p2, (String) entry2.getValue(), (String) map2.get(entry2.getKey()));
                    }
                    linkedList.add(p2);
                }
            }
            for (Map map22 : linkedList) {
                tracker.send(map22);
            }
        } catch (Throwable e) {
            bh.m1381b("Unable to send transaction", e);
        }
    }

    private void m3221b(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private Map<String, String> m3222c(C1367a c1367a) {
        Object o = dh.m1468o(c1367a);
        if (!(o instanceof Map)) {
            return null;
        }
        Map map = (Map) o;
        Map<String, String> linkedHashMap = new LinkedHashMap();
        for (Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private String cc(String str) {
        Object obj = this.WK.get(str);
        return obj == null ? null : obj.toString();
    }

    private boolean m3223e(Map<String, C1367a> map, String str) {
        C1367a c1367a = (C1367a) map.get(str);
        return c1367a == null ? false : dh.m1466n(c1367a).booleanValue();
    }

    private List<Map<String, String>> lU() {
        Object obj = this.WK.get("transactionProducts");
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            for (Object obj2 : (List) obj) {
                if (!(obj2 instanceof Map)) {
                    throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
                }
            }
            return (List) obj;
        }
        throw new IllegalArgumentException("transactionProducts should be of type List.");
    }

    private Map<String, String> m3224p(C1367a c1367a) {
        if (c1367a == null) {
            return new HashMap();
        }
        Map<String, String> c = m3222c(c1367a);
        if (c == null) {
            return new HashMap();
        }
        String str = (String) c.get("&aip");
        if (str != null && this.aaW.contains(str.toLowerCase())) {
            c.remove("&aip");
        }
        return c;
    }

    public void m3225z(Map<String, C1367a> map) {
        Tracker bU = this.aaX.bU("_GTM_DEFAULT_TRACKER_");
        if (m3223e(map, aaP)) {
            bU.send(m3224p((C1367a) map.get(aaQ)));
        } else if (m3223e(map, aaR)) {
            m3220a(bU, map);
        } else {
            bh.m1387z("Ignoring unknown tag.");
        }
    }
}
