/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.HeadersMode;
import com.squareup.okhttp.internal.spdy.Settings;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import okio.BufferedSource;
import okio.ByteString;

public interface FrameReader
extends Closeable {
    public boolean nextFrame(Handler var1) throws IOException;

    public void readConnectionPreface() throws IOException;

    public static interface Handler {
        public void ackSettings();

        public void alternateService(int var1, String var2, ByteString var3, String var4, int var5, long var6);

        public void data(boolean var1, int var2, BufferedSource var3, int var4) throws IOException;

        public void goAway(int var1, ErrorCode var2, ByteString var3);

        public void headers(boolean var1, boolean var2, int var3, int var4, List<Header> var5, HeadersMode var6);

        public void ping(boolean var1, int var2, int var3);

        public void priority(int var1, int var2, int var3, boolean var4);

        public void pushPromise(int var1, int var2, List<Header> var3) throws IOException;

        public void rstStream(int var1, ErrorCode var2);

        public void settings(boolean var1, Settings var2);

        public void windowUpdate(int var1, long var2);
    }

}

