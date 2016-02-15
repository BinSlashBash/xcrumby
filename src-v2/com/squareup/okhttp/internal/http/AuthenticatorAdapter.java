/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.util.List;

public final class AuthenticatorAdapter
implements Authenticator {
    public static final Authenticator INSTANCE = new AuthenticatorAdapter();

    private InetAddress getConnectToInetAddress(Proxy proxy, URL uRL) throws IOException {
        if (proxy != null && proxy.type() != Proxy.Type.DIRECT) {
            return ((InetSocketAddress)proxy.address()).getAddress();
        }
        return InetAddress.getByName(uRL.getHost());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Request authenticate(Proxy object, Response object2) throws IOException {
        List<Challenge> list = object2.challenges();
        object2 = object2.request();
        URL uRL = object2.url();
        int n2 = 0;
        int n3 = list.size();
        while (n2 < n3) {
            Object object3 = list.get(n2);
            if ("Basic".equalsIgnoreCase(object3.getScheme()) && (object3 = java.net.Authenticator.requestPasswordAuthentication(uRL.getHost(), this.getConnectToInetAddress((Proxy)object, uRL), uRL.getPort(), uRL.getProtocol(), object3.getRealm(), object3.getScheme(), uRL, Authenticator.RequestorType.SERVER)) != null) {
                object = Credentials.basic(object3.getUserName(), new String(object3.getPassword()));
                return object2.newBuilder().header("Authorization", (String)object).build();
            }
            ++n2;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Request authenticateProxy(Proxy object, Response object2) throws IOException {
        List<Challenge> list = object2.challenges();
        object2 = object2.request();
        URL uRL = object2.url();
        int n2 = 0;
        int n3 = list.size();
        while (n2 < n3) {
            InetSocketAddress inetSocketAddress;
            Object object3 = list.get(n2);
            if ("Basic".equalsIgnoreCase(object3.getScheme()) && (object3 = java.net.Authenticator.requestPasswordAuthentication((inetSocketAddress = (InetSocketAddress)object.address()).getHostName(), this.getConnectToInetAddress((Proxy)object, uRL), inetSocketAddress.getPort(), uRL.getProtocol(), object3.getRealm(), object3.getScheme(), uRL, Authenticator.RequestorType.PROXY)) != null) {
                object = Credentials.basic(object3.getUserName(), new String(object3.getPassword()));
                return object2.newBuilder().header("Proxy-Authorization", (String)object).build();
            }
            ++n2;
        }
        return null;
    }
}

