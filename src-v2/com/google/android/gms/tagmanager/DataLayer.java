/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.bh;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    static final String[] Xp = "gtm.lifetime".toString().split("\\.");
    private static final Pattern Xq = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<b, Integer> Xr;
    private final Map<String, Object> Xs;
    private final ReentrantLock Xt;
    private final LinkedList<Map<String, Object>> Xu;
    private final c Xv;
    private final CountDownLatch Xw;

    DataLayer() {
        this(new c(){

            @Override
            public void a(c.a a2) {
                a2.a(new ArrayList<a>());
            }

            @Override
            public void a(List<a> list, long l2) {
            }

            @Override
            public void bx(String string2) {
            }
        });
    }

    DataLayer(c c2) {
        this.Xv = c2;
        this.Xr = new ConcurrentHashMap();
        this.Xs = new HashMap<String, Object>();
        this.Xt = new ReentrantLock();
        this.Xu = new LinkedList();
        this.Xw = new CountDownLatch(1);
        this.ko();
    }

    private void A(Map<String, Object> map) {
        this.Xt.lock();
        try {
            this.Xu.offer(map);
            if (this.Xt.getHoldCount() == 1) {
                this.kp();
            }
            this.B(map);
            return;
        }
        finally {
            this.Xt.unlock();
        }
    }

    private void B(Map<String, Object> object) {
        Long l2 = this.C((Map<String, Object>)object);
        if (l2 == null) {
            return;
        }
        object = this.E((Map<String, Object>)object);
        object.remove("gtm.lifetime");
        this.Xv.a((List<a>)object, l2);
    }

    private Long C(Map<String, Object> object) {
        if ((object = this.D((Map<String, Object>)object)) == null) {
            return null;
        }
        return DataLayer.bw(object.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object D(Map<String, Object> object) {
        String[] arrstring = Xp;
        int n2 = arrstring.length;
        int n3 = 0;
        do {
            Object object2 = object;
            if (n3 >= n2) return object2;
            object2 = arrstring[n3];
            if (!(object instanceof Map)) {
                return null;
            }
            object = ((Map)object).get(object2);
            ++n3;
        } while (true);
    }

    private List<a> E(Map<String, Object> map) {
        ArrayList<a> arrayList = new ArrayList<a>();
        this.a(map, "", arrayList);
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void F(Map<String, Object> map) {
        Map<String, Object> map2 = this.Xs;
        synchronized (map2) {
            Iterator<String> iterator = map.keySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT [2, 3, 4] lbl6 : MonitorExitStatement: MONITOREXIT : var2_2
                    this.G(map);
                    return;
                }
                String string2 = iterator.next();
                this.a(this.c(string2, map.get(string2)), this.Xs);
            } while (true);
        }
    }

    private void G(Map<String, Object> map) {
        Iterator iterator = this.Xr.keySet().iterator();
        while (iterator.hasNext()) {
            ((b)iterator.next()).y(map);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(Map<String, Object> object, String string2, Collection<a> collection) {
        Iterator iterator = object.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            StringBuilder stringBuilder = new StringBuilder().append(string2);
            object = string2.length() == 0 ? "" : ".";
            object = stringBuilder.append((String)object).append((String)entry.getKey()).toString();
            if (entry.getValue() instanceof Map) {
                this.a((Map)entry.getValue(), (String)object, collection);
                continue;
            }
            if (object.equals("gtm.lifetime")) continue;
            collection.add(new a((String)object, entry.getValue()));
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Long bw(String string2) {
        long l2;
        Object object = Xq.matcher(string2);
        if (!object.matches()) {
            bh.x("unknown _lifetime: " + string2);
            return null;
        }
        try {
            l2 = Long.parseLong(object.group(1));
        }
        catch (NumberFormatException var4_3) {
            bh.z("illegal number in _lifetime value: " + string2);
            l2 = 0;
        }
        if (l2 <= 0) {
            bh.x("non-positive _lifetime: " + string2);
            return null;
        }
        if ((object = object.group(2)).length() == 0) {
            return l2;
        }
        switch (object.charAt(0)) {
            default: {
                bh.z("unknown units in _lifetime: " + string2);
                return null;
            }
            case 's': {
                return l2 * 1000;
            }
            case 'm': {
                return l2 * 1000 * 60;
            }
            case 'h': {
                return l2 * 1000 * 60 * 60;
            }
            case 'd': 
        }
        return l2 * 1000 * 60 * 60 * 24;
    }

    private void ko() {
        this.Xv.a(new c.a(){

            @Override
            public void a(List<a> object) {
                object = object.iterator();
                while (object.hasNext()) {
                    a a2 = (a)object.next();
                    DataLayer.this.A(DataLayer.this.c(a2.Xy, a2.Xz));
                }
                DataLayer.this.Xw.countDown();
            }
        });
    }

    private void kp() {
        Map<String, Object> map;
        int n2 = 0;
        while ((map = this.Xu.poll()) != null) {
            this.F(map);
            if (++n2 <= 500) continue;
            this.Xu.clear();
            throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
        }
    }

    public static /* varargs */ List<Object> listOf(Object ... arrobject) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int i2 = 0; i2 < arrobject.length; ++i2) {
            arrayList.add(arrobject[i2]);
        }
        return arrayList;
    }

    public static /* varargs */ Map<String, Object> mapOf(Object ... arrobject) {
        if (arrobject.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        for (int i2 = 0; i2 < arrobject.length; i2 += 2) {
            if (!(arrobject[i2] instanceof String)) {
                throw new IllegalArgumentException("key is not a string: " + arrobject[i2]);
            }
            hashMap.put((String)arrobject[i2], arrobject[i2 + 1]);
        }
        return hashMap;
    }

    void a(b b2) {
        this.Xr.put(b2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void a(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        int n2 = 0;
        while (n2 < list.size()) {
            Object object = list.get(n2);
            if (object instanceof List) {
                if (!(list2.get(n2) instanceof List)) {
                    list2.set(n2, new ArrayList());
                }
                this.a((List)object, (List)list2.get(n2));
            } else if (object instanceof Map) {
                if (!(list2.get(n2) instanceof Map)) {
                    list2.set(n2, new HashMap());
                }
                this.a((Map)object, (Map)list2.get(n2));
            } else if (object != OBJECT_NOT_PRESENT) {
                list2.set(n2, object);
            }
            ++n2;
        }
    }

    void a(Map<String, Object> map, Map<String, Object> map2) {
        for (String string2 : map.keySet()) {
            Object object = map.get(string2);
            if (object instanceof List) {
                if (!(map2.get(string2) instanceof List)) {
                    map2.put(string2, new ArrayList());
                }
                this.a((List)object, (List)map2.get(string2));
                continue;
            }
            if (object instanceof Map) {
                if (!(map2.get(string2) instanceof Map)) {
                    map2.put(string2, new HashMap());
                }
                this.a((Map)object, (Map)map2.get(string2));
                continue;
            }
            map2.put(string2, object);
        }
    }

    void bv(String string2) {
        this.push(string2, null);
        this.Xv.bx(string2);
    }

    Map<String, Object> c(String hashMap, Object object) {
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        String[] arrstring = hashMap.toString().split("\\.");
        hashMap = hashMap2;
        for (int i2 = 0; i2 < arrstring.length - 1; ++i2) {
            HashMap<String, HashMap<String, Object>> hashMap3 = new HashMap<String, HashMap<String, Object>>();
            hashMap.put(arrstring[i2], hashMap3);
            hashMap = hashMap3;
        }
        hashMap.put(arrstring[arrstring.length - 1], (HashMap<String, Object>)object);
        return hashMap2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object get(String object) {
        Map<String, Object> map = this.Xs;
        synchronized (map) {
            Object object2 = this.Xs;
            String[] arrstring = object.split("\\.");
            int n2 = arrstring.length;
            object = object2;
            int n3 = 0;
            while (n3 < n2) {
                object2 = arrstring[n3];
                if (!(object instanceof Map)) {
                    return null;
                }
                if ((object = ((Map)object).get(object2)) == null) {
                    return null;
                }
                ++n3;
            }
            return object;
        }
    }

    public void push(String string2, Object object) {
        this.push(this.c(string2, object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void push(Map<String, Object> map) {
        try {
            this.Xw.await();
        }
        catch (InterruptedException var2_2) {
            bh.z("DataLayer.push: unexpected InterruptedException");
        }
        this.A(map);
    }

    public void pushEvent(String string2, Map<String, Object> map) {
        map = new HashMap<String, Object>(map);
        map.put("event", string2);
        this.push(map);
    }

    static final class a {
        public final String Xy;
        public final Object Xz;

        a(String string2, Object object) {
            this.Xy = string2;
            this.Xz = object;
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (!(object instanceof a)) {
                return false;
            }
            object = (a)object;
            if (!this.Xy.equals(object.Xy)) return false;
            if (!this.Xz.equals(object.Xz)) return false;
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode((Object[])new Integer[]{this.Xy.hashCode(), this.Xz.hashCode()});
        }

        public String toString() {
            return "Key: " + this.Xy + " value: " + this.Xz.toString();
        }
    }

    static interface b {
        public void y(Map<String, Object> var1);
    }

    static interface c {
        public void a(a var1);

        public void a(List<com.google.android.gms.tagmanager.DataLayer$a> var1, long var2);

        public void bx(String var1);

        public static interface a {
            public void a(List<com.google.android.gms.tagmanager.DataLayer$a> var1);
        }

    }

}

