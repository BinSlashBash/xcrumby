/*
 * Decompiled with CFR 0_110.
 */
package android.support.v4.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int n2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = n2;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    private int safeSizeOf(K k2, V v2) {
        int n2 = this.sizeOf(k2, v2);
        if (n2 < 0) {
            throw new IllegalStateException("Negative size: " + k2 + "=" + v2);
        }
        return n2;
    }

    protected V create(K k2) {
        return null;
    }

    public final int createCount() {
        synchronized (this) {
            int n2 = this.createCount;
            return n2;
        }
    }

    protected void entryRemoved(boolean bl2, K k2, V v2, V v3) {
    }

    public final void evictAll() {
        this.trimToSize(-1);
    }

    public final int evictionCount() {
        synchronized (this) {
            int n2 = this.evictionCount;
            return n2;
        }
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
        V v2 = this.map.get(k2);
        if (v2 != null) {
            ++this.hitCount;
            // MONITOREXIT : this
            return v2;
        }
        ++this.missCount;
        // MONITOREXIT : this
        v2 = this.create(k2);
        if (v2 == null) {
            return null;
        }
        // MONITORENTER : this
        ++this.createCount;
        V v3 = this.map.put(k2, v2);
        if (v3 != null) {
            this.map.put(k2, v3);
        } else {
            this.size += this.safeSizeOf(k2, v2);
        }
        // MONITOREXIT : this
        if (v3 != null) {
            this.entryRemoved(false, k2, v2, v3);
            return v3;
        }
        this.trimToSize(this.maxSize);
        return v2;
    }

    public final int hitCount() {
        synchronized (this) {
            int n2 = this.hitCount;
            return n2;
        }
    }

    public final int maxSize() {
        synchronized (this) {
            int n2 = this.maxSize;
            return n2;
        }
    }

    public final int missCount() {
        synchronized (this) {
            int n2 = this.missCount;
            return n2;
        }
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
        ++this.putCount;
        this.size += this.safeSizeOf(k2, v2);
        V v3 = this.map.put(k2, v2);
        if (v3 != null) {
            this.size -= this.safeSizeOf(k2, v3);
        }
        // MONITOREXIT : this
        if (v3 != null) {
            this.entryRemoved(false, k2, v3, v2);
        }
        this.trimToSize(this.maxSize);
        return v3;
    }

    public final int putCount() {
        synchronized (this) {
            int n2 = this.putCount;
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V remove(K k2) {
        if (k2 == null) {
            throw new NullPointerException("key == null");
        }
        // MONITORENTER : this
        V v2 = this.map.remove(k2);
        if (v2 != null) {
            this.size -= this.safeSizeOf(k2, v2);
        }
        // MONITOREXIT : this
        if (v2 == null) return v2;
        this.entryRemoved(false, k2, v2, null);
        return v2;
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

    public final Map<K, V> snapshot() {
        synchronized (this) {
            LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<K, V>(this.map);
            return linkedHashMap;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String toString() {
        int n2 = 0;
        synchronized (this) {
            int n3 = this.hitCount + this.missCount;
            if (n3 == 0) return String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", this.maxSize, this.hitCount, this.missCount, n2);
            n2 = this.hitCount * 100 / n3;
            return String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", this.maxSize, this.hitCount, this.missCount, n2);
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
                if (this.size < 0 || this.map.isEmpty() && this.size != 0) {
                    throw new IllegalStateException(this.getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }
                if (this.size <= n2 || this.map.isEmpty()) {
                    return;
                }
                Map.Entry entry2 = this.map.entrySet().iterator().next();
                k2 = entry2.getKey();
                entry2 = entry2.getValue();
                this.map.remove(k2);
                this.size -= this.safeSizeOf(k2, entry2);
                ++this.evictionCount;
            }
            this.entryRemoved(true, k2, entry2, null);
        } while (true);
    }
}

