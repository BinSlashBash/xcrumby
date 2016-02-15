/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.InputStream;

public final class MergedStream
extends InputStream {
    private byte[] _b;
    private final IOContext _ctxt;
    private final int _end;
    private final InputStream _in;
    private int _ptr;

    public MergedStream(IOContext iOContext, InputStream inputStream, byte[] arrby, int n2, int n3) {
        this._ctxt = iOContext;
        this._in = inputStream;
        this._b = arrby;
        this._ptr = n2;
        this._end = n3;
    }

    private void _free() {
        byte[] arrby = this._b;
        if (arrby != null) {
            this._b = null;
            if (this._ctxt != null) {
                this._ctxt.releaseReadIOBuffer(arrby);
            }
        }
    }

    @Override
    public int available() throws IOException {
        if (this._b != null) {
            return this._end - this._ptr;
        }
        return this._in.available();
    }

    @Override
    public void close() throws IOException {
        this._free();
        this._in.close();
    }

    @Override
    public void mark(int n2) {
        if (this._b == null) {
            this._in.mark(n2);
        }
    }

    @Override
    public boolean markSupported() {
        if (this._b == null && this._in.markSupported()) {
            return true;
        }
        return false;
    }

    @Override
    public int read() throws IOException {
        if (this._b != null) {
            byte[] arrby = this._b;
            int n2 = this._ptr;
            this._ptr = n2 + 1;
            n2 = arrby[n2];
            if (this._ptr >= this._end) {
                this._free();
            }
            return n2 & 255;
        }
        return this._in.read();
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n2, int n3) throws IOException {
        if (this._b != null) {
            int n4 = this._end - this._ptr;
            int n5 = n3;
            if (n3 > n4) {
                n5 = n4;
            }
            System.arraycopy(this._b, this._ptr, arrby, n2, n5);
            this._ptr += n5;
            if (this._ptr >= this._end) {
                this._free();
            }
            return n5;
        }
        return this._in.read(arrby, n2, n3);
    }

    @Override
    public void reset() throws IOException {
        if (this._b == null) {
            this._in.reset();
        }
    }

    @Override
    public long skip(long l2) throws IOException {
        long l3 = 0;
        long l4 = l2;
        if (this._b != null) {
            int n2 = this._end - this._ptr;
            if ((long)n2 > l2) {
                this._ptr += (int)l2;
                return l2;
            }
            this._free();
            l3 = 0 + (long)n2;
            l4 = l2 - (long)n2;
        }
        l2 = l3;
        if (l4 > 0) {
            l2 = l3 + this._in.skip(l4);
        }
        return l2;
    }
}

