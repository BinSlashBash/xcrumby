/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.util.LinkedNode;
import java.lang.reflect.Array;
import java.util.List;

public final class ObjectBuffer {
    private static final int MAX_CHUNK = 262144;
    private static final int SMALL_CHUNK = 16384;
    private Object[] _freeBuffer;
    private LinkedNode<Object[]> _head;
    private int _size;
    private LinkedNode<Object[]> _tail;

    protected final void _copyTo(Object object, int n2, Object[] arrobject, int n3) {
        int n4 = 0;
        for (LinkedNode<Object[]> linkedNode = this._head; linkedNode != null; linkedNode = linkedNode.next()) {
            Object[] arrobject2 = linkedNode.value();
            int n5 = arrobject2.length;
            System.arraycopy(arrobject2, 0, object, n4, n5);
            n4 += n5;
        }
        System.arraycopy(arrobject, 0, object, n4, n3);
        n3 = n4 + n3;
        if (n3 != n2) {
            throw new IllegalStateException("Should have gotten " + n2 + " entries, got " + n3);
        }
    }

    protected void _reset() {
        if (this._tail != null) {
            this._freeBuffer = this._tail.value();
        }
        this._tail = null;
        this._head = null;
        this._size = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object[] appendCompletedChunk(Object[] arrobject) {
        int n2;
        LinkedNode<Object[]> linkedNode = new LinkedNode<Object[]>(arrobject, null);
        if (this._head == null) {
            this._tail = linkedNode;
            this._head = linkedNode;
        } else {
            this._tail.linkNext(linkedNode);
            this._tail = linkedNode;
        }
        int n3 = arrobject.length;
        this._size += n3;
        if (n3 < 16384) {
            n2 = n3 + n3;
            return new Object[n2];
        }
        n2 = n3;
        if (n3 >= 262144) return new Object[n2];
        n2 = n3 + (n3 >> 2);
        return new Object[n2];
    }

    public int bufferedSize() {
        return this._size;
    }

    public void completeAndClearBuffer(Object[] arrobject, int n2, List<Object> list) {
        int n3;
        for (LinkedNode<Object[]> linkedNode = this._head; linkedNode != null; linkedNode = linkedNode.next()) {
            Object[] arrobject2 = linkedNode.value();
            int n4 = arrobject2.length;
            for (n3 = 0; n3 < n4; ++n3) {
                list.add(arrobject2[n3]);
            }
        }
        for (n3 = 0; n3 < n2; ++n3) {
            list.add(arrobject[n3]);
        }
    }

    public Object[] completeAndClearBuffer(Object[] arrobject, int n2) {
        int n3 = n2 + this._size;
        Object[] arrobject2 = new Object[n3];
        this._copyTo(arrobject2, n3, arrobject, n2);
        return arrobject2;
    }

    public <T> T[] completeAndClearBuffer(Object[] arrobject, int n2, Class<T> arrobject2) {
        int n3 = n2 + this._size;
        arrobject2 = (Object[])Array.newInstance(arrobject2, n3);
        this._copyTo(arrobject2, n3, arrobject, n2);
        this._reset();
        return arrobject2;
    }

    public int initialCapacity() {
        if (this._freeBuffer == null) {
            return 0;
        }
        return this._freeBuffer.length;
    }

    public Object[] resetAndStart() {
        this._reset();
        if (this._freeBuffer == null) {
            return new Object[12];
        }
        return this._freeBuffer;
    }
}

