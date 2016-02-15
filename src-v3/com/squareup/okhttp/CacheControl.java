package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HeaderParser;

public final class CacheControl {
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    private CacheControl(boolean noCache, boolean noStore, int maxAgeSeconds, int sMaxAgeSeconds, boolean isPublic, boolean mustRevalidate, int maxStaleSeconds, int minFreshSeconds, boolean onlyIfCached) {
        this.noCache = noCache;
        this.noStore = noStore;
        this.maxAgeSeconds = maxAgeSeconds;
        this.sMaxAgeSeconds = sMaxAgeSeconds;
        this.isPublic = isPublic;
        this.mustRevalidate = mustRevalidate;
        this.maxStaleSeconds = maxStaleSeconds;
        this.minFreshSeconds = minFreshSeconds;
        this.onlyIfCached = onlyIfCached;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public static CacheControl parse(Headers headers) {
        boolean noCache = false;
        boolean noStore = false;
        int maxAgeSeconds = -1;
        int sMaxAgeSeconds = -1;
        boolean isPublic = false;
        boolean mustRevalidate = false;
        int maxStaleSeconds = -1;
        int minFreshSeconds = -1;
        boolean onlyIfCached = false;
        for (int i = 0; i < headers.size(); i++) {
            if (!headers.name(i).equalsIgnoreCase("Cache-Control")) {
                if (!headers.name(i).equalsIgnoreCase("Pragma")) {
                }
            }
            String string = headers.value(i);
            int pos = 0;
            while (pos < string.length()) {
                String parameter;
                int tokenStart = pos;
                pos = HeaderParser.skipUntil(string, pos, "=,;");
                String directive = string.substring(tokenStart, pos).trim();
                if (pos == string.length() || string.charAt(pos) == ',' || string.charAt(pos) == ';') {
                    pos++;
                    parameter = null;
                } else {
                    pos = HeaderParser.skipWhitespace(string, pos + 1);
                    int parameterStart;
                    if (pos >= string.length() || string.charAt(pos) != '\"') {
                        parameterStart = pos;
                        pos = HeaderParser.skipUntil(string, pos, ",;");
                        parameter = string.substring(parameterStart, pos).trim();
                    } else {
                        pos++;
                        parameterStart = pos;
                        pos = HeaderParser.skipUntil(string, pos, "\"");
                        parameter = string.substring(parameterStart, pos);
                        pos++;
                    }
                }
                if ("no-cache".equalsIgnoreCase(directive)) {
                    noCache = true;
                } else if ("no-store".equalsIgnoreCase(directive)) {
                    noStore = true;
                } else if ("max-age".equalsIgnoreCase(directive)) {
                    maxAgeSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("s-maxage".equalsIgnoreCase(directive)) {
                    sMaxAgeSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("public".equalsIgnoreCase(directive)) {
                    isPublic = true;
                } else if ("must-revalidate".equalsIgnoreCase(directive)) {
                    mustRevalidate = true;
                } else if ("max-stale".equalsIgnoreCase(directive)) {
                    maxStaleSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("min-fresh".equalsIgnoreCase(directive)) {
                    minFreshSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("only-if-cached".equalsIgnoreCase(directive)) {
                    onlyIfCached = true;
                }
            }
        }
        return new CacheControl(noCache, noStore, maxAgeSeconds, sMaxAgeSeconds, isPublic, mustRevalidate, maxStaleSeconds, minFreshSeconds, onlyIfCached);
    }
}
