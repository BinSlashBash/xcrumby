/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUMap<K, V>
extends LinkedHashMap<K, V>
implements Serializable {
    private static final long serialVersionUID = 1;
    protected transient int _jdkSerializeMaxEntries;
    protected final transient int _maxEntries;
    protected final transient Lock _readLock;
    protected final transient Lock _writeLock;

    public LRUMap(int n2, int n3) {
        super(n2, 0.8f, true);
        this._maxEntries = n3;
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this._readLock = reentrantReadWriteLock.readLock();
        this._writeLock = reentrantReadWriteLock.writeLock();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        this._jdkSerializeMaxEntries = objectInputStream.readInt();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this._jdkSerializeMaxEntries);
    }

    @Override
    public void clear() {
        this._writeLock.lock();
        try {
            super.clear();
            return;
        }
        finally {
            this._writeLock.unlock();
        }
    }

    @Override
    public V get(Object object) {
        this._readLock.lock();
        try {
            object = super.get(object);
            return (V)object;
        }
        finally {
            this._readLock.unlock();
        }
    }

    @Override
    public V put(K object, V v2) {
        this._writeLock.lock();
        try {
            object = super.put(object, v2);
            return (V)object;
        }
        finally {
            this._writeLock.unlock();
        }
    }

    protected Object readResolve() {
        return new LRUMap<K, V>(this._jdkSerializeMaxEntries, this._jdkSerializeMaxEntries);
    }

    @Override
    public V remove(Object object) {
        this._writeLock.lock();
        try {
            object = super.remove(object);
            return (V)object;
        }
        finally {
            this._writeLock.unlock();
        }
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
        if (this.size() > this._maxEntries) {
            return true;
        }
        return false;
    }
}

