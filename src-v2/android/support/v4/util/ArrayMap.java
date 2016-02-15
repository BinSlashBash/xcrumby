/*
 * Decompiled with CFR 0_110.
 */
package android.support.v4.util;

import android.support.v4.util.MapCollections;
import android.support.v4.util.SimpleArrayMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ArrayMap<K, V>
extends SimpleArrayMap<K, V>
implements Map<K, V> {
    MapCollections<K, V> mCollections;

    public ArrayMap() {
    }

    public ArrayMap(int n2) {
        super(n2);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }

    private MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<K, V>(){

                @Override
                protected void colClear() {
                    ArrayMap.this.clear();
                }

                @Override
                protected Object colGetEntry(int n2, int n3) {
                    return ArrayMap.this.mArray[(n2 << 1) + n3];
                }

                @Override
                protected Map<K, V> colGetMap() {
                    return ArrayMap.this;
                }

                @Override
                protected int colGetSize() {
                    return ArrayMap.this.mSize;
                }

                @Override
                protected int colIndexOfKey(Object object) {
                    if (object == null) {
                        return ArrayMap.this.indexOfNull();
                    }
                    return ArrayMap.this.indexOf(object, object.hashCode());
                }

                @Override
                protected int colIndexOfValue(Object object) {
                    return ArrayMap.this.indexOfValue(object);
                }

                @Override
                protected void colPut(K k2, V v2) {
                    ArrayMap.this.put(k2, v2);
                }

                @Override
                protected void colRemoveAt(int n2) {
                    ArrayMap.this.removeAt(n2);
                }

                @Override
                protected V colSetValue(int n2, V v2) {
                    return ArrayMap.this.setValueAt(n2, v2);
                }
            };
        }
        return this.mCollections;
    }

    public boolean containsAll(Collection<?> collection) {
        return MapCollections.containsAllHelper(this, collection);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.getCollection().getEntrySet();
    }

    @Override
    public Set<K> keySet() {
        return this.getCollection().getKeySet();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        this.ensureCapacity(this.mSize + object.size());
        for (Map.Entry entry : object.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(Collection<?> collection) {
        return MapCollections.removeAllHelper(this, collection);
    }

    public boolean retainAll(Collection<?> collection) {
        return MapCollections.retainAllHelper(this, collection);
    }

    @Override
    public Collection<V> values() {
        return this.getCollection().getValues();
    }

}

