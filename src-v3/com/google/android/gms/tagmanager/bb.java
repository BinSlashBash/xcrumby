package com.google.android.gms.tagmanager;

import android.util.LruCache;
import com.google.android.gms.tagmanager.C0525l.C0524a;

class bb<K, V> implements C0523k<K, V> {
    private LruCache<K, V> Yu;

    /* renamed from: com.google.android.gms.tagmanager.bb.1 */
    class C04981 extends LruCache<K, V> {
        final /* synthetic */ C0524a Yv;
        final /* synthetic */ bb Yw;

        C04981(bb bbVar, int i, C0524a c0524a) {
            this.Yw = bbVar;
            this.Yv = c0524a;
            super(i);
        }

        protected int sizeOf(K key, V value) {
            return this.Yv.sizeOf(key, value);
        }
    }

    bb(int i, C0524a<K, V> c0524a) {
        this.Yu = new C04981(this, i, c0524a);
    }

    public void m2467e(K k, V v) {
        this.Yu.put(k, v);
    }

    public V get(K key) {
        return this.Yu.get(key);
    }
}
