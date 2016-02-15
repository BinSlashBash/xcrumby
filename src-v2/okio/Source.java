/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.Closeable;
import java.io.IOException;
import okio.Buffer;
import okio.Timeout;

public interface Source
extends Closeable {
    @Override
    public void close() throws IOException;

    public long read(Buffer var1, long var2) throws IOException;

    public Timeout timeout();
}

