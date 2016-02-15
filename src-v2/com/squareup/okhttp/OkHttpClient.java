/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.AuthenticatorAdapter;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public final class OkHttpClient
implements Cloneable {
    private static SSLSocketFactory defaultSslSocketFactory;
    private Authenticator authenticator;
    private Cache cache;
    private int connectTimeout;
    private ConnectionPool connectionPool;
    private CookieHandler cookieHandler;
    private Dispatcher dispatcher = new Dispatcher();
    private boolean followSslRedirects = true;
    private HostnameVerifier hostnameVerifier;
    private InternalCache internalCache;
    private List<Protocol> protocols;
    private Proxy proxy;
    private ProxySelector proxySelector;
    private int readTimeout;
    private final RouteDatabase routeDatabase = new RouteDatabase();
    private SocketFactory socketFactory;
    private SSLSocketFactory sslSocketFactory;
    private int writeTimeout;

    static {
        Internal.instance = new Internal(){

            @Override
            public void addLine(Headers.Builder builder, String string2) {
                builder.addLine(string2);
            }

            @Override
            public boolean clearOwner(Connection connection) {
                return connection.clearOwner();
            }

            @Override
            public void closeIfOwnedBy(Connection connection, Object object) throws IOException {
                connection.closeIfOwnedBy(object);
            }

            @Override
            public void connect(Connection connection, int n2, int n3, int n4, Request request) throws IOException {
                connection.connect(n2, n3, n4, request);
            }

            @Override
            public Object getOwner(Connection connection) {
                return connection.getOwner();
            }

            @Override
            public InternalCache internalCache(OkHttpClient okHttpClient) {
                return okHttpClient.internalCache();
            }

            @Override
            public boolean isConnected(Connection connection) {
                return connection.isConnected();
            }

            @Override
            public boolean isReadable(Connection connection) {
                return connection.isReadable();
            }

            @Override
            public boolean isSpdy(Connection connection) {
                return connection.isSpdy();
            }

            @Override
            public Transport newTransport(Connection connection, HttpEngine httpEngine) throws IOException {
                return connection.newTransport(httpEngine);
            }

            @Override
            public void recycle(ConnectionPool connectionPool, Connection connection) {
                connectionPool.recycle(connection);
            }

            @Override
            public int recycleCount(Connection connection) {
                return connection.recycleCount();
            }

            @Override
            public RouteDatabase routeDatabase(OkHttpClient okHttpClient) {
                return okHttpClient.routeDatabase;
            }

            @Override
            public void setCache(OkHttpClient okHttpClient, InternalCache internalCache) {
                okHttpClient.setInternalCache(internalCache);
            }

            @Override
            public void setOwner(Connection connection, HttpEngine httpEngine) {
                connection.setOwner(httpEngine);
            }

            @Override
            public void setProtocol(Connection connection, Protocol protocol) {
                connection.setProtocol(protocol);
            }

            @Override
            public void setTimeouts(Connection connection, int n2, int n3) throws IOException {
                connection.setTimeouts(n2, n3);
            }

            @Override
            public void share(ConnectionPool connectionPool, Connection connection) {
                connectionPool.share(connection);
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SSLSocketFactory getDefaultSSLSocketFactory() {
        synchronized (this) {
            Object object = defaultSslSocketFactory;
            if (object != null) return defaultSslSocketFactory;
            try {
                object = SSLContext.getInstance("TLS");
                object.init(null, null, null);
                defaultSslSocketFactory = object.getSocketFactory();
            }
            catch (GeneralSecurityException var1_2) {
                throw new AssertionError();
            }
            return defaultSslSocketFactory;
        }
    }

    public OkHttpClient cancel(Object object) {
        this.dispatcher.cancel(object);
        return this;
    }

    public OkHttpClient clone() {
        try {
            OkHttpClient okHttpClient = (OkHttpClient)super.clone();
            return okHttpClient;
        }
        catch (CloneNotSupportedException var1_2) {
            throw new AssertionError();
        }
    }

    OkHttpClient copyWithDefaults() {
        OkHttpClient okHttpClient = this.clone();
        if (okHttpClient.proxySelector == null) {
            okHttpClient.proxySelector = ProxySelector.getDefault();
        }
        if (okHttpClient.cookieHandler == null) {
            okHttpClient.cookieHandler = CookieHandler.getDefault();
        }
        if (okHttpClient.socketFactory == null) {
            okHttpClient.socketFactory = SocketFactory.getDefault();
        }
        if (okHttpClient.sslSocketFactory == null) {
            okHttpClient.sslSocketFactory = this.getDefaultSSLSocketFactory();
        }
        if (okHttpClient.hostnameVerifier == null) {
            okHttpClient.hostnameVerifier = OkHostnameVerifier.INSTANCE;
        }
        if (okHttpClient.authenticator == null) {
            okHttpClient.authenticator = AuthenticatorAdapter.INSTANCE;
        }
        if (okHttpClient.connectionPool == null) {
            okHttpClient.connectionPool = ConnectionPool.getDefault();
        }
        if (okHttpClient.protocols == null) {
            okHttpClient.protocols = Util.immutableList(new Protocol[]{Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1});
        }
        return okHttpClient;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public Cache getCache() {
        return this.cache;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public CookieHandler getCookieHandler() {
        return this.cookieHandler;
    }

    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    public boolean getFollowSslRedirects() {
        return this.followSslRedirects;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<Protocol> getProtocols() {
        return this.protocols;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    RouteDatabase getRoutesDatabase() {
        return this.routeDatabase;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    InternalCache internalCache() {
        return this.internalCache;
    }

    public Call newCall(Request request) {
        return new Call(this.copyWithDefaults(), this.dispatcher, request);
    }

    public OkHttpClient setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public OkHttpClient setCache(Cache object) {
        this.cache = object;
        object = object != null ? object.internalCache : null;
        this.internalCache = object;
        return this;
    }

    public void setConnectTimeout(long l2, TimeUnit timeUnit) {
        if (l2 < 0) {
            throw new IllegalArgumentException("timeout < 0");
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        }
        if ((l2 = timeUnit.toMillis(l2)) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Timeout too large.");
        }
        this.connectTimeout = (int)l2;
    }

    public OkHttpClient setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public OkHttpClient setCookieHandler(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
        return this;
    }

    public OkHttpClient setDispatcher(Dispatcher dispatcher) {
        if (dispatcher == null) {
            throw new IllegalArgumentException("dispatcher == null");
        }
        this.dispatcher = dispatcher;
        return this;
    }

    public OkHttpClient setFollowSslRedirects(boolean bl2) {
        this.followSslRedirects = bl2;
        return this;
    }

    public OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    void setInternalCache(InternalCache internalCache) {
        this.internalCache = internalCache;
        this.cache = null;
    }

    public OkHttpClient setProtocols(List<Protocol> list) {
        if (!(list = Util.immutableList(list)).contains((Object)Protocol.HTTP_1_1)) {
            throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + list);
        }
        if (list.contains(null)) {
            throw new IllegalArgumentException("protocols must not contain null");
        }
        this.protocols = Util.immutableList(list);
        return this;
    }

    public OkHttpClient setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public OkHttpClient setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        return this;
    }

    public void setReadTimeout(long l2, TimeUnit timeUnit) {
        if (l2 < 0) {
            throw new IllegalArgumentException("timeout < 0");
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        }
        if ((l2 = timeUnit.toMillis(l2)) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Timeout too large.");
        }
        this.readTimeout = (int)l2;
    }

    public OkHttpClient setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
        return this;
    }

    public OkHttpClient setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
        return this;
    }

    public void setWriteTimeout(long l2, TimeUnit timeUnit) {
        if (l2 < 0) {
            throw new IllegalArgumentException("timeout < 0");
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        }
        if ((l2 = timeUnit.toMillis(l2)) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Timeout too large.");
        }
        this.writeTimeout = (int)l2;
    }

}

