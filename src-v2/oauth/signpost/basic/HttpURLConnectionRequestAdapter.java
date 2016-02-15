/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.basic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oauth.signpost.http.HttpRequest;

public class HttpURLConnectionRequestAdapter
implements HttpRequest {
    protected HttpURLConnection connection;

    public HttpURLConnectionRequestAdapter(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
    }

    @Override
    public Map<String, String> getAllHeaders() {
        Map<String, List<String>> map = this.connection.getRequestProperties();
        HashMap<String, String> hashMap = new HashMap<String, String>(map.size());
        for (String string2 : map.keySet()) {
            List<String> list = map.get(string2);
            if (list.isEmpty()) continue;
            hashMap.put(string2, list.get(0));
        }
        return hashMap;
    }

    @Override
    public String getContentType() {
        return this.connection.getRequestProperty("Content-Type");
    }

    @Override
    public String getHeader(String string2) {
        return this.connection.getRequestProperty(string2);
    }

    @Override
    public InputStream getMessagePayload() throws IOException {
        return null;
    }

    @Override
    public String getMethod() {
        return this.connection.getRequestMethod();
    }

    @Override
    public String getRequestUrl() {
        return this.connection.getURL().toExternalForm();
    }

    @Override
    public void setHeader(String string2, String string3) {
        this.connection.setRequestProperty(string2, string3);
    }

    @Override
    public void setRequestUrl(String string2) {
    }

    @Override
    public HttpURLConnection unwrap() {
        return this.connection;
    }
}

