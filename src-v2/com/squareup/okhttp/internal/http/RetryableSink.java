/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.ProtocolException;
import okio.Buffer;
import okio.BufferedSink;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class RetryableSink
implements Sink {
    private boolean closed;
    private final Buffer content = new Buffer();
    private final int limit;

    public RetryableSink() {
        this(-1);
    }

    public RetryableSink(int n2) {
        this.limit = n2;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (this.content.size() >= (long)this.limit) return;
        throw new ProtocolException("content-length promised " + this.limit + " bytes, but received " + this.content.size());
    }

    public long contentLength() throws IOException {
        return this.content.size();
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public Timeout timeout() {
        return Timeout.NONE;
    }

    @Override
    public void write(Buffer buffer, long l2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        Util.checkOffsetAndCount(buffer.size(), 0, l2);
        if (this.limit != -1 && this.content.size() > (long)this.limit - l2) {
            throw new ProtocolException("exceeded content-length limit of " + this.limit + " bytes");
        }
        this.content.write(buffer, l2);
    }

    public void writeToSocket(BufferedSink bufferedSink) throws IOException {
        bufferedSink.writeAll((Source)this.content.clone());
    }
}

