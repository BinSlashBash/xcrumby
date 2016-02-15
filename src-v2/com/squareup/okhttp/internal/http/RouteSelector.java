/*
 * Decompiled with CFR 0_110.
 */
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
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocketFactory;

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

    public RouteSelector(Address address, URI uRI, ProxySelector proxySelector, ConnectionPool connectionPool, Dns dns, RouteDatabase routeDatabase) {
        this.address = address;
        this.uri = uRI;
        this.proxySelector = proxySelector;
        this.pool = connectionPool;
        this.dns = dns;
        this.routeDatabase = routeDatabase;
        this.postponedRoutes = new LinkedList<Route>();
        this.resetNextProxy(uRI, address.getProxy());
    }

    private boolean hasNextInetSocketAddress() {
        if (this.socketAddresses != null) {
            return true;
        }
        return false;
    }

    private boolean hasNextPostponed() {
        if (!this.postponedRoutes.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean hasNextProxy() {
        return this.hasNextProxy;
    }

    private boolean hasNextTlsVersion() {
        if (this.nextTlsVersion != null) {
            return true;
        }
        return false;
    }

    private InetSocketAddress nextInetSocketAddress() throws UnknownHostException {
        Object object = this.socketAddresses;
        int n2 = this.nextSocketAddressIndex;
        this.nextSocketAddressIndex = n2 + 1;
        object = new InetSocketAddress(object[n2], this.socketPort);
        if (this.nextSocketAddressIndex == this.socketAddresses.length) {
            this.socketAddresses = null;
            this.nextSocketAddressIndex = 0;
        }
        return object;
    }

    private Route nextPostponed() {
        return this.postponedRoutes.remove(0);
    }

    private Proxy nextProxy() {
        if (this.userSpecifiedProxy != null) {
            this.hasNextProxy = false;
            return this.userSpecifiedProxy;
        }
        if (this.proxySelectorProxies != null) {
            while (this.proxySelectorProxies.hasNext()) {
                Proxy proxy = this.proxySelectorProxies.next();
                if (proxy.type() == Proxy.Type.DIRECT) continue;
                return proxy;
            }
        }
        this.hasNextProxy = false;
        return Proxy.NO_PROXY;
    }

    private String nextTlsVersion() {
        if (this.nextTlsVersion == null) {
            throw new IllegalStateException("No next TLS version");
        }
        if (this.nextTlsVersion.equals("TLSv1")) {
            this.nextTlsVersion = "SSLv3";
            return "TLSv1";
        }
        if (this.nextTlsVersion.equals("SSLv3")) {
            this.nextTlsVersion = null;
            return "SSLv3";
        }
        throw new AssertionError();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resetNextInetSocketAddress(Proxy object) throws UnknownHostException {
        this.socketAddresses = null;
        if (object.type() == Proxy.Type.DIRECT) {
            object = this.uri.getHost();
            this.socketPort = Util.getEffectivePort(this.uri);
        } else {
            if (!((object = object.address()) instanceof InetSocketAddress)) {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + object.getClass());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)object;
            object = inetSocketAddress.getHostName();
            this.socketPort = inetSocketAddress.getPort();
        }
        this.socketAddresses = this.dns.getAllByName((String)object);
        this.nextSocketAddressIndex = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resetNextProxy(URI list, Proxy proxy) {
        this.hasNextProxy = true;
        if (proxy != null) {
            this.userSpecifiedProxy = proxy;
            return;
        } else {
            if ((list = this.proxySelector.select((URI)((Object)list))) == null) return;
            {
                this.proxySelectorProxies = list.iterator();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resetNextTlsVersion() {
        String string2 = this.address.getSslSocketFactory() != null ? "TLSv1" : "SSLv3";
        this.nextTlsVersion = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void connectFailed(Connection object, IOException iOException) {
        if (Internal.instance.recycleCount((Connection)object) <= 0) {
            if ((object = object.getRoute()).getProxy().type() != Proxy.Type.DIRECT && this.proxySelector != null) {
                this.proxySelector.connectFailed(this.uri, object.getProxy().address(), iOException);
            }
            this.routeDatabase.failed((Route)object);
            if (!(iOException instanceof SSLHandshakeException) && !(iOException instanceof SSLProtocolException)) {
                while (this.hasNextTlsVersion()) {
                    object = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.nextTlsVersion());
                    this.routeDatabase.failed((Route)object);
                }
            }
        }
    }

    public boolean hasNext() {
        if (this.hasNextTlsVersion() || this.hasNextInetSocketAddress() || this.hasNextProxy() || this.hasNextPostponed()) {
            return true;
        }
        return false;
    }

    public Connection next(String string2) throws IOException {
        Object object;
        while ((object = this.pool.get(this.address)) != null) {
            if (string2.equals("GET") || Internal.instance.isReadable((Connection)object)) {
                return object;
            }
            object.getSocket().close();
        }
        if (!this.hasNextTlsVersion()) {
            if (!this.hasNextInetSocketAddress()) {
                if (!this.hasNextProxy()) {
                    if (!this.hasNextPostponed()) {
                        throw new NoSuchElementException();
                    }
                    return new Connection(this.pool, this.nextPostponed());
                }
                this.lastProxy = this.nextProxy();
                this.resetNextInetSocketAddress(this.lastProxy);
            }
            this.lastInetSocketAddress = this.nextInetSocketAddress();
            this.resetNextTlsVersion();
        }
        object = this.nextTlsVersion();
        if (this.routeDatabase.shouldPostpone((Route)(object = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, (String)object)))) {
            this.postponedRoutes.add((Route)object);
            return this.next(string2);
        }
        return new Connection(this.pool, (Route)object);
    }
}

