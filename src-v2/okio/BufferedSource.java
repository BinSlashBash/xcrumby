/*
 * Decompiled with CFR 0_110.
 */
package okio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.ByteString;
import okio.Sink;
import okio.Source;

public interface BufferedSource
extends Source {
    public Buffer buffer();

    public boolean exhausted() throws IOException;

    public long indexOf(byte var1) throws IOException;

    public InputStream inputStream();

    public int read(byte[] var1) throws IOException;

    public int read(byte[] var1, int var2, int var3) throws IOException;

    public long readAll(Sink var1) throws IOException;

    public byte readByte() throws IOException;

    public byte[] readByteArray() throws IOException;

    public byte[] readByteArray(long var1) throws IOException;

    public ByteString readByteString() throws IOException;

    public ByteString readByteString(long var1) throws IOException;

    public void readFully(Buffer var1, long var2) throws IOException;

    public void readFully(byte[] var1) throws IOException;

    public int readInt() throws IOException;

    public int readIntLe() throws IOException;

    public long readLong() throws IOException;

    public long readLongLe() throws IOException;

    public short readShort() throws IOException;

    public short readShortLe() throws IOException;

    public String readString(long var1, Charset var3) throws IOException;

    public String readString(Charset var1) throws IOException;

    public String readUtf8() throws IOException;

    public String readUtf8(long var1) throws IOException;

    public String readUtf8Line() throws IOException;

    public String readUtf8LineStrict() throws IOException;

    public void require(long var1) throws IOException;

    public void skip(long var1) throws IOException;
}

