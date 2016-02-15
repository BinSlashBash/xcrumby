/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class UTF32Reader
extends Reader {
    protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
    protected static final char NC = '\u0000';
    protected final boolean _bigEndian;
    protected byte[] _buffer;
    protected int _byteCount;
    protected int _charCount;
    protected final IOContext _context;
    protected InputStream _in;
    protected int _length;
    protected final boolean _managedBuffers;
    protected int _ptr;
    protected char _surrogate;
    protected char[] _tmpBuf;

    public UTF32Reader(IOContext iOContext, InputStream inputStream, byte[] arrby, int n2, int n3, boolean bl2) {
        boolean bl3 = false;
        this._surrogate = '\u0000';
        this._charCount = 0;
        this._byteCount = 0;
        this._tmpBuf = null;
        this._context = iOContext;
        this._in = inputStream;
        this._buffer = arrby;
        this._ptr = n2;
        this._length = n3;
        this._bigEndian = bl2;
        bl2 = bl3;
        if (inputStream != null) {
            bl2 = true;
        }
        this._managedBuffers = bl2;
    }

    private void freeBuffers() {
        byte[] arrby = this._buffer;
        if (arrby != null) {
            this._buffer = null;
            this._context.releaseReadIOBuffer(arrby);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean loadMore(int n2) throws IOException {
        this._byteCount += this._length - n2;
        if (n2 > 0) {
            if (this._ptr > 0) {
                System.arraycopy(this._buffer, this._ptr, this._buffer, 0, n2);
                this._ptr = 0;
            }
            this._length = n2;
        } else {
            this._ptr = 0;
            n2 = this._in == null ? -1 : this._in.read(this._buffer);
            if (n2 < 1) {
                this._length = 0;
                if (n2 < 0) {
                    if (this._managedBuffers) {
                        this.freeBuffers();
                    }
                    return false;
                }
                this.reportStrangeStream();
            }
            this._length = n2;
        }
        while (this._length < 4) {
            n2 = this._in == null ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
            if (n2 < 1) {
                if (n2 < 0) {
                    if (this._managedBuffers) {
                        this.freeBuffers();
                    }
                    this.reportUnexpectedEOF(this._length, 4);
                }
                this.reportStrangeStream();
            }
            this._length += n2;
        }
        return true;
    }

    private void reportBounds(char[] arrc, int n2, int n3) throws IOException {
        throw new ArrayIndexOutOfBoundsException("read(buf," + n2 + "," + n3 + "), cbuf[" + arrc.length + "]");
    }

    private void reportInvalid(int n2, int n3, String string2) throws IOException {
        int n4 = this._byteCount;
        int n5 = this._ptr;
        int n6 = this._charCount;
        throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(n2) + string2 + " at char #" + (n6 + n3) + ", byte #" + (n4 + n5 - 1) + ")");
    }

    private void reportStrangeStream() throws IOException {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }

    private void reportUnexpectedEOF(int n2, int n3) throws IOException {
        int n4 = this._byteCount;
        int n5 = this._charCount;
        throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + n2 + ", needed " + n3 + ", at char #" + n5 + ", byte #" + (n4 + n2) + ")");
    }

    @Override
    public void close() throws IOException {
        InputStream inputStream = this._in;
        if (inputStream != null) {
            this._in = null;
            this.freeBuffers();
            inputStream.close();
        }
    }

    @Override
    public int read() throws IOException {
        if (this._tmpBuf == null) {
            this._tmpBuf = new char[1];
        }
        if (this.read(this._tmpBuf, 0, 1) < 1) {
            return -1;
        }
        return this._tmpBuf[0];
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public int read(char[] arrc, int n2, int n3) throws IOException {
        if (this._buffer == null) {
            return -1;
        }
        if (n3 < 1) {
            return n3;
        }
        if (n2 < 0 || n2 + n3 > arrc.length) {
            this.reportBounds(arrc, n2, n3);
        }
        int n4 = n3 + n2;
        if (this._surrogate != '\u0000') {
            n3 = n2 + 1;
            arrc[n2] = this._surrogate;
            this._surrogate = '\u0000';
        } else {
            n3 = this._length - this._ptr;
            if (n3 < 4) {
                if (!this.loadMore(n3)) return -1;
            }
            n3 = n2;
        }
        while (n3 < n4) {
            int n5;
            int n6 = this._ptr;
            n6 = this._bigEndian ? this._buffer[n6] << 24 | (this._buffer[n6 + 1] & 255) << 16 | (this._buffer[n6 + 2] & 255) << 8 | this._buffer[n6 + 3] & 255 : this._buffer[n6] & 255 | (this._buffer[n6 + 1] & 255) << 8 | (this._buffer[n6 + 2] & 255) << 16 | this._buffer[n6 + 3] << 24;
            this._ptr += 4;
            if (n6 > 65535) {
                int n7;
                if (n6 > 1114111) {
                    this.reportInvalid(n6, n3 - n2, "(above " + Integer.toHexString(1114111) + ") ");
                }
                n5 = n6 - 65536;
                n6 = n3 + 1;
                arrc[n3] = (char)(55296 + (n5 >> 10));
                n5 = n7 = 56320 | n5 & 1023;
                n3 = n6;
                if (n6 >= n4) {
                    this._surrogate = (char)n7;
                    n3 = n6;
                    break;
                }
            } else {
                n5 = n6;
            }
            n6 = n3 + 1;
            arrc[n3] = (char)n5;
            n3 = n6;
            if (this._ptr < this._length) continue;
            n3 = n6;
            break;
        }
        n2 = n3 - n2;
        this._charCount += n2;
        return n2;
    }
}

