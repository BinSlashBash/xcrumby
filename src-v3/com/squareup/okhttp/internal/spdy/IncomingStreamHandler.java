package com.squareup.okhttp.internal.spdy;

import java.io.IOException;

public interface IncomingStreamHandler {
    public static final IncomingStreamHandler REFUSE_INCOMING_STREAMS;

    /* renamed from: com.squareup.okhttp.internal.spdy.IncomingStreamHandler.1 */
    static class C11521 implements IncomingStreamHandler {
        C11521() {
        }

        public void receive(SpdyStream stream) throws IOException {
            stream.close(ErrorCode.REFUSED_STREAM);
        }
    }

    void receive(SpdyStream spdyStream) throws IOException;

    static {
        REFUSE_INCOMING_STREAMS = new C11521();
    }
}
