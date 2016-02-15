/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.format.DataFormatMatcher;
import com.fasterxml.jackson.core.format.MatchStrength;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public interface InputAccessor {
    public boolean hasMoreBytes() throws IOException;

    public byte nextByte() throws IOException;

    public void reset();

    public static class Std
    implements InputAccessor {
        protected final byte[] _buffer;
        protected int _bufferedEnd;
        protected final int _bufferedStart;
        protected final InputStream _in;
        protected int _ptr;

        public Std(InputStream inputStream, byte[] arrby) {
            this._in = inputStream;
            this._buffer = arrby;
            this._bufferedStart = 0;
            this._ptr = 0;
            this._bufferedEnd = 0;
        }

        public Std(byte[] arrby) {
            this._in = null;
            this._buffer = arrby;
            this._bufferedStart = 0;
            this._bufferedEnd = arrby.length;
        }

        public Std(byte[] arrby, int n2, int n3) {
            this._in = null;
            this._buffer = arrby;
            this._ptr = n2;
            this._bufferedStart = n2;
            this._bufferedEnd = n2 + n3;
        }

        public DataFormatMatcher createMatcher(JsonFactory jsonFactory, MatchStrength matchStrength) {
            return new DataFormatMatcher(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, jsonFactory, matchStrength);
        }

        @Override
        public boolean hasMoreBytes() throws IOException {
            if (this._ptr < this._bufferedEnd) {
                return true;
            }
            if (this._in == null) {
                return false;
            }
            int n2 = this._buffer.length - this._ptr;
            if (n2 < 1) {
                return false;
            }
            if ((n2 = this._in.read(this._buffer, this._ptr, n2)) <= 0) {
                return false;
            }
            this._bufferedEnd += n2;
            return true;
        }

        @Override
        public byte nextByte() throws IOException {
            if (this._ptr >= this._bufferedEnd && !this.hasMoreBytes()) {
                throw new EOFException("Failed auto-detect: could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
            }
            byte[] arrby = this._buffer;
            int n2 = this._ptr;
            this._ptr = n2 + 1;
            return arrby[n2];
        }

        @Override
        public void reset() {
            this._ptr = this._bufferedStart;
        }
    }

}

