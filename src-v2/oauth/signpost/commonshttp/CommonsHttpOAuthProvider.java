/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.DefaultHttpClient
 */
package oauth.signpost.commonshttp;

import java.io.IOException;
import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.commonshttp.HttpRequestAdapter;
import oauth.signpost.commonshttp.HttpResponseAdapter;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class CommonsHttpOAuthProvider
extends AbstractOAuthProvider {
    private static final long serialVersionUID = 1;
    private transient HttpClient httpClient;

    public CommonsHttpOAuthProvider(String string2, String string3, String string4) {
        super(string2, string3, string4);
        this.httpClient = new DefaultHttpClient();
    }

    public CommonsHttpOAuthProvider(String string2, String string3, String string4, HttpClient httpClient) {
        super(string2, string3, string4);
        this.httpClient = httpClient;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void closeConnection(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        if (httpResponse == null || (httpRequest = ((org.apache.http.HttpResponse)httpResponse.unwrap()).getEntity()) == null) return;
        try {
            httpRequest.consumeContent();
            return;
        }
        catch (IOException var1_2) {
            var1_2.printStackTrace();
            return;
        }
    }

    @Override
    protected HttpRequest createRequest(String string2) throws Exception {
        return new HttpRequestAdapter((HttpUriRequest)new HttpPost(string2));
    }

    @Override
    protected HttpResponse sendRequest(HttpRequest httpRequest) throws Exception {
        return new HttpResponseAdapter(this.httpClient.execute((HttpUriRequest)httpRequest.unwrap()));
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}

