/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.Closeable;
import java.io.IOException;
import okio.Buffer;
import okio.Timeout;

public interface Sink
extends Closeable {
    @Override
    public void close() throws IOException;

    public void flush() throws IOException;

    public Timeout timeout();

    public void write(Buffer var1, long var2) throws IOException;
}

