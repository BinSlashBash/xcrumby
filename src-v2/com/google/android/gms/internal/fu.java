/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class fu<K, V> {
    private final LinkedHashMap<K, V> DL;
    private int DM;
    private int DN;
    private int DO;
    private int DP;
    private int DQ;
    private int DR;
    private int size;

    public fu(int n2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.DM = n2;
        this.DL = new LinkedHashMap(0, 0.75f, true);
    }

    private int c(K k2, V v2) {
        int n2 = this.sizeOf(k2, v2);
        if (n2 < 0) {
            throw new IllegalStateException("Negative size: " + k2 + "=" + v2);
        }
        return n2;
    }

    protected V create(K k2) {
        return null;
    }

    protected void entryRemoved(boolean bl2, K k2, V v2, V v3) {
    }

    public final void evictAll() {
        this.trimToSize(-1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V get(K k2) {
        if (k2 == null) {
            throw new NullPointerException("key == null");
        }
        // MONITORENTER : this
        V v2 = this.DL.get(k2);
        if (v2 != null) {
            ++this.DQ;
            // MONITOREXIT : this
            return v2;
        }
        ++this.DR;
        // MONITOREXIT : this
        v2 = this.create(k2);
        if (v2 == null) {
            return null;
        }
        // MONITORENTER : this
        ++this.DO;
        V v3 = this.DL.put(k2, v2);
        if (v3 != null) {
            this.DL.put(k2, v3);
        } else {
            this.size += this.c(k2, v2);
        }
        // MONITOREXIT : this
        if (v3 != null) {
            this.entryRemoved(false, k2, v2, v3);
            return v3;
        }
        this.trimToSize(this.DM);
        return v2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V put(K k2, V v2) {
        if (k2 == null) throw new NullPointerException("key == null || value == null");
        if (v2 == null) {
            throw new NullPointerException("key == null || value == null");
        }
        // MONITORENTER : this
        ++this.DN;
        this.size += this.c(k2, v2);
        V v3 = this.DL.put(k2, v2);
        if (v3 != null) {
            this.size -= this.c(k2, v3);
        }
        // MONITOREXIT : this
        if (v3 != null) {
            this.entryRemoved(false, k2, v3, v2);
        }
        this.trimToSize(this.DM);
        return v3;
    }

    public final int size() {
        synchronized (this) {
            int n2 = this.size;
            return n2;
        }
    }

    protected int sizeOf(K k2, V v2) {
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String toString() {
        int n2 = 0;
        synchronized (this) {
            int n3 = this.DQ + this.DR;
            if (n3 == 0) return String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", this.DM, this.DQ, this.DR, n2);
            n2 = this.DQ * 100 / n3;
            return String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", this.DM, this.DQ, this.DR, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void trimToSize(int n2) {
        do {
            K k2;
            Map.Entry entry2;
            synchronized (this) {
                if (this.size < 0 || this.DL.isEmpty() && this.size != 0) {
                    throw new IllegalStateException(this.getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }
                if (this.size <= n2 || this.DL.isEmpty()) {
                    return;
                }
                Map.Entry entry2 = this.DL.entrySet().iterator().next();
                k2 = entry2.getKey();
                entry2 = entry2.getValue();
                this.DL.remove(k2);
                this.size -= this.c(k2, entry2);
                ++this.DP;
            }
            this.entryRemoved(true, k2, entry2, null);
        } while (true);
    }
}

