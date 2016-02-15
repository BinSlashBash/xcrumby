package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;

/* renamed from: com.google.android.gms.tagmanager.l */
class C0525l<K, V> {
    final C0524a<K, V> WH;

    /* renamed from: com.google.android.gms.tagmanager.l.a */
    public interface C0524a<K, V> {
        int sizeOf(K k, V v);
    }

    /* renamed from: com.google.android.gms.tagmanager.l.1 */
    class C10791 implements C0524a<K, V> {
        final /* synthetic */ C0525l WI;

        C10791(C0525l c0525l) {
            this.WI = c0525l;
        }

        public int sizeOf(K k, V v) {
            return 1;
        }
    }

    public C0525l() {
        this.WH = new C10791(this);
    }

    public C0523k<K, V> m1481a(int i, C0524a<K, V> c0524a) {
        if (i > 0) {
            return jZ() < 12 ? new cz(i, c0524a) : new bb(i, c0524a);
        } else {
            throw new IllegalArgumentException("maxSize <= 0");
        }
    }

    int jZ() {
        return VERSION.SDK_INT;
    }
}
