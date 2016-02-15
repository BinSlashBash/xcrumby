/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.tagmanager.bb;
import com.google.android.gms.tagmanager.cz;
import com.google.android.gms.tagmanager.k;

class l<K, V> {
    final a<K, V> WH;

    public l() {
        this.WH = new a<K, V>(){

            @Override
            public int sizeOf(K k2, V v2) {
                return 1;
            }
        };
    }

    public k<K, V> a(int n2, a<K, V> a2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (this.jZ() < 12) {
            return new cz<K, V>(n2, a2);
        }
        return new bb<K, V>(n2, a2);
    }

    int jZ() {
        return Build.VERSION.SDK_INT;
    }

    public static interface a<K, V> {
        public int sizeOf(K var1, V var2);
    }

}

