/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 */
package oauth.signpost.commonshttp;

import java.io.IOException;
import java.io.InputStream;
import oauth.signpost.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

public class HttpResponseAdapter
implements HttpResponse {
    private org.apache.http.HttpResponse response;

    public HttpResponseAdapter(org.apache.http.HttpResponse httpResponse) {
        this.response = httpResponse;
    }

    @Override
    public InputStream getContent() throws IOException {
        return this.response.getEntity().getContent();
    }

    @Override
    public String getReasonPhrase() throws Exception {
        return this.response.getStatusLine().getReasonPhrase();
    }

    @Override
    public int getStatusCode() throws IOException {
        return this.response.getStatusLine().getStatusCode();
    }

    @Override
    public Object unwrap() {
        return this.response;
    }
}

