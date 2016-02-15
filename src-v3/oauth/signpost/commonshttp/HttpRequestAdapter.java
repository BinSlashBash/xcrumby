package oauth.signpost.commonshttp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import oauth.signpost.http.HttpRequest;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpUriRequest;

public class HttpRequestAdapter implements HttpRequest {
    private HttpEntity entity;
    private HttpUriRequest request;

    public HttpRequestAdapter(HttpUriRequest request) {
        this.request = request;
        if (request instanceof HttpEntityEnclosingRequest) {
            this.entity = ((HttpEntityEnclosingRequest) request).getEntity();
        }
    }

    public String getMethod() {
        return this.request.getRequestLine().getMethod();
    }

    public String getRequestUrl() {
        return this.request.getURI().toString();
    }

    public void setRequestUrl(String url) {
        throw new RuntimeException(new UnsupportedOperationException());
    }

    public String getHeader(String name) {
        Header header = this.request.getFirstHeader(name);
        if (header == null) {
            return null;
        }
        return header.getValue();
    }

    public void setHeader(String name, String value) {
        this.request.setHeader(name, value);
    }

    public Map<String, String> getAllHeaders() {
        Header[] origHeaders = this.request.getAllHeaders();
        HashMap<String, String> headers = new HashMap();
        for (Header h : origHeaders) {
            headers.put(h.getName(), h.getValue());
        }
        return headers;
    }

    public String getContentType() {
        if (this.entity == null) {
            return null;
        }
        Header header = this.entity.getContentType();
        if (header != null) {
            return header.getValue();
        }
        return null;
    }

    public InputStream getMessagePayload() throws IOException {
        if (this.entity == null) {
            return null;
        }
        return this.entity.getContent();
    }

    public Object unwrap() {
        return this.request;
    }
}
