package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okio.BufferedSink;
import org.apache.commons.codec.binary.Hex;
import org.json.HTTP;

public final class MultipartBuilder {
    public static final MediaType ALTERNATIVE;
    public static final MediaType DIGEST;
    public static final MediaType FORM;
    public static final MediaType MIXED;
    public static final MediaType PARALLEL;
    private final String boundary;
    private final List<RequestBody> partBodies;
    private final List<Headers> partHeaders;
    private MediaType type;

    private static final class MultipartRequestBody extends RequestBody {
        private final String boundary;
        private final MediaType contentType;
        private final List<RequestBody> partBodies;
        private final List<Headers> partHeaders;

        public MultipartRequestBody(MediaType type, String boundary, List<Headers> partHeaders, List<RequestBody> partBodies) {
            if (type == null) {
                throw new NullPointerException("type == null");
            }
            this.boundary = boundary;
            this.contentType = MediaType.parse(type + "; boundary=" + boundary);
            this.partHeaders = Util.immutableList((List) partHeaders);
            this.partBodies = Util.immutableList((List) partBodies);
        }

        public MediaType contentType() {
            return this.contentType;
        }

        public void writeTo(BufferedSink sink) throws IOException {
            byte[] boundary = this.boundary.getBytes(Hex.DEFAULT_CHARSET_NAME);
            boolean first = true;
            for (int i = 0; i < this.partHeaders.size(); i++) {
                Headers headers = (Headers) this.partHeaders.get(i);
                RequestBody body = (RequestBody) this.partBodies.get(i);
                writeBoundary(sink, boundary, first, false);
                writePart(sink, headers, body);
                first = false;
            }
            writeBoundary(sink, boundary, false, true);
        }

        private static void writeBoundary(BufferedSink sink, byte[] boundary, boolean first, boolean last) throws IOException {
            if (!first) {
                sink.writeUtf8(HTTP.CRLF);
            }
            sink.writeUtf8("--");
            sink.write(boundary);
            if (last) {
                sink.writeUtf8("--");
            } else {
                sink.writeUtf8(HTTP.CRLF);
            }
        }

        private void writePart(BufferedSink sink, Headers headers, RequestBody body) throws IOException {
            if (headers != null) {
                for (int i = 0; i < headers.size(); i++) {
                    sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8(HTTP.CRLF);
                }
            }
            MediaType contentType = body.contentType();
            if (contentType != null) {
                sink.writeUtf8("Content-Type: ").writeUtf8(contentType.toString()).writeUtf8(HTTP.CRLF);
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                sink.writeUtf8("Content-Length: ").writeUtf8(Long.toString(contentLength)).writeUtf8(HTTP.CRLF);
            }
            sink.writeUtf8(HTTP.CRLF);
            body.writeTo(sink);
        }
    }

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

    public MultipartBuilder(String boundary) {
        this.type = MIXED;
        this.partHeaders = new ArrayList();
        this.partBodies = new ArrayList();
        this.boundary = boundary;
    }

    public MultipartBuilder type(MediaType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        } else if (type.type().equals("multipart")) {
            this.type = type;
            return this;
        } else {
            throw new IllegalArgumentException("multipart != " + type);
        }
    }

    public MultipartBuilder addPart(RequestBody body) {
        return addPart(null, body);
    }

    public MultipartBuilder addPart(Headers headers, RequestBody body) {
        if (body == null) {
            throw new NullPointerException("body == null");
        } else if (headers != null && headers.get("Content-Type") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Type");
        } else if (headers == null || headers.get("Content-Length") == null) {
            this.partHeaders.add(headers);
            this.partBodies.add(body);
            return this;
        } else {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
    }

    public RequestBody build() {
        if (!this.partHeaders.isEmpty()) {
            return new MultipartRequestBody(this.type, this.boundary, this.partHeaders, this.partBodies);
        }
        throw new IllegalStateException("Multipart body must have at least one part.");
    }
}
