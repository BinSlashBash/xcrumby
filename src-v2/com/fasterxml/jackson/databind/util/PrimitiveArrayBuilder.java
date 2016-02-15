/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

public abstract class PrimitiveArrayBuilder<T> {
    static final int INITIAL_CHUNK_SIZE = 12;
    static final int MAX_CHUNK_SIZE = 262144;
    static final int SMALL_CHUNK_SIZE = 16384;
    protected Node<T> _bufferHead;
    protected Node<T> _bufferTail;
    protected int _bufferedEntryCount;
    protected T _freeBuffer;

    protected PrimitiveArrayBuilder() {
    }

    protected abstract T _constructArray(int var1);

    protected void _reset() {
        if (this._bufferTail != null) {
            this._freeBuffer = this._bufferTail.getData();
        }
        this._bufferTail = null;
        this._bufferHead = null;
        this._bufferedEntryCount = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final T appendCompletedChunk(T node, int n2) {
        node = new Node<Node<Object>>(node, n2);
        if (this._bufferHead == null) {
            this._bufferTail = node;
            this._bufferHead = node;
        } else {
            this._bufferTail.linkNext(node);
            this._bufferTail = node;
        }
        this._bufferedEntryCount += n2;
        if (n2 < 16384) {
            n2 += n2;
            return this._constructArray(n2);
        }
        n2 += n2 >> 2;
        return this._constructArray(n2);
    }

    public T completeAndClearBuffer(T t2, int n2) {
        int n3 = n2 + this._bufferedEntryCount;
        T t3 = this._constructArray(n3);
        int n4 = 0;
        for (Node<T> node = this._bufferHead; node != null; node = node.next()) {
            n4 = node.copyData(t3, n4);
        }
        System.arraycopy(t2, 0, t3, n4, n2);
        n2 = n4 + n2;
        if (n2 != n3) {
            throw new IllegalStateException("Should have gotten " + n3 + " entries, got " + n2);
        }
        return t3;
    }

    public T resetAndStart() {
        this._reset();
        if (this._freeBuffer == null) {
            return this._constructArray(12);
        }
        return this._freeBuffer;
    }

    static final class Node<T> {
        final T _data;
        final int _dataLength;
        Node<T> _next;

        public Node(T t2, int n2) {
            this._data = t2;
            this._dataLength = n2;
        }

        public int copyData(T t2, int n2) {
            System.arraycopy(this._data, 0, t2, n2, this._dataLength);
            return n2 + this._dataLength;
        }

        public T getData() {
            return this._data;
        }

        public void linkNext(Node<T> node) {
            if (this._next != null) {
                throw new IllegalStateException();
            }
            this._next = node;
        }

        public Node<T> next() {
            return this._next;
        }
    }

}

