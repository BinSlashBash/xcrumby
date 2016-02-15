/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 */
package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

public interface Downloader {
    public Response load(Uri var1, boolean var2) throws IOException;

    public static class Response {
        final Bitmap bitmap;
        final boolean cached;
        final long contentLength;
        final InputStream stream;

        @Deprecated
        public Response(Bitmap bitmap, boolean bl2) {
            this(bitmap, bl2, -1);
        }

        public Response(Bitmap bitmap, boolean bl2, long l2) {
            if (bitmap == null) {
                throw new IllegalArgumentException("Bitmap may not be null.");
            }
            this.stream = null;
            this.bitmap = bitmap;
            this.cached = bl2;
            this.contentLength = l2;
        }

        @Deprecated
        public Response(InputStream inputStream, boolean bl2) {
            this(inputStream, bl2, -1);
        }

        public Response(InputStream inputStream, boolean bl2, long l2) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Stream may not be null.");
            }
            this.stream = inputStream;
            this.bitmap = null;
            this.cached = bl2;
            this.contentLength = l2;
        }

        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public long getContentLength() {
            return this.contentLength;
        }

        public InputStream getInputStream() {
            return this.stream;
        }
    }

    public static class ResponseException
    extends IOException {
        public ResponseException(String string2) {
            super(string2);
        }
    }

}

