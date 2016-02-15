package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BeanPropertyMap implements Iterable<SettableBeanProperty>, Serializable {
    private static final long serialVersionUID = 1;
    private final Bucket[] _buckets;
    private final int _hashMask;
    private int _nextBucketIndex;
    private final int _size;

    private static final class Bucket implements Serializable {
        private static final long serialVersionUID = 1;
        public final int index;
        public final String key;
        public final Bucket next;
        public final SettableBeanProperty value;

        public Bucket(Bucket next, String key, SettableBeanProperty value, int index) {
            this.next = next;
            this.key = key;
            this.value = value;
            this.index = index;
        }
    }

    private static final class IteratorImpl implements Iterator<SettableBeanProperty> {
        private final Bucket[] _buckets;
        private Bucket _currentBucket;
        private int _nextBucketIndex;

        public IteratorImpl(Bucket[] buckets) {
            int i;
            this._buckets = buckets;
            int len = this._buckets.length;
            int i2 = 0;
            while (i2 < len) {
                i = i2 + 1;
                Bucket b = this._buckets[i2];
                if (b != null) {
                    this._currentBucket = b;
                    break;
                }
                i2 = i;
            }
            i = i2;
            this._nextBucketIndex = i;
        }

        public boolean hasNext() {
            return this._currentBucket != null;
        }

        public SettableBeanProperty next() {
            Bucket curr = this._currentBucket;
            if (curr == null) {
                throw new NoSuchElementException();
            }
            Bucket b = curr.next;
            while (b == null && this._nextBucketIndex < this._buckets.length) {
                Bucket[] bucketArr = this._buckets;
                int i = this._nextBucketIndex;
                this._nextBucketIndex = i + 1;
                b = bucketArr[i];
            }
            this._currentBucket = b;
            return curr.value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public BeanPropertyMap(Collection<SettableBeanProperty> properties) {
        this._nextBucketIndex = 0;
        this._size = properties.size();
        int bucketCount = findSize(this._size);
        this._hashMask = bucketCount - 1;
        Bucket[] buckets = new Bucket[bucketCount];
        for (SettableBeanProperty property : properties) {
            String key = property.getName();
            int index = key.hashCode() & this._hashMask;
            Bucket bucket = buckets[index];
            int i = this._nextBucketIndex;
            this._nextBucketIndex = i + 1;
            buckets[index] = new Bucket(bucket, key, property, i);
        }
        this._buckets = buckets;
    }

    private BeanPropertyMap(Bucket[] buckets, int size, int index) {
        this._nextBucketIndex = 0;
        this._buckets = buckets;
        this._size = size;
        this._hashMask = buckets.length - 1;
        this._nextBucketIndex = index;
    }

    public BeanPropertyMap withProperty(SettableBeanProperty newProperty) {
        int bcount = this._buckets.length;
        Bucket[] newBuckets = new Bucket[bcount];
        System.arraycopy(this._buckets, 0, newBuckets, 0, bcount);
        String propName = newProperty.getName();
        if (find(newProperty.getName()) == null) {
            int index = propName.hashCode() & this._hashMask;
            Bucket bucket = newBuckets[index];
            int i = this._nextBucketIndex;
            this._nextBucketIndex = i + 1;
            newBuckets[index] = new Bucket(bucket, propName, newProperty, i);
            return new BeanPropertyMap(newBuckets, this._size + 1, this._nextBucketIndex);
        }
        BeanPropertyMap newMap = new BeanPropertyMap(newBuckets, bcount, this._nextBucketIndex);
        newMap.replace(newProperty);
        return newMap;
    }

    public BeanPropertyMap renameAll(NameTransformer transformer) {
        if (transformer == null || transformer == NameTransformer.NOP) {
            return this;
        }
        Iterator<SettableBeanProperty> it = iterator();
        ArrayList<SettableBeanProperty> newProps = new ArrayList();
        while (it.hasNext()) {
            SettableBeanProperty prop = (SettableBeanProperty) it.next();
            prop = prop.withSimpleName(transformer.transform(prop.getName()));
            JsonDeserializer<?> deser = prop.getValueDeserializer();
            if (deser != null) {
                JsonDeserializer<Object> newDeser = deser.unwrappingDeserializer(transformer);
                if (newDeser != deser) {
                    prop = prop.withValueDeserializer(newDeser);
                }
            }
            newProps.add(prop);
        }
        this(newProps);
        return this;
    }

    public BeanPropertyMap assignIndexes() {
        int index = 0;
        Bucket[] arr$ = this._buckets;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            Bucket bucket = arr$[i$];
            int index2 = index;
            while (bucket != null) {
                index = index2 + 1;
                bucket.value.assignIndex(index2);
                bucket = bucket.next;
                index2 = index;
            }
            i$++;
            index = index2;
        }
        return this;
    }

    private static final int findSize(int size) {
        int result = 2;
        while (result < (size <= 32 ? size + size : size + (size >> 2))) {
            result += result;
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Properties=[");
        SettableBeanProperty[] arr$ = getPropertiesInInsertionOrder();
        int len$ = arr$.length;
        int i$ = 0;
        int count = 0;
        while (i$ < len$) {
            int count2;
            SettableBeanProperty prop = arr$[i$];
            if (prop == null) {
                count2 = count;
            } else {
                count2 = count + 1;
                if (count > 0) {
                    sb.append(", ");
                }
                sb.append(prop.getName());
                sb.append('(');
                sb.append(prop.getType());
                sb.append(')');
            }
            i$++;
            count = count2;
        }
        sb.append(']');
        return sb.toString();
    }

    public Iterator<SettableBeanProperty> iterator() {
        return new IteratorImpl(this._buckets);
    }

    public SettableBeanProperty[] getPropertiesInInsertionOrder() {
        SettableBeanProperty[] result = new SettableBeanProperty[this._nextBucketIndex];
        for (Bucket bucket : this._buckets) {
            for (Bucket bucket2 = arr$[i$]; bucket2 != null; bucket2 = bucket2.next) {
                result[bucket2.index] = bucket2.value;
            }
        }
        return result;
    }

    public int size() {
        return this._size;
    }

    public SettableBeanProperty find(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Can not pass null property name");
        }
        int index = key.hashCode() & this._hashMask;
        Bucket bucket = this._buckets[index];
        if (bucket == null) {
            return null;
        }
        if (bucket.key == key) {
            return bucket.value;
        }
        do {
            bucket = bucket.next;
            if (bucket == null) {
                return _findWithEquals(key, index);
            }
        } while (bucket.key != key);
        return bucket.value;
    }

    public SettableBeanProperty find(int propertyIndex) {
        for (Bucket bucket : this._buckets) {
            for (Bucket bucket2 = this._buckets[i]; bucket2 != null; bucket2 = bucket2.next) {
                if (bucket2.index == propertyIndex) {
                    return bucket2.value;
                }
            }
        }
        return null;
    }

    public void replace(SettableBeanProperty property) {
        String name = property.getName();
        int index = name.hashCode() & (this._buckets.length - 1);
        int foundIndex = -1;
        Bucket bucket = this._buckets[index];
        Bucket tail = null;
        while (bucket != null) {
            Bucket tail2;
            if (foundIndex >= 0 || !bucket.key.equals(name)) {
                tail2 = new Bucket(tail, bucket.key, bucket.value, bucket.index);
            } else {
                foundIndex = bucket.index;
                tail2 = tail;
            }
            bucket = bucket.next;
            tail = tail2;
        }
        if (foundIndex < 0) {
            throw new NoSuchElementException("No entry '" + property + "' found, can't replace");
        }
        this._buckets[index] = new Bucket(tail, name, property, foundIndex);
    }

    public void remove(SettableBeanProperty property) {
        String name = property.getName();
        int index = name.hashCode() & (this._buckets.length - 1);
        boolean found = false;
        Bucket bucket = this._buckets[index];
        Bucket tail = null;
        while (bucket != null) {
            Bucket tail2;
            if (found || !bucket.key.equals(name)) {
                tail2 = new Bucket(tail, bucket.key, bucket.value, bucket.index);
            } else {
                found = true;
                tail2 = tail;
            }
            bucket = bucket.next;
            tail = tail2;
        }
        if (found) {
            this._buckets[index] = tail;
            return;
        }
        throw new NoSuchElementException("No entry '" + property + "' found, can't remove");
    }

    private SettableBeanProperty _findWithEquals(String key, int index) {
        for (Bucket bucket = this._buckets[index]; bucket != null; bucket = bucket.next) {
            if (key.equals(bucket.key)) {
                return bucket.value;
            }
        }
        return null;
    }
}
