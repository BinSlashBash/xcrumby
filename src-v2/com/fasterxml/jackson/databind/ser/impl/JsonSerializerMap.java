/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonSerializerMap {
    private final Bucket[] _buckets;
    private final int _size;

    public JsonSerializerMap(Map<SerializerCache.TypeKey, JsonSerializer<Object>> object) {
        int n2;
        this._size = n2 = JsonSerializerMap.findSize(object.size());
        Bucket[] arrbucket = new Bucket[n2];
        for (Map.Entry entry : object.entrySet()) {
            SerializerCache.TypeKey typeKey = (SerializerCache.TypeKey)entry.getKey();
            int n3 = typeKey.hashCode() & n2 - 1;
            arrbucket[n3] = new Bucket(arrbucket[n3], typeKey, (JsonSerializer)entry.getValue());
        }
        this._buckets = arrbucket;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final int findSize(int n2) {
        n2 = n2 <= 64 ? (n2 += n2) : (n2 += n2 >> 2);
        int n3 = 8;
        while (n3 < n2) {
            n3 += n3;
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public JsonSerializer<Object> find(SerializerCache.TypeKey typeKey) {
        int n2;
        int n3 = typeKey.hashCode();
        Bucket bucket = this._buckets[n3 & (n2 = this._buckets.length) - 1];
        if (bucket == null) {
            return null;
        }
        Bucket bucket2 = bucket;
        if (typeKey.equals(bucket.key)) {
            return bucket.value;
        }
        do {
            if ((bucket = bucket2.next) == null) return null;
            bucket2 = bucket;
        } while (!typeKey.equals(bucket.key));
        return bucket.value;
    }

    public int size() {
        return this._size;
    }

    private static final class Bucket {
        public final SerializerCache.TypeKey key;
        public final Bucket next;
        public final JsonSerializer<Object> value;

        public Bucket(Bucket bucket, SerializerCache.TypeKey typeKey, JsonSerializer<Object> jsonSerializer) {
            this.next = bucket;
            this.key = typeKey;
            this.value = jsonSerializer;
        }
    }

}

