/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

public class BufferRecycler {
    public static final int BYTE_BASE64_CODEC_BUFFER = 3;
    private static final int[] BYTE_BUFFER_LENGTHS = new int[]{8000, 8000, 2000, 2000};
    public static final int BYTE_READ_IO_BUFFER = 0;
    public static final int BYTE_WRITE_CONCAT_BUFFER = 2;
    public static final int BYTE_WRITE_ENCODING_BUFFER = 1;
    private static final int[] CHAR_BUFFER_LENGTHS = new int[]{4000, 4000, 200, 200};
    public static final int CHAR_CONCAT_BUFFER = 1;
    public static final int CHAR_NAME_COPY_BUFFER = 3;
    public static final int CHAR_TEXT_BUFFER = 2;
    public static final int CHAR_TOKEN_BUFFER = 0;
    protected final byte[][] _byteBuffers;
    protected final char[][] _charBuffers;

    public BufferRecycler() {
        this(4, 4);
    }

    protected BufferRecycler(int n2, int n3) {
        this._byteBuffers = new byte[n2][];
        this._charBuffers = new char[n3][];
    }

    public final byte[] allocByteBuffer(int n2) {
        return this.allocByteBuffer(n2, 0);
    }

    public byte[] allocByteBuffer(int n2, int n3) {
        byte[] arrby;
        int n4 = this.byteBufferLength(n2);
        int n5 = n3;
        if (n3 < n4) {
            n5 = n4;
        }
        if ((arrby = this._byteBuffers[n2]) == null || arrby.length < n5) {
            return this.balloc(n5);
        }
        this._byteBuffers[n2] = null;
        return arrby;
    }

    public final char[] allocCharBuffer(int n2) {
        return this.allocCharBuffer(n2, 0);
    }

    public char[] allocCharBuffer(int n2, int n3) {
        char[] arrc;
        int n4 = this.charBufferLength(n2);
        int n5 = n3;
        if (n3 < n4) {
            n5 = n4;
        }
        if ((arrc = this._charBuffers[n2]) == null || arrc.length < n5) {
            return this.calloc(n5);
        }
        this._charBuffers[n2] = null;
        return arrc;
    }

    protected byte[] balloc(int n2) {
        return new byte[n2];
    }

    protected int byteBufferLength(int n2) {
        return BYTE_BUFFER_LENGTHS[n2];
    }

    protected char[] calloc(int n2) {
        return new char[n2];
    }

    protected int charBufferLength(int n2) {
        return CHAR_BUFFER_LENGTHS[n2];
    }

    public final void releaseByteBuffer(int n2, byte[] arrby) {
        this._byteBuffers[n2] = arrby;
    }

    public void releaseCharBuffer(int n2, char[] arrc) {
        this._charBuffers[n2] = arrc;
    }
}

