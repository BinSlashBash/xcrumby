/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.LruCache
 */
package com.google.android.gms.tagmanager;

import android.util.LruCache;
import com.google.android.gms.tagmanager.k;
import com.google.android.gms.tagmanager.l;

class bb<K, V>
implements k<K, V> {
    private LruCache<K, V> Yu;

    bb(int n2, final l.a<K, V> a2) {
        this.Yu = new LruCache<K, V>(n2){

            protected int sizeOf(K k2, V v2) {
                return a2.sizeOf(k2, v2);
            }
        };
    }

    @Override
    public void e(K k2, V v2) {
        this.Yu.put(k2, v2);
    }

    @Override
    public V get(K k2) {
        return (V)this.Yu.get(k2);
    }

}

