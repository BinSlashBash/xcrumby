package com.squareup.okhttp.internal;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.Transport;
import java.io.IOException;

public abstract class Internal {
    public static Internal instance;

    public abstract void addLine(Builder builder, String str);

    public abstract boolean clearOwner(Connection connection);

    public abstract void closeIfOwnedBy(Connection connection, Object obj) throws IOException;

    public abstract void connect(Connection connection, int i, int i2, int i3, Request request) throws IOException;

    public abstract Object getOwner(Connection connection);

    public abstract InternalCache internalCache(OkHttpClient okHttpClient);

    public abstract boolean isConnected(Connection connection);

    public abstract boolean isReadable(Connection connection);

    public abstract boolean isSpdy(Connection connection);

    public abstract Transport newTransport(Connection connection, HttpEngine httpEngine) throws IOException;

    public abstract void recycle(ConnectionPool connectionPool, Connection connection);

    public abstract int recycleCount(Connection connection);

    public abstract RouteDatabase routeDatabase(OkHttpClient okHttpClient);

    public abstract void setCache(OkHttpClient okHttpClient, InternalCache internalCache);

    public abstract void setOwner(Connection connection, HttpEngine httpEngine);

    public abstract void setProtocol(Connection connection, Protocol protocol);

    public abstract void setTimeouts(Connection connection, int i, int i2) throws IOException;

    public abstract void share(ConnectionPool connectionPool, Connection connection);
}
