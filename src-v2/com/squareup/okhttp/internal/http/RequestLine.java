/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import java.net.Proxy;
import java.net.URL;

public final class RequestLine {
    private RequestLine() {
    }

    /*
     * Enabled aggressive block sorting
     */
    static String get(Request request, Proxy.Type type, Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.method());
        stringBuilder.append(' ');
        if (RequestLine.includeAuthorityInRequestLine(request, type)) {
            stringBuilder.append(request.url());
        } else {
            stringBuilder.append(RequestLine.requestPath(request.url()));
        }
        stringBuilder.append(' ');
        stringBuilder.append(RequestLine.version(protocol));
        return stringBuilder.toString();
    }

    private static boolean includeAuthorityInRequestLine(Request request, Proxy.Type type) {
        if (!request.isHttps() && type == Proxy.Type.HTTP) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String requestPath(URL object) {
        String string2 = object.getFile();
        if (string2 == null) {
            return "/";
        }
        object = string2;
        if (string2.startsWith("/")) return object;
        return "/" + string2;
    }

    public static String version(Protocol protocol) {
        if (protocol == Protocol.HTTP_1_0) {
            return "HTTP/1.0";
        }
        return "HTTP/1.1";
    }
}

