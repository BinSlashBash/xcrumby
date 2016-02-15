package com.squareup.okhttp.internal.http;

import com.google.android.gms.wallet.WalletConstants;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import oauth.signpost.OAuth;
import uk.co.senab.photoview.IPhotoView;

public final class CacheStrategy {
    public final Response cacheResponse;
    public final Request networkRequest;

    public static class Factory {
        private int ageSeconds;
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

        public Factory(long nowMillis, Request request, Response cacheResponse) {
            this.ageSeconds = -1;
            this.nowMillis = nowMillis;
            this.request = request;
            this.cacheResponse = cacheResponse;
            if (cacheResponse != null) {
                for (int i = 0; i < cacheResponse.headers().size(); i++) {
                    String fieldName = cacheResponse.headers().name(i);
                    String value = cacheResponse.headers().value(i);
                    if ("Date".equalsIgnoreCase(fieldName)) {
                        this.servedDate = HttpDate.parse(value);
                        this.servedDateString = value;
                    } else if ("Expires".equalsIgnoreCase(fieldName)) {
                        this.expires = HttpDate.parse(value);
                    } else if ("Last-Modified".equalsIgnoreCase(fieldName)) {
                        this.lastModified = HttpDate.parse(value);
                        this.lastModifiedString = value;
                    } else if ("ETag".equalsIgnoreCase(fieldName)) {
                        this.etag = value;
                    } else if ("Age".equalsIgnoreCase(fieldName)) {
                        this.ageSeconds = HeaderParser.parseSeconds(value);
                    } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.sentRequestMillis = Long.parseLong(value);
                    } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(fieldName)) {
                        this.receivedResponseMillis = Long.parseLong(value);
                    }
                }
            }
        }

        public CacheStrategy get() {
            CacheStrategy candidate = getCandidate();
            if (candidate.networkRequest == null || !this.request.cacheControl().onlyIfCached()) {
                return candidate;
            }
            return new CacheStrategy(null, null);
        }

        private CacheStrategy getCandidate() {
            if (this.cacheResponse == null) {
                return new CacheStrategy(null, null);
            }
            if (this.request.isHttps()) {
                if (this.cacheResponse.handshake() == null) {
                    return new CacheStrategy(null, null);
                }
            }
            if (CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                CacheControl requestCaching = this.request.cacheControl();
                if (!requestCaching.noCache()) {
                    if (!hasConditions(this.request)) {
                        long ageMillis = cacheResponseAge();
                        long freshMillis = computeFreshnessLifetime();
                        if (requestCaching.maxAgeSeconds() != -1) {
                            freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis((long) requestCaching.maxAgeSeconds()));
                        }
                        long minFreshMillis = 0;
                        if (requestCaching.minFreshSeconds() != -1) {
                            minFreshMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.minFreshSeconds());
                        }
                        long maxStaleMillis = 0;
                        CacheControl responseCaching = this.cacheResponse.cacheControl();
                        if (!(responseCaching.mustRevalidate() || requestCaching.maxStaleSeconds() == -1)) {
                            maxStaleMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.maxStaleSeconds());
                        }
                        if (responseCaching.noCache() || ageMillis + minFreshMillis >= freshMillis + maxStaleMillis) {
                            String str;
                            Builder conditionalRequestBuilder = this.request.newBuilder();
                            if (this.lastModified != null) {
                                str = "If-Modified-Since";
                                conditionalRequestBuilder.header(r18, this.lastModifiedString);
                            } else if (this.servedDate != null) {
                                str = "If-Modified-Since";
                                conditionalRequestBuilder.header(r18, this.servedDateString);
                            }
                            if (this.etag != null) {
                                str = "If-None-Match";
                                conditionalRequestBuilder.header(r18, this.etag);
                            }
                            Request conditionalRequest = conditionalRequestBuilder.build();
                            if (!hasConditions(conditionalRequest)) {
                                return new CacheStrategy(null, null);
                            }
                            return new CacheStrategy(this.cacheResponse, null);
                        }
                        Response.Builder builder = this.cacheResponse.newBuilder();
                        if (ageMillis + minFreshMillis >= freshMillis) {
                            builder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                        }
                        if (ageMillis > 86400000 && isFreshnessLifetimeHeuristic()) {
                            builder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                        }
                        return new CacheStrategy(builder.build(), null);
                    }
                }
                return new CacheStrategy(null, null);
            }
            return new CacheStrategy(null, null);
        }

        private long computeFreshnessLifetime() {
            CacheControl responseCaching = this.cacheResponse.cacheControl();
            if (responseCaching.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis((long) responseCaching.maxAgeSeconds());
            }
            long delta;
            if (this.expires != null) {
                delta = this.expires.getTime() - (this.servedDate != null ? this.servedDate.getTime() : this.receivedResponseMillis);
                if (delta <= 0) {
                    delta = 0;
                }
                return delta;
            } else if (this.lastModified == null || this.cacheResponse.request().url().getQuery() != null) {
                return 0;
            } else {
                delta = (this.servedDate != null ? this.servedDate.getTime() : this.sentRequestMillis) - this.lastModified.getTime();
                if (delta > 0) {
                    return delta / 10;
                }
                return 0;
            }
        }

        private long cacheResponseAge() {
            long receivedAge;
            long apparentReceivedAge = 0;
            if (this.servedDate != null) {
                apparentReceivedAge = Math.max(0, this.receivedResponseMillis - this.servedDate.getTime());
            }
            if (this.ageSeconds != -1) {
                receivedAge = Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis((long) this.ageSeconds));
            } else {
                receivedAge = apparentReceivedAge;
            }
            return (receivedAge + (this.receivedResponseMillis - this.sentRequestMillis)) + (this.nowMillis - this.receivedResponseMillis);
        }

        private boolean isFreshnessLifetimeHeuristic() {
            return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        private static boolean hasConditions(Request request) {
            return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
        }
    }

    private CacheStrategy(Request networkRequest, Response cacheResponse) {
        this.networkRequest = networkRequest;
        this.cacheResponse = cacheResponse;
    }

    public static boolean isCacheable(Response response, Request request) {
        int responseCode = response.code();
        if (responseCode != IPhotoView.DEFAULT_ZOOM_DURATION && responseCode != 203 && responseCode != 300 && responseCode != 301 && responseCode != WalletConstants.ERROR_CODE_INVALID_TRANSACTION) {
            return false;
        }
        CacheControl responseCaching = response.cacheControl();
        if ((request.header(OAuth.HTTP_AUTHORIZATION_HEADER) == null || responseCaching.isPublic() || responseCaching.mustRevalidate() || responseCaching.sMaxAgeSeconds() != -1) && !responseCaching.noStore()) {
            return true;
        }
        return false;
    }
}
