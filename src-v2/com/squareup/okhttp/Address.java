/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address {
    final Authenticator authenticator;
    final HostnameVerifier hostnameVerifier;
    final List<Protocol> protocols;
    final Proxy proxy;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final String uriHost;
    final int uriPort;

    public Address(String string2, int n2, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, Authenticator authenticator, Proxy proxy, List<Protocol> list) throws UnknownHostException {
        if (string2 == null) {
            throw new NullPointerException("uriHost == null");
        }
        if (n2 <= 0) {
            throw new IllegalArgumentException("uriPort <= 0: " + n2);
        }
        if (authenticator == null) {
            throw new IllegalArgumentException("authenticator == null");
        }
        if (list == null) {
            throw new IllegalArgumentException("protocols == null");
        }
        this.proxy = proxy;
        this.uriHost = string2;
        this.uriPort = n2;
        this.socketFactory = socketFactory;
        this.sslSocketFactory = sSLSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
        this.authenticator = authenticator;
        this.protocols = Util.immutableList(list);
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof Address) {
            object = (Address)object;
            bl3 = bl2;
            if (Util.equal(this.proxy, object.proxy)) {
                bl3 = bl2;
                if (this.uriHost.equals(object.uriHost)) {
                    bl3 = bl2;
                    if (this.uriPort == object.uriPort) {
                        bl3 = bl2;
                        if (Util.equal(this.sslSocketFactory, object.sslSocketFactory)) {
                            bl3 = bl2;
                            if (Util.equal(this.hostnameVerifier, object.hostnameVerifier)) {
                                bl3 = bl2;
                                if (Util.equal(this.authenticator, object.authenticator)) {
                                    bl3 = bl2;
                                    if (Util.equal(this.protocols, object.protocols)) {
                                        bl3 = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl3;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
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

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public String getUriHost() {
        return this.uriHost;
    }

    public int getUriPort() {
        return this.uriPort;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = 0;
        int n3 = this.uriHost.hashCode();
        int n4 = this.uriPort;
        int n5 = this.sslSocketFactory != null ? this.sslSocketFactory.hashCode() : 0;
        int n6 = this.hostnameVerifier != null ? this.hostnameVerifier.hashCode() : 0;
        int n7 = this.authenticator.hashCode();
        if (this.proxy != null) {
            n2 = this.proxy.hashCode();
        }
        return ((((((n3 + 527) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n2) * 31 + this.protocols.hashCode();
    }
}

