/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.internal.Util;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public abstract class RequestBody {
    public static RequestBody create(final MediaType mediaType, final File file) {
        if (file == null) {
            throw new NullPointerException("content == null");
        }
        return new RequestBody(){

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                Source source;
                Source source2 = null;
                try {
                    source2 = source = Okio.source(file);
                }
                catch (Throwable var1_2) {
                    Util.closeQuietly(source2);
                    throw var1_2;
                }
                bufferedSink.writeAll(source);
                Util.closeQuietly(source);
            }
        };
    }

    public static RequestBody create(MediaType mediaType, String string2) {
        Charset charset = Util.UTF_8;
        MediaType mediaType2 = mediaType;
        if (mediaType != null) {
            Charset charset2;
            charset = charset2 = mediaType.charset();
            mediaType2 = mediaType;
            if (charset2 == null) {
                charset = Util.UTF_8;
                mediaType2 = MediaType.parse(mediaType + "; charset=utf-8");
            }
        }
        return RequestBody.create(mediaType2, string2.getBytes(charset));
    }

    public static RequestBody create(final MediaType mediaType, final byte[] arrby) {
        if (arrby == null) {
            throw new NullPointerException("content == null");
        }
        return new RequestBody(){

            @Override
            public long contentLength() {
                return arrby.length;
            }

            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(arrby);
            }
        };
    }

    public long contentLength() {
        return -1;
    }

    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink var1) throws IOException;

}

