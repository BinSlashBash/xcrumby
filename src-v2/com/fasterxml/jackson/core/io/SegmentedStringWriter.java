/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.Writer;

public final class SegmentedStringWriter
extends Writer {
    protected final TextBuffer _buffer;

    public SegmentedStringWriter(BufferRecycler bufferRecycler) {
        this._buffer = new TextBuffer(bufferRecycler);
    }

    @Override
    public Writer append(char c2) {
        this.write(c2);
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence) {
        charSequence = charSequence.toString();
        this._buffer.append((String)charSequence, 0, charSequence.length());
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence, int n2, int n3) {
        charSequence = charSequence.subSequence(n2, n3).toString();
        this._buffer.append((String)charSequence, 0, charSequence.length());
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    public String getAndClear() {
        String string2 = this._buffer.contentsAsString();
        this._buffer.releaseBuffers();
        return string2;
    }

    @Override
    public void write(int n2) {
        this._buffer.append((char)n2);
    }

    @Override
    public void write(String string2) {
        this._buffer.append(string2, 0, string2.length());
    }

    @Override
    public void write(String string2, int n2, int n3) {
        this._buffer.append(string2, n2, n3);
    }

    @Override
    public void write(char[] arrc) {
        this._buffer.append(arrc, 0, arrc.length);
    }

    @Override
    public void write(char[] arrc, int n2, int n3) {
        this._buffer.append(arrc, n2, n3);
    }
}

