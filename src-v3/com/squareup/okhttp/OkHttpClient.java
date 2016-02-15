package com.squareup.okhttp;

import com.squareup.okhttp.Headers.Builder;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public final class OkHttpClient implements Cloneable {
    private static SSLSocketFactory defaultSslSocketFactory;
    private Authenticator authenticator;
    private Cache cache;
    private int connectTimeout;
    private ConnectionPool connectionPool;
    private CookieHandler cookieHandler;
    private Dispatcher dispatcher;
    private boolean followSslRedirects;
    private HostnameVerifier hostnameVerifier;
    private InternalCache internalCache;
    private List<Protocol> protocols;
    private Proxy proxy;
    private ProxySelector proxySelector;
    private int readTimeout;
    private final RouteDatabase routeDatabase;
    private SocketFactory socketFactory;
    private SSLSocketFactory sslSocketFactory;
    private int writeTimeout;

    /* renamed from: com.squareup.okhttp.OkHttpClient.1 */
    static class C11461 extends Internal {
        C11461() {
        }

        public Transport newTransport(Connection connection, HttpEngine httpEngine) throws IOException {
            return connection.newTransport(httpEngine);
        }

        public boolean clearOwner(Connection connection) {
            return connection.clearOwner();
        }

        public void closeIfOwnedBy(Connection connection, Object owner) throws IOException {
            connection.closeIfOwnedBy(owner);
        }

        public int recycleCount(Connection connection) {
            return connection.recycleCount();
        }

        public Object getOwner(Connection connection) {
            return connection.getOwner();
        }

        public void setProtocol(Connection connection, Protocol protocol) {
            connection.setProtocol(protocol);
        }

        public void setOwner(Connection connection, HttpEngine httpEngine) {
            connection.setOwner(httpEngine);
        }

        public void connect(Connection connection, int connectTimeout, int readTimeout, int writeTimeout, Request request) throws IOException {
            connection.connect(connectTimeout, readTimeout, writeTimeout, request);
        }

        public boolean isConnected(Connection connection) {
            return connection.isConnected();
        }

        public boolean isSpdy(Connection connection) {
            return connection.isSpdy();
        }

        public void setTimeouts(Connection connection, int readTimeout, int writeTimeout) throws IOException {
            connection.setTimeouts(readTimeout, writeTimeout);
        }

        public boolean isReadable(Connection pooled) {
            return pooled.isReadable();
        }

        public void addLine(Builder builder, String line) {
            builder.addLine(line);
        }

        public void setCache(OkHttpClient client, InternalCache internalCache) {
            client.setInternalCache(internalCache);
        }

        public InternalCache internalCache(OkHttpClient client) {
            return client.internalCache();
        }

        public void recycle(ConnectionPool pool, Connection connection) {
            pool.recycle(connection);
        }

        public void share(ConnectionPool connectionPool, Connection connection) {
            connectionPool.share(connection);
        }

        public RouteDatabase routeDatabase(OkHttpClient client) {
            return client.routeDatabase;
        }
    }

    static {
        Internal.instance = new C11461();
    }

    public OkHttpClient() {
        this.followSslRedirects = true;
        this.routeDatabase = new RouteDatabase();
        this.dispatcher = new Dispatcher();
    }

    public void setConnectTimeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.connectTimeout = (int) millis;
        }
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setReadTimeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.readTimeout = (int) millis;
        }
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setWriteTimeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.writeTimeout = (int) millis;
        }
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    public OkHttpClient setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public OkHttpClient setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        return this;
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public OkHttpClient setCookieHandler(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
        return this;
    }

    public CookieHandler getCookieHandler() {
        return this.cookieHandler;
    }

    void setInternalCache(InternalCache internalCache) {
        this.internalCache = internalCache;
        this.cache = null;
    }

    InternalCache internalCache() {
        return this.internalCache;
    }

    public OkHttpClient setCache(Cache cache) {
        this.cache = cache;
        this.internalCache = cache != null ? cache.internalCache : null;
        return this;
    }

    public Cache getCache() {
        return this.cache;
    }

    public OkHttpClient setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
        return this;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public OkHttpClient setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public OkHttpClient setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public OkHttpClient setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public OkHttpClient setFollowSslRedirects(boolean followProtocolRedirects) {
        this.followSslRedirects = followProtocolRedirects;
        return this;
    }

    public boolean getFollowSslRedirects() {
        return this.followSslRedirects;
    }

    RouteDatabase getRoutesDatabase() {
        return this.routeDatabase;
    }

    public OkHttpClient setDispatcher(Dispatcher dispatcher) {
        if (dispatcher == null) {
            throw new IllegalArgumentException("dispatcher == null");
        }
        this.dispatcher = dispatcher;
        return this;
    }

    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    public OkHttpClient setProtocols(List<Protocol> protocols) {
        List protocols2 = Util.immutableList((List) protocols);
        if (!protocols2.contains(Protocol.HTTP_1_1)) {
            throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + protocols2);
        } else if (protocols2.contains(null)) {
            throw new IllegalArgumentException("protocols must not contain null");
        } else {
            this.protocols = Util.immutableList(protocols2);
            return this;
        }
    }

    public List<Protocol> getProtocols() {
        return this.protocols;
    }

    public Call newCall(Request request) {
        return new Call(copyWithDefaults(), this.dispatcher, request);
    }

    public OkHttpClient cancel(Object tag) {
        this.dispatcher.cancel(tag);
        return this;
    }

    OkHttpClient copyWithDefaults() {
        OkHttpClient result = clone();
        if (result.proxySelector == null) {
            result.proxySelector = ProxySelector.getDefault();
        }
        if (result.cookieHandler == null) {
            result.cookieHandler = CookieHandler.getDefault();
        }
        if (result.socketFactory == null) {
            result.socketFactory = SocketFactory.getDefault();
        }
        if (result.sslSocketFactory == null) {
            result.sslSocketFactory = getDefaultSSLSocketFactory();
        }
        if (result.hostnameVerifier == null) {
            result.hostnameVerifier = OkHostnameVerifier.INSTANCE;
        }
        if (result.authenticator == null) {
            result.authenticator = AuthenticatorAdapter.INSTANCE;
        }
        if (result.connectionPool == null) {
            result.connectionPool = ConnectionPool.getDefault();
        }
        if (result.protocols == null) {
            result.protocols = Util.immutableList(Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1);
        }
        return result;
    }

    private synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
        if (defaultSslSocketFactory == null) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, null, null);
                defaultSslSocketFactory = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new AssertionError();
            }
        }
        return defaultSslSocketFactory;
    }

    public OkHttpClient clone() {
        try {
            return (OkHttpClient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
