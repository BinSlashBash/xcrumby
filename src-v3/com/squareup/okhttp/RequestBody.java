package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import okio.BufferedSink;
import okio.Okio;

public abstract class RequestBody {

    /* renamed from: com.squareup.okhttp.RequestBody.1 */
    static class C11471 extends RequestBody {
        final /* synthetic */ byte[] val$content;
        final /* synthetic */ MediaType val$contentType;

        C11471(MediaType mediaType, byte[] bArr) {
            this.val$contentType = mediaType;
            this.val$content = bArr;
        }

        public MediaType contentType() {
            return this.val$contentType;
        }

        public long contentLength() {
            return (long) this.val$content.length;
        }

        public void writeTo(BufferedSink sink) throws IOException {
            sink.write(this.val$content);
        }
    }

    /* renamed from: com.squareup.okhttp.RequestBody.2 */
    static class C11482 extends RequestBody {
        final /* synthetic */ MediaType val$contentType;
        final /* synthetic */ File val$file;

        C11482(MediaType mediaType, File file) {
            this.val$contentType = mediaType;
            this.val$file = file;
        }

        public MediaType contentType() {
            return this.val$contentType;
        }

        public long contentLength() {
            return this.val$file.length();
        }

        public void writeTo(BufferedSink sink) throws IOException {
            Closeable source = null;
            try {
                source = Okio.source(this.val$file);
                sink.writeAll(source);
            } finally {
                Util.closeQuietly(source);
            }
        }
    }

    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink bufferedSink) throws IOException;

    public long contentLength() {
        return -1;
    }

    public static RequestBody create(MediaType contentType, String content) {
        Charset charset = Util.UTF_8;
        if (contentType != null) {
            charset = contentType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                contentType = MediaType.parse(contentType + "; charset=utf-8");
            }
        }
        return create(contentType, content.getBytes(charset));
    }

    public static RequestBody create(MediaType contentType, byte[] content) {
        if (content != null) {
            return new C11471(contentType, content);
        }
        throw new NullPointerException("content == null");
    }

    public static RequestBody create(MediaType contentType, File file) {
        if (file != null) {
            return new C11482(contentType, file);
        }
        throw new NullPointerException("content == null");
    }
}
