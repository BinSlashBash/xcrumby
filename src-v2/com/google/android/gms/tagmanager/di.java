/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.b;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.de;
import com.google.android.gms.tagmanager.df;
import com.google.android.gms.tagmanager.dh;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class di
extends df {
    private static final String ID = a.aF.toString();
    private static final String aaO = b.aV.toString();
    private static final String aaP = b.be.toString();
    private static final String aaQ = b.bd.toString();
    private static final String aaR = b.eg.toString();
    private static final String aaS = b.ei.toString();
    private static final String aaT = b.ek.toString();
    private static Map<String, String> aaU;
    private static Map<String, String> aaV;
    private final DataLayer WK;
    private final Set<String> aaW;
    private final de aaX;

    public di(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new de(context));
    }

    di(Context context, DataLayer dataLayer, de de2) {
        super(ID, new String[0]);
        this.WK = dataLayer;
        this.aaX = de2;
        this.aaW = new HashSet<String>();
        this.aaW.add("");
        this.aaW.add("0");
        this.aaW.add("false");
    }

    private Map<String, String> H(Map<String, d.a> hashMap) {
        if ((hashMap = hashMap.get(aaS)) != null) {
            return this.c((d.a)((Object)hashMap));
        }
        if (aaU == null) {
            hashMap = new HashMap<String, String>();
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

    private Map<String, String> I(Map<String, d.a> hashMap) {
        if ((hashMap = hashMap.get(aaT)) != null) {
            return this.c((d.a)((Object)hashMap));
        }
        if (aaV == null) {
            hashMap = new HashMap<String, String>();
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(Tracker tracker, Map<String, d.a> object) {
        LinkedList<Object> linkedList;
        block9 : {
            Iterator iterator;
            String string2 = this.cc("transactionId");
            if (string2 == null) {
                bh.w("Cannot find transactionId in data layer.");
                return;
            }
            linkedList = new LinkedList<Object>();
            try {
                iterator = this.p(object.get(aaQ));
                iterator.put((String)"&t", (String)"transaction");
                for (Object object2 : this.H((Map<String, d.a>)object).entrySet()) {
                    this.b((Map<String, String>)((Object)iterator), object2.getValue(), this.cc(object2.getKey()));
                }
                linkedList.add(iterator);
                iterator = this.lU();
                if (iterator == null) break block9;
                iterator = iterator.iterator();
            }
            catch (IllegalArgumentException var1_2) {
                bh.b("Unable to send transaction", var1_2);
                return;
            }
            while (iterator.hasNext()) {
                Object object2;
                Map map = (Map)iterator.next();
                if (map.get("name") == null) {
                    bh.w("Unable to send transaction item hit due to missing 'name' field.");
                    return;
                }
                object2 = this.p((d.a)object.get(aaQ));
                object2.put("&t", "item");
                object2.put("&ti", string2);
                for (Map.Entry<String, String> entry : this.I((Map<String, d.a>)object).entrySet()) {
                    this.b((Map<String, String>)object2, entry.getValue(), (String)map.get(entry.getKey()));
                }
                linkedList.add(object2);
            }
        }
        object = linkedList.iterator();
        while (object.hasNext()) {
            tracker.send((Map)object.next());
        }
    }

    private void b(Map<String, String> map, String string2, String string3) {
        if (string3 != null) {
            map.put(string2, string3);
        }
    }

    private Map<String, String> c(d.a linkedHashMap) {
        if (!((linkedHashMap = dh.o(linkedHashMap)) instanceof Map)) {
            return null;
        }
        Map map = linkedHashMap;
        linkedHashMap = new LinkedHashMap<String, String>();
        for (Map.Entry entry : map.entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private String cc(String object) {
        if ((object = this.WK.get((String)object)) == null) {
            return null;
        }
        return object.toString();
    }

    private boolean e(Map<String, d.a> object, String string2) {
        if ((object = object.get(string2)) == null) {
            return false;
        }
        return dh.n((d.a)object);
    }

    private List<Map<String, String>> lU() {
        Object object = this.WK.get("transactionProducts");
        if (object == null) {
            return null;
        }
        if (!(object instanceof List)) {
            throw new IllegalArgumentException("transactionProducts should be of type List.");
        }
        Iterator iterator = ((List)object).iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof Map) continue;
            throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
        }
        return (List)object;
    }

    private Map<String, String> p(d.a object) {
        if (object == null) {
            return new HashMap<String, String>();
        }
        if ((object = this.c((d.a)object)) == null) {
            return new HashMap<String, String>();
        }
        String string2 = (String)object.get("&aip");
        if (string2 != null && this.aaW.contains(string2.toLowerCase())) {
            object.remove("&aip");
        }
        return object;
    }

    @Override
    public void z(Map<String, d.a> map) {
        Tracker tracker = this.aaX.bU("_GTM_DEFAULT_TRACKER_");
        if (this.e(map, aaP)) {
            tracker.send(this.p(map.get(aaQ)));
            return;
        }
        if (this.e(map, aaR)) {
            this.a(tracker, map);
            return;
        }
        bh.z("Ignoring unknown tag.");
    }
}

