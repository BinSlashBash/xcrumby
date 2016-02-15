/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

final class MarkableInputStream
extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private long defaultMark = -1;
    private final InputStream in;
    private long limit;
    private long offset;
    private long reset;

    public MarkableInputStream(InputStream inputStream) {
        this(inputStream, 4096);
    }

    public MarkableInputStream(InputStream inputStream, int n2) {
        InputStream inputStream2 = inputStream;
        if (!inputStream.markSupported()) {
            inputStream2 = new BufferedInputStream(inputStream, n2);
        }
        this.in = inputStream2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setLimit(long l2) {
        try {
            if (this.reset < this.offset && this.offset <= this.limit) {
                this.in.reset();
                this.in.mark((int)(l2 - this.reset));
                this.skip(this.reset, this.offset);
            } else {
                this.reset = this.offset;
                this.in.mark((int)(l2 - this.offset));
            }
            this.limit = l2;
            return;
        }
        catch (IOException var3_2) {
            throw new IllegalStateException("Unable to mark: " + var3_2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void skip(long l2, long l3) throws IOException {
        while (l2 < l3) {
            long l4;
            long l5 = l4 = this.in.skip(l3 - l2);
            if (l4 == 0) {
                if (this.read() == -1) {
                    return;
                }
                l5 = 1;
            }
            l2 += l5;
        }
    }

    @Override
    public int available() throws IOException {
        return this.in.available();
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }

    @Override
    public void mark(int n2) {
        this.defaultMark = this.savePosition(n2);
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }

    @Override
    public int read() throws IOException {
        int n2 = this.in.read();
        if (n2 != -1) {
            ++this.offset;
        }
        return n2;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        int n2 = this.in.read(arrby);
        if (n2 != -1) {
            this.offset += (long)n2;
        }
        return n2;
    }

    @Override
    public int read(byte[] arrby, int n2, int n3) throws IOException {
        if ((n2 = this.in.read(arrby, n2, n3)) != -1) {
            this.offset += (long)n2;
        }
        return n2;
    }

    @Override
    public void reset() throws IOException {
        this.reset(this.defaultMark);
    }

    public void reset(long l2) throws IOException {
        if (this.offset > this.limit || l2 < this.reset) {
            throw new IOException("Cannot reset");
        }
        this.in.reset();
        this.skip(this.reset, l2);
        this.offset = l2;
    }

    public long savePosition(int n2) {
        long l2 = this.offset + (long)n2;
        if (this.limit < l2) {
            this.setLimit(l2);
        }
        return this.offset;
    }

    @Override
    public long skip(long l2) throws IOException {
        l2 = this.in.skip(l2);
        this.offset += l2;
        return l2;
    }
}

