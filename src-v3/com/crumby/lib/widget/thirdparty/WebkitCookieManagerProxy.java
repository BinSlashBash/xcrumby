package com.crumby.lib.widget.thirdparty;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebkitCookieManagerProxy extends CookieManager {
    private android.webkit.CookieManager webkitCookieManager;

    public WebkitCookieManagerProxy() {
        this(null, null);
    }

    public WebkitCookieManagerProxy(CookieStore store, CookiePolicy cookiePolicy) {
        super(null, cookiePolicy);
        this.webkitCookieManager = android.webkit.CookieManager.getInstance();
    }

    public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
        if (uri != null && responseHeaders != null) {
            String url = uri.toString();
            for (String headerKey : responseHeaders.keySet()) {
                if (headerKey != null && (headerKey.equalsIgnoreCase("Set-Cookie2") || headerKey.equalsIgnoreCase("Set-Cookie"))) {
                    for (String headerValue : (List) responseHeaders.get(headerKey)) {
                        this.webkitCookieManager.setCookie(url, headerValue);
                    }
                }
            }
        }
    }

    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
        if (uri == null || requestHeaders == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        String url = uri.toString();
        Map<String, List<String>> res = new HashMap();
        if (this.webkitCookieManager.getCookie(url) != null) {
            res.put("Cookie", Arrays.asList(new String[]{this.webkitCookieManager.getCookie(url)}));
        }
        return res;
    }

    public CookieStore getCookieStore() {
        throw new UnsupportedOperationException();
    }
}
