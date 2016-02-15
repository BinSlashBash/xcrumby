/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.k;
import com.google.android.gms.tagmanager.l;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class cz<K, V>
implements k<K, V> {
    private final Map<K, V> aap = new HashMap();
    private final int aaq;
    private final l.a<K, V> aar;
    private int aas;

    cz(int n2, l.a<K, V> a2) {
        this.aaq = n2;
        this.aar = a2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void e(K k2, V v2) {
        synchronized (this) {
            if (k2 == null || v2 == null) {
                throw new NullPointerException("key == null || value == null");
            }
            this.aas += this.aar.sizeOf(k2, v2);
            if (this.aas > this.aaq) {
                Iterator<Map.Entry<K, V>> iterator = this.aap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    this.aas -= this.aar.sizeOf(entry.getKey(), entry.getValue());
                    iterator.remove();
                    if (this.aas > this.aaq) continue;
                }
            }
            this.aap.put(k2, v2);
            return;
        }
    }

    @Override
    public V get(K object) {
        synchronized (this) {
            object = this.aap.get(object);
            return (V)object;
        }
    }
}

