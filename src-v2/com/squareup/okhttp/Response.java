/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.http.OkHeaders;
import java.util.Collections;
import java.util.List;

public final class Response {
    private final ResponseBody body;
    private volatile CacheControl cacheControl;
    private Response cacheResponse;
    private final int code;
    private final Handshake handshake;
    private final Headers headers;
    private final String message;
    private Response networkResponse;
    private final Response priorResponse;
    private final Protocol protocol;
    private final Request request;

    private Response(Builder builder) {
        this.request = builder.request;
        this.protocol = builder.protocol;
        this.code = builder.code;
        this.message = builder.message;
        this.handshake = builder.handshake;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.networkResponse = builder.networkResponse;
        this.cacheResponse = builder.cacheResponse;
        this.priorResponse = builder.priorResponse;
    }

    public ResponseBody body() {
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

    public Response cacheResponse() {
        return this.cacheResponse;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Challenge> challenges() {
        String string2;
        if (this.code == 401) {
            string2 = "WWW-Authenticate";
            do {
                return OkHeaders.parseChallenges(this.headers(), string2);
                break;
            } while (true);
        }
        if (this.code != 407) return Collections.emptyList();
        string2 = "Proxy-Authenticate";
        return OkHeaders.parseChallenges(this.headers(), string2);
    }

    public int code() {
        return this.code;
    }

    public Handshake handshake() {
        return this.handshake;
    }

    public String header(String string2) {
        return this.header(string2, null);
    }

    public String header(String string2, String string3) {
        if ((string2 = this.headers.get(string2)) != null) {
            return string2;
        }
        return string3;
    }

    public Headers headers() {
        return this.headers;
    }

    public List<String> headers(String string2) {
        return this.headers.values(string2);
    }

    public boolean isRedirect() {
        switch (this.code) {
            default: {
                return false;
            }
            case 300: 
            case 301: 
            case 302: 
            case 303: 
            case 307: 
        }
        return true;
    }

    public boolean isSuccessful() {
        if (this.code >= 200 && this.code < 300) {
            return true;
        }
        return false;
    }

    public String message() {
        return this.message;
    }

    public Response networkResponse() {
        return this.networkResponse;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public Response priorResponse() {
        return this.priorResponse;
    }

    public Protocol protocol() {
        return this.protocol;
    }

    public Request request() {
        return this.request;
    }

    public String toString() {
        return "Response{protocol=" + (Object)((Object)this.protocol) + ", code=" + this.code + ", message=" + this.message + ", url=" + this.request.urlString() + '}';
    }

    public static class Builder {
        private ResponseBody body;
        private Response cacheResponse;
        private int code = -1;
        private Handshake handshake;
        private Headers.Builder headers;
        private String message;
        private Response networkResponse;
        private Response priorResponse;
        private Protocol protocol;
        private Request request;

        public Builder() {
            this.headers = new Headers.Builder();
        }

        private Builder(Response response) {
            this.request = response.request;
            this.protocol = response.protocol;
            this.code = response.code;
            this.message = response.message;
            this.handshake = response.handshake;
            this.headers = response.headers.newBuilder();
            this.body = response.body;
            this.networkResponse = response.networkResponse;
            this.cacheResponse = response.cacheResponse;
            this.priorResponse = response.priorResponse;
        }

        private void checkPriorResponse(Response response) {
            if (response.body != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
        }

        private void checkSupportResponse(String string2, Response response) {
            if (response.body != null) {
                throw new IllegalArgumentException(string2 + ".body != null");
            }
            if (response.networkResponse != null) {
                throw new IllegalArgumentException(string2 + ".networkResponse != null");
            }
            if (response.cacheResponse != null) {
                throw new IllegalArgumentException(string2 + ".cacheResponse != null");
            }
            if (response.priorResponse != null) {
                throw new IllegalArgumentException(string2 + ".priorResponse != null");
            }
        }

        public Builder addHeader(String string2, String string3) {
            this.headers.add(string2, string3);
            return this;
        }

        public Builder body(ResponseBody responseBody) {
            this.body = responseBody;
            return this;
        }

        public Response build() {
            if (this.request == null) {
                throw new IllegalStateException("request == null");
            }
            if (this.protocol == null) {
                throw new IllegalStateException("protocol == null");
            }
            if (this.code < 0) {
                throw new IllegalStateException("code < 0: " + this.code);
            }
            return new Response(this);
        }

        public Builder cacheResponse(Response response) {
            if (response != null) {
                this.checkSupportResponse("cacheResponse", response);
            }
            this.cacheResponse = response;
            return this;
        }

        public Builder code(int n2) {
            this.code = n2;
            return this;
        }

        public Builder handshake(Handshake handshake) {
            this.handshake = handshake;
            return this;
        }

        public Builder header(String string2, String string3) {
            this.headers.set(string2, string3);
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers.newBuilder();
            return this;
        }

        public Builder message(String string2) {
            this.message = string2;
            return this;
        }

        public Builder networkResponse(Response response) {
            if (response != null) {
                this.checkSupportResponse("networkResponse", response);
            }
            this.networkResponse = response;
            return this;
        }

        public Builder priorResponse(Response response) {
            if (response != null) {
                this.checkPriorResponse(response);
            }
            this.priorResponse = response;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder removeHeader(String string2) {
            this.headers.removeAll(string2);
            return this;
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }
    }

}

