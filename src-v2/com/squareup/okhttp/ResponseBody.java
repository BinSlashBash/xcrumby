/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okio.BufferedSource;

public abstract class ResponseBody
implements Closeable {
    private Reader reader;

    private Charset charset() {
        MediaType mediaType = this.contentType();
        if (mediaType != null) {
            return mediaType.charset(Util.UTF_8);
        }
        return Util.UTF_8;
    }

    public final InputStream byteStream() {
        return this.source().inputStream();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final byte[] bytes() throws IOException {
        long l2 = this.contentLength();
        if (l2 > Integer.MAX_VALUE) {
            throw new IOException("Cannot buffer entire body for content length: " + l2);
        }
        BufferedSource bufferedSource = this.source();
        byte[] arrby = bufferedSource.readByteArray();
        if (l2 == -1 || l2 == (long)arrby.length) return arrby;
        throw new IOException("Content-Length and stream length disagree");
        finally {
            Util.closeQuietly(bufferedSource);
        }
    }

    public final Reader charStream() {
        Reader reader = this.reader;
        if (reader != null) {
            return reader;
        }
        this.reader = reader = new InputStreamReader(this.byteStream(), this.charset());
        return reader;
    }

    @Override
    public void close() throws IOException {
        this.source().close();
    }

    public abstract long contentLength();

    public abstract MediaType contentType();

    public abstract BufferedSource source();

    public final String string() throws IOException {
        return new String(this.bytes(), this.charset().name());
    }
}

