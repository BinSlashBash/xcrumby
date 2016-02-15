/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Address;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.net.ssl.SSLSocketFactory;

public final class Route {
    final Address address;
    final InetSocketAddress inetSocketAddress;
    final Proxy proxy;
    final String tlsVersion;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress, String string2) {
        if (address == null) {
            throw new NullPointerException("address == null");
        }
        if (proxy == null) {
            throw new NullPointerException("proxy == null");
        }
        if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        }
        if (string2 == null) {
            throw new NullPointerException("tlsVersion == null");
        }
        this.address = address;
        this.proxy = proxy;
        this.inetSocketAddress = inetSocketAddress;
        this.tlsVersion = string2;
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof Route) {
            object = (Route)object;
            bl3 = bl2;
            if (this.address.equals(object.address)) {
                bl3 = bl2;
                if (this.proxy.equals(object.proxy)) {
                    bl3 = bl2;
                    if (this.inetSocketAddress.equals(object.inetSocketAddress)) {
                        bl3 = bl2;
                        if (this.tlsVersion.equals(object.tlsVersion)) {
                            bl3 = true;
                        }
                    }
                }
            }
        }
        return bl3;
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

    public int hashCode() {
        return (((this.address.hashCode() + 527) * 31 + this.proxy.hashCode()) * 31 + this.inetSocketAddress.hashCode()) * 31 + this.tlsVersion.hashCode();
    }

    public boolean requiresTunnel() {
        if (this.address.sslSocketFactory != null && this.proxy.type() == Proxy.Type.HTTP) {
            return true;
        }
        return false;
    }

    boolean supportsNpn() {
        if (!this.tlsVersion.equals("SSLv3")) {
            return true;
        }
        return false;
    }
}

