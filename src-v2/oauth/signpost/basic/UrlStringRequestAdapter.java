/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.basic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import oauth.signpost.http.HttpRequest;

public class UrlStringRequestAdapter
implements HttpRequest {
    private String url;

    public UrlStringRequestAdapter(String string2) {
        this.url = string2;
    }

    @Override
    public Map<String, String> getAllHeaders() {
        return Collections.emptyMap();
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getHeader(String string2) {
        return null;
    }

    @Override
    public InputStream getMessagePayload() throws IOException {
        return null;
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String getRequestUrl() {
        return this.url;
    }

    @Override
    public void setHeader(String string2, String string3) {
    }

    @Override
    public void setRequestUrl(String string2) {
        this.url = string2;
    }

    @Override
    public Object unwrap() {
        return this.url;
    }
}

