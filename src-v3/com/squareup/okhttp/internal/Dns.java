package com.squareup.okhttp.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface Dns {
    public static final Dns DEFAULT;

    /* renamed from: com.squareup.okhttp.internal.Dns.1 */
    static class C11491 implements Dns {
        C11491() {
        }

        public InetAddress[] getAllByName(String host) throws UnknownHostException {
            if (host != null) {
                return InetAddress.getAllByName(host);
            }
            throw new UnknownHostException("host == null");
        }
    }

    InetAddress[] getAllByName(String str) throws UnknownHostException;

    static {
        DEFAULT = new C11491();
    }
}
