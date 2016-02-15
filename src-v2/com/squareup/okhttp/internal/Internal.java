/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.Transport;
import java.io.IOException;

public abstract class Internal {
    public static Internal instance;

    public abstract void addLine(Headers.Builder var1, String var2);

    public abstract boolean clearOwner(Connection var1);

    public abstract void closeIfOwnedBy(Connection var1, Object var2) throws IOException;

    public abstract void connect(Connection var1, int var2, int var3, int var4, Request var5) throws IOException;

    public abstract Object getOwner(Connection var1);

    public abstract InternalCache internalCache(OkHttpClient var1);

    public abstract boolean isConnected(Connection var1);

    public abstract boolean isReadable(Connection var1);

    public abstract boolean isSpdy(Connection var1);

    public abstract Transport newTransport(Connection var1, HttpEngine var2) throws IOException;

    public abstract void recycle(ConnectionPool var1, Connection var2);

    public abstract int recycleCount(Connection var1);

    public abstract RouteDatabase routeDatabase(OkHttpClient var1);

    public abstract void setCache(OkHttpClient var1, InternalCache var2);

    public abstract void setOwner(Connection var1, HttpEngine var2);

    public abstract void setProtocol(Connection var1, Protocol var2);

    public abstract void setTimeouts(Connection var1, int var2, int var3) throws IOException;

    public abstract void share(ConnectionPool var1, Connection var2);
}

