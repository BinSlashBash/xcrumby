package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Dns;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;

public final class RouteSelector {
    public static final String SSL_V3 = "SSLv3";
    public static final String TLS_V1 = "TLSv1";
    private final Address address;
    private final Dns dns;
    private boolean hasNextProxy;
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private int nextSocketAddressIndex;
    private String nextTlsVersion;
    private final ConnectionPool pool;
    private final List<Route> postponedRoutes;
    private final ProxySelector proxySelector;
    private Iterator<Proxy> proxySelectorProxies;
    private final RouteDatabase routeDatabase;
    private InetAddress[] socketAddresses;
    private int socketPort;
    private final URI uri;
    private Proxy userSpecifiedProxy;

    public RouteSelector(Address address, URI uri, ProxySelector proxySelector, ConnectionPool pool, Dns dns, RouteDatabase routeDatabase) {
        this.address = address;
        this.uri = uri;
        this.proxySelector = proxySelector;
        this.pool = pool;
        this.dns = dns;
        this.routeDatabase = routeDatabase;
        this.postponedRoutes = new LinkedList();
        resetNextProxy(uri, address.getProxy());
    }

    public boolean hasNext() {
        return hasNextTlsVersion() || hasNextInetSocketAddress() || hasNextProxy() || hasNextPostponed();
    }

    public Connection next(String method) throws IOException {
        while (true) {
            Connection pooled = this.pool.get(this.address);
            if (pooled == null) {
                break;
            } else if (method.equals("GET") || Internal.instance.isReadable(pooled)) {
                return pooled;
            } else {
                pooled.getSocket().close();
            }
        }
        if (!hasNextTlsVersion()) {
            if (!hasNextInetSocketAddress()) {
                if (hasNextProxy()) {
                    this.lastProxy = nextProxy();
                    resetNextInetSocketAddress(this.lastProxy);
                } else if (hasNextPostponed()) {
                    return new Connection(this.pool, nextPostponed());
                } else {
                    throw new NoSuchElementException();
                }
            }
            this.lastInetSocketAddress = nextInetSocketAddress();
            resetNextTlsVersion();
        }
        Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, nextTlsVersion());
        if (!this.routeDatabase.shouldPostpone(route)) {
            return new Connection(this.pool, route);
        }
        this.postponedRoutes.add(route);
        return next(method);
    }

    public void connectFailed(Connection connection, IOException failure) {
        if (Internal.instance.recycleCount(connection) <= 0) {
            Route failedRoute = connection.getRoute();
            if (!(failedRoute.getProxy().type() == Type.DIRECT || this.proxySelector == null)) {
                this.proxySelector.connectFailed(this.uri, failedRoute.getProxy().address(), failure);
            }
            this.routeDatabase.failed(failedRoute);
            if (!(failure instanceof SSLHandshakeException) && !(failure instanceof SSLProtocolException)) {
                while (hasNextTlsVersion()) {
                    this.routeDatabase.failed(new Route(this.address, this.lastProxy, this.lastInetSocketAddress, nextTlsVersion()));
                }
            }
        }
    }

    private void resetNextProxy(URI uri, Proxy proxy) {
        this.hasNextProxy = true;
        if (proxy != null) {
            this.userSpecifiedProxy = proxy;
            return;
        }
        List<Proxy> proxyList = this.proxySelector.select(uri);
        if (proxyList != null) {
            this.proxySelectorProxies = proxyList.iterator();
        }
    }

    private boolean hasNextProxy() {
        return this.hasNextProxy;
    }

    private Proxy nextProxy() {
        if (this.userSpecifiedProxy != null) {
            this.hasNextProxy = false;
            return this.userSpecifiedProxy;
        }
        if (this.proxySelectorProxies != null) {
            while (this.proxySelectorProxies.hasNext()) {
                Proxy candidate = (Proxy) this.proxySelectorProxies.next();
                if (candidate.type() != Type.DIRECT) {
                    return candidate;
                }
            }
        }
        this.hasNextProxy = false;
        return Proxy.NO_PROXY;
    }

    private void resetNextInetSocketAddress(Proxy proxy) throws UnknownHostException {
        String socketHost;
        this.socketAddresses = null;
        if (proxy.type() == Type.DIRECT) {
            socketHost = this.uri.getHost();
            this.socketPort = Util.getEffectivePort(this.uri);
        } else {
            SocketAddress proxyAddress = proxy.address();
            if (proxyAddress instanceof InetSocketAddress) {
                InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
                socketHost = proxySocketAddress.getHostName();
                this.socketPort = proxySocketAddress.getPort();
            } else {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
            }
        }
        this.socketAddresses = this.dns.getAllByName(socketHost);
        this.nextSocketAddressIndex = 0;
    }

    private boolean hasNextInetSocketAddress() {
        return this.socketAddresses != null;
    }

    private InetSocketAddress nextInetSocketAddress() throws UnknownHostException {
        InetAddress[] inetAddressArr = this.socketAddresses;
        int i = this.nextSocketAddressIndex;
        this.nextSocketAddressIndex = i + 1;
        InetSocketAddress result = new InetSocketAddress(inetAddressArr[i], this.socketPort);
        if (this.nextSocketAddressIndex == this.socketAddresses.length) {
            this.socketAddresses = null;
            this.nextSocketAddressIndex = 0;
        }
        return result;
    }

    private void resetNextTlsVersion() {
        this.nextTlsVersion = this.address.getSslSocketFactory() != null ? TLS_V1 : SSL_V3;
    }

    private boolean hasNextTlsVersion() {
        return this.nextTlsVersion != null;
    }

    private String nextTlsVersion() {
        if (this.nextTlsVersion == null) {
            throw new IllegalStateException("No next TLS version");
        } else if (this.nextTlsVersion.equals(TLS_V1)) {
            this.nextTlsVersion = SSL_V3;
            return TLS_V1;
        } else if (this.nextTlsVersion.equals(SSL_V3)) {
            this.nextTlsVersion = null;
            return SSL_V3;
        } else {
            throw new AssertionError();
        }
    }

    private boolean hasNextPostponed() {
        return !this.postponedRoutes.isEmpty();
    }

    private Route nextPostponed() {
        return (Route) this.postponedRoutes.remove(0);
    }
}
