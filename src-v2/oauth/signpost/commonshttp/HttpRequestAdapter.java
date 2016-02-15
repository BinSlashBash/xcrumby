/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpEntityEnclosingRequest
 *  org.apache.http.RequestLine
 *  org.apache.http.client.methods.HttpUriRequest
 */
package oauth.signpost.commonshttp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import oauth.signpost.http.HttpRequest;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpUriRequest;

public class HttpRequestAdapter
implements HttpRequest {
    private HttpEntity entity;
    private HttpUriRequest request;

    public HttpRequestAdapter(HttpUriRequest httpUriRequest) {
        this.request = httpUriRequest;
        if (httpUriRequest instanceof HttpEntityEnclosingRequest) {
            this.entity = ((HttpEntityEnclosingRequest)httpUriRequest).getEntity();
        }
    }

    @Override
    public Map<String, String> getAllHeaders() {
        Header[] arrheader = this.request.getAllHeaders();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Header header : arrheader) {
            hashMap.put(header.getName(), header.getValue());
        }
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getContentType() {
        Header header;
        if (this.entity == null || (header = this.entity.getContentType()) == null) {
            return null;
        }
        return header.getValue();
    }

    @Override
    public String getHeader(String string2) {
        if ((string2 = this.request.getFirstHeader(string2)) == null) {
            return null;
        }
        return string2.getValue();
    }

    @Override
    public InputStream getMessagePayload() throws IOException {
        if (this.entity == null) {
            return null;
        }
        return this.entity.getContent();
    }

    @Override
    public String getMethod() {
        return this.request.getRequestLine().getMethod();
    }

    @Override
    public String getRequestUrl() {
        return this.request.getURI().toString();
    }

    @Override
    public void setHeader(String string2, String string3) {
        this.request.setHeader(string2, string3);
    }

    @Override
    public void setRequestUrl(String string2) {
        throw new RuntimeException(new UnsupportedOperationException());
    }

    @Override
    public Object unwrap() {
        return this.request;
    }
}

