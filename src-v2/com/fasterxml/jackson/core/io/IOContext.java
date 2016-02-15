/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;

public class IOContext {
    protected byte[] _base64Buffer = null;
    protected final BufferRecycler _bufferRecycler;
    protected char[] _concatCBuffer = null;
    protected JsonEncoding _encoding;
    protected final boolean _managedResource;
    protected char[] _nameCopyBuffer = null;
    protected byte[] _readIOBuffer = null;
    protected final Object _sourceRef;
    protected char[] _tokenCBuffer = null;
    protected byte[] _writeEncodingBuffer = null;

    public IOContext(BufferRecycler bufferRecycler, Object object, boolean bl2) {
        this._bufferRecycler = bufferRecycler;
        this._sourceRef = object;
        this._managedResource = bl2;
    }

    private IllegalArgumentException wrongBuf() {
        return new IllegalArgumentException("Trying to release buffer not owned by the context");
    }

    protected final void _verifyAlloc(Object object) {
        if (object != null) {
            throw new IllegalStateException("Trying to call same allocXxx() method second time");
        }
    }

    protected final void _verifyRelease(byte[] arrby, byte[] arrby2) {
        if (arrby != arrby2 && arrby.length <= arrby2.length) {
            throw this.wrongBuf();
        }
    }

    protected final void _verifyRelease(char[] arrc, char[] arrc2) {
        if (arrc != arrc2 && arrc.length <= arrc2.length) {
            throw this.wrongBuf();
        }
    }

    public byte[] allocBase64Buffer() {
        byte[] arrby;
        this._verifyAlloc(this._base64Buffer);
        this._base64Buffer = arrby = this._bufferRecycler.allocByteBuffer(3);
        return arrby;
    }

    public char[] allocConcatBuffer() {
        char[] arrc;
        this._verifyAlloc(this._concatCBuffer);
        this._concatCBuffer = arrc = this._bufferRecycler.allocCharBuffer(1);
        return arrc;
    }

    public char[] allocNameCopyBuffer(int n2) {
        char[] arrc;
        this._verifyAlloc(this._nameCopyBuffer);
        this._nameCopyBuffer = arrc = this._bufferRecycler.allocCharBuffer(3, n2);
        return arrc;
    }

    public byte[] allocReadIOBuffer() {
        byte[] arrby;
        this._verifyAlloc(this._readIOBuffer);
        this._readIOBuffer = arrby = this._bufferRecycler.allocByteBuffer(0);
        return arrby;
    }

    public byte[] allocReadIOBuffer(int n2) {
        byte[] arrby;
        this._verifyAlloc(this._readIOBuffer);
        this._readIOBuffer = arrby = this._bufferRecycler.allocByteBuffer(0, n2);
        return arrby;
    }

    public char[] allocTokenBuffer() {
        char[] arrc;
        this._verifyAlloc(this._tokenCBuffer);
        this._tokenCBuffer = arrc = this._bufferRecycler.allocCharBuffer(0);
        return arrc;
    }

    public char[] allocTokenBuffer(int n2) {
        char[] arrc;
        this._verifyAlloc(this._tokenCBuffer);
        this._tokenCBuffer = arrc = this._bufferRecycler.allocCharBuffer(0, n2);
        return arrc;
    }

    public byte[] allocWriteEncodingBuffer() {
        byte[] arrby;
        this._verifyAlloc(this._writeEncodingBuffer);
        this._writeEncodingBuffer = arrby = this._bufferRecycler.allocByteBuffer(1);
        return arrby;
    }

    public byte[] allocWriteEncodingBuffer(int n2) {
        byte[] arrby;
        this._verifyAlloc(this._writeEncodingBuffer);
        this._writeEncodingBuffer = arrby = this._bufferRecycler.allocByteBuffer(1, n2);
        return arrby;
    }

    public TextBuffer constructTextBuffer() {
        return new TextBuffer(this._bufferRecycler);
    }

    public JsonEncoding getEncoding() {
        return this._encoding;
    }

    public Object getSourceReference() {
        return this._sourceRef;
    }

    public boolean isResourceManaged() {
        return this._managedResource;
    }

    public void releaseBase64Buffer(byte[] arrby) {
        if (arrby != null) {
            this._verifyRelease(arrby, this._base64Buffer);
            this._base64Buffer = null;
            this._bufferRecycler.releaseByteBuffer(3, arrby);
        }
    }

    public void releaseConcatBuffer(char[] arrc) {
        if (arrc != null) {
            this._verifyRelease(arrc, this._concatCBuffer);
            this._concatCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(1, arrc);
        }
    }

    public void releaseNameCopyBuffer(char[] arrc) {
        if (arrc != null) {
            this._verifyRelease(arrc, this._nameCopyBuffer);
            this._nameCopyBuffer = null;
            this._bufferRecycler.releaseCharBuffer(3, arrc);
        }
    }

    public void releaseReadIOBuffer(byte[] arrby) {
        if (arrby != null) {
            this._verifyRelease(arrby, this._readIOBuffer);
            this._readIOBuffer = null;
            this._bufferRecycler.releaseByteBuffer(0, arrby);
        }
    }

    public void releaseTokenBuffer(char[] arrc) {
        if (arrc != null) {
            this._verifyRelease(arrc, this._tokenCBuffer);
            this._tokenCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(0, arrc);
        }
    }

    public void releaseWriteEncodingBuffer(byte[] arrby) {
        if (arrby != null) {
            this._verifyRelease(arrby, this._writeEncodingBuffer);
            this._writeEncodingBuffer = null;
            this._bufferRecycler.releaseByteBuffer(1, arrby);
        }
    }

    public void setEncoding(JsonEncoding jsonEncoding) {
        this._encoding = jsonEncoding;
    }
}

