package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.RouteSelector;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public final class Route {
    final Address address;
    final InetSocketAddress inetSocketAddress;
    final Proxy proxy;
    final String tlsVersion;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, String tlsVersion) {
        if (address == null) {
            throw new NullPointerException("address == null");
        } else if (proxy == null) {
            throw new NullPointerException("proxy == null");
        } else if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        } else if (tlsVersion == null) {
            throw new NullPointerException("tlsVersion == null");
        } else {
            this.address = address;
            this.proxy = proxy;
            this.inetSocketAddress = inetSocketAddress;
            this.tlsVersion = tlsVersion;
        }
    }

    public Address getAddress() {
        return this.address;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public InetSocketAddress getSocketAddress() {
        return this.inetSocketAddress;
    }

    public String getTlsVersion() {
        return this.tlsVersion;
    }

    boolean supportsNpn() {
        return !this.tlsVersion.equals(RouteSelector.SSL_V3);
    }

    public boolean requiresTunnel() {
        return this.address.sslSocketFactory != null && this.proxy.type() == Type.HTTP;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) {
            return false;
        }
        Route other = (Route) obj;
        if (this.address.equals(other.address) && this.proxy.equals(other.proxy) && this.inetSocketAddress.equals(other.inetSocketAddress) && this.tlsVersion.equals(other.tlsVersion)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.address.hashCode() + 527) * 31) + this.proxy.hashCode()) * 31) + this.inetSocketAddress.hashCode()) * 31) + this.tlsVersion.hashCode();
    }
}
