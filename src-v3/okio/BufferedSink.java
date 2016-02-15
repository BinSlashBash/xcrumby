package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public interface BufferedSink extends Sink {
    Buffer buffer();

    BufferedSink emitCompleteSegments() throws IOException;

    OutputStream outputStream();

    BufferedSink write(ByteString byteString) throws IOException;

    BufferedSink write(byte[] bArr) throws IOException;

    BufferedSink write(byte[] bArr, int i, int i2) throws IOException;

    long writeAll(Source source) throws IOException;

    BufferedSink writeByte(int i) throws IOException;

    BufferedSink writeInt(int i) throws IOException;

    BufferedSink writeIntLe(int i) throws IOException;

    BufferedSink writeLong(long j) throws IOException;

    BufferedSink writeLongLe(long j) throws IOException;

    BufferedSink writeShort(int i) throws IOException;

    BufferedSink writeShortLe(int i) throws IOException;

    BufferedSink writeString(String str, Charset charset) throws IOException;

    BufferedSink writeUtf8(String str) throws IOException;
}
