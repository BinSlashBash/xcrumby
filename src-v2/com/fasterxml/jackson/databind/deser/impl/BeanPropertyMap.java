/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BeanPropertyMap
implements Iterable<SettableBeanProperty>,
Serializable {
    private static final long serialVersionUID = 1;
    private final Bucket[] _buckets;
    private final int _hashMask;
    private int _nextBucketIndex = 0;
    private final int _size;

    public BeanPropertyMap(Collection<SettableBeanProperty> object) {
        this._size = object.size();
        int n2 = BeanPropertyMap.findSize(this._size);
        this._hashMask = n2 - 1;
        Bucket[] arrbucket = new Bucket[n2];
        object = object.iterator();
        while (object.hasNext()) {
            SettableBeanProperty settableBeanProperty = (SettableBeanProperty)object.next();
            String string2 = settableBeanProperty.getName();
            n2 = string2.hashCode() & this._hashMask;
            Bucket bucket = arrbucket[n2];
            int n3 = this._nextBucketIndex;
            this._nextBucketIndex = n3 + 1;
            arrbucket[n2] = new Bucket(bucket, string2, settableBeanProperty, n3);
        }
        this._buckets = arrbucket;
    }

    private BeanPropertyMap(Bucket[] arrbucket, int n2, int n3) {
        this._buckets = arrbucket;
        this._size = n2;
        this._hashMask = arrbucket.length - 1;
        this._nextBucketIndex = n3;
    }

    private SettableBeanProperty _findWithEquals(String string2, int n2) {
        Bucket bucket = this._buckets[n2];
        while (bucket != null) {
            if (string2.equals(bucket.key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final int findSize(int n2) {
        n2 = n2 <= 32 ? (n2 += n2) : (n2 += n2 >> 2);
        int n3 = 2;
        while (n3 < n2) {
            n3 += n3;
        }
        return n3;
    }

    public BeanPropertyMap assignIndexes() {
        int n2 = 0;
        for (Bucket bucket : this._buckets) {
            while (bucket != null) {
                bucket.value.assignIndex(n2);
                bucket = bucket.next;
                ++n2;
            }
        }
        return this;
    }

    public SettableBeanProperty find(int n2) {
        for (Bucket bucket : this._buckets) {
            while (bucket != null) {
                if (bucket.index == n2) {
                    return bucket.value;
                }
                bucket = bucket.next;
            }
        }
        return null;
    }

    public SettableBeanProperty find(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Can not pass null property name");
        }
        int n2 = string2.hashCode() & this._hashMask;
        Bucket bucket = this._buckets[n2];
        if (bucket == null) {
            return null;
        }
        Bucket bucket2 = bucket;
        if (bucket.key == string2) {
            return bucket.value;
        }
        while ((bucket = bucket2.next) != null) {
            bucket2 = bucket;
            if (bucket.key != string2) continue;
            return bucket.value;
        }
        return this._findWithEquals(string2, n2);
    }

    public SettableBeanProperty[] getPropertiesInInsertionOrder() {
        SettableBeanProperty[] arrsettableBeanProperty = new SettableBeanProperty[this._nextBucketIndex];
        for (Bucket bucket : this._buckets) {
            while (bucket != null) {
                arrsettableBeanProperty[bucket.index] = bucket.value;
                bucket = bucket.next;
            }
        }
        return arrsettableBeanProperty;
    }

    @Override
    public Iterator<SettableBeanProperty> iterator() {
        return new IteratorImpl(this._buckets);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void remove(SettableBeanProperty settableBeanProperty) {
        String string2 = settableBeanProperty.getName();
        int n2 = string2.hashCode() & this._buckets.length - 1;
        boolean bl2 = false;
        Bucket bucket = this._buckets[n2];
        Bucket bucket2 = null;
        while (bucket != null) {
            if (!bl2 && bucket.key.equals(string2)) {
                bl2 = true;
            } else {
                bucket2 = new Bucket(bucket2, bucket.key, bucket.value, bucket.index);
            }
            bucket = bucket.next;
        }
        if (!bl2) {
            throw new NoSuchElementException("No entry '" + settableBeanProperty + "' found, can't remove");
        }
        this._buckets[n2] = bucket2;
    }

    public BeanPropertyMap renameAll(NameTransformer nameTransformer) {
        if (nameTransformer == null || nameTransformer == NameTransformer.NOP) {
            return this;
        }
        Iterator<SettableBeanProperty> iterator = this.iterator();
        ArrayList<SettableBeanProperty> arrayList = new ArrayList<SettableBeanProperty>();
        while (iterator.hasNext()) {
            SettableBeanProperty settableBeanProperty = iterator.next();
            SettableBeanProperty settableBeanProperty2 = settableBeanProperty.withSimpleName(nameTransformer.transform(settableBeanProperty.getName()));
            JsonDeserializer<Object> jsonDeserializer = settableBeanProperty2.getValueDeserializer();
            settableBeanProperty = settableBeanProperty2;
            if (jsonDeserializer != null) {
                JsonDeserializer<Object> jsonDeserializer2 = jsonDeserializer.unwrappingDeserializer(nameTransformer);
                settableBeanProperty = settableBeanProperty2;
                if (jsonDeserializer2 != jsonDeserializer) {
                    settableBeanProperty = settableBeanProperty2.withValueDeserializer(jsonDeserializer2);
                }
            }
            arrayList.add(settableBeanProperty);
        }
        return new BeanPropertyMap(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void replace(SettableBeanProperty settableBeanProperty) {
        String string2 = settableBeanProperty.getName();
        int n2 = string2.hashCode() & this._buckets.length - 1;
        int n3 = -1;
        Bucket bucket = this._buckets[n2];
        Bucket bucket2 = null;
        while (bucket != null) {
            if (n3 < 0 && bucket.key.equals(string2)) {
                n3 = bucket.index;
            } else {
                bucket2 = new Bucket(bucket2, bucket.key, bucket.value, bucket.index);
            }
            bucket = bucket.next;
        }
        if (n3 < 0) {
            throw new NoSuchElementException("No entry '" + settableBeanProperty + "' found, can't replace");
        }
        this._buckets[n2] = new Bucket(bucket2, string2, settableBeanProperty, n3);
    }

    public int size() {
        return this._size;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Properties=[");
        SettableBeanProperty[] arrsettableBeanProperty = this.getPropertiesInInsertionOrder();
        int n2 = arrsettableBeanProperty.length;
        int n3 = 0;
        int n4 = 0;
        do {
            if (n3 >= n2) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            SettableBeanProperty settableBeanProperty = arrsettableBeanProperty[n3];
            if (settableBeanProperty != null) {
                int n5 = n4 + 1;
                if (n4 > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(settableBeanProperty.getName());
                stringBuilder.append('(');
                stringBuilder.append(settableBeanProperty.getType());
                stringBuilder.append(')');
                n4 = n5;
            }
            ++n3;
        } while (true);
    }

    public BeanPropertyMap withProperty(SettableBeanProperty settableBeanProperty) {
        int n2 = this._buckets.length;
        Object object = new Bucket[n2];
        System.arraycopy(this._buckets, 0, object, 0, n2);
        String string2 = settableBeanProperty.getName();
        if (this.find(settableBeanProperty.getName()) == null) {
            n2 = string2.hashCode() & this._hashMask;
            Bucket bucket = object[n2];
            int n3 = this._nextBucketIndex;
            this._nextBucketIndex = n3 + 1;
            object[n2] = new Bucket(bucket, string2, settableBeanProperty, n3);
            return new BeanPropertyMap((Bucket[])object, this._size + 1, this._nextBucketIndex);
        }
        object = new BeanPropertyMap((Bucket[])object, n2, this._nextBucketIndex);
        object.replace(settableBeanProperty);
        return object;
    }

    private static final class Bucket
    implements Serializable {
        private static final long serialVersionUID = 1;
        public final int index;
        public final String key;
        public final Bucket next;
        public final SettableBeanProperty value;

        public Bucket(Bucket bucket, String string2, SettableBeanProperty settableBeanProperty, int n2) {
            this.next = bucket;
            this.key = string2;
            this.value = settableBeanProperty;
            this.index = n2;
        }
    }

    private static final class IteratorImpl
    implements Iterator<SettableBeanProperty> {
        private final Bucket[] _buckets;
        private Bucket _currentBucket;
        private int _nextBucketIndex;

        /*
         * Enabled aggressive block sorting
         */
        public IteratorImpl(Bucket[] object) {
            int n2;
            block2 : {
                this._buckets = object;
                int n3 = this._buckets.length;
                int n4 = 0;
                while (n4 < n3) {
                    object = this._buckets;
                    n2 = n4 + 1;
                    if ((object = object[n4]) != null) {
                        this._currentBucket = object;
                        break block2;
                    }
                    n4 = n2;
                }
                n2 = n4;
            }
            this._nextBucketIndex = n2;
        }

        @Override
        public boolean hasNext() {
            if (this._currentBucket != null) {
                return true;
            }
            return false;
        }

        @Override
        public SettableBeanProperty next() {
            Bucket bucket = this._currentBucket;
            if (bucket == null) {
                throw new NoSuchElementException();
            }
            Object object = bucket.next;
            while (object == null && this._nextBucketIndex < this._buckets.length) {
                object = this._buckets;
                int n2 = this._nextBucketIndex;
                this._nextBucketIndex = n2 + 1;
                object = object[n2];
            }
            this._currentBucket = object;
            return bucket.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

