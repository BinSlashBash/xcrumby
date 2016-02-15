package okio;

import java.io.Closeable;
import java.io.IOException;

public interface Sink extends Closeable {
    void close() throws IOException;

    void flush() throws IOException;

    Timeout timeout();

    void write(Buffer buffer, long j) throws IOException;
}
