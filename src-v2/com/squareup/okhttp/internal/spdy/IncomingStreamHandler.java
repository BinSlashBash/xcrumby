/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import java.io.IOException;

public interface IncomingStreamHandler {
    public static final IncomingStreamHandler REFUSE_INCOMING_STREAMS = new IncomingStreamHandler(){

        @Override
        public void receive(SpdyStream spdyStream) throws IOException {
            spdyStream.close(ErrorCode.REFUSED_STREAM);
        }
    };

    public void receive(SpdyStream var1) throws IOException;

}

