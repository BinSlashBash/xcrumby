/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.Settings;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import okio.Buffer;

public interface FrameWriter
extends Closeable {
    public void ackSettings() throws IOException;

    public void connectionPreface() throws IOException;

    public void data(boolean var1, int var2, Buffer var3) throws IOException;

    public void data(boolean var1, int var2, Buffer var3, int var4) throws IOException;

    public void flush() throws IOException;

    public void goAway(int var1, ErrorCode var2, byte[] var3) throws IOException;

    public void headers(int var1, List<Header> var2) throws IOException;

    public void ping(boolean var1, int var2, int var3) throws IOException;

    public void pushPromise(int var1, int var2, List<Header> var3) throws IOException;

    public void rstStream(int var1, ErrorCode var2) throws IOException;

    public void settings(Settings var1) throws IOException;

    public void synReply(boolean var1, int var2, List<Header> var3) throws IOException;

    public void synStream(boolean var1, boolean var2, int var3, int var4, List<Header> var5) throws IOException;

    public void windowUpdate(int var1, long var2) throws IOException;
}

