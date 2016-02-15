/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.http.HttpMethod;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public final class Request {
    private final RequestBody body;
    private volatile CacheControl cacheControl;
    private final Headers headers;
    private final String method;
    private final Object tag;
    private volatile URI uri;
    private volatile URL url;
    private final String urlString;

    /*
     * Enabled aggressive block sorting
     */
    private Request(Builder builder) {
        this.urlString = builder.urlString;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
        Object object = builder.tag != null ? builder.tag : this;
        this.tag = object;
        this.url = builder.url;
    }

    public RequestBody body() {
        return this.body;
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.cacheControl;
        if (cacheControl != null) {
            return cacheControl;
        }
        this.cacheControl = cacheControl = CacheControl.parse(this.headers);
        return cacheControl;
    }

    public String header(String string2) {
        return this.headers.get(string2);
    }

    public Headers headers() {
        return this.headers;
    }

    public List<String> headers(String string2) {
        return this.headers.values(string2);
    }

    public boolean isHttps() {
        return this.url().getProtocol().equals("https");
    }

    public String method() {
        return this.method;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public Object tag() {
        return this.tag;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder().append("Request{method=").append(this.method).append(", url=").append(this.url).append(", tag=");
        if (this.tag != this) {
            object = this.tag;
            do {
                return stringBuilder.append(object).append('}').toString();
                break;
            } while (true);
        }
        object = null;
        return stringBuilder.append(object).append('}').toString();
    }

    public URI uri() throws IOException {
        URI uRI;
        block3 : {
            try {
                uRI = this.uri;
                if (uRI == null) break block3;
                return uRI;
            }
            catch (URISyntaxException var1_2) {
                throw new IOException(var1_2.getMessage());
            }
        }
        this.uri = uRI = Platform.get().toUriLenient(this.url);
        return uRI;
    }

    public URL url() {
        URL uRL;
        block3 : {
            try {
                uRL = this.url;
                if (uRL == null) break block3;
                return uRL;
            }
            catch (MalformedURLException var1_2) {
                throw new RuntimeException("Malformed URL: " + this.urlString, var1_2);
            }
        }
        this.url = uRL = new URL(this.urlString);
        return uRL;
    }

    public String urlString() {
        return this.urlString;
    }

    public static class Builder {
        private RequestBody body;
        private Headers.Builder headers;
        private String method;
        private Object tag;
        private URL url;
        private String urlString;

        public Builder() {
            this.method = "GET";
            this.headers = new Headers.Builder();
        }

        private Builder(Request request) {
            this.urlString = request.urlString;
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
            this.headers = request.headers.newBuilder();
        }

        public Builder addHeader(String string2, String string3) {
            this.headers.add(string2, string3);
            return this;
        }

        public Request build() {
            if (this.urlString == null) {
                throw new IllegalStateException("url == null");
            }
            return new Request(this);
        }

        public Builder delete() {
            return this.method("DELETE", null);
        }

        public Builder get() {
            return this.method("GET", null);
        }

        public Builder head() {
            return this.method("HEAD", null);
        }

        public Builder header(String string2, String string3) {
            this.headers.set(string2, string3);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder method(String string2, RequestBody requestBody) {
            if (string2 == null || string2.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            }
            if (requestBody != null && !HttpMethod.hasRequestBody(string2)) {
                throw new IllegalArgumentException("method " + string2 + " must not have a request body.");
            }
            this.method = string2;
            this.body = requestBody;
            return this;
        }

        public Builder patch(RequestBody requestBody) {
            return this.method("PATCH", requestBody);
        }

        public Builder post(RequestBody requestBody) {
            return this.method("POST", requestBody);
        }

        public Builder put(RequestBody requestBody) {
            return this.method("PUT", requestBody);
        }

        public Builder removeHeader(String string2) {
            this.headers.removeAll(string2);
            return this;
        }

        public Builder tag(Object object) {
            this.tag = object;
            return this;
        }

        public Builder url(String string2) {
            if (string2 == null) {
                throw new IllegalArgumentException("url == null");
            }
            this.urlString = string2;
            return this;
        }

        public Builder url(URL uRL) {
            if (uRL == null) {
                throw new IllegalArgumentException("url == null");
            }
            this.url = uRL;
            this.urlString = uRL.toString();
            return this;
        }
    }

}

