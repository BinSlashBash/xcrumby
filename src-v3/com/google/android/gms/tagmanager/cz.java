package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.C0525l.C0524a;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class cz<K, V> implements C0523k<K, V> {
    private final Map<K, V> aap;
    private final int aaq;
    private final C0524a<K, V> aar;
    private int aas;

    cz(int i, C0524a<K, V> c0524a) {
        this.aap = new HashMap();
        this.aaq = i;
        this.aar = c0524a;
    }

    public synchronized void m2516e(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        this.aas += this.aar.sizeOf(k, v);
        if (this.aas > this.aaq) {
            Iterator it = this.aap.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                this.aas -= this.aar.sizeOf(entry.getKey(), entry.getValue());
                it.remove();
                if (this.aas <= this.aaq) {
                    break;
                }
            }
        }
        this.aap.put(k, v);
    }

    public synchronized V get(K key) {
        return this.aap.get(key);
    }
}
