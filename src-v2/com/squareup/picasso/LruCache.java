/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 */
package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Utils;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LruCache
implements Cache {
    private int evictionCount;
    private int hitCount;
    final LinkedHashMap<String, Bitmap> map;
    private final int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int n2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("Max size must be positive.");
        }
        this.maxSize = n2;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    public LruCache(Context context) {
        this(Utils.calculateMemoryCacheSize(context));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void trimToSize(int n2) {
        do {
            synchronized (this) {
                if (this.size < 0 || this.map.isEmpty() && this.size != 0) {
                    throw new IllegalStateException(this.getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }
                if (this.size <= n2 || this.map.isEmpty()) {
                    return;
                }
                Bitmap bitmap = this.map.entrySet().iterator().next();
                String string2 = bitmap.getKey();
                bitmap = bitmap.getValue();
                this.map.remove(string2);
                this.size -= Utils.getBitmapBytes(bitmap);
                ++this.evictionCount;
            }
        } while (true);
    }

    @Override
    public final void clear() {
        synchronized (this) {
            this.evictAll();
            return;
        }
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
     */
    @Override
    public Bitmap get(String string2) {
        if (string2 == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            string2 = this.map.get(string2);
            if (string2 != null) {
                ++this.hitCount;
                return string2;
            }
            ++this.missCount;
            return null;
        }
    }

    public final int hitCount() {
        synchronized (this) {
            int n2 = this.hitCount;
            return n2;
        }
    }

    @Override
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
     */
    @Override
    public void set(String string2, Bitmap bitmap) {
        if (string2 == null || bitmap == null) {
            throw new NullPointerException("key == null || bitmap == null");
        }
        synchronized (this) {
            ++this.putCount;
            this.size += Utils.getBitmapBytes(bitmap);
            string2 = this.map.put(string2, bitmap);
            if (string2 != null) {
                this.size -= Utils.getBitmapBytes((Bitmap)string2);
            }
        }
        this.trimToSize(this.maxSize);
    }

    @Override
    public final int size() {
        synchronized (this) {
            int n2 = this.size;
            return n2;
        }
    }
}

