package com.jakewharton.disklrucache;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class StrictLineReader implements Closeable {
    private static final byte CR = (byte) 13;
    private static final byte LF = (byte) 10;
    private byte[] buf;
    private final Charset charset;
    private int end;
    private final InputStream in;
    private int pos;

    /* renamed from: com.jakewharton.disklrucache.StrictLineReader.1 */
    class C05801 extends ByteArrayOutputStream {
        C05801(int x0) {
            super(x0);
        }

        public String toString() {
            int length = (this.count <= 0 || this.buf[this.count - 1] != 13) ? this.count : this.count - 1;
            try {
                return new String(this.buf, 0, length, StrictLineReader.this.charset.name());
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
    }

    public StrictLineReader(InputStream in, Charset charset) {
        this(in, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, charset);
    }

    public StrictLineReader(InputStream in, int capacity, Charset charset) {
        if (in == null || charset == null) {
            throw new NullPointerException();
        } else if (capacity < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(Util.US_ASCII)) {
            this.in = in;
            this.charset = charset;
            this.buf = new byte[capacity];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public void close() throws IOException {
        synchronized (this.in) {
            if (this.buf != null) {
                this.buf = null;
                this.in.close();
            }
        }
    }

    public String readLine() throws IOException {
        String res;
        synchronized (this.in) {
            if (this.buf == null) {
                throw new IOException("LineReader is closed");
            }
            if (this.pos >= this.end) {
                fillBuf();
            }
            int i = this.pos;
            while (i != this.end) {
                if (this.buf[i] == LF) {
                    int lineEnd = (i == this.pos || this.buf[i - 1] != 13) ? i : i - 1;
                    res = new String(this.buf, this.pos, lineEnd - this.pos, this.charset.name());
                    this.pos = i + 1;
                } else {
                    i++;
                }
            }
            ByteArrayOutputStream out = new C05801((this.end - this.pos) + 80);
            loop1:
            while (true) {
                out.write(this.buf, this.pos, this.end - this.pos);
                this.end = -1;
                fillBuf();
                i = this.pos;
                while (i != this.end) {
                    if (this.buf[i] == LF) {
                        break loop1;
                    }
                    i++;
                }
            }
            if (i != this.pos) {
                out.write(this.buf, this.pos, i - this.pos);
            }
            this.pos = i + 1;
            res = out.toString();
        }
        return res;
    }

    private void fillBuf() throws IOException {
        int result = this.in.read(this.buf, 0, this.buf.length);
        if (result == -1) {
            throw new EOFException();
        }
        this.pos = 0;
        this.end = result;
    }
}
