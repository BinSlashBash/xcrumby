/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.basic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import oauth.signpost.AbstractOAuthProvider;
import oauth.signpost.basic.HttpURLConnectionRequestAdapter;
import oauth.signpost.basic.HttpURLConnectionResponseAdapter;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;

public class DefaultOAuthProvider
extends AbstractOAuthProvider {
    private static final long serialVersionUID = 1;

    public DefaultOAuthProvider(String string2, String string3, String string4) {
        super(string2, string3, string4);
    }

    @Override
    protected void closeConnection(HttpRequest object, HttpResponse httpResponse) {
        if ((object = (HttpURLConnection)object.unwrap()) != null) {
            object.disconnect();
        }
    }

    @Override
    protected HttpRequest createRequest(String object) throws MalformedURLException, IOException {
        object = (HttpURLConnection)new URL((String)object).openConnection();
        object.setRequestMethod("POST");
        object.setAllowUserInteraction(false);
        object.setRequestProperty("Content-Length", "0");
        return new HttpURLConnectionRequestAdapter((HttpURLConnection)object);
    }

    @Override
    protected HttpResponse sendRequest(HttpRequest object) throws IOException {
        object = (HttpURLConnection)object.unwrap();
        object.connect();
        return new HttpURLConnectionResponseAdapter((HttpURLConnection)object);
    }
}

