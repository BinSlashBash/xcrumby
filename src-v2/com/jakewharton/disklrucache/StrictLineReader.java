/*
 * Decompiled with CFR 0_110.
 */
package com.jakewharton.disklrucache;

import com.jakewharton.disklrucache.Util;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class StrictLineReader
implements Closeable {
    private static final byte CR = 13;
    private static final byte LF = 10;
    private byte[] buf;
    private final Charset charset;
    private int end;
    private final InputStream in;
    private int pos;

    public StrictLineReader(InputStream inputStream, int n2, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        if (!charset.equals(Util.US_ASCII)) {
            throw new IllegalArgumentException("Unsupported encoding");
        }
        this.in = inputStream;
        this.charset = charset;
        this.buf = new byte[n2];
    }

    public StrictLineReader(InputStream inputStream, Charset charset) {
        this(inputStream, 8192, charset);
    }

    private void fillBuf() throws IOException {
        int n2 = this.in.read(this.buf, 0, this.buf.length);
        if (n2 == -1) {
            throw new EOFException();
        }
        this.pos = 0;
        this.end = n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        InputStream inputStream = this.in;
        synchronized (inputStream) {
            if (this.buf != null) {
                this.buf = null;
                this.in.close();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String readLine() throws IOException {
        InputStream inputStream = this.in;
        synchronized (inputStream) {
            if (this.buf == null) {
                throw new IOException("LineReader is closed");
            }
            if (this.pos >= this.end) {
                this.fillBuf();
            }
            int n2 = this.pos;
            do {
                if (n2 == this.end) break;
                if (this.buf[n2] == 10) {
                    int n3 = n2 != this.pos && this.buf[n2 - 1] == 13 ? n2 - 1 : n2;
                    String string2 = new String(this.buf, this.pos, n3 - this.pos, this.charset.name());
                    this.pos = n2 + 1;
                    return string2;
                }
                ++n2;
            } while (true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.end - this.pos + 80){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public String toString() {
                    int n2 = this.count > 0 && this.buf[this.count - 1] == 13 ? this.count - 1 : this.count;
                    try {
                        return new String(this.buf, 0, n2, StrictLineReader.this.charset.name());
                    }
                    catch (UnsupportedEncodingException var2_3) {
                        throw new AssertionError(var2_3);
                    }
                }
            };
            block5 : do {
                byteArrayOutputStream.write(this.buf, this.pos, this.end - this.pos);
                this.end = -1;
                this.fillBuf();
                n2 = this.pos;
                do {
                    if (n2 == this.end) continue block5;
                    if (this.buf[n2] == 10) {
                        if (n2 != this.pos) {
                            byteArrayOutputStream.write(this.buf, this.pos, n2 - this.pos);
                        }
                        this.pos = n2 + 1;
                        return byteArrayOutputStream.toString();
                    }
                    ++n2;
                } while (true);
                break;
            } while (true);
        }
    }

}

