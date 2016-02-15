/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.ByteString;
import okio.Sink;
import okio.Source;

public interface BufferedSink
extends Sink {
    public Buffer buffer();

    public BufferedSink emitCompleteSegments() throws IOException;

    public OutputStream outputStream();

    public BufferedSink write(ByteString var1) throws IOException;

    public BufferedSink write(byte[] var1) throws IOException;

    public BufferedSink write(byte[] var1, int var2, int var3) throws IOException;

    public long writeAll(Source var1) throws IOException;

    public BufferedSink writeByte(int var1) throws IOException;

    public BufferedSink writeInt(int var1) throws IOException;

    public BufferedSink writeIntLe(int var1) throws IOException;

    public BufferedSink writeLong(long var1) throws IOException;

    public BufferedSink writeLongLe(long var1) throws IOException;

    public BufferedSink writeShort(int var1) throws IOException;

    public BufferedSink writeShortLe(int var1) throws IOException;

    public BufferedSink writeString(String var1, Charset var2) throws IOException;

    public BufferedSink writeUtf8(String var1) throws IOException;
}

