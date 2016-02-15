/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.util.BufferRecycler;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder
extends OutputStream {
    static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
    private static final int INITIAL_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 262144;
    private static final byte[] NO_BYTES = new byte[0];
    private final BufferRecycler _bufferRecycler;
    private byte[] _currBlock;
    private int _currBlockPtr;
    private final LinkedList<byte[]> _pastBlocks = new LinkedList();
    private int _pastLen;

    public ByteArrayBuilder() {
        this(null);
    }

    public ByteArrayBuilder(int n2) {
        this(null, n2);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler) {
        this(bufferRecycler, 500);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ByteArrayBuilder(BufferRecycler bufferRecycler, int n2) {
        void var1_3;
        this._bufferRecycler = bufferRecycler;
        if (bufferRecycler == null) {
            void var2_5;
            byte[] arrby = new byte[var2_5];
        } else {
            byte[] arrby = bufferRecycler.allocByteBuffer(2);
        }
        this._currBlock = var1_3;
    }

    private void _allocMore() {
        int n2;
        this._pastLen += this._currBlock.length;
        int n3 = n2 = Math.max(this._pastLen >> 1, 1000);
        if (n2 > 262144) {
            n3 = 262144;
        }
        this._pastBlocks.add(this._currBlock);
        this._currBlock = new byte[n3];
        this._currBlockPtr = 0;
    }

    public void append(int n2) {
        if (this._currBlockPtr >= this._currBlock.length) {
            this._allocMore();
        }
        byte[] arrby = this._currBlock;
        int n3 = this._currBlockPtr;
        this._currBlockPtr = n3 + 1;
        arrby[n3] = (byte)n2;
    }

    public void appendThreeBytes(int n2) {
        if (this._currBlockPtr + 2 < this._currBlock.length) {
            byte[] arrby = this._currBlock;
            int n3 = this._currBlockPtr;
            this._currBlockPtr = n3 + 1;
            arrby[n3] = (byte)(n2 >> 16);
            arrby = this._currBlock;
            n3 = this._currBlockPtr;
            this._currBlockPtr = n3 + 1;
            arrby[n3] = (byte)(n2 >> 8);
            arrby = this._currBlock;
            n3 = this._currBlockPtr;
            this._currBlockPtr = n3 + 1;
            arrby[n3] = (byte)n2;
            return;
        }
        this.append(n2 >> 16);
        this.append(n2 >> 8);
        this.append(n2);
    }

    public void appendTwoBytes(int n2) {
        if (this._currBlockPtr + 1 < this._currBlock.length) {
            byte[] arrby = this._currBlock;
            int n3 = this._currBlockPtr;
            this._currBlockPtr = n3 + 1;
            arrby[n3] = (byte)(n2 >> 8);
            arrby = this._currBlock;
            n3 = this._currBlockPtr;
            this._currBlockPtr = n3 + 1;
            arrby[n3] = (byte)n2;
            return;
        }
        this.append(n2 >> 8);
        this.append(n2);
    }

    @Override
    public void close() {
    }

    public byte[] completeAndCoalesce(int n2) {
        this._currBlockPtr = n2;
        return this.toByteArray();
    }

    public byte[] finishCurrentSegment() {
        this._allocMore();
        return this._currBlock;
    }

    @Override
    public void flush() {
    }

    public byte[] getCurrentSegment() {
        return this._currBlock;
    }

    public int getCurrentSegmentLength() {
        return this._currBlockPtr;
    }

    public void release() {
        this.reset();
        if (this._bufferRecycler != null && this._currBlock != null) {
            this._bufferRecycler.releaseByteBuffer(2, this._currBlock);
            this._currBlock = null;
        }
    }

    public void reset() {
        this._pastLen = 0;
        this._currBlockPtr = 0;
        if (!this._pastBlocks.isEmpty()) {
            this._pastBlocks.clear();
        }
    }

    public byte[] resetAndGetFirstSegment() {
        this.reset();
        return this._currBlock;
    }

    public void setCurrentSegmentLength(int n2) {
        this._currBlockPtr = n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public byte[] toByteArray() {
        int n2 = this._pastLen + this._currBlockPtr;
        if (n2 == 0) {
            return NO_BYTES;
        }
        byte[] arrby = new byte[n2];
        int n3 = 0;
        Object object = this._pastBlocks.iterator();
        while (object.hasNext()) {
            byte[] arrby2 = (byte[])object.next();
            int n4 = arrby2.length;
            System.arraycopy(arrby2, 0, arrby, n3, n4);
            n3 += n4;
        }
        System.arraycopy(this._currBlock, 0, arrby, n3, this._currBlockPtr);
        if ((n3 += this._currBlockPtr) != n2) {
            throw new RuntimeException("Internal error: total len assumed to be " + n2 + ", copied " + n3 + " bytes");
        }
        object = arrby;
        if (this._pastBlocks.isEmpty()) return object;
        this.reset();
        return arrby;
    }

    @Override
    public void write(int n2) {
        this.append(n2);
    }

    @Override
    public void write(byte[] arrby) {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n2, int n3) {
        int n4 = n2;
        do {
            int n5 = Math.min(this._currBlock.length - this._currBlockPtr, n3);
            int n6 = n4;
            n2 = n3;
            if (n5 > 0) {
                System.arraycopy(arrby, n4, this._currBlock, this._currBlockPtr, n5);
                n6 = n4 + n5;
                this._currBlockPtr += n5;
                n2 = n3 - n5;
            }
            if (n2 <= 0) {
                return;
            }
            this._allocMore();
            n4 = n6;
            n3 = n2;
        } while (true);
    }
}

