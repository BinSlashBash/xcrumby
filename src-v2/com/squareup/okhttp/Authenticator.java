/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.Proxy;

public interface Authenticator {
    public Request authenticate(Proxy var1, Response var2) throws IOException;

    public Request authenticateProxy(Proxy var1, Response var2) throws IOException;
}

