/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.SpdyTransport;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public final class Connection {
    private boolean connected = false;
    private Handshake handshake;
    private HttpConnection httpConnection;
    private long idleStartTimeNs;
    private Object owner;
    private final ConnectionPool pool;
    private Protocol protocol = Protocol.HTTP_1_1;
    private int recycleCount;
    private final Route route;
    private Socket socket;
    private SpdyConnection spdyConnection;

    public Connection(ConnectionPool connectionPool, Route route) {
        this.pool = connectionPool;
        this.route = route;
    }

    private void makeTunnel(Request object, int n2, int n3) throws IOException {
        HttpConnection httpConnection = new HttpConnection(this.pool, this, this.socket);
        httpConnection.setTimeouts(n2, n3);
        Object object2 = object.url();
        String string2 = "CONNECT " + object2.getHost() + ":" + object2.getPort() + " HTTP/1.1";
        block4 : do {
            httpConnection.writeRequest(object.headers(), string2);
            httpConnection.flush();
            object = httpConnection.readResponse().request((Request)object).build();
            httpConnection.emptyResponseBody();
            switch (object.code()) {
                default: {
                    throw new IOException("Unexpected response code for CONNECT: " + object.code());
                }
                case 200: {
                    if (httpConnection.bufferSize() <= 0) break block4;
                    throw new IOException("TLS tunnel buffered too many bytes!");
                }
                case 407: {
                    object = object2 = OkHeaders.processAuthHeader(this.route.address.authenticator, (Response)object, this.route.proxy);
                    if (object2 != null) continue block4;
                    throw new IOException("Failed to authenticate with proxy");
                }
            }
            break;
        } while (true);
    }

    private void upgradeToTls(Request object, int n2, int n3) throws IOException {
        Object object2 = Platform.get();
        if (object != null) {
            this.makeTunnel((Request)object, n2, n3);
        }
        this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
        object = (SSLSocket)this.socket;
        object2.configureTls((SSLSocket)object, this.route.address.uriHost, this.route.tlsVersion);
        boolean bl2 = this.route.supportsNpn();
        if (bl2) {
            object2.setProtocols((SSLSocket)object, this.route.address.protocols);
        }
        object.startHandshake();
        if (!this.route.address.hostnameVerifier.verify(this.route.address.uriHost, object.getSession())) {
            throw new IOException("Hostname '" + this.route.address.uriHost + "' was not verified");
        }
        this.handshake = Handshake.get(object.getSession());
        if (bl2 && (object2 = object2.getSelectedProtocol((SSLSocket)object)) != null) {
            this.protocol = Protocol.get((String)object2);
        }
        if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
            object.setSoTimeout(0);
            this.spdyConnection = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket).protocol(this.protocol).build();
            this.spdyConnection.sendConnectionPreface();
            return;
        }
        this.httpConnection = new HttpConnection(this.pool, this, this.socket);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean clearOwner() {
        ConnectionPool connectionPool = this.pool;
        synchronized (connectionPool) {
            if (this.owner == null) {
                return false;
            }
            this.owner = null;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void closeIfOwnedBy(Object object) throws IOException {
        if (this.isSpdy()) {
            throw new IllegalStateException();
        }
        ConnectionPool connectionPool = this.pool;
        synchronized (connectionPool) {
            if (this.owner != object) {
                return;
            }
            this.owner = null;
        }
        this.socket.close();
    }

    /*
     * Enabled aggressive block sorting
     */
    void connect(int n2, int n3, int n4, Request request) throws IOException {
        if (this.connected) {
            throw new IllegalStateException("already connected");
        }
        this.socket = this.route.proxy.type() != Proxy.Type.HTTP ? new Socket(this.route.proxy) : this.route.address.socketFactory.createSocket();
        this.socket.setSoTimeout(n3);
        Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, n2);
        if (this.route.address.sslSocketFactory != null) {
            this.upgradeToTls(request, n3, n4);
        } else {
            this.httpConnection = new HttpConnection(this.pool, this, this.socket);
        }
        this.connected = true;
    }

    public Handshake getHandshake() {
        return this.handshake;
    }

    long getIdleStartTimeNs() {
        if (this.spdyConnection == null) {
            return this.idleStartTimeNs;
        }
        return this.spdyConnection.getIdleStartTimeNs();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Object getOwner() {
        ConnectionPool connectionPool = this.pool;
        synchronized (connectionPool) {
            return this.owner;
        }
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public Route getRoute() {
        return this.route;
    }

    public Socket getSocket() {
        return this.socket;
    }

    void incrementRecycleCount() {
        ++this.recycleCount;
    }

    boolean isAlive() {
        if (!(this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown())) {
            return true;
        }
        return false;
    }

    boolean isConnected() {
        return this.connected;
    }

    boolean isExpired(long l2) {
        if (this.getIdleStartTimeNs() < System.nanoTime() - l2) {
            return true;
        }
        return false;
    }

    boolean isIdle() {
        if (this.spdyConnection == null || this.spdyConnection.isIdle()) {
            return true;
        }
        return false;
    }

    boolean isReadable() {
        if (this.httpConnection != null) {
            return this.httpConnection.isReadable();
        }
        return true;
    }

    boolean isSpdy() {
        if (this.spdyConnection != null) {
            return true;
        }
        return false;
    }

    Transport newTransport(HttpEngine httpEngine) throws IOException {
        if (this.spdyConnection != null) {
            return new SpdyTransport(httpEngine, this.spdyConnection);
        }
        return new HttpTransport(httpEngine, this.httpConnection);
    }

    int recycleCount() {
        return this.recycleCount;
    }

    void resetIdleStartTime() {
        if (this.spdyConnection != null) {
            throw new IllegalStateException("spdyConnection != null");
        }
        this.idleStartTimeNs = System.nanoTime();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setOwner(Object object) {
        if (this.isSpdy()) {
            return;
        }
        ConnectionPool connectionPool = this.pool;
        synchronized (connectionPool) {
            if (this.owner != null) {
                throw new IllegalStateException("Connection already has an owner!");
            }
            this.owner = object;
            return;
        }
    }

    void setProtocol(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol == null");
        }
        this.protocol = protocol;
    }

    void setTimeouts(int n2, int n3) throws IOException {
        if (!this.connected) {
            throw new IllegalStateException("setTimeouts - not connected");
        }
        if (this.httpConnection != null) {
            this.socket.setSoTimeout(n2);
            this.httpConnection.setTimeouts(n2, n3);
        }
    }
}

