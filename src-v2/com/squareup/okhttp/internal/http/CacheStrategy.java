/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.HeaderParser;
import com.squareup.okhttp.internal.http.HttpDate;
import com.squareup.okhttp.internal.http.OkHeaders;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    private CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static boolean isCacheable(Response object, Request request) {
        int n2 = object.code();
        if (n2 != 200 && n2 != 203 && n2 != 300 && n2 != 301 && n2 != 410) {
            return false;
        }
        object = object.cacheControl();
        if (request.header("Authorization") != null && !object.isPublic() && !object.mustRevalidate()) {
            if (object.sMaxAgeSeconds() == -1) return false;
        }
        if (object.noStore()) return false;
        return true;
    }

    public static class Factory {
        private int ageSeconds = -1;
        final Response cacheResponse;
        private String etag;
        private Date expires;
        private Date lastModified;
        private String lastModifiedString;
        final long nowMillis;
        private long receivedResponseMillis;
        final Request request;
        private long sentRequestMillis;
        private Date servedDate;
        private String servedDateString;

        /*
         * Enabled aggressive block sorting
         */
        public Factory(long l2, Request object, Response response) {
            this.nowMillis = l2;
            this.request = object;
            this.cacheResponse = response;
            if (response == null) {
                return;
            }
            int n2 = 0;
            while (n2 < response.headers().size()) {
                object = response.headers().name(n2);
                String string2 = response.headers().value(n2);
                if ("Date".equalsIgnoreCase((String)object)) {
                    this.servedDate = HttpDate.parse(string2);
                    this.servedDateString = string2;
                } else if ("Expires".equalsIgnoreCase((String)object)) {
                    this.expires = HttpDate.parse(string2);
                } else if ("Last-Modified".equalsIgnoreCase((String)object)) {
                    this.lastModified = HttpDate.parse(string2);
                    this.lastModifiedString = string2;
                } else if ("ETag".equalsIgnoreCase((String)object)) {
                    this.etag = string2;
                } else if ("Age".equalsIgnoreCase((String)object)) {
                    this.ageSeconds = HeaderParser.parseSeconds(string2);
                } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase((String)object)) {
                    this.sentRequestMillis = Long.parseLong(string2);
                } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase((String)object)) {
                    this.receivedResponseMillis = Long.parseLong(string2);
                }
                ++n2;
            }
        }

        private long cacheResponseAge() {
            long l2 = 0;
            if (this.servedDate != null) {
                l2 = Math.max(0, this.receivedResponseMillis - this.servedDate.getTime());
            }
            if (this.ageSeconds != -1) {
                l2 = Math.max(l2, TimeUnit.SECONDS.toMillis(this.ageSeconds));
            }
            return l2 + (this.receivedResponseMillis - this.sentRequestMillis) + (this.nowMillis - this.receivedResponseMillis);
        }

        /*
         * Enabled aggressive block sorting
         */
        private long computeFreshnessLifetime() {
            long l2 = 0;
            CacheControl cacheControl = this.cacheResponse.cacheControl();
            if (cacheControl.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis(cacheControl.maxAgeSeconds());
            }
            if (this.expires != null) {
                long l3 = this.servedDate != null ? this.servedDate.getTime() : this.receivedResponseMillis;
                l3 = this.expires.getTime() - l3;
                if (l3 <= 0) return 0;
                return l3;
            }
            long l4 = l2;
            if (this.lastModified == null) return l4;
            l4 = l2;
            if (this.cacheResponse.request().url().getQuery() != null) return l4;
            l4 = this.servedDate != null ? this.servedDate.getTime() : this.sentRequestMillis;
            long l5 = l4 - this.lastModified.getTime();
            l4 = l2;
            if (l5 <= 0) return l4;
            return l5 / 10;
        }

        /*
         * Enabled aggressive block sorting
         */
        private CacheStrategy getCandidate() {
            long l2;
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            Object object = this.request.cacheControl();
            if (object.noCache() || Factory.hasConditions(this.request)) {
                return new CacheStrategy(this.request, null);
            }
            long l3 = this.cacheResponseAge();
            long l4 = l2 = this.computeFreshnessLifetime();
            if (object.maxAgeSeconds() != -1) {
                l4 = Math.min(l2, TimeUnit.SECONDS.toMillis(object.maxAgeSeconds()));
            }
            l2 = 0;
            if (object.minFreshSeconds() != -1) {
                l2 = TimeUnit.SECONDS.toMillis(object.minFreshSeconds());
            }
            long l5 = 0;
            CacheControl cacheControl = this.cacheResponse.cacheControl();
            long l6 = l5;
            if (!cacheControl.mustRevalidate()) {
                l6 = l5;
                if (object.maxStaleSeconds() != -1) {
                    l6 = TimeUnit.SECONDS.toMillis(object.maxStaleSeconds());
                }
            }
            if (!cacheControl.noCache() && l3 + l2 < l4 + l6) {
                object = this.cacheResponse.newBuilder();
                if (l3 + l2 >= l4) {
                    object.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                }
                if (l3 > 86400000 && this.isFreshnessLifetimeHeuristic()) {
                    object.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                }
                return new CacheStrategy(null, object.build());
            }
            object = this.request.newBuilder();
            if (this.lastModified != null) {
                object.header("If-Modified-Since", this.lastModifiedString);
            } else if (this.servedDate != null) {
                object.header("If-Modified-Since", this.servedDateString);
            }
            if (this.etag != null) {
                object.header("If-None-Match", this.etag);
            }
            if (Factory.hasConditions((Request)(object = object.build()))) {
                return new CacheStrategy((Request)object, this.cacheResponse);
            }
            return new CacheStrategy((Request)object, null);
        }

        private static boolean hasConditions(Request request) {
            if (request.header("If-Modified-Since") != null || request.header("If-None-Match") != null) {
                return true;
            }
            return false;
        }

        private boolean isFreshnessLifetimeHeuristic() {
            if (this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null) {
                return true;
            }
            return false;
        }

        public CacheStrategy get() {
            CacheStrategy cacheStrategy;
            CacheStrategy cacheStrategy2 = cacheStrategy = this.getCandidate();
            if (cacheStrategy.networkRequest != null) {
                cacheStrategy2 = cacheStrategy;
                if (this.request.cacheControl().onlyIfCached()) {
                    cacheStrategy2 = new CacheStrategy(null, null);
                }
            }
            return cacheStrategy2;
        }
    }

}

