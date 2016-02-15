/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okio.BufferedSink;

public final class MultipartBuilder {
    public static final MediaType ALTERNATIVE;
    public static final MediaType DIGEST;
    public static final MediaType FORM;
    public static final MediaType MIXED;
    public static final MediaType PARALLEL;
    private final String boundary;
    private final List<RequestBody> partBodies = new ArrayList<RequestBody>();
    private final List<Headers> partHeaders = new ArrayList<Headers>();
    private MediaType type = MIXED;

    static {
        MIXED = MediaType.parse("multipart/mixed");
        ALTERNATIVE = MediaType.parse("multipart/alternative");
        DIGEST = MediaType.parse("multipart/digest");
        PARALLEL = MediaType.parse("multipart/parallel");
        FORM = MediaType.parse("multipart/form-data");
    }

    public MultipartBuilder() {
        this(UUID.randomUUID().toString());
    }

    public MultipartBuilder(String string2) {
        this.boundary = string2;
    }

    public MultipartBuilder addPart(Headers headers, RequestBody requestBody) {
        if (requestBody == null) {
            throw new NullPointerException("body == null");
        }
        if (headers != null && headers.get("Content-Type") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Type");
        }
        if (headers != null && headers.get("Content-Length") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
        this.partHeaders.add(headers);
        this.partBodies.add(requestBody);
        return this;
    }

    public MultipartBuilder addPart(RequestBody requestBody) {
        return this.addPart(null, requestBody);
    }

    public RequestBody build() {
        if (this.partHeaders.isEmpty()) {
            throw new IllegalStateException("Multipart body must have at least one part.");
        }
        return new MultipartRequestBody(this.type, this.boundary, this.partHeaders, this.partBodies);
    }

    public MultipartBuilder type(MediaType mediaType) {
        if (mediaType == null) {
            throw new NullPointerException("type == null");
        }
        if (!mediaType.type().equals("multipart")) {
            throw new IllegalArgumentException("multipart != " + mediaType);
        }
        this.type = mediaType;
        return this;
    }

    private static final class MultipartRequestBody
    extends RequestBody {
        private final String boundary;
        private final MediaType contentType;
        private final List<RequestBody> partBodies;
        private final List<Headers> partHeaders;

        public MultipartRequestBody(MediaType mediaType, String string2, List<Headers> list, List<RequestBody> list2) {
            if (mediaType == null) {
                throw new NullPointerException("type == null");
            }
            this.boundary = string2;
            this.contentType = MediaType.parse(mediaType + "; boundary=" + string2);
            this.partHeaders = Util.immutableList(list);
            this.partBodies = Util.immutableList(list2);
        }

        private static void writeBoundary(BufferedSink bufferedSink, byte[] arrby, boolean bl2, boolean bl3) throws IOException {
            if (!bl2) {
                bufferedSink.writeUtf8("\r\n");
            }
            bufferedSink.writeUtf8("--");
            bufferedSink.write(arrby);
            if (bl3) {
                bufferedSink.writeUtf8("--");
                return;
            }
            bufferedSink.writeUtf8("\r\n");
        }

        private void writePart(BufferedSink bufferedSink, Headers object, RequestBody requestBody) throws IOException {
            long l2;
            if (object != null) {
                for (int i2 = 0; i2 < object.size(); ++i2) {
                    bufferedSink.writeUtf8(object.name(i2)).writeUtf8(": ").writeUtf8(object.value(i2)).writeUtf8("\r\n");
                }
            }
            if ((object = requestBody.contentType()) != null) {
                bufferedSink.writeUtf8("Content-Type: ").writeUtf8(object.toString()).writeUtf8("\r\n");
            }
            if ((l2 = requestBody.contentLength()) != -1) {
                bufferedSink.writeUtf8("Content-Length: ").writeUtf8(Long.toString(l2)).writeUtf8("\r\n");
            }
            bufferedSink.writeUtf8("\r\n");
            requestBody.writeTo(bufferedSink);
        }

        @Override
        public MediaType contentType() {
            return this.contentType;
        }

        @Override
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            byte[] arrby = this.boundary.getBytes("UTF-8");
            boolean bl2 = true;
            for (int i2 = 0; i2 < this.partHeaders.size(); ++i2) {
                Headers headers = this.partHeaders.get(i2);
                RequestBody requestBody = this.partBodies.get(i2);
                MultipartRequestBody.writeBoundary(bufferedSink, arrby, bl2, false);
                this.writePart(bufferedSink, headers, requestBody);
                bl2 = false;
            }
            MultipartRequestBody.writeBoundary(bufferedSink, arrby, false, true);
        }
    }

}

