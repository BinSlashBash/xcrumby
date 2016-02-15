/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.IOException;
import okio.Buffer;
import okio.Source;
import okio.Timeout;

public abstract class ForwardingSource
implements Source {
    private final Source delegate;

    public ForwardingSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = source;
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    public final Source delegate() {
        return this.delegate;
    }

    @Override
    public long read(Buffer buffer, long l2) throws IOException {
        return this.delegate.read(buffer, l2);
    }

    @Override
    public Timeout timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}

