/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.IOException;
import okio.Buffer;
import okio.Sink;
import okio.Timeout;

public abstract class ForwardingSink
implements Sink {
    private final Sink delegate;

    public ForwardingSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = sink;
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    public final Sink delegate() {
        return this.delegate;
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public Timeout timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }

    @Override
    public void write(Buffer buffer, long l2) throws IOException {
        this.delegate.write(buffer, l2);
    }
}

