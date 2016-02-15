package com.google.android.gms.tagmanager;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT;
    static final String[] Xp;
    private static final Pattern Xq;
    private final ConcurrentHashMap<C0486b, Integer> Xr;
    private final Map<String, Object> Xs;
    private final ReentrantLock Xt;
    private final LinkedList<Map<String, Object>> Xu;
    private final C0488c Xv;
    private final CountDownLatch Xw;

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.a */
    static final class C0485a {
        public final String Xy;
        public final Object Xz;

        C0485a(String str, Object obj) {
            this.Xy = str;
            this.Xz = obj;
        }

        public boolean equals(Object o) {
            if (!(o instanceof C0485a)) {
                return false;
            }
            C0485a c0485a = (C0485a) o;
            return this.Xy.equals(c0485a.Xy) && this.Xz.equals(c0485a.Xz);
        }

        public int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.Xy.hashCode()), Integer.valueOf(this.Xz.hashCode())});
        }

        public String toString() {
            return "Key: " + this.Xy + " value: " + this.Xz.toString();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.b */
    interface C0486b {
        void m1331y(Map<String, Object> map);
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.c */
    interface C0488c {

        /* renamed from: com.google.android.gms.tagmanager.DataLayer.c.a */
        public interface C0487a {
            void m1332a(List<C0485a> list);
        }

        void m1333a(C0487a c0487a);

        void m1334a(List<C0485a> list, long j);

        void bx(String str);
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.1 */
    class C10561 implements C0488c {
        C10561() {
        }

        public void m2439a(C0487a c0487a) {
            c0487a.m1332a(new ArrayList());
        }

        public void m2440a(List<C0485a> list, long j) {
        }

        public void bx(String str) {
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.2 */
    class C10572 implements C0487a {
        final /* synthetic */ DataLayer Xx;

        C10572(DataLayer dataLayer) {
            this.Xx = dataLayer;
        }

        public void m2441a(List<C0485a> list) {
            for (C0485a c0485a : list) {
                this.Xx.m1335A(this.Xx.m1348c(c0485a.Xy, c0485a.Xz));
            }
            this.Xx.Xw.countDown();
        }
    }

    static {
        OBJECT_NOT_PRESENT = new Object();
        Xp = "gtm.lifetime".toString().split("\\.");
        Xq = Pattern.compile("(\\d+)\\s*([smhd]?)");
    }

    DataLayer() {
        this(new C10561());
    }

    DataLayer(C0488c persistentStore) {
        this.Xv = persistentStore;
        this.Xr = new ConcurrentHashMap();
        this.Xs = new HashMap();
        this.Xt = new ReentrantLock();
        this.Xu = new LinkedList();
        this.Xw = new CountDownLatch(1);
        ko();
    }

    private void m1335A(Map<String, Object> map) {
        this.Xt.lock();
        try {
            this.Xu.offer(map);
            if (this.Xt.getHoldCount() == 1) {
                kp();
            }
            m1336B(map);
        } finally {
            this.Xt.unlock();
        }
    }

    private void m1336B(Map<String, Object> map) {
        Long C = m1337C(map);
        if (C != null) {
            List E = m1339E(map);
            E.remove("gtm.lifetime");
            this.Xv.m1334a(E, C.longValue());
        }
    }

    private Long m1337C(Map<String, Object> map) {
        Object D = m1338D(map);
        return D == null ? null : bw(D.toString());
    }

    private Object m1338D(Map<String, Object> map) {
        String[] strArr = Xp;
        int length = strArr.length;
        int i = 0;
        Object obj = map;
        while (i < length) {
            Object obj2 = strArr[i];
            if (!(obj instanceof Map)) {
                return null;
            }
            i++;
            obj = ((Map) obj).get(obj2);
        }
        return obj;
    }

    private List<C0485a> m1339E(Map<String, Object> map) {
        Object arrayList = new ArrayList();
        m1344a(map, UnsupportedUrlFragment.DISPLAY_NAME, arrayList);
        return arrayList;
    }

    private void m1340F(Map<String, Object> map) {
        synchronized (this.Xs) {
            for (String str : map.keySet()) {
                m1347a(m1348c(str, map.get(str)), this.Xs);
            }
        }
        m1341G(map);
    }

    private void m1341G(Map<String, Object> map) {
        for (C0486b y : this.Xr.keySet()) {
            y.m1331y(map);
        }
    }

    private void m1344a(Map<String, Object> map, String str, Collection<C0485a> collection) {
        for (Entry entry : map.entrySet()) {
            String str2 = str + (str.length() == 0 ? UnsupportedUrlFragment.DISPLAY_NAME : ".") + ((String) entry.getKey());
            if (entry.getValue() instanceof Map) {
                m1344a((Map) entry.getValue(), str2, collection);
            } else if (!str2.equals("gtm.lifetime")) {
                collection.add(new C0485a(str2, entry.getValue()));
            }
        }
    }

    static Long bw(String str) {
        Matcher matcher = Xq.matcher(str);
        if (matcher.matches()) {
            long parseLong;
            try {
                parseLong = Long.parseLong(matcher.group(1));
            } catch (NumberFormatException e) {
                bh.m1387z("illegal number in _lifetime value: " + str);
                parseLong = 0;
            }
            if (parseLong <= 0) {
                bh.m1385x("non-positive _lifetime: " + str);
                return null;
            }
            String group = matcher.group(2);
            if (group.length() == 0) {
                return Long.valueOf(parseLong);
            }
            switch (group.charAt(0)) {
                case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                    return Long.valueOf((((parseLong * 1000) * 60) * 60) * 24);
                case LocationRequest.PRIORITY_LOW_POWER /*104*/:
                    return Long.valueOf(((parseLong * 1000) * 60) * 60);
                case 'm':
                    return Long.valueOf((parseLong * 1000) * 60);
                case 's':
                    return Long.valueOf(parseLong * 1000);
                default:
                    bh.m1387z("unknown units in _lifetime: " + str);
                    return null;
            }
        }
        bh.m1385x("unknown _lifetime: " + str);
        return null;
    }

    private void ko() {
        this.Xv.m1333a(new C10572(this));
    }

    private void kp() {
        int i = 0;
        while (true) {
            Map map = (Map) this.Xu.poll();
            if (map != null) {
                m1340F(map);
                int i2 = i + 1;
                if (i2 > 500) {
                    break;
                }
                i = i2;
            } else {
                return;
            }
        }
        this.Xu.clear();
        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
    }

    public static List<Object> listOf(Object... objects) {
        List<Object> arrayList = new ArrayList();
        for (Object add : objects) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        Map<String, Object> hashMap = new HashMap();
        int i = 0;
        while (i < objects.length) {
            if (objects[i] instanceof String) {
                hashMap.put((String) objects[i], objects[i + 1]);
                i += 2;
            } else {
                throw new IllegalArgumentException("key is not a string: " + objects[i]);
            }
        }
        return hashMap;
    }

    void m1345a(C0486b c0486b) {
        this.Xr.put(c0486b, Integer.valueOf(0));
    }

    void m1346a(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                m1346a((List) obj, (List) list2.get(i));
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                m1347a((Map) obj, (Map) list2.get(i));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }

    void m1347a(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                m1346a((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                m1347a((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    void bv(String str) {
        push(str, null);
        this.Xv.bx(str);
    }

    Map<String, Object> m1348c(String str, Object obj) {
        Map hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        Map map = hashMap;
        while (i < split.length - 1) {
            HashMap hashMap2 = new HashMap();
            map.put(split[i], hashMap2);
            i++;
            Object obj2 = hashMap2;
        }
        map.put(split[split.length - 1], obj);
        return hashMap;
    }

    public Object get(String key) {
        synchronized (this.Xs) {
            Map map = this.Xs;
            String[] split = key.split("\\.");
            int length = split.length;
            Object obj = map;
            int i = 0;
            while (i < length) {
                Object obj2 = split[i];
                if (obj instanceof Map) {
                    obj2 = ((Map) obj).get(obj2);
                    if (obj2 == null) {
                        return null;
                    }
                    i++;
                    obj = obj2;
                } else {
                    return null;
                }
            }
            return obj;
        }
    }

    public void push(String key, Object value) {
        push(m1348c(key, value));
    }

    public void push(Map<String, Object> update) {
        try {
            this.Xw.await();
        } catch (InterruptedException e) {
            bh.m1387z("DataLayer.push: unexpected InterruptedException");
        }
        m1335A(update);
    }

    public void pushEvent(String eventName, Map<String, Object> update) {
        Map hashMap = new HashMap(update);
        hashMap.put(EVENT_KEY, eventName);
        push(hashMap);
    }
}
