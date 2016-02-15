/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteBufferBackedOutputStream
extends OutputStream {
    protected final ByteBuffer _b;

    public ByteBufferBackedOutputStream(ByteBuffer byteBuffer) {
        this._b = byteBuffer;
    }

    @Override
    public void write(int n2) throws IOException {
        this._b.put((byte)n2);
    }

    @Override
    public void write(byte[] arrby, int n2, int n3) throws IOException {
        this._b.put(arrby, n2, n3);
    }
}

